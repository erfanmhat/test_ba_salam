package ir.erfan_mh_at.test_ba_salam.domain.model

import java.io.Serializable

data class Animal(
    val id: Int,
    val name: String,
    val image: String
) : Serializable
