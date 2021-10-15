package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

sealed class SearchAnimalAndFlowerState {
    object Empty : SearchAnimalAndFlowerState()
    object Loading : SearchAnimalAndFlowerState()
    object NoItemFound : SearchAnimalAndFlowerState()
    class Success(val result: List<AnimalAndFlower>) : SearchAnimalAndFlowerState()
    class Error(val error: String) : SearchAnimalAndFlowerState()
}