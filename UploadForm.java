package recruit;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.SQLException;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

class UploadForm extends JFrame
{
	private GridBagLayout layout=null;
	private GridBagConstraints constraints=null;
	public LoginForm loginForm = null;
	public ActionAdapter a = null;
	public JDBCManager jdbc = null;
	public RegisterForm registerForm = null;
	public RuleForm ruleForm = null;
	public JTextField uploadField = null;
	public JLabel selectedLabel = null;
	public AdminForm adminForm = null;

	public JScrollPane TableScroll = null;
	public ResultSetTableModel tableModel;
	public JTable resultTable = null;
	public JLabel info = null;
        public JLabel info1 = null;
        public JLabel free1 = null;
        public JLabel free2 = null;
	public JButton upload = null;
        public JButton browse = null;
        public JButton logout = null;
        public JButton resumeRule = null;

	public UploadForm(LoginForm loginForm,JDBCManager jdbcObj)throws Exception
	{
		super("Upload your Resume Here");
		this.loginForm = loginForm;
		layout = new GridBagLayout();
		this.jdbc = jdbcObj;
		setLayout(layout);
		registerForm = new RegisterForm(loginForm,jdbc,this);
		ruleForm = new RuleForm(loginForm,registerForm,jdbc,this);
		adminForm = new AdminForm(loginForm,registerForm,jdbc,this,ruleForm);

		a = new ActionAdapter(loginForm,registerForm,jdbc,this,ruleForm,adminForm);
		constraints = new GridBagConstraints();
		info = new JLabel(" Information for you... ");
		info1 = new JLabel(" ");
		uploadField = new JTextField();
		free1 = new JLabel(" ");
		upload = new JButton("Upload");
		JButton browse = new JButton("Browse");
		free2 = new JLabel(" ");
		logout = new JButton(" Logout ");
		selectedLabel = new JLabel(" ");
		resumeRule = new JButton(" Resume rules & Vacancy List ");

		constraints.fill=GridBagConstraints.BOTH;

		resumeRule.setActionCommand("resumerule");
		browse.setActionCommand("browse");
		upload.setActionCommand("resumeupload");
		logout.setActionCommand("logout");



		addComponent(info,0,0,2,1);
		addComponent(selectedLabel,1,0,2,1);
		addComponent(info1,2,0,2,2);
		addComponent(uploadField,4,0,2,1);
		addComponent(free1,5,0,2,1);
		addComponent(upload,6,0,1,1);
		addComponent(browse,6,1,1,1);
		addComponent(free2,7,0,2,1);
		addComponent(resumeRule,8,0,1,1);
		addComponent(logout,8,1,1,1);


		resumeRule.addActionListener(a);
		browse.addActionListener(a);
		upload.addActionListener(a);
		logout.addActionListener(a);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300,300);
	}

	private void addComponent(Component component,int row,int col,int width,int height)
	{
		constraints.gridx=col;
		constraints.gridy=row;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		layout.setConstraints(component,constraints);
		add(component);
	}

	public void checkSelect()throws SQLException
	{
		String selectedPost=" ";
		String change;
		try
		{

		jdbc.resultSet = jdbc.statement.executeQuery("select * from resume");
		int count=0;
		int resume=0;

		while(jdbc.resultSet.next())
		{
			if((jdbc.resultSet.getObject(1).toString()).equals(loginForm.uname.getText()))
			{
				resume++;
				break;
			}
		}
		jdbc.resultSet = jdbc.statement.executeQuery("select * from selected");

		while(jdbc.resultSet.next())
		{
			if((jdbc.resultSet.getObject(1).toString()).equals(loginForm.uname.getText()))
			{
				selectedPost = jdbc.resultSet.getObject(2).toString();
				remove(selectedLabel);
				change = " You have been selected for interview for the "+selectedPost+" post.";
				changeLabel(selectedLabel,change);
				addComponent(selectedLabel,1,0,2,1);
				count++;
				return;
			}

		}


		if(resume==0)
		{
			remove(selectedLabel);
			change = "You have not uploaded your resume.";
			changeLabel(selectedLabel,change);
			addComponent(selectedLabel,1,0,2,1);
			return;
		}

		if(resume==1 && count==0)
		{
			remove(selectedLabel);
			change = "Sorry, you have not been Selected for the interview!";
			changeLabel(selectedLabel,change);
			addComponent(selectedLabel,1,0,2,1);
			return;
		}
		}

		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void changeLabel(JLabel lab,String change)
	{
		lab.setText(change);
	}
}
