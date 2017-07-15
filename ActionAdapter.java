package recruit;

import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.sql.SQLException;
import javax.swing.JPasswordField;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;


public class ActionAdapter implements ActionListener
{
	public RegisterForm registerForm=null;
	public LoginForm loginForm=null;
	public JDBCManager jdbc=null;
	public UploadForm upload=null;
	JFrame frame = new JFrame();
	public String uname1,action;
	String pass;
	public RegisterDB regDB = null;
	public RuleForm ruleForm = null;
	AccountRecord record = null;
	ReadTextFile readFile = null;
	public AdminForm adminForm = null;

	public ActionAdapter(LoginForm loginForm,RegisterForm registerForm,JDBCManager jdbc,UploadForm upload,RuleForm ruleForm,AdminForm adminForm)throws Exception
	{
		this.registerForm = registerForm;
		this.loginForm = loginForm;
		this.jdbc = jdbc;
		this.upload = upload;
		this.ruleForm = ruleForm;
		this.adminForm = adminForm;
		record = new AccountRecord();
		readFile = new ReadTextFile(upload);
		regDB = new RegisterDB(registerForm,jdbc);
		registerForm.dispose();
	}

	public void actionPerformed(ActionEvent e)
	{
		uname1=loginForm.uname.getText();

		char [] input=loginForm.pass.getPassword();
		pass = String.valueOf(input);
		action = e.getActionCommand();
		if(action == "resetlogin")
		{
			loginForm.uname.setText("");
			loginForm.pass.setText("");
		}
		if(action == "register")
		{
			loginForm.dispose();
			registerForm.show();
		}

		if(action == "Reset")
		{
			registerForm.uname.setText("");
			registerForm.pass.setText("");
			registerForm.name.setText("");
			registerForm.phone.setText("");
			registerForm.email.setText("");
		}
		if(action == "Back")
		{
			registerForm.dispose();
			loginForm.show();
		}

		if(action == "login")
		{
			try
			{
				loginCheck();
			}
			catch(SQLException e1)
			{
				e1.printStackTrace();
			}

		}

		if(action == "RegisterForm")
		{
			try
			{
				insertUserDB();
			}
			catch(SQLException e2)
			{
				e2.printStackTrace();
			}
		}

		if(action == "resumerule")
		{
			ruleForm.show();
			Align.set(ruleForm,800,800);
			upload.dispose();
			loginForm.dispose();
			registerForm.dispose();
		}

		if(action == "backUpload")
		{
			try
			{
				upload.show();
				upload.checkSelect();
				ruleForm.dispose();
				loginForm.dispose();
				registerForm.dispose();
			}
			catch(SQLException a)
			{
				a.printStackTrace();
			}
		}

		if(action == "browse")
		{
			Chooser frame = new Chooser();
			upload.uploadField.setText(frame.fileName);
		}

		if(action == "resumeupload")
		{
			try
			{
			resumeCheck();
			upload.show();
			upload.checkSelect();
			}
			catch(SQLException e3)
			{
			e3.printStackTrace();
			}
		}

		if(action == "logout")
		{
			upload.dispose();
			loginForm.show();
			adminForm.dispose();
			loginForm.uname.setText("");
			loginForm.pass.setText("");
		}

		if(action == "resetpost")
		{
			adminForm.post.setText("");
			adminForm.postNumText.setText("");
			adminForm.postCritText.setText("");
			adminForm.expYear.setText("");
		}

		if(action == "deletepost")
		{
			try
			{
			deletepost();
			}
			catch(SQLException sql)
			{
				sql.printStackTrace();
			}
		}

		if(action == "updatepost")
		{
			try
			{
				updateVacancyCrit();
			}
			catch(Exception sql1)
			{
				sql1.printStackTrace();
			}
		}
	}

