package com.bkap.convert;

import com.bkap.dto.RoleDTO;
import com.bkap.dto.UserDTO;
import com.bkap.entity.Role;
import com.bkap.entity.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:47
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class UserConvert {
    private UserConvert() {
    }

    public static UserDTO convertEntitytoDTO(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setUserName(users.getUserName());
        userDTO.setFullName(users.getFullName());
        userDTO.setEmail(users.getEmail());
        userDTO.setPassword(users.getPassword());
        userDTO.setAddress(users.getAddress());
        userDTO.setPhone(users.getPhone());
        userDTO.setCreatedAt(users.getCreatedAt());
        userDTO.setUpdatedAt(users.getUpdatedAt());
        userDTO.setStatus(users.getStatus());
        List<Role> roleList = users.getRoles();
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (Role role : roleList) {
            RoleDTO roleDTO = RoleConvert.convertRoleToDTO(role);
            roleDTOList.add(roleDTO);
        }
        userDTO.setRoleDTOList(roleDTOList);
        return userDTO;
    }

    public static Users convertDTOtoEntity(UserDTO users) {
        Users userDTO = new Users();
        userDTO.setUserName(users.getUserName());
        userDTO.setFullName(users.getFullName());
        userDTO.setEmail(users.getEmail());
        userDTO.setPassword(users.getPassword());
        userDTO.setAddress(users.getAddress());
        userDTO.setPhone(users.getPhone());
        return userDTO;
    }
}
