package com.bkap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "Name không được để trống")
    private String name;
    @NotEmpty(message = "Image không được để trống")
    private String image;
    @NotEmpty(message = "Mô tả không được để trống")
    private String description;
    @DecimalMin("0.00")
    @DecimalMax("99999999.00")
    private double price;
    private Date createdAt;
    private Date updatedAt;
    private int status;
    @NotEmpty(message = "Category không được để trống")
    private Long[] categoryIds;
    private List<CategoryDTO> categoryDTOList;

    public ProductDTO(ProductDTO productDTO) {
        this.id = productDTO.id;
        this.name = productDTO.name;
        this.price = productDTO.price;
        this.image = productDTO.image;
        this.description = productDTO.description;
    }

    public void setProducts(List<ProductDTO> productDTOS) {

    }
}
