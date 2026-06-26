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

}
