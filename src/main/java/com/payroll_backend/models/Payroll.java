package com.payroll_backend.models;

import com.payroll_backend.enums.PaymentMode;
import com.payroll_backend.enums.PaymentPeriod;
import com.payroll_backend.enums.PaymentRate;
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
public class Payroll {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private AppUser employee;
    private String refNo;
    private double baseSalary;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode = PaymentMode.DIRECT_DEPOSIT;

    @Enumerated(EnumType.STRING)
    private PaymentPeriod paymentPeriod = PaymentPeriod.MONTHLY;

    @Enumerated(EnumType.STRING)
    private PaymentRate paymentRate = PaymentRate.MONTH;
    private int unpaidPayslips = 0; // number of periods gone without pay
    private int uncompletedPayments; // number of payslips not fully paid
    private String status;
    private LocalDateTime dateCreated;

    public Payroll(String id) {
        this.id = UUID.fromString(id);
    }

}
