package serverconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static void main(String[] args) {
		final String ID = "admin";
		final String PW = "COSC*ncm6n";
		final String SERVER = "jdbc:mysql://34.123.199.211:3306/?serverTimezoneEST#/caretrackdb";

		try (Connection con = DriverManager.getConnection(SERVER, ID, PW);){
			System.out.println("Connection successful");

			//all other code goes here

		} catch (SQLException e) {
			System.out.println(e);
		}
    }
}
