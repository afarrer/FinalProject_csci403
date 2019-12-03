import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.net.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

public class InterfaceGUI extends JFrame{
	SQLExecution sqlHandler;
	private int resultCounter = 0;
	private JPanel container;
	private ResultSet results;
	private ResultSet crimeResults;
	private JPanel upperTopPanel;

	public InterfaceGUI() throws SQLException {
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sqlHandler = new SQLExecution();
		results = null;

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,0));
		upperTopPanel = new JPanel();
		JLabel cityListingLabel = new JLabel("You are viewing listings in Austin");
		upperTopPanel.add(cityListingLabel);
		JPanel lowerTopPanel = new JPanel();
		JLabel cityLabel = new JLabel("Select city to search for listings.");
		JButton searchButton = new JButton("Search");
		lowerTopPanel.add(cityLabel);
		JComboBox<String> box = new JComboBox<String>();
		ResultSet cities;
		
		
		try {
	
			results = sqlHandler.makeStatement("Austin");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cities = sqlHandler.getCities();
			while(cities.next()) {
				box.addItem(cities.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			crimeResults = sqlHandler.makeCrimeStatement("austin");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HashMap<Integer, Integer> crimeMap = coordinateMath.distanceMap(results, crimeResults);
		
		
		    
		    results = sqlHandler.makeStatement("Austin");
		    
		lowerTopPanel.add(box);
		lowerTopPanel.add(searchButton);
		
		topPanel.add(upperTopPanel);
		topPanel.add(lowerTopPanel);

		JPanel botPanel = new JPanel();
		JButton nextButton = new JButton("Next Page");
		botPanel.add(nextButton);


		
		
		

		container = new JPanel();
		container.setLayout(new GridLayout(10,0));
		JScrollPane scrPane = new JScrollPane(container);

		scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		class nextListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				resultCounter++;
				container.removeAll();
				redrawPanel();
				repaint();
				
			}

		}
		nextButton.addActionListener(new nextListener());
		
		class searchListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultCounter = 0;
				cityListingLabel.setText("You are viewing listings in " + (String) box.getSelectedItem());
				
				container.removeAll();
				try {
					results = sqlHandler.makeStatement((String) box.getSelectedItem());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				redrawPanel();
			}
			
		}
		searchButton.addActionListener(new searchListener());
		
		redrawPanel();

		

		add(scrPane, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(botPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		
		
	}



	//This is testing that we are able to print image of listings
	public JLabel addImage(String imageURL) throws SQLException {

		Image image = null;

		try {
			URL url = new URL(imageURL);
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = image.getScaledInstance(300, 300, Image.SCALE_DEFAULT);

		JLabel label = new JLabel(new ImageIcon(image));

		return label;
	}


	public JButton addHyperlink(String listingURL) throws SQLException {

		JButton hyperlink = new JButton("Click to visit listing.");
		hyperlink.setSize(100, 50);
		hyperlink.setForeground(Color.BLUE.darker());
		hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		hyperlink.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					Desktop.getDesktop().browse(new URI(listingURL));

				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// the mouse has entered the label
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// the mouse has exited the label
			}
		});

		return hyperlink;

	}
	
	public void redrawPanel() {
		
		try {
			for(int i = resultCounter * 10; i < (resultCounter * 10) + 10; i++) {
				results.next();

				JPanel tempPanel = new JPanel();
				JPanel rightPanel = new JPanel();
				JPanel leftPanel = new JPanel();
				leftPanel.setLayout(new GridLayout(2,0));

				JPanel botLeftPanel = new JPanel();

				try {
					JLabel neighborhood = new JLabel(results.getString(4));
					neighborhood.setPreferredSize(new Dimension(200,100));
					neighborhood.setMinimumSize(new Dimension(200,100));
					leftPanel.add(neighborhood);
				} catch (SQLException e) {
					e.printStackTrace();
				}


				try {
					botLeftPanel.add(addHyperlink(results.getString(2)));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JLabel safety = new JLabel("Safety Score");
				botLeftPanel.add(safety);
				leftPanel.add(botLeftPanel);

				try {
					rightPanel.add(addImage(results.getString(3)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				tempPanel.add(leftPanel);
				tempPanel.add(rightPanel);
				tempPanel.setBorder(new EtchedBorder());
				container.add(tempPanel);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
