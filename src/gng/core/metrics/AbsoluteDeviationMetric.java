/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gng.core.metrics;

import gng.core.Connection;
import java.util.ArrayList;

/**
 *
 * @author mhhf
 */
public class AbsoluteDeviationMetric {
    
    public double ad;
    public ArrayList<Connection> cons;

    public AbsoluteDeviationMetric() {

    }
    
    public AbsoluteDeviationMetric( ArrayList<Connection> cons ) {
        this.cons = cons;
    }

    private double calculateAD(ArrayList<Connection> cons) {
        double arithmetischesMittel = calculeteMean( cons ); 
        double ad = 0;
        
        for( Connection con:cons ){
            ad += Math.abs(con.getNorm() - arithmetischesMittel);
        }
        
        this.ad = ad/cons.size();
        return this.ad;
    }

    private double calculeteMean(ArrayList<Connection> cons) {
        double mean = 0;
        
        for( Connection con:cons ){
            mean += con.getNorm();
        }
        
        return mean/cons.size();
    }

    public double calculate() {
        return this.calculateAD(cons);
    }
    
    
    
}
