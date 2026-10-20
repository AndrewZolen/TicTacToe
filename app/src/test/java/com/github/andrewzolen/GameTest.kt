package com.github.andrewzolen

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameTest {
    @Test
    fun newGameHasEmptyBoard() {
        val game = Game()
        assertEquals(9, game.board.size)
        assertTrue(game.board.all { it == Cell.EMPTY })
    }
    @Test
    fun makeFirstMove() {
        val game = Game()
        val result = game.makeMove(0)
        assertTrue(result)
        assertEquals(Cell.X, game.board[0])
    }
    @Test
    fun occupiedCellCannotBeChanged() {
        val game = Game()
        val firstMove = game.makeMove(0)
        assertTrue(firstMove)
        val result = game.makeMove(0)
        assertFalse(result)
        assertEquals(Cell.X, game.board[0])
    }
    @Test
    fun playersAlternateTurns() {
        val game = Game()
        val firstMove = game.makeMove(0)
        assertTrue(firstMove)
        assertEquals(Cell.X, game.board[0])
        val secondMove = game.makeMove(1)
        assertTrue(secondMove)
        assertEquals(Cell.O, game.board[1])
    }
    @Test
    fun incorrectIndices() {
        val game = Game()
        val incorrectMove1 = game.makeMove(-1)
        assertFalse(incorrectMove1)
        assertTrue(game.board.all { it == Cell.EMPTY })
        val incorrectMove2 = game.makeMove(9)
        assertFalse(incorrectMove2)
        assertTrue(game.board.all { it == Cell.EMPTY })
        val firstMove = game.makeMove(0)
        assertTrue(firstMove)
        assertEquals(Cell.X, game.board[0])
    }
    @Test
    fun winnerX() {
        val game = Game()
        game.makeMove(0)
        game.makeMove(4)
        game.makeMove(1)
        game.makeMove(5)
        game.makeMove(2)
        assertEquals(Cell.X,game.checkWinner())
        assertTrue(game.isGameOver)
    }
}