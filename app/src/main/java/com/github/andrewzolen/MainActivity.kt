package com.github.andrewzolen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var isXTurn = true
    private var isGameOver = false

    private lateinit var cell00: Button
    private lateinit var cell01: Button
    private lateinit var cell02: Button

    private lateinit var cell10: Button
    private lateinit var cell11: Button
    private lateinit var cell12: Button

    private lateinit var cell20: Button
    private lateinit var cell21: Button
    private lateinit var cell22: Button

    private lateinit var buttonList : List<Button>

    private lateinit var clearButton: Button
    private lateinit var turnNext: TextView
    private var moveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        cell00 = findViewById(R.id.cell00)
        cell01 = findViewById(R.id.cell01)
        cell02 = findViewById(R.id.cell02)

        cell10 = findViewById(R.id.cell10)
        cell11 = findViewById(R.id.cell11)
        cell12 = findViewById(R.id.cell12)

        cell20 = findViewById(R.id.cell20)
        cell21 = findViewById(R.id.cell21)
        cell22 = findViewById(R.id.cell22)



        clearButton = findViewById(R.id.clearButton)
        turnNext = findViewById(R.id.turnText)

        buttonList = listOf(
            cell00, cell01, cell02,
            cell10, cell11, cell12,
            cell20, cell21, cell22
        )

        buttonList.forEach { button ->
            button.setOnClickListener {
                makeMove(button)
            }
        }


        clearButton.setOnClickListener {
            clearTable()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun makeMove(button: Button) {
        if (isGameOver)
            return
        if (button.text.isNotEmpty())
            return

        val currentSymbol = if (isXTurn) "X" else "O"
        button.text = currentSymbol
        moveCount++
        if (moveCount >= 5) {
            val winner = checkWinner()
            if (winner != null) {
                turnNext.text = "Победил $winner"
                isGameOver = true
                return
            }
        }
        if (moveCount == 9) {
            turnNext.text = "Ничья"
            isGameOver = true
            return
        }
        isXTurn = !isXTurn
        turnNext.text = "Ход: ${if (isXTurn) "X" else "O"}"

    }

    private fun clearTable() {
        buttonList.forEach { it.text = "" }
        isGameOver = false
        isXTurn = true
        moveCount = 0
        turnNext.text = "Ход: X"

    }

    private fun checkWinner(): String? {
        val winningLines = listOf(
            listOf(cell00, cell01, cell02),
            listOf(cell10, cell11, cell12),
            listOf(cell20, cell21, cell22),
            listOf(cell00, cell10, cell20),
            listOf(cell01, cell11, cell21),
            listOf(cell02, cell12, cell22),
            listOf(cell00, cell11, cell22),
            listOf(cell02, cell11, cell20)
        )
        for (line in winningLines) {
            val symbol = line[0].text.toString()

            if (symbol.isNotEmpty() && line.all { button -> button.text.toString() == symbol })
                return symbol
        }

        return null
    }

}