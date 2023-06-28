package com.example.calculator2

import com.example.calculator2.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumber = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onAllClearClick(view: View) {
//        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumber = false
        binding.resultTv.visibility = View.GONE
    }

//    fun onEqualTo(view: View) {
//        if (stateError)
//            binding.dataTv.text = binding.resultTv.text.toString().drop(1)
//
//    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.dataTv.text = (view as Button).text as Editable?
            stateError = false
        } else {
            binding.dataTv.append((view as Button).text)
        }
        lastNumber = true
        onEqual()
    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumber) {
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumber = false
            onEqual()
        }
    }

    fun onBackButton(view: View) {
//        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()
            if (lastChar.isDigit()) {
                onEqual()
            }
        } catch (e: Exception) {
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }

    fun onClearClick(view: View) {
//        binding.dataTv.text = ""
        lastNumber = false
    }

    fun onEqual() {
        if (lastNumber && !stateError) {
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.resultTv.text = "= $result"
                binding.resultTv.visibility = View.VISIBLE
            } catch (ex: ArithmeticException) {
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumber = false
            }
        }
    }
}
