package woowacourse.movie.movieList.cinemaListDialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.movieDetail.MovieDetailActivity
import woowacourse.movie.movieList.THEATER_KEY
import java.time.LocalTime

class TheatersBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetTheatersBinding? = null
    private val binding get() = _binding ?: error("error")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetTheatersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theater = arguments?.let {
            BundleCompat.getSerializable(it, THEATER_KEY, Theater::class.java)
        } ?: error("")
        val adapter = CinemaAdapter {
            navigateToMovieDetail(it)
        }
        binding.rvCinema.adapter = adapter
        adapter.submitList(
            listOf(
                Cinema(
                    "CGV",
                    theater.copy(
                        times = listOf(LocalTime.of(9, 0), LocalTime.of(11, 0), LocalTime.of(15, 0))
                    ),
                ),
                Cinema(
                    "롯데시네마", theater.copy(
                        times = listOf(
                            LocalTime.of(13, 0),
                            LocalTime.of(15, 0),
                            LocalTime.of(17, 0),
                            LocalTime.of(19, 0),
                        )
                    )
                ),
                Cinema(
                    "메가 박스", theater.copy(
                        times = listOf(
                            LocalTime.of(20, 0),
                            LocalTime.of(22, 0),
                            LocalTime.of(23, 30),
                        )
                    )
                ),
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToMovieDetail(cinema: Cinema) {
        requireActivity().apply {
            val intent =
                Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra("Cinema", cinema)
                }
            dismiss()
            startActivity(intent)
        }
    }
}
