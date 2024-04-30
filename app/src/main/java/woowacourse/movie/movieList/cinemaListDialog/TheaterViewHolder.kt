package woowacourse.movie.movieList.cinemaListDialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BottomSheetTheatersItemBinding
import woowacourse.movie.model.Cinema

class TheaterViewHolder(
    private val binding: BottomSheetTheatersItemBinding,
    private val onCinemaClicked: (Cinema) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cinema) {
        val (title, theater) = item
        binding.tvMovieTitle.text = title
        binding.tvMovieDescription.text = "${theater.times.size}"
        binding.root.setOnClickListener {
            onCinemaClicked(item)
        }
    }
}