package com.github.andrewzolen

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.andrewzolen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val game = Game()
    private lateinit var binding : ActivityMainBinding

    private lateinit var buttonList : List<Button>


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
        render()

        buttonList.forEachIndexed { index, button ->
            button.setOnClickListener {
                game.makeMove(index)
                render()
            }
        }


        binding.clearButton.setOnClickListener {
            game.reset()
            render()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun render(){
        buttonList.forEachIndexed { index, button ->
            when(game.board[index]){
                Cell.EMPTY -> button.text = ""
                Cell.X -> button.text = "X"
                Cell.O -> button.text = "O"
            }
        }
        val winner = game.checkWinner()
        if (winner != null){
            binding.turnText.text = "Победил $winner"
        } else if (game.isDraw){
            binding.turnText.text = "Ничья"
        } else {
            binding.turnText.text = "Ход: ${game.currentPlayer}"
        }
    }
}