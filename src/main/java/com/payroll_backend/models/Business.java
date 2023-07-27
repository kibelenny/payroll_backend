package com.payroll_backend.models;

import com.payroll_backend.dto.BusinessDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Business {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Lob
    private String description;
    private LocalDateTime dateCreated;
    private UUID createdBy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private ContactInformation contactInformation;



    public Business(UUID id) {
        this.id = id;
    }

    public Business(String id) {
        this.id = UUID.fromString(id);
    }

    public BusinessDTO toDTO() {
        return new BusinessDTO(getId(), getName(), getDescription());
    }

}
