package org.erd.transactionpaymentoptions

import java.math.BigDecimal
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "transaction_payments")
class TransactionPayment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private var payment_id: Int? = null

    @Column(name = "payment_date", nullable = false)
    private var payment_date: LocalDateTime = LocalDateTime.now()



    @Column(name = "amount", nullable = false)
    private var amount: BigDecimal = BigDecimal.ZERO

    @Column(name = "verified_by")
    private var verified_by: Int? = null

    @Column(name = "transaction_id", nullable = false)
    private var transaction_id: String = ""

    @Column(name = "method_id", nullable = false)
    private var method_id: Int = 1
}