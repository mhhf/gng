/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.handlers;

import gng.core.AbstractGNGHandler;
import gng.core.Node;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;

/**
 *
 * @author mhhf
 */
public class SetBasedGNGHandler extends AbstractGNGHandler {
    
    protected ArrayList<DenseVector> inputs;

    public SetBasedGNGHandler(ArrayList<DenseVector> inputs){
        this.inputs = inputs;
        this.initCycle();
    }

    public SetBasedGNGHandler() {
    }

    @Override
    public DenseVector getRandomInput() {
        double[] point = {0, 0};
        DenseVector returnPoint = null;
        
        if(this.inputs == null) {
            point[0] = Math.random();
            point[1] = Math.random();
            returnPoint = new DenseVector( point );
        } else {
            do{
                int position = ((int) (Math.floor(Math.random()*this.inputs.size())));
                returnPoint = this.inputs.get(position);
            } while( Math.random() > returnPoint.get(2));
        }
        
        return returnPoint;
    }

    @Override
    public DenseVector getRandomNegativeInput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    // Is the node valid for update
    public boolean validateNodeForUpdate(Node node) {
        return true;
    }
    
}
