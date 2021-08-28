package com.tenniscourts.config.persistence;

import com.tenniscourts.audit.CustomAuditEntityListener;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(CustomAuditEntityListener.class)
public class BaseEntity<T> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    private String ipNumberUpdate;

    private Long userCreate;

    private Long userUpdate;

    @LastModifiedDate
    private LocalDateTime dateUpdate;

    private String ipNumberCreate;

    @CreatedDate
    private LocalDateTime dateCreate;

}
