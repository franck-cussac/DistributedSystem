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
	
	private static int cpt_stateless = 0;
	private static int cpt_statefull_actif = 0;
	private static int cpt_statefull_passif = 0;
	
	private static JTextArea textarea;
	
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// on cr�e un panel qui s'organisera comme une grille de 4 par 9
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		// on ajoute tout de suite le panel � notre fen�tre
		panel.setVisible(true);
		panel2.setVisible(true);
		
		
		// on cr�e les boutons
		JButton add_client = new JButton("Tester toutes les requ�tes");
		JButton add_server_stateless = new JButton("Ajouter un serveur stateless");
		JButton add_server_statefull_actif = new JButton("Ajouter un serveur statefull actif");
		JButton add_server_statefull_passif = new JButton("Ajouter un server statefull passif");
		JButton request_stateless = new JButton("Envoyer une requ�te stateless");
		JButton request_statefull_actif = new JButton("Envoyer une requ�te statefull actif");
		JButton request_statefull_passif = new JButton("Envoyer une requ�te statefull passif");
		
	    //On d�finit le layout en lui indiquant qu'il travaillera en ligne
	    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
	    panel.add(add_client);
	    panel.add(add_server_stateless);
	    panel.add(add_server_statefull_actif);
	    panel.add(add_server_statefull_passif);
	    
	    panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
	    panel2.add(request_stateless);
	    panel2.add(request_statefull_actif);
	    panel2.add(request_statefull_passif);
	    
	    JPanel b4 = new JPanel();
	    //On positionne maintenant ces trois lignes en colonne
	    b4.setLayout(new BoxLayout(b4, BoxLayout.PAGE_AXIS));
	    b4.add(panel);
	    b4.add(panel2);
	    frame.add(b4);
	    
	    JPanel b5 = new JPanel();
	    b5.setLayout(new BoxLayout(b5, BoxLayout.PAGE_AXIS));
	    textarea = new JTextArea();
	    JScrollPane sp = new JScrollPane(textarea);
	    b5.add(sp);
	    b4.add(b5);
		
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
					cpt_stateless++;
					textarea.setText(textarea.getText() + "Vous avez maintenant " + cpt_stateless + " serveur stateless\n");
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
					cpt_statefull_actif++;
					textarea.setText(textarea.getText() + "Vous avez maintenant " + cpt_statefull_actif + " serveur statefull actif\n");
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
					cpt_statefull_passif++;
					textarea.setText(textarea.getText() + "Vous avez maintenant " + cpt_statefull_passif + " serveur statefull passif\n");
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
					textarea.setText(textarea.getText() + "sended : " + list + "\n");
				    list = sorter.sort(list);
					textarea.setText(textarea.getText() + "received : " + list + "\n");
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
				    String key = "test";
				    String value = "test value";
					textarea.setText(textarea.getText() + "sended : key = " + key + " value = " + value + "\n");
				    repo.setProperty(key, value);
				    value = repo.getProperty(key);
					textarea.setText(textarea.getText() + "received : key = " + key + " value = " + value + "\n");
				    repo.removeProperty(key);
					textarea.setText(textarea.getText() + "removed key = " + key + "\n");
				    value = repo.getProperty(key);
					textarea.setText(textarea.getText() + "get key = " + key + " value = " + value + "\n");
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
				    Repository repo = (Repository) registry.lookup(SERVICE_NAME_STATEFULL_PASSIF);
				    

				    String key = "test";
				    String value = "test value";
					textarea.setText(textarea.getText() + "sended : key = " + key + " value = " + value + "\n");
				    repo.setProperty(key, value);
				    value = repo.getProperty(key);
					textarea.setText(textarea.getText() + "received : key = " + key + " value = " + value + "\n");
				    repo.removeProperty(key);
					textarea.setText(textarea.getText() + "removed key = " + key + "\n");
				    value = repo.getProperty(key);
					textarea.setText(textarea.getText() + "get key = " + key + " value = " + value + "\n");
					value = "test value2";
					textarea.setText(textarea.getText() + "sended : key = " + key + " value = " + value + "\n");
				    repo.setProperty(key, value);
				    value = repo.getProperty(key);
					textarea.setText(textarea.getText() + "get key = " + key + " value = " + value + "\n");
				}
			    catch (RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.setVisible(true);
	}
}
