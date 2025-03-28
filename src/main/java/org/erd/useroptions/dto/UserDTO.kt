package org.erd.useroptions.dto

import org.erd.useroptions.models.Users
import org.springframework.stereotype.Component

@Component
class UserDTO private constructor() {
    private var user: Users? = null

    fun setUser(user: Users?) {
        this.user = user
    }

    fun getUser(): Users? {
        return this.user
    }

    companion object {
        @Volatile
        private var instance: UserDTO? = null

        @Synchronized
        fun getInstance(): UserDTO {
            if (instance == null) {
                instance = UserDTO()
            }
            return instance!!
        }
    }
}