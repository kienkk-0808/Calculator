package com.kienkk.calculator

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var num1 = 0.0
    private var num2 = 0.0
    private var operator = ""
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
    }

    fun onNumberClick(view: View) {
        if (isNewInput) {
            display.text = ""
            isNewInput = false
        }
        val number = (view as Button).text.toString()
        display.append(number)
    }

    fun onNumberDelete(view: View) {
        isNewInput = false
        var temp = display.text.toString().dropLast(1)
        display.text = temp
    }

    fun onRotaleClick(view: View) {
        val currentOrientation = resources.configuration.orientation

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
    }

    fun onOperatorClick(view: View) {
        operator = (view as Button).text.toString()
        try {
            num1 = display.text.toString().toDouble()
            isNewInput = true
        }catch (e : Exception){
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }

    }

    fun onPercentClick(view: View){
        try {
            num2 = display.text.toString().toDouble() / 100
            display.text = num2.toString()
            isNewInput = false
        }catch (e : Exception){
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    fun onEqualClick(view: View) {
        try {
            num2 = display.text.toString().toDouble()
        }catch (e:Exception){
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            return
        }

        var result = 0.0
        when (operator) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "x" -> result = num1 * num2
            "/" -> if (num2 != 0.0) result = num1 / num2 else Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            else -> result = num2
        }
        operator = ""
        display.text = result.toString()
        isNewInput = true
    }

    fun onClearClick(view: View) {
        display.text = ""
        isNewInput = true
        num1 = 0.0
        num2 = 0.0
        operator = ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("isNewInput", isNewInput)
        outState.putString("operator", operator)
        outState.putString("display", display.text.toString())
        outState.putDouble("num1",num1)
        outState.putDouble("num2",num2)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isNewInput = savedInstanceState.getBoolean("isNewInput", true)
        operator = savedInstanceState.getString("operator","")
        display.text = savedInstanceState.getString("display","0")
        num1 = savedInstanceState.getDouble("num1",0.0)
        num2 = savedInstanceState.getDouble("num2",0.0)
    }
}
