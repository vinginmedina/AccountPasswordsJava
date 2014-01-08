package net.isageek.ving.accountpasswords;

import java.awt.event.*;

import javax.swing.*;

import org.omg.CORBA.portable.InputStream;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Collections;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.isageek.ving.accountpasswords.AccountPasswords;

public class AccountWindow extends JFrame {
	
	private JPanel data = null;
	private JButton btnSave = null;

public AccountWindow() {
	
	GridBagLayout gridBagLayout = new GridBagLayout();
//	gridBagLayout.columnWidths = new int[]{638, 0};
//	gridBagLayout.rowHeights = new int[]{35, 70, 0};
	gridBagLayout.columnWeights = new double[]{0.0};
	gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
	getContentPane().setLayout(gridBagLayout);
	
	JPanel menu = new JPanel();
	menu.setBackground(Color.WHITE);
	menu.setForeground(Color.WHITE);
	menu.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	JButton btnExitButton = new JButton("Exit");
	btnExitButton.setFont(new Font("Dialog", Font.BOLD, 14));
	btnExitButton.setHorizontalAlignment(SwingConstants.LEFT);
	btnExitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			exitProg();
		}
	});
	menu.add(btnExitButton);
	
	JButton btnRefresh = new JButton("Refresh");
	btnRefresh.setFont(new Font("Dialog", Font.BOLD, 14));
	btnRefresh.setHorizontalAlignment(SwingConstants.LEFT);
	btnRefresh.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			refreshFile();
		}
	});
	menu.add(btnRefresh);
	
	JButton btnEdit = new JButton("Edit");
	btnEdit.setFont(new Font("Dialog", Font.BOLD, 14));
	btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
	btnEdit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			editRow();
		}
	});
	menu.add(btnEdit);
	
	JButton btnDelete = new JButton("Delete");
	btnDelete.setFont(new Font("Dialog", Font.BOLD, 14));
	btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
	btnDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			deleteRow();
		}
	});
	menu.add(btnDelete);
	
	JButton btnNew = new JButton("New");
	btnNew.setFont(new Font("Dialog", Font.BOLD, 14));
	btnNew.setHorizontalAlignment(SwingConstants.LEFT);
	btnNew.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			newRow();
		}
	});
	menu.add(btnNew);
	
	btnSave = new JButton("Save");
	btnSave.setBackground(UIManager.getColor("Button.background"));
	btnSave.setFont(new Font("Dialog", Font.BOLD, 14));
	btnSave.setHorizontalAlignment(SwingConstants.LEFT);
	btnSave.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			saveData();
		}
	});
	menu.add(btnSave);
	
