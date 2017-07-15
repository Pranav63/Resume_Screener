package recruit;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.sql.SQLException;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTextField;

class AdminForm extends JFrame
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
	public RuleForm ruleForm = null;
	public JLabel postLabel = null;
	public JLabel postName = null;
	public JLabel postNum = null;
	public JLabel postCrit = null;
	public JTextField post = null;
        public JTextField postNumText = null;
        public JTextField postCritText = null;
	public JTextField expYear = null;
	public JLabel expYearLabel = null;
	public JButton addUpdate = null;
	public JButton delete = null;
	public JButton reset = null;

	public AdminForm(LoginForm loginForm,RegisterForm registerForm,JDBCManager jdbc,UploadForm upload,RuleForm ruleForm)throws Exception
	{
		super("Welcome Administrator!");
		layout = new GridBagLayout();
		this.loginForm = loginForm;
		this.registerForm = registerForm;
		this.jdbcObj = jdbc;
		this.upload = upload;
		this.ruleForm = ruleForm;

		JLabel info = new JLabel(" The Selected candidates for the interview are...");
		JButton logout = new JButton(" Logout ");
		addUpdate = new JButton(" Add or Update... ");
		postLabel = new JLabel(" You can update or insert new posts for candidates to opt...");
		postName = new JLabel(" Enter the post Name: ");
		postNum = new JLabel(" Enter the number of posts vacant: ");
		postCrit = new JLabel(" Enter the criteria for the post: ");
		post = new JTextField(15);
		postNumText = new JTextField(15);
		postCritText = new JTextField(15);
		delete = new JButton("Delete Post");
		reset = new JButton("Reset");
		expYearLabel = new JLabel("Enter the years of experience: ");
		expYear = new JTextField(15);

		logout.setActionCommand("logout");
		delete.setActionCommand("deletepost");
		addUpdate.setActionCommand("updatepost");
		reset.setActionCommand("resetpost");

		setLayout(layout);
		constraints = new GridBagConstraints();
		tableModel = new ResultSetTableModel("jdbc:mysql://localhost/recruit","root","root","select * from selected");

		resultTable = new JTable(tableModel);

		TableScroll = new JScrollPane(resultTable);

		ActionAdapter a = new ActionAdapter(loginForm,registerForm,jdbcObj,upload,ruleForm,this);

		constraints.fill=GridBagConstraints.BOTH;

		addComponent(postLabel,0,0,2,1);
		addComponent(postName,1,0,1,1);
		addComponent(post,1,1,1,1);
		addComponent(postNum,2,0,1,1);
		addComponent(postNumText,2,1,1,1);
		addComponent(postCrit,3,0,1,1);
		addComponent(postCritText,3,1,1,1);
		addComponent(expYearLabel,4,0,1,1);
		addComponent(expYear,4,1,1,1);
		addComponent(addUpdate,5,0,1,1);
		addComponent(delete,5,1,1,1);
		addComponent(reset,6,0,2,1);
		addComponent(TableScroll,7,0,3,3);
		addComponent(logout,11,0,2,1);

		logout.addActionListener(a);
		reset.addActionListener(a);
		delete.addActionListener(a);
		addUpdate.addActionListener(a);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,700);

		this.setVisible(false);
	}

        private void addComponent(Component component,int row,int col,int width, int height)
        {
                constraints.gridx=col;
                constraints.gridy=row;
                constraints.gridwidth=width;
                constraints.gridheight=height;
                layout.setConstraints(component,constraints);
                add(component);
        }

}
