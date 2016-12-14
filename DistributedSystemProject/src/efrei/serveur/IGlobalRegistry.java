package efrei.serveur;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

public interface IGlobalRegistry extends Registry {
	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException;
	
	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException;
	
	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException;
	
	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException;

	@Override
	public String[] list() throws RemoteException, AccessException;
	
	// retourne la liste des stubs qui correspond à un service
	public List<Remote> list(String service) throws RemoteException, AccessException;
	
	
}
