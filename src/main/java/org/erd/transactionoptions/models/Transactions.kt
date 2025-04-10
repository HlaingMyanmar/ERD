package org.erd.transactionoptions.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.erd.capitaloptions.model.CapitalInjections
import org.erd.chartofaccountoptions.model.ChartOfAccounts
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Entity
@Component
@Table(name = "transactions")
data class Transactions(
    @Id
    @Column(name = "transaction_id")
    val transaction_id: String? = null,

    @Column(name = "transaction_date")
    val transaction_date: Timestamp? = null,

    @NotBlank(message = "Required Reference No.")
    @Column(name = "reference_no")
    val reference_no: String? = null,

    @Column(name = "total_amount")
    val total_amount: Double? = null,

    @Column(name = "paid_amount")
    val paid_amount: Double? = null,

    @Column(name = "balance")
    val balance: Double? = null,

    @Column(name = "status")
    val status: String? = null,

    @Column(name = "notes")
    val notes: String? = null,

    @ManyToOne
    @JoinColumn(name = "account_id")
    val account_id: ChartOfAccounts? = null,

    @OneToOne(mappedBy = "transaction") // Fixed: points to the field name in CapitalInjections
    val capitalInjection: CapitalInjections? = null
)