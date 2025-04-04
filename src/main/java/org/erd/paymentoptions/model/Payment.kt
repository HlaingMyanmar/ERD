package org.erd.paymentoptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "payment_methods")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int  method_id;

    @NotBlank(message = "Method Code is required")
    @Column(name = "method_code", length = 150)
    private String methodCode;

    @NotBlank(message = "Method Name is required")
    @Column(name = "method_name", length = 150)
    private String methodName ;

    @Column(name = "is_digital", length = 1)
    private Byte isdigital;

    @Column(name = "is_active", length = 1)
    private Byte isactive;


    public Payment(String methodCode, String methodName, Byte isdigital, Byte isactive) {
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.isdigital = isdigital;
        this.isactive = isactive;
    }
}
