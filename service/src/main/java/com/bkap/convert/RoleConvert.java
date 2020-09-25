package com.bkap.convert;

import com.bkap.dto.RoleDTO;
import com.bkap.entity.Role;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:47
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class RoleConvert {
    public static RoleDTO convertRoleToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getName());
        return roleDTO;
    }
}
