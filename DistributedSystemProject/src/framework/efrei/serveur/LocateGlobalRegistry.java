package framework.efrei.serveur;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;


public class LocateGlobalRegistry {
	
	private static final int REGISTRY_PORT = 1099;
	private static final String REGISTRY_NAME = "efrei.serveur.registry.name";
	private static final String LOCALHOST_STRING = "localhost";
	
	public static Registry createRegistry() throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
		
		GlobalRegistry glob_reg = new GlobalRegistry();
		// create a skeleton and a stub for that remote object
		Remote stub = UnicastRemoteObject.exportObject(glob_reg, 0);
	    System.out.println("server: generated skeleton and stub");
		registry.rebind(REGISTRY_NAME, stub);
		
		return registry;
	}
	
	public static Registry getRegistry() throws AccessException, RemoteException, NotBoundException {
		return LocateGlobalRegistry.getRegistry(LOCALHOST_STRING);
	}
	
	public static Registry getRegistry(String host) throws AccessException, RemoteException, NotBoundException {
		return (Registry)LocateRegistry.getRegistry(host).lookup(REGISTRY_NAME);
	}
}
