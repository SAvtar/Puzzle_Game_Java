/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traverse;
import java.util.*;
import state.IState;
import state.Letters;

/**
 * The class has a solver that search for a solution with help of breadth first
 * an prints out that solution. The class implements the methods that are
 * declared in the interface ITraverse
 * @author Please, enter your name instead of this sentence!
 */
public class BreadthFirst implements ITraverse {
    private IState start, goal;
    private int iteration = 0;
    private boolean verbose = false;
    private Queue<IState> open = new LinkedList<IState>();
    private List<IState> closed = new ArrayList<IState>();
    
    /**
     * The constructor saves the start and goal states.
     * @param startState The start state from which the search of a solution begins.
     * @param goalState The goal state which the solver search for. 
     */
    public BreadthFirst(IState startState, IState goalState, boolean verbose) {
        start = startState;
        goal = goalState;
        this.verbose = verbose;
    }
    
    /**
     * The method return the number of iterations that has been done
     * to find the goal state.
     * @return the number of iterations
     */
    @Override
    public int getIterations() {
        return iteration;
    }
    
    /**
     * This is an implementation of the breadth first algorithm that can be
     * find in the textbook chapter 3.
     * The method does a breath first traversal to find the goal state. If the
     * goal is found the trace from start down to the goal is returned. If the
     * goal is not found the method return null
     * @return the trace from start to goal or null if the goal is not found.
     */
    @Override
    public IState search() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       //IState s = new Letters(start.);
       
       open.add(start);
       IState currentState = null; 
       while(!(open.isEmpty())){
            
            currentState = open.remove();
            if(currentState == null)
                continue;
            //generated chidrens of currentnode
            //currentState.show();
            if(currentState.equals(goal)){
                //System.out.println("Inside");
                return currentState;
            }
            else{
                IState[] childrens = new IState[1];
               // System.out.println("currentState = " +currentState.toString());
                childrens = currentState.createChildren();
                 //Code to check chidren of currentState
//                int count = 0;
//                for (IState children : childrens) {
//                    System.out.println("chidren"+"["+(count++)+"]"+children);
//                    
//                }
                closed.add(currentState);
                for (IState children : childrens) {
                //goes in only children is not in open or closed
                    if(!(open.contains(children) || closed.contains(children))){
                        open.add(children);
                    }
                }
                
            }
            iteration++;
        }
       return currentState;
    }
    
    /**
     * The method print out the content of the both lists open and closed.
     * @param open A list with all the nodes that are discovered but not
     * visited.
     * @param closed A list with all the nodes that are visited.
     * @param iteration The count of iterations, for each iteration a node was visited.
     */
    private void showStatus(Iterable<IState> open, Iterable<IState> closed, int iteration) {
        if (verbose){
            System.out.print("Breadth first, iteration: " + iteration);
            
            System.out.println("*******************");

            System.out.println("OPEN NODES");
            open.forEach(savedState -> savedState.show());
            /*
            for (IState savedState : open){
                System.out.println(savedState.show());
            }
            */
            
            System.out.println();

            System.out.println("CLOSED NODES");
            closed.forEach(savedState -> savedState.show());
            System.out.println();

            System.out.println("----------------------------------------------");
        }
    }
}
