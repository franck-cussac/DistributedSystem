package efrei.serveur;

import java.net.InetAddress;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import efrei.remote.*;

/**
 * Server program.
 *
 * Note: After the main method exits, the JVM will still run. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class ServeurStatefull {

  //
  // CONSTANTS
  //
  private static final String SERVICE_NAME = "Repository";

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
	    System.out.println("server: instanciated SimpleSorter");

	    // create a skeleton and a stub for that remote object
	    Repository stub = (Repository) UnicastRemoteObject.exportObject(repo, 0);
	    System.out.println("server: generated skeleton and stub");

	    // register the remote object's stub in the registry
	    Registry registry = LocateGlobalRegistry.getRegistry();
	    registry.rebind(SERVICE_NAME, stub);
	    System.out.println("server: registered remote object's stub");

	    // main terminates here, but the JVM still runs because of the skeleton
	    System.out.println("server: ready");
  }

}