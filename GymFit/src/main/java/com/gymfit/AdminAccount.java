package com.gymfit;
import jakarta.persistence.*;
@Entity public class AdminAccount {
 @Id public String username;
 @Column(nullable=false,length=100) public String passwordHash;
 public AdminAccount(){} public AdminAccount(String u,String p){username=u;passwordHash=p;}
}
