package com.poemorder.app.service;

import com.poemorder.app.domain.order.Order;
import com.poemorder.app.domain.order.OrderStatus;
import com.poemorder.app.dto.OrderForm;
import com.poemorder.app.repo.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createFromForm(OrderForm form) {
        // honeypot
        if (form.getWebsite() != null && !form.getWebsite().isBlank()) {
            // тихо игнорим бота
            return null;
        }

        Order o = new Order();
        o.setName(form.getName());
        o.setPhone(form.getPhone());
        o.setSocial(form.getSocial());
        o.setDescription(form.getDescription());
        o.setStatus(OrderStatus.NEW);

        return orderRepository.save(o);
    }

    @Transactional(readOnly = true)
    public List<Order> listAll() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public Order get(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void changeStatus(long id, OrderStatus status) {
        Order o = get(id);
        o.setStatus(status);
    }
}
