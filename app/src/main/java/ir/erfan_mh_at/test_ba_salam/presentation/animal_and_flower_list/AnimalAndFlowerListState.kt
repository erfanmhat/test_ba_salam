package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

sealed class AnimalAndFlowerListState {
    object Empty : AnimalAndFlowerListState()
    object Loading : AnimalAndFlowerListState()
    class Success(val animalAndFlowerList: List<AnimalAndFlower>) : AnimalAndFlowerListState()
    class Error(val error: String) : AnimalAndFlowerListState()
}
