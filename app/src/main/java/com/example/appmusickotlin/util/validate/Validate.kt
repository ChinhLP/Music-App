package com.example.appmusickotlin.util.validate

import com.example.appmusickotlin.model.User

/**
 * check validat
 */
class Validate(
    val username: String?,
    val email: String?,
    val password: String?,
    val rePassword: String?,
) {


    fun validUsername(): Boolean {
        return isValidUsername(username)
    }

    fun validEmail(): Boolean {

        return isValidEmail(email)
    }

    fun validPassword(): Boolean {
        return isValidPassword(password)
    }

    fun validRePassword(): Boolean {
        return isValidRePassword(rePassword)
    }

    private fun isValidUsername(username: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !specialChars.containsMatchIn(username!!) && !username.contains(" ")
    }

    private fun isValidEmail(email: String?): Boolean {
        // Sử dụng regex để kiểm tra định dạng email hợp lệ
        val regex = "^[a-zA-Z0-9._-]+@apero\\.vn\$"
        // Kiểm tra email có khớp với regex và không chứa khoảng trắng
        return email?.matches(regex.toRegex()) == true && !email.contains(" ")
    }


    private fun isValidPassword(password: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !password.isNullOrEmpty() && !password.contains(specialChars) && !password.contains(" ")
    }

    /**
     * check re password
     */
    private fun isValidRePassword(rePassword: String?): Boolean {
        return rePassword == password
    }


}