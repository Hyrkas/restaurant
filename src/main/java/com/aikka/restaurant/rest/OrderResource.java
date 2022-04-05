package com.aikka.restaurant.rest;

import com.aikka.restaurant.filter.RestaurantUserPrinciple;
import com.aikka.restaurant.model.Order;
import com.aikka.restaurant.model.dto.OrderCreateDTO;
import com.aikka.restaurant.model.dto.OrderStatusDTO;
import com.aikka.restaurant.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@Tag(name = "Orders", description = "api for creating/fetching customer orders")
@RestController
@RequestMapping(path = "orders/", produces = "application/json", consumes = "application/json")
public class OrderResource {

    @Autowired
    OrderService orderService;
    @Autowired
    RestaurantUserPrinciple principle;
    @GetMapping(path = "history")
    @PreAuthorize("hasAuthority('USER')")
    @Parameters(value = {
            @Parameter(
                    description = "Bearer Access token",
                    name = "Authorization",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            ),
            @Parameter(
                    description = "content type header",
                    name = "Content-Type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public Collection<Order> getHistory() {
        return orderService.fetchHistory(principle.getUserId());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @Parameters(value = {
            @Parameter(
                    description = "Bearer Access token",
                    name = "Authorization",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            ),
            @Parameter(
                    description = "content type header",
                    name = "Content-Type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public Order postOrder(@RequestBody OrderCreateDTO orderDTO) {
        return orderService.createOrder(orderDTO, principle.getUserId());
    }

    @PatchMapping(path = "{orderId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Parameters(value = {
            @Parameter(
                    description = "Bearer Access token",
                    name = "Authorization",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            ),
            @Parameter(
                    description = "content type header",
                    name = "Content-Type",
                    schema = @Schema(type = "string"),
                    in = ParameterIn.HEADER,
                    required = true
            )
    })
    public ResponseEntity<?> updateOrder(@PathVariable("orderId") Integer orderId, @RequestBody OrderStatusDTO status) {
        Order order = orderService.fetchOrderById(orderId);
        if (order == null) throw new RuntimeException("Order not found");
        if (order.getStatus() == Order.OrderStatus.CANCELLED || order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("not allowed to modify delivered/cancelled orders");
        }
        if (order.getStatus() != Order.OrderStatus.PENDING && status.getStatus() == Order.OrderStatus.PENDING) {
            throw new RuntimeException("not allowed to change status to PENDING after initial approval");
        }

        order.setStatus(status.getStatus());
        orderService.updateOrder(order);
        return ResponseEntity.ok().build();

    }
}
