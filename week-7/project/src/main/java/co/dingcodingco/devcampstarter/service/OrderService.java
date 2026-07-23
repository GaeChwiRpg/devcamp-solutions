package co.dingcodingco.devcampstarter.service;

import co.dingcodingco.devcampstarter.domain.order.Order;
import co.dingcodingco.devcampstarter.domain.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Order create(Long productId, int quantity) {
        productService.decreaseStock(productId, quantity);
        Order order = new Order(productId, quantity);
        order.complete();
        return orderRepository.save(order);
    }
}
