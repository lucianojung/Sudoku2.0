package de.Luciano.Sudoku;


import javax.swing.JOptionPane;

public class Game {
	private Box[][] boxes;
	private static int rerun;

	public Game(Box[][] boxes, int difficulty) {
		this.setBoxes(boxes);
		// fillSudoku then Fix like difficult then delete the not Fix Ones
		if (fillSudoku((int) (Math.random() * 9 + 1))) {
			setFixPoints(difficulty);
			deleteNotFixCells();
		} else {
			JOptionPane.showMessageDialog(null, "Cant create Sudoku! \nTry again!");
		}
	}

	public boolean fillSudoku(int number) {
		rerun = 0;
		return fillSudoku(0, number);
	}
	
	// backtracking Sudoku fill
	public boolean fillSudoku(int cell, int number) {
		// so the algorithm can over-flow the inserted numbers
		int i, j;
		cell--;
		do {
			cell++;
			i = (cell % 9);
			j = (int) (cell / 9);
		} while (boxes[i][j].isDontChange() && cell < 80);
		// if backtracking takes to long
		rerun++;
		// count numbers of iteration
		int countNumbers = 0;
		// while there is still a possible answer
		while (countNumbers < 9 && rerun < 150000) {
			// calculate
			countNumbers++;
			if (number > 9) {
				number %= 9;
			}
			// set number
			getBoxes()[i][j].setNumber(number, true);
			// System.out.println("cell: " + cell + " /number: " + number + " /countNumber:
			// " + countNumbers); //proof if everything is right
			// if this number works
			if (proofSudoku(cell)) { // -> set the number in the array if true
				// if Sudoku is full
				if (cell == 80)
					return true;
				// else do rekursion
				else {
					if (fillSudoku(cell + 1, (int) (Math.random() * 9 + 1)))
						return true;
					// ...or get next number and delete the actual one
					else {
						// go back to last step
						getBoxes()[i][j].setNumber(0);
					}
				}
			} else {
				// else number is 0 again
				getBoxes()[i][j].setNumber(0);
			}
			number++;
		}
		return false;
	}

	// fit the number in? && public because it doesnt change anything
	public boolean proofSudoku(int cell) {
		int i = (cell % 9);
		int j = (int) (cell / 9);
		// horizontal proof for actual box
		for (int k = 0; k < boxes.length; k++) {
			if (boxes[k][j].getNumber() == boxes[i][j].getNumber() && k != i) {
				return false;
			}
		}
		// vertical proof of actual box
		for (int k = 0; k < boxes.length; k++) {
			if (boxes[i][k].getNumber() == boxes[i][j].getNumber() && k != j) {
				return false;
			}
		}
		// proof 3x3 Boxes with actual box
		for (int k = i - (i % 3); k <= i + 2 - (i % 3); k++) {
			for (int l = j - (j % 3); l <= j + 2 - (j % 3); l++) {
				if (boxes[k][l].getNumber() == boxes[i][j].getNumber() && (k != i || l != j)) {
					return false;
				}
			}
		}
		// else
		return true;
	}

	// contains the fix cells
	private void setFixPoints(int fixNumbers) {
		int[] fixCells = new int[fixNumbers];
		int newFixCell = 0;
		for (int i = 0; i < fixNumbers; i++) {
			boolean notInArray;
			do {
				notInArray = true;
				newFixCell = ((int) (Math.random() * 81));
				for (int j : fixCells)
					if (j == newFixCell)
						notInArray = false;
			} while (!notInArray);
			fixCells[i] = newFixCell;
			// set the cell fix
			int k = (fixCells[i] % 9);
			int l = (int) (fixCells[i] / 9);
			boxes[k][l].setFix(true);
		}
	}

	// if its not fix delete it, so noone can see the answer
	private void deleteNotFixCells() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[0].length; j++) {
				if (!boxes[i][j].isFix())
					boxes[i][j].setNumber(0);
			}
		}
	}

	// delete the cells that are not fix and must be filled
	public void deleteChangeableCells() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[0].length; j++) {
				if (!boxes[i][j].isDontChange())
					boxes[i][j].setNumber(0);
			}
		}

	}

	public Box[][] getBoxes() {
		return boxes;
	}

	public void setBoxes(Box[][] boxes) {
		this.boxes = boxes;
	}

	public static int getRerun() {
		return rerun;
	}

	public static void setRerun(int rerun) {
		Game.rerun = rerun;
	}
}
