package efrei.client;

import java.rmi.registry.*;
import java.util.Arrays;
import java.util.List;

import efrei.remote.*;
import efrei.serveur.LocateGlobalRegistry;

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
  private static String SERVICE_NAME_STATEFULL_ACTIF = "Repository actif";
  private static String SERVICE_NAME_STATEFULL_PASSIF = "Repository passif";
  private static String SERVICE_NAME_STATELESS = "Sorter";
  private static String SERVICE_HOST = "localhost";
  
  // Si true, l'algorithme utilisé est circulaire (round-robin) 
  // sinon, l'algorithme de répartition utilise des méthodes java qui calculent les performances CPU 
  //private static final boolean REPARTITION_ROUND_ROBIN = true;

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    Registry registry = LocateGlobalRegistry.getRegistry(SERVICE_HOST);
    System.out.println("client: retrieved registry");
    System.out.println("registry : " + registry);
    
    // retrieve the stub of the remote object by its name
    Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME_STATELESS);
    Sorter sorter1 = (Sorter) registry.lookup(SERVICE_NAME_STATELESS);
    Sorter sorter2 = (Sorter) registry.lookup(SERVICE_NAME_STATELESS);
    System.out.println("client: retrieved Sorter stub");
    System.out.println("sorter : " + sorter);

    // call the remote object to perform sorts and reverse sorts
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    list = sorter.sort(list);
    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    list = sorter.reverseSort(list);

    list = Arrays.asList("3", "5", "1", "2", "4");
    list = sorter1.sort(list);
    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    list = sorter1.reverseSort(list);

    list = Arrays.asList("3", "5", "1", "2", "4");
    list = sorter2.sort(list);
    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    list = sorter2.reverseSort(list);
    
    
    Repository repo = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_ACTIF);
    Repository repo1 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_ACTIF);
    Repository repo2 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_ACTIF);
    System.out.println("client: retrieved Sorter stub");
    
    repo.setProperty("key", "value");
    repo.getProperty("key");
    repo.removeProperty("key");
    repo.getProperty("key");
    
    repo1.setProperty("key", "value");
    repo1.getProperty("key");
    repo1.removeProperty("key");
    repo1.getProperty("key");
    
    repo2.setProperty("key", "value");
    repo2.getProperty("key");
    repo2.removeProperty("key");
    repo2.getProperty("key");
    
    
    Repository repo3 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_PASSIF);
    Repository repo4 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_PASSIF);
    Repository repo5 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_PASSIF);
    System.out.println("client: retrieved Sorter stub");
    
    repo3.setProperty("key", "value");
    repo3.getProperty("key");
    //repo3.removeProperty("key");
    repo3.getProperty("key");
    
    repo4.setProperty("key", "value");
    repo4.getProperty("key");
    //repo4.removeProperty("key");
    repo4.getProperty("key");
    
    repo5.setProperty("key", "value");
    repo5.getProperty("key");
    //repo5.removeProperty("key");
    repo5.getProperty("key");
    

    // main terminates here
    System.out.println("client: exiting");

  }

}
