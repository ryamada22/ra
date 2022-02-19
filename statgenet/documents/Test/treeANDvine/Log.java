/*
 * 作成日: 2005/07/18
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author Ryo Yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Log {
	//Field
	Graph gr[],rm_gr[];
	Node nd[],rm_nd[];
	Edge ed[],rm_ed[];
	Event ev[];
	Haplotype hp[];
	GrTrans gt[];
	Path pt[];
	int retroMR[];
	Haplotype retroHpChild[];
	Haplotype retroHpParent1[];
	Haplotype retroHpParent2[];
	int backrecdepth;
	
	
	int numsnp;//SNPの数、配列長にも相当
	int iteration;//mutation + recombinationの回数
	int num_iteration;
	double recratio;//recombination - mutation の分配確率　閾値未満はrec
	
	//constructor
	
	
	public Log(){
		gr = new Graph[0];
		nd = new Node[0];
		rm_nd = new Node[0];
		ed = new Edge[0];
		rm_ed = new Edge[0];
		ev = new Event[0];
		hp = new Haplotype[0];
		gt = new GrTrans[0];
		pt = new Path[0];
		retroMR = new int[0];//m with p=0,m without p=1,r = 2
		retroHpChild = new Haplotype[0];
		retroHpParent1 = new Haplotype[0];
		retroHpParent2 = new Haplotype[0];
		
	}
	//method
	public void addRetroHpChild(Haplotype hp_){
		if(retroHpChild == null){
			retroHpChild = new Haplotype[1];
			retroHpChild[0] = hp_;
			
		}else{
			Haplotype tmp[];
			tmp = new Haplotype[retroHpChild.length+1];
			for(int i=0;i<retroHpChild.length;i++){
				tmp[i]=retroHpChild[i];
			}
			tmp[retroHpChild.length] = hp_;
			retroHpChild=new Haplotype[tmp.length];
			for(int i=0;i<tmp.length;i++){
				retroHpChild[i]=tmp[i];
			}
			
		}
	}
	public void addRetroHpParent1(Haplotype hp_){
		if(retroHpParent1 == null){
			retroHpParent1 = new Haplotype[1];
			retroHpParent1[0] = hp_;
			
		}else{
			Haplotype tmp[];
			tmp = new Haplotype[retroHpParent1.length+1];
			for(int i=0;i<retroHpParent1.length;i++){
				tmp[i]=retroHpParent1[i];
			}
			tmp[retroHpParent1.length] = hp_;
			retroHpParent1=new Haplotype[tmp.length];
			for(int i=0;i<tmp.length;i++){
				retroHpParent1[i]=tmp[i];
			}
			
		}
	}
	public void addRetroHpParent2(Haplotype hp_){
		if(retroHpParent2 == null){
			retroHpParent2 = new Haplotype[1];
			retroHpParent2[0] = hp_;
			
		}else{
			Haplotype tmp[];
			tmp = new Haplotype[retroHpParent2.length+1];
			for(int i=0;i<retroHpParent2.length;i++){
				tmp[i]=retroHpParent2[i];
			}
			tmp[retroHpParent2.length] = hp_;
			retroHpParent2=new Haplotype[tmp.length];
			for(int i=0;i<tmp.length;i++){
				retroHpParent2[i]=tmp[i];
			}
			
		}
	}
	public void addRetroMR(int int_){
		if(retroMR == null){
			retroMR = new int[1];
			retroMR[0] = int_;
			
		}else{
			int tmp[];
			tmp = new int[retroMR.length+1];
			for(int i=0;i<retroMR.length;i++){
				tmp[i]=retroMR[i];
			}
			tmp[retroMR.length] = int_;
			retroMR=new int[tmp.length];
			for(int i=0;i<tmp.length;i++){
				retroMR[i]=tmp[i];
			}
			
		}
	}
	public void addGraph(Graph gr_){
		if(gr == null){
			gr = new Graph[1];
			gr[0] = gr_;
			
		}else{
			Graph tmp[];
			tmp = new Graph[gr.length+1];
			for(int i=0;i<gr.length;i++){
				tmp[i]=gr[i];
			}
			tmp[gr.length] = gr_;
			gr=new Graph[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr[i]=tmp[i];
			}
			
		}
	}
	public void addNode(Node nd_){
		if(nd == null){
			nd = new Node[1];
			nd[0] = nd_;
		}else{
			Node tmp[];
			tmp = new Node[nd.length+1];
			for(int i=0;i<nd.length;i++){
				tmp[i]=nd[i];
			}
			tmp[nd.length] = nd_;
			nd=new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				nd[i]=tmp[i];
			}
		}
	}
	public void addEdge(Edge ed_){
		if(ed == null){
			ed = new Edge[1];
			ed[0] = ed_;
		}else{
			Edge tmp[];
			tmp = new Edge[ed.length+1];
			for(int i=0;i<ed.length;i++){
				tmp[i]=ed[i];
			}
			tmp[ed.length] = ed_;
			ed=new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed[i]=tmp[i];
			}
		}
	}
	public void addEvent(Event ev_){
		if(ev == null){
			ev = new Event[1];
			ev[0] = ev_;
		}else{
			Event tmp[];
			tmp = new Event[ev.length+1];
			for(int i=0;i<ev.length;i++){
				tmp[i]=ev[i];
			}
			tmp[ev.length] = ev_;
			ev=new Event[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ev[i]=tmp[i];
			}
		}
	}
	public void addHaplotype(Haplotype hp_){
		if(hp == null){
			hp = new Haplotype[1];
			hp[0] = hp_;
		}else{
			Haplotype tmp[];
			tmp = new Haplotype[hp.length+1];
			for(int i=0;i<hp.length;i++){
				tmp[i]=hp[i];
			}
			tmp[hp.length] = hp_;
			hp=new Haplotype[tmp.length];
			for(int i=0;i<tmp.length;i++){
				hp[i]=tmp[i];
			}
		}
	}
	public void addGrTrans(GrTrans gt_){
		if(gt == null){
			gt = new GrTrans[1];
			gt[0] = gt_;
		}else{
			GrTrans tmp[];
			tmp = new GrTrans[gt.length+1];
			for(int i=0;i<gt.length;i++){
				tmp[i]=gt[i];
			}
			tmp[gt.length] = gt_;
			gt=new GrTrans[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gt[i]=tmp[i];
			}
		}
	}
	public void addPath(Path pt_){
		if(pt == null){
			pt = new Path[1];
			pt[0] = pt_;
			
		}else{
			Path tmp[];
			tmp = new Path[pt.length+1];
			for(int i=0;i<pt.length;i++){
				tmp[i]=pt[i];
			}
			tmp[pt.length] = pt_;
			pt=new Path[tmp.length];
			for(int i=0;i<tmp.length;i++){
				pt[i]=tmp[i];
			}
			
		}
	}
	public void printAll(){
		printGraph();
		printNode();
		printEdge();
		printEvent();
		printHaplotype();
		printGrTrans();
		printPath();
	}
	public void printGraph(){
		String st;
		st = "Graph\n";
		for(int i=0;i<gr.length;i++){
			st += "graph id " + i;
		}
		st += "\n";
		System.out.println(st);
	}
	public void printPath(){
		String st;
		st = "Path\n";
		for(int i=0;i<pt.length;i++){
			st += "path id " + i;
			pt[i].printPathAll();
		}
		st += "\n";
		System.out.println(st);
	}
	public void printNode(){
		String st;
		st = "Node\n";
		for(int i=0;i<nd.length;i++){
			st += "node id " + i + " ";
			nd[i].printNodeAll();
		}
		st += "\n";
		System.out.println(st);
	}
	public void printEdge(){
		String st;
		st = "Edge\n";
		for(int i=0;i<ed.length;i++){
			st += "edge id " + i + " ";
		}
		st += "\n";
		System.out.println(st);
	}
	public void printEvent(){
		String st;
		st = "Event\n";
		for(int i=0;i<ev.length;i++){
			st += "event id " + i + " ";
			ev[i].printAll();
		}
		st += "\n";
		System.out.println(st);
	}
	public void printEventLess(){
		String st;
		//st = "ああああああああああEvent\n";
		st ="";
		for(int i=0;i<ev.length;i++){
			//st += "event id " + i + " ";
			//ev[i].printAll();
			//String tmpst="";
			st += ev[i].printLessString();
			
		}
		//st += "\n";
		System.out.println(st);
	}
	public void outEventLess(String st_){
		String file = st_;
		String st;
		st = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
		//st = "ああああああああああEvent\n";
		st ="";
		for(int i=0;i<ev.length;i++){
			//st += "event id " + i + " ";
			//ev[i].printAll();
			//String tmpst="";
			st += ev[i].printLessString();
			
		}
		//st += "\n";
		
		
		//System.out.println(st);
		bw1.write(st);
		bw1.close();

	}catch(Exception e){
		System.out.println(e);
	}
	}
	public void printHaplotype(){
		String st;
		st = "Haplotype\n";
		for(int i=0;i<hp.length;i++){
			st += "haplotype id " + i + " ";
			hp[i].printHaplotypeElem();
			hp[i].printHaplotypeAll();
		}
		st += "\n";
		System.out.println(st);
	}
	public void printGrTrans(){
		String st;
		st = "GrTrans\n";
		for(int i=0;i<gt.length;i++){
			st += "GrTrans id " + i + " ";
		}
		st += "\n";
		System.out.println(st);
	}
	public void printRetroMR(){
		String st;
		st = "RetroMR\n";
		for(int i=0;i<retroMR.length;i++){
			st += "iter" + i + " mr " + retroMR[i] + " \n";
		}
		st += "\n";
		System.out.println(st);
	}
	public void printRetroHp(){
		String st;
		st = "RetroHp\n";
		String tmphp;
		for(int i=0;i<retroHpChild.length;i++){
			if(retroMR[i]==0 || retroMR[i]==1){
				st += "iter" + i + "mr " + retroMR[i] + " child " + retroHpChild[i].id  
				+ " " + retroHpChild[i].st[0] 
				+ " " + retroHpChild[i].end[0]
				+ " prt1 " + retroHpParent1[i].id;
				if(retroHpParent1[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpParent1[i].st[0] 
													+ " " + retroHpParent1[i].end[0];
				}
				 
				st += " prt2 " + "none"+ " "
				
				;
				tmphp = retroHpChild[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent1[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				st += "\n";
			}else if(retroMR[i]==2){
				st += "iter" + i + "mr " + retroMR[i] + " child " + retroHpChild[i].id  
				+ " " + retroHpChild[i].st[0] 
				+ " " + retroHpChild[i].end[0]
				+ " prt1 " + retroHpParent1[i].id;
				if(retroHpParent1[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpParent1[i].st[0] 
													+ " " + retroHpParent1[i].end[0];
				}
				st+= " prt2 " + retroHpParent2[i].id+ " ";
				if(retroHpParent2[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpParent2[i].st[0] 
													+ " " + retroHpParent2[i].end[0];
				}
				tmphp = retroHpChild[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent1[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent2[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				st += "\n";
			}else if(retroMR[i]==3){
				st += "iter" + i + "mr " + retroMR[i] + " child " + retroHpChild[i].id  
				+ " ";
				if(retroHpChild[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpChild[i].st[0] 
													+ " " + retroHpChild[i].end[0];
				}
				st+= " prt1 " + retroHpParent1[i].id;
				if(retroHpParent1[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpParent1[i].st[0] 
													+ " " + retroHpParent1[i].end[0];
				}
				st+= " prt2 " + "none"+ " "
				
				;
				tmphp = retroHpChild[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent1[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent2[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				st += "\n";
			}else if(retroMR[i]==4){
				st += "iter" + i + "mr " + retroMR[i] + " child " + retroHpChild[i].id  
				+ " ";
				if(retroHpChild[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpChild[i].st[0] 
													+ " " + retroHpChild[i].end[0];
				}
				st+= " prt1 " + retroHpParent1[i].id;
				if(retroHpParent1[i].st.length==0){
					st += " st=null ";
				}else{
					st +=" " + retroHpParent1[i].st[0] 
													+ " " + retroHpParent1[i].end[0];
				}
				st+= " prt2 " + "none"+ " "
				
				;
				tmphp = retroHpChild[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				tmphp = retroHpParent1[i].printHaplotypeElemOnlyString();
				st += " " + tmphp;
				st += "\n";
			}
			
		}
		st += "\n";
		System.out.println(st);
	}
}
