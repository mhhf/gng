/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng;

import gng.core.handlers.inputs.StaticPolygonInputs;
import gng.core.handlers.PolygonBasedGNGHandler;
import gng.core.handlers.inputs.InputSpaceManager;
import gng.core.metrics.AbsoluteDeviationMetric;
import gng.core.metrics.SpaceMetric;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public class StaticPolygonSetupPanel  extends Panel implements KeyListener{
    
     // DEFINITION
    
    // visualizer
    InputSpaceManager inputManager;
    
    // GNG Handeler
    PolygonBasedGNGHandler gngHandler;
    
    // Graph Visualizer
    GraphVisualizer graphVisualizer;
    private Image graphVisualisation;
    
    // Metrics
    protected AbsoluteDeviationMetric adMetric;
    protected SpaceMetric psMetric;
    
 
    public StaticPolygonSetupPanel() throws IOException {
        addKeyListener(this);
        
        setBackground(new Color(0xff00ff));
         
        // init visualizer
        inputManager = new StaticPolygonInputs( StaticPolygonInputs.POLYGON_TYPE4 );
        
        // init Handler
        gngHandler = new PolygonBasedGNGHandler( inputManager );
        
        // init Graph Visualizer
        graphVisualizer = new GraphVisualizer( 
                gngHandler.getNodes(), 
                gngHandler.getConnections(), 
                this.getGraphics()
                );
        
        // init Metrics
        this.adMetric = new AbsoluteDeviationMetric( gngHandler.getConnections() );
        this.psMetric = new SpaceMetric(inputManager, gngHandler);
    }
    
    @Override
    public void paint( Graphics g ) {
        super.paint(g);
        g.drawImage( inputManager.getVisualisation(), 0, 0, this);
        this.getGraphics().drawImage(this.graphVisualisation, 0, 0, this);
    }
   
    // TODO: WTF doas this?
    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        ArrayList points;
        
        switch(ke.getKeyChar()) {
            case '3':
                points = this.gngHandler.cycle(1500);
            case '2': 
                points = this.gngHandler.cycle(400);
            case '1': 
                points = this.gngHandler.cycle(100);
            case ' ': 
                points = this.gngHandler.cycle();
                this.graphVisualisation = this.graphVisualizer.drawGraph(points);
                break;
            case 'l':
                points = this.gngHandler.cycle();
                this.graphVisualisation = this.graphVisualizer.drawFilledGraph(gngHandler.getComPoint());
		this.gngHandler.calculateMeanError();
                System.out.println("Iterations: "+this.gngHandler.getIterations());
		System.out.println("AD: " + 1/this.adMetric.calculate());
                System.out.println("PS: " + 1/this.psMetric.calculatePositive());
                System.out.println("NS: " + this.psMetric.calculateNegative());
                System.out.println("ME: " + this.gngHandler.meanError);
		break;
        }
        
        this.paint(this.getGraphics());
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
