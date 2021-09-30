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
import ir.erfan_mh_at.test_ba_salam.data.models.AnimalAndFlower
import ir.erfan_mh_at.test_ba_salam.other.commonLetters

class AnimalAndFlowerAdapter :
    RecyclerView.Adapter<AnimalAndFlowerAdapter.AnimalAndFlowerViewHolder>() {

    inner class AnimalAndFlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var context: Context
    private lateinit var binding: ItemAnimalAndFlowerRowBinding

    private val differCallback = object : DiffUtil.ItemCallback<AnimalAndFlower>() {
        override fun areItemsTheSame(
            oldItem: AnimalAndFlower,
            newItem: AnimalAndFlower
        ) = (oldItem.animal.id == newItem.animal.id) && (oldItem.flower.id == newItem.flower.id)

        override fun areContentsTheSame(
            oldItem: AnimalAndFlower,
            newItem: AnimalAndFlower
        ) = (oldItem.animal.name == newItem.animal.name) &&
                (oldItem.flower.name == newItem.flower.name)
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
            val animalName = currentItem.animal.name
            val flowerName = currentItem.flower.name
            tvAnimalAndFlowerName.text = context.getString(
                R.string.animal_and_flower_name, animalName, flowerName
            )

            tvNumberOfCommonLetters.text = context.getString(
                R.string.number_of_common_letters,
                commonLetters(animalName, flowerName).length
            )

            Glide.with(holder.itemView)
                .load(currentItem.animal.image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .centerCrop()
                .into(ivAnimal)
            Glide.with(holder.itemView)
                .load(currentItem.flower.image)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .centerCrop()
                .into(ivFlower)

            clCircle.setOnClickListener {
                circleOnClickListener?.let {
                    it(currentItem)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(newList: List<AnimalAndFlower>) = differ.submitList(newList)

    private var circleOnClickListener: ((AnimalAndFlower) -> Unit)? = null

    fun setCircleOnClickListener(listener: ((AnimalAndFlower) -> Unit)?) {
        circleOnClickListener = listener
    }
}