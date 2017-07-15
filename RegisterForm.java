package recruit;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

class RegisterForm extends JFrame
{
	private GridBagLayout layout=null;
	private GridBagConstraints constraints=null;
	public LoginForm loginForm=null;
	public JTextField uname=null;
	public JPasswordField pass=null;
	public JTextField name=null;
	public JTextField phone=null;
	public JTextField email=null;
	public JDBCManager jdbc=null;
	public UploadForm upload=null;
	public RuleForm ruleForm = null;
	public ActionAdapter a=null;
	public AdminForm adminForm = null;

	public RegisterForm(LoginForm loginForm,JDBCManager jdbc,UploadForm upload)throws Exception
	{
		super("Registration");
		this.loginForm = loginForm;
		this.jdbc = jdbc;
		this.upload = upload;
		ruleForm = new RuleForm(loginForm,this,jdbc,upload);
		adminForm = new AdminForm(loginForm,this,jdbc,upload,ruleForm);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
		JLabel promptLabel = new JLabel(" Fill in your details... ");
		JLabel unameLabel = new JLabel(" Username: ");
		JLabel passLabel = new JLabel(" Password: ");
		JLabel nameLabel = new JLabel(" Name: ");
		JLabel phoneLabel = new JLabel(" Mobile Number: ");
		JLabel emailLabel = new JLabel(" E-Mail ID: ");

		uname = new JTextField(15);
		pass = new JPasswordField(15);
		name = new JTextField(15);
		phone = new JTextField(15);
		email = new JTextField(15);

		JButton Register = new JButton(" Register ");
		JButton Reset = new JButton(" Reset ");
		JButton Back = new JButton(" Back ");
		constraints.fill=GridBagConstraints.BOTH;
		a = new ActionAdapter(loginForm,this,jdbc,upload,ruleForm,adminForm);

		Register.setActionCommand("RegisterForm");
		Reset.setActionCommand("Reset");
		Back.setActionCommand("Back");

		addComponent(promptLabel,0,0,2,1);

		addComponent(unameLabel,2,0,1,1);
		addComponent(uname,2,1,1,1);
		addComponent(passLabel,4,0,1,1);
		addComponent(pass,4,1,1,1);
		addComponent(nameLabel,6,0,1,1);
		addComponent(name,6,1,1,1);
		addComponent(phoneLabel,8,0,1,1);
		addComponent(phone,8,1,1,1);
		addComponent(emailLabel,10,0,1,1);
		addComponent(email,10,1,1,1);

		addComponent(Register,12,0,1,1);
		addComponent(Reset,12,1,1,1);
		addComponent(Back,13,0,2,1);

		Reset.addActionListener(a);
		Back.addActionListener(a);
		Register.addActionListener(a);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		Align.set(this,300,300);
	}

	private void addComponent(Component component,int row,int col,int width,int height)
	{
		constraints.gridx=col;
		constraints.gridy=row;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		layout.setConstraints(component,constraints);
		this.getContentPane().add(component);
	}

}
