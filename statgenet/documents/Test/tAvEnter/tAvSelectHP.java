package tAvEnter;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import treeANDvine.PostSimRetro1;

class tAvSelectHP extends Frame implements ActionListener{
	
	FileDialog fd;
	Label lb;
	
	public tAvSelectHP(){
		super("Select haplotype file");
		setSize(300,120);
		setLayout(new GridLayout(3,1));
		
		Button load = new Button("Open");
		add(load);
		load.addActionListener(this);
	/*	Button exit = new Button("Exit");
		add(exit);
		exit.addActionListener(this);
	*/
		
		lb = new Label();
		lb.setSize(70,300);
		add(lb);
		
		addWindowListener(new WindowAdapter(){ 		 //フレームのクローズ。
			public void windowClosing(WindowEvent e){	 //
				System.exit(0);						 //
			}										 //
		});
		
		show();
	}
	
	public static void main(String args[]){
		tAvSelectHP tavselect = new tAvSelectHP();		
	}
	
	public void actionPerformed(ActionEvent e) {	// ActionListenerに必要なメソッド。	
		if(e.getActionCommand().equals("Open")){
			fd = new FileDialog(this,"Select a haplotype file");
			fd.show();
			
			lb.setText(fd.getDirectory() + fd.getFile());
			
			PostSimRetro1 psr = new PostSimRetro1();
				psr.main(fd.getDirectory()+fd.getFile());
	/*	}else if(e.getActionCommand().equals("Exit")){
	 *	System.exit(0);
	 */
		}
	}
}