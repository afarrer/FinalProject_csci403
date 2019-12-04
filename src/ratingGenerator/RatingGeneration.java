package ratingGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import rentalSafety.SQLExecution;

public class RatingGeneration {
	private SQLExecution sqlHandler;
	ResultSet allListings;
	ResultSet allCrimes;
	Map<Integer, Integer> rawMap;

	public RatingGeneration() {
		sqlHandler = new SQLExecution();
	}

	//It took over an hour (and was still running) when I tried to do all listings at once (Likely because not all crime have crime data)
	
	//So I am instead loading them individually
	//So far I have added all that have crimes in the crimes table
	
	public void generate() {
		try {
			allListings = sqlHandler.makeStatement("Washington");
			allCrimes = sqlHandler.makeCrimeStatement("Washington_dc");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			rawMap = CoordinateMath.distanceMap(allListings, allCrimes);
			allListings = sqlHandler.makeStatement("Washington");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while(allListings.next()) {
				sqlHandler.insertRatings(allListings.getInt(1), safetyScore(allListings.getInt(1),rawMap));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int safetyScore(int ID, Map<Integer, Integer> crimes) {
		if(crimes.get(ID) != null) {
			int crime = crimes.get(ID);
			crime = crime / 1000;
			int output;

			if(crime < 4) {
				output = 5;
			}else if(crime < 8) {
				output = 4;
			}else if(crime < 12) {
				output = 3;
			}else if(crime < 15) {
				output = 2;
			}else {
				output = 1;
			}

			return output;
		}else {
			return 1;
		}
	}

	public static void main(String[] args) {
		RatingGeneration generator = new RatingGeneration();
		generator.generate();
	}
}







