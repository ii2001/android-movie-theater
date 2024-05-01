package woowacourse.movie

import MovieAdapter
import MovieListView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.common.ui.withArgs
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.model.MovieDisplayData
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.movieList.MovieListPresenter
import woowacourse.movie.movieList.THEATER_KEY
import woowacourse.movie.movieList.cinemaListDialog.TheatersBottomSheetFragment

class MovieListFragment : Fragment(), MovieListView {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.null_point_error))
    private var _presenter: MovieListPresenter? = null
    private val presenter get() = _presenter ?: error(getString(R.string.null_point_error))
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initPresenter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter(displayData: List<MovieDisplayData>) {
        adapter.updateItems(displayData)
    }

    override fun showBottomSheet(theater: Theater) {
        val bottomSheet = TheatersBottomSheetFragment().withArgs {
            putSerializable(THEATER_KEY, theater)
        }
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    private fun initViews() {
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            MovieAdapter(requireContext(), mutableListOf()) { position ->
                presenter.onDetailButtonClicked(position)
            }
        binding.rvMovies.adapter = adapter
    }

    private fun initPresenter() {
        _presenter = MovieListPresenter(this)
        presenter.loadMovies()
    }
}