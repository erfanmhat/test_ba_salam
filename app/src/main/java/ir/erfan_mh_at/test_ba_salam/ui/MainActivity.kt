package ir.erfan_mh_at.test_ba_salam.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.adapters.AnimalAndFlowerAdapter
import ir.erfan_mh_at.test_ba_salam.models.Animal
import ir.erfan_mh_at.test_ba_salam.models.Flower
import ir.erfan_mh_at.test_ba_salam.other.safeCallApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configure()
    }

    private fun configure() {
        val rv = findViewById<RecyclerView>(R.id.rvAnimalAndFlower)
        val adapter = AnimalAndFlowerAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(baseContext)
        adapter.submitList(
            listOf(
                Pair(Animal(1, "a1"), Flower(1, "f1")),
                Pair(Animal(2, "a2"), Flower(2, "f2")),
                Pair(Animal(3, "a3"), Flower(3, "f3")),
                Pair(Animal(4, "a4"), Flower(4, "f4")),
                Pair(Animal(5, "a5"), Flower(5, "f5"))
            )
        )
    }
}