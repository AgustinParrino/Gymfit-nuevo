package com.gymfit;
import jakarta.persistence.*;
@Entity public class Exercise {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false,length=100) public String name;
 @Column(nullable=false,length=40) public String muscle;
 @Column(nullable=false,length=40) public String difficulty;
 @Column(nullable=false,length=100) public String equipment;
 @Column(nullable=false,length=2000) public String description;
 public Exercise(){} public Exercise(String n,String m,String d,String e,String t){name=n;muscle=m;difficulty=d;equipment=e;description=t;}
}
