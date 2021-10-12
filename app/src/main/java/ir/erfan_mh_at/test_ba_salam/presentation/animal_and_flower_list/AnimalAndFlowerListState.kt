package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_list

import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

sealed class AnimalAndFlowerListState(
    val animalAndFlowerList: List<AnimalAndFlower> = emptyList(),
    val error: String = ""
) {
    object Empty : AnimalAndFlowerListState()
    object Loading : AnimalAndFlowerListState()
    class Success(animalAndFlowerList: List<AnimalAndFlower>) :
        AnimalAndFlowerListState(animalAndFlowerList)

    class Error(errorMessage: String) : AnimalAndFlowerListState(error = errorMessage)
}
