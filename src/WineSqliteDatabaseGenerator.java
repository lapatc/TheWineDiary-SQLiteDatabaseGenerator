import java.io.File;
import java.sql.*;

public class WineSqliteDatabaseGenerator {
	
		// Database name
		private static final String DATABASE_NAME = "wines.db";

		// Table Names
		private static final String TABLE_COLOR = "color";
		private static final String TABLE_WINERY = "winery";
		private static final String TABLE_WINERY_LOCATION = "winery_location";
		private static final String TABLE_WINE_GRAPE = "wine_grape";
		private static final String TABLE_WINE = "wine";

		// Common column names
		private static final String KEY_ID = "_id";
		private static final String KEY_NAME = "name";
		private static final String KEY_COLOR = "color";

		// Winery column names
		private static final String KEY_LOCATION_ID = "location_id";

		// WineryLocation column names
		private static final String KEY_COUNTRY = "country";
		private static final String KEY_STATE_PROV = "state_prov";
		private static final String KEY_TOWN = "town";

		// WineGrape column names
		private static final String KEY_WINE_ID = "wine_id";

		// Wine column names
		private static final String KEY_YEAR = "year";
		private static final String KEY_SPARKLING = "sparkling";
		private static final String KEY_ALCOHOL = "alcohol";
		private static final String KEY_DATE_TASTED = "date_tasted";
		private static final String KEY_WINERY_ID = "winery_id";
		private static final String KEY_LABEL_FRONT = "label_front";
		private static final String KEY_LABEL_BACK = "label_back";
		private static final String KEY_TASTING_NOTE = "tasting_note";
		private static final String KEY_PERSONAL_NOTE = "personal_note";

		// table create statements
		// Color table create statement
		private static final String CREATE_TABLE_COLOR = "CREATE TABLE "
				+ TABLE_COLOR + "(" + KEY_COLOR + " TEXT PRIMARY KEY)";

		// WineryLocation table create statement
		private static final String CREATE_TABLE_WINERY_LOCATION = "CREATE TABLE "
				+ TABLE_WINERY_LOCATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_COUNTRY + " TEXT," + KEY_STATE_PROV + " TEXT," + KEY_TOWN
				+ " TEXT)";

		// Winery table create statement
		private static final String CREATE_TABLE_WINERY = "CREATE TABLE "
				+ TABLE_WINERY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
				+ " TEXT NOT NULL," + KEY_LOCATION_ID + " INTEGER,"
				+ "FOREIGN KEY(" + KEY_LOCATION_ID + ") REFERENCES "
				+ TABLE_WINERY_LOCATION + "(" + KEY_ID + ")" + ")";
		
		// Wine table create statement
		private static final String CREATE_TABLE_WINE = "CREATE TABLE "
				+ TABLE_WINE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEAR
				+ " TEXT," + KEY_SPARKLING + " BOOLEAN CHECK(" + KEY_SPARKLING + " IN (0,1))," + KEY_ALCOHOL
				+ " INTEGER," + KEY_NAME + " TEXT NOT NULL," + KEY_DATE_TASTED
				+ " DATE," + KEY_WINERY_ID + " INTEGER," + KEY_COLOR
				+ " TEXT NOT NULL," + KEY_LABEL_FRONT + " TEXT," + KEY_LABEL_BACK
				+ " TEXT," + KEY_TASTING_NOTE + " TEXT," + KEY_PERSONAL_NOTE
				+ " TEXT," + "FOREIGN KEY (" + KEY_WINERY_ID + ") REFERENCES "
				+ TABLE_WINERY + "(" + KEY_ID + ")," + "FOREIGN KEY (" + KEY_COLOR
				+ ") REFERENCES " + TABLE_COLOR + "(" + KEY_COLOR + ")" + ")";

