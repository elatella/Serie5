/* ************************************************************************* *\
*                Programmierung 1 HS 2018 - Serie 5-1                         * 
\* ************************************************************************* */

import java.util.Arrays;
import java.util.Scanner;


public class VierGewinnt
{

    public static final int COLS = 7;
    public static final int ROWS = 6;

    private Token[][] board = new Token[ COLS ][ ROWS ]; // 7 columns with 6 fields each
    private IPlayer[] players = new IPlayer[ 2 ]; // two players

    /** initialize board and players and start the game */
    public void play()
    {
        // initialize the board
        for ( Token[] column : this.board ) {
            Arrays.fill( column, Token.empty );
        }

        /* initialize players */
        players[ 0 ] = new HumanPlayer();
        System.out.print( "Play against a human opponent? (y / n) " );
        String opponent = new Scanner( System.in ).nextLine().toLowerCase();
        while ( ( 1 != opponent.length() ) || ( -1 == ( "yn".indexOf ( opponent ) ) ) ) {
            System.out.print( "Can't understand your answer. Play against a human opponent? (y / n) " );
            opponent = new Scanner( System.in ).nextLine().toLowerCase();
        }
        if ( opponent.equals( "y" ) ) {
            players[ 1 ] = new HumanPlayer();
        } else {
            players[ 1 ] = new ComputerPlayer();
        }
        players[ 0 ].setToken( Token.player1 );
        players[ 1 ].setToken( Token.player2 );

        /* play... */
        boolean solved = false;
        int currentPlayer = ( new java.util.Random() ).nextInt( 2 );  //choose randomly who begins
        System.out.println( "current player: " + currentPlayer );
        int insertCol, insertRow; // starting from 0
        while ( !solved && !this.isBoardFull() ) {
            // get player's next "move"
            // note that we pass only a copy of the board as an argument,
            // otherwise the player would be able to manipulate the board and cheat!
            insertCol = players[ currentPlayer ].getNextColumn( getCopyOfBoard() );
            // insert the token and get the row where it landed
            insertRow = this.insertToken( insertCol, players[ currentPlayer ].getToken() );
            // check if the game is over
            solved = this.checkVierGewinnt( insertCol, insertRow );
            //switch to other player
            if ( !solved )
                currentPlayer = ( currentPlayer + 1 ) % 2;
        }
        System.out.println( displayBoard( this.board ) );
        if ( solved )
            System.out.println( "Player " + players[ currentPlayer ].getToken() + " wins!" );
        else
            System.out.println( "Draw! Game over." );
    }


    /**
     * Inserts the token at the specified column (if possible)
     * @param column the column to insert the token
     * @param token the players token
     * @return the row where the token landed 
     */
    private int insertToken( int column, Token tok )
    {
     //TODO: Your code goes here
        int depth = 0;
        if (0 <= column && column <= COLS)
    
        {
         String a = board[column][depth].toString();
  
         while (a.equals (" ")
            && depth <= ROWS)
            {depth++;}
         board [column][depth -1] = tok;
         }
        else
        {System.exit(1);}
    
     return depth -1; //TODO: Replace this line
    
    }


    /**
     * Checks if every position is occupied 
     * @returns true, if the board is full.
     */
    private boolean isBoardFull()
    {
        //TODO: Your code goes here
        
        int depth = 0;
        for (int column = 0; column < COLS ; column ++) 
        {
                    if ((board[column][depth].toString()).equals(" ")) 
                        {
                            return false;
                        }
                    
        }
        return true;
        
    }


    /**
     * Checks for at least four equal tokens in a row in
     * either direction, starting from the given position. 
     */
    private boolean checkVierGewinnt( int col, int row )
    {
        //TODO: Your code goes here
        //private boolean checkForWin(int x, int y, int z){
        //return newBoard().get(x).equals(newBoard().get(y)) && newBoard().get(y).equals(newBoard().get(z)); 
    
        //column 4 equals
        for (int depth = 0; depth < ROWS ; depth ++) {
            for (int column = 0; column < COLS ; column ++) 
            {
                    if ((board[column][depth].toString()).equals(" ")) 
                        {
                            return true;
                        }
                    
            }
        }
        
        //rows 4 equals
        for (int column = 0; column < COLS ; column ++) {
            for (int depth = 0; depth < ROWS ; depth ++) 
            {
                    if ((board[column][depth].toString()).equals(" ")) 
                        {
                            return true;
                        }
                    
            }
        }
        
        //one way vertical
        
	int col1, row1, coloff;
	int xwin = 0, owin = 0;
	for (col1 = 0; col1 < COLS - 3; col1++)
	{
		coloff = 0;
		for (row1 = ROWS - 1; row1 > 0; row1--)
		{
			if (board[col1 + coloff][row1].toString().equals("X"))
			{
				owin = 0;
				xwin++;
			} else if (board[col1 + coloff][row1].toString().equals("O"))
			{
				owin++;
				xwin = 0;
			} else
			{
				owin = xwin = 0;
			}
			coloff++;

			if (xwin >= 4)
			{
				return 'X';
			} else if (ywin >= 4)
			{
				return 'O';
			} else
			{
				return NULL;
			}
		}
	}
	
	//other way vertical
	
	for (col1 = COLS; col1 > 3; col1--)
	{
		coloff = 0;
		for (row1 = ROWS - 1; row1 > 0; row1--)
		{
			if (board[col1 - coloff][row1].toString().equals("X"))
			{
				owin = 0;
				xwin++;
			} else if (board[col1 - coloff][row1].toString().equals("O"))
			{
				owin++;
				xwin = 0;
			} else
			{
				owin = xwin = 0;
			}
			coloff++;

			if (xwin >= 4)
			{
				return 'X';
			} else if (ywin >= 4)
			{
				return 'O';
			} else
			{
				return NULL;
			}
		}
	}

        
        return false; //TODO: Replace this line!
        }


    /** Returns a (deep) copy of the board array */
    private Token[][] getCopyOfBoard()
    {
        Token[][] copiedBoard = new Token[ COLS ][ ROWS ];
        for ( int i = 0; i < copiedBoard.length; i++ ) {
            for ( int j = 0; j < copiedBoard[ i ].length; j++ ) {
                copiedBoard[ i ][ j ] = this.board[ i ][ j ];
            }
        }
        return copiedBoard;
    }


    /** returns a graphical representation of the board */
    public static String displayBoard( Token[][] myBoard )
    {
        String rowDelimiter = "+";
        String rowNumbering = " ";
        for ( int col = 0; col < myBoard.length; col++ ) {
            rowDelimiter += "---+";
            rowNumbering += " " + ( col + 1 ) + "  ";
        }
        rowDelimiter += "\n";

        String rowStr;
        String presentation = rowDelimiter;
        for ( int row = myBoard[ 0 ].length - 1; row >= 0; row-- ) {
            rowStr = "| ";
            for ( int col = 0; col < myBoard.length; col++ ) {
                rowStr += myBoard[ col ][ row ].toString() + " | ";
            }
            presentation += rowStr + "\n" + rowDelimiter;
        }
        presentation += rowNumbering;
        return presentation;
    }



    /** main method, starts the program */
    public static void main( String args[] )
    {
        VierGewinnt game = new VierGewinnt();
        game.play();
    }
}
