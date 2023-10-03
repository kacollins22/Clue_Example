package clueGame;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ClueGame extends JFrame{
	
	private static Board boardPanel = null;
	private static GameCardPanel cardPanel = null;
	private static GameControlPanel controlPanel = null;
	
	public static void makeSplashScreen() {
		//Create a splash screen panel to show at the program start.
		JPanel splashContents = new JPanel();
		JOptionPane.showMessageDialog(splashContents, "You are the Vessel. \nYour goal is to find the solution \nbefore any computer players.");
	}
	
	public void updateControlPanel(Player currPlayer, int dieRoll) {
		controlPanel.setName(currPlayer);
		controlPanel.setRoll(dieRoll);
	}
	
	public void updateBoard() {
		boardPanel.repaint();
		cardPanel.updatePanel(cardPanel, (HumanPlayer)boardPanel.getPlayerList()[0]);
		cardPanel.repaint();
	}
	
	public void gameWon() {
		//Make a splash screen that lets people know that the game is won, by who,
		//maybe what the solution is for fun, and then closes the whole program.
		JPanel winContents = new JPanel();
		
		//Get the winning player and the solution cards.
		int playerTurn = boardPanel.getPlayerTurn();
		String winningPlayer = boardPanel.getPlayerList()[playerTurn].name;
		Solution solutionCards = boardPanel.getSolution();
		String weapon = solutionCards.getWeapon().getCardName();
		String person = solutionCards.getPerson().getCardName();
		String room = solutionCards.getRoom().getCardName();
		
		//Print the info out in a dialog message, then close the game when the dialog is closed.
		String winMessage = (winningPlayer + " has won! \nThe solution was the following: \n" + person + "\n" + room + "\n" + weapon + "\nClose this window to end the game.");
		JOptionPane.showMessageDialog(winContents, winMessage);
		this.dispose();
	}
	
	public void gameLost() {
		//Make a splash screen showing that you lost due to making a wrong
		//accusation. Also, close the game afterwards.
		JPanel loseContents = new JPanel();

		//Get the solution cards.
		Solution solutionCards = boardPanel.getSolution();
		String weapon = solutionCards.getWeapon().getCardName();
		String person = solutionCards.getPerson().getCardName();
		String room = solutionCards.getRoom().getCardName();
		
		//Print the info out in a dialog message, then close the game when the dialog is closed.
		String loseMessage = "You have lost due to sumbitting an incorrect accusation! \nThe correct solution was the following: \n" + person + "\n" + room + "\n" + weapon + "\nClose this window to end the game.";
		JOptionPane.showMessageDialog(loseContents, loseMessage);
		this.dispose();
	}
	
	public void verifyHumanAccusation(String person, String room, String weapon) {
		Solution finalCheck = boardPanel.getSolution();
		String finalPerson = finalCheck.getPerson().getCardName();
		String finalRoom =  finalCheck.getRoom().getCardName();
		String finalWeapon =  finalCheck.getWeapon().getCardName();
		
		if(person.equals(finalPerson) && room.equals(finalRoom) && weapon.equals(finalWeapon)) {
			gameWon();
		} else {
			gameLost();
		}
	}
	
	public static void main(String[] args) {
		//This is the initialization function for the entire program. Right now, the goal
		//is to make it pull up a complete Board, the GameControlPanel, and the GameCardPanel
		//simultaneously.

		//Create the frame, make it exit on close, and make it visible. 
		ClueGame gameFrame = new ClueGame();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		
		//Get the contents of the board from their various places.
		//Create and quickly initialize the board.
		boardPanel = Board.getInstance();
		boardPanel.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		boardPanel.initialize();
		//Get the human player for GameCardPanel, then create it
		Player[] players = boardPanel.getPlayerList();
		HumanPlayer human = (HumanPlayer) players[0];
		cardPanel = new GameCardPanel(human);
		controlPanel = new GameControlPanel(boardPanel, gameFrame);
		
		//Finally, set the contents of the panel to be the respective controls and displays.
		gameFrame.setTitle("CSCI 306 - Clue");
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(boardPanel, BorderLayout.CENTER);
		gameFrame.add(cardPanel,BorderLayout.EAST);
		gameFrame.add(controlPanel,BorderLayout.SOUTH);
		gameFrame.setSize(900, 800);
		
		boardPanel.setGameFrame(gameFrame);
		
		//Once all of that is done, throw up a splash screen. This should pop up
		//once when the program is started, and the game should only start when closed.
		makeSplashScreen();
		try {
			boardPanel.nextFunction(gameFrame);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void updateControlGuess(Solution suggestion, Card disproved){
		String personName = suggestion.getPerson().getCardName();
		String roomName = suggestion.getRoom().getCardName();
		String weaponName = suggestion.getWeapon().getCardName();
		
		controlPanel.setGuess(personName + ", " + roomName + ", " + weaponName);
		if (disproved != null) {
			controlPanel.setGuessResult("Disproved: " + disproved.getCardName());
		}
		else {
			controlPanel.setGuessResult("Disproved: None");
		}
		
	}
}
