package efrei.serveur;

import java.util.*;

import efrei.remote.Repository;

public class SimpleRepository implements Repository {
	private Map<String, String> repo = new HashMap<>();
	@Override
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		System.out.println("Server sended : key = " + key + " value = " + repo.get(key));
		return repo.get(key);
	}

	@Override
	public void setProperty(String key, String value) {
		// TODO Auto-generated method stub
		System.out.println("Server received : key = " + key + " value = " + value);
		repo.put(key, value);
	}

	@Override
	public void removeProperty(String key) {
		// TODO Auto-generated method stub
		System.out.println("Server removed : key = " + key + " value = " + repo.get(key));
		repo.remove(key);
	}

}
