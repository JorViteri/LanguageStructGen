import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	static private String host;
	static private String database; 
	static private  String user;
	static private String passwd ;
	static private String esp = "spa";
	static private String eng = "eng";

	public MySQLAccess(){
		
	}
	
	@SuppressWarnings("finally")
	public ArrayList<String> wordnetQuery(String iliOffset, boolean option){ //TODO INDICAR EL IDIOMA QUE HAY QUE PILLAR !! IMPORTANTE
		ArrayList<String> result = new ArrayList<>();
		String offset, lang;
		//String host, database, user, passwd;
		Properties prop = new Properties();
		InputStream input;
		if (option){ //tengo que hacerme una clase que use properties
			lang=esp;
		}else{
			lang=eng;
		}
		
		try {
			input =  new FileInputStream("configuration.properties");
			prop.load(input);
			host=prop.getProperty("dbhost");
			database= prop.getProperty("database");
			user=prop.getProperty("dbuser");
			passwd = prop.getProperty("dbpassword");
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(prop.getProperty("dbdriver"));
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"+ database + "?" + "user=" + user + "&password=" + passwd);
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("SELECT Offset FROM `wei_"+lang+"-30_to_ili` WHERE iliOffset='"+iliOffset+"'");
			resultSet.next();
			offset = resultSet.getString("offset");
			resultSet = statement.executeQuery("SELECT word FROM `wei_"+lang+"-30_variant` WHERE Offset ='"+offset+"'");
			//writeResultSet(resultSet);
			while (resultSet.next()) {
				String word = resultSet.getString("word");
				result.add(word);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
			return result;
		}
	}
	
	/*public void readDataBase() throws Exception {
		String offset = "";
		Properties prop = new Properties();
		InputStream input;
		try {
			input =  new FileInputStream("config.properties");
			prop.load(input);
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(prop.getProperty("dbdriver"));
			host=prop.getProperty("dbhost");
			database= prop.getProperty("database");
			user=prop.getProperty("dbuser");
			passwd = prop.getProperty("dbpassword");
			passwd="SMTOyakata1993IIIGlassex_spaniEN";
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"+ database + "?" + "user=" + user + "&password=" + passwd);
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("SELECT Offset FROM `wei_spa-30_variant` WHERE Word='libro'");
			resultSet.next();
			offset = resultSet.getString("offset");
			resultSet = statement.executeQuery("SELECT word FROM `wei_spa-30_variant` WHERE Offset ='"+offset+"'");
			writeResultSet(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}*/

	


	/*private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String word = resultSet.getString("word");
			System.out.println("Word: " + word);
		}
	}*/

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
