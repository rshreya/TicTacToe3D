
/*
 Various routines for working with the key arrays: board, lines and sums in one 
 possible implementation of the TTT3D project. 
 
 Allows the computer to play 4x4x4 3D Tic Tac Toe with some strategy against user input and graphically
 shows the board. 
 
 Code was modified and derived from the provided sample code found at /afs/cats.ucsc.edu/courses/cmps012a-cm/pa5
 Authors: Delbert Bailey and Shreya Radesh (sradesh@ucsc.edu) 11/23/2016
 */
import java.io.*;

import java.util.*;
public class BoardTest{
	static Scanner scan = new Scanner(System.in);

	static int numCMove = 1;
    static int[][][] board = new int[4][4][4];
    static int[] sums = new int[76];
    static Random rand = new Random();
   // static boolean userWins = false;
   // static boolean compWins = false;
    static boolean draw = false;
    static final int[][][] lines = {
    	{{0,0,0},{0,0,1},{0,0,2},{0,0,3}},  //lev 0; row 0   rows in each level     
    	{{0,1,0},{0,1,1},{0,1,2},{0,1,3}},  //       row 1     
    	{{0,2,0},{0,2,1},{0,2,2},{0,2,3}},  //       row 2     
    	{{0,3,0},{0,3,1},{0,3,2},{0,3,3}},  //       row 3     
    	{{1,0,0},{1,0,1},{1,0,2},{1,0,3}},  //lev 1; row 0     
    	{{1,1,0},{1,1,1},{1,1,2},{1,1,3}},  //       row 1     
    	{{1,2,0},{1,2,1},{1,2,2},{1,2,3}},  //       row 2     
    	{{1,3,0},{1,3,1},{1,3,2},{1,3,3}},  //       row 3     
	{{2,0,0},{2,0,1},{2,0,2},{2,0,3}},  //lev 2; row 0     
	{{2,1,0},{2,1,1},{2,1,2},{2,1,3}},  //       row 1     
	{{2,2,0},{2,2,1},{2,2,2},{2,2,3}},  //       row 2       
	{{2,3,0},{2,3,1},{2,3,2},{2,3,3}},  //       row 3     
	{{3,0,0},{3,0,1},{3,0,2},{3,0,3}},  //lev 3; row 0     
	{{3,1,0},{3,1,1},{3,1,2},{3,1,3}},  //       row 1 
	{{3,2,0},{3,2,1},{3,2,2},{3,2,3}},  //       row 2       
	{{3,3,0},{3,3,1},{3,3,2},{3,3,3}},  //       row 3           
	{{0,0,0},{0,1,0},{0,2,0},{0,3,0}},  //lev 0; col 0   columns in each level  
	{{0,0,1},{0,1,1},{0,2,1},{0,3,1}},  //       col 1    
	{{0,0,2},{0,1,2},{0,2,2},{0,3,2}},  //       col 2    
	{{0,0,3},{0,1,3},{0,2,3},{0,3,3}},  //       col 3    
	{{1,0,0},{1,1,0},{1,2,0},{1,3,0}},  //lev 1; col 0     
	{{1,0,1},{1,1,1},{1,2,1},{1,3,1}},  //       col 1    
	{{1,0,2},{1,1,2},{1,2,2},{1,3,2}},  //       col 2    
	{{1,0,3},{1,1,3},{1,2,3},{1,3,3}},  //       col 3    
	{{2,0,0},{2,1,0},{2,2,0},{2,3,0}},  //lev 2; col 0     
	{{2,0,1},{2,1,1},{2,2,1},{2,3,1}},  //       col 1    
	{{2,0,2},{2,1,2},{2,2,2},{2,3,2}},  //       col 2    
	{{2,0,3},{2,1,3},{2,2,3},{2,3,3}},  //       col 3    
	{{3,0,0},{3,1,0},{3,2,0},{3,3,0}},  //lev 3; col 0     
	{{3,0,1},{3,1,1},{3,2,1},{3,3,1}},  //       col 1
	{{3,0,2},{3,1,2},{3,2,2},{3,3,2}},  //       col 2
	{{3,0,3},{3,1,3},{3,2,3},{3,3,3}},  //       col 3
    {{0,0,0},{1,0,0},{2,0,0},{3,0,0}},  //cols in vert plane in front
    {{0,0,1},{1,0,1},{2,0,1},{3,0,1}},
    {{0,0,2},{1,0,2},{2,0,2},{3,0,2}},
    {{0,0,3},{1,0,3},{2,0,3},{3,0,3}},
    {{0,1,0},{1,1,0},{2,1,0},{3,1,0}},  //cols in vert plane one back
    {{0,1,1},{1,1,1},{2,1,1},{3,1,1}},
    {{0,1,2},{1,1,2},{2,1,2},{3,1,2}},
    {{0,1,3},{1,1,3},{2,1,3},{3,1,3}},
    {{0,2,0},{1,2,0},{2,2,0},{3,2,0}},  //cols in vert plane two back
    {{0,2,1},{1,2,1},{2,2,1},{3,2,1}},
    {{0,2,2},{1,2,2},{2,2,2},{3,2,2}},
    {{0,2,3},{1,2,3},{2,2,3},{3,2,3}},
    {{0,3,0},{1,3,0},{2,3,0},{3,3,0}},  //cols in vert plane in rear
    {{0,3,1},{1,3,1},{2,3,1},{3,3,1}},
    {{0,3,2},{1,3,2},{2,3,2},{3,3,2}},
    {{0,3,3},{1,3,3},{2,3,3},{3,3,3}},
     	{{0,0,0},{0,1,1},{0,2,2},{0,3,3}},  //diags in lev 0
     	{{0,3,0},{0,2,1},{0,1,2},{0,0,3}},
        {{1,0,0},{1,1,1},{1,2,2},{1,3,3}},  //diags in lev 1
        {{1,3,0},{1,2,1},{1,1,2},{1,0,3}},
        {{2,0,0},{2,1,1},{2,2,2},{2,3,3}},  //diags in lev 2
        {{2,3,0},{2,2,1},{2,1,2},{2,0,3}},
        {{3,0,0},{3,1,1},{3,2,2},{3,3,3}},  //diags in lev 3
        {{3,3,0},{3,2,1},{3,1,2},{3,0,3}},
        {{0,0,0},{1,0,1},{2,0,2},{3,0,3}},  //diags in vert plane in front
        {{3,0,0},{2,0,1},{1,0,2},{0,0,3}},
        {{0,1,0},{1,1,1},{2,1,2},{3,1,3}},  //diags in vert plane one back
        {{3,1,0},{2,1,1},{1,1,2},{0,1,3}},
        {{0,2,0},{1,2,1},{2,2,2},{3,2,3}},  //diags in vert plane two back
        {{3,2,0},{2,2,1},{1,2,2},{0,2,3}},
        {{0,3,0},{1,3,1},{2,3,2},{3,3,3}},  //diags in vert plane in rear
        {{3,3,0},{2,3,1},{1,3,2},{0,3,3}},
        {{0,0,0},{1,1,0},{2,2,0},{3,3,0}},  //diags left slice      
        {{3,0,0},{2,1,0},{1,2,0},{0,3,0}},        
        {{0,0,1},{1,1,1},{2,2,1},{3,3,1}},  //diags slice one to right
        {{3,0,1},{2,1,1},{1,2,1},{0,3,1}},        
        {{0,0,2},{1,1,2},{2,2,2},{3,3,2}},  //diags slice two to right      
        {{3,0,2},{2,1,2},{1,2,2},{0,3,2}},        
        {{0,0,3},{1,1,3},{2,2,3},{3,3,3}},  //diags right slice      
        {{3,0,3},{2,1,3},{1,2,3},{0,3,3}},        
        {{0,0,0},{1,1,1},{2,2,2},{3,3,3}},  //cube vertex diags
        {{3,0,0},{2,1,1},{1,2,2},{0,3,3}},
        {{0,3,0},{1,2,1},{2,1,2},{3,0,3}},
        {{3,3,0},{2,2,1},{1,1,2},{0,0,3}}        
    };
    //utility display game array
    
    
    //checks if there is a win for the user
    static boolean userWins(){
    	compLineSums();
    	for (int i = sums.length-1; i>=0; i--){
    		if (sums[i] == 20){
    			return true;
    		}
    	}
    	return false;
    }
    //checks if there is a win for the computer
    static boolean compWins(){
    	compLineSums();
    	for (int i = sums.length-1; i>=0; i--){
    		if (sums[i] == 4){
    			return true;
    		}
    	}
    	return false;
    }
    //checks if there is a draw situation. Always run this method after checking both compWins() and userWins() first
    static boolean draw(){
		compLineSums();
		boolean temp = false;
		for (int i = sums.length-1; i>=0; i--){
    		if (sums[i] == 7 || sums[i]== 11){
    			temp = true;
    			//break;
    		} else {
    			temp = false;
    		}
    	}
		return temp;
	}

