package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HumanPlayer extends Player{
	
	JFrame suggestionFrame;
	JComboBox <String> peopleDropdown;
	JLabel currentRoomLabel;
	JComboBox <String> weaponDropdown;
	Solution humanSuggestion;
	
	public HumanPlayer(String string, String string2, boolean b) {
		super(string, string2, b);
	}
	
	public void updateHand(Card card) {
		super.updateHand(card);
	}
	
	public ArrayList<Card> getHand(){
		return super.getHand();
	}
	
	public boolean isHuman() {
		return super.isHuman();
	}
	
	public Card disproveSuggestion(Card weapon, Card room, Card person) {
		return super.disproveSuggestion(weapon, room, person);
	}
	
	public void setPosition(int row, int column) {
		super.setPosition(row, column);
	}
	
	public void addRoomsSeen(char c) {
		super.addRoomsSeen(c);
	}
	
	public void addSeen(Card card) {
		super.addSeen(card);
	}
	
	public String getName() {
		return super.getName();
	}
	
	public String getColor() {
		return super.getColor();
	}
	
	public ArrayList<Card> getHandType(CardType type){
		return super.getHandType(type);
	}
	
	public ArrayList<Card> getSeenType(CardType type){
		return super.getSeenType(type);
	}
	
	public Color colorStringToColor() {
		return super.colorStringToColor();
	}
	
	public void drawPlayer(Graphics g, int width, int height) {
		super.drawPlayer(g, width, height);
	}
	
	public int getPlayerRow() {
		return super.getPlayerRow();
	}
	
	public int getPlayerCol() {
		return super.getPlayerCol();
	}
	
	public void makeHumanSuggestion(ClueGame gameFrame) {
		Board board = Board.getInstance();
		BoardCell cell = board.getCell(row, column);
		
		Card roomCard = board.getRoomCard(board.getRoom(cell));
		
		//Create variables to be set by the accusation pop-up.
		Card person;
		Card room;
		Card weapon;
		
		//Make a pop-up dialog that must be filled out. Should have fields for person,
		//and weapon to be filled through dropdowns, along with a way to cancel.
		suggestionFrame = new JFrame();
		suggestionFrame.setLayout(new GridLayout(4,2));
		
		//Get lists of certain card types from the deck for future use
		ArrayList <Card> peopleCards = board.getPersonCards();
		ArrayList <Card> weaponCards = board.getWeaponCards();
		
		//Make a dropdown menu and respective label for the person cards
		JLabel personLabel = new JLabel("Person");
		suggestionFrame.add(personLabel);
		
		peopleDropdown = new JComboBox <String> ();
		for (int i = 0; i < peopleCards.size(); i++) {
			peopleDropdown.addItem(peopleCards.get(i).getCardName());
		}
		suggestionFrame.add(peopleDropdown);
		
		//Show which room you're currently in, which will be a part of the suggestion
		JLabel roomLabel = new JLabel("Room");
		suggestionFrame.add(roomLabel);
		currentRoomLabel = new JLabel(roomCard.getCardName());
		suggestionFrame.add(currentRoomLabel);
		
		//Make a dropdown menu and respective label for the weapon cards
		JLabel weaponLabel = new JLabel("Weapon");
		suggestionFrame.add(weaponLabel);
		
		weaponDropdown = new JComboBox <String>();
		for (int i = 0; i < weaponCards.size(); i++) {
			weaponDropdown.addItem(weaponCards.get(i).getCardName());
		}
		suggestionFrame.add(weaponDropdown);
		
		//Make two buttons - one to submit, one to cancel
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitListener());
		suggestionFrame.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelListener());
		suggestionFrame.add(cancelButton);
		
		//Show the JFrame for the player to fill out
		suggestionFrame.setTitle("Make a Suggestion");
		suggestionFrame.setSize(new Dimension(320,150));
		suggestionFrame.setVisible(true);
	}
	
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Get the things from the dropdown boxes and get their corresponding cards.
			String selectedPerson = peopleDropdown.getItemAt(peopleDropdown.getSelectedIndex());
			String selectedRoom = currentRoomLabel.getText();
			String selectedWeapon = weaponDropdown.getItemAt(weaponDropdown.getSelectedIndex());
			
			//Get lists of the card types from the deck for use in finding the cards from strings
			Board board = Board.getInstance();
			ArrayList <Card> peopleCards = board.getPersonCards();
			ArrayList <Card> roomCards = board.getRoomCards();
			ArrayList <Card> weaponCards = board.getWeaponCards();
			
			//Get the person card from the deck using the name
			Card selectedPersonCard = new Card();
			for (int i = 0; i < peopleCards.size(); i++) {
				Card currentCard = peopleCards.get(i);
				if (currentCard.getCardName().equals(selectedPerson)) {
					selectedPersonCard = currentCard;
				}
			}
			
			//Get the room card from the deck using the name
			Card selectedRoomCard = new Card();
			for (int i = 0; i < roomCards.size(); i++) {
				Card currentCard = roomCards.get(i);
				if (currentCard.getCardName().equals(selectedRoom)) {
					selectedRoomCard = currentCard;
				}
			}
			
			//Get the weapon card from the deck using the name.
			Card selectedWeaponCard = new Card();
			for (int i = 0; i < weaponCards.size(); i++) {
				Card currentCard = weaponCards.get(i);
				if (currentCard.getCardName().equals(selectedWeapon)) {
					selectedWeaponCard = currentCard;
				}
			}
			
			//Finally, set human suggestion and dispose of the frame.
			humanSuggestion = new Solution(selectedPersonCard,selectedRoomCard,selectedWeaponCard);
			board.handleHumanSuggestion(humanSuggestion);
			suggestionFrame.dispose();
		}
	}
	
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Cancel the suggestion and close out the window.
			humanSuggestion = new Solution();
			suggestionFrame.dispose();
		}
	}
}
