import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InterfaceGUI extends JFrame{
	SQLExecution sqlHandler;

	public InterfaceGUI() {
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sqlHandler = new SQLExecution();
		ResultSet results = null;
		try {
			results = sqlHandler.makeStatement("Austin");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JPanel container = new JPanel();
		JScrollPane scrPane = new JScrollPane(container);


		try {
			for(int i = 0; i < 10; i++) {
				results.next();
				try {
					container.add(addHyperlink(results.getString(2)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				try {
					container.add(addImage(results.getString(3)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(scrPane, BorderLayout.CENTER);
		this.repaint();
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
		image = image.getScaledInstance(400, 400, Image.SCALE_DEFAULT);

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

}
