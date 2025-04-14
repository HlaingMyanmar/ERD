package org.erd.capitaloptions.service

import org.erd.capitaloptions.impls.Capitaldb
import org.erd.capitaloptions.model.CapitalInjection
import org.erd.capitaloptions.views.CapitalInjectionDTO
import org.erd.transactionoptions.model.Transaction
import org.hibernate.Session
import org.springframework.stereotype.Service

@Service
class CaptialService (private val capitaldb: Capitaldb) {


    fun getCaptialInjectdataView(): List<CapitalInjectionDTO>{


        return capitaldb.getCapitalViewModel()


    }

    fun insertTransactionCapitalInjection(t: Transaction, c: CapitalInjection?, session: Session): Boolean {
       return capitaldb.insertTransactionCapital(t,c,session)
    }


}