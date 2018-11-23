package de.Luciano.Sudoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

	/**
	 * @author Luciano Jung
	 * @category Game
	 * @version 2.5.1
	 */

	public GUI() {
		initComponents();
	}

	// Create a new Game with the Game Item
	private void newGameItemActionPerformed(java.awt.event.ActionEvent evt) {
		// create GameField
		if (field != null)
			this.getContentPane().remove(field);
		field = new Field(difficulty);
		// set Location of panel middle
		field.setLocation(getJFrameSize().width / 2 - field.getWidth() / 2, labelSudoku.getHeight());
		// delete old panel than add Panel
		this.add(field, BorderLayout.CENTER);
		setColorfulItemActionPerformed(evt);
		refreshJFrame();
		// add Items to MenuBar
		gameMenu.add(proofSudokuItem);
		helpMenu.add(setColorfulItem);
		helpMenu.add(CountAnswersItem);
	}

	// Proof if the numbers filled in the Sudoku are right
	private boolean proofSudokuItemActionPerformed(ActionEvent evt) {
		return field.proofSudoku(true);

	}

	// set Colorful Sudoku on/off
	private void setColorfulItemActionPerformed(ActionEvent evt) {
		for (int i = 0; i < field.getBoxes().length; i++) {
			for (int j = 0; j < field.getBoxes()[0].length; j++) {
				field.getBoxes()[i][j].setColored(setColorfulItem.isSelected());
				field.getBoxes()[i][j].setColored();
			}
		}
	}

	// count the number of Answers the sudoku has
	protected void CountAnswersItemActionPerformed(ActionEvent evt) {
		int count = 0;
		ArrayList<int[]> possibleResults = new ArrayList<int[]>();
		// search max 100 results
		if (proofSudokuItemActionPerformed(evt))
			do {
				if (field.game.fillSudoku((int) (Math.random() * 9 + 1))) {
					int[] resultSet = new int[81];
					for (int cell = 0; cell < resultSet.length; cell++) {
						resultSet[cell] = field.getBox(cell).getNumber();
					}
					// add result set to possible results
					possibleResults.add(resultSet);
					// look if its double, break the inner loop if anything isn't identical
					for (int i = 0; i < possibleResults.size() - 1; i++) {
						for (int k = 0; k < 80; k++) {
							if ((possibleResults.get(i))[k] != (possibleResults.get(possibleResults.size() - 1))[k]) {
								break;
							}
							// last loop for this resultSet => everything's identical => remove because its
							// double
							if (k == 79) {
								possibleResults.remove(possibleResults.size() - 1);
								break;
							}
						}
					}
				}
				count++;
				field.game.deleteChangeableCells();
				// more than 100 results are enough
				if (possibleResults.size() > 100) {
					JOptionPane.showMessageDialog(field, "There are " + possibleResults.size() + " possible Answers!");
					return;
				}
			} while (count < possibleResults.size() * 25 + 25);
		// show possible results
		JOptionPane.showMessageDialog(field,
				"There are approximately " + possibleResults.size() + " possible Answers!");
	}

	// initialize the Components
	private void initComponents() {
		// getting the frame started
		this.setTitle("Sudoku");
		this.setSize(frameSize.width, frameSize.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		// getting the menubar started
		menuBar = new JMenuBar();
		createMenuBar();
		this.setJMenuBar(menuBar);

		// getting the label started
		labelSudoku = new JLabel("Sudoku", JLabel.CENTER);
		this.add(labelSudoku);
		labelSudoku.setSize(getJFrameSize().width, 50);
		labelSudoku.setFont(fontTitle);

		labelLJ = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/de/Luciano/Pictures/LJ.JPG")));
		this.add(labelLJ);
		labelLJ.setSize(100, 50);
		labelLJ.setLocation(0, (int) (frameSize.height / 1.2));
	}

	// create the Menu Bar; Yes thats happened only inside of init Components
	private void createMenuBar() {
		// create menus
		gameMenu = new JMenu("Game");
		difficultyMenu = new JMenu("Difficulty");
		infoMenu = new JMenu("Info");
		helpMenu = new JMenu("Help");
		// create menuItems
		newGameItem = new JMenuItem("New Game");
		proofSudokuItem = new JMenuItem("Proof Sudoku");
		setColorfulItem = new JCheckBoxMenuItem("Colorful Field");
		CountAnswersItem = new JMenuItem("Count Answers");
		difficultEmptyItem = new JRadioButtonMenuItem("Empty");
		difficultEasyItem = new JRadioButtonMenuItem("Easy");
		difficultEasyItem.setSelected(true);
		difficultNormalItem = new JRadioButtonMenuItem("Normal");
		difficultHardItem = new JRadioButtonMenuItem("Hard");
		difficultFullItem = new JRadioButtonMenuItem("Full");
		aboutAppItem = new JMenuItem("About");
		// create ButtonGroup + add Items
		DifficultyGroup = new ButtonGroup();
		DifficultyGroup.add(difficultEmptyItem);
		DifficultyGroup.add(difficultEasyItem);
		DifficultyGroup.add(difficultNormalItem);
		DifficultyGroup.add(difficultHardItem);
		DifficultyGroup.add(difficultFullItem);

		// create menuItem(s) + ActionListener
		// New Game
		newGameItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newGameItemActionPerformed(evt);
			}
		});
		// Set Count Answer
		CountAnswersItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CountAnswersItemActionPerformed(evt);
			}
		});
		// difficulty empty
		difficultEmptyItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				difficulty = 0;
			}
		});
		// difficulty easy
		difficultEasyItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				difficulty = 45;
			}
		});
		// difficulty normal
		difficultNormalItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				difficulty = 40;
			}
		});
		// difficulty hard
		difficultHardItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				difficulty = 35;
			}
		});
		// difficulty full
		difficultFullItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				difficulty = 80;
			}
		});
		// Show Info
		aboutAppItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(field,
						"Hello there and thanks for playing :)!" + "\n\nMy Name is Luciano Jung"
								+ "\nand I created this Game because of the" + "\nBacktracking Algorithm"
								+ "\nand because of a bet with a friend." + "\n\nYou can give me Feedback at:"
								+ "\nlucianoj@posteo.de",
						"About the App and Me", 3);
			}
		});
		// Proof Sudoku
		proofSudokuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!proofSudokuItemActionPerformed(evt))
					JOptionPane.showMessageDialog(field, "The Sudoku is incorrect", "Thats NOT the way it goes!", 1);
				else
					JOptionPane.showMessageDialog(field, "Everythings Right", "Thats the way it goes!", -1);
			}
		});
		// Set Colorful
		setColorfulItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				setColorfulItemActionPerformed(evt);
			}
		});

		// add MenuItme(s)
		gameMenu.add(newGameItem);
		difficultyMenu.add(difficultEmptyItem);
		difficultyMenu.add(difficultEasyItem);
		difficultyMenu.add(difficultNormalItem);
		difficultyMenu.add(difficultHardItem);
		difficultyMenu.add(difficultFullItem);
		infoMenu.add(aboutAppItem);
		// add Menus
		menuBar.add(gameMenu);
		menuBar.add(difficultyMenu);
		menuBar.add(infoMenu);
		menuBar.add(helpMenu);
	}

	public JFrame getJFrame() {
		return this;
	}

	public JMenu getGameMenu() {
		return gameMenu;
	}

	public JMenuItem getNewGameItem() {
		return newGameItem;
	}

	public JMenuBar getJMenuBar() {
		return menuBar;
	}

	// refresh the frame
	private void refreshJFrame() {
		SwingUtilities.updateComponentTreeUI(this);
	}

	public Dimension getJFrameSize() {
		return frameSize;
	}

	public void setJFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

	// Variables declaration
	private Font fontTitle = new Font("serif", Font.PLAIN, 50);
	private Dimension frameSize = new Dimension(600, 700);
	private int difficulty = 35;
	private ButtonGroup DifficultyGroup;
	// GUI Objects
	private Field field;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu difficultyMenu;
	private JMenu infoMenu;
	private JMenu helpMenu;
	private JMenuItem newGameItem;
	private JMenuItem proofSudokuItem;
	private JMenuItem CountAnswersItem;
	private JMenuItem difficultEmptyItem;
	private JMenuItem difficultEasyItem;
	private JMenuItem difficultNormalItem;
	private JMenuItem difficultHardItem;
	private JMenuItem difficultFullItem;
	private JMenuItem aboutAppItem;
	private JCheckBoxMenuItem setColorfulItem;
	private JLabel labelSudoku;
	private JLabel labelLJ;
	// End Variables declaration

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GUI ex = new GUI();
			ex.setVisible(true);
		});
	}
}
