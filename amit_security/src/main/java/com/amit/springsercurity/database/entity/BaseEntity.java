package com.amit.springsercurity.database.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    @Basic(
            optional = true
    )
    @Column(
            name = "created_at",
            nullable = true,
            updatable = false
    )
    private Date created;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = true
    )
    private Date lastUpdated;
}