	public void updateVacancyCrit()throws SQLException
	{
		String postName = adminForm.post.getText().toString();
		int confirm1 = 0;
		int postNum = 0;
		String postNumStr = "";
		String yearStr = "";
 		int year = 0;
		int confirm2 = 0;
		String selectCommand = "";
		String foundPost = "";

		postNumStr = adminForm.postNumText.getText().toString();
		yearStr = adminForm.expYear.getText().toString();
		String postCrit = adminForm.postCritText.getText().toString();

		if(postName.equals(""))
		{
			JOptionPane.showMessageDialog(new JFrame()," Please enter all the fields!");
			return;
		}

		else
		{
			String falseStr = "";
			selectCommand = "select * from vacancy where post='"+postName+"'";

			jdbc.resultSet = jdbc.statement.executeQuery(selectCommand);


//			jdbc.resultSet.next();

			while(!jdbc.resultSet.next())
			{
				if(postNumStr.equals("") || yearStr.equals("") || postCrit.equals(""))
				{
					JOptionPane.showMessageDialog(new JFrame(),"Please Enter all the fields!");
					return;
				}

				else
				{
					try
					{
						postNum = Integer.parseInt(postNumStr);
						year = Integer.parseInt(yearStr);
					}
					catch(Exception sql3)
					{
						JOptionPane.showMessageDialog(new JFrame()," The Experience and the number of posts must be a number!");
						return;
					}
				}

				System.out.println("entered");
				selectCommand = "insert into vacancy values('"+postName+"',"+postNum+");";

				confirm1 = jdbc.statement.executeUpdate(selectCommand);

				selectCommand = "insert into criteria values('"+postName+"','"+postCrit+"',"+year+");";

				confirm2 = jdbc.statement.executeUpdate(selectCommand);

				if(confirm1 == 1 && confirm2 == 1)
				{
					JOptionPane.showMessageDialog(new JFrame()," Successfully updated with new post!");
					return;
				}
			}

				if( !(postNumStr.equals("")) && !(postCrit.equals("")) && !(yearStr.equals("")) )
				{
					try
					{
						postNum = Integer.parseInt(postNumStr);
						year = Integer.parseInt(yearStr);
					}
					catch(Exception sql4)
					{
						JOptionPane.showMessageDialog(new JFrame(),"Please enter number values in number of post and year fields.");
						return;
					}
					selectCommand = "update vacancy set no="+postNum+" where post='"+postName+"';";

					confirm1 = jdbc.statement.executeUpdate(selectCommand);

					selectCommand = "update criteria set crit='"+postCrit+"',exp="+year+" where post='"+postName+"'";

					confirm2 = jdbc.statement.executeUpdate(selectCommand);
				}
				else if( !(postNumStr.equals("")) && !(postCrit.equals("")) && (yearStr.equals("")) )
				{
					try
					{
						postNum = Integer.parseInt(postNumStr);
					}
					catch(Exception sql5)
					{
						JOptionPane.showMessageDialog(new JFrame()," Please enter number values in the number of post and year fields.");
						return;
					}

                                        selectCommand = "update vacancy set no="+postNum+" where post='"+postName+"';";

					confirm1 = jdbc.statement.executeUpdate(selectCommand);

					selectCommand = "update criteria set crit='"+postCrit+"' where post='"+postName+"'";

					confirm2 = jdbc.statement.executeUpdate(selectCommand);

				}
                                else if( !(postNumStr.equals("")) && (postCrit.equals("")) && !(yearStr.equals("")) )
				{
                                        try
                                        {
                                                postNum = Integer.parseInt(postNumStr);
						year = Integer.parseInt(yearStr);
                                        }
                                        catch(Exception sql6)
                                        {
                                                JOptionPane.showMessageDialog(new JFrame()," Please enter number values in the number of post and year fields.");
						return;
                                        }

                                        selectCommand = "update vacancy set no="+postNum+" where post='"+postName+"';";

                                        confirm1 = jdbc.statement.executeUpdate(selectCommand);

                                        selectCommand = "update criteria set exp="+year+" where post='"+postName+"'";

                                        confirm2 = jdbc.statement.executeUpdate(selectCommand);

				}
                                else if( !(postNumStr.equals("")) && (postCrit.equals("")) && (yearStr.equals("")) )
				{
                                        try
                                        {
                                                postNum = Integer.parseInt(postNumStr);
                                        }
                                        catch(Exception sql7)
                                        {
                                                JOptionPane.showMessageDialog(new JFrame()," Please enter number values in the number of post and year fields");
						return;
                                        }

                                        selectCommand = "update vacancy set no="+postNum+" where post='"+postName+"';";

                                        confirm1 = jdbc.statement.executeUpdate(selectCommand);

                                        confirm2 = 1;

				}
                                else if( (postNumStr.equals("")) && !(postCrit.equals("")) && !(yearStr.equals("")) )
				{
                                        try
                                        {
                                                year = Integer.parseInt(yearStr);
                                        }
                                        catch(Exception sql8)
                                        {
                                                JOptionPane.showMessageDialog(new JFrame()," Please enter number values in the number of post and year fields");
						return;
                                        }

                                        confirm1 = 1;

                                        selectCommand = "update criteria set crit='"+postCrit+"',exp="+year+" where post='"+postName+"'";

                                        confirm2 = jdbc.statement.executeUpdate(selectCommand);

				}
                                else if( (postNumStr.equals("")) && !(postCrit.equals("")) && (yearStr.equals("")) )
				{

					selectCommand = "update criteria set crit='"+postCrit+"' where post='"+postName+"'";

					confirm1 = jdbc.statement.executeUpdate(selectCommand);

					confirm2 = 1;
				}
                                else if( (postNumStr.equals("")) && (postCrit.equals("")) && !(yearStr.equals("")) )
				{
                                        try
                                        {
                                                year = Integer.parseInt(yearStr);
                                        }
                                        catch(Exception sql9)
                                        {
                                                JOptionPane.showMessageDialog(new JFrame()," Please enter number values in the number of post and year fields!");
						return;
                                        }

                                        confirm1 = 1;

                                        selectCommand = "update criteria set exp='"+year+"' where post='"+postName+"'";

                                        confirm2 = jdbc.statement.executeUpdate(selectCommand);

				}
                                else if( (postNumStr.equals("")) && (postCrit.equals("")) && (yearStr.equals("")) )
				{
					JOptionPane.showMessageDialog(new JFrame(),"All the Fields are empty! please enter fields to update.");
					return;
				}
				System.out.println("entered else");

				if(confirm1 == 1 && confirm2 == 1)
				{
					JOptionPane.showMessageDialog(new JFrame(),"Successfully updated!");
					adminForm.post.setText("");
					adminForm.postNumText.setText("");
					adminForm.expYear.setText("");
					adminForm.postCritText.setText("");
				}

		}
	}
	public void deletepost()throws SQLException
	{
		String reqDel = adminForm.post.getText();
		String selectCommand = "select * from vacancy where post='"+reqDel+"'";
		boolean found = false;
		int postrem = 0;
		int confirm = 0;

		jdbc.resultSet = jdbc.statement.executeQuery(selectCommand);

		while(jdbc.resultSet.next())
		{
			if(reqDel.equals(jdbc.resultSet.getObject(1).toString()))
			{
				postrem = Integer.parseInt(jdbc.resultSet.getObject(2).toString());
				found = true;
			}
		}

		if(found==true && postrem>0)
		{
			confirm = JOptionPane.showConfirmDialog(new JFrame()," You have posts vacant in this, do you wish to delete?");

			if(confirm == JOptionPane.YES_OPTION)
			{
				deletePostDB(reqDel);
				return;
			}
			else
			{
				adminForm.post.setText("");
				return;
			}
		}

		if(found==true && postrem==0)
		{
			deletePostDB(reqDel);
		}

		else
		{
			JOptionPane.showMessageDialog(new JFrame(),"No such posts found!");
		}
	}

