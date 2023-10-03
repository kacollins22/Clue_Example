package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String name, String color, boolean isHuman) {
		super(name, color, isHuman);
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
	
	//This function is essentially the path-finding algorithm for a computer player.
	public BoardCell selectTarget(int pathLength) {
		//Create a set to contain targets, then get that set from the board.
		Set<BoardCell> targets = new HashSet<BoardCell>();
		Board board = Board.getInstance();
		BoardCell positionCell = board.getCell(row, column);
		positionCell.setOccupied(false);
		board.calcTargets(positionCell, pathLength);
		targets = board.getTargets();
		
		//If the computerPlayer cannot move, then it must stay there.
		if (targets.isEmpty()) {
			return positionCell;
		}
		
		//Otherwise, find the available rooms to move to and try to do so.
		ArrayList<BoardCell> availibleRooms = new ArrayList<BoardCell>();
		for(BoardCell cell : targets) {
			if (cell.isRoomCenter() && !seenRooms.contains(board.getRoom(cell))) {
				availibleRooms.add(cell);
			}
		}
		
		//If there are available unseen rooms, move into a room at random and return that.
		if(!availibleRooms.isEmpty()) {
			Random rand = new Random();
			int randomRoom = rand.nextInt(availibleRooms.size());
			
			BoardCell roomChosen = availibleRooms.get(randomRoom);
			setPosition(roomChosen.getRow(), roomChosen.getCol());
			seenRooms.add(board.getRoom(roomChosen));
			
			return roomChosen;
			
		}
		//Otherwise, move to a random cell.
		else {
			ArrayList<BoardCell> targetArrayList = new ArrayList<BoardCell>(targets);
			
			Random rand = new Random();
			int randomSpace = rand.nextInt(targetArrayList.size());
			
			BoardCell spaceChosen = targetArrayList.get(randomSpace);
			setPosition(spaceChosen.getRow(), spaceChosen.getCol());
			
			return spaceChosen;
		}
	}
	
	public Solution makeSuggestion() {
		Solution suggestion;
		Board board = Board.getInstance();
		BoardCell cell = board.getCell(row, column);
		
		Card roomCard = board.getRoomCard(board.getRoom(cell));
		//TODO: Get person and weapon cards from deck - seen;
		
		ArrayList<Card> personOptions = board.getPersonCards();
		personOptions.removeAll(seenCards);
		personOptions.removeAll(hand);
		
		ArrayList<Card> weaponOptions = board.getWeaponCards();
		weaponOptions.removeAll(seenCards);
		weaponOptions.removeAll(hand);
		
		Random rand = new Random();
		int randomPerson = rand.nextInt(personOptions.size());
		Card personCard = personOptions.get(randomPerson);
		
		rand = new Random();
		int randomWeapon = rand.nextInt(weaponOptions.size());
		Card weaponCard = weaponOptions.get(randomWeapon);
		
		suggestion = new Solution(roomCard, personCard, weaponCard);
		return suggestion;
	}
	
	public void makeAccusation(ClueGame gameFrame) {
		Board board = Board.getInstance();
		ArrayList<Card> roomCards = board.getPersonCards();
		ArrayList<Card> personCards = board.getPersonCards();
		ArrayList<Card> weaponCards = board.getWeaponCards();
		
		roomCards.removeAll(seenCards);
		roomCards.removeAll(hand);
		
		personCards.removeAll(seenCards);
		personCards.removeAll(hand);
		
		weaponCards.removeAll(seenCards);
		weaponCards.removeAll(hand);
		
		if(roomCards.size() == 1 && personCards.size() == 1 && weaponCards.size() == 1) {
			gameFrame.gameWon();
		}
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
	
	public ArrayList<Card> getSeenCards(){
		return seenCards;
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
}
