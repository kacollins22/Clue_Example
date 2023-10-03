package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TargetListener implements MouseListener{
	Board boardPanel = Board.getInstance();
	

	@Override
	public void mousePressed(MouseEvent e) {
		//For every board cell target, get it's coordinates.
		boolean validTarget = false;
		for(BoardCell target : boardPanel.getTargets()) {
			int cellWidth = boardPanel.getCellWidth();
			int cellHeight = boardPanel.getCellHeight();
			int xLoc = target.getCol() * cellWidth;
			int yLoc = target.getRow() * cellHeight;

			//If you click on the target, set the position of the player to that target
			//and then de-render the targets. Afterwards, exit.
			//This will automatically prevent players from moving when it's not their turn, due
			//to the lack of targets to click on and therefore move to.
			if((e.getX() >= xLoc && e.getX() < xLoc + cellWidth) && ((e.getY() >= yLoc && e.getY() < yLoc + cellHeight))) {
				validTarget = true;
				boardPanel.getPlayerList()[0].setPosition(target.getRow(), target.getCol());
				boardPanel.derenderTargets();
				boardPanel.repaint();
				//Also, set the occupied state of the new cell to true.
				BoardCell currentCell = boardPanel.getCell(target.getRow(),target.getCol());
				currentCell.setOccupied(true);
				if (currentCell.isRoomCenter()) {
					boardPanel.humanSuggestion();
				}
				break;
			}
		}
		if (!validTarget) {
			//Shows this message when clicking on an invalid target, supposedly.
			JPanel invalidTargets = new JPanel();
			JOptionPane.showMessageDialog(invalidTargets,"This is not a valid target to move to.");
		}
	}

	//Everything below this point are empty events that simply need to
	//exist in order to implement mouseListener properly.
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}