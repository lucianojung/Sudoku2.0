package de.Luciano.Sudoku;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class Box extends JButton {

	public Box(int edge, int cell) {
		colored = false;
		this.setFont(biggerFont);
		this.setText("");
		this.setSize(edge, edge);
		this.setHasCell(cell);
		this.setBackground(white);
		this.setFocusable(false);
	}

	public boolean isFix() {
		return isFix;
	}

	public void setFix(boolean isFix) {
		// set Fix and en- or disable the button
		this.isFix = isFix;
		this.setDontChange(true);
		this.setColored();
	}

	public int getNumber() {
		return number;
	}

	//@Overload
	public void setNumber(int number) {
		if (number == 0)
			setNumber(number, true);
		this.setColored();
	}

	public void setNumber(int number, boolean changeable) {
		this.number = number;
		this.setColored();
		if (number == 0) {
			this.setText("");
			this.setDontChange(false);
		} else {
			this.setText(Integer.toString(number));
			this.setDontChange(!changeable);
		}

	}

	public void setColored() {
		if (colored) {
			switch (this.getNumber()) {
			case 1:
				this.setBackground(lightBlue);
				break;
			case 2:
				this.setBackground(lightCyan);
				break;
			case 3:
				this.setBackground(lightGreen);
				break;
			case 4:
				this.setBackground(lightPink);
				break;
			case 5:
				this.setBackground(lightPurple);
				break;
			case 6:
				this.setBackground(lightRed);
				break;
			case 7:
				this.setBackground(lightYellow);
				break;
			case 8:
				this.setBackground(lightOrange);
				break;
			case 9:
				this.setBackground(lightGrey);
				break;
			default:
				this.setBackground(white);
				break;
			}
		} else {
			if (isFix)
				this.setBackground(lightGrey);
			else
				this.setBackground(white);
		}
	}

	public int getHasCell() {
		return hasCell;
	}

	public void setHasCell(int hasCell) {
		this.hasCell = hasCell;
	}

	public boolean isDontChange() {
		return dontChange;
	}

	public void setDontChange(boolean dontChange) {
		this.dontChange = dontChange;
	}
	
	public boolean isColored() {
		return colored;
	}
	
	public void setColored(boolean colored) {
		this.colored = colored;
	}

	//declare variables
	private boolean isFix = false;
	private	boolean colored;
	private boolean dontChange = false;
	private int hasCell = 0;
	private int number = 0;
	private Font biggerFont = new Font("Arial", Font.BOLD, 20);
	//colors
	private Color white = new Color(255, 255, 255);
	private Color lightBlue = new Color(102, 163, 255);
	private Color lightCyan = new Color(128, 255, 255);
	private Color lightGreen = new Color(128, 255, 128);
	private Color lightYellow = new Color(255, 255, 128);
	private Color lightOrange = new Color(252, 191, 131);
	private Color lightRed = new Color(255, 128, 128);
	private Color lightPink = new Color(255, 128, 255);
	private Color lightPurple = new Color(191, 128, 255);
	private Color lightGrey = new Color(217, 217, 217);
	//End of declaration
}
