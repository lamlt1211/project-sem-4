package com.bkap.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 04/08/2020 - 10:22
 * @created_by Tung lam
 * @since 04/08/2020
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApiLog {
    @Id
    @GeneratedValue
    private Long id;
    private Date calledTime;
    private String data;
    private String errorMessage;
    private int retryNum;
}
