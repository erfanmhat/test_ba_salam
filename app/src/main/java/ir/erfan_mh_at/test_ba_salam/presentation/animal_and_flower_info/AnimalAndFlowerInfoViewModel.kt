package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_info

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

class AnimalAndFlowerInfoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    val animalAndFlower: AnimalAndFlower = savedStateHandle["animalAndFlower"]
        ?: throw IllegalArgumentException("missing animalAndFlower args")
}