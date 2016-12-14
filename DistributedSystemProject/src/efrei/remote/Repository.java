package efrei.remote;

import java.rmi.RemoteException;

/**
 * Interface of a property repository.
 *
 */
public interface Repository extends IStatefull {

  public String getProperty(String key) throws RemoteException;

  public void setProperty(String key, String value) throws RemoteException;

  public void removeProperty(String key) throws RemoteException;
  
  public void setProperty(String key, String value, int num) throws RemoteException;
  
  public void removeProperty(String key, int num) throws RemoteException;

  public void setService_name(String service_name) throws RemoteException;
}