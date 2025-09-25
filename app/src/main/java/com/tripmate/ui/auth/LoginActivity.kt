
package com.tripmate.ui.auth
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tripmate.databinding.ActivityLoginBinding
import com.tripmate.ui.MainActivity
class LoginActivity: AppCompatActivity(){
    private lateinit var b: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater); setContentView(b.root)
        b.btnLogin.setOnClickListener{ startActivity(Intent(this, MainActivity::class.java)) }
        b.btnToRegister.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)) }
    }
}
