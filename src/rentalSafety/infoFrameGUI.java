package rentalSafety;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class infoFrameGUI extends JFrame{
	
	public infoFrameGUI() {
		this.setSize(500, 200);
		this.setTitle("About");
		
		ImageIcon img = new ImageIcon("infoLogo.png");
		this.setIconImage(img.getImage());
		JTextArea info = new JTextArea();
		
		String aboutInfo =
				"Rental Safety's safety scores are based off of the number of crimes committed within \n"
				+ "a two mile radius of the listings location. Crime information is sourced from public police \n"
				+ "records published within the last eight years. 'Crime Safety' in this application has no \n"
				+ "legal or standardized definition and is used purely to organize the listings.";
		
		info.setText(aboutInfo);
		this.add(info);
		this.setVisible(true);
	}
	
	
	
	
}
