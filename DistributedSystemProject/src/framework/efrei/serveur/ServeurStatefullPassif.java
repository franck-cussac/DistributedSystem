package framework.efrei.serveur;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import framework.efrei.remote.*;

/**
 * Server program.
 *
 * Note: After the main method exits, the JVM will still run. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class ServeurStatefullPassif {

  //
  // CONSTANTS
  //
  private static final String SERVICE_NAME = "Repository passif";

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

	// check the name of the local machine (two methods)
	    System.out.println("server: running on host " + InetAddress.getLocalHost());
	    System.out.println("server: hostname property "
	            + System.getProperty("java.rmi.server.hostname"));

	    // instanciate the remote object
	    Repository repo = new SimpleRepository();
	    repo.setService_name(SERVICE_NAME);
	    System.out.println("server: instanciated SimpleSorter");

	    // create a skeleton and a stub for that remote object
	    Repository stub = (Repository) UnicastRemoteObject.exportObject(repo, 0);
	    System.out.println("server: generated skeleton and stub");

	    // register the remote object's stub in the registry
	    IGlobalRegistry registry = (IGlobalRegistry)LocateGlobalRegistry.getRegistry();
	    registry.rebind(SERVICE_NAME, stub);
	    System.out.println("server: registered remote object's stub");
	    
	    // cr�ation de la tache asynchrone qui va mettre � jours la valeur du Remote
	    if(registry.list(SERVICE_NAME).size() > 1){
	    	System.out.println("le serveur passif s'abonne au premier");
	    	MyTask task = new MyTask();
		    task.setRepo(stub);
		    Timer t = new Timer();
		    t.schedule(task, 1000, 1000);
	    }
	    
	    // main terminates here, but the JVM still runs because of the skeleton
	    System.out.println("server: ready");
  }
}

class MyTask extends TimerTask {
	  private Repository repo;
	  
	  public void run() {
		  try {
			IGlobalRegistry reg = (IGlobalRegistry)LocateGlobalRegistry.getRegistry();
			
			Repository repo_actif = (Repository)reg.lookup(this.repo.getService_name());
			System.out.println("mise � jours de mon passif");
			
			for(String key_set : repo_actif.getMap().keySet()){
				this.repo.setProperty(key_set, repo_actif.getProperty(key_set));
				System.out.println("mise � jours de mon passif : " + this.repo.getProperty(key_set));
			}
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	public Repository getRepo() {
		return repo;
	}

	public void setRepo(Repository repo) {
		this.repo = repo;
	}
}