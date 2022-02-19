/*
 * 作成日: 2005/07/19
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

/**
 * @author yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class GrTrans {
	int id;
	Graph gr;
	Node nwnodes[];
	Node rmnodes[];
	Edge nwedges[];
	Edge rmedges[];
	
	//constructor
	public GrTrans(int id_, Graph gr_){
		id = id_;
		gr = gr_;
		nwnodes = new Node[0];
		rmnodes = new Node[0];
		nwedges = new Edge[0];
		rmedges = new Edge[0];
	}
	
	//method
	public void addNwnode(Node nd_){
		Node tmpnd[];
		tmpnd =new Node[nwnodes.length+1];
		for(int i=0;i<nwnodes.length;i++){
			tmpnd[i]=nwnodes[i];
		}
		tmpnd[nwnodes.length] = nd_;
		nwnodes = new Node[tmpnd.length];
		for(int i=0;i<tmpnd.length;i++){
			nwnodes[i]=tmpnd[i];
		}
	}
	public void addRmnode(Node nd_){
		Node tmpnd[];
		tmpnd =new Node[rmnodes.length+1];
		for(int i=0;i<rmnodes.length;i++){
			tmpnd[i]=rmnodes[i];
		}
		tmpnd[rmnodes.length] = nd_;
		rmnodes = new Node[tmpnd.length];
		for(int i=0;i<tmpnd.length;i++){
			rmnodes[i]=tmpnd[i];
		}
	}
	public void addNwedge(Edge ed_){
		Edge tmped[];
		tmped =new Edge[nwedges.length+1];
		for(int i=0;i<nwedges.length;i++){
			tmped[i]=nwedges[i];
		}
		tmped[nwedges.length] = ed_;
		nwedges = new Edge[tmped.length];
		for(int i=0;i<tmped.length;i++){
			nwedges[i]=tmped[i];
		}
	}
	public void addRmedge(Edge ed_){
		Edge tmped[];
		tmped =new Edge[rmedges.length+1];
		for(int i=0;i<rmedges.length;i++){
			tmped[i]=rmedges[i];
		}
		tmped[rmedges.length] = ed_;
		rmedges = new Edge[tmped.length];
		for(int i=0;i<tmped.length;i++){
			rmedges[i]=tmped[i];
		}
	}
	
	public void printAll(){
		printid();
		printrmnode();
		printrmedge();
		printnwnode();
		printnwedge();
	}
	public void printid(){
		String st="";
		st += "id " + id;
		System.out.println(st);
	}
	
	public void printrmnode(){
		String st="";
		st += "removed node " ;
		for(int i=0;i<rmnodes.length;i++){
			st += rmnodes[i].id + " ";
		}
		System.out.println(st);
	}
	public void printrmedge(){
		String st="";
		st += "removed edge " ;
		for(int i=0;i<rmedges.length;i++){
			st += rmedges[i].id + " ";
		}
		System.out.println(st);
	}
	public void printnwnode(){
		String st="";
		st += "new node " ;
		for(int i=0;i<nwnodes.length;i++){
			st +=nwnodes[i].id + " ";
		}
		System.out.println(st);
	}
	public void printnwedge(){
		String st="";
		st += "new edge " ;
		for(int i=0;i<nwedges.length;i++){
			st += nwedges[i].id + " ";
		}
		System.out.println(st);
	}
}