    //prints the board
    static void printBoard(){
	for (int lev=3; lev >= 0; lev--){
	    for (int row=3; row >= 0; row--){
	        for (int i = row; i > 0; i--){
	    		System.out.print(" ");
	    	}
	    	System.out.printf("%1d", lev);
	    	System.out.printf("%1d", row);
	    	System.out.print(" ");
		for (int col=0; col<4; col++){
			if (board[lev][row][col] == 5){
				System.out.printf("%c", 'X');
				System.out.print(" ");
			} else if (board[lev][row][col] == 1){
				System.out.printf("%c", 'O');
				System.out.print(" ");
			} else {
				System.out.printf("%c", '_');
				System.out.print(" ");
			}
		    //System.out.printf("%1d ", board[lev][row][col]);
		}
		System.out.printf("\n");
	    }
	    System.out.printf("\n");
	}
	System.out.println("  0 1 2 3");
	System.out.printf("\n");
    }
    
    
    //utility display sums array
    static void printSums(){
	for (int i=0; i<sums.length; i++){
	    System.out.println("line " +i+ "is " + sums[i]);
	}
    }

    //is a cell at specified adr empty
    static boolean isEmpty(int[] celAdr){
	if (board[celAdr[0]][celAdr[1]][celAdr[2]] == 0){
	    return true;
	}
	else{
	    return false;
	}
    }

