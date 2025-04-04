package org.erd.paymentoptions.service

import org.erd.paymentoptions.impl.PaymentDb
import org.erd.paymentoptions.model.Payment
import org.springframework.stereotype.Service


@Service
class PaymentService ( private val paymentDb: PaymentDb){

    fun getAllPayment(): List<Payment> {

        return paymentDb.allData
    }

    fun findById(id:Int): Payment? {
        return paymentDb.findDataById(id)
    }

    fun save(payment: Payment) : Boolean {
        return paymentDb.insertData(payment)
    }
    
    fun update(payment: Payment) : Boolean {
        return paymentDb.updateData(payment)
    }

    fun delete(payment: Payment) : Boolean {
        return paymentDb.deleteData(payment)
    }

    fun deleteById(id: Int?) : Boolean {
        return paymentDb.deleteDataById(id)
    }



}