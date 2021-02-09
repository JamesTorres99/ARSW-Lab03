package edu.eci.black.blacklistvalidator;

import java.util.LinkedList;
import edu.eci.black.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class HostBlackListsValidatorThread extends Thread {
	
	protected int inicio;
	protected int fin;
	protected int ocurrencesCount;
	protected String ipAddr;
	protected LinkedList<Integer> servers;
	
	/**
	 * Constructor principal de la clase. El proceso en el thread está modelado con 
	 * la técnica de partición de datos.
	 * @param inicio inicio de la partición de datos asignada.
	 * @param fin fin de la partición de datos asignada. Es inclusive.
	 * @param ip dirección ip sobre la que se realizará la validación.
	 */
	public HostBlackListsValidatorThread(int inicio, int fin, String ip) {
		reset(inicio, fin, ip);
	}
	
	/**
	 * Devuelve la cantidad de servidores que se han revisado en la ejecución del proceso.
	 * @return la cantidad servidores hasta el momento de la invocación.
	 */
	public int getOcurrencesCount() {
		return ocurrencesCount;
	}
	
	/**
	 * Devuelve la cantidad de veces que la dirección ip fue encontrada en los servidores.
	 * @return el numero de veces que la ip se encontró hasta el momento de la invocación.
	 */
	public int getServersAmount() {
		return servers.size();
	}
	
	/**
	 * Devuelve la cantidad de servidores en los quela dirección ip fue encontrada.
	 * @return los servidores que se encontraron hasta el momento de la invocación.
	 */
	public LinkedList<Integer> getServersFound(){
		return servers;
	}
	
	@Override
	public void run() {
		HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
		for (int i=inicio; i<=fin; i++) {
			ocurrencesCount++;
			if (skds.isInBlackListServer(i, ipAddr)) {
				servers.add(i);
			}
		}
	}
	
	/**
	 * Método que encapsula la lógica de inicialización de las variables de la clase.
	 * @param inicio inicio de la partición de datos asignada.
	 * @param fin fin de la partición de datos asignada. Es inclusive.
	 * @param ipAddr dirección ip sobre la que se realizará la validación.
	 */
	private void reset(int inicio, int fin, String ipAddr) {
		this.inicio = inicio;
		this.fin = fin;
		this.ocurrencesCount = 0;
		this.ipAddr = ipAddr;
		servers = new LinkedList<Integer>();
	}

}
