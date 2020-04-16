package com.apps.kim.weather.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
data class LoginBean(
    val code: Int?,
    val message: String?,
    val status: Boolean?,
    val user: UserModel?
)

data class UserModel(
    val id: Int?,
    val token: String?,
    val email: String?,
    val mobile: String?,
    val points: Int?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("img_url")
    val imgUrl: String?
)