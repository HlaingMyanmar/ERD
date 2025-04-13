package org.erd.capitaloptions.views

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.stereotype.Component
import java.time.LocalDate

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Component
data class CapitalInjectionDTO(

    val capitalDate: LocalDate? = null,
    val transactionCode: String? = null,
    val paymentMethodName: String? = null,
    val amount: Double? = 0.0,
    val description: String? = null

)

