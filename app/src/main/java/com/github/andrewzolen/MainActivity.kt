package com.github.andrewzolen

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.andrewzolen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var isXTurn = true
    private var isGameOver = false

    private lateinit var buttonList : List<Button>

    private var moveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonList = listOf(
            binding.cell00, binding.cell01, binding.cell02,
            binding.cell10, binding.cell11, binding.cell12,
            binding.cell20, binding.cell21, binding.cell22
        )

        buttonList.forEach { button ->
            button.setOnClickListener {
                makeMove(button)
            }
        }


        binding.clearButton.setOnClickListener {
            clearTable()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
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
                binding.turnText.text = "Победил $winner"
                isGameOver = true
                return
            }
        }
        if (moveCount == 9) {
            binding.turnText.text = "Ничья"
            isGameOver = true
            return
        }
        isXTurn = !isXTurn
        binding.turnText.text = "Ход: ${if (isXTurn) "X" else "O"}"

    }

    private fun clearTable() {
        buttonList.forEach { it.text = "" }
        isGameOver = false
        isXTurn = true
        moveCount = 0
        binding.turnText.text= "Ход: X"

    }

    private fun checkWinner(): String? {
        val winningLines = listOf(
            listOf(binding.cell00, binding.cell01, binding.cell02),
            listOf(binding.cell10, binding.cell11, binding.cell12),
            listOf(binding.cell20, binding.cell21, binding.cell22),
            listOf(binding.cell00, binding.cell10, binding.cell20),
            listOf(binding.cell01, binding.cell11, binding.cell21),
            listOf(binding.cell02, binding.cell12, binding.cell22),
            listOf(binding.cell00, binding.cell11, binding.cell22),
            listOf(binding.cell02, binding.cell11, binding.cell20)
        )
        for (line in winningLines) {
            val symbol = line[0].text.toString()

            if (symbol.isNotEmpty() && line.all { button -> button.text.toString() == symbol })
                return symbol
        }

        return null
    }

}