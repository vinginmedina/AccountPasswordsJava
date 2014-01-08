package net.isageek.ving.accountpasswords;

import java.awt.Color;
import java.util.regex.PatternSyntaxException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PasswordData implements Comparable<PasswordData> {
	private String account;
	private String userId;
	private String password;
	private JPanel row;
	private JRadioButton rb;
	private JLabel accountField;
	private JLabel userIdField;
	private JLabel passwordField;
	
	public PasswordData (String newAccount, String newUserId, String newPassword) {
		account = newAccount;
		userId = newUserId;
		password = newPassword;
		row = null;
		rb = null;
		accountField = null;
		userIdField = null;
		passwordField = null;
	}
	
	public PasswordData (String input) {
		try {
			String[] fields = input.split("\\|");
			account = fields[0];
			userId = fields[1];
			password = fields[2];
		} catch (PatternSyntaxException ex) {
			account = "";
			userId = "";
			password = "";
		}
	}
	
	public int compareTo (PasswordData pwd) {
		int rtn = this.account.toLowerCase().compareTo(pwd.account.toLowerCase());
		return rtn;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void update(String newAccount, String newUserId, String newPassword) {
		account = newAccount;
		userId = newUserId;
		password = newPassword;
		if (accountField != null) {
			accountField.setText(account);
		}
		if (userIdField != null) {
			userIdField.setText(userId);
		}
		if (passwordField != null) {
			passwordField.setText(password);
		}
	}
	
	public void setGuiItems(JPanel newRow, JRadioButton newRb, JLabel newAccount, JLabel newUserId, JLabel newPassword) {
		row = newRow;
		rb = newRb;
		accountField = newAccount;
		userIdField = newUserId;
		passwordField = newPassword;
	}
	
	public void resetBackground() {
	    accountField.setBackground(Color.WHITE);
	    userIdField.setBackground(Color.WHITE);
	    passwordField.setBackground(Color.WHITE);
    }
	
	public Boolean isSelected() {
		return rb.isSelected();
	}
}
