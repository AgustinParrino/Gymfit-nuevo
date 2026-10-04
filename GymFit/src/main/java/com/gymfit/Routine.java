package com.gymfit;
import jakarta.persistence.*;
import java.util.*;
@Entity public class Routine {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false,length=100) public String name;
    @Column(nullable=false,length=100) public String goal;
    @Column(name="training_day",nullable=false,length=30) public String day;
    @Column(nullable=false,length=40) public String difficulty;
    @OneToMany(mappedBy="routine",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER) @OrderBy("position ASC") public List<RoutineItem> items=new ArrayList<>();
    public Routine(){}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    public Diet diet;
}
