package com.example.appmusickotlin.controller

import com.example.appmusickotlin.model.myUser

/**
 * Api đăng nhập
 */
abstract class Controller {
    abstract fun SignIn(email: String, password: String): String
    abstract fun SignUp(
        username: String,
        email: String,
        phoneNumber: String,
        password: String,
        rePassword: String,
    )
}

/**
 * Impl Api đăng nhập
 */

class ControllerImpl : Controller() {

    /**
     * Đăng nhập
     */
    override fun SignIn(email: String, password: String): String {
        if (email != myUser.email || password != myUser.password) {
            return "Tài khoản hoặc mật khẩu sai"
        } else {
            return "Đăng nhập thành công"
        }
    }

    /**
     * Đăng ký
     */
    override fun SignUp(
        username: String,
        email: String,
        phoneNumber: String,
        password: String,
        rePassword: String
    ) {
        myUser.username = username
        myUser.email = email
        myUser.phoneNumber = phoneNumber
        myUser.password = password
        myUser.rePassword = rePassword
    }

}