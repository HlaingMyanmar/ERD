package org.erd.paymentoptions.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.springframework.stereotype.Component

@Component
@Entity
@Table(name = "payment_methods")
data class Payment(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val paymentId: Int=0,

    @NotBlank
    @Column(name="method_code",length = 150)
    val methodCode: String = "",

    @NotBlank
    @Column(name="method_name",length = 150)
    val methodName: String = "",

    @Column(name="is_digital",length = 1)
    val isdigital: Byte =0,

    @Column(name="is_active",length = 1)
    val isactive: Byte =0


)


