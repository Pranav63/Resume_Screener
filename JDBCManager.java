package recruit;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class JDBCManager 
{
	public final String DBURL = "jdbc:mysql://localhost/recruit";
	public Connection connection=null;
	public Statement statement=null;
	public ResultSet resultSet;

	public JDBCManager()
	{	
		try
		{
		connection = DriverManager.getConnection(DBURL,"root","pranav63");
		statement = connection.createStatement();
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
	}
	
	public void CloseDB()throws SQLException
	{
		connection.close();
		statement.close();
	}
}
