package de.Luciano.Sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Field extends JPanel {
	
	public Field(int difficulty) {
		initComponents();
		game = new Game(boxes, difficulty);
	}

	private void initComponents() {
		// getting the panel started
		this.setSize(panelSize.x, panelSize.y);
		this.setVisible(true);
		this.setLayout(new GroupLayout(this));
		this.setBackground(Color.WHITE);
		

		// create the Boxes and put them into the array
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[0].length; j++) {
				boxes[i][j] = new Box(boxEdge, (9*j+i));
				boxes[i][j].setLocation(i * boxEdge + (i + 1) * boxGap, j * boxEdge + (j + 1) * boxGap);
				boxes[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						boxesMouseClicked(evt);
					}
				});
				this.add(boxes[i][j]);
			}
		}
	}

	protected void boxesMouseClicked(MouseEvent evt) {
		// what happen, when you click a button
		Box aktualButton = (Box) evt.getSource();

		//only not Fixed are changeable from the person
		if(!aktualButton.isFix())
			//right click for counting up
			if(!SwingUtilities.isRightMouseButton(evt)) 
				aktualButton.setNumber((aktualButton.getNumber()+1)%10, false);
			//left click for counting down
			else
				aktualButton.setNumber((aktualButton.getNumber()+9)%10, false);
			if(proofSudoku(false) && SudokuIsFull())
				JOptionPane.showMessageDialog(this, "You did it!", "Hurray!!!", -1);
	}
	
	public boolean proofSudoku(boolean feedback) {
		boolean sudokuRight = true;
		// proof all cells which are filled
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[0].length; j++) {
				boxes[i][j].setNumber(boxes[i][j].getNumber()); //for refreshing the color
				if (boxes[i][j].getNumber() != 0) {
					int cell = j * 9 + i;
					if (!game.proofSudoku(cell)) {
						if(feedback)
							boxes[i][j].setBackground(attentionRed);
						sudokuRight = false;
					}
				}
			}
		}
		return sudokuRight;
	}

	// testing with graphics
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
		// getting start with Graphics; Initialize
		paintGraphics = (Graphics2D) g;
		paintGraphics.setStroke(new BasicStroke(3));
		paintGraphics.drawRect(1, 1, panelSize.x - 3, panelSize.y - 3);
		// lines between 3 boxes
			// horizontal
			paintGraphics.drawLine(0, (int) (3 * boxEdge + 3.5 * boxGap), panelSize.x, (int) (3 * boxEdge + 3.5 * boxGap));
			paintGraphics.drawLine(0, (int) (6 * boxEdge + 6.5 * boxGap), panelSize.x, (int) (6 * boxEdge + 6.5 * boxGap));
			// vertical
			paintGraphics.drawLine((int) (3 * boxEdge + 3.5 * boxGap), 0, (int) (3 * boxEdge + 3.5 * boxGap), panelSize.y);
			paintGraphics.drawLine((int) (6 * boxEdge + 6.5 * boxGap), 0, (int) (6 * boxEdge + 6.5 * boxGap), panelSize.y);
	}

	public Game getGame() {
		return game;
	}
	
	public Box[][] getBoxes() {
		return boxes;
	}
	
	public Box getBox(int cell) {
		int i = (cell % 9);
		int j = (int) (cell / 9);
		return boxes[i][j];
	}
	
	//return true if every box is filled up
	public boolean SudokuIsFull() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[0].length; j++) {
				if (boxes[i][j].getNumber() == 0)
					return false;
			}
		}
		return true;
	}

		//Variables Declaration
		//define Objects
		Game game;
		// define panelsize
		private Point panelSize = new Point(500, 500);
		private Color attentionRed = new Color(255, 26, 26);
		//primitive data
		private final int boxEdge = (int) (panelSize.x * 7 / 71); // Edge of a box
		private final int boxGap = (int) (panelSize.x / 73); // Gap between two boxes
		//array of box elements
		private Box[][] boxes = new Box[9][9]; // twodimensional int for the boxes of the sudoku

		//buffered Image for Paint
		Graphics2D paintGraphics; // ...too
	//end Variables declaration

}
