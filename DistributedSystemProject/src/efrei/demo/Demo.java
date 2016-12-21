package efrei.demo;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import efrei.client.Client;
import efrei.remote.Repository;
import efrei.remote.Sorter;
import efrei.serveur.*;

public class Demo {
	
	private static String SERVICE_NAME_STATEFULL_ACTIF = "Repository actif";
	private static String SERVICE_NAME_STATEFULL_PASSIF = "Repository passif";
	private static String SERVICE_NAME_STATELESS = "Sorter";
	private static String SERVICE_HOST = "localhost";
	
	public static void main(String[] args){
		Thread t = new Thread(){
			@Override
			public void run(){
				try {
					MyRegistry.main(null);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		};
		t.start();
		
		JFrame frame = new JFrame("Demo");
		frame.setSize(1200, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// on crée un panel qui s'organisera comme une grille de 4 par 9
		GridLayout l = new GridLayout(4, 9);
		JPanel panel = new JPanel(l);
		// on ajoute tout de suite le panel à notre fenètre
		panel.setVisible(true);
		frame.add(panel);
		
		
		// on crée les boutons
		JButton add_client = new JButton("Ajouter un client");
		JButton add_server_stateless = new JButton("Ajouter un serveur stateless");
		JButton add_server_statefull_actif = new JButton("Ajouter un serveur statefull actif");
		JButton add_server_statefull_passif = new JButton("Ajouter un server statefull passif");
		JButton request_stateless = new JButton("Envoyer une requête stateless");
		JButton request_statefull_actif = new JButton("Envoyer une requête statefull actif");
		JButton request_statefull_passif = new JButton("Envoyer une requête statefull passif");
		
		panel.add(add_client);
		panel.add(add_server_stateless);
		panel.add(add_server_statefull_actif);
		panel.add(add_server_statefull_passif);
		panel.add(request_stateless);
		panel.add(request_statefull_actif);
		panel.add(request_statefull_passif);
		
		for(Component elem : panel.getComponents()){
			elem.setVisible(true);
		}
		
		// on ajoute les actions aux boutons
		add_client.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Client.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		add_server_stateless.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ServeurStateLess.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		add_server_statefull_actif.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ServeurStatefullActif.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		add_server_statefull_passif.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					ServeurStatefullPassif.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		request_stateless.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    Registry registry;
				try {
					registry = LocateGlobalRegistry.getRegistry(SERVICE_HOST);
					Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME_STATELESS);
					List<String> list = Arrays.asList("3", "5", "1", "2", "4");
				    list = sorter.sort(list);
				    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
				    list = sorter.reverseSort(list);
				}
				catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		request_statefull_actif.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    try {
					Registry registry = LocateGlobalRegistry.getRegistry(SERVICE_HOST);
				    Repository repo = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_ACTIF);
				    repo.setProperty("key", "value");
				    repo.getProperty("key");
				    repo.removeProperty("key");
				    repo.getProperty("key");
				}
			    catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// on ajoute les actions aux boutons
		request_statefull_passif.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    try {
					Registry registry = LocateGlobalRegistry.getRegistry(SERVICE_HOST);
				    Repository repo3 = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_PASSIF);
				    
				    repo3.setProperty("key", "value");
				    repo3.getProperty("key");
				    repo3.removeProperty("key");
				    repo3.getProperty("key");
				    repo3.setProperty("key", "value2");
				}
			    catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
