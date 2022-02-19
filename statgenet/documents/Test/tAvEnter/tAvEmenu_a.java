package tAvEnter;

import java.applet.Applet;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class tAvEmenu_a extends Applet implements ActionListener{
	private Button b1;
	private Button b2;
	
	public void init(){
		setSize(250,40);
		setLayout(new GridLayout(1,1));	
		b1 = new Button("Read haplotype file.");
		add(b1);
		b1.addActionListener(this);
		/*b2 = new Button("Exit.");
		add(b2);
		b2.addActionListener(this);*/
		show();
	}

	public void actionPerformed(ActionEvent event) {	// ActionListenerに必要なメソッド。	
		if(event.getSource().equals(b1)){
			tAvSelectHP tavselect = new tAvSelectHP();
		}else if(event.getSource().equals(b2)){
			System.exit(0);
		}
		
	}
}