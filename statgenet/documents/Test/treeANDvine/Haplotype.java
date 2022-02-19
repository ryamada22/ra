/*
 * 作成日: 2005/07/18
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;


/**
 * @author Ryo Yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Haplotype {
	//field
	int id;
	int hp[];
	Event ev;
	Node argnd;
	Node forestnd[];
	Node treend[];
	int st[];
	int end[];
	int part;//解析範囲全長の場合はst endを与えないでＯＫ
	
	//constructor
	public Haplotype(int id_,int[] hp_){
		id = id_;
		hp = new int[hp_.length];
		for(int i=0;i<hp_.length;i++){
			hp[i]=hp_[i];
		}
		forestnd = new Node[0];
		treend = new Node[0];
		st = new int[0];
		end = new int[0];
		part = 0;
		
	}
	public Haplotype(int id_,int[] hp_,Node nd_arg,Node nd_fr,Node nd_tr){
		id = id_;
		hp = new int[hp_.length];
		for(int i=0;i<hp_.length;i++){
			hp[i]=hp_[i];
		}
		argnd = nd_arg;
		if(forestnd == null){
			forestnd = new Node[1];
			forestnd[0] = nd_fr;
		}else{
			Node tmp[];
			tmp = new Node[forestnd.length+1];
			for(int i=0;i<forestnd.length;i++){
				tmp[i]=forestnd[i];
			}
			tmp[forestnd.length]=nd_fr;
			forestnd = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				forestnd[i]=tmp[i];
			}
		}
		if(treend == null){
			treend = new Node[1];
			treend[0] = nd_tr;
		}else{
			Node tmp[];
			tmp = new Node[treend.length+1];
			for(int i=0;i<treend.length;i++){
				tmp[i]=treend[i];
			}
			tmp[treend.length]=nd_tr;
			treend = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				treend[i]=tmp[i];
			}
		}
		st = new int[0];
		end = new int[0];
		part = 0;
		
	}
	public Haplotype(int id_,int[] hp_,Node nd_arg,Node nd_tr){//forest node以外の登録
		id = id_;
		hp = new int[hp_.length];
		for(int i=0;i<hp_.length;i++){
			hp[i]=hp_[i];
		}
		argnd = nd_arg;
		if(forestnd == null){
			forestnd = new Node[0];
			//forestnd[0] = nd_fr;
		}else{
			Node tmp[];
			tmp = new Node[forestnd.length];
			for(int i=0;i<forestnd.length;i++){
				tmp[i]=forestnd[i];
			}
			//tmp[forestnd.length]=nd_fr;
			forestnd = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				forestnd[i]=tmp[i];
			}
		}
		if(treend == null){
			treend = new Node[1];
			treend[0] = nd_tr;
		}else{
			Node tmp[];
			tmp = new Node[treend.length+1];
			for(int i=0;i<treend.length;i++){
				tmp[i]=treend[i];
			}
			tmp[treend.length]=nd_tr;
			treend = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				treend[i]=tmp[i];
			}
		}
		st = new int[0];
		end = new int[0];
		part = 0;
	}
	public void addStEnd(int[] st_, int[] end_){
		part =1;
		st = new int[st_.length];
		end = new int[end_.length];
		for(int i=0;i<st_.length;i++){
			st[i]=st_[i];
			end[i]=end_[i];
		}
	}
	public void addEvent(Event ev_){
		ev= ev_;
	}
	public void addArgNode(Node nd_){
		argnd = nd_;
	}
	public void addForestNode(Node nd_){
		if(forestnd == null){
			forestnd = new Node[1];
			forestnd[0]=nd_;
		}else{
			Node tmp[];
			tmp = new Node[forestnd.length+1];
			for(int i=0;i<forestnd.length;i++){
				tmp[i] = forestnd[i];
			}
			tmp[forestnd.length]=nd_;
			forestnd = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				forestnd[i]=tmp[i];
			}
		}
		
	}
	public void addTreeNode(Node nd_){
		Node tmp[];
		tmp = new Node[treend.length+1];
		for(int i=0;i<treend.length;i++){
			tmp[i] = treend[i];
		}
		tmp[treend.length]=nd_;
		treend = new Node[tmp.length];
		for(int i=0;i<tmp.length;i++){
			treend[i]=tmp[i];
		}
	}
	public void printHaplotypeAll(){
		String st ="";
		st = "haplotype ";
		st += "id " + id;
		st += "\nevent " + ev;
		if(argnd == null){
			
		}else{
			st += "\nARGnode " + argnd.id;
		}
		
		st += "\nforest ";
		for(int i=0;i<forestnd.length;i++){
			st += forestnd[i].id + " ";
		}
		st += "\nhp ";
		for(int i=0;i<hp.length;i++){
			st += hp[i] + " ";
		}
		System.out.println(st);
		
	}
	public void printHaplotypeElem(){
		String st ="";
		st = "haplotype ";
		st += "id " + id + " * ";
		
		for(int i=0;i<hp.length;i++){
			st += hp[i] + " ";
		}
		System.out.println(st);
		
	}
	public String printHaplotypeElemString(){
		String st ="";
		st = "haplotype ";
		st += "id " + id + " * ";
		
		for(int i=0;i<hp.length;i++){
			st += hp[i] + " ";
		}
		//System.out.println(st);
		return st;
	}
	public String printHaplotypeElemOnlyString(){
		String st ="";
		//st = "haplotype ";
		st += "id " + id + " * ";
		
		for(int i=0;i<hp.length;i++){
			st += hp[i] + " ";
		}
		//System.out.println(st);
		return st;
	}
	public void printHaplotype(){
		String st ="";
		st = "haplotype ";
		
		st += "hp ";
		for(int i=0;i<hp.length;i++){
			st += hp[i] + " ";
		}
		System.out.println(st);
	}
}
