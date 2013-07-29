/*
 * Interface for converting the input space to an image for a visualisation
 */
package gng.core.handlers.inputs;

import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public interface InputSpaceVisualizer {
    
    public Image getVisualisation();

    public int getWidth();

    public int getHeight();

    public ArrayList getInputs();
    
}
