/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng;

import gng.core.handlers.PolygonBasedGNGHandler;
import gng.core.handlers.inputs.InputSpaceManager;
import gng.core.handlers.inputs.WindowPolygonInputs;
import gng.core.handlers.inputs.dynamicPolygons.WindowObsticle;
import gng.core.metrics.AbsoluteDeviationMetric;
import gng.core.metrics.SpaceMetric;

/**
 *
 * @author mhhf
 */
public class HeadlessSetup {
	
	 // DEFINITION
    
    // visualizer
    InputSpaceManager inputManager;
    
    // GNG Handeler
    PolygonBasedGNGHandler gngHandler;
    
    // Metrics
    protected AbsoluteDeviationMetric adMetric;
    protected SpaceMetric psMetric;

	public HeadlessSetup() {
		
		// init visualizer
		inputManager = new WindowPolygonInputs( new WindowObsticle() );

		// init Handler
		gngHandler = new PolygonBasedGNGHandler( inputManager );

		// init Metrics
		this.adMetric = new AbsoluteDeviationMetric( gngHandler.getConnections() );
		this.psMetric = new SpaceMetric(inputManager, gngHandler);
		
		for (int i = 0; i < 10; i++) {
			cycle();
		}
	}
	
	
	public void cycle() {
		this.gngHandler.cycle();
		this.gngHandler.calculateMeanError();
                System.out.println("Iterations: "+this.gngHandler.getIterations());
		System.out.println("AD: " + 1/this.adMetric.calculate());
                System.out.println("PS: " + 1/this.psMetric.calculatePositive());
                System.out.println("NS: " + this.psMetric.calculateNegative());
                System.out.println("ME: " + this.gngHandler.meanError);
	}
}
