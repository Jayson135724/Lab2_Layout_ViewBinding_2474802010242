package vn.edu.vlu.lab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.vlu.lab2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Giữ padding 24dp của layout và cộng thêm phần thanh hệ thống/bàn phím
        val basePadding = binding.root.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(
                basePadding + bars.left, basePadding + bars.top,
                basePadding + bars.right, basePadding + bars.bottom
            )
            insets
        }

        binding.tvValueEmail.text = intent.getStringExtra(EXTRA_EMAIL).orEmpty()
        binding.btnLogout.setOnClickListener { finish() }   // quay lại màn hình Login
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}
