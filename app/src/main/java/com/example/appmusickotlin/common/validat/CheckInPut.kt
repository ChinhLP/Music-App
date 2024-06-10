package com.example.appmusickotlin.common.validat

import com.example.appmusickotlin.model.User

/**
 * check validat
 */
class CheckInput(
    val user: User,
) {


    fun validUsername(): Boolean {
        return isValidUsername(user.username)
    }

    fun validEmail(): Boolean {

        return isValidEmail(user.email)
    }

    fun validPhoneNumber(): Boolean {
        return isValidPhoneNumber(user.phoneNumber)
    }

    fun validPassword(): Boolean {
        return isValidPassword(user.password)
    }

    fun validRePassword(): Boolean {
        return isValidRePassword(user.rePassword)
    }

    /**
     * check username
     */
    private fun isValidUsername(username: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !specialChars.containsMatchIn(username!!) && !username.contains(" ")
    }

    /**
     * check email
     */
    private fun isValidEmail(email: String?): Boolean {

        return email?.endsWith("@apero.vn") ?: false
    }

    /**
     * check phone number
     */
    private fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        if (phoneNumber == null) return false

        val digits = phoneNumber.filter { it.isDigit() }
        return digits.length in 10..11 && digits.length == phoneNumber.length
    }

    /**
     *  check password
     */
    private fun isValidPassword(password: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !password.isNullOrEmpty() && !password.contains(specialChars)
    }

    /**
     * check re password
     */
    private fun isValidRePassword(rePassword: String?): Boolean {
        return rePassword == user.password
    }


}