package org.erd.paymentoptions.views

import org.springframework.stereotype.Component

@Component
data class PaymentView(


    val method_id: Int? =0,


    val methodCode: String = "",


    val methodName: String = "",


    val isdigital: String ="",


    val isactive: String =""



)
