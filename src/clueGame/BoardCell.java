package clueGame;

import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;

public class BoardCell extends JPanel{
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private static final Color doorGreen = new Color(150,255,150);
	
	private Boolean isOccupied;
	private Boolean isRoom;
	private Boolean isTarget;

	//On BoardCell initialize, set default case  and initialize adjList
	BoardCell(){
		super();
		this.roomLabel = false;
		this.roomCenter = false;
		this.isOccupied = false;
		this.isRoom = false;
		this.secretPassage = '0';
		adjList = new HashSet<BoardCell>();
	}
	
	//Set cell row, col
	public void setRowCol(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	//Getter and setter for cell initial
	public void setInitial(char c) {
		this.initial = c;
	}
	public char getInitial() {
		return initial;
	}
	//Add adjacentcies for target finding
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	//Return adjList for testing and target finding
	public Set<BoardCell> getAdj(){
		return adjList;
	}
	//Getter and Setter for Door Direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public void setDoorDirection(DoorDirection dd) {
		this.doorDirection = dd;
	}
	//Checker for doorway status
	public Boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		} else {
			return false;
		}
	}
	//Getter and setter for cell label
	public boolean isLabel() {
		return roomLabel;
	}
	public void setLabel() {
		this.roomLabel = true;
	}
	//Getter and setter for room center
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public void setRoomCenter() {
		this.roomCenter = true;
	}
	//Getter and setter for secret passage
	public char getSecretPassage() {
		return secretPassage;
	}
	public void setSecretPassage(char sp) {
		this.secretPassage = sp;
	}
	//Getter and setter for occupied
	public Boolean getOccupied() {
		return isOccupied;
	}
	public void setOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	//Getter and setter for room status
	public Boolean getRoom() {
		return isRoom;
	}
	public void setRoom() {
		this.isRoom = true;
	}
	
	public void draw(Graphics g, int xOffset, int yOffset, int width, int height) {
		//Handles the drawing of a walkway tile.
		if (initial == 'W') {
			g.setColor(new Color(255,255,150));
			//If this tile is a target, change the color to cyan to indicate so.
			g.drawRect(xOffset + 2, yOffset + 2, width - 4, height - 4);
			
			if(doorDirection == DoorDirection.DOWN) {
				g.setColor(doorGreen);
				g.drawLine((xOffset + 2), (yOffset + 2), (xOffset + (width - 2)/2), (yOffset + height - 2));
				g.drawLine((xOffset + width - 2), (yOffset + 2), (xOffset + (width - 2)/2), (yOffset + height - 2));
				g.drawLine((xOffset + 2), (yOffset+2), (xOffset+width-2), (yOffset+2));
			}
			else if (doorDirection == DoorDirection.RIGHT) {
				g.setColor(doorGreen);
				g.drawLine((xOffset + 2), (yOffset + 2), (xOffset + width - 2), (yOffset + (height - 2)/2));
				g.drawLine((xOffset + 2), (yOffset + height - 2), (xOffset + width - 2), (yOffset + (height - 2)/2));
				g.drawLine((xOffset + 2), (yOffset + 2), (xOffset + 2), (yOffset + height - 2));
			}
			else if (doorDirection == DoorDirection.UP) {
				g.setColor(doorGreen);
				g.drawLine((xOffset + 2), (yOffset + height - 2), (xOffset + (width - 2)/2), (yOffset + 2));
				g.drawLine((xOffset + width - 2), (yOffset + height - 2), (xOffset + (width - 2)/2), (yOffset + 2));
				g.drawLine((xOffset + 2), (yOffset + height - 2), (xOffset + width - 2), (yOffset + height - 2));
			}
			else if (doorDirection == DoorDirection.LEFT) {
				g.setColor(doorGreen);
				g.drawLine((xOffset + width - 2), (yOffset + 2), (xOffset + 2), (yOffset + (height - 2)/2));
				g.drawLine((xOffset + width - 2), (yOffset + height - 2), (xOffset + 2), (yOffset + (height - 2)/2));
				g.drawLine((xOffset + width - 2), (yOffset + 2), (xOffset + width - 2), (yOffset + height - 2));
			}
		}
		else if (initial != 'X') {
			g.setColor(new Color(124,133,146));
			g.drawRect(xOffset, yOffset, width, height);
			g.fillRect(xOffset, yOffset, width, height);
		}
	}
	
	public void drawLabel(Graphics g, int xOffset, int yOffset, String label) {
		g.setColor(new Color(0,200,200));
		g.drawString(label,xOffset - 25,yOffset + 10);
	}
	
	public void drawTarget(Graphics g, int width, int height) {
		g.setColor(new Color(0, 255, 255));
		g.drawRect((col*width) + 1, (row*height) + 1, width - 2, height - 2);
		g.drawRect((col*width) + 2, (row*height) + 2, width - 4, height - 4);
	}

	public void setTarget(boolean b) {
		isTarget = b;
	}
	
	public boolean isTarget() {
		return isTarget;
	}
}
