package com.github.andrewzolen

enum class Cell{
    EMPTY,
    X,
    O,
}

class Game {
    private val _board : MutableList<Cell> = MutableList(9){Cell.EMPTY}
    val board: List<Cell>
        get() = _board
    val currentPlayer: Cell
        get() {
            return if (board.count{it != Cell.EMPTY} % 2 == 0)
                Cell.X
            else
                Cell.O
        }
    val isGameOver: Boolean
        get() {
            return checkWinner() != null || board.all {it != Cell.EMPTY}
        }
    val isDraw: Boolean
        get() {return isGameOver && checkWinner() == null }


    fun makeMove(index: Int) : Boolean{
        if (isGameOver) return false
        if (index  !in board.indices)
            return false
        if (board[index] != Cell.EMPTY)
            return false
        _board[index] = currentPlayer
        return true
    }

    fun checkWinner() : Cell?{
        val winningLines = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(6, 4, 2)
        )
        for (line in winningLines) {
            val symbol = board[line[0]]
            if (symbol != Cell.EMPTY && line.all { index -> board[index] == symbol })
                return symbol
        }

        return null
    }

    fun reset(){
        _board.fill(Cell.EMPTY)
    }
}