		// WineGrape table create statement
		private static final String CREATE_TABLE_WINE_GRAPE = "CREATE TABLE "
				+ TABLE_WINE_GRAPE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_NAME + " TEXT NOT NULL," + KEY_WINE_ID + " INTEGER NOT NULL,"
				+ "FOREIGN KEY (" + KEY_WINE_ID + ") REFERENCES " + TABLE_WINE
				+ "(" + KEY_ID + ")" + ")";
		
		

		
	public static void main(String args[]){
		Connection c = null;
		Statement stmt = null;
		
		File dbFile = new File("databases/" + DATABASE_NAME);
		
		if(dbFile.exists()){
			dbFile.delete();
		}
		
		String[] createStatements = {CREATE_TABLE_COLOR, CREATE_TABLE_WINERY_LOCATION, CREATE_TABLE_WINERY, CREATE_TABLE_WINE, CREATE_TABLE_WINE_GRAPE};
		String[] insertColors = {
				"INSERT INTO " + TABLE_COLOR + "(" + KEY_COLOR + ")" + " VALUES('RED');",
				"INSERT INTO " + TABLE_COLOR + "(" + KEY_COLOR + ")" + " VALUES('WHITE');",
				"INSERT INTO " + TABLE_COLOR + "(" + KEY_COLOR + ")" + " VALUES('ROSE');"
		};
		
		String[] insertWineryLocations = {
				"INSERT INTO " + TABLE_WINERY_LOCATION + "(" + KEY_COUNTRY + "," + KEY_STATE_PROV + "," + KEY_TOWN + ") VALUES('USA', 'CALIFORNIA', 'TEMECULA');",
				"INSERT INTO " + TABLE_WINERY_LOCATION + "(" + KEY_COUNTRY + "," + KEY_STATE_PROV + "," + KEY_TOWN + ") VALUES('USA', 'CALIFORNIA', 'NAPA VALLEY');",
				"INSERT INTO " + TABLE_WINERY_LOCATION + "(" + KEY_COUNTRY + "," + KEY_STATE_PROV + ") VALUES('ITALY', 'VENETO');",
				
		};
		
		String[] insertWineries = {
				"INSERT INTO " + TABLE_WINERY + "(" + KEY_NAME + "," + KEY_LOCATION_ID +")" + " VALUES('WINERY1', 1);",
				"INSERT INTO " + TABLE_WINERY + "(" + KEY_NAME + "," + KEY_LOCATION_ID +")" + " VALUES('WINERY2', 2);"
		};
		
		String[] insertWines = {
				"INSERT INTO " + TABLE_WINE  + "(" + KEY_NAME + "," + KEY_COLOR + "," + KEY_YEAR + "," + KEY_SPARKLING + "," + KEY_ALCOHOL + "," + KEY_DATE_TASTED + "," + KEY_WINERY_ID + "," + KEY_LABEL_FRONT + "," + KEY_LABEL_BACK + "," + KEY_TASTING_NOTE + "," + KEY_PERSONAL_NOTE + ")" +
						" VALUES('WINE1', 'WHITE', 2009, 0, 10.5,'2010-07-03', 1, 'sample-image1.jpg', 'sample-image2.jpg', 'Light and mineral with a hint of lemon.', 'Wow, so amazing!');",
				"INSERT INTO " + TABLE_WINE  + "(" + KEY_NAME + "," + KEY_COLOR + "," + KEY_YEAR + "," + KEY_SPARKLING + "," + KEY_ALCOHOL + "," + KEY_DATE_TASTED + "," + KEY_WINERY_ID + "," + KEY_LABEL_FRONT + "," + KEY_LABEL_BACK + "," + KEY_TASTING_NOTE + "," + KEY_PERSONAL_NOTE + ")" +
						" VALUES('WINE2', 'RED', 2011, 1, 14,'2012-12-25', 2, 'sample-image3.jpg', 'sample-image4.jpg', 'Ruby red with flavours of fresh berries.', 'Gimme more!!!!');"
						
		};
		
		String[] insertWineGrapes = {
				"INSERT INTO " + TABLE_WINE_GRAPE + "(" + KEY_NAME + "," + KEY_WINE_ID + ")" +
						" VALUES('VERDICCHIO', 1);",
				"INSERT INTO " + TABLE_WINE_GRAPE + "(" + KEY_NAME + "," + KEY_WINE_ID + ")" +
						" VALUES('TREBBIANO', 1);",
				"INSERT INTO " + TABLE_WINE_GRAPE + "(" + KEY_NAME + "," + KEY_WINE_ID + ")" +
						" VALUES('ROMAGNOLO', 1);",
				"INSERT INTO " + TABLE_WINE_GRAPE + "(" + KEY_NAME + "," + KEY_WINE_ID + ")" +
						" VALUES('MALVASIA', 1);",
				"INSERT INTO " + TABLE_WINE_GRAPE + "(" + KEY_NAME + "," + KEY_WINE_ID + ")" +
						" VALUES('MERLOT', 2);",
		};
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			c = DriverManager.getConnection("jdbc:sqlite:databases/" + DATABASE_NAME);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			
			// enforce foreign key constraints
			stmt.execute("PRAGMA foreign_keys = ON");
			
			for(String createStmt : createStatements){
				stmt.executeUpdate(createStmt);
			}
			
			for(String insertStmt : insertColors){
				stmt.executeUpdate(insertStmt);
			}
			
			for(String insertStmt : insertWineryLocations){
				stmt.executeUpdate(insertStmt);
			}
			
			for(String insertStmt : insertWineries){
				stmt.executeUpdate(insertStmt);
			}
			
			for(String insertStmt : insertWines){
				stmt.executeUpdate(insertStmt);
			}
			
			for(String insertStmt : insertWineGrapes){
				stmt.executeUpdate(insertStmt);
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}finally{
			try{
				if(stmt != null){
					stmt.close();
				}
			}catch(SQLException e){
				System.err.println("Error closing the SQL statement!");
			}
			
			try{
				if(c != null){
					c.close();
				}
			}catch(SQLException e){
				System.err.println("Error closing the database connection!");
			}
		}
	}
}
