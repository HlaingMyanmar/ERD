package org.erd.transactionoptions.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.erd.capitaloptions.model.CapitalInjection
import org.erd.chartofaccountoptions.model.ChartOfAccounts
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
@Entity
@Table(name = "transactions")
class Transaction {

    @Id
    var transaction_id: Int = 0

    @Column(name = "transaction_date")
    var transaction_date: Timestamp = Timestamp(System.currentTimeMillis())

    @NotBlank(message = "Required Reference No.")
    @Column(name = "reference_no")
    var reference_no : String? = null

    @Column(name = "total_amount")
    var total_amount : Double = 0.0

    @Column(name = "paid_amount")
    var paid_amount : Double = 0.0

    @Column(name = "balance")
    var  balance : Double = 0.0

    @Column(name = "status")
    var statu : String? = "Unpaid"

    var notes : String? = ""

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account_id: ChartOfAccounts? = null

    @OneToOne(mappedBy = "transaction")
    val capitalInjection: CapitalInjection? = null
















}