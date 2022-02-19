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
public class Event {
	//field
	int id;
	//Haplotype event
	int type;//0 mutation, 1 recombination, 2 none, 3 root, 
	Haplotype hp;
	int event_site[];
	Haplotype parent_hp[];
	Haplotype child_hp[];
	
	//ARG Event
	Graph arg;
	Node adndarg[];
	Node rmndarg[];
	Edge adedarg[];
	Edge rmedarg[];
	
	Node oya1,oya2,ko;
	
	Edge ed[];
	//Forest Event
	Graph fr;
	Node adndfr[];
	Node rmndfr[];
	Edge adedfr[];
	Edge rmedfr[];
	//Tree Event
	Graph adTree[];
	Graph rmTree[];
	Node adndtr[];
	Node rmndtr[];
	Edge adedtr[];
	Edge rmedtr[];
	
	//constructor
	//For root
	public Event(int id_,int type_,Graph arg_,Graph forest_,Graph tree_,Node nd_arg,Node nd_fr,Node nd_tr){
		id = id_;
		type = type_;
		arg= arg_;
		fr=forest_;
		adTree = new Graph[1];
		adTree[0] = tree_;
		adndarg = new Node[1];
		adndarg[0] = nd_arg;
		adndfr = new Node[1];
		adndfr[0]=nd_fr;
		adndtr = new Node[1];
		adndtr[0]=nd_tr;
		event_site = new int[0];
		parent_hp = new Haplotype[0];
		child_hp = new Haplotype[0];
		rmndarg = new Node[0];
		adedarg =new Edge[0];
		rmedarg = new Edge[0];
		rmndfr = new Node[0];
		adedfr = new Edge[0];
		rmedfr = new Edge[0];
		rmndtr = new Node[0];
		adedtr = new Edge[0];
		rmedtr = new Edge[0];
		
	}
	//For mutation
	public Event(int id_,int type_,Graph arg_,Graph forest_,Graph tree_,Node nd_arg,Node nd_fr,Node nd_tr,Haplotype hp1,Haplotype hp2,int[] sites){
		id = id_;
		type = type_;
		arg= arg_;
		fr=forest_;
		adTree = new Graph[1];
		adTree[0] = tree_;
		adndarg = new Node[1];
		adndarg[0] = nd_arg;
		adndfr = new Node[1];
		adndfr[0]=nd_fr;
		adndtr = new Node[1];
		adndtr[0]=nd_tr;
		event_site = new int[sites.length];
		for(int i=0;i<sites.length;i++){
			event_site[i]=sites[i];
		}
		parent_hp = new Haplotype[1];
		parent_hp[0] = hp1;
		child_hp = new Haplotype[1];
		child_hp[0] = hp2;
		rmndarg = new Node[0];
		adedarg =new Edge[0];
		rmedarg = new Edge[0];
		rmndfr = new Node[0];
		adedfr = new Edge[0];
		rmedfr = new Edge[0];
		rmndtr = new Node[0];
		adedtr = new Edge[0];
		rmedtr = new Edge[0];
		
	}
	//For recombination
	public Event(int id_,int type_,Graph arg_,Node nd_arg,Haplotype hp1,Haplotype hp2,Haplotype hp3,int[] sites){
		id = id_;
		type = type_;
		arg= arg_;
		
		
		adndarg = new Node[1];
		adndarg[0] = nd_arg;
		
		
		event_site = new int[sites.length];
		for(int i=0;i<sites.length;i++){
			event_site[i]=sites[i];
		}
		parent_hp = new Haplotype[2];
		parent_hp[0] = hp1;
		parent_hp[1]=hp2;
		child_hp = new Haplotype[1];
		child_hp[0] = hp3;
		rmndarg = new Node[0];
		adedarg =new Edge[0];
		rmedarg = new Edge[0];
		rmndfr = new Node[0];
		adedfr = new Edge[0];
		rmedfr = new Edge[0];
		rmndtr = new Node[0];
		adedtr = new Edge[0];
		rmedtr = new Edge[0];
		
	}
	public Event(int iteration_,int type_,Node oya1_,Node oya2_,Node ko_,int[] sites){
		id = iteration_;
		type = type_;
		oya1 = oya1_;
		oya2 = oya2_;
		ko = ko_;
		
		event_site = new int[sites.length];
		for(int i=0;i<sites.length;i++){
			event_site[i]=sites[i];
		}
	}
	//For mutation
	public Event(int iteration_,int type_,Node oya1_,Node ko_,int[] sites){
		id = iteration_;
		type = type_;
		oya1 = oya1_;
		
		ko = ko_;
		event_site = new int[sites.length];
		for(int i=0;i<sites.length;i++){
			event_site[i]=sites[i];
		}
	}
	//For none
	public Event(int iteration_,int type_){
		id = iteration_;
		type = type_;
		
	}
	//For root
	public Event(int iteration_,int type_, Node ko_){
		id = iteration_;
		type = type_;
		ko = ko_;
		
	}
	//method
	public void addParentHaplotype(Haplotype hp_){
		Haplotype tmp[];
		tmp = new Haplotype[parent_hp.length];
		for(int i=0;i<parent_hp.length;i++){
			tmp[i]=parent_hp[i];
		}
		tmp[parent_hp.length]=hp_;
		parent_hp=new Haplotype[tmp.length];
		for(int i=0;i<tmp.length;i++){
			parent_hp[i]=tmp[i];
		}
	}
	public void addChildHaplotype(Haplotype hp_){
		Haplotype tmp[];
		tmp = new Haplotype[child_hp.length+1];
		for(int i=0;i<child_hp.length;i++){
			tmp[i]=child_hp[i];
		}
		tmp[child_hp.length]=hp_;
		child_hp=new Haplotype[tmp.length];
		for(int i=0;i<tmp.length;i++){
			child_hp[i]=tmp[i];
		}
	}
	public void addHaplotype(Haplotype hp_){
		hp = hp_;
	}
	public void addEdge(Edge ed_){
		if(ed == null){
			ed = new Edge[1];
			ed[0] = ed_;
		}else{
			Edge tmp[];
			tmp = new Edge[ed.length+1];
			for(int i=0;i<ed.length;i++){
				tmp[i] = ed[i];
			}
			tmp[ed.length]=ed_;
			ed = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed[i] = tmp[i];
			}
		}
	}
	public void printAll(){
		String st ="Event ";
		st += "id " + id;
		st += " type " + type;//0 mutation, 1 recombination, 2 none, 3 root
		if(oya1 == null){
			
		}else{
			st += " oya1 " + oya1.id;
		}
		if(oya2 == null){
			
		}else{
			st += " oya2 " + oya2.id;
		}
		if(ko == null){
			
		}else{
			st += " ko " + ko.id;
		}
		if(child_hp == null){
			
		}else{
			st += " child hp ";
			for(int i=0;i<child_hp.length;i++){
				st += child_hp[i].id + " ";
			}
		}
		if(child_hp == null){
			
		}else{
			st += " parent hp ";
			for(int i=0;i<parent_hp.length;i++){
				st += parent_hp[i].id + " ";
			}
		}
		if(event_site ==null){
			
		}else{
			st += " event site ";
			for(int i=0;i<event_site.length;i++){
				st += event_site[i] + " ";
			}
		}
		if(hp == null){
			
		}else{
			st += " haplotype ";
			for(int i=0;i<hp.hp.length;i++){
				st += hp.hp[i] + " ";
			}
		}
		if(ed == null){
			
		}else{
			st += "edge ";
			for(int i=0;i<ed.length;i++){
				st += ed[i].id + " ";
			}
		}
		System.out.println(st);
	}
	public void printLess(){
		String st ="Event ";
		st += "id " + id;
		st += " type " + type;//0 mutation, 1 recombination, 2 none, 3 root
		if(oya1 == null){
			
		}else{
			st += " oya1 " + oya1.id;
		}
		if(oya2 == null){
			
		}else{
			st += " oya2 " + oya2.id;
		}
		if(ko == null){
			
		}else{
			st += " ko " + ko.id;
		}
		if(child_hp == null){
			
		}else{
			//st += " child hp ";
			for(int i=0;i<child_hp.length;i++){
				//st += child_hp[i].id + " ";
			}
		}
		if(child_hp == null){
			
		}else{
			//st += " parent hp ";
			for(int i=0;i<parent_hp.length;i++){
				//st += parent_hp[i].id + " ";
			}
		}
		if(event_site ==null){
			
		}else{
			st += " event site ";
			for(int i=0;i<event_site.length;i++){
				st += event_site[i] + " ";
			}
		}
		if(hp == null){
			
		}else{
			//st += " haplotype ";
			for(int i=0;i<hp.hp.length;i++){
				//st += hp.hp[i] + " ";
			}
		}
		if(ed == null){
			
		}else{
			st += "edge ";
			for(int i=0;i<ed.length;i++){
				st += ed[i].id + " ";
			}
		}
		System.out.println(st);
	}
	public String printLessString(){
		String st ="Event ";
		st += "id " + id;
		st += " type " + type;//0 mutation, 1 recombination, 2 none, 3 root
		if(oya1 == null){
			
		}else{
			st += " oya1 " + oya1.id;
		}
		if(oya2 == null){
			
		}else{
			st += " oya2 " + oya2.id;
		}
		if(ko == null){
			
		}else{
			st += " ko " + ko.id;
		}
		if(child_hp == null){
			
		}else{
			//st += " child hp ";
			for(int i=0;i<child_hp.length;i++){
				//st += child_hp[i].id + " ";
			}
		}
		if(child_hp == null){
			
		}else{
			//st += " parent hp ";
			for(int i=0;i<parent_hp.length;i++){
				//st += parent_hp[i].id + " ";
			}
		}
		if(event_site ==null){
			
		}else{
			st += " event site ";
			for(int i=0;i<event_site.length;i++){
				st += event_site[i] + " ";
			}
		}
		if(hp == null){
			
		}else{
			//st += " haplotype ";
			for(int i=0;i<hp.hp.length;i++){
				//st += hp.hp[i] + " ";
			}
		}
		if(ed == null){
			
		}else{
			st += "edge ";
			for(int i=0;i<ed.length;i++){
				st += ed[i].id + " ";
			}
		}
		st += "\n";
		return st;
		//System.out.println(st);
	}
	public void outLessString(String st_){
		String file = st_;
		String st;
		st = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
		st ="Event ";
		st += "id " + id;
		st += " type " + type;//0 mutation, 1 recombination, 2 none, 3 root
		if(oya1 == null){
			
		}else{
			st += " oya1 " + oya1.id;
		}
		if(oya2 == null){
			
		}else{
			st += " oya2 " + oya2.id;
		}
		if(ko == null){
			
		}else{
			st += " ko " + ko.id;
		}
		if(child_hp == null){
			
		}else{
			//st += " child hp ";
			for(int i=0;i<child_hp.length;i++){
				//st += child_hp[i].id + " ";
			}
		}
		if(child_hp == null){
			
		}else{
			//st += " parent hp ";
			for(int i=0;i<parent_hp.length;i++){
				//st += parent_hp[i].id + " ";
			}
		}
		if(event_site ==null){
			
		}else{
			st += " event site ";
			for(int i=0;i<event_site.length;i++){
				st += event_site[i] + " ";
			}
		}
		if(hp == null){
			
		}else{
			//st += " haplotype ";
			for(int i=0;i<hp.hp.length;i++){
				//st += hp.hp[i] + " ";
			}
		}
		if(ed == null){
			
		}else{
			st += "edge ";
			for(int i=0;i<ed.length;i++){
				st += ed[i].id + " ";
			}
		}
		st += "\n";
		
		//System.out.println(st);
		//System.out.println(st);
		bw1.write(st);
		bw1.close();

	}catch(Exception e){
		System.out.println(e);
	}
	
	}
}
