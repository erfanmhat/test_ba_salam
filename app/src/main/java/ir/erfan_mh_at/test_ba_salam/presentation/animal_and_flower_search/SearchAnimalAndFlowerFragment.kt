package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.erfan_mh_at.test_ba_salam.databinding.FragmentSearchAnimalAndFlowerBinding

class SearchAnimalAndFlowerFragment:Fragment() {

    lateinit var binding: FragmentSearchAnimalAndFlowerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchAnimalAndFlowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configure()
    }

    private fun configure(){

    }
}