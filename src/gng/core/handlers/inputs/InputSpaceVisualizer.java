/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.handlers.inputs;

import java.awt.Image;
import java.util.ArrayList;
import no.uib.cipr.matrix.DenseVector;

/**
 *
 * @author mhhf
 */
public interface InputSpaceVisualizer {
    
    public Image getVisualisation();

    public int getWidth();

    public int getHeight();

    public ArrayList<DenseVector> getInputSet();
    
}