//	JLabel lblAccountPasswords = DefaultComponentFactory.getInstance().createTitle("Account Passwords");
//	lblAccountPasswords.setHorizontalAlignment(SwingConstants.CENTER);
//	menu.add(lblAccountPasswords);
	
	GridBagConstraints gbc_menu = new GridBagConstraints();
	gbc_menu.anchor = GridBagConstraints.NORTHWEST;
	gbc_menu.fill = GridBagConstraints.HORIZONTAL;
	gbc_menu.insets = new Insets(0, 0, 5, 0);
	gbc_menu.gridx = 0;
	gbc_menu.gridy = 0;
	gbc_menu.weightx = 1;
	gbc_menu.weighty = 0;
	getContentPane().add(menu, gbc_menu);
	
	JPanel header = new JPanel();
	header.setBackground(Color.WHITE);
	header.setForeground(Color.WHITE);
	header.setLayout(new GridLayout(2, 1));
	
	JPanel hr1 = new JPanel();
	hr1.setBackground(Color.WHITE);
	hr1.setForeground(Color.WHITE);
	hr1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	JLabel txtSelect = new JLabel();
	txtSelect.setFont(new Font("Dialog", Font.BOLD, 14));
	txtSelect.setOpaque(true);
	txtSelect.setForeground(Color.BLACK);
	txtSelect.setBackground(Color.WHITE);
	txtSelect.setText("Select");
	txtSelect.setPreferredSize(new Dimension(55, 20));
	hr1.add(txtSelect);
	
	JLabel txtSystem = new JLabel();
	txtSystem.setFont(new Font("Dialog", Font.BOLD, 14));
	txtSystem.setOpaque(true);
	txtSystem.setForeground(Color.BLACK);
	txtSystem.setBackground(Color.WHITE);
	txtSystem.setText("System");
	txtSystem.setPreferredSize(new Dimension(250, 20));
	hr1.add(txtSystem);
	
	JLabel txtUserId = new JLabel();
	txtUserId.setOpaque(true);
	txtUserId.setForeground(Color.BLACK);
	txtUserId.setBackground(Color.WHITE);
	txtUserId.setFont(new Font("Dialog", Font.BOLD, 14));
	txtUserId.setText("User Id");
	txtUserId.setPreferredSize(new Dimension(250, 20));
	hr1.add(txtUserId);
	
	JLabel txtPasswd = new JLabel();
	txtPasswd.setOpaque(true);
	txtPasswd.setForeground(Color.BLACK);
	txtPasswd.setBackground(Color.WHITE);
	txtPasswd.setFont(new Font("Dialog", Font.BOLD, 14));
	txtPasswd.setText("Password");
	txtPasswd.setPreferredSize(new Dimension(250, 20));
	hr1.add(txtPasswd);
	header.add(hr1);
	
	JPanel hr2 = new JPanel();
	hr2.setBackground(Color.WHITE);
	hr2.setForeground(Color.WHITE);
	hr2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	JLabel spacer1 = new JLabel();
	spacer1.setFont(new Font("Dialog", Font.BOLD, 14));
	spacer1.setOpaque(true);
	spacer1.setForeground(Color.BLACK);
	spacer1.setBackground(Color.WHITE);
	spacer1.setText("====");
	spacer1.setPreferredSize(new Dimension(55, 20));
	hr2.add(spacer1);
	
	JLabel spacer2 = new JLabel();
	spacer2.setFont(new Font("Dialog", Font.BOLD, 14));
	spacer2.setOpaque(true);
	spacer2.setForeground(Color.BLACK);
	spacer2.setBackground(Color.WHITE);
	spacer2.setText("==================");
	spacer2.setPreferredSize(new Dimension(250, 20));
	hr2.add(spacer2);
	
	JLabel spacer3 = new JLabel();
	spacer3.setFont(new Font("Dialog", Font.BOLD, 14));
	spacer3.setOpaque(true);
	spacer3.setForeground(Color.BLACK);
	spacer3.setBackground(Color.WHITE);
	spacer3.setText("==================");
	spacer3.setPreferredSize(new Dimension(250, 20));
	hr2.add(spacer3);
	
	JLabel spacer4 = new JLabel();
	spacer4.setFont(new Font("Dialog", Font.BOLD, 14));
	spacer4.setOpaque(true);
	spacer4.setForeground(Color.BLACK);
	spacer4.setBackground(Color.WHITE);
	spacer4.setText("==================");
	spacer4.setPreferredSize(new Dimension(250, 20));
	hr2.add(spacer4);
	header.add(hr2);
	
	GridBagConstraints gbc_header = new GridBagConstraints();
	gbc_header.anchor = GridBagConstraints.NORTHWEST;
	gbc_header.fill = GridBagConstraints.HORIZONTAL;
	gbc_header.insets = new Insets(0, 0, 5, 0);
	gbc_header.gridx = 0;
	gbc_header.gridy = 1;
	gbc_header.weightx = 1;
	gbc_header.weighty = 0;
	getContentPane().add(header, gbc_header);
	
	data = new JPanel();
	data.setLayout(new GridLayout(0, 1));
	
	refreshDisplay();
		
	GridBagConstraints gbc_data = new GridBagConstraints();
	gbc_data.anchor = GridBagConstraints.NORTHWEST;
	gbc_data.fill = GridBagConstraints.BOTH;
	gbc_data.insets = new Insets(0, 0, 5, 0);
	gbc_data.gridx = 0;
	gbc_data.gridy = 2;
	gbc_data.weightx = 1;
	gbc_data.weighty = 1;

	JScrollPane vertical = new JScrollPane();
	vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	vertical.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	vertical.setWheelScrollingEnabled(true);
	vertical.getVerticalScrollBar().setUnitIncrement(20);
	vertical.setViewportView(data);
	getContentPane().add(vertical, gbc_data);
	
	setTitle("Account Passwords");
	getContentPane().setBackground(Color.WHITE);
	getContentPane().setForeground(Color.WHITE);
