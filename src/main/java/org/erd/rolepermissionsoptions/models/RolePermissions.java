package org.erd.rolepermissionsoptions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.erd.permissionsoptions.models.Permissions;
import org.erd.roleoptions.models.Roles;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name="role_permissions")

public class RolePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_permission_id;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Roles roles;

    @ManyToOne
    @JoinColumn(name="permission_id",nullable = false)
    private Permissions permission;

    @Column(name="is_active",length=1)
    private byte is_active;



}
