package com.Ecommerce.Ecommerce.service.impl;
import com.Ecommerce.Ecommerce.DTOs.OrderItemDto;
import com.Ecommerce.Ecommerce.DTOs.OrderRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.entity.Order;
import com.Ecommerce.Ecommerce.entity.OrderItem;
import com.Ecommerce.Ecommerce.entity.Product;
import com.Ecommerce.Ecommerce.entity.User;
import com.Ecommerce.Ecommerce.enums.OrderStatus;
import com.Ecommerce.Ecommerce.exception.NotFoundException;
import com.Ecommerce.Ecommerce.mapper.EntityDtoMapper;
import com.Ecommerce.Ecommerce.repositories.OrderItemRepo;
import com.Ecommerce.Ecommerce.repositories.OrderRepo;
import com.Ecommerce.Ecommerce.repositories.ProductRepo;
import com.Ecommerce.Ecommerce.service.interf.OrderItemService;
import com.Ecommerce.Ecommerce.service.interf.UserService;
import com.Ecommerce.Ecommerce.specification.OrderItemSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(
                orderItemRequest -> {
            Product product = productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().
                    multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;

        }).collect(Collectors.toList());
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderRepo.save(order);
        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();
    }


    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order Item not found"));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem>spec= Specification.allOf(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));
        Page<OrderItem>orderItemPage = orderItemRepo.findAll(spec,pageable);

        if (orderItemPage.isEmpty()){
            throw new NotFoundException("No Order found");
        }
        List<OrderItemDto>orderItemDtos = orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .orderItemList(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }


}


