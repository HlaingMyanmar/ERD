package org.erd.useroptions.models;

import com.google.protobuf.Timestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.erd.roleoptions.models.Roles;
import org.springframework.stereotype.Component;

@Entity
@Component
@Getter@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @NotBlank(message = "အသုံးပြူသူ အမည်ထည့်ပါ။")
    @Column(name="usr_name",length = 50)
    private String user_name;

    @NotBlank(message = "စကားဝှက် ထည့်သွင်းပါ။")
    @Column(name="password",length = 50)
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    private Roles role;

    private Timestamp crated_at;

    @Column(name="is_active",length = 1)
    private byte is_active;


    public Users(String user_name, String password, byte is_active) {
        this.user_name = user_name;
        this.password = password;
        this.is_active = is_active;
    }

    public Users(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }
}
