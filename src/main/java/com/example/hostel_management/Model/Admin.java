package com.example.hostel_management.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private Integer PhoneNumber;
}
