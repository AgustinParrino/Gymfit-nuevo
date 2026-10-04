package com.gymfit;

import jakarta.persistence.*;

@Entity
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 100)
    public String name;

    @Column(nullable = false, length = 300)
    public String goal;

    @Column(nullable = false, length = 50)
    public String type;

    @Column(nullable = false, length = 2000)
    public String description;

    @Column(nullable = false, length = 4000)
    public String meals;

    public Diet() {}

    public Diet(String name, String goal, String type, String description, String meals) {
        this.name = name;
        this.goal = goal;
        this.type = type;
        this.description = description;
        this.meals = meals;
    }
}
