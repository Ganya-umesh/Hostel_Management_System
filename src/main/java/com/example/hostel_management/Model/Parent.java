package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "parent")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parent{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String PhoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "hosteller-parent")
    private Hosteller hosteller; //joinColumn should come on the weaker side of the relationship


    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "parent-leaveforms")
    private List<LeaveForm> leaveFormsApproved;
}
