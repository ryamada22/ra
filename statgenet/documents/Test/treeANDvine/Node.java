/*
 * 作成日: 2005/07/15
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.util.Hashtable;


/**
 * @author yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 * Nodeはグラフの中で相互に関係を有する解析の単位である
 * Nodeはただ１つのグラフに存在する
 */
public class Node {
	/*
	 * field
	 */
	int id;
	Graph gr;//該当グラフ
	int id_gr;//グラフ内id
	double x,y,z;//位置を有する
	double r;//大きさを有する
	Edge edge[];
	String name;
	int label;//int型の名称
	int type;//0 root, 1 coalescence, 2 recombinant
	Event ev[];
	Hashtable hs;//もろもろの情報を格納する
	
	//ハプロタイプフェージング特有設定
	Haplotype hp[];
	
	/*
	 * method
	 */
	public void shift(double x_,double y_,double z_){//平行移動
		x+=x_;y+=y_;z+=z_;
	}

	public void addId_gr(int id_){//id_gr変更
		id_gr = id_;
	}
	
	public void copy(Node nd_){
		gr = nd_.gr;
		id_gr = nd_.id_gr;
		x=nd_.x;y=nd_.y;z=nd_.z;
		r = nd_.r;//大きさを有する
		edge =new Edge[nd_.edge.length];
		for(int i=0;i<nd_.edge.length;i++){
			edge[i] = nd_.edge[i];
		}
		name = nd_.name;
		label = nd_.label;//int型の名称
		type = nd_.type;//0 root, 1 coalescence, 2 recombinant
		ev = new Event[nd_.ev.length];
		for(int i=0;i<nd_.ev.length;i++){
			ev[i]=nd_.ev[i];
		}
		//Hashtable hs;//もろもろの情報を格納する
		
		hp = new Haplotype[nd_.hp.length];
		for(int i=0;i<nd_.hp.length;i++){
			hp[i]=nd_.hp[i];
		}
	}
	
	public void addEdge(Edge edge_){//edgeを追加
		if(edge == null){
			edge = new Edge[1];
			edge[0] = edge_;
		}else{
			Edge tmpedge[];
			tmpedge = new Edge[edge.length +1];
			for(int i=0;i<edge.length;i++){
				tmpedge[i]=edge[i];
			}
			tmpedge[edge.length] = edge_;
			edge = new Edge[tmpedge.length];
			for(int i=0;i<tmpedge.length;i++){
				edge[i]=tmpedge[i];
			}
		}
	}


	public void addEvent(Event ev_){//eventを追加
		if(ev == null){
			ev = new Event[1];
			ev[0] = ev_;
		}else{
			Event tmpev[];
			tmpev = new Event[ev.length +1];
			for(int i=0;i<ev.length;i++){
				tmpev[i]=ev[i];
			}
			tmpev[ev.length] = ev_;
			ev = new Event[tmpev.length];
			for(int i=0;i<tmpev.length;i++){
				ev[i]=tmpev[i];
			}
		}
	}

	public void addType(int type_){//typeを追加
		type = type_;
	}
	
	public void addHaplotype(Haplotype hp_){
		if(hp == null){
			hp = new Haplotype[1];
			hp[0] = hp_;
		}else{
			Haplotype tmphp[];
			tmphp = new Haplotype[hp.length +1];
			for(int i=0;i<hp.length;i++){
				tmphp[i]=hp[i];
			}
			tmphp[hp.length] = hp_;
			hp = new Haplotype[tmphp.length];
			for(int i=0;i<tmphp.length;i++){
				hp[i]=tmphp[i];
			}
		}
	}
	public void printNodeAll(){
		String st = "　Node ";
		st += "　id" + id;
		st += "　gr " ;
		st += id_gr ;
		st += "　x,y,z " + x + " " + y + " " + z + " ";
		st += "　edge ";
		if(edge == null){
		}else{
			for(int i=0;i<edge.length;i++){
				st += edge[i].id + " ";
			}
		}
		st += " event ";
		if(ev == null){
		}else{
			for(int i=0;i<ev.length;i++){
				st += ev[i].id + " ";
			}
		}
		
	}
	public void addHashdata(String key,String value){
		hs = new Hashtable();
		hs.put("key","value");
	}
	public void takeawayEdge(Edge ed_){
		if(edge.length>0){
			int numregistry=0;
			for(int i=0;i<edge.length;i++){
				if(edge[i]==ed_){
					numregistry++;
				}
			}
			Edge tmp[];
			//tmp = new Edge[edge.length-1];
			tmp = new Edge[edge.length-numregistry];
			int counter=0;
			for(int i=0;i<edge.length;i++){
				if(edge[i]==ed_){
					
				}else{
					tmp[counter]=edge[i];
					counter++;
				}
			}
			edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				edge[i]=tmp[i];
			}
		}
		
	}

	/*
	 * constructor
	 */
	
	public Node(int id_){
		id = id_;
		x = 0;y=0;z=0;//defaultで位置を与える
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_){
		id = id_;
		gr=gr_;
		x = 0;y=0;z=0;//defaultで位置を与える
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_){
		id = id_;
		gr=gr_;
		id_gr=id_gr_;
		x = 0;y=0;z=0;//defaultで位置を与える
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,double x_,double y_,double z_){
		id = id_;
		x = x_;y=y_;z=z_;//defaultで位置を与える
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,double x_,double y_,double z_){
		id = id_;
		gr = gr_;
		x = x_;y=y_;z=z_;
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_,double x_,double y_,double z_){
		id = id_;
		gr = gr_;
		id_gr=id_gr_;
		x = x_;y=y_;z=z_;
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_,double x_,double y_,double z_,int type_){
		id = id_;
		gr = gr_;
		id_gr=id_gr_;
		x = x_;y=y_;z=z_;
		r =1;//defaultでサイズを与える
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = type_;
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
}
