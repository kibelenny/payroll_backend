package com.payroll_backend.models;

import com.payroll_backend.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payslip {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String payslipNo;

    @ManyToOne
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;
    private LocalDate issueDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private double basicSalary;
    private String deductions; // pattern: DEDUCTION_ID:AMOUNT#DEDUCTION_ID:AMOUNT
    private double totalDeductions;
    private String allowances; // pattern: ALLOWANCE_ID:AMOUNT#ALLOWANCE_ID:AMOUNT
    private double totalAllowances;
    private String earnings; // pattern: EARNING_ID:AMOUNT#EARNING_ID:AMOUNT
    private double totalEarnings;
    private double netSalary;
    private double paidAmount;
    private String status;
    private boolean fullyPaid;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    public Payslip(String id) {
        this.id = UUID.fromString(id);
    }
}
