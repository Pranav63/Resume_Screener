package recruit;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class RegisterDB
{
	public RegisterForm registerForm = null;
	public JDBCManager jdbc = null;
	PreparedStatement insertNewPerson = null;
	PreparedStatement insertNewLogin = null;
	String uname,pass,name,mobile,email;
	int count1=0;
	
	public RegisterDB(RegisterForm registerForm,JDBCManager jdbc)
	{
		this.registerForm = registerForm;
		this.jdbc = jdbc;
	}

	public int DBInsert()throws SQLException
	{
		insertNewPerson = jdbc.connection.prepareStatement("INSERT INTO candid"+"(uname,name,mobile,email)"+"VALUES (?,?,?,?)");
		insertNewLogin = jdbc.connection.prepareStatement("INSERT INTO auth"+"(id,pass)"+"VALUES(?,?)");
		uname = registerForm.uname.getText();
		char [] input=registerForm.pass.getPassword();
		pass = String.valueOf(input);
		name = registerForm.name.getText();
		mobile = registerForm.phone.getText();
		email = registerForm.email.getText();
		try
		{
			System.out.print(" "+mobile);
			if(uname.equals("") || pass.equals("") || name.equals("") || mobile.equals("") || email.equals(""))
			{
				JOptionPane.showMessageDialog(new JFrame()," Fields Empty! Fill all the fields! ");
				return 0;
			}
			insertNewPerson.setString(1,uname);
			insertNewPerson.setString(2,name);
			insertNewPerson.setString(3,mobile);
			insertNewPerson.setString(4,email);
			
			insertNewLogin.setString(1,uname);
			insertNewLogin.setString(2,pass);

			
			int count2=0;

			count1 = insertNewPerson.executeUpdate();
			count2 = insertNewLogin.executeUpdate();
			
			if(count1==1 && count2==1)
			{
				JOptionPane.showMessageDialog(new JFrame()," Successfully Registered. You can Proceed after Logging in.");
				return 1;
			}
			
			if(count1==0)
			{
				JOptionPane.showMessageDialog(new JFrame(),"Error in Entry! Please give a unique username, Username may be already existing...");
				return 0;
			}
		}
		
		catch(SQLException e)
		{
			if(count1==0)
			{
				JOptionPane.showMessageDialog(new JFrame(),"Error in Entry! Please give a unique username, Username may be already existing...");
				return 0;
			}
			e.printStackTrace();	
		}
return 0;
	}
}