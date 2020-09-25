package com.bkap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 23/07/2020 - 11:36
 * @created_by Tung lam
 * @since 23/07/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    @NotEmpty(message = "Name không được để trống")
    private String name;
    @NotEmpty(message = "Mô tả không được để trống")
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private int status;
    private List<ProductDTO> productDTOList;
}
