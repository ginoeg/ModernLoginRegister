package com.amier.modernloginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amier.modernloginregister.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()
        binding.button2.setOnClickListener {
            loginUser()
        }
        btnRegLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

    private fun loginUser() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            user.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(
                            Intent(
                                this,
                                HomeActivity::class.java

                            )
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this, task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }

    }
}



// /override fun onCreate(savedInstanceState: Bundle?) {//
// super.onCreate(savedInstanceState)
// setContentView(R.layout.activity_login)
// btnRegLogin.setOnClickListener {
// startActivity(Intent(this,RegisterActivity::class.java))
// overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
// }
// setup()
// /*/}
//
// private fun setup() {
// button2.setOnClickListener {
// if (emailET.text!!.isNotEmpty() && passwordET.text!!.isNotEmpty()) {
// FirebaseAuth.getInstance().signInWithEmailAndPassword(
// emailET.text.toString(),
// passwordET.text.toString()
// ).addOnCompleteListener {
// if (it.isSuccessful) {
// showMain(it.result?.user?.email ?:"", ProviderType.BASIC)
// } else {
// showAlert()
// }
// }
//
//
// }
// }
//
// }
// private fun showAlert() {
// val builder = AlertDialog.Builder(this)
// builder.setTitle("Error")
// builder.setMessage("Se ha producido un error ingresarr al usuario")
// builder.setPositiveButton("Aceptar", null)
// val dialog: AlertDialog = builder.create()
// dialog.show()
// }
// private fun showMain(email: String, provider: ProviderType){
//
// val mainIntent = Intent(this, MainActivity::class.java).apply {
// putExtra("email", email)
// putExtra("provider" , provider.name)
// }
// startActivity(mainIntent)
// }