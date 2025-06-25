package com.isa.tugas3

data class UserResponse(
    val users: List<User>
)

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val university: String? = null
) 