    //place a move on the board by cell adr array
    static void move(int[] celAdr, int val){
	move(celAdr[0], celAdr[1], celAdr[2], val);
    }

    //place a move on the board explicit coordinates
    static void move(int lev, int row, int col, int val){
	board[lev][row][col] = val;
    }

    //clear board to a value
    static void setAll(int val){
	for (int lev = 0; lev < 4; lev++){
	    for (int row = 0; row < 4; row++){
		for (int col = 0; col < 4; col++){
		    move(lev, row, col, val);
		}
	    }
	}
    }

    //are two cell addresses the same
    static boolean isEqual(int[] a, int[] b){
	for (int i=0; i<a.length; i++){
	    if (a[i] != b[i]){
		return false;
	    }
	}
	    return true;
    }

    //fine empty cell in a line
    static int[] findEmptyCel(int[][]line){
	for (int i=0; i<4; i++){
	    if ( isEmpty(line[i]) ) return line[i];
	}
	return null;
    }
		 

    //find common empty cell to two lines
    static int[] findComMtCel(int[][]line1, int[][]line2){
	for (int i=0; i<line1.length; i++ ){
	    for (int j=0; j<line1.length; j++ ){
		if (isEqual(line1[i], line2[j]) &&
		    isEmpty(line1[i]) &&
		    isEmpty(line2[j])) {

		    return line1[i];
		}
	    }
	}
	return null;
    }

    // find the sums of all the lines and place them in the sums[] array
    static void compLineSums(){
	for (int i=0; i<sums.length; i++){
	    sums[i] = 0;
	    for (int j=0; j<4; j++){
		sums[i] += board[lines[i][j][0]]
		                [lines[i][j][1]]
		                [lines[i][j][2]];
	    }
	}
    }

    //find line with a specific sum
    static int findLineSum(int sum){
	for (int i=0; i<sums.length; i++){
	    if (sums[i] == sum) return i; // line i has the sum
	}
	return -1; //no such sum found
    }
    
    //allows the user to move
	static void userMove(){
		System.out.println("Type your move as one three digit number(lrc)");
		int x = 0;
		int y = 0;
		int z = 0;
		String userIn = scan.next();
		System.out.println("Scanner read " + userIn);
		x = (int)userIn.charAt(0)-48;
		y = (int)userIn.charAt(1)-48;
		z = (int)userIn.charAt(2)-48;
		if (board[x][y][z]==0){ //if spot is empty, allow user to move there
			board[x][y][z] = 5; //sets the user's specified place to 5
			printBoard();
		} else {
			System.out.println("Not a valid move."); //if spot is full, notify user 
			userMove();	
			
		}
		
	}
	
	//AI move
	static void compMove(){
		if (numCMove < 3){ // try to place first two moves in the middle cube to maximize line possibilities
			if (board[1][1][1] == 0){
				board[1][1][1] = 1;
				
			} else if (board[1][1][2] == 0) {board[1][1][2] = 1;
			} else if (board[1][2][1] == 0) {board[1][2][1] = 1;
			} else if (board[1][2][2] == 0) {board[1][2][2] = 1;
			} else if (board[2][1][1] == 0) {board[2][1][1] = 1;
			} else if (board[2][1][2] == 0) {board[2][1][2] = 1;
			} else if (board[2][2][1] == 0) {board[2][2][1] = 1;
			} else if (board[2][2][2] == 0) {board[2][2][2] = 1;}
			
		} else {
			compLineSums();
			if (blockWin() == 0){ //if there is no win to be blocked, check if a line can be completed for the computer
				if (completeLine() == 0){ // if no line can be completed for the computer, check if a fork can be created for user
					if (userFork() == 0){//if no fork can be created for user, check if fork can be created for computer
						if (compFork() == 0){ //if no fork can be created for computer, check if there are 2 in a row for comp
							if (fillFor3() == 0){//if no two in a row for comp, check if there is one in a row for comp
								if (fillFor2() == 0){//if no one in a row (impossible), randomly place a marker somewhere for comp
									if (draw() == false){
										fillRandEmpty();
									}
								}
							}
						}
					}
				}
			}
		}
		
		numCMove++;
		printBoard();
	}
	
