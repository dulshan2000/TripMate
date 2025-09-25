
package com.tripmate.ui.auth
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tripmate.databinding.ActivityRegisterBinding
import com.tripmate.ui.MainActivity
class RegisterActivity: AppCompatActivity(){
    private lateinit var b: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater); setContentView(b.root)
        b.btnRegister.setOnClickListener{ startActivity(Intent(this, MainActivity::class.java)); finish() }
    }
}
