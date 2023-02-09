package uz.infinity.app.presentation.ui.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.infinity.app.data.mapper.getMusicDataByPosition
import uz.infinity.app.data.model.MusicData
import uz.infinity.app.databinding.ItemMusicBinding

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    var cursor: Cursor? = null
    private var selectMusicPositionListener: ((Int) -> Unit)? = null

    inner class MusicViewHolder(private val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                selectMusicPositionListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind() {
            cursor?.getMusicDataByPosition(absoluteAdapterPosition)?.let {
                binding.textMusicName.text = it.title
                binding.textArtistName.text = it.artist
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MusicViewHolder(ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
holder.bind()
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    fun selectMusicPositionListener(block: (Int) -> Unit) {
        selectMusicPositionListener = block
    }
}