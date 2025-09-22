package com.Ecommerce.Ecommerce.controllers;

import com.Ecommerce.Ecommerce.DTOs.OrderRequest;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.enums.OrderStatus;
import com.Ecommerce.Ecommerce.service.interf.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "OrderItem APIs")
public class OrderItemController {

    private final OrderItemService orderItemService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/crete")
    public ResponseEntity<Response>placeOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-item-status/{orderItemId}")
    public ResponseEntity<Response>updateOrderItemStatus(@PathVariable Long orderItemId,@RequestParam String status){
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderItemId,status));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Response>filterOrderItem(
            @RequestParam(required = false)@DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
            @RequestParam(required = false)@DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
            @RequestParam(required = false)String status,
            @RequestParam(required = false)Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000")int size
            ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));
        OrderStatus orderStatus= status!=null?OrderStatus.valueOf(status.toUpperCase()):null;
        return ResponseEntity.ok(orderItemService.filterOrderItems(orderStatus,startDate,endDate,itemId,pageable));

    }

}


