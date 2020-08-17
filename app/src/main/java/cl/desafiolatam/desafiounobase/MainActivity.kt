package cl.desafiolatam.desafiounobase

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val filename ="cl.desafiolatam.desafiounobase.MainActivity"
    public val tag = "MainActivity"
    lateinit var container: ConstraintLayout
    lateinit var preferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences(filename, Context.MODE_PRIVATE)
        setUpListeners()
    }

    private fun setUpListeners() {
        login_button.setOnClickListener {
            if (name_input.text!!.isNotEmpty()) {
                val intent: Intent
                if (hasSeenWelcome()) {
                    intent = Intent(this, HomeActivity::class.java)
                } else {
                    saveUser(name_input.text.toString())
                    intent = Intent(this, WelcomeActivity::class.java)
                }
                startActivity(intent)
            } else {
                Snackbar.make(container, "El nombre no puede estar vacío", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasSeenWelcome(): Boolean {
        val stringSet = preferences.getStringSet("Usuarios", setOf())
        Log.d(tag, preferences.all.toString())
         return stringSet.contains(name_input.text.toString())
        //implementar este método para saber si el usuario ya ha entrado a la aplicación y ha visto
        //la pantalla de bienvenida. Este método permite decidir que pantalla se muestra después de presionar Ingresar
        //recorra la lista de usuarios

    }
    private fun saveUser(userName : String){
        val setString = preferences.getStringSet("Usuarios", mutableSetOf())
        setString.add(userName)
        preferences.edit().putStringSet("Usuarios", setString).apply()
        Log.d(tag, preferences.all.toString())
    }
}
