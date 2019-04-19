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
public class Heuristics implements ITraverse {
    private IState start, goal;
    private int iteration = 0, value = 0;
    private boolean verbose = false;
    private Queue<IState> open = new LinkedList<IState>();
    private List<IState> closed = new ArrayList<IState>();
    
    /**
     * The constructor saves the start and goal states.
     * @param startState The start state from which the search of a solution begins.
     * @param goalState The goal state which the solver search for. 
     */
    public Heuristics(IState startState, IState goalState, boolean verbose) {
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
       List<IState> open = new ArrayList<>();
       Queue<IState> closed = new PriorityQueue<>();
       Comparator<IState> com;
       Map<IState, Integer> data = new HashMap<>();
       com =(IState iStateA, IState iStateB) -> data.get(iStateA) - data.get(iStateB);
       start.heuristicValue(goal);
       open.add(start);
       data.put(start, start.getHeuristics());
       while(!open.isEmpty()){
           IState currentState = open.remove(0);
           if (currentState.equals(goal)) {
               closed.add(goal);
               iteration = closed.size();
               showStatus(open, closed, iteration);
               return goal;
            } else {
               IState[] createChildrens = goal.createChildren();
               for(IState children : createChildrens) {
                   if (children != null) {
                       if (!open.contains(children) && !closed.contains(children)){
                           children.heuristicValue(goal);
                           data.put(children, children.getHeuristics());
                           open.add(children);
                        } else if (open.contains(children)) {
                            children.heuristicValue(goal);
                            if (children.getHeuristics() < data.get(children)) {
                                data.put(children, children.getHeuristics());
                        } else if (closed.contains(children)) {
                            children.heuristicValue(goal);
                            if (children.getHeuristics() < data.get(children)) {
                                closed.remove(children);
                                open.add(children);
                                }
                            } 
                        }
                    }
                }
                closed.add(goal);
                Collections.sort(open, com);
                }
            iteration = closed.size();
            showStatus(open, closed, iteration);
            return null;
        }
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
            System.out.print("Best first, iteration: " + iteration);
            
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
    