package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Sudoku {
	static int[][] savedOriginalBoard;
    static boolean isFullSolution(int[][] board) {
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(board[y][x]==0)
    			{
    				return false;
    			}
    		}
    	}
        return true;
    }

    static boolean reject(int[][] board) {
    	ArrayList<Integer> numberChecks = new ArrayList<Integer>();
    	for(int i=1; i<=9; i++)
    	{
    		numberChecks.add(i);
    	}
    	//Check the columns for duplicate values
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(numberChecks.contains((Integer)board[x][y]))
    			{
    				numberChecks.remove((Integer)board[x][y]);
    			}
    			else
    			{
    				if(board[x][y]!=0)
    				{
    					return true;
    				}
    			}
    		}
    		numberChecks= new ArrayList<Integer>();
    		for(int i=1; i<=9; i++)
        	{
        		numberChecks.add(i);
        	}
    	}
		numberChecks= new ArrayList<Integer>();
		for(int i=1; i<=9; i++)
    	{
    		numberChecks.add(i);
    	}
    	//Check rows for duplicate values
    	for(int x=0; x<9; x++)
    	{
    		for(int y=0; y<9; y++)
    		{
    			if(numberChecks.contains((Integer)board[x][y]))
    			{
    				numberChecks.remove((Integer)board[x][y]);
    			}
    			else
    			{
    				if(board[x][y]!=0)
    				{
    					return true;
    				}
    			}
    		}
    		numberChecks= new ArrayList<Integer>();
    		for(int i=1; i<=9; i++)
        	{
        		numberChecks.add(i);
        	}
    	}
    	//Check for duplicates in a 3x3 grid
    	for(int i=0; i<9; i++)
    	{
    		int x=0, y=0, xb=0, yb=0;
    		if(i==0)
    		{
    			x=0;
    			y=0;
    		}
    		if(i==1)
    		{
    			x=3;
    			y=0;
    		}
    		if(i==2)
    		{
    			x=6;
    			y=0;
    		}
    		if(i==3)
    		{
    			x=0;
    			y=3;
    		}
    		if(i==4)
    		{
    			x=3;
    			y=3;
    		}
    		if(i==5)
    		{
    			x=6;
    			y=3;
    		}
    		if(i==6)
    		{
    			x=0;
    			y=6;
    		}
    		if(i==7)
    		{
    			x=3;
    			y=6;
    		}
    		if(i==8)
    		{
    			x=6;
    			y=6;
    		}
    		int xStore =x;
    		xb=x+3;
    		yb=y+3;
    		for(;y<yb;y++)
    		{
    			for(x = xStore;x<xb;x++)
    			{
    				if(numberChecks.contains((Integer)board[y][x]))
    				{
    					numberChecks.remove((Integer)board[y][x]);
    				}
    				else
    				{
    					if(board[y][x]!=0)
    					{
    						return true;
    					}
    				}
    			}
    		}
    		numberChecks= new ArrayList<Integer>();
    		for(int j=1; j<=9; j++)
        	{
        		numberChecks.add(j);
        	}
    	}
    	return false;
    }

    static int[][] extend(int[][] board) {
    	int[][] newBoard = new int[9][9];
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			newBoard[y][x] = board[y][x];
    		}
    	}
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(newBoard[y][x]==0)
    			{
    				newBoard[y][x]=1;
    				return newBoard;
    			}
    		}
    	}
    	return null;
    }

    static int[][] next(int[][] board) {
    	int[][] newBoard = new int[9][9];
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			newBoard[y][x]=board[y][x];
    		}
    	}
    	for(int y=8; y>=0; y--)
    	{
    		for(int x=8; x>=0; x--)
    		{
    			if((newBoard[y][x]!=savedOriginalBoard[y][x]) && (newBoard[y][x]!=0))
    			{
    				int temp = newBoard[y][x];
    				temp++;
    				if(temp==10)
    				{
    					return null;
    				}
    				newBoard[y][x] = temp;
    				return newBoard;
    			}
    		}
    	}
    	return null;
    }

    static void testIsFullSolution() {
    	System.out.println("-----isFull()-----");
    	int[][] testBoard = readBoard("easy.su");
    	int[][] testBoard2 = readBoard("easy.su");
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(testBoard2[x][y]==0)
    			{
    				testBoard2[x][y]=1;
    			}
    		}
    	}
		System.out.println("This board is full and it returns " + isFullSolution(testBoard2));
    	System.out.println("This board is not full and it returns " + isFullSolution(testBoard));
    }

    static void testReject() {
    	System.out.println("-----reject()-----");
    	int[][] testBoard = readBoard("easy.su");
    	int[][] testBoard2 = readBoard("easy.su");
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(testBoard2[x][y]==0)
    				testBoard2[x][y]=1;
    		}
    	}
    	System.out.println("This board has duplicate values in the same rows/columns and it returns " + reject(testBoard2));
    	printBoard(testBoard2);
    	System.out.println("This board does not have duplicate values in the same rows/columns and it returns " + reject(readBoard("easy.su")));
    	printBoard(testBoard);
    }

    static void testExtend() {
    	System.out.println("-----extend()-----");
    	int[][] testBoard = readBoard("easy.su");
    	int[][] testBoard2 = readBoard("easy.su");
    	for(int y=0; y<9; y++)
    	{
    		for(int x=0; x<9; x++)
    		{
    			if(testBoard2[x][y]==0)
    				testBoard2[x][y]=1;
    		}
    	}
    
    	System.out.println("This board was not full and generated a new partial solution ");
    	printBoard(extend(testBoard));
    	System.out.println("This board was full and should have returned null " + extend(testBoard2));
    	
    }

    static void testNext() {
    	System.out.println("-----next()-----");
    	int[][] testBoard2 = readBoard("easy.su");
    	System.out.println("Here is  plain board, with nothing added");
    	printBoard(testBoard2);
    	testBoard2[0][0] = 2;
    	System.out.println("\nThe same board but with a 2 added in the front cell");
    	printBoard(testBoard2);
    	System.out.println("\nThe same board but that 2 should now be 3 if next() worked correctly");
    	testBoard2 = next(testBoard2);
    	printBoard(testBoard2);
    	System.out.println();
    	System.out.println("Now a 3 was added in address (5, 7) in test board, it should be a 4");
    	testBoard2[7][5] = 3;
    	testBoard2 = next(testBoard2);
    	printBoard(testBoard2);
    	System.out.println();
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        savedOriginalBoard = new int[9][9];
       for(int y=0; y<9; y++)
       {
    	   for(int x=0; x<9; x++)
    	   {
    		  savedOriginalBoard[y][x] = board[y][x];
    	   }
       }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            printBoard(board);
            System.out.println();
            printBoard(solve(board));
        }
    }
}

