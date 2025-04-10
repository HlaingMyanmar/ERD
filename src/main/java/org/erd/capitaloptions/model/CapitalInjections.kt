package org.erd.capitaloptions.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.erd.transactionoptions.models.Transactions
import org.springframework.stereotype.Component
import java.sql.Date


@Component
@Entity
@Table(name = "capital_injections")
class CapitalInjections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "injection_id")
    val injectionId: Int = 0

    @Column(name = "injection_date", nullable = false)
    val injectionDate: Date? = null

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    val amount: Double = 0.0

    @Column(name = "description")
    val description: String? = null 

    @OneToOne
    @JoinColumn(name = "transaction_id", unique = true)
    val transaction: Transactions?=null
}