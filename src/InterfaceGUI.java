import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfaceGUI extends JFrame{
	SQLExecution sqlHandler;
	
	public InterfaceGUI() {
		sqlHandler = new SQLExecution();
		
		try {
			this.addImage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setSize(800,500);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	//This is testing that we are able to print image of listings
	public void addImage() throws SQLException {
		ResultSet results = sqlHandler.makeStatement("Austin");
		//Adding more of these will cycle through different listings
		results.next();
		results.next();
		results.next();

		Image image = null;
        try {
            URL url = new URL(results.getString(3));
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        
        JLabel label = new JLabel(new ImageIcon(image));
        add(label);
	}
	
}
