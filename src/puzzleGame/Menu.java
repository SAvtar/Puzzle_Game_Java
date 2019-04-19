/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzleGame;
import state.Letters;
import state.Puzzle;
import traverse.DepthFirst;
import traverse.BreadthFirst;
import state.IState;
import java.util.*;
import java.io.*;

/**
 * The class is a menu-system. To run the system call the method run.
 * 
 * @author Please, enter your name instead of this sentence!
 */
class Menu {
    private Scanner keyboard = new Scanner(System.in);
    private Puzzle puzzle = null;
    private Solver solver = new Solver();
    private IState startState = null;
    private IState goalState = null;

    /**
     * The method print out the command that is available. The user can choose
     * among the commands. A selected command will be executed.
     */
    public void run(){
        int command = 0;
        
        do {
            command = chooseCommand();
            
            switch(command){
                case 10:    // Quit the program.
                    System.out.println("Bye!");
                    break;
                case 1:     // Create a new puzzle.
                    System.out.print("The size of the puzzle, 3, 8 or 15 tiles? ");
                    int size = keyboard.nextInt();
                    puzzle = new Puzzle(size);
                    puzzle.show();
                    break;
                case 2:     // Manually move the tiles.
                    if (puzzle == null)
                        break;
                    puzzle.show();
                    puzzle.solveByHuman();
                    break;
                case 3:     // Mix the puzzle.
                    if (puzzle == null)
                        break;
                    puzzle.shuffle();   //Rename to a call to your method
                    puzzle.show();
                    break;
                case 4:     // Current puzzle as start state.
                    startState = puzzle;
                    System.out.println("Start state is: ");
                    startState.show();
                    break;
                case 5:     // Use the tree of letters, assign start and goal.
                    System.out.print("Assign the start state, e.g. letter A: ");
                    char letter = keyboard.next().charAt(0);
                    startState = new Letters(letter);
                    System.out.print("Start state is: ");
                    startState.show();
                    System.out.println();
                    
                    System.out.print("Assign the goal state, e.g. letter U: ");
                    letter = keyboard.next().charAt(0);
                    goalState = new Letters(letter);
                    System.out.print("Goal state is: ");
                    goalState.show();
                    System.out.println();
                    break;
                case 6:     // Read a puzzle from a file, assign start or goal.                    
                    try {
                        Puzzle newPuzzle = readPuzzleFromFile();
                        
                        System.out.print("Puzzle as 1. Start or 2. Goal: ");
                        int choice = keyboard.nextInt();
                        
                        if (choice == 1){
                            puzzle = newPuzzle;
                            startState = newPuzzle;
                            System.out.println("Start state is:");
                            startState.show();
                        } else if (choice == 2) {
                            goalState = newPuzzle;
                            System.out.println("Goal state is:");
                            goalState.show();                            
                        } else {
                            System.out.println("No state has been choosen.");
                        }    
                    } catch (Exception error) {
                        System.err.println(error);
                    }
                    break;
                case 7:     // Solve by breadth first search.
                    solver.setTraversingStrategi(new BreadthFirst(startState, goalState, true));
                    solver.searchSolution();
                    break;
                case 8:     // Solve by depth first search.
                    System.out.print("Depth bound? ");
                    int depthBound = keyboard.nextInt();
                    solver.setTraversingStrategi(new DepthFirst(startState, goalState, depthBound, true));
                    solver.searchSolution();
                    break;
                default:    // Unknown command
                    System.out.println("The command number " + command + " is unknown.");
            }
            
        } while (command != 10);
    }
    
    /**
     * The method print out available commands and return the number for the
     * command that is selected.
     * @return number for the selected command
     */
    private int chooseCommand() {
        System.out.println("");
        System.out.println("10. Quit the program.");
        System.out.println("1. Create a new puzzle.");
        
        if (puzzle != null){
            System.out.println("2. Manually move the tiles.");
            System.out.println("3. Mix the puzzle.");
            System.out.println("4. Current puzzle as start.");
        }
        
        System.out.println("5. Use the tree of letters, assign start and goal.");
        System.out.println("6. Read a puzzle from a file, assign start or goal.");
        
        if (startState != null && goalState != null){
            System.out.println("7. Solve by breadth first search.");
            System.out.println("8. Solve by depth first search.");
        }
               
        System.out.print("Your choice: ");
        return keyboard.nextInt();
    }

    /**
     * The method ask for a file namen and reads in a puzzle from that file.
     * @return The new puzzle.
     * @throws Exception 
     */
    private Puzzle readPuzzleFromFile() throws Exception {
        keyboard.nextLine();    // Clears the input buffer
        System.out.print("Name of the file? ");
        String name = keyboard.next();
        Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(name)));
        Puzzle aPuzzle = new Puzzle(fileScanner);
        fileScanner.close();
        
        return aPuzzle;
    }
}
