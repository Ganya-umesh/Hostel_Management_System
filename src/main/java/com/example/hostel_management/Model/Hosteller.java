package com.example.hostel_management.Model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hosteller")
public class Hosteller{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name = "year",nullable = false)
    private Integer year;

    @Column(name="role",columnDefinition = "VARCHAR(255) DEFAULT 'ROLE_USER'")
    private String role;


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", year=" + year +
                '}';
    }

    public String getRole() {
        this.role = role;
        return null;
    }
}
