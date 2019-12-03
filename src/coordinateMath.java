import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class coordinateMath {

	static SQLExecution sqlHandler;
	//Function that takes the listings and crimes in the given cities and uses the haversine formula to determine if the crime was within 
	//2 miles of the listing. If it is, the counter increments and then a map is created hat links listings with a number of crime value
public static HashMap distanceMap(ResultSet rs1, ResultSet rs2) throws SQLException {
	
	
	HashMap<Integer, Integer> crimeMap = new HashMap<>();
	
	
	
	
	ArrayList<Pair> listingGeo = new ArrayList<>();
	ArrayList<Pair> crimeGeo = new ArrayList<>();
	ArrayList<Integer> IDList = new ArrayList<>();
	
	while(rs1.next()) {
		IDList.add((int) rs1.getInt(1));
		Pair temp = new Pair(rs1.getFloat(8),rs1.getFloat(7));
		listingGeo.add(temp);
	}
	while(rs2.next()) {
		Pair temp = new Pair(rs2.getFloat(3),rs2.getFloat(2));
		crimeGeo.add(temp);

	}
	
	int i = 0;
	
	for (Pair lg: listingGeo) {
		
		int ID = IDList.get(i);
		int crimeCount = 0;
		double x1 = lg.lon;
		double y1 = lg.lat;
		double x1rad = Math.toRadians(x1);
		double y1rad = Math.toRadians(y1);
		for (Pair cg: crimeGeo) {
			double distance;
			double x2 = cg.lon;
			double y2 = cg.lat;
			double x2rad = Math.toRadians(x2);
			double y2rad = Math.toRadians(y2);
			
			double earthRad = 6371e3;
			
			double deltaLong = Math.toRadians((x2-x1));
			double deltaLat = Math.toRadians((y2-y1));
			
			double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(y1rad) * Math.cos(y2rad) * Math.sin(deltaLong/2) * Math.sin(deltaLong/2);
			
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			
			double distanceKM = earthRad * c;
			
			distance = distanceKM/1.609344/1000;
			
			
			if (distance <= 2) {
				crimeCount++;
			}
			
		}
		
		crimeMap.put(ID, crimeCount);
	}
	
	
	
	return crimeMap;
	
}
	
}
