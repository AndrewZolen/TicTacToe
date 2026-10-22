package com.github.andrewzolen

class Computer {
    fun chooseMove(board: List<Cell>): Int? {
        val list = board.indices.filter { board[it] == Cell.EMPTY }
        return list.randomOrNull()
    }
}