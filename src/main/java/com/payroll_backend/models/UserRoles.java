package com.payroll_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.payroll_backend.dto.UserRolesDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdBy", "modifiedBy"})

@Entity
public class UserRoles {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String description;

    @Lob
    private String permissions;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private AppUser createdBy;

    private boolean defaultValue;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private AppUser modifiedBy;

    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public UserRolesDTO toDTO() {
        return new UserRolesDTO(
                this.id,
                this.name,
                this.description,
                this.business.getId(),
                this.defaultValue,
                this.dateCreated,
                Arrays.asList(this.permissions.split(","))
        );
    }

}
