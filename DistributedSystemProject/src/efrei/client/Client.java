package efrei.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

import efrei.remote.*;

/**
 * Client program.
 *
 * Note: For the the client to retrieve the stub of the remote object, it needs
 * to know: (1) what the name of the object is, (2) which machine hosts the
 * remote object.
 *
 */
public class Client {

  //
  // CONSTANTS
  //
  private static String SERVICE_NAME_STATEFULL = "Repository";
  private static String SERVICE_NAME_STATELESS = "Sorter";
  private static String SERVICE_HOST = "192.168.1.164";

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    Registry registry = LocateRegistry.getRegistry(SERVICE_HOST);
    System.out.println("client: retrieved registry");

    // retrieve the stub of the remote object by its name
    Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME_STATELESS);
    System.out.println("client: retrieved Sorter stub");

    // call the remote object to perform sorts and reverse sorts
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    System.out.println("client: sending " + list);

    list = sorter.sort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);

    list = sorter.reverseSort(list);
    System.out.println("client: received " + list);

    // main terminates here
    System.out.println("client: exiting");
    
    // call the remote object to perform sorts and reverse sorts
    System.out.println("client: sending " + "setProperty sur key = key et value = value");
    
    
    Repository repo = (Repository) registry.lookup(SERVICE_NAME_STATEFULL);
    System.out.println("client: retrieved Sorter stub");
    
    repo.setProperty("key", "value");

    System.out.println("client: received ");

    System.out.println("client: sending ");
    
    String s = repo.getProperty("key");
    
    System.out.println("client: received " + s);
    
    System.out.println("client: sending ");
    
    repo.removeProperty("key");
    
    System.out.println("client: received " + s);
    System.out.println("client: sending ");
    
    String s2 = repo.getProperty("key");
    
    System.out.println("client: received " + s2);

    // main terminates here
    System.out.println("client: exiting");

  }

}
