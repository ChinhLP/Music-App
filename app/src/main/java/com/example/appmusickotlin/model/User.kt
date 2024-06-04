package com.example.appmusickotlin.model
 object myUser {
     var username: String? = ""
     var email: String? = ""
     var phoneNumber: String? = ""
     var password: String? = ""
     var rePassword: String? = ""
 }

class User(
    var username: String? = "",
    var email: String? = "",
    var phoneNumber: String? = "",
    var password: String? = "",
    var rePassword: String? = ""
) {


    fun validUsername(): Boolean{
        return isValidUsername(username)
    }
    fun validEmail(): Boolean{

        return isValidEmail(email)
    }
    fun validPhoneNumber(): Boolean{
        return isValidPhoneNumber(phoneNumber)
    }
    fun validPassword(): Boolean{
        return isValidPassword(password)
    }
    fun validRePassword(): Boolean{
        return isValidRePassword(rePassword)
    }

    // Phương thức kiểm tra username
    private fun isValidUsername(username: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !specialChars.containsMatchIn(username!!) && !username.contains(" ")
    }

    private fun isValidEmail(email: String?): Boolean {

        return email?.endsWith("@apero.vn") ?: false
    }

    private fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        if (phoneNumber == null) return false

        val digits = phoneNumber.filter { it.isDigit() }
        return digits.length in 10..11 && digits.length == phoneNumber.length
    }

    private fun isValidPassword(password: String?): Boolean {
        val specialChars = Regex("[^A-Za-z0-9]")
        return !password.isNullOrEmpty() && !password.contains(specialChars)
    }

    private fun isValidRePassword(rePassword: String?) : Boolean {
        return rePassword == password
    }


}

