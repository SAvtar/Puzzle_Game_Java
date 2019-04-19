/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;
import java.util.*;
import java.io.*;

/**
 * The class describe a n-puzzle. The class has methods for showing the puzzle
 * mixing the tiles of the puzzle and move a tile. The class implements the
 * methods that are declared in the interface ITraverse
 * @author Please, enter your name instead of this sentence if you do any changes.
 */
public abstract class Puzzle implements IState {
    private int rowAndColumnSize = 4;
    private int[][] board;
    private Scanner keyboard = new Scanner(System.in);
    private int spaceRow = 0, spaceColumn = 0;
    private Random randomGenerator = new Random();
    private Puzzle parent = null;
    private int depth = 0;
    private int row = 0;
    private int col = 0;
    //Letters l = new Letters();
    
    
    
    /**
     * The constructor initiate a puzzle that has number of tiles.
     * The number of tiles must be 3, 8 or 15. If any other value is given
     * the number of tiles will be set to 8.
     * 
     * @param numberOfTiles the number of tiles that the puzzle shall have
     */
    public Puzzle(int numberOfTiles){
        if (numberOfTiles == 3)
            rowAndColumnSize = 2;
        else if (numberOfTiles == 15)
            rowAndColumnSize = 4;
        else
            rowAndColumnSize = 3;
        
        board = new int[rowAndColumnSize][];
        
        for(int i=0; i<rowAndColumnSize; i++)
            board[i] = new int[rowAndColumnSize];
        
        int tileNumber = 1;
        for (int i=0; i<rowAndColumnSize; i++)
            for(int j=0; j<rowAndColumnSize; j++)
                board[i][j] = tileNumber++;
        
        spaceRow = rowAndColumnSize - 1;
        spaceColumn = rowAndColumnSize - 1;
        board[spaceRow][spaceColumn] = 0;
        
    }
    
    /**
     * The constructor initiate a puzzle with the content that are read in
     * via the scanner.
     * @param scanner The scanner has to be connected to source that contains
     * the state of a puzzle.
     */
    public Puzzle(Scanner scanner) {                    
        int numberOfTiles = scanner.nextInt();

        if (numberOfTiles == 3)
            rowAndColumnSize = 2;
        else if (numberOfTiles == 15)
            rowAndColumnSize = 4;
        else
            rowAndColumnSize = 3;

        board = new int[rowAndColumnSize][];

        for(int i=0; i<rowAndColumnSize; i++)
            board[i] = new int[rowAndColumnSize];

        for (int i=0; i<rowAndColumnSize; i++) {
            for(int j=0; j<rowAndColumnSize; j++){
                int tileNumber = scanner.nextInt();
                board[i][j] = tileNumber;

                if (tileNumber == 0){
                    spaceRow = i;
                    spaceColumn = j;
                }
            }
        }
    }

    /**
     * The constructor initiate a puzzle to be a copy of the original.
     * @param original The original to the new puzzle. 
     */
    private Puzzle(Puzzle original) {
        this.rowAndColumnSize = original.rowAndColumnSize;
        
        board = new int[rowAndColumnSize][];
        
        for(int i=0; i<rowAndColumnSize; i++)
            board[i] = new int[rowAndColumnSize];
        
        for (int i=0; i<rowAndColumnSize; i++)
            for(int j=0; j<rowAndColumnSize; j++)
                board[i][j] = original.board[i][j];
        
        spaceRow = original.spaceRow;
        spaceColumn = original.spaceColumn;
        parent = original.parent;
        depth = original.depth;
    }
    
