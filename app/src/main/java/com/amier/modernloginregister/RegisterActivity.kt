package com.amier.modernloginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.inject.Provider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnLogRegister.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
        }
        setup()
    }

    private fun setup() {
        button.setOnClickListener {
            if (emailET.text!!.isNotEmpty() && passwordET.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailET.text.toString(),
                    passwordET.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showMain(it.result?.user?.email?:"", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showMain(email: String, provider: ProviderType){

        val mainIntent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider" , provider.name)
        }
        startActivity(mainIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right)
    }
}