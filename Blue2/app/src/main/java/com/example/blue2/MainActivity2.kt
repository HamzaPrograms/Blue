package com.example.blue2
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //back button
        val backbutton = findViewById<ImageButton>(R.id.imageButton3)
        backbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        //Termux Code-----------------------------------------------------------------------------------

        //Launch app with button
        val launchIntent = packageManager.getLaunchIntentForPackage("com.termux")
        val launchButton = findViewById<Button>(R.id.buttonScan)
        launchButton.setOnClickListener {
            val serviceIntent = Intent()
            serviceIntent.setClassName("com.termux", "com.termux.app.RunCommandService")
            serviceIntent.setAction("com.termux.RUN_COMMAND")
            serviceIntent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/bash")
            serviceIntent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home")
            serviceIntent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", arrayOf("-c", "nmap -A 10.0.0.0/24; exec bash"))
            serviceIntent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false) // for some reason true doesnt work
            serviceIntent.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0")


            try {
                startService(serviceIntent)
                Toast.makeText(this, "Command sent to Termux", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to execute command: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

}