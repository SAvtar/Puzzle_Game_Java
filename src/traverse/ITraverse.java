/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traverse;

import java.util.LinkedList;
import state.IState;

/**
 * This interface is the facade to the package traverse. Use the interface to
 * access methods needed for the solver.
 * @author Please, enter your name instead of this sentence if you do any changes.
 */
public interface ITraverse {
    int getIterations();
    IState search();
}
