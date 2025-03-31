package org.erd.useroptions.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Entity
@Component
@Getter@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int user_id;

    @NotBlank(message = "အသုံးပြူသူ အမည်ထည့်ပါ။")
    @Column(name="username",length = 50)
    private String user_name;

    @NotBlank(message = "စကားဝှက် ထည့်သွင်းပါ။")
    @Column(name="password",length = 50)
    private String password;

    @Column(name="created_at")
    private Timestamp created_at;

    @Column(name="is_active",length = 1)
    private byte is_active;

    private String activation;


    public Users(String user_name, String password, byte is_active) {
        this.user_name = user_name;
        this.password = password;
        this.is_active = is_active;
    }

    public Users(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }


    public Users(int user_id, String user_name, String password, byte is_active) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.is_active = is_active;
    }


    public Users(int user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
    }

    public Users(int user_id, String user_name, String password, String activation) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.activation = activation;
    }

    public Users(int user_id, String user_name, String password, Timestamp created_at, String activation) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.created_at = created_at;
        this.activation = activation;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public byte getIs_active() {
        return is_active;
    }

    public void setIs_active(byte is_active) {
        this.is_active = is_active;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }
}
