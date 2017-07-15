package recruit;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

class RuleForm extends JFrame
{
	private GridBagLayout layout;
	private GridBagConstraints constraints;
 	public ActionAdapter a = null;
	public LoginForm loginForm = null;
	public RegisterForm registerForm = null;
	public JDBCManager jdbcObj = null;
	public UploadForm upload = null;
	public JScrollPane TableScroll = null;
	public ResultSetTableModel tableModel;
	public JTable resultTable = null;
	public AdminForm adminForm = null;

	public RuleForm(LoginForm loginForm, RegisterForm registerForm,JDBCManager jdbcObj,UploadForm upload)throws Exception
	{
		super("Resume  Rules");
		this.loginForm = loginForm;
		this.registerForm = registerForm;
		this.jdbcObj = jdbcObj;
		this.upload = upload;
		layout = new GridBagLayout();
		adminForm = new AdminForm(loginForm,registerForm,jdbcObj,upload,this);
		a = new ActionAdapter(loginForm,registerForm,jdbcObj,upload,this,adminForm);
		setLayout(layout);
		constraints = new GridBagConstraints();

		System.out.println(a);

		JLabel rulelabel = new JLabel(" Follow these rules to prepare your resume before Uploading...");
		JLabel jLabel1 = new JLabel("* Type your resume in a .txt File and Upload it");
		JLabel jLabel2 = new JLabel("* In first line of the resume Enter your username and then type your full name in next line.");
		JLabel jLabel3 = new JLabel("* In the third line type your date of birth in the format of yyyy-mm-dd");
		JLabel jLabel4 = new JLabel("* In the fourth line mention your gender by a single letter, either m or f");
		JLabel jLabel5 = new JLabel("* In the fifth line type your full address");
		JLabel jLabel6 = new JLabel("* In the Sixth line type your Skill set that is going to get u the job");
		JLabel jLabel7 = new JLabel("* Skill set includes What are your areas of expertise !");
		JLabel jLabel8 = new JLabel("* In the Seventh line type your work designation of your previous job");
		JLabel jLabel9 = new JLabel("* In the eighth line type in your number of years of experience");
		JLabel jLabel10 = new JLabel("* In the nineth line type in what job you want to apply for as principal,head of department,staff,lab assistant,peon");
		JLabel jLabel11 = new JLabel(" ");
		JLabel jLabel12 = new JLabel(" ");
		JLabel jLabel13 = new JLabel(" These are the Posts vacant... ");

		JButton back = new JButton("Back...");
		constraints.fill=GridBagConstraints.BOTH;

		back.setActionCommand("backUpload");

                tableModel = new ResultSetTableModel("jdbc:mysql://localhost/recruit","root","root","select * from vacancy");

                resultTable = new JTable(tableModel);

		TableScroll = new JScrollPane(resultTable);

		addComponent(rulelabel,0,0,1,1);
		addComponent(jLabel11,1,0,1,1);
		addComponent(jLabel1,2,0,1,1);
		addComponent(jLabel2,3,0,1,1);
		addComponent(jLabel3,4,0,1,1);
		addComponent(jLabel4,5,0,1,1);
		addComponent(jLabel5,6,0,1,1);
		addComponent(jLabel6,7,0,1,1);
		addComponent(jLabel7,8,0,1,1);
		addComponent(jLabel8,9,0,1,1);
		addComponent(jLabel9,10,0,1,1);
		addComponent(jLabel10,11,0,1,1);
		addComponent(jLabel12,12,0,1,1);
		addComponent(jLabel13,13,0,1,1);
		addComponent(TableScroll,14,0,5,5);
		addComponent(back,19,0,1,1);


		back.addActionListener(a);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,700);


		this.setVisible(false);
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

}
