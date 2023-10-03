package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private JTextField name;
	private JTextField roll;
	private JTextField guess;
	private JTextField guessResult;
	private Board boardPanel;
	private ClueGame gameFrame;
	private JFrame accusationFrame;
	private JComboBox <String> peopleDropdown;
	private JComboBox <String> roomDropdown;
	private JComboBox <String> weaponDropdown;

	// A constructor for the panel. Between it and it's submethods,
	// it does the vast majority of the work.
	public GameControlPanel(Board board, ClueGame game)  {
		//Create a layout with 2 columns.
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopRow();
		add(panel);
		panel = createBottomRow();
		add(panel);
		boardPanel = board;
		gameFrame = game;
	}
	
	public JPanel createTopRow() {
		//Create the top panel and set it to a 1x4 layout (1 row, 4 elements)
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		//Create the subpanels in their own submethods
		JPanel nPanel = createNamePanel();
		add(nPanel);
		JPanel tPanel = createTurnPanel();
		add(tPanel);
		JPanel accButton = createAccButton();
		add(accButton);
		JPanel nextTurnButton = createNextTurnButton();
		add(nextTurnButton);
		//Return the topPanel, where the four sub-panels are stored
		return topPanel;
	}
	
	private JPanel createNamePanel() {
		//Create a panel with layout of 2,1 (2 rows, 1 element each)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		//Above the name, we signify this is for who's turn it is
		JLabel nameLabel = new JLabel("Whose Turn?", SwingConstants.CENTER);
		//Also, make the name a part of this (name was set earlier as a private variable)
		name = new JTextField(20);
		name.setEditable(false);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		//Add both nameLabel and name to the panel, then return it
		panel.add(nameLabel);
		panel.add(name);
		return panel;
	}
	
	private JPanel createTurnPanel() {
		//Create a panel with a layout of 2 rows, 1 element each (consistency!)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		//To the left, we have a label signifying this is the roll
		JLabel rollLabel = new JLabel("Roll:", SwingConstants.CENTER);
		//We have a JTextField that should contain a number
		roll = new JTextField(5);
		roll.setEditable(false);
		roll.setHorizontalAlignment(SwingConstants.CENTER);
		//Add both rollLabel and roll to the panel
		panel.add(rollLabel);
		panel.add(roll);
		return panel;
	}
	
	private JPanel createAccButton() {
		JPanel panel = new JPanel();
		JButton accuse = new JButton("Make Accusation");
		accuse.setPreferredSize(new Dimension(180,70));
		panel.add(accuse);
		accuse.addActionListener(new AccusationListener());
		return panel;
	}
	
	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Solution accusation = makeAccusation();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		public Solution makeAccusation() {
			//Create variables to be set by the accusation pop-up.
			Card person;
			Card room;
			Card weapon;
			
			//Make a pop-up dialog that must be filled out. Should have fields for person,
			//room, and weapon to be filled through dropdowns, along with a way to cancel.
			accusationFrame = new JFrame();
			accusationFrame.setLayout(new GridLayout(4,2));
			
			//Get lists of certain card types from the deck for future use
			ArrayList <Card> peopleCards = boardPanel.getPersonCards();
			ArrayList <Card> roomCards = boardPanel.getRoomCards();
			ArrayList <Card> weaponCards = boardPanel.getWeaponCards();
			
			//Make a dropdown menu and respective label for the person cards
			JLabel personLabel = new JLabel("Person");
			accusationFrame.add(personLabel);
			
			peopleDropdown = new JComboBox <String> ();
			for (int i = 0; i < peopleCards.size(); i++) {
				peopleDropdown.addItem(peopleCards.get(i).getCardName());
			}
			accusationFrame.add(peopleDropdown);
			
			//Make a dropdown menu and respective label for the room cards
			JLabel roomLabel = new JLabel("Room");
			accusationFrame.add(roomLabel);
			
			roomDropdown = new JComboBox <String>();
			for (int i = 0; i < roomCards.size(); i++) {
				roomDropdown.addItem(roomCards.get(i).getCardName());
			}
			accusationFrame.add(roomDropdown);
			
			//Make a dropdown menu and respective label for the weapon cards
			JLabel weaponLabel = new JLabel("Weapon");
			accusationFrame.add(weaponLabel);
			
			weaponDropdown = new JComboBox <String>();
			for (int i = 0; i < weaponCards.size(); i++) {
				weaponDropdown.addItem(weaponCards.get(i).getCardName());
			}
			accusationFrame.add(weaponDropdown);
			
			//Make two buttons - one to submit, one to cancel
			//There may be an easy way to do this if you make the right type of dialog
			JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(new SubmitListener());
			accusationFrame.add(submitButton);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelListener());
			accusationFrame.add(cancelButton);
			
			//Show the JFrame for the player to fill out
			accusationFrame.setTitle("Make an Accusation");
			accusationFrame.setSize(new Dimension(320,150));
			accusationFrame.setVisible(true);
			
			//Once the pop-up has been completed, extract the chosen cards and
			//fill in the solution before returning it to the above function.
			Solution playerSolution = new Solution();
			return playerSolution;
		}
		
		private class SubmitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//Get the things from the dropdown boxes, pack them
				//nicely into a solution, and throw it to clueGame.
				String selectedPerson = peopleDropdown.getItemAt(peopleDropdown.getSelectedIndex());
				String selectedRoom = roomDropdown.getItemAt(roomDropdown.getSelectedIndex());
				String selectedWeapon = weaponDropdown.getItemAt(weaponDropdown.getSelectedIndex());
				boardPanel.setPlayerTurn(0);
				gameFrame.verifyHumanAccusation(selectedPerson,selectedRoom,selectedWeapon);
				accusationFrame.dispose();
			}
		}
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//Cancel the accusation and close out the window.
				accusationFrame.dispose();
			}
		}
	}
	
	private JPanel createNextTurnButton() {
		JPanel panel = new JPanel();
		JButton nextTurn = new JButton("Next!");
		nextTurn.setPreferredSize(new Dimension(180,70));
		panel.add(nextTurn);
		nextTurn.addActionListener(new NextListener());
		return panel;
	}
	
	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				boardPanel.nextFunction(gameFrame);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public JPanel createBottomRow() {
		//Create the lower panel, and set it to a 1x2 layout (1 row, 2 elements)
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,0));
		JPanel gPanel = createGuessPanel();
		add(gPanel);
		JPanel gResultPanel = createGuessResultPanel();
		add(gResultPanel);
		return bottomPanel;
	}
	
	public JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, undefined elements 
		panel.setLayout(new GridLayout(1,0));
		guess = new JTextField(20);
		panel.add(guess);
		guess.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Your Guess:"));
		return panel;
	}
	
	public JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		guessResult = new JTextField(20);
		panel.add(guessResult);
		guessResult.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Your Guess Result:"));
		return panel;
	}
	
	public Color colorStringToColor(String input) {
		Color output = Color.WHITE;
		if (input.equals("YELLOW")) { output = new Color(255,255,150); }
		if (input.equals("RED")) { output = new Color(255,150,150); }
		if (input.equals("BLUE")) { output = new Color(150,150,255); }
		if (input.equals("GREEN")) { output = new Color(150,255,150); }
		if (input.equals("PURPLE")) { output = new Color(198,100,185); }
		if (input.equals("WHITE")) { output = new Color(220,220,220); }
		return output; 
	}
	
	//Setters for name, the die roll, guess, and guessResult
	public void setName(Player player) {
		name.setText(player.getName());
		name.setBackground(colorStringToColor(player.getColor()));
	}
	
	public void setRoll(Integer newRoll) {
		roll.setText(newRoll.toString());
	}
	
	public void setGuess(String newGuess) {
		guess.setText(newGuess);
	}
	
	public void setGuessResult(String newGuessResult) {
		guessResult.setText(newGuessResult);
	}
}