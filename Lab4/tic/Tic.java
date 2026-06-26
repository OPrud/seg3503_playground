package tic;

public class Tic {
	String[][] board;
	int rows;
	int cols;
	String turn;
	public Tic(int row, int col) {
		board = new String[row][col];
		rows = row;
		cols = col;
		turn = "X";
		for(int i = 0; i<row; i++) {
			for(int j = 0; j<col; j++) {
				board[i][j] = "_";
			}
		}
	}

	@Override
    public boolean equals(Object obj) {

        if (obj == null) return false;

        if (!(obj instanceof Tic)) return false;

        Tic other = (Tic) obj;

        if (rows != other.rows) return false;

        if (cols != other.cols) return false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!board[i][j].equals(other.board[i][j])) return false;
            }
        }
        return true;
    }

	public String getTurn() {
        return turn;
    }

	public void play(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return;

		if (!board[row][col].equals("_")) return;

        board[row][col] = turn;

        if (turn.equals("X")) turn = "O";
        else turn = "X";
    }

	public String getCell(int row, int col) {
		return board[row][col];
	}

	public boolean hasWinner() {

		// Victoire sur une ligne
		for (int i = 0; i < rows; i++) {
			if (!board[i][0].equals("_") &&
				board[i][0].equals(board[i][1]) &&
				board[i][1].equals(board[i][2])) {
				return true;
			}
		}
		//Victoire sur une colonne (verticale)
		for (int j = 0; j < cols; j++) {
			if (!board[0][j].equals("_")
				&& board[0][j].equals(board[1][j])
				&& board[1][j].equals(board[2][j])) {
				return true;
			}
		}
		//Victoire diagonale
		if (!board[0][0].equals("_")
			&& board[0][0].equals(board[1][1])
			&& board[1][1].equals(board[2][2])) {
			return true;
		}
		// other diagonal win
		if (!board[0][2].equals("_")
			&& board[0][2].equals(board[1][1])
			&& board[1][1].equals(board[2][0])) {
			return true;
		}

		return false;
	}
}
