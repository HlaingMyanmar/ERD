package org.erd.capitaloptions.impls

import org.erd.capitaloptions.model.CapitalInjection
import org.erd.dao.DataAccessObject
import org.springframework.stereotype.Repository

@Repository
class Capitaldb : DataAccessObject<CapitalInjection>{


    override fun getAllData(): List<CapitalInjection?>? {
        TODO("Not yet implemented")
    }

    override fun findDataById(id: Int): CapitalInjection? {
        TODO("Not yet implemented")
    }

    override fun insertData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteData(t: CapitalInjection?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}