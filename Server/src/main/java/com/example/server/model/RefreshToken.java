package com.example.server.model;

import lombok.Data;

import java.time.Instant;

import javax.persistence.*;
@Data
@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date",nullable = false)
    private Instant expiryDate;


}
