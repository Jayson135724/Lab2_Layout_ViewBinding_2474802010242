package vn.edu.vlu.lab2

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.vlu.lab2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        setupUI()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener { handleLogin() }
        binding.cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            binding.edtPassword.transformationMethod =
                if (isChecked) null                                  // hiện ký tự
                else PasswordTransformationMethod.getInstance()
            binding.edtPassword.setSelection(binding.edtPassword.text.length) // giữ con trỏ cuối
        }
        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, R.string.msg_forgot_password, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        // Xóa thông báo lỗi của lần bấm trước
        binding.edtEmail.error = null
        binding.edtPassword.error = null

        when {
            email.isEmpty() || password.isEmpty() -> {
                Toast.makeText(this, R.string.msg_missing_info, Toast.LENGTH_SHORT).show()
                binding.tvStatus.setText(R.string.status_missing)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                binding.edtEmail.error = getString(R.string.err_email_invalid)
            password.length < 6 ->
                binding.edtPassword.error = getString(R.string.err_password_short)
            else -> {
                // Xác thực GIẢ LẬP: chưa gọi máy chủ. Xác thực thật ở các buổi sau.
                binding.tvStatus.text = getString(R.string.status_login_ok, email)
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.EXTRA_EMAIL, email)
                startActivity(intent)
            }
        }
    }
}
