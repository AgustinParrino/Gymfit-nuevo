package com.gymfit;
import jakarta.persistence.*;
import java.time.*;
@Entity public class WorkoutLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,length=100) public String routineName;
 public LocalDate date; public int minutes;
 @Column(length=1000) public String notes;
 public WorkoutLog(){}
}
