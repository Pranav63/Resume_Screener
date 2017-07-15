package recruit; // packaged for reuse

public class AccountRecord
{
	private String uname;
	private String Name;
	private String date;
	private String sex;
	private String address;
	private String expprof;
	private int expyear;
	private String applyfor;
	private String qualific;

	// no-argument constructor calls other constructor with default values
	
	public AccountRecord()
	{
		this( "", "","","","","","", 0,"" ); // call four-argument constructor
	} // end no-argument AccountRecord constructor

	// initialize a record
	public AccountRecord( String user,String name,String date,String sex,String Addr,String qual,String expprofession,int expyear,String apply)
	{
		setUsername( user );
		setName( name );
		setDOB( date );
		setSex( sex );
		setAddr(Addr);
		setExpProf( expprofession );
		setExpYear(expyear);
		setApply(apply);
		setQualification(qual);
	} // end four-argument AccountRecord constructor

	public void setApply(String applyf)
	{
		applyfor = applyf;
	}
	
	public void setQualification(String qual)
	{
		qualific = qual;
	}
	
	public void setExpProf(String exp)
	{
		expprof = exp;	
	}

	public void setExpYear(int exp)
	{
		expyear = exp;
	}

	public void setAddr(String addres)
	{	
		address = addres;
	}

	public void setSex(String gender)
	{
		sex = gender;
	}

	public void setDOB(String birth)
	{
		date = birth;
	}

	public void setUsername(String user)
	{
		uname = user;
	}

	// set account number

	// set first name
	public void setName( String name )
	{
		Name = name;
	} // end method setFirstName
	public String getApply()
	{
		return applyfor;
	}
	
	public String getQualification()
	{
		return qualific;
	}
	public String getExpProf()
	{
		return expprof;	
	}

	public int getExpYear()
	{
		return expyear;
	}

	public String getAddr()
	{	
		return address;
	}

	public String getSex()
	{
		return sex;
	}

	public String getDOB()
	{
		return date;
	}

	public String getUsername()
	{
		return uname;
	}

	// set account number

	// set first name
	public String getName()
	{
		return Name;
	} // end method setFirstName
}