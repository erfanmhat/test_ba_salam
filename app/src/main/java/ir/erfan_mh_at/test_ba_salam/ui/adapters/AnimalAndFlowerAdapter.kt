package ir.erfan_mh_at.test_ba_salam.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.databinding.ItemAnimalAndFlowerRowBinding
import ir.erfan_mh_at.test_ba_salam.data.models.Animal
import ir.erfan_mh_at.test_ba_salam.data.models.Flower
import ir.erfan_mh_at.test_ba_salam.other.numberOfCommonLetters

class AnimalAndFlowerAdapter :
    RecyclerView.Adapter<AnimalAndFlowerAdapter.AnimalAndFlowerViewHolder>() {

    inner class AnimalAndFlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var context: Context
    private lateinit var binding: ItemAnimalAndFlowerRowBinding

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
        context = parent.context
        binding = ItemAnimalAndFlowerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimalAndFlowerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AnimalAndFlowerViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        binding.apply {
            val animalName = currentItem.first.name
            val flowerName = currentItem.second.name
            tvAnimalAndFlowerName.text = context.getString(
                R.string.animal_and_flower_name, animalName, flowerName
            )

            tvNumberOfCommonLetters.text = context.getString(
                R.string.number_of_common_letters,
                numberOfCommonLetters(animalName, flowerName)
            )

            Glide.with(holder.itemView)
                .load(currentItem.first.image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .centerCrop()
                .into(ivAnimal)
            Glide.with(holder.itemView)
                .load(currentItem.second.image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .centerCrop()
                .into(ivFlower)
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(newList: List<Pair<Animal, Flower>>) = differ.submitList(newList)
    fun getCurrentList() = differ.currentList
}