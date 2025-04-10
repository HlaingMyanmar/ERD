package org.erd.capitaloptions.impl

import org.erd.capitaloptions.model.CapitalInjections
import org.erd.dao.DataAccessObject
import org.springframework.stereotype.Repository

@Repository
class Capitaldb() : DataAccessObject<CapitalInjections>{



    override fun getAllData(): List<CapitalInjections?>? {
        TODO("Not yet implemented")
    }

    override fun findDataById(id: Int): CapitalInjections? {
        TODO("Not yet implemented")
    }

    override fun insertData(t: CapitalInjections?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateData(t: CapitalInjections?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteData(t: CapitalInjections?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}