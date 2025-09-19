package com.Ecommerce.Ecommerce.service.interf;


import com.Ecommerce.Ecommerce.DTOs.OrderRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.enums.OrderStatus;
import org.springframework.data.domain.Pageable;



import java.time.LocalDateTime;

public interface OrderItemService {

    Response placeOrder(OrderRequest orderRequest);

    Response updateOrderItemStatus(Long orderItemId, String status);

    Response filterOrderItems(OrderStatus status, LocalDateTime startDate,
                              LocalDateTime endDate, Long itemId, Pageable pageable);

}


