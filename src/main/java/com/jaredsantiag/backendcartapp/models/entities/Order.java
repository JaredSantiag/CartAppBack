package com.jaredsantiag.backendcartapp.models.entities;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGIST_DATE", nullable = false)
    private Calendar orderDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }
}
