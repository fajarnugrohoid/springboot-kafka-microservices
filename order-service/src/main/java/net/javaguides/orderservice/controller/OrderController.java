package net.javaguides.orderservice.controller;

import net.javaguides.basedomains.dto.OrderEvent;
import net.javaguides.basedomains.dto.OrderX;
import net.javaguides.orderservice.kafka.OrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }


    @PostMapping(value = "/orders", consumes = {"application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String placeOrder(@RequestBody OrderX orderx){

        LOGGER.info( "orders controller" );

        orderx.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrderx(orderx);

        orderProducer.sendMessage( orderEvent );

        return "Order placed successfully ...";
    }


    @GetMapping("/load")
    public String loadData(){
        return "test";
    }


}
