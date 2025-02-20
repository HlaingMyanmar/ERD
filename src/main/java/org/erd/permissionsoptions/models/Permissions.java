package org.erd.permissionsoptions.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="permissions")
@Component

public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int permission_id;

    @NotBlank(message = "ခွင့်ပြုမည့်သူ အမည် ထည့်ပါ။")
    @Column(name="permission_name",length=100)
    private String permission_name;

    @Column(name="description",length=100)
    private String description;

    @Column(name = "is_active",length=1)
    private byte is_active;

    private String activation;

    public Permissions(String permission_name, String description, String activation) {
        this.permission_name = permission_name;
        this.description = description;
        this.activation = activation;
    }

    public Permissions(String permission_name, String description, byte is_active) {
        this.permission_name = permission_name;
        this.description = description;
        this.is_active = is_active;
    }

    public Permissions(String permission_name, String description) {
        this.permission_name = permission_name;
        this.description = description;
    }

    public Permissions(int permission_id, String permission_name, String description, byte is_active) {
        this.permission_id = permission_id;
        this.permission_name = permission_name;
        this.description = description;
        this.is_active = is_active;
    }

}
