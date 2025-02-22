package org.erd.roleoptions.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.erd.rolepermissionsoptions.models.RolePermissions;
import org.erd.useroptions.models.Users;
import org.springframework.stereotype.Component;

import java.util.Set;
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="roles")
@Component
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    @NotBlank(message = "ရာထူး သတ်မှတ်ချက် ထည့်ပါ။")
    @Column(name="role_name",length = 100)
    private String role_name;

    @NotBlank(message = "သက်ဆိုင်ရာ တာဝန်များ ထည့်ပါ။")
    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "is_active",length = 1)
    private byte is_active;


    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RolePermissions> role_permissions;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private Users users;


    private String activation;


    /*
     DataView
     */
    public Roles(String role_name, String description, String activation) {
        this.role_name = role_name;
        this.description = description;
        this.activation = activation;
    }
    /* Data Insert*/
    public Roles(String role_name, String description, byte is_active) {
        this.role_name = role_name;
        this.description = description;
        this.is_active = is_active;
    }


    /*
    Data Insert without is_active
     */
    public Roles(String role_name, String description) {
        this.role_name = role_name;
        this.description = description;
    }

    /*
    Data Update
     */
    public Roles(int role_id, String role_name, String description, byte is_active) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.description = description;
        this.is_active = is_active;
    }
}
