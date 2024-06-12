package com.example.appmusickotlin.controller

import android.util.Log
import android.view.MenuItem
import com.example.appmusickotlin.model.myUser

/**
 * Api đăng nhập
 */
abstract class Controller {
    abstract fun SignIn(username: String, password: String): String
    abstract fun SignUp(
        username: String,
        email: String,
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
    override fun SignIn(username: String, password: String): String {
        if (username != myUser.username || password != myUser.password) {
            return "Tài khoản hoặc mật khẩu sai"
        } else {
            return "Đăng nhập thành công"
        }
    }

    /**
     *
     * Đăng ký
     */
    override fun SignUp(
        username: String,
        email: String,
        password: String,
        rePassword: String
    ) {
        myUser.username = username
        myUser.email = email
        myUser.password = password
        myUser.rePassword = rePassword
    }

}