	public void deletePostDB(String reqDel)throws SQLException
	{
			String selectCommand = "";
			int confirm1 = 0;
			int confirm2 = 0;
                        selectCommand = "delete from vacancy where post='"+reqDel+"'";
                        confirm1 = jdbc.statement.executeUpdate(selectCommand);

                        selectCommand = "delete from criteria where post='"+reqDel+"'";
                        confirm2 = jdbc.statement.executeUpdate(selectCommand);

                        if(confirm1==1 && confirm2==1)
                        {
                                JOptionPane.showMessageDialog(new JFrame(),"Successfully deleted the post from the database!");
                        }
			adminForm.post.setText("");
			return;

	}
	public void insertUserDB()throws SQLException
	{
		int check=0;

		check = regDB.DBInsert();

		if(check==1)
		{
			registerForm.dispose();
			loginForm.show();
		}
	}
	public void loginCheck()throws SQLException
	{

		int count=0;
		if(uname1.equals("admin") && pass.equals("admin"))
		{
			adminForm.show();
			loginForm.dispose();
		}

		else
		{
		jdbc.resultSet = jdbc.statement.executeQuery("select id,pass from auth");
			String unamedb;
			String passdb;

			while(jdbc.resultSet.next())
			{
				unamedb=jdbc.resultSet.getObject(1).toString();
				passdb=jdbc.resultSet.getObject(2).toString();
				if(uname1.equals(unamedb))
				{
					if(pass.equals(passdb))
					{

					count++;
					break;
					}
				}
			}
			if(count==0)
				{
					loginForm.show();
					registerForm.dispose();
				}
		}
		if(uname1.equals("admin"))
		{
			adminForm.show();
			Align.set(adminForm,700,700);
			loginForm.dispose();
			registerForm.dispose();
		}

		else if(count==1)
		{
			upload.show();
			Align.set(upload,400,400);
			upload.checkSelect();
			loginForm.dispose();
			registerForm.dispose();
		}
	}

