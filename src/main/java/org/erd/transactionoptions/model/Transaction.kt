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
import org.erd.paymentoptions.model.Payment
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Timestamp

@Component
@Entity
@Table(name = "transactions")
class Transaction {

    @Id
    var transaction_id: String = ""

    @Column(name = "transaction_date")
    var transaction_date: Timestamp = Timestamp(System.currentTimeMillis())

    @NotBlank(message = "Required Reference No.")
    @Column(name = "reference_no")
    var reference_no : String? = null

    @Column(name = "total_amount")
    var total_amount : BigDecimal = BigDecimal.ZERO

    @Column(name = "paid_amount")
    var paid_amount : BigDecimal = BigDecimal.ZERO

    @Column(name = "balance")
    var  balance : BigDecimal = BigDecimal.ZERO

    @Column(name = "status")
    var statu : String? = "Unpaid"

    var notes : String? = ""

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account_id: ChartOfAccounts? = null

    @OneToOne(mappedBy = "transaction")
    var capitalInjection: CapitalInjection? = null

    @ManyToOne
    @JoinColumn(name = "account_id")
    val method_Id: Payment? = null
















}