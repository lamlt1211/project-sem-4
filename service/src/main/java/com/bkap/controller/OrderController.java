package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
import com.bkap.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:23
 * @created_by Tung lam
 * @since 22/07/2020
 */
@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageSource messageSource;

    // get all order - done
    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrder(Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrders();
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy ra tất cả những order phân trang = done
    @GetMapping("/page")
    public ResponseEntity<APIResponse<Page<OrderDTO>>> getAllOrders(Locale locale,
                                                                    @RequestParam(defaultValue = "", required = false) String searchValue,
                                                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                    @RequestParam(defaultValue = "5", required = false) Integer size,
                                                                    @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<OrderDTO> orderDTOS = orderService.getOrderByPage(searchValue, page, size, sortBy);
        APIResponse<Page<OrderDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        responseData.setData(orderDTOS);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // get all Order Order by username - done
    @GetMapping("/{username}")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrderByUserName(@PathVariable("username") String username, Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrdersByUserName(username);
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get all Order by Id - done
    @GetMapping("/id/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> getOrderById(@PathVariable("id") Long id, Locale locale) {
        OrderDTO orderDTO = orderService.findOrderById(id);
        APIResponse<OrderDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Tạo mới một order
    @PostMapping
    public ResponseEntity<APIResponse<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO, Locale locale) {
        OrderDTO orderDTO1 = orderService.createOrder(orderDTO);
        APIResponse<OrderDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTO1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // view order in clien - done
    @GetMapping("/view")
    public ResponseEntity<APIResponse<OrderDTO>> viewOrder(
            @RequestParam("id") Long id) {
        OrderDTO orderDTO = orderService.findOrderById(id);
        APIResponse<OrderDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(orderDTO);
        responseData.setMessage("get by id successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // view order in admin - done
    @GetMapping("/detail")
    public ResponseEntity<APIResponse<List<OrderDetailDTO>>> viewOrderDetail(
            @RequestParam("id") Long id) {
        List<OrderDetailDTO> orderDTO = orderService.findOrderDetailById(id);
        APIResponse<List<OrderDetailDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());

        responseData.setData(orderDTO);
        responseData.setMessage("get by id successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Thay đổi trạng thái của đơn hàng , bắt đầu từ tiếp nhận , xử lý , hoàn thành
    @GetMapping("/change/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> changeOrderStatus(@PathVariable("id") Long id, Locale locale) {
        OrderDTO orderDTO = orderService.changeOrderStatus(id);
        APIResponse<OrderDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Test (copy )
    @PutMapping
    public ResponseEntity<APIResponse<OrderDTO>> changeStatus(@RequestBody OrderDTO ordersDTO) {
        orderService.updateOrderStatus(ordersDTO);
        APIResponse<OrderDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("update successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // detail order client - order me
    @GetMapping("/member")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrderMember(
            @RequestParam("username") String member,
            @RequestParam("status") Integer status) {
        List<OrderDTO> orders = orderService.getAllOrderMember(member, status);
        APIResponse<List<OrderDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(orders);
        responseData.setMessage("get successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // cancel order in client - order me
    @GetMapping("/cancel/{id}")
    public ResponseEntity<APIResponse<OrderDTO>> cancelOrderStatus(@PathVariable("id") Long id, Locale locale) {
        OrderDTO orderDTO = orderService.cancelOrder(id);
        APIResponse<OrderDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy ra tất cả những order đang ở trạng thái hủy bỏ
    @GetMapping("/cancel")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrdersCancel(Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrdersCancel();
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Lấy ra tất cả những Order đang ở trạng thái đã xử lý thành công
    @GetMapping("/success")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrdersSuccess(Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrdersSuccess();
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // get tất cả những Order đang ở trạng thái đang xử lý (xác nhận )
    @GetMapping("/confirm")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrdersConfirm(Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrdersConfirm();
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Lấy ra tất cả những Order đang ở trạng thái đã tiếp nhận , chưa xử lý
    @GetMapping("/processing")
    public ResponseEntity<APIResponse<List<OrderDTO>>> getAllOrdersProcessing(Locale locale) {
        List<OrderDTO> orderDTOList = orderService.getAllOrdersProcessing();
        APIResponse<List<OrderDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(orderDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> countOrder() {
        Long orderNum = orderService.countOrder();
        APIResponse<Long> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(orderNum);
        responseData.setMessage("get number of order on hold successful");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
