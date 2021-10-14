package ir.erfan_mh_at.test_ba_salam.presentation.animal_and_flower_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.erfan_mh_at.test_ba_salam.R
import ir.erfan_mh_at.test_ba_salam.databinding.ItemAnimalAndFlowerImageBinding

class AnimalAndFlowerImageAdapter :
    RecyclerView.Adapter<AnimalAndFlowerImageAdapter.AnimalAndFlowerImageViewHolder>() {

    inner class AnimalAndFlowerImageViewHolder(b: ItemAnimalAndFlowerImageBinding) :
        RecyclerView.ViewHolder(b.root) {
        val binding: ItemAnimalAndFlowerImageBinding = b
    }

    private var imageList: List<String> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AnimalAndFlowerImageViewHolder(
        ItemAnimalAndFlowerImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AnimalAndFlowerImageViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(root)
                .load(imageList[position])
                .error(R.drawable.image_error)
                .placeholder(R.drawable.image_placeholder)
                .centerCrop()
                .into(ivAnimalAndFlowerImage)
        }
    }

    override fun getItemCount() = imageList.size

    fun submitList(newList: List<String>) {
        imageList = newList
        notifyDataSetChanged()
    }
}