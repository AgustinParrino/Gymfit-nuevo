package com.gymfit;
import jakarta.persistence.*;
@Entity public class RoutineItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) public Routine routine;
 @ManyToOne(optional=false) public Exercise exercise;
 public int position; public int sets; public int reps; public int restSeconds;
 public RoutineItem(){}
}
