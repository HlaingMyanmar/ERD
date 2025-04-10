package org.erd.transactionoptions.impl

import org.erd.dao.DataAccessObject
import org.erd.transactionoptions.models.Transactions
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class Transactiondb(val sessionFactory: SessionFactory) : DataAccessObject<Transactions> {



    override fun getAllData(): List<Transactions?>? {
        TODO("Not yet implemented")
    }

    override fun findDataById(id: Int): Transactions? {
        TODO("Not yet implemented")
    }

    override fun insertData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteData(t: Transactions?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteDataById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}