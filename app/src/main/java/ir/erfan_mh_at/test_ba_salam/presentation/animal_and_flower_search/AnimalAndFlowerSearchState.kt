package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import ir.erfan_mh_at.test_ba_salam.domain.model.AnimalAndFlower

sealed class AnimalAndFlowerSearchState {
    object Empty : AnimalAndFlowerSearchState()
    object Loading : AnimalAndFlowerSearchState()
    object NoItemFound : AnimalAndFlowerSearchState()
    class Success(val result: List<AnimalAndFlower>) : AnimalAndFlowerSearchState()
    class Error(val error: String) : AnimalAndFlowerSearchState()
}