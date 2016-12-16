package efrei.serveur;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import efrei.remote.IStatefull;
import efrei.remote.IStateless;

public class GlobalRegistry implements IGlobalRegistry {
	
	private Map<String, List<Remote>> map = new HashMap<>();
	
	private Map<String, Integer> map_it = new HashMap<>();
	private Map<String, Integer> map_nb_obj = new HashMap<>();
	

	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
		Remote stub = map.get(name).get(0);
		
		if(stub instanceof IStatefull){
			
		}
		else if(stub instanceof IStateless){
			stub = map.get(name).get(map_it.get(name) % map_nb_obj.get(name));
			map_it.put(name, map_it.get(name) + 1);
		}
		
		return stub;
	}

	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
		// TODO Auto-generated method stub
		if(map.get(name) == null){
			map.put(name, new ArrayList<Remote>());
			map_nb_obj.put(name, 0);
			map_it.put(name, 0);
		}
		map.get(name).add(obj);
		map_nb_obj.put(name, map_nb_obj.get(name) + 1);
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		// TODO Auto-generated method stub
	}

	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		// TODO Auto-generated method stub
		if(map.get(name) == null){
			map.put(name, new ArrayList<Remote>());
			map_nb_obj.put(name, 0);
			map_it.put(name, 0);
		}
		map.get(name).add(obj);
		map_nb_obj.put(name, map_nb_obj.get(name) + 1);
	}

	@Override
	public String[] list() throws RemoteException, AccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// retourne la liste des stubs qui correspond à un service
	@Override
	public List<Remote> list(String service) throws RemoteException, AccessException {
		return map.get(service);
	}

}