	public void resumeCheck()throws SQLException
	{
		jdbc.resultSet = jdbc.statement.executeQuery("select * from selected ");

		int confirm;
		int selected=0;
		int resume=0;
		String unamedb;
		while(jdbc.resultSet.next())
		{
			unamedb=jdbc.resultSet.getObject(1).toString();
			if(uname1.equals(unamedb))
			{
				selected=1;
				confirm = JOptionPane.showConfirmDialog(new JFrame(),"Your have been selected! you cannot opt for other jobs you can only update minor details! Do you wish to Continue?");


				if(confirm == JOptionPane.YES_OPTION)
				{
					updateResumeSelected();
				}

				else if(confirm == JOptionPane.NO_OPTION)
				{
					upload.uploadField.setText("");
					return;
				}

				else
				{
				}
			}
		}

		if(selected==0)
		{
		jdbc.resultSet = jdbc.statement.executeQuery("select * from resume");

		while(jdbc.resultSet.next())
		{
			unamedb=jdbc.resultSet.getObject(1).toString();

			if(uname1.equals(unamedb))
			{
				resume=1;
				updateResumeSelected();
			}
		}
		}

		if(resume==0)
		{
			if(upload.uploadField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(new JFrame(),"Please enter your resume's path!");
				return;
			}

			else
			{
				readFile.openFile();
				record = readFile.readRecords();
				readFile.closeFile();
				writeResume();
			}
		}
	}

	public void updateResumeSelected()throws SQLException
	{

                jdbc.resultSet = jdbc.statement.executeQuery("select * from selected where uname='"+uname1+"'");

                while(jdbc.resultSet.next())
                {
                        if((jdbc.resultSet.getObject(1).toString()).equals(loginForm.uname.getText()))
                        {

		                jdbc.resultSet = jdbc.statement.executeQuery("select * from resume where uname='"+uname1+"';");

		                String username = "";
		                String dob = "";
		                String sex = "";
		                String addr = "";
		                String qual = "";
		                String expwork = "";
		                int expyear = 0;
		                String postapp = "";


		                while(jdbc.resultSet.next())
		                {
                		        username = jdbc.resultSet.getObject(2).toString();
		                        dob = jdbc.resultSet.getObject(3).toString();
		                        sex = jdbc.resultSet.getObject(4).toString();
		                        addr = jdbc.resultSet.getObject(5).toString();
		                        qual = jdbc.resultSet.getObject(6).toString();
		                        expwork = jdbc.resultSet.getObject(7).toString();
		                        expyear = Integer.parseInt(jdbc.resultSet.getObject(8).toString());
		                        postapp = jdbc.resultSet.getObject(9).toString();
		                }

		                readFile.openFile();
		                record = readFile.readRecords();
		                readFile.closeFile();


		                if(!uname1.equals(record.getUsername()) || !username.equals(record.getName()) || !sex.equals(record.getSex()) || !postapp.equals(record.getApply()) || !dob.equals(record.getDOB()))
		                {
		                        JOptionPane.showMessageDialog(new JFrame()," You cannot alter these basic details.");
		                        return;
		                }

				if(jdbc.statement.executeUpdate("update resume set address='"+record.getAddr()+"',qualification='"+record.getQualification()+"',expwork='"+record.getExpProf()+"',expyear='"+record.getExpYear()+"' where uname='"+uname1+"'") == 1)
				{
					JOptionPane.showMessageDialog(new JFrame()," Successfully Updated your resume!");
					return;
				}
                	}
		}

               jdbc.resultSet = jdbc.statement.executeQuery("select * from resume where uname='"+uname1+"';");

               String username = "";
               String dob = "";
               String sex = "";
               String addr = "";
               String qual = "";
               String expwork = "";
               int expyear = 0;
               String postapp = "";


               while(jdbc.resultSet.next())
               {
     	       		username = jdbc.resultSet.getObject(2).toString();
                        dob = jdbc.resultSet.getObject(3).toString();
                        sex = jdbc.resultSet.getObject(4).toString();
                        addr = jdbc.resultSet.getObject(5).toString();
                        qual = jdbc.resultSet.getObject(6).toString();
                        expwork = jdbc.resultSet.getObject(7).toString();
                        expyear = Integer.parseInt(jdbc.resultSet.getObject(8).toString());
                        postapp = jdbc.resultSet.getObject(9).toString();
               }

                                readFile.openFile();
                                record = readFile.readRecords();
                                readFile.closeFile();


                                if(!uname1.equals(record.getUsername()) || !username.equals(record.getName()) || !sex.equals(record.getSex()) || !dob.equals(record.getDOB()))
                                {
                                        JOptionPane.showMessageDialog(new JFrame()," You cannot alter these basic details.");
                                        return;
                                }

                                if(jdbc.statement.executeUpdate("update resume set address='"+record.getAddr()+"',qualification='"+record.getQualification()+"',expwork='"+record.getExpProf()+"',expyear='"+record.getExpYear()+"',apply='"+record.getApply()+"' where uname='"+uname1+"'") == 1)
                                {
                                        JOptionPane.showMessageDialog(new JFrame()," Successfully Updated your resume!");
					selectCandid();
                                        return;
                                }

	}


