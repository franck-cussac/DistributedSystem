package efrei.serveur;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.HashMap;
import java.util.Map;

public class GlobalRegistry implements Registry {
	
	private Map<String, Remote> map = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
		return map.get(name);
	}

	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
		// TODO Auto-generated method stub
		map.put(name, obj);
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
	}

	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		// TODO Auto-generated method stub
		map.put(name, obj);
	}

	@Override
	public String[] list() throws RemoteException, AccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
