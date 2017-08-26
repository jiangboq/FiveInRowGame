import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
public class ChessFrame extends JFrame {
    private static ChessPanel chp; 
    private static Board board;
 
    public ChessFrame() {
        ChessFrame.chp=new ChessPanel();  
        this.addMouseListener(new CustomMouseListener());
        this.getContentPane().add(ChessFrame.chp);
        ChessFrame.board = new Board();

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    private static class CustomMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            int cellX = transfer(e.getX(), e.getY())[0];
            int cellY = transfer(e.getX(), e.getY())[1];
			
            if(!board.isFinished()) {
                Cell userCell = new Cell(cellX, cellY);
                board.move(userCell, Player.USER);
                chp.updateBoard(getChessCenter(cellX, cellY)[0], getChessCenter(cellX, cellY)[1], 0);
				
                if (board.isFinished()) {
                    System.out.println("finished");
                }
				
                board.callMinimax(0, Player.COMPUTER);
				
                Cell computerCell = board.getBestMove();
				
                board.move(computerCell, Player.COMPUTER);  
				
                chp.updateBoard(getChessCenter(computerCell.getX(), computerCell.getY())[0], getChessCenter(computerCell.getX(), computerCell.getY())[1], 1); 
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        	// TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub	
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
		
        public int[] transfer(int x, int y) {
            int[] result = new int[2];
            result[0] = (x - 22) / 35;
            result[1] = (y - 46) / 35;
			
            return result;
        }
		
        public int[] getChessCenter(int cellX, int cellY) {
            int[] result = new int[2];
            result[0] = 22 + cellX * 35 + 17;
            result[1] = 46 + cellY * 35;
            return result;
        }
    }
}