//	setLocation(10,200);
	pack();
	setSize(860,768);
	setVisible(true);
			
			// Window Listeners
	addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
		   exitProg();
	  	} //windowClosing
	} );
  }

	public void refreshDisplay() {
		ButtonGroup rbGroup = new ButtonGroup();

		JPanel row = null;
		JRadioButton rb = null;
		JLabel acct = null;
		JLabel uid = null;
		JLabel pass = null;
		
		data.removeAll();
		
		Collections.sort(AccountPasswords.passwordData);
		
		for (PasswordData pd : AccountPasswords.passwordData) {
			row = new JPanel();
			row.setBackground(Color.WHITE);
			row.setForeground(Color.WHITE);
			row.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			rb = new JRadioButton();
			rb.setBackground(Color.WHITE);
			rb.setForeground(Color.WHITE);
			rb.setHorizontalAlignment(SwingConstants.CENTER);
			rb.setPreferredSize(new Dimension(55, 20));
			row.add(rb);
			rbGroup.add(rb);
			
			acct = new JLabel();
			acct.setFont(new Font("Dialog", Font.BOLD, 14));
			acct.setOpaque(true);
			acct.setForeground(Color.BLACK);
			acct.setBackground(Color.WHITE);
			acct.setText(pd.getAccount());
			acct.setPreferredSize(new Dimension(250, 20));
			row.add(acct);
			
			uid = new JLabel();
			uid.setFont(new Font("Dialog", Font.BOLD, 14));
			uid.setOpaque(true);
			uid.setForeground(Color.BLACK);
			uid.setBackground(Color.WHITE);
			uid.setText(pd.getUserId());
			uid.setPreferredSize(new Dimension(250, 20));
			uid.addMouseListener(new SelectItem());
			row.add(uid);
			
			pass = new JLabel();
			pass.setFont(new Font("Dialog", Font.BOLD, 14));
			pass.setOpaque(true);
			pass.setForeground(Color.BLACK);
			pass.setBackground(Color.WHITE);
			pass.setText(pd.getPassword());
			pass.setPreferredSize(new Dimension(250, 20));
			pass.addMouseListener(new SelectItem());
			row.add(pass);
		
			pd.setGuiItems(row, rb, acct, uid, pass);
			
			data.add(row);
		}
		data.revalidate();
	}
	
	public void refreshFile() {
		if (AccountPasswords.dataChanged) {
    		int rtn = JOptionPane.showConfirmDialog(null,
    			    "There are unsaved changes. Do you\n"
    			    + "really want to reload?",
    			    "Reload File?",
    			    JOptionPane.YES_NO_OPTION);
    		if (rtn == JOptionPane.YES_OPTION) {
    			AccountPasswords.readFile();
    			btnSave.setBackground(UIManager.getColor("Button.background"));
    	    	AccountPasswords.dataChanged = false;
    			refreshDisplay();
    		}
    	} else {
			AccountPasswords.readFile();
			refreshDisplay();
    	}
	}

    public void editRow() {
    	PasswordData rowToEdit = null;
    	for (PasswordData pd : AccountPasswords.passwordData) {
    		if (pd.isSelected()) {
    			rowToEdit = pd;
    		}
    	}
    	if (rowToEdit != null) {
    		JLabel system = new JLabel(rowToEdit.getAccount());
    		JTextField userid = new JTextField();
    		JTextField password = new JTextField();
    		final JComponent[] inputs = new JComponent[] {
    				new JLabel("System"),
    				system,
    				new JLabel("User ID"),
    				userid,
    				new JLabel("Password"),
    				password
    		};
    		String oldUserId = rowToEdit.getUserId();
    		String oldPassword = rowToEdit.getPassword();
    		userid.setText(rowToEdit.getUserId());
    		password.setText(rowToEdit.getPassword());
    		int rtn = JOptionPane.showConfirmDialog(null, inputs, "Edit Entry",
    				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    		if ((rtn == JOptionPane.YES_OPTION) &&
    				((! oldUserId.equals(userid.getText())) || (! oldPassword.equals(password.getText())))) {
	    		rowToEdit.update(system.getText(), userid.getText(), password.getText());
	    		btnSave.setBackground(Color.MAGENTA);
	    		AccountPasswords.dataChanged = true;
    		}
    	} else {
    		JOptionPane.showMessageDialog(null, "You must select a row to edit.", "No Selection", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void deleteRow() {
    	PasswordData rowToRemove = null;
    	for (PasswordData pd : AccountPasswords.passwordData) {
    		if (pd.isSelected()) {
    			rowToRemove = pd;
    		}
    	}
    	if (rowToRemove != null) {
    		if (AccountPasswords.passwordData.remove(rowToRemove)) {
    			btnSave.setBackground(Color.MAGENTA);
    			AccountPasswords.dataChanged = true;
    		}
    		refreshDisplay();
    	} else {
    		JOptionPane.showMessageDialog(null, "You must select a row to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void newRow() {
    	JTextField system = new JTextField();
		JTextField userid = new JTextField();
		JTextField password = new JTextField();
		final JComponent[] inputs = new JComponent[] {
				new JLabel("System"),
				system,
				new JLabel("User ID"),
				userid,
				new JLabel("Password"),
				password
		};
		int rtn = JOptionPane.showConfirmDialog(null, inputs, "New Entry",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (rtn == JOptionPane.YES_OPTION) {
			AccountPasswords.passwordData.add(new PasswordData(system.getText(), userid.getText(), password.getText()));
			btnSave.setBackground(Color.MAGENTA);
    		AccountPasswords.dataChanged = true;
    		refreshDisplay();
		}
    }
    
    public void saveData() {
    	FileWriter fw = null;
    	BufferedWriter bw = null;
    	PrintWriter fileOut = null;
    	URL url = null;
    	Boolean error = false;
        HttpURLConnection connection = null;
    	if (AccountPasswords.useFile) {
			try {
		        fw = new FileWriter (AccountPasswords.fileName);
		        bw = new BufferedWriter(fw);
		        fileOut = new PrintWriter(bw);
			} catch (Exception e) {
				e.printStackTrace();
				error = true;
			}
    	} else {
    		try {
	            // Create connection
	            url = new URL(AccountPasswords.urlToPost);
	            connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setUseCaches(false);
	            connection.setRequestProperty("Content-Type",
	                    "application/x-www-form-urlencoded");
	            connection.setRequestProperty("Content-Language", "en-US");
    		} catch (Exception e) {
    			e.printStackTrace();
    			error = true;
    		}
    	}
        String decodeText;
    	String encodedText;
    	String fullFile = "INPFILE=";
    	for (PasswordData pd : AccountPasswords.passwordData) {
    		decodeText = pd.getAccount() + "|" + pd.getUserId() + "|" + pd.getPassword();
    		encodedText = AccountPasswords.encrypt(decodeText);
    		try {
    		    fullFile += URLEncoder.encode(encodedText + "\n", "UTF-8");
    		} catch (Exception e) {
    			e.printStackTrace();
    			error = true;
    		}
    		if (AccountPasswords.useFile) {
    			try {
    		        fileOut.println(encodedText);
    			} catch (Exception e) {
    				e.printStackTrace();
    				error = true;
    			}
    		}
    	}
	    if (! AccountPasswords.useFile) {
	    	try {
	            connection.setRequestProperty("Content-Length", ""
	                    + Integer.toString(fullFile.getBytes().length));
	            DataOutputStream wr = new DataOutputStream(connection
	                    .getOutputStream());
	            wr.writeBytes(fullFile);
	            wr.flush();
	            wr.close();
	            InputStreamReader is = new InputStreamReader(connection.getInputStream());
	            BufferedReader rd = new BufferedReader(is);
	            String line;
	            String response = "";
	            while ((line = rd.readLine()) != null) {
	                response += line;
	            }
	            rd.close();
	            if (! response.equals("File was created.")) {
	            	error = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            error = true;
    	    }  finally {
    	        if (connection != null) {
    	            connection.disconnect();
    	        }
    	    }
	    } else {
	    	try {
				fileOut.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				error = true;
			}
	    }
	    if (! error) {
	    	btnSave.setBackground(UIManager.getColor("Button.background"));
	    	AccountPasswords.dataChanged = false;
	    } else {
	    	JOptionPane.showMessageDialog(null,
	    			"There was an error is saving the file.",
	    			"File Not Saved",
	    			JOptionPane.ERROR_MESSAGE);
	    }
    }
    
    public void exitProg() {
    	if (AccountPasswords.dataChanged) {
    		int rtn = JOptionPane.showConfirmDialog(null,
    			    "There are unsaved changes. Do you\n"
    			    + "want to save before you exit?",
    			    "Save Before Exit?",
    			    JOptionPane.YES_NO_OPTION);
    		if (rtn == JOptionPane.YES_OPTION) {
    			saveData();
    		}
    	}
    	System.exit(0);
    }
	
}
