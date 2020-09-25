package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.UserDTO;
import com.bkap.dto.VerificationTokenDTO;
import com.bkap.repositories.UserRepository;
import com.bkap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @created_at 22/07/2020 - 14:54
 * @created_by Tung lam
 * @since 22/07/2020
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    // get all user service - done
    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getAllUsers(Locale locale) {
        List<UserDTO> userDTOList = userService.getAllUsers();
        APIResponse<List<UserDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(userDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get user theo id service - done
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getUserById(@PathVariable("id") Long id, Locale locale) {
        UserDTO userDTO = userService.getUserById(id);
        APIResponse<UserDTO> apiResponse = new APIResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        apiResponse.setData(userDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // tìm user theo tên service - done
    @GetMapping("username/{username}")
    public ResponseEntity<APIResponse<UserDTO>> getUserByUsername(@PathVariable("username") String username, Locale locale) {
        UserDTO userDTO = userService.getUserByUserName(username);
        APIResponse<UserDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // tìm user theo email - done
    @GetMapping("email/{email}")
    public ResponseEntity<APIResponse<UserDTO>> findByEmail(@PathVariable("email") String email, Locale locale) {
        UserDTO userDTO = userService.getUserByMail(email);
        APIResponse<UserDTO> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // get all user theo page
    @GetMapping("/page")
    public ResponseEntity<APIResponse<Page<UserDTO>>> getAllProducts(Locale locale,
                                                                     @RequestParam(defaultValue = "", required = false) String searchValue,
                                                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                     @RequestParam(defaultValue = "5", required = false) Integer size,
                                                                     @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<UserDTO> userDTOS = userService.getUserByPage(searchValue, page, size, sortBy);
        APIResponse<Page<UserDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        responseData.setData(userDTOS);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/create") // tạo mới user service - done
    public ResponseEntity<APIResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO, Locale locale) {
        UserDTO request = userService.createUser(userDTO);
        APIResponse<UserDTO> apiResponse = new APIResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        apiResponse.setData(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/enable")
    public ResponseEntity<APIResponse<List<UserDTO>>> getAllUsersEnable(Locale locale) {
        List<UserDTO> userDTOList = userService.getAllUsersEnable();
        APIResponse<List<UserDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(userDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update") // dùng để updateProfile ở service - done
    public ResponseEntity<APIResponse<UserDTO>> updateUser(@RequestBody UserDTO userDTO, Locale locale) {
        UserDTO request = userService.updateUserById(userDTO);
        APIResponse<UserDTO> apiResponse = new APIResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        apiResponse.setData(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<APIResponse<UserDTO>> disableUser(@PathVariable("id") Long id, Locale locale) {
        UserDTO request = userService.disableUser(id);
        APIResponse<UserDTO> apiResponse = new APIResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        apiResponse.setData(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/toggle-block")
    public ResponseEntity<APIResponse<UserDTO>> updateUserBlockedStatus(
            @RequestParam Long id, @RequestParam Integer status) {
        UserDTO user = userService.updateUserBlockedStatus(id, status);
        APIResponse<UserDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(user);
        responseData.setMessage("Update user status successfull!");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<APIResponse<UserDTO>> updateNewPassword(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.changePassword(userDTO);
        APIResponse<UserDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(user);
        responseData.setMessage("Change password successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @PostMapping("/generate-verify-token")
    public ResponseEntity<APIResponse<String>> generateVerificationToken(@RequestBody UserDTO userDTO) {
        String token = userService.createVerificationToken(userDTO);
        APIResponse<String> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(token);
        responseData.setMessage("generate verification token for mail successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @GetMapping("/verify-token/{token}")
    public ResponseEntity<APIResponse<VerificationTokenDTO>> getVerificationToken(@PathVariable("token") String token) {
        VerificationTokenDTO verifyToken = userService.getVerificationToken(token);
        APIResponse<VerificationTokenDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(verifyToken);
        responseData.setMessage("get verification token successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @PostMapping("/generate-pass-reset-token")
    public ResponseEntity<APIResponse<String>> generatePasswordResetToken(@RequestBody UserDTO userDTO) {
        String token = userService.createPasswordResetToken(userDTO);
        APIResponse<String> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(token);
        responseData.setMessage("generate password token for mail successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @GetMapping("/pass-reset-token/{token}")
    public ResponseEntity<APIResponse<VerificationTokenDTO>> getPasswordResetToken(@PathVariable("token") String token) {
        VerificationTokenDTO verifyToken = userService.getPasswordResetToken(token);
        APIResponse<VerificationTokenDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(verifyToken);
        responseData.setMessage("get password reset token successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @PostMapping("/active")
    public ResponseEntity<APIResponse<UserDTO>> activeRegisteredUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.activeUser(userDTO);
        APIResponse<UserDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(user);
        responseData.setMessage("get verification token successfull");
        return new ResponseEntity<>(responseData, null, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> countUser() {
        Long userNum = userService.countUser();
        APIResponse<Long> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(userNum);
        responseData.setMessage("get number of user unblocked successfull");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
