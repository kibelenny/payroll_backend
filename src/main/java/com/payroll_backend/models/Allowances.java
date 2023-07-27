package com.payroll_backend.models;

import com.payroll_backend.enums.AllowanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Allowances {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    @Enumerated(EnumType.STRING)
    private AllowanceType type;
    private String name;
    private double amount;

    @Lob
    private String description;
    private boolean isTaxable;
    private boolean isRecurring;
    private LocalDateTime dateCreated;
    private LocalDate effectiveDate;

    public void setPayroll(String id) {
        payroll = new Payroll(id);
    }

}
