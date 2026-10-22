package com.github.andrewzolen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.andrewzolen.databinding.ActivityMainBinding
import com.github.andrewzolen.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private val game = Game()
    private lateinit var gameMode : GameMode // инициализируем позже в зависимости от выбора режима
    private lateinit var binding : FragmentGameBinding
    private lateinit var buttonList : List<Button>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        buttonList = listOf(
            binding.cell00, binding.cell01, binding.cell02,
            binding.cell10, binding.cell11, binding.cell12,
            binding.cell20, binding.cell21, binding.cell22
        )
        if (humanSymbol == Cell.O && gameMode == GameMode.HUMAN_VS_COMPUTER){
            makeComputerMove()
        }
        render()
        binding.clearButton.setOnClickListener {
            game.reset()
            if (humanSymbol == Cell.O && gameMode == GameMode.HUMAN_VS_COMPUTER)
                makeComputerMove()
            render()
        }

        buttonList.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleCellClick(index)
            }
        }


        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    private fun makeComputerMove(){
        val botIndex = bot.chooseMove(board = game.board)
        if (botIndex != null){
            game.makeMove(botIndex)
        }
    }
    private fun handleCellClick(index: Int){
        val isMoveSuccessful = game.makeMove(index)

        if (isMoveSuccessful && gameMode == GameMode.HUMAN_VS_COMPUTER && !game.isGameOver){
            makeComputerMove()
        }
        render()
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