package woowacourse.movie.seat

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityTheaterSeatBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity

@SuppressLint("DiscouragedApi")
class TheaterSeatActivity : AppCompatActivity(), TheaterSeatContract.View {
    private lateinit var presenter: TheaterSeatPresenter
    private val binding: ActivityTheaterSeatBinding by lazy {
        ActivityTheaterSeatBinding.inflate(
            layoutInflater,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        initializePresenter()
        setupSeats()

        binding.confirmButton.setOnClickListener {
            confirmTicketPurchase()
        }
    }

    override fun updateSeatDisplay(seat: Seat) {
        val buttonId = resources.getIdentifier("${seat.row}${seat.number}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        val color = if (seat.chosen) Color.RED else Color.WHITE
        button.setBackgroundColor(color)
    }

    override fun showConfirmationDialog(
        title: String,
        message: String,
        positiveLabel: String,
        onPositiveButtonClicked: () -> Unit,
        negativeLabel: String,
        onNegativeButtonClicked: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(positiveLabel) { _, _ -> onPositiveButtonClicked() }
        builder.setNegativeButton(negativeLabel) { dialog, _ ->
            onNegativeButtonClicked()
            dialog.dismiss()
        }
        builder.show()
    }

    override fun setSeatBackground(
        seatId: String,
        color: String,
    ) {
        val buttonId = resources.getIdentifier(seatId, "id", packageName)
        val button = findViewById<Button>(buttonId)
        val colorInt = Color.parseColor(color)
        button.setBackgroundColor(colorInt)
    }

    override fun updateTotalPrice(price: Int) {
        binding.invalidateAll()
    }

    override fun navigateToNextPage(intent: Intent) {
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun initializePresenter() {
        val intent = intent
        val ticketNum = intent.getStringExtra("ticketNum")?.toInt() ?: 0
        IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java)?.let { cinema ->
            presenter =
                TheaterSeatPresenter(this, ticketNum, cinema).also { presenter ->
                    binding.presenter = presenter
                }
        }
    }

    private fun setupSeats() {
        binding.seatTable.children.filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<Button>()
                    .forEach { button ->
                        button.setOnClickListener {
                            presenter.toggleSeatSelection(button.text.toString())
                        }
                    }
            }
    }

    private fun confirmTicketPurchase() {
        showConfirmationDialog(
            title = "예매 확인",
            message = "정말 예매하시겠습니까?",
            positiveLabel = "예매 완료",
            onPositiveButtonClicked = {
                val cinema = IntentCompat.getSerializableExtra(intent, "Cinema", Cinema::class.java)
                val ticketPrice = findViewById<TextView>(R.id.total_price).text
                if (cinema != null) {
                    val intent =
                        Intent(this, PurchaseConfirmationActivity::class.java).apply {
                            putExtra("ticketPrice", ticketPrice.toString())
                            putExtra("seatNumber", presenter.selectedSeats.toTypedArray())
                            putExtra("Cinema", cinema)
                            putExtra("timeDate", intent.getStringExtra("timeDate"))
                        }
                    navigateToNextPage(intent)
                } else {
                    Toast.makeText(this, "Cinema data is not available.", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            negativeLabel = "취소",
            onNegativeButtonClicked = {},
        )
    }
}
