package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private  AtomicInteger health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private boolean pausar, finalizar;
    
    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = new AtomicInteger(health);
        this.defaultDamageValue=defaultDamageValue;
    }

    public void run() {

        while (true) {
        	
        	synchronized (this) {
				while (pausar) try { 
					wait();
					} catch (InterruptedException e){
						 e.printStackTrace();
					};				
				}
        	
            Immortal im;
            
            int myIndex = immortalsPopulation.indexOf(this);
            
            if (immortalsPopulation.size() !=1) {
            
	            int nextFighterIndex = r.nextInt(immortalsPopulation.size());
	
	            //avoid self-fight
	            if (nextFighterIndex == myIndex) {
	                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
	            }
	
	            im = immortalsPopulation.get(nextFighterIndex);
	
	            this.fight(im);
            }else {
            	terminar();
            }
            
            
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void fight(Immortal i2) {
    	synchronized (this) {
        if (i2.getHealth() > 0) {
            i2.changeHealth(- defaultDamageValue);
            this.changeHealth(defaultDamageValue);
            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
        } else {
        	immortalsPopulation.remove(this);
        	this.parar();
        	this.terminar();
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }
    	
    }

  }

    public void changeHealth(int v) {
    	health.addAndGet(v);
    }

    public int getHealth() {
    	return health.intValue();
    }

    public synchronized void parar() {
		pausar= true;
		notifyAll();
		
	}
	
	public synchronized void renaudar() {
		pausar= false;
		notifyAll();
	}
		
	public synchronized void terminar() {
		finalizar= true;
		notifyAll();
	}
    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
