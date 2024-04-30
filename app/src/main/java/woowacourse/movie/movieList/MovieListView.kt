import woowacourse.movie.model.MovieDisplayData
import woowacourse.movie.model.theater.Theater

interface MovieListView {
    fun showToast(message: String)

    fun updateAdapter(displayData: List<MovieDisplayData>)

    fun showBottomSheet(theater: Theater)
}
