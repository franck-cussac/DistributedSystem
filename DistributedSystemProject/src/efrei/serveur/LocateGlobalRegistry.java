package efrei.serveur;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class LocateGlobalRegistry {
	
	private static final int REGISTRY_PORT = 1099;
	
	public static Registry createRegistry() throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
		
		return registry;
	}
	
	public static Registry getRegistry() {
		return null;
	}
	
	public static Registry getRegistry(String host) {
		
		return null;
	}
}
