package efrei.serveur;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import efrei.remote.Repository;



public class SimpleRepository implements Repository {
	private Map<String, String> repo = new HashMap<>();
	private int cpt = 0;
	private static final String SERVICE_NAME = "Repository";
	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		System.out.println("Server sended : key = " + key + " value = " + repo.get(key));
		
		return repo.get(key);
	}

	@Override
	public void setProperty(String key, String value) throws AccessException, RemoteException {
		// TODO Auto-generated method stub
		try {
			List<Remote> list = ((GlobalRegistry)LocateGlobalRegistry.getRegistry()).list(SERVICE_NAME);
			
			for(Remote elem : list){
				SimpleRepository repo = (SimpleRepository)elem;
				
				if(repo != this){
					repo.setProperty(key, value, this.cpt);
				}
			}
			this.cpt++;
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.repo.put(key, value);
	}
	
	public void setProperty(String key, String value, int num){
		while(num != this.cpt){
			
		}
		this.repo.put(key,  value);
		this.cpt++;
	}
	
	public void removeProperty(String key, int num){
		while(num != this.cpt){
			
		}
		this.repo.remove(key);
		this.cpt++;
	}

	@Override
	public void removeProperty(String key) {
		// TODO Auto-generated method stub
		try {
			List<Remote> list = ((GlobalRegistry)LocateGlobalRegistry.getRegistry()).list(SERVICE_NAME);
			
			for(Remote elem : list){
				SimpleRepository repo = (SimpleRepository)elem;
				
				if(repo != this){
					repo.removeProperty(key, this.cpt);
				}
			}
			this.cpt++;
		} catch (NotBoundException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Server removed : key = " + key + " value = " + repo.get(key));
		repo.remove(key);
	}
	

	private class Compteur implements Comparable<Compteur> {
		private int time;
		private int id_server;
		private SimpleRepository repo;
		
		public Compteur(SimpleRepository repo, int time, int id_server){
			this.repo = repo;
			this.time = time;
			this.id_server = id_server;
		}
		
		@Override
		public int compareTo(Compteur arg0) {
			// TODO Auto-generated method stub
			
			if(this.time < arg0.time) {
				return -1;
			}
			else if(this.time > arg0.time) {
				return 1;
			}
			else {
				if(id_server < arg0.id_server) {
					return -1;
				}
				else if(id_server > arg0.id_server) {
					return 1;
				}
				else {
					return 0;
				}
			}
		}
	}
}
