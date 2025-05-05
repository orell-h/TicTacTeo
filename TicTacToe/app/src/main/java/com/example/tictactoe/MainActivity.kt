package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    enum class Turn {
        NOUGHT,
        CROSS
    }

    private var currentTurn = Turn.CROSS
    private var boardList = mutableListOf<Button>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()
        setTurnLabel()

        binding.resetButton.setOnClickListener {
            resetBoard()
        }
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if (view !is Button) return
        if (view.text != "") return

        addToBoard(view)

        if (checkForVictory(NOUGHT)) {
            result("המנצח הוא O")
        } else if (checkForVictory(CROSS)) {
            result("המנצח הוא X")
        } else if (fullBoard()) {
            result("תיקו!")
        }
    }

    private fun addToBoard(button: Button) {
        if (currentTurn == Turn.NOUGHT) {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        } else {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        val text = when (currentTurn) {
            Turn.CROSS -> "התור של X"
            Turn.NOUGHT -> "התור של O"
        }
        binding.turnTV.text = text
    }

    private fun checkForVictory(symbol: String): Boolean {
        return (match(binding.a1, symbol) && match(binding.a2, symbol) && match(binding.a3, symbol)) ||
                (match(binding.b1, symbol) && match(binding.b2, symbol) && match(binding.b3, symbol)) ||
                (match(binding.c1, symbol) && match(binding.c2, symbol) && match(binding.c3, symbol)) ||
                (match(binding.a1, symbol) && match(binding.b1, symbol) && match(binding.c1, symbol)) ||
                (match(binding.a2, symbol) && match(binding.b2, symbol) && match(binding.c2, symbol)) ||
                (match(binding.a3, symbol) && match(binding.b3, symbol) && match(binding.c3, symbol)) ||
                (match(binding.a1, symbol) && match(binding.b2, symbol) && match(binding.c3, symbol)) ||
                (match(binding.a3, symbol) && match(binding.b2, symbol) && match(binding.c1, symbol))
    }

    private fun match(button: Button, symbol: String): Boolean {
        return button.text == symbol
    }

    private fun fullBoard(): Boolean {
        return boardList.all { it.text != "" }
    }

    private fun result(text: String) {
        binding.turnTV.text = text
        disableBoard()
    }

    private fun disableBoard() {
        for (button in boardList) {
            button.isEnabled = false
        }
    }

    private fun resetBoard() {
        for (button in boardList) {
            button.text = ""
            button.isEnabled = true
        }
        currentTurn = Turn.CROSS
        setTurnLabel()
    }

    companion object {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}