   /**
     * The method return true if two puzzle object are equal, otherwise false.
     * @param anObject The object that this object shall be compered with.
     * The method is called by any containers method contains. Example of
     * containers is Stack, Queue and LinkedList, see the documentation of
     * package java.util for more information about container classes.
     * @return true if this object is equal to anObject, otherwise false.
     */
    @Override
    public boolean equals(Object anObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * The method print out the board of tiles
     */
    public void show(){
        System.out.println("Depth = " + depth);
        
        for (int i=0; i<rowAndColumnSize; i++)
        {
            for(int j=0; j<rowAndColumnSize; j++){
                if (board[i][j] == 0)
                    System.out.print("  _");
                else if (board[i][j] <= 9)
                    System.out.print("  " + board[i][j]);
                else
                    System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * The method makes it possible for a person to move around tiles
     */
    public void solveByHuman() {
        String prompt = "Move the space, 0 left, 1 up, 2 right, 3 down, 10 quit: ";
        System.out.print(prompt);
        int direction = keyboard.nextInt();
        
        while (direction != 10){
            moveSpace(direction);
            show();
            System.out.print(prompt);
            direction = keyboard.nextInt();            
        } 
    }

    /**
     * The method move the space on step in the direction that is sent to the
     * method. The parameter direction can have for different values;
     * 0 moves the space one step left if it is possible.
     * 1 moves the space one step up if it is possible.
     * 2 moves the space one step right if it is possible.
     * 3 moves the space one step down if it is possible.
     * 
     * @param direction the direction to move 
     */
    private void moveSpace(int direction){
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        currentPosOfCursor(board);
        if(direction >= 0 && direction <= 3){
            if(direction == 0){
                if(col - 1 >= 0){
                    swap(row, col, row, --col);
                }
            }
            else if(direction == 1){
                if(row - 1 >= 0){
                    swap(row, col, --row, col);
                }
            }
            else if(direction == 2){
                if(col + 1 <= spaceColumn){
                    swap(row, col, row, ++col);
                }
            }
            else if(direction == 3){
                if(row + 1 <= spaceRow){
                    swap(row, col, ++row, col);
                }
            }
        }
    }

    /**
     * The method swap the value of board[i][j] with the value of board[k][l].
     * 
     * @param i row index for one element of the board.
     * @param j column index for one element of the board.
     * @param k row index for another element of the board.
     * @param l column index for another element of the board.
     */
    private void swap(int i, int j, int k, int l) {
        int number = board[i][j];
        board[i][j] = board[k][l];
        board[k][l] = number;
    }

    /**
     * The method shuffle the tiles of the puzzle.
     */
    public void shuffle() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (int i = board.length - 1; i > 0; i--) {
        for (int j = board[i].length - 1; j > 0; j--) {
            int m = randomGenerator.nextInt(i + 1);
            int n = randomGenerator.nextInt(j + 1);

//            int temp = board[i][j];956

//            board[i][j] = board[m][n];
//            board[m][n] = temp;
            swap(i, j, m, n);
        }
    }
    }

   /**
     * The method create the children and return the references to them in a
     * array with 4 element. Some of the elements can have the value null if
     * it not was possible to create all children. 
     * @return An array with children, some of the elements can be null. 
     */
    @Override
    public IState[] createChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.      
        //return l.createChildren();
    }

    /**
     * Any children will not be created if the parents depth is equal to the
     * depthBound.
     * @param depthBound Maximal depth for any node.
     * @return the created children
     */
    @Override
    public IState[] createChildren(int depthBound) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //return l.createChildren(depthBound);
    }

     /**
     * The method shall return a reference to the parent state or null
     * if the state has no parent.
     * @return A reference to the parent.
     */
    @Override
    public IState getParent() {
        if(parent != null)
            return parent;
        return parent;
    }

    /**
     * The method check if it is possible to move the space in a direction.
     * @param direction The direction to move the space.
     * @return true if it possible to move the space, otherwise false.
     */
    private boolean isSpacePossibleToMove(int direction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void currentPosOfCursor(int boardToCheck[][]){
        for(int i = 0; i < boardToCheck.length; i++){
            for(int j = 0; j < boardToCheck.length; j++){
                if(boardToCheck[i][j] == 0){
                    row = i;
                    col = j;
                    break;
                }
            }
        }
    }
}
