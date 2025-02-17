package org.erd.roleoptions.models;


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
@Table(name="roles")
@Component
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int role_id;

    @NotBlank(message = "Require Role Name")
    @Column(name="role_name",length = 100)
    private String role_name;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "is_active",length = 1)
    private byte is_active;

}
