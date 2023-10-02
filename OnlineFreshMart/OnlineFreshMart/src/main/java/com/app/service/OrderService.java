package com.app.service;
 

import java.util.List;

import com.app.dto.MyOrderResponse;
import com.app.dto.UpdateDeliveryStatusRequest;
import com.app.entity.Orders;

public interface OrderService {
    void createOrderForCustomer(int userId) throws Exception;
    List<MyOrderResponse> getOrdersForUser(int userId);
    List<MyOrderResponse> getAllOrders();
    List<MyOrderResponse> getOrdersByOrderId(String orderId);
    List<MyOrderResponse> updateOrderDeliveryStatus(UpdateDeliveryStatusRequest deliveryRequest);
    List<MyOrderResponse> assignDeliveryPersonForOrder(UpdateDeliveryStatusRequest deliveryRequest);
    List<MyOrderResponse> getDeliveryOrders(int deliveryPersonId);
    
}

