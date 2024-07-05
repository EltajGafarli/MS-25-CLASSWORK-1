package org.example.ms25project.entity;


import jakarta.persistence.Entity;

@Entity
public class Shopping {
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
