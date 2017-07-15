
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
import javax.swing.JPasswordField;

class LoginForm extends JFrame
{
	private GridBagLayout layout=null;
	private GridBagConstraints constraints=null;
	public ActionAdapter a=null;


	public JTextField uname=null;
	public JPasswordField pass=null;
	public JDBCManager jdbcObj = null;
	public UploadForm upload = null;
	public RuleForm ruleForm = null;
	public RegisterForm registerForm = null;
	public AdminForm adminForm = null;

	public LoginForm()throws Exception
	{

		super("Login Here!");
		layout = new GridBagLayout();
		jdbcObj = new JDBCManager();
		upload = new UploadForm(this,jdbcObj);
		registerForm = new RegisterForm(this,jdbcObj,upload);
		ruleForm = new RuleForm(this,registerForm,jdbcObj,upload);
		adminForm = new AdminForm(this,registerForm,jdbcObj,upload,ruleForm);

		a = new ActionAdapter(this,registerForm,jdbcObj,upload,ruleForm,adminForm);

		setLayout(layout);
		constraints = new GridBagConstraints();
		uname = new JTextField("",15);
		pass = new JPasswordField("",15);
		JLabel unameLabel = new JLabel(" Username: ");
		JLabel passLabel = new JLabel(" Password: ");
		JLabel Header = new JLabel(" Welcome to Resume Screener! ");
		JLabel newUser = new JLabel(" Are you a New User? ");
		JButton login = new JButton("Login");
		JButton reset = new JButton("Reset");
		JButton register = new JButton("Register");
		JLabel free1 = new JLabel(" ");
		JLabel free2 = new JLabel(" ");
		JLabel free3 = new JLabel(" ");

		// Actionadapter


		login.setActionCommand("login");
		reset.setActionCommand("resetlogin");
		register.setActionCommand("register");

		constraints.fill=GridBagConstraints.BOTH;
		addComponent(Header,0,0,2,1);

		addComponent(free1,1,0,2,1);

		constraints.fill=GridBagConstraints.HORIZONTAL;

		constraints.fill=GridBagConstraints.BOTH;
		addComponent(unameLabel,2,0,1,1);
		addComponent(uname,2,1,1,1);
		addComponent(passLabel,3,0,1,1);
		addComponent(pass,3,1,1,1);

		addComponent(free2,4,0,2,1);

		addComponent(login,5,0,1,1);
		addComponent(reset,5,1,1,1);

		addComponent(free3,6,0,2,1);

		addComponent(newUser,7,0,2,1);
		addComponent(register,8,0,2,1);

		register.addActionListener(a);
		reset.addActionListener(a);
		login.addActionListener(a);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		this.setVisible(true);
		Align.set(this,300,300);
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

	public static void main(String [] args)throws Exception
	{
		new LoginForm();
	}
}
