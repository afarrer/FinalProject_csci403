import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLExecution {
	private Connection db;
	
	public SQLExecution() {
	
		//This commented out stuff was for me to be able to just put login info in file and not have
		//to enter it in every time.
/*
		FileReader reader = null;
		try {
			reader = new FileReader("login.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Scanner fileScan = new Scanner(reader);
        String username = fileScan.nextLine();
        String password = fileScan.nextLine();
  */
		Scanner scan = new Scanner(System.in);
        
		System.out.println("Userame: ");
		String username = scan.nextLine();
		//I don't know how to make the invisible password thing work
		System.out.println("Password: ");
		String password = scan.nextLine();
        String connectString = "jdbc:postgresql://bartik.mines.edu/csci403";
        
        try {
            db = DriverManager.getConnection(connectString, username, password);
            System.out.println("Successfully connected to database.");
        } 
        catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
            return;
        }
        
        try {
        	//Testing that function works
        	this.makeStatement("Austin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	public ResultSet makeStatement(String city) throws SQLException {
		
		String query = " Select * from listings where city = ?";
		
		PreparedStatement prepared = db.prepareStatement(query);
		
		
		prepared.setString(1, city);
		
		return prepared.executeQuery();
		
	}
	
	
}
