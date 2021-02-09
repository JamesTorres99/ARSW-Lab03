package edu.eci.black;

import java.util.List;

import edu.eci.black.blacklistvalidator.*; 

/**
 * @author Eduard Arias, James Torres
 * @verion 3.0
 */
public class MainDistribuido {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HostBlackListsValidator hblv=new HostBlackListsValidatorSyncronizado();
        List<Integer> blackListOcurrences;
        
        blackListOcurrences=hblv.checkHost("202.24.34.55", 10);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
        blackListOcurrences=hblv.checkHost("202.24.34.55", 20);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
        blackListOcurrences=hblv.checkHost("202.24.34.55", 50);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
        blackListOcurrences=hblv.checkHost("202.24.34.55", 100);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
        blackListOcurrences=hblv.checkHost("202.24.34.55", 200);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
	}

}
