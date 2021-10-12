package ir.erfan_mh_at.test_ba_salam.domain.model

import java.io.Serializable


data class AnimalAndFlower(
    val animal: Animal,
    val flower: Flower
) : Serializable