import java.sql.SQLException;

public class DrawInterface {
	private InterfaceGUI programInterface;
	
	public DrawInterface() throws SQLException {
		this.programInterface = new InterfaceGUI();
	}
	
	
	
	public static void main(String[] args) throws SQLException {
		DrawInterface newInterface = new DrawInterface();
	}
}
