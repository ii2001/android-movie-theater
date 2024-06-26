package woowacourse.movie

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class PurchaseConfirmationActivityTest {
    private val movie =
        MovieInfo(
            Title("차람과 하디의 진지한 여행기"),
            MovieDate(LocalDate.of(2024, 2, 25)),
            RunningTime(230),
            Synopsis("wow!"),
        )
    private val seats =
        mapOf(
            "B1" to Seat('B', 1, "B"),
            "C1" to Seat('S', 1, "B"),
            "E1" to Seat('A', 1, "B"),
        )
    private val theater = Theater(movie, seats)
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            PurchaseConfirmationActivity::class.java,
        ).also {
            it.putExtra("Theater", theater)
            it.putExtra("ticketPrice", "Total Price: 30000")
        }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<PurchaseConfirmationActivity>(intent)

    @Test
    fun `영화제목표시_영화제목정확히표시되는지검증`() {
        Espresso.onView(withId(R.id.movie_title_confirmation))
            .check(matches(ViewMatchers.withText(movie.title.toString())))
    }

    @Test
    fun `상영시간표시_상영시간정확히표시되는지검증`() {
        Espresso.onView(withId(R.id.purchase_movie_running_time))
            .check(matches(ViewMatchers.withText(movie.runningTime.toString())))
    }

    @Test
    fun `티켓_가격_표시되는지검증`() {
        Espresso.onView(withId(R.id.ticket_charge))
            .check(matches(ViewMatchers.withText("Total Price: 30000")))
    }
}