	//block a win for the user, return 1 if a move was blocked, 0 if no move to be blocked
	static int blockWin(){
		for (int i = sums.length-1; i >=0; i--){
			if (sums[i] == 15){
    			//return true;
    			//find empty cell; fill that empty cell
    			int[] tempArr = findEmptyCel(lines[i]);
    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
    			return 1;
    			//break;
    		} 
		}
		return 0;
	}
	
	//create a fork for the computer, return 1 if a fork was created, 0 if not
	static int compFork(){
		//int[] maybeFork
		ArrayList<Integer> maybeFork = new ArrayList<Integer>();
		for (int i = sums.length-1; i >= 0; i--){
			if (sums[i] == 2){
				maybeFork.add(i);
			}
		}
		for (int i = maybeFork.size()-1; i >= 0; i--){
			for (int j = 0; j < i; j++){
				if (findComMtCel(lines[i], lines[j])!= null){
					int[] tempArr = findComMtCel(lines[i], lines[j]);
	    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
	    			return 1;
				}
			}
		}
		return 0;
	}
	
	//basically try to create 3 in a row, return 1 if 3 in a row was created for comp, 0 if not
	static int fillFor3(){
		for (int i = sums.length-1; i >=0; i--){
			if (sums[i] == 2){
    			//find empty cell; fill that empty cell
    			int[] tempArr = findEmptyCel(lines[i]);
    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
    			return 1;
    			
    		} 
		}
		return 0;
	}
	
	//try to create 2 in a row, return 1 if 2 in a row was created for comp, 0 if not
	static int fillFor2(){
		for (int i = sums.length-1; i >=0; i--){
			if (sums[i] == 1){
    			//find empty cell; fill that empty cell
    			int[] tempArr = findEmptyCel(lines[i]);
    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
    			return 1;
    			
    		} 
		}
		return 0;
	}
	
	//check if a fork can be created for the user and block it if there is. return 1 if a fork was blocked, 0 if no fork to be blocked
	static int userFork(){
		//int[] maybeFork
		ArrayList<Integer> maybeFork = new ArrayList<Integer>();
		for (int i = sums.length-1; i >= 0; i--){
			if (sums[i] == 10){
				maybeFork.add(i);
			}
		}
		for (int i = maybeFork.size()-1; i >= 0; i--){
			for (int j = 0; j < i; j++){
				if (findComMtCel(lines[i], lines[j])!= null){
					int[] tempArr = findComMtCel(lines[i], lines[j]);
	    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
	    			return 1;
				}
			}
		}
		return 0;
	}
	
	// check if a line can be completed for the computer and complete it if there is. return 1 if line exists and was completed, 
	//0 if no such line exists
	static int completeLine(){
		for (int i = sums.length-1; i >=0; i--){
			if (sums[i] == 3){
    			//return true;
    			//find empty cell; fill that empty cell
    			int[] tempArr = findEmptyCel(lines[i]);
    			board[tempArr[0]][tempArr[1]][tempArr[2]] = 1;
    			return 1;
    			//break;
    		} 
		}
		return 0;
	}
	//checks if game is over
	static boolean gameOver(){
		if (userWins() == true || compWins() == true || draw() == true){
		
			return true;
		} else {
			return false;
		}
	}
	//randomly fill an empty cell with a marker for the computer
	static void fillRandEmpty(){
		boolean foundEmpty = false;
		while (foundEmpty == false){
			int a = rand.nextInt(4);
			int b = rand.nextInt(4);
			int c = rand.nextInt(4);
			int [] tempArray = {a,b,c};
			if (isEmpty(tempArray) == true){
				board[a][b][c] = 1;
				//return 0;
				foundEmpty = true;
			} 
		}
	}

    //main routine for testing
    public static void main(String[] args){
    	
    	
    	
    	printBoard();
    	while (gameOver() == false){
    		userMove();
    		compMove();
    	}
    	System.out.println("Game Over!");
    	if (compWins() == true){
    		System.out.println("Computer Wins!");
    	} else if (userWins() == true){
    		System.out.println("You win!");
    	} else if (draw() == true){
    		System.out.println("No playable moves-- game ended in a draw");
    	}


    }
    

 

	
}




