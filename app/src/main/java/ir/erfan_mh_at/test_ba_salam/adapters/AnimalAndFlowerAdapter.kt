package ir.erfan_mh_at.test_ba_salam.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.erfan_mh_at.test_ba_salam.models.Animal
import ir.erfan_mh_at.test_ba_salam.models.Flower

class AnimalAndFlowerAdapter :
    RecyclerView.Adapter<AnimalAndFlowerAdapter.AnimalAndFlowerViewHolder>() {

    inner class AnimalAndFlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Pair<Animal, Flower>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Animal, Flower>,
            newItem: Pair<Animal, Flower>
        ) = (oldItem.first.id == newItem.first.id) && (oldItem.second.id == newItem.second.id)

        override fun areContentsTheSame(
            oldItem: Pair<Animal, Flower>,
            newItem: Pair<Animal, Flower>
        ) = (oldItem.first.name == newItem.first.name) &&
                (oldItem.second.name == newItem.second.name)
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalAndFlowerViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AnimalAndFlowerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(newList: List<Pair<Animal, Flower>>) = differ.submitList(newList)
    fun getCurrentList() = differ.currentList
}