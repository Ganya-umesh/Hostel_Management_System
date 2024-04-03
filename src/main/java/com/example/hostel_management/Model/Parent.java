package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "parent")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parent{
    @Id
    @Column(name = "hosteller_id", nullable = false)
    private Long hosteller_id;



    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private Integer PhoneNumber;

    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference(value = "hosteller-parent")
    private Hosteller hosteller;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "parent-leaveforms")
    private List<LeaveForm> leaveFormsApproved;
}
