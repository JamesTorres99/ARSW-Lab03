/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.black.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable{

	private int min;
	private int max;
	
	public CountThread(int ini, int fin) {
		min = Math.min(ini, fin);
		max = Math.max(ini, fin);
	}
	
	
	@Override
	public void run() {
		for (int i=min; i<=max; i++) System.out.println(i);
	}
    
	
}
