package com.payroll_backend.models;


import com.payroll_backend.dto.AppUserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * AppUser is the employee
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser extends Person implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String password;
    @Column(unique = true)
    private String email;
    private String title;
    @Lob
    private String description;
    private UUID deletedBy;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private UserRoles role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactInformation_id")
    private List<ContactInformation> contactInformation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    public AppUser(UUID id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role == null) {
            return authorities;
        }
        String[] roleList = role.getPermissions().split(",");

        Arrays.stream(roleList).forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AppUserDTO toDTO() {
        return new AppUserDTO(getId(), getFirstName(), getLastName(), getUsername(), getEmail(), getPhone(), getTitle(), getDescription(), getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }
}
