package com.amit.springsercurity.database.entity;

import com.amit.springsercurity.model.request.AddUserRequest;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user" , schema = "amit")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", length = 64)
    private String userName;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "age")
    private Integer age;

    public User(AddUserRequest request){
        this.userName = request.getUserName();
        this.name = request.getName();
        this.age = request.getAge();
    }

    public User() {

    }
}
