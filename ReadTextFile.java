package recruit;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class ReadTextFile
{
	public Scanner input;
	public UploadForm upload;

	public ReadTextFile(UploadForm upload)
	{
		this.upload = upload;
	}
	public void openFile()
	{
		try
		{
			input = new Scanner(new File(upload.uploadField.getText()));
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			JOptionPane.showMessageDialog(new JFrame()," Error Opening File.");
			System.exit(1);
		}
	}

	public AccountRecord readRecords()
	{
		AccountRecord record = new AccountRecord();

		try
		{
		while(input.hasNext())
		{
			record.setUsername(input.next());
			record.setName(input.next());
			record.setDOB(input.next());
			record.setSex(input.next());
			record.setAddr(input.next());
			record.setQualification(input.next());
			record.setExpProf(input.next());
			record.setExpYear(input.nextInt());
			record.setApply(input.next());

		}

		return record;
		}

		catch(NoSuchElementException elementException)
		{
			JOptionPane.showMessageDialog(new JFrame(),"File improperly formed.");
			input.close();
			System.exit(1);
		}

		catch(IllegalStateException stateException)
		{
			JOptionPane.showMessageDialog(new JFrame()," Error reading from file.");
			System.exit(1);
		}
		return null;
	}

	public void closeFile()
	{
		if(input != null)
			input.close();
	}
}
