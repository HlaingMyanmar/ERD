package org.erd.capitaloptions.service

import org.erd.capitaloptions.impls.Capitaldb
import org.erd.capitaloptions.views.CapitalInjectionDTO
import org.springframework.stereotype.Service

@Service
class CaptialService (private val capitaldb: Capitaldb) {


    fun getCaptialInjectdataView(): List<CapitalInjectionDTO>{


        return capitaldb.getCapitalViewModel()


    }


}