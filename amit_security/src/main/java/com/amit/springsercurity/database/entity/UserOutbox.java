package com.amit.springsercurity.database.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_outbox" , schema = "amit")
public class UserOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "birthday", length = 64)
    private String birthday;
}
