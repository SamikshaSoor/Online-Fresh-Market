package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.MyOrderResponse;
import com.app.dto.UpdateDeliveryStatusRequest;
import com.app.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/user/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    // Endpoint to create an order for a customer
    @PostMapping("order")
    public ResponseEntity<String> customerOrder(@RequestParam("userId") int userId) {
        try {
            orderService.createOrderForCustomer(userId); // Call the service to create an order for the customer
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
        return new ResponseEntity<>("ORDER SUCCESS", HttpStatus.OK); // Return success message with OK status
    }

    // Endpoint to get orders for a specific user
    @GetMapping("myorder")
    public ResponseEntity<List<MyOrderResponse>> getMyOrder(@RequestParam("userId") int userId) {
        List<MyOrderResponse> orderDatas = orderService.getOrdersForUser(userId); // Call the service to get orders for the user
        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return orders with OK status
    }

    // Endpoint to get all orders for admin
    @GetMapping("admin/allorder")
    public ResponseEntity<List<MyOrderResponse>> getAllOrder() {
        List<MyOrderResponse> orderDatas = orderService.getAllOrders(); // Call the service to get all orders
        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return orders with OK status
    }

    // Endpoint to get orders by order ID for admin
    @GetMapping("admin/showorder")
    public ResponseEntity<List<MyOrderResponse>> getOrdersByOrderId(@RequestParam("orderId") String orderId) {
        List<MyOrderResponse> orderDatas = orderService.getOrdersByOrderId(orderId); // Call the service to get orders by order ID
        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return orders with OK status
    }

    // Endpoint to update delivery status of an order for admin
    @PostMapping("admin/order/deliveryStatus/update")
    public ResponseEntity<List<MyOrderResponse>> updateOrderDeliveryStatus(
            @RequestBody UpdateDeliveryStatusRequest deliveryRequest) throws JsonProcessingException {
        // Process request to update delivery status
        List<MyOrderResponse> orderDatas = orderService.updateOrderDeliveryStatus(deliveryRequest);

        // Convert response to JSON format for debugging
        String orderJson = objectMapper.writeValueAsString(orderDatas);
        System.out.println(orderJson);

        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return updated orders with OK status
    }

    // Endpoint to assign a delivery person for an order for admin
    @PostMapping("admin/order/assignDelivery")
    public ResponseEntity<List<MyOrderResponse>> assignDeliveryPersonForOrder(
            @RequestBody UpdateDeliveryStatusRequest deliveryRequest) throws JsonProcessingException {
        // Process request to assign delivery person
        List<MyOrderResponse> orderDatas = orderService.assignDeliveryPersonForOrder(deliveryRequest);

        // Convert response to JSON format for debugging
        String orderJson = objectMapper.writeValueAsString(orderDatas);
        System.out.println(orderJson);

        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return updated orders with OK status
    }

    // Endpoint to get delivery orders for a specific delivery person
    @GetMapping("delivery/myorder")
    public ResponseEntity<List<MyOrderResponse>> getMyDeliveryOrders(
            @RequestParam("deliveryPersonId") int deliveryPersonId) throws JsonProcessingException {
        System.out.println("request came for MY DELIVERY ORDER for USER ID : " + deliveryPersonId);

        // Get delivery orders for the delivery person
        List<MyOrderResponse> orderDatas = orderService.getDeliveryOrders(deliveryPersonId);

        // Convert response to JSON format for debugging
        String json = objectMapper.writeValueAsString(orderDatas);
        System.out.println(json);

        return new ResponseEntity<>(orderDatas, HttpStatus.OK); // Return delivery orders with OK status
    }
}
