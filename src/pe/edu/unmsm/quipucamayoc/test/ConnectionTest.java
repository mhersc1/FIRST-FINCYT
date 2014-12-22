package pe.edu.unmsm.quipucamayoc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void test() throws SQLException{
		
		
        try
        {
            //Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=cjavaperu");
            Connection conn =(Connection)  DriverManager.getConnection("jdbc:mysql://localhost:3306/QPLOGISTICA", "root", "cjavaperu");
            Statement   s = (Statement) conn.createStatement();
            int result = s.executeUpdate("CREATE DATABASE databasename");
        }


        catch ( Exception e)
        {
            e.printStackTrace();
        }
	}

}