	public void writeResume()throws SQLException
	{
		String insertQuery;
		readFile.openFile();
		record = readFile.readRecords();
		readFile.closeFile();
		insertQuery = "insert into resume values('"+record.getUsername()+"','"+record.getName()+"','"+record.getDOB()+"','"+record.getSex()+"','"+record.getAddr()+"','"+record.getQualification()+"','"+record.getExpProf()+"',"+record.getExpYear()+",'"+record.getApply()+"')";

		int count = jdbc.statement.executeUpdate(insertQuery);

		if(count==1)
		{
			JOptionPane.showMessageDialog(new JFrame(),"Successfully uploaded!");
			selectCandid();
		}
	}

	public void selectCandid()throws SQLException
	{
		String selectResume = "select * from resume where uname='"+uname1+"';";
		String qualification = "";
		int experience = 0;
		String applyFor = "";
		String reduceVacancy = "";
		String vacancyNumber = "";
		String name = "";
		int vacancyNum = 0;
		int selectInfo=0;
		int vacancyInfo=0;
		String critSelect = "select * from criteria where post='";
		String critqual = "";
		int critexp = 0;

		jdbc.resultSet = jdbc.statement.executeQuery(selectResume);

		while(jdbc.resultSet.next())
		{
			qualification = jdbc.resultSet.getObject(6).toString();
			experience = Integer.parseInt(jdbc.resultSet.getObject(8).toString());
			applyFor = jdbc.resultSet.getObject(9).toString();
			name = jdbc.resultSet.getObject(2).toString();
		}

			critSelect = critSelect + applyFor + "'";

			jdbc.resultSet = jdbc.statement.executeQuery(critSelect);

			while(jdbc.resultSet.next())
			{
				critqual = jdbc.resultSet.getObject(2).toString();
				critexp = Integer.parseInt(jdbc.resultSet.getObject(3).toString());
			}

			if(critqual=="")
			{
				JOptionPane.showMessageDialog(new JFrame()," There is no such post vacant for which you opt!");
				return;
			}

			if((qualification.equals(critqual)) && experience>=critexp)
			{
				String selectedInsert;
				selectedInsert = "insert into selected values('"+uname1+"','"+applyFor+"','"+name+"');";

				selectInfo = jdbc.statement.executeUpdate(selectedInsert);

				vacancyNumber = "select * from vacancy where post='"+applyFor+"';";

				jdbc.resultSet = jdbc.statement.executeQuery(vacancyNumber);

				while(jdbc.resultSet.next())
				{
					vacancyNum=Integer.parseInt(jdbc.resultSet.getObject(2).toString());
				}
				if(vacancyNum==0)
				{
					JOptionPane.showMessageDialog(new JFrame(),"Sorry, Applications closed for this post!");
				}

				else
				{
				vacancyNum-=1;

				reduceVacancy = "update vacancy set no="+vacancyNum+" where post='"+applyFor+"'";

				vacancyInfo = jdbc.statement.executeUpdate(reduceVacancy);
				}
			}

/*		else if((applyFor.equals("staff")))
		{
                        critSelect = critSelect + applyFor + "'";

                        jdbc.resultSet = jdbc.statement.executeQuery(critSelect);

                        while(jdbc.resultSet.next())
                        {
                                critqual = jdbc.resultSet.getObject(2).toString();
                                critexp = Integer.parseInt(jdbc.resultSet.getObject(3).toString());
                        }
                        if((qualification.equals(critqual)) && experience>critexp)
			{
				String selectedInsert;
				selectedInsert = "insert into selected values('"+uname1+"','staff','"+name+"');";

				selectInfo = jdbc.statement.executeUpdate(selectedInsert);

				vacancyNumber = "select * from vacancy where post='"+applyFor+"';";

				jdbc.resultSet = jdbc.statement.executeQuery(vacancyNumber);

			      	while(jdbc.resultSet.next())
				{
					vacancyNum=Integer.parseInt(jdbc.resultSet.getObject(2).toString());
				}
				if(vacancyNum==0)
				{
					JOptionPane.showMessageDialog(new JFrame(),"There is no post vacant for the post you opt!");
				}

				else
				{
				vacancyNum-=1;

				reduceVacancy = "update vacancy set no="+vacancyNum+" where post='staff'";

				vacancyInfo = jdbc.statement.executeUpdate(reduceVacancy);
				}
			}
		}

		else if((applyFor.equals("hod")))
		{
                        critSelect = critSelect + applyFor + "'";

                        jdbc.resultSet = jdbc.statement.executeQuery(critSelect);

                        while(jdbc.resultSet.next())
                        {
                                critqual = jdbc.resultSet.getObject(2).toString();
                                critexp = Integer.parseInt(jdbc.resultSet.getObject(3).toString());
                        }
                        if((qualification.equals(critqual)) && experience>critexp)

			{
				String selectedInsert;
				selectedInsert = "insert into selected values('"+uname1+"','hod','"+name+"');";

				selectInfo = jdbc.statement.executeUpdate(selectedInsert);

				vacancyNumber = "select * from vacancy where post='"+applyFor+"';";

				jdbc.resultSet = jdbc.statement.executeQuery(vacancyNumber);

				while(jdbc.resultSet.next())
				{
					vacancyNum=Integer.parseInt(jdbc.resultSet.getObject(2).toString());
				}
				if(vacancyNum==0)
				{
					JOptionPane.showMessageDialog(new JFrame(),"There is no post vacant for the post you opt!");
				}

				else
				{
				vacancyNum-=1;

				reduceVacancy = "update vacancy set no="+vacancyNum+" where post='hod'";

				vacancyInfo = jdbc.statement.executeUpdate(reduceVacancy);
				}
			}
		}

		else if((applyFor.equals("peon")))
		{
                        critSelect = critSelect + applyFor + "'";

                        jdbc.resultSet = jdbc.statement.executeQuery(critSelect);

                        while(jdbc.resultSet.next())
                        {
                                critqual = jdbc.resultSet.getObject(2).toString();
                                critexp = Integer.parseInt(jdbc.resultSet.getObject(3).toString());
                        }
                        if((qualification.equals(critqual)) && experience>critexp)

			{
				String selectedInsert;
				selectedInsert = "insert into selected values('"+uname1+"','peon','"+name+"');";

				selectInfo = jdbc.statement.executeUpdate(selectedInsert);

				vacancyNumber = "select * from vacancy where post='"+applyFor+"';";

				jdbc.resultSet = jdbc.statement.executeQuery(vacancyNumber);

				while(jdbc.resultSet.next())
				{
					vacancyNum=Integer.parseInt(jdbc.resultSet.getObject(2).toString());
				}
				if(vacancyNum==0)
				{
					JOptionPane.showMessageDialog(new JFrame(),"There is no post vacant for the post you opt!");
				}

				else
				{
				vacancyNum-=1;

				reduceVacancy = "update vacancy set no="+vacancyNum+" where post='peon'";

				vacancyInfo = jdbc.statement.executeUpdate(reduceVacancy);
				}
			}
		}*/

		else
		{
			JOptionPane.showMessageDialog(new JFrame(),"Sorry, You are not elligible for the post!");
		}

		if(selectInfo==1 && vacancyInfo==1)
			JOptionPane.showMessageDialog(new JFrame(),"Congratulations! You have been selected for interview for the "+applyFor+" post.");

	}


}

class Chooser extends JFrame
{

	JFileChooser chooser;
	public String fileName;

	public Chooser()
	{
		chooser = new JFileChooser();
		int r = chooser.showOpenDialog(new JFrame());
		if (r == JFileChooser.APPROVE_OPTION)
		{
		fileName = chooser.getSelectedFile().getPath();
		}
	}
}
