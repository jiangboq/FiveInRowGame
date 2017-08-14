import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ChessPanel extends JPanel {

	private static final int panelWidth = 712;
	private static final int panelHeight = 712;
	private static final int chessRadius = 16;
	private static final int offsetX = 5;
	private static final int offsetY = 5;
	private static final int lineNumber = 19;
	private static final int mainWidth = 630;
	private static final int gridSize = 35;

	public ChessPanel() {
		// TODO Auto-generated constructor

		this.setPreferredSize(new Dimension(panelWidth, panelHeight));

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		for (int i = 0; i < lineNumber; i++) {
			g.drawLine(offsetX + chessRadius, offsetY + chessRadius + i * gridSize, offsetX
			  + chessRadius + mainWidth, offsetY + chessRadius + i * gridSize);
		}
		for (int j = 0; j < lineNumber; j++) {
			g.drawLine(offsetX + chessRadius + j * gridSize, offsetY + chessRadius, offsetX
			  + chessRadius + j * gridSize, offsetY + chessRadius + mainWidth);
		}
	}
 	public void updateBoard(int x, int y, int color) {
 		Graphics g = this.getGraphics();
 		if(color == 0) {
 			g.setColor(Color.BLACK);
 		}
 		else {
 			g.setColor(Color.WHITE);
 		}
 		g.fillOval(x-10, y-18, 20, 20);
 	}
}


