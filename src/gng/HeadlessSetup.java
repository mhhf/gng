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
import java.io.FileWriter;
import java.io.IOException;

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
    private FileWriter fw;

	public HeadlessSetup() throws IOException {
		
		// init visualizer
		inputManager = new WindowPolygonInputs( new WindowObsticle() );

		// init Handler
		gngHandler = new PolygonBasedGNGHandler( inputManager );

		// init Metrics
		this.adMetric = new AbsoluteDeviationMetric( gngHandler.getConnections() );
		this.psMetric = new SpaceMetric(inputManager, gngHandler);
		
		this.fw = new FileWriter( "log.txt" );
		
		for (int i = 0; i < 50000; i++) {
			cycle();
			if(i%50 == 0) log();
		}
		System.out.print("rdy");
	}
	
	
	public void cycle() {
		this.gngHandler.cycle();
		
		/*System.out.println("Iterations: "+this.gngHandler.getIterations());
		System.out.println("N: " + this.gngHandler.getNodes().size());
		System.out.println("AD: " + 1/this.adMetric.calculate());
                System.out.println("PS: " + 1/this.psMetric.calculatePositive());
                System.out.println("NS: " + this.psMetric.calculateNegative());
                System.out.println("ME: " + this.gngHandler.meanError);*/
	}
	
	public void log() throws IOException {
		this.gngHandler.calculateMeanError();
		int i = this.gngHandler.getIterations();
		int n = this.gngHandler.getNodes().size();
		double a = 1/this.adMetric.calculate();
		double po = 1/this.psMetric.calculatePositive();
		double ne = this.psMetric.calculateNegative();
		double f = ( a*0.5 + po + ne ) / 2.5;
		 fw.write( i+","+ n +","+ a +","+ po +","+ ne +","+ this.gngHandler.meanError+ ","+ f +"\n");
	}
}
