package tAvEnter;
import java.awt.*;
import java.awt.event.*;


public class tAvEmenu extends Frame implements ActionListener{
	private Button b1;
	private Button b2;
		
	/*�R���X�g���N�^*/
	public tAvEmenu(){
		super("treeANDvineEnter Menu");
		setSize(400,100);
		setLayout(new GridLayout(1,1));	
		b1 = new Button("Read haplotype file.");
		add(b1);
		b1.addActionListener(this);
		
		/*b2 = new Button("test.");
		add(b2);
		b2.addActionListener(this);*/
		
		addWindowListener(new WindowAdapter(){ 		 //�t���[���̃N���[�Y�B
			public void windowClosing(WindowEvent e){	 //
				System.exit(0);						 //
			}										 //
		});
		show();
	}
	
	
	public static void main(String args[]){
		tAvEmenu tavemenu = new tAvEmenu();
		//tavemenu.show();
	}

	public void actionPerformed(ActionEvent event) {	// ActionListener�ɕK�v�ȃ��\�b�h�B	
		if(event.getSource().equals(b1)){
			tAvSelectHP tavselect = new tAvSelectHP();
		}else{
		}
		
	}
}