package org.erd.capitaloptions.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.erd.transactionoptions.model.Transaction
import org.springframework.stereotype.Component
import java.sql.Date


@Component
@Entity
@Table(name = "capital_injections")
class CapitalInjection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "injection_id")
    var injection_id : Int? = null

    @Column(name = "injection_date", nullable = false)
    var injection_date : Date? = null

    @Column(name = "amount")
    var amount : Double? = null

    @Column(name = "description")
    var description : String? = null

    @OneToOne
    @JoinColumn(name = "transaction_id", unique = true)
    var transaction: Transaction  = Transaction()


}