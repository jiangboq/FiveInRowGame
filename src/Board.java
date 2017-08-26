import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private List<Cell> candidateCells;
    private List<Cell> rootValues;
    private Player[][] board;
    private static int BOARD_SIZE = 18;
	
    public Board() {
        this.candidateCells = new ArrayList<Cell>();
        this.rootValues = new ArrayList<Cell>();
        this.board = new Player[BOARD_SIZE][BOARD_SIZE];
        this.initializeBoard();
    }
	
    public List<Cell> getEmptyCells() {
        
        List<Cell> emptyCells = new ArrayList<>();
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Player.NONE) {
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        return emptyCells;
    }
	
    public void move(Cell point, Player player) {
        board[point.getX()][point.getY()] = player;   
    }
	 
    public List<Cell> getCandidateCells() {
        List<Cell> candidateCells = new ArrayList<Cell>();
		
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                if(board[i][j] == Player.NONE) {
                    if(i+1 < BOARD_SIZE && board[i+1][j] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }
                    else if(i-1 > 0 && board[i-1][j] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }
                    else if(j-1 > 0 && board[i][j-1] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }
                    else if(j+1 < BOARD_SIZE && board[i][j+1] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }
                    else if(i+1 < BOARD_SIZE && j+1 < BOARD_SIZE && board[i+1][j+1] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }		
                    else if(i-1 > 0 && j-1 > 0 && board[i-1][j-1] != Player.NONE) {
                        candidateCells.add(new Cell(i, j));
                    }
                }
            }
        }
        
        return candidateCells;
    }

    public boolean isWinning(Player player) {
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                if(board[i][j] == player) {
                    int k;
					
                    for(k=1; k<5 && j+k<BOARD_SIZE; k++) {
                        if(board[i][j+k] != player) {
                            break;
                        }
                    }
                    if(k == 5) {
                        return true;
                    }
					
                    for(k=1; k<5 && i+k<BOARD_SIZE; k++) {
                        if(board[i+k][j] != player) {
                            break;
                        }
                    }
                    if(k == 5) {
                        return true;
                    }
					
                    for(k=1; k<5 && j+k<BOARD_SIZE && i-k>=0; k++) {
                        if(board[i-k][j+k] != player) {
                            break;
                        }
                    }
                    if(k == 5) {
                        return true;
                    }
					
                    for(k=1; k<5 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
                        if(board[i+k][j+k] != player) {
                            break;
                        }
                    }
                    if(k == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
    public boolean isFinished() {
        if(isWinning(Player.COMPUTER)) {
            return true;
        }
		
        if(isWinning(Player.USER)) {
            return true;
        }
        if(getEmptyCells().isEmpty()) {
            return true;
        }
		
        return false;
    }
	
    public Cell getBestMove() {
        int best = Integer.MIN_VALUE;
        int index = Integer.MIN_VALUE;
		
        for (int i = 0; i < rootValues.size(); ++i) { 
            if (best < rootValues.get(i).getValue()) {
                best = rootValues.get(i).getValue();
                index = i;
            }
        }

        return rootValues.get(index);
    }
	
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = Integer.MIN_VALUE;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        
        return list.get(index);
    }
    
    public void callMinimax(int depth, Player player){
        rootValues.clear();
        minimax(depth, player);
    }
    
    public int minimax(int depth, Player player) {
        if(isWinning(Player.COMPUTER)) {
            return 10000;
        }
        else if(isWinning(Player.USER)) {
            return -10000;
        }
		
		
        List<Cell> candidateCells = getCandidateCells();
		
        if (candidateCells.isEmpty() || depth == 4) {
            return countScore(player.COMPUTER) - countScore(player.USER); 
        }
		
        List<Integer> scores = new ArrayList<Integer>(); 
        for (int i = 0; i < candidateCells.size(); i++) {
            Cell point = candidateCells.get(i);  
            board[point.getX()][point.getY()] = player;
        	
            if (player == Player.COMPUTER) { 
                int currentScore = minimax(depth + 1, Player.USER);
                scores.add(currentScore);

                if (depth == 0) {
                    point.setValue(currentScore);
                    rootValues.add(point);
                }    	    
            } 
            else if (player == Player.USER) {
                scores.add(minimax(depth + 1, Player.COMPUTER));
            }
            
            board[point.getX()][point.getY()] = Player.NONE; //Reset this point
        }
        
        if( player == Player.COMPUTER ){
        	return returnMax(scores);
        }
        
        return returnMin(scores);
    }
	
    public void initializeBoard() {
        for(int i=0; i<BOARD_SIZE; i++){
            for(int j=0; j<BOARD_SIZE; j++){
                board[i][j] = Player.NONE; 
            }
        }
    }
	
    public boolean isOpposite(Player player, int x, int y) {
        return player == Player.COMPUTER ? board[x][y] == Player.USER : board[x][y] == Player.COMPUTER;
    }
    
    public int countScore(Player player) {
        int totalScore = 0;
        List<Shape> shape = Arrays.asList(Shape.DEADTWO, Shape.LIVETWO, Shape.DEADTHREE, Shape.LIVETHREE, Shape.DEADFOUR, Shape.LIVEFOUR, Shape.FIVE);
		
        for(int i=0; i<7; i++) {
            totalScore += Math.pow(10.0, i) * countShape(shape.get(i), player);
        }
		
        return totalScore;
    }
	
	public int countShape(Shape s, Player player) {
		int count = 0;
		switch(s) {
			case LIVETWO:
				for(int i=0; i<board.length; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player && j-1>0 && board[i][j-1] == Player.NONE && j+2 < BOARD_SIZE && board[i][j+1] == player && board[i][j+2] == Player.NONE) {
							count++;
						}
						if(board[i][j] == player && i-1>0 && board[i-1][j] == Player.NONE && i+2 < BOARD_SIZE && board[i+1][j] == player && board[i+2][j] == Player.NONE) {
							count++;
						}				
					}
				}
				break;
			case DEADTWO:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if((board[i][j] == player && j-1>0 && isOpposite(player, i, j-1) && j+2 < BOARD_SIZE && board[i][j+1] == player && board[i][j+2] == Player.NONE) ||
						(board[i][j] == player && j-1>0 && board[i][j-1] == Player.NONE && j+2 < BOARD_SIZE && board[i][j+1] == player && isOpposite(player, i, j+2))) {
							count++;
						}
						if((board[i][j] == player && i-1>0 && isOpposite(player, i-1, j) && i+2 < BOARD_SIZE && board[i+1][j] == player && board[i+2][j] == Player.NONE) ||
						(board[i][j] == player && i-1>0 && board[i-1][j] == Player.NONE && i+2 < BOARD_SIZE && board[i+1][j] == player && isOpposite(player, i+2, j))){
							count++;
						}			
					}
				}
				break;
				
			case LIVETHREE:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player) {
							int k;
							
							for(k=1; k<3 && j+k<BOARD_SIZE; k++) {
								if(board[i][j+k] != player) {
									break;
								}
							}
							
							if(k == 3 && j-1 >= 0 && j+3 < BOARD_SIZE && board[i][j-1] == Player.NONE && board[i][j+3] == Player.NONE) {
								count++;
							}
							for(k=1; k<3 && i+k<BOARD_SIZE; k++) {
								if(board[i+k][j] != player) {
									break;
								}
							}
							
							if(k == 3 && i-1 >= 0 && i+3 < BOARD_SIZE && board[i-1][j] == Player.NONE && board[i+3][j] == Player.NONE) {
								count++;
							}
							
							for(k=1; k<3 && j+k<BOARD_SIZE && i-k>=0; k++) {
								if(board[i-k][j+k] != player) {
									break;
								}
							}
							if(k == 3 && i-3>=0 && i+1 < BOARD_SIZE && j+3 < BOARD_SIZE && j-1 >= 0 && board[i-3][j+3] == Player.NONE && board[i+1][j-1] == Player.NONE) {
								count++;
							}
							for(k=1; k<3 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
								if(board[i+k][j+k] != player) {
									break;
								}
							}
							
							if(k == 3 && i-1 >= 0 && i+3 < BOARD_SIZE && j+3 < BOARD_SIZE && j-1 >= 0 && board[i+3][j+3] == Player.NONE && board[i-1][j-1] == Player.NONE) {
								count++;
							}
						}
					}
				}
				break;
			case DEADTHREE:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player) {
							int k;
							
							for(k=1; k<3 && j+k<BOARD_SIZE; k++) {
								if(board[i][j+k] != player) {
									break;
								}
							}
							if((k == 3 && i-1 >= 0 && i+3 < BOARD_SIZE && board[i-1][j] == Player.NONE && isOpposite(player, i+3, j)) ||
							(k == 3 && i-1 >= 0 && i+3 < BOARD_SIZE && isOpposite(player, i-1, j) && board[i-1][j] == Player.NONE)) {
								count++;
							}
							
							for(k=1; k<3 && i+k<BOARD_SIZE; k++) {
								if(board[i+k][j] != player) {
									break;
								}
							}
							if((k == 3 && j-1 >= 0 && j+3 < BOARD_SIZE && board[i][j-1] == Player.NONE && isOpposite(player, i, j+3)) ||
							(k == 3 && j-1 >= 0 && j+3 < BOARD_SIZE && isOpposite(player, i, j-1) && board[i][j+3] == Player.NONE)) {
								count++;
							}			
							
							for(k=1; k<3 && j+k<BOARD_SIZE && i-k>=0; k++) {
								if(board[i-k][j+k] != player) {
									break;
								}
							}
							if(k == 3 && i-3>=0 && i+1 < BOARD_SIZE && j+3 < BOARD_SIZE && j-1 >= 0 && board[i-3][j+3] == Player.NONE && isOpposite(player, i+1, j-1)) {
								count++;
							}
							
							for(k=1; k<3 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
								if(board[i+k][j+k] != player) {
									break;
								}
							}
							
							if(k == 3 && i-1 >= 0 && i+3 < BOARD_SIZE && j+3 < BOARD_SIZE && j-1 >= 0 && isOpposite(player, i+3, j+3) && board[i-1][j-1] == Player.NONE) {
								count++;
							}
						}
					}
				}
				break;
			case LIVEFOUR:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player) {
							int k;
							
							for(k=1; k<4 && j+k<BOARD_SIZE; k++) {
								if(board[i][j+k] != player) {
									break;
								}
							}
							if(k == 4 && j-1 >= 0 && j+4 < BOARD_SIZE && board[i][j-1] == Player.NONE && board[i][j+4] == Player.NONE) {
								count++;
							}	

							for(k=1; k<4 && i+k<BOARD_SIZE; k++) {
								if(board[i+k][j] != player) {
									break;
								}
							}
							if(k == 4 && i-1 >= 0 && i+4 < BOARD_SIZE && board[i-1][j] == Player.NONE && board[i+4][j] == Player.NONE) {
								count++;
							}
							
							for(k=1; k<4 && j+k<BOARD_SIZE && i-k>=0; k++) {
								if(board[i-k][j+k] != player) {
									break;
								}
							}
							
							
							if(k == 4 && i-4>=0 && i+1 < BOARD_SIZE && j+4 < BOARD_SIZE && j-1 >= 0 && board[i-4][j+4] == Player.NONE && board[i+1][j-1] == Player.NONE) {
								count++;
								System.out.println("got live four");
							}
							
							for(k=1; k<4 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
								if(board[i+k][j+k] != player) {
									break;
								}
							}
							
							if(k == 4 && i-1 >= 0 && i+4 < BOARD_SIZE && j+4 < BOARD_SIZE && j-1 >= 0 && board[i+4][j+4] == Player.NONE && board[i-1][j-1] == Player.NONE) {
								
								count++;
							}
						}
					}
				}
				break;
			case DEADFOUR:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player) {
							int k;
							
							for(k=1; k<4 && j+k<BOARD_SIZE; k++) {
								if(board[i][j+k] != player) {
									break;
								}
							}
							if((k == 4 && j-1 >= 0 && j+4 < BOARD_SIZE && board[i][j-1] == Player.NONE && isOpposite(player, i, j+4)) ||
								(k == 4 && j-1 >= 0 && j+4 < BOARD_SIZE && isOpposite(player, i, j-1) && board[i][j+4] == Player.NONE)) {
										count++;
							}
							
							for(k=1; k<4 && i+k<BOARD_SIZE; k++) {
								if(board[i+k][j] != player) {
									break;
								}
							}
							if((k == 4 && i-1 >= 0 && i+4 < BOARD_SIZE && board[i-1][j] == Player.NONE && isOpposite(player, i+4, j)) ||
								(k == 4 && i-1 >= 0 && i+4 < BOARD_SIZE && isOpposite(player, i-1, j) && board[i-1][j] == Player.NONE)) {
										count++;
							}
							
							for(k=1; k<4 && i+k<BOARD_SIZE && j-k>=0; k++) {
								if(board[i+k][j-k] != player) {
									break;
								}
							}
							if(k == 4 && i-4>=0 && i+1 < BOARD_SIZE && j+4 < BOARD_SIZE && j-1 >= 0 && isOpposite(player, i-4, j+4) && board[i+1][j-1] == Player.NONE) {
								count++;
							}
							
							for(k=1; k<4 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
								if(board[i+k][j+k] != player) {
									break;
								}
							}
							
							if(k == 4 && i-1 >= 0 && i+4 < BOARD_SIZE && j+4 < BOARD_SIZE && j-1 >= 0 && board[i+4][j+4] == Player.NONE && isOpposite(player, i-1, j-1)) {
								count++;
							}
						}
					}
				}
				break;
			case FIVE:
				for(int i=0; i<BOARD_SIZE; i++) {
					for(int j=0; j<BOARD_SIZE; j++) {
						if(board[i][j] == player) {
							int k;
							
							for(k=1; k<5 && j+k<BOARD_SIZE; k++) {
								if(board[i][j+k] != player) {
									break;
								}
							}
							if(k == 5) {
								count++;
							}
							
							for(k=1; k<5 && i+k<BOARD_SIZE; k++) {
								if(board[i+k][j] != player) {
									break;
								}
							}
							if(k == 5) {
								count++;
							}
							
							for(k=1; k<5 && j+k<BOARD_SIZE && i-k>=0; k++) {
								if(board[i-k][j+k] != player) {
									break;
								}
							}
							if(k == 5) {
								count++;
							}
							
							for(k=1; k<5 && i+k<BOARD_SIZE && j+k<BOARD_SIZE; k++) {
								if(board[i+k][j+k] != player) {
									break;
								}
							}
							if(k == 5) {
								count++;
							}
						}
					}
				}
				break;
        }
        return count;		
    }
}

