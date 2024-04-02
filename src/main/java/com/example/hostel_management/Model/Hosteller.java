package com.example.hostel_management.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="hosteller")
public class Hosteller{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false,unique = true)
    private Integer PhoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name="parent_number", nullable = false)
    private Integer parentNumber;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_grad_year", columnDefinition = "ENUM('1', '2', '3', '4')")
    public GradYear currentGradYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch", columnDefinition = "ENUM('CSE', 'ECE', 'MECH', 'AI-ML', 'EEE', 'BT')")
    public Branch branch;

    public enum GradYear {
        YEAR_1, YEAR_2, YEAR_3, YEAR_4
    }

    public enum Branch {
        CSE, ECE, MECH, AI_ML, EEE, BT
    }


    @OneToMany(mappedBy = "hosteller",fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Complaint> complaintsLodged;

    @OneToMany(mappedBy = "hosteller",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeaveForm> leavesApplied;

}
