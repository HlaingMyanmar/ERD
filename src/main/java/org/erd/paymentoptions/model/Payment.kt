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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var method_Id: Int? = null,

    @NotBlank(message = "Method Code is required")
    @Column(name = "method_code", length = 150)
    var methodCode: String = "",

    @NotBlank(message = "Method Name is required")
    @Column(name = "method_name", length = 150)
    var methodName: String = "",

    @Column(name = "is_digital", length = 1)
    var isDigital: Byte = 0,

    @Column(name = "is_active", length = 1)
    var isActive: Byte = 0
)