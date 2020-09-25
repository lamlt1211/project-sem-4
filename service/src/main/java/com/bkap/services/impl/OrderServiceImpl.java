package com.bkap.services.impl;

import com.bkap.convert.OrderConvert;
import com.bkap.convert.OrderDetailConvert;
import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
import com.bkap.entity.OrderDetail;
import com.bkap.entity.OrderStatus;
import com.bkap.entity.Orders;
import com.bkap.entity.Product;
import com.bkap.entity.Users;
import com.bkap.exceptions.NotFoundException;
import com.bkap.repositories.OrderDetailRepository;
import com.bkap.repositories.OrderRepository;
import com.bkap.repositories.ProductRepository;
import com.bkap.repositories.UserRepository;
import com.bkap.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:57
 * @created_by Tung lam
 * @since 22/07/2020
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Orders> ordersList = orderRepository.findAll();
        if (ordersList.isEmpty()) {
            log.error("Get all fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        ordersList.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("Get all success");
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserName(String username) {
        List<Orders> orders = orderRepository.findAllByUsersUserName(username);
        if (orders.isEmpty()) {
            log.error("Get all fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orders.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("Get all success");
        return orderDTOList;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Orders orders = OrderConvert.convertDTOtoEntity(orderDTO);
        Users users = userRepository.findByUserName(orderDTO.getUserDTO().getUserName());
        orders.setUsers(users);
        Orders orders1 = orderRepository.save(orders);
        //Convert OrderDetailDto to Entity and save to db
        List<OrderDetailDTO> orderDetailDTOS = orderDTO.getOrderDetailDTOList();
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailDTOS.forEach(o -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(o.getQuantity());
            orderDetail.setPrice(o.getPrice());
            orderDetail.setStatus(1);
            orderDetail.setOrders(orders1);
            Optional<Product> product = productRepository.findById(o.getProductDTO().getId());
            orderDetail.setProduct(product.get());
            orderDetails.add(orderDetail);
            orderDetailRepository.save(orderDetail);
        });
        orders1.setOrderDetailDTOList(orderDetails);
        Orders orders2 = orderRepository.save(orders1);
        return OrderConvert.convertEntityToDTO(orders2);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public OrderDTO changeOrderStatus(Long id) {
        Optional<Orders> ordersOptional = orderRepository.findById(id);
        if (ordersOptional.isPresent()) {
            switch (ordersOptional.get().getStatus()) {
                case 0:
                    ordersOptional.get().setStatus(OrderStatus.ORDER_CONFIRM.getValue());
                    log.info("Change ORDER_PROCESSING success");
                    break;
                case 1:
                    ordersOptional.get().setStatus(OrderStatus.ORDER_SUCCESS.getValue());
                    log.info("Change ORDER_CONFIRM success");
                    break;
                default:
                    break;
            }
            return OrderConvert.convertEntityToDTO(orderRepository.save(ordersOptional.get()));
        }
        log.error("Id not found");
        throw new NotFoundException("NotFound.id");
    }

    @Override
    @Transactional(rollbackFor = NotFoundException.class)
    public OrderDTO cancelOrder(Long id) {
        Optional<Orders> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            if (orderOptional.get().getStatus() == OrderStatus.ORDER_PROCESSING.getValue()) {
                orderOptional.get().setStatus(OrderStatus.ORDER_CANCLED.getValue());
                orderOptional.get().setUpdatedAt(new Date());
            } else if (orderOptional.get().getStatus() == OrderStatus.ORDER_CANCLED.getValue()) {
                orderOptional.get().setStatus(OrderStatus.ORDER_PROCESSING.getValue());
                orderOptional.get().setUpdatedAt(new Date());
            }
            return OrderConvert.convertEntityToDTO(orderRepository.save(orderOptional.get()));
        }
        log.error("Id not found");
        throw new NotFoundException("NotFound.id");
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id) {
        Optional<Orders> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            log.info("findOrderById success");
            return OrderConvert.convertEntityToDTO(orderOptional.get());
        }
        log.error("Id not found");
        throw new NotFoundException("NotFound.id");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailDTO> findOrderDetailById(Long id) {
        Optional<Orders> orderOptional = orderRepository.findById(id);
        List<OrderDetail> orderDetailList = orderOptional.get().getOrderDetailDTOList();
        List<OrderDetailDTO> result = new ArrayList<>();
        for (OrderDetail od : orderDetailList) {
            result.add(OrderDetailConvert.convertEntitytoDTO(od));
        }
        return result;
    }

    @Override
    public List<OrderDTO> getAllOrdersProcessing() {
        List<Orders> ordersList = orderRepository.findAllOrderProcessing();
        if (ordersList.isEmpty()) {
            log.error("getAllOrdersProcessing fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        ordersList.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("getAllOrdersProcessing success");
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrdersConfirm() {
        List<Orders> ordersList = orderRepository.findAllOrderOrderConfirm();
        if (ordersList.isEmpty()) {
            log.error("getAllOrdersConfirm fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        ordersList.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("getAllOrdersConfirm success");
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrdersSuccess() {
        List<Orders> ordersList = orderRepository.findAllOrderSuccess();
        if (ordersList.isEmpty()) {
            log.error("getAllOrdersSuccess fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        ordersList.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("getAllOrdersSuccess success");
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrdersCancel() {
        List<Orders> ordersList = orderRepository.findAllOrderCancel();
        if (ordersList.isEmpty()) {
            log.error("getAllOrdersCancel fail");
            throw new NotFoundException("Empty.order");
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        ordersList.forEach(or -> {
            OrderDTO orderDTO = OrderConvert.convertEntityToDTO(or);
            orderDTOList.add(orderDTO);
        });
        log.info("getAllOrdersCancel success");
        return orderDTOList;
    }

    public boolean updateOrderStatus(OrderDTO orderDTO) {
        Optional<Orders> orders = orderRepository.findById(orderDTO.getId());
        if (orders.isPresent()) {
            switch (orderDTO.getStatus()) {
                case 0:
                    orders.get().setStatus(1);
                    break;
                case 1:
                    orders.get().setStatus(2);
                    break;
                default:
                    orders.get().setStatus(3);
                    break;
            }
            if (orders.get() != null) {
                orderRepository.save(orders.get());
                return true;
            }
        }
        return false;
    }

    public boolean cancelOrderStatus(Long orderId) {
        Optional<Orders> orders = orderRepository.findById(orderId);
        if (orders.isPresent()) {
            if (orders.get().getStatus() == 0) {
                orders.get().setStatus(3);
                orders.get().setUpdatedAt(new Date());
            } else if (orders.get().getStatus() == 3) {
                orders.get().setStatus(0);
                orders.get().setUpdatedAt(new Date());
            }
            if (orders.get() != null) {
                orderRepository.save(orders.get());
                return true;
            }
        }
        return false;
    }

    public Long countOrder() {
        return orderRepository.count();
    }

    @Override
    public Page<OrderDTO> getOrderByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Orders> pageResult = orderRepository.findByUsers_UserName(searchValue, pageable);
        return pageResult.map(OrderConvert::convertEntityToDTO);
    }

    public List<OrderDTO> getAllOrderMember(String username, int status) {
        List<Orders> listResult = orderRepository.findByUsersUserName(username, status);
        return listResult.stream().map(OrderConvert::convertEntityToDTO).collect(Collectors.toList());
    }


}
