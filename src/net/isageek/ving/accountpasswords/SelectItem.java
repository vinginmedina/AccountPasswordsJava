package net.isageek.ving.accountpasswords;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import net.isageek.ving.accountpasswords.AccountPasswords;

public class SelectItem implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for (PasswordData pd : AccountPasswords.passwordData) {
			pd.resetBackground();
		}
		JLabel lbl = (JLabel) e.getSource();
		lbl.setBackground(Color.LIGHT_GRAY);
		String str = lbl.getText();
		StringSelection stringSelection = new StringSelection (str);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
