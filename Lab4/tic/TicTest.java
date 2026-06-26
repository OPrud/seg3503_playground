package tic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TicTest {

	@Test
	void test1BoardEquality() {
		Tic board = new Tic(3, 3);
		Tic board2 = new Tic(3,3);
		assertEquals(board, board2);
	}
	
	@Test
    void test2InitialTurn() {

        Tic board = new Tic(3,3);

        assertEquals("X", board.getTurn());
    }

    @Test
    void test3PlayPlacesMark() {

        Tic board = new Tic(3,3);

        board.play(1,1);

        assertEquals("X", board.getCell(1,1));
    }

	@Test
	void test4OutOfBoundsMoveIgnored() {

		Tic board = new Tic(3,3);

		board.play(5,5);

		assertEquals("_", board.getCell(0,0));
		assertEquals("_", board.getCell(1,1));
	}

	@Test
	void test5WinDetection() {

		Tic board = new Tic(3,3);

		board.play(0,0); // X
		board.play(1,0); // O
		board.play(0,1); // X
		board.play(1,1); // O
		board.play(0,2); // X gagne ligne du haut

		assertTrue(board.hasWinner());
	}
	@Test
	void test6VerticalWin() {
		Tic board = new Tic(3,3);

		board.play(0,0); // X
		board.play(0,1); // O
		board.play(1,0); // X
		board.play(1,1); // O
		board.play(2,0); // X wins vertically

		assertTrue(board.hasWinner());
	}
	@Test
	void test7DiagonalWin() {
		Tic board = new Tic(3,3);

		board.play(0,0); // X
		board.play(0,1); // O
		board.play(1,1); // X
		board.play(0,2); // O
		board.play(2,2); // X wins diagonal

		assertTrue(board.hasWinner());
	}
	@Test
	void test8AntiDiagonalWin() {
		Tic board = new Tic(3,3);

		board.play(2,0); // X
		board.play(0,0); // O
		board.play(1,1); // X
		board.play(0,1); // O
		board.play(0,2); // X wins anti-diagonal

		assertTrue(board.hasWinner());
	}
	@Test
	void test9CannotOverwriteCell() {
		Tic board = new Tic(3,3);

		board.play(0,0); // X

		board.play(0,0); // O tries to play on occupied cell

		assertEquals("X", board.getCell(0,0));
		assertEquals("O", board.getTurn()); // Turn should not change after invalid move
	}
	@Test
	void test10NoMoveAfterWinner() {
		Tic board = new Tic(3,3);

		board.play(0,0); // X
		board.play(1,0); // O
		board.play(0,1); // X
		board.play(1,1); // O
		board.play(0,2); // X wins

		board.play(2,2); // should be ignored

		assertEquals("_", board.getCell(2,2));
	}
}
