package woowacourse.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.common.ui.replaceTo
import woowacourse.movie.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            binding.botNavMain.selectedItemId = R.id.movieListFragment
            replaceTo<MovieListFragment>(R.id.fc_main)
        }
        initClickListener()
    }

    private fun initClickListener() {
        binding.botNavMain.setOnItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.reserveFragment -> replaceTo<ReservationFragment>(R.id.fc_main)
            R.id.movieListFragment -> replaceTo<MovieListFragment>(R.id.fc_main)
            R.id.settingFragment -> replaceTo<SettingFragment>(R.id.fc_main)
            else -> error("Invalid itemId: $itemId")
        }
        return true
    }
}
