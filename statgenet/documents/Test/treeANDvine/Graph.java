/*
 * 作成日: 2005/07/15
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Hashtable;


/**
 * @author yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 * グラフは木か林(複数の木の集合)か
 * サイクルを持つかいなか
 * については、作成の過程で記録しておくと便利なので、記録する
 */
public class Graph {
	/*
	 * field
	 */
	int id;
	Node nodes[];
	Edge edges[];
	int direction;//0 undirected,1 directed
	int type;//0 ARG, 1 Forest, 2 Tree
	int tr;//tree:1,non forests:2,それ以外:3
	Event ev[];
	String name;
	int label;
	double or_x,or_y,or_z;//グラフの原点　ノードの表示はこの原点とノードの持つ座標とで決める
	double len_x,len_y,len_z;//無限３時限空間におけるグラフの存在範囲
	Hashtable hs;

	
	/*
	 * method
	 */
	public void shift_or(double x_,double y_,double z_){
		or_x += x_;or_y += y_;or_z+=z_;
	}
	public void shift(double x_,double y_,double z_){
		for(int i=0;i<nodes.length;i++){
			nodes[i].x +=x_;nodes[i].y +=y_;nodes[i].z +=z_;
		}
	}
	public void scale(double x_,double y_,double z_){
		for(int i=0;i<nodes.length;i++){
			nodes[i].x *=x_;nodes[i].y *=y_;nodes[i].z *=z_;
		}
	}
	public void range(double x1,double x2,double y1,double y2,double z1,double z2){
		or_x = x1;or_y = y1;or_z=z1;
		len_x=x2;len_y=y2;len_z=z2;
	}
	public void addDirection(int di){
		direction = di;
	}
	public void addEvent(Event ev_){
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
	/*
	 * グラフ基本操作
	 */
	public void addNode(Graph gr_,Node node_){
		/*
		 * 既存のグラフにそれとは連結していない、木(ただし点のみ)を加える作業にあたる
		 * ノードをグラフのノードリストに加える
		 * ノードの所属グラフを記載する
		 * ノードグラフ内IDを付与する
		 * 既存グラフと与えられた点との間にエッジがないので、ノードにつながるエッジ情報の書き換えは不要
		 * 点の付与によりtree属性が変化する
		 * 点を加えてそのあとに、plusEdgeをしないなら、tree属性は確定、plusEdgeとセットで行うときは、連結の可能性あり
		 * 
		 */
		node_.gr=gr_;
		node_.id_gr=gr_.nodes.length;
		if(gr_.nodes == null){
			gr_.nodes = new Node[1];
			gr_.nodes[0]=node_;
		}else{
			Node tmp[];
			tmp = new Node[gr_.nodes.length +1];
			for(int i=0;i<gr_.nodes.length;i++){
				tmp[i] = gr_.nodes[i];
			}
			tmp[gr_.nodes.length]=node_;
			gr_.nodes = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.nodes[i] = tmp[i];
			}
			
		}
		if(gr_.nodes.length==1){
			gr_.tr=1;
		}else{
			if(gr_.tr ==1){
				gr_.tr=2;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
		}
		
		
	}
	public void takeawayNode(Graph gr_,Node nd_){//単にノードの登録抹消
		Node tmp[];
		tmp= new Node[gr_.nodes.length-1];
		int counter=0;
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i]==nd_){
				gr_.nodes[i].gr=null;
				
			}else{
				tmp[counter]=gr_.nodes[i];
				tmp[counter].id_gr=counter;
				counter++;
				
			}
		}
		gr_.nodes=new Node[tmp.length];
		for(int i=0;i<tmp.length;i++){
			gr_.nodes[i]=tmp[i];
		}
		
	}
	public void takeawayEdge(Graph gr_,Edge edge_){//単にエッジの登録抹消
		Edge tmp[];
		tmp= new Edge[gr_.edges.length-1];
		int counter=0;
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i]==edge_){
				gr_.edges[i].gr=null;
			}else{
				tmp[counter]=gr_.edges[i];
				tmp[counter].id_gr=counter;
				counter++;
				
			}
		}
		gr_.edges=new Edge[tmp.length];
		for(int i=0;i<tmp.length;i++){
			gr_.edges[i]=tmp[i];
		}
		
	}
	public void plusEdge(Graph gr_,Edge edge_){//単にedgeをエッジリストに加えるだけ
		edge_.gr=gr_;
		edge_.id_gr=gr_.edges.length;
		if(gr_.edges == null){
			gr_.edges = new Edge[1];
			gr_.edges[0]=edge_;
		}else{
			Edge tmp[];
			tmp = new Edge[gr_.edges.length +1];
			for(int i=0;i<gr_.edges.length;i++){
				tmp[i] = gr_.edges[i];
			}
			tmp[gr_.edges.length]=edge_;
			gr_.edges = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.edges[i] = tmp[i];
			}
		}
		
		
	}
	public void addEdge(Graph gr_,Edge edge_){
		/*
		 * edgeは両端点とそれを結ぶedgeからなる
		 * このエッジはまだグラフに未登録なので、既存グラフ内に全部もしくは一部が存在する可能性がある
		 * それを既存のグラフに加える
		 * 両端点が既存のグラフにすでに存在しているかどうかで変わる
		 * 両端点ともに存在しない場合には、2ノード１エッジの非連結の木の追加に相当する
		 * １端点が存在し、もう１端点が存在しない場合には、連結することになる
		 * 両端点が存在し、それを結ぶエッジがすでに存在している場合には、
		 *     加えるエッジが既存のエッジと同じ関係を表しているならば何もしない
		 * 両端点が存在し、それを結ぶエッジが存在していない場合には、エッジのみを加える
		 * 両端点が存在し、それを結ぶエッジが存在しているが、2重エッジを持たせることが適切な場合には、追加する
		 * 今、既存グラフのエッジのすべてと加えようとしているエッジとの間でEdge.eaqualityチェックを行い、trueが返れば追加しない
		 * falseが返れば、追加する
		 */
		boolean blconn = false;
		boolean dblconn = false;
		boolean blexist = false;
		blconn=edge_.connection(gr_);
		if(blconn == true){
			dblconn = edge_.double_connection(gr_);
			if(dblconn == true){
				blexist= edge_.exist(gr_);
			}
		}
		
		if(blexist == true){
			//System.out.println("already exist");
			//何もしない
		}else if(dblconn==true){
			//System.out.println("double connection");
			/*
			 * nodeは増やさずにエッジを加える
			 * エッジの所属グラフを与える
			 * エッジには、新たなグラフ内IDを振る
			 * 加えるエッジの両端点のエッジ情報を更新する
			 * 生じるグラフは必ず、木でも林でもない
			 */
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			gr_.tr=3;
			
			
		}else if(blconn == true){
			//System.out.println("connection");
			/*
			 * 1点で連結
			 * １点を追加
			 * ノードの所属グラフを与える
			 * エッジを追加
			 * エッジの所属グラフを与える
			 * それぞれグラフ内IDを振る
			 * 連結エッジ情報を更新する
			 * 生じるグラフは木への追加なら、木、林への追加なら林、それ以外への追加ならそれ以外
			 */
			Node tmpnd;
			tmpnd = new Node(-1);//仮
			for(int i=0;i<gr_.nodes.length;i++){
				if(gr_.nodes[i] == edge_.start){
					tmpnd=edge_.end;
				}
				if(gr_.nodes[i] == edge_.end){
					tmpnd=edge_.start;
				}
			}
			tmpnd.gr=gr_;
			tmpnd.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,tmpnd);
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			if(gr_.tr==1){
				gr_.tr=1;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			
		}else{
			//System.out.println("independent");
			/*
			 * ２ノード追加、１エッジ追加
			 * ノードとエッジの所属グラフを与える
			 * グラフ内IDの更新
			 * 両端点ノードの連結エッジ情報に注意
			 * 生じるグラフは木への追加なら、林、林への追加なら林、それ以外への追加ならそれ以外
			 */
			edge_.start.gr=gr_;
			edge_.end.gr=gr_;
			edge_.start.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,edge_.start);
			edge_.end.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,edge_.end);
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			if(gr_.tr==1){
				gr_.tr=2;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			
		}
	}

	public void addGraph(Graph graph1,Graph graph2){
		/*
		 * 第1引数グラフに第2引数グラフのノードとエッジが付け加わる
		 * ノードとエッジにはグラフ内IDが振りなおされる
		 * ノードとエッジはグラフに所属するので、ノードとエッジの所属情報を書き換える
		 * 既存グラフと与えられた点との間にエッジがないので、ノードにつながるエッジ情報の書き換えは不要
		 * 引数として与えられたグラフは空になる、空にする
		 * グラフとグラフを合わせるとき
		 * 木＋木＝林
		 * 林＋木＝林
		 * 林＋林＝林
		 * 木/林＋その他＝その他
		 * その他＋その他＝その他である
		 * */
		//ノードの付け加え
		if(graph2.nodes == null){
		}else{
			if(graph1.nodes == null){
				graph1.nodes = new Node[graph2.nodes.length];
				for(int i=0;i<graph2.nodes.length;i++){
					graph1.nodes[i]=graph2.nodes[i];
				}
			}else{
				Node tmpnodes[];
				tmpnodes = new Node[graph1.nodes.length + graph2.nodes.length];
				for(int i=0;i<graph1.nodes.length;i++){
					tmpnodes[i]=graph1.nodes[i];
				}
				for(int i=graph1.nodes.length;i<graph1.nodes.length+graph2.nodes.length;i++){
					tmpnodes[i] = graph2.nodes[i-graph1.nodes.length];
				}
				graph1.nodes = new Node[tmpnodes.length];
				for(int i=0;i<tmpnodes.length;i++){
					graph1.nodes[i] = tmpnodes[i];
				}
			}
		}
		//エッジの付け加え
		if(graph2.edges == null){
		}else{
			if(graph1.edges == null){
				graph1.edges = new Edge[graph2.edges.length];
				for(int i=0;i<graph2.edges.length;i++){
					graph1.edges[i]=graph2.edges[i];
				}

			}else{
				Edge tmpedges[];
				tmpedges = new Edge[graph1.edges.length + graph2.edges.length];
				for(int i=0;i<graph1.edges.length;i++){
					tmpedges[i]=graph1.edges[i];
				}
				for(int i=graph1.edges.length;i<graph1.edges.length+graph2.edges.length;i++){
					tmpedges[i] = graph2.edges[i-graph1.edges.length];
				}
				graph1.edges = new Edge[tmpedges.length];
				for(int i=0;i<tmpedges.length;i++){
					graph1.edges[i] = tmpedges[i];
				}
			}
		}
		//グラフ内ID、エッジ内IDの付け直し、その所属グラフの付け直し
		for(int i=0;i<graph1.nodes.length;i++){
			graph1.nodes[i].id_gr=i;
			graph1.nodes[i].gr=graph1;
		}
		for(int i=0;i<graph1.edges.length;i++){
			graph1.edges[i].id_gr=i;
			graph1.edges[i].gr=graph1;
		}
		//tree状態
		if(graph1.tr == 1){
			if(graph2.tr ==1){
				graph1.tr=2;
			}else if(graph2.tr ==2){
				graph1.tr =2;
			}else if(graph2.tr==3){
				graph1.tr=3;
			}
		}else if(graph1.tr==2){
			if(graph2.tr ==1){
				graph1.tr=2;
			}else if(graph2.tr ==2){
				graph1.tr =2;
			}else if(graph2.tr==3){
				graph1.tr=3;
			}
		}else if(graph1.tr==3){
			graph1.tr=3;
		}
	}
/*
 * ここまでメソッドは確認July23
 * constructorも確認済み
 */
	public void removeEdge(Graph gr_,Edge ed){
		/*
		 * existチェックをして存在していたら、ノードを残して取り去る
		 * ノードのエッジ登録を修正する
		 * エッジのグラフ所属情報を変更する
		 * tree属性は木から林へ、林から林へ、
		 * その他からは林や木への変化の可能性があるが、
		 * この関数内では確認しない
		 */
		boolean ex=false;
		ex= ed.exist(gr_);
		if(ex == false){
			//System.out.println("No exist");
		}else{
			//ed登録抹消
			Edge tmp[];
			tmp = new Edge[gr_.edges.length-1];
			int counter=0;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i] == ed){
				}else{
					tmp[counter]=gr_.edges[i];
					tmp[counter].id_gr=counter;
					counter++;
				}
			}
			gr_.edges = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.edges[i]=tmp[i];
			}
			ed.gr=null;
			//nodeのエッジ情報修正
			tmp = new Edge[ed.start.edge.length-1];
			counter=0;
			for(int i=0;i<ed.start.edge.length;i++){
				if(ed.start.edge[i] == ed){
				}else{
					tmp[counter]=ed.start.edge[i];
					counter++;
				}
			}
			ed.start.edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed.start.edge[i]=tmp[i];
			}
			tmp = new Edge[ed.end.edge.length-1];
			counter=0;
			for(int i=0;i<ed.end.edge.length;i++){
				if(ed.end.edge[i] == ed){
				}else{
					tmp[counter]=ed.end.edge[i];
					counter++;
				}
			}
			ed.end.edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed.end.edge[i]=tmp[i];
			}
		}
		if(gr_.tr==1){
			gr_.tr=2;
		}else if(gr_.tr==2){
			gr_.tr=2;
		}else if(gr_.tr==3){
			gr_.tr=3;
		}
		
	}
	public void removeNode(Graph gr_,Node nd_){
		/*
		 * ノードを取り去るとは、ノードとともにそれに連結しているエッジも取り去ること
		 * ノードをリストからはずし、
		 * そのノードのグラフ所属を変更
		 * ついで、ノードに連結していたエッジをすべてとりさる
		 * tr属性は
		 * 木の葉(エッジ一本でつながっているノード)の除去の場合は木のまま
		 * 木から葉以外のノードを取り去ったら林
		 * 林から取り去ると普通は林だが、特殊な場合(取り去ることで２本の木からなる林の中１本の木を
		 * まるごと取り去ったら、木、などの特殊な場合は、あとで、残りが木かどうかの判定をすればよし
		 * その他からの取り去りはその他のまま(確かめてみないと不明だから）
		 * 
		 */
		
		//nodeの存在チェック
		int check=0;
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == nd_){
				check=1;
			}
		}
		if(check==0){
			System.out.println("node not exist");
		}else{
			if(gr_.tr==1){
				if(nd_.edge.length>=2){
					gr_.tr=2;
				}else if(nd_.edge.length<2){
					gr_.tr=1;
				}
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			gr_.takeawayNode(gr_,nd_);
		
			for(int i=0;i<nd_.edge.length;i++){
				gr_.removeEdge(gr_,nd_.edge[i]);
			}
		}		
		
	}
	public void changeNode(Graph gr_,Node nw,Node old){
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i].start == old){
				gr_.edges[i].start = nw;
				//gr_.edges[i].start.takeawayEdge(gr_.edges[i]);
				nw.addEdge(gr_.edges[i]);
				Edge tmp[];
				tmp=new Edge[old.edge.length-1];
				int counter=0;
				for(int j=0;j<old.edge.length;j++){
					if(old.edge[j]==gr_.edges[i]){
						
					}else{
						tmp[counter]=old.edge[j];
						counter++;
					}
				}
				old.edge = new Edge[tmp.length];
				for(int j=0;j<tmp.length;j++){
					old.edge[j]=tmp[j];
				}
			}
			if(gr_.edges[i].end == old){
				gr_.edges[i].end = nw;
				//gr_.edges[i].end.takeawayEdge(gr_.edges[i]);
				nw.addEdge(gr_.edges[i]);
				Edge tmp[];
				tmp=new Edge[old.edge.length-1];
				int counter=0;
				for(int j=0;j<old.edge.length;j++){
					if(old.edge[j]==gr_.edges[i]){
						
					}else{
						//System.out.println("old node " + old.id);
						//System.out.println("j of node " + j);
						tmp[counter]=old.edge[j];
						counter++;
					}
				}
				old.edge = new Edge[tmp.length];
				for(int j=0;j<tmp.length;j++){
					old.edge[j]=tmp[j];
				}
			}
		}
	}
	//Treeの場合
	public void removeEdgeAndGlue(Log lg_,Graph gr_,Edge ed_){
		/*
		 * 与えられたエッジを削除し
		 * その両端点をtakeawayし
		 * 新規のノードを作成登録し
		 * 両端点に接続しているエッジをこの新規ノードに付け替える
		 * tr属性は変化しない
		 */
		//gr_.printGraphAll();
		//System.out.println("removeEdge前");
		gr_.removeEdge(gr_,ed_);
		//System.out.println("removeEdge後addNode前");
		//gr_.printGraphAll();
		//gr_.takeawayNode(gr_,ed_.start);
		//gr_.takeawayNode(gr_,ed_.end);
		Node nwnd;
		double x_,y_,z_;
		x_=(ed_.start.x+ed_.end.x)/2+100;
		y_=(ed_.start.y+ed_.end.y)/2+100;
		z_=(ed_.start.z+ed_.end.z)/2+100;
		nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
		lg_.addNode(nwnd);
		//gr_.printGraphAllNodeEdge();
		gr_.addNode(gr_,nwnd);
		//System.out.println("addNode後changeNode1前");
		//gr_.printGraphAllNodeEdge();
		gr_.changeNode(gr_,nwnd,ed_.start);
		
		//System.out.println("changeNode1後changeNode2前");
		//gr_.printGraphAllNodeEdge();
		
		gr_.changeNode(gr_,nwnd,ed_.end);
		//System.out.println("changeNode2後takeawayNode1前");
		//gr_.printGraphAllNodeEdge();
		gr_.removeNode(gr_,ed_.start);	
		//System.out.println("takeawayNode1後takeawayNode2前");
		//gr_.printGraphAllNodeEdge();
		gr_.removeNode(gr_,ed_.end);	
		//System.out.println("takeawayNode2後haplotype書き換え前");
		//gr_.printGraphAllNodeEdge();
		
		//ハプロタイプ関係の継承
		
		for(int i=0;i<ed_.start.hp.length;i++){
			//新ノードへの登録
			nwnd.addHaplotype(ed_.start.hp[i]);
			//ハプロタイプへの新ノードの登録
			ed_.start.hp[i].addTreeNode(nwnd);
		}
		for(int i=0;i<ed_.end.hp.length;i++){
			nwnd.addHaplotype(ed_.end.hp[i]);
			ed_.end.hp[i].addTreeNode(nwnd);
		}
		//System.out.println("post removeEdgeAndGlue \n");
		//gr_.printGraphAllNodeEdge();
		
		
	}
	//サイクル・ループありの場合
	public void removeEdgeAndGluePT(Log lg_,Graph gr_,Edge ed_){
		/*
		 * 与えられたエッジを削除し
		 * その両端点をtakeawayし
		 * 新規のノードを作成登録し
		 * 両端点に接続しているエッジをこの新規ノードに付け替える
		 * tr属性は変化しない
		 */
		/*
		 * treeでの処置とサイクル・ループを許可した場合の処置は違いそうなので、
		 * 別関数とする
		 */
		/*
		 * 始点・終点の同一のエッジの除去は、該当エッジの登録をグラフと始点・終点ノードから抹消するだけ
		 * 
		 */
		
		if(ed_.start==ed_.end){
			gr_.takeawayEdge(gr_,ed_);
			ed_.start.takeawayEdge(ed_);
		}else{
//			gr_.printGraphAll();
			//System.out.println("removeEdge前");
			gr_.removeEdge(gr_,ed_);
			//System.out.println("removeEdge後addNode前");
			//gr_.printGraphAll();
			//gr_.takeawayNode(gr_,ed_.start);
			//gr_.takeawayNode(gr_,ed_.end);
			Node nwnd;
			double x_,y_,z_;
			x_=(ed_.start.x+ed_.end.x)/2+100;
			y_=(ed_.start.y+ed_.end.y)/2+100;
			z_=(ed_.start.z+ed_.end.z)/2+100;
			nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
			lg_.addNode(nwnd);
			nwnd.addHaplotype(ed_.start.hp[0]);
			//gr_.printGraphAllNodeEdge();
			gr_.addNode(gr_,nwnd);
			//System.out.println("addNode後changeNode1前");
			//gr_.printGraphAllNodeEdge();
			
			/*
			 * treeの場合との違いは、付け替えるエッジの中に、
			 * 取り去るエッジの始点・終点を、その始点・終点としているものが
			 * 存在することである
			 * それらを予め別処理しておく
			 * edgeの始点終点の変更と、取り去るエッジの旧始点・終点ノードのエッジ情報の書き換え
			 * 
			 */
			for(int i=0;i<gr_.edges.length;i++){
				//System.out.println("haitta");
				Node st=gr_.edges[i].start;
				Node end=gr_.edges[i].end;
				int point=0;
				if(st == ed_.start){
					point++;
				}else if(st == ed_.end){
					point++;
				}
				if(end == ed_.start){
					point++;
				}else if(end == ed_.end){
					point++;
				}
				//System.out.println("point " + point);
				if(point ==2){//新ノードから始まり、新ノードに終わるエッジである
					gr_.edges[i].start=nwnd;
					gr_.edges[i].end=nwnd;
					nwnd.addEdge(edges[i]);
					nwnd.addEdge(edges[i]);
					ed_.start.takeawayEdge(edges[i]);
					ed_.end.takeawayEdge(edges[i]);
				}
				
			}
			
			gr_.changeNode(gr_,nwnd,ed_.start);
			
			//System.out.println("changeNode1後changeNode2前");
			//gr_.printGraphAllNodeEdge();
			
			gr_.changeNode(gr_,nwnd,ed_.end);
			//System.out.println("changeNode2後takeawayNode1前");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.start);	
			//System.out.println("takeawayNode1後takeawayNode2前");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.end);	
			//System.out.println("takeawayNode2後haplotype書き換え前");
			//gr_.printGraphAllNodeEdge();
			
			//ハプロタイプ関係の継承
			
			for(int i=0;i<ed_.start.hp.length;i++){
				//新ノードへの登録
				nwnd.addHaplotype(ed_.start.hp[i]);
				//ハプロタイプへの新ノードの登録
				ed_.start.hp[i].addTreeNode(nwnd);
			}
			for(int i=0;i<ed_.end.hp.length;i++){
				nwnd.addHaplotype(ed_.end.hp[i]);
				ed_.end.hp[i].addTreeNode(nwnd);
			}
			//System.out.println("post removeEdgeAndGlue \n");
			//gr_.printGraphAllNodeEdge();
		}
		
		
		
	}
	public void removeEdgeAndGluePT4Retro(Log lg_,Graph gr_,Edge ed_){
		/*
		 * つけなおしたハプロタイプをTreeに付け加えるのではなく、argに付け加える
		 */
		/*
		 * 与えられたエッジを削除し
		 * その両端点をtakeawayし
		 * 新規のノードを作成登録し
		 * 両端点に接続しているエッジをこの新規ノードに付け替える
		 * tr属性は変化しない
		 */
		/*
		 * treeでの処置とサイクル・ループを許可した場合の処置は違いそうなので、
		 * 別関数とする
		 */
		/*
		 * 始点・終点の同一のエッジの除去は、該当エッジの登録をグラフと始点・終点ノードから抹消するだけ
		 * 
		 */
		/*
		 * 取り去るエッジの子ノードを親とするノード(１本のはず)については
		 * エッジのタイプ・ラベルは、取り去るそれで上書き
		 */
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i].start == ed_.end){
				//edgeラベルの付け替え
				gr_.edges[i].rec1=ed_.rec1;
				gr_.edges[i].rec2=ed_.rec2;
				gr_.edges[i].label=ed_.label;
				gr_.edges[i].type=ed_.type;
			}
		}
		if(ed_.start==ed_.end){
			gr_.takeawayEdge(gr_,ed_);
			ed_.start.takeawayEdge(ed_);
		}else{
//			gr_.printGraphAll();
			//System.out.println("removeEdge前");
			gr_.removeEdge(gr_,ed_);
			//System.out.println("removeEdge後addNode前");
			//gr_.printGraphAll();
			//gr_.takeawayNode(gr_,ed_.start);
			//gr_.takeawayNode(gr_,ed_.end);
			Node nwnd;
			double x_,y_,z_;
			x_=(ed_.start.x+ed_.end.x)/2+100;
			y_=(ed_.start.y+ed_.end.y)/2+100;
			z_=(ed_.start.z+ed_.end.z)/2+100;
			nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
			lg_.addNode(nwnd);
			nwnd.addHaplotype(ed_.start.hp[0]);
			//gr_.printGraphAllNodeEdge();
			gr_.addNode(gr_,nwnd);
			//System.out.println("addNode後changeNode1前");
			//gr_.printGraphAllNodeEdge();
			
			/*
			 * treeの場合との違いは、付け替えるエッジの中に、
			 * 取り去るエッジの始点・終点を、その始点・終点としているものが
			 * 存在することである
			 * それらを予め別処理しておく
			 * edgeの始点終点の変更と、取り去るエッジの旧始点・終点ノードのエッジ情報の書き換え
			 * 
			 */
			
			for(int i=0;i<gr_.edges.length;i++){
				//System.out.println("haitta");
				Node st=gr_.edges[i].start;
				Node end=gr_.edges[i].end;
				int point=0;
				if(st == ed_.start){
					point++;
				}else if(st == ed_.end){
					point++;
				}
				if(end == ed_.start){
					point++;
				}else if(end == ed_.end){
					point++;
				}
				//System.out.println("point " + point);
				if(point ==2){//新ノードから始まり、新ノードに終わるエッジである
					gr_.edges[i].start=nwnd;
					gr_.edges[i].end=nwnd;
					nwnd.addEdge(edges[i]);
					nwnd.addEdge(edges[i]);
					ed_.start.takeawayEdge(edges[i]);
					ed_.end.takeawayEdge(edges[i]);
				}
				
			}
			
			gr_.changeNode(gr_,nwnd,ed_.start);
			
			//System.out.println("changeNode1後changeNode2前");
			//gr_.printGraphAllNodeEdge();
			
			gr_.changeNode(gr_,nwnd,ed_.end);
			//System.out.println("changeNode2後takeawayNode1前");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.start);	
			//System.out.println("takeawayNode1後takeawayNode2前");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.end);	
			//System.out.println("takeawayNode2後haplotype書き換え前");
			//gr_.printGraphAllNodeEdge();
			
			//ハプロタイプ関係の継承
			
			for(int i=0;i<ed_.start.hp.length;i++){
				//新ノードへの登録
				nwnd.addHaplotype(ed_.start.hp[i]);
//				//ForRetroバージョンの違い
				//ハプロタイプへの新ノードの登録
				ed_.start.hp[i].addArgNode(nwnd);
				//ed_.start.hp[i].addTreeNode(nwnd);
			}
			for(int i=0;i<ed_.end.hp.length;i++){
				nwnd.addHaplotype(ed_.end.hp[i]);
				//ForRetroバージョンの違い
				ed_.end.hp[i].addArgNode(nwnd);
				//ed_.end.hp[i].addTreeNode(nwnd);
			}
			//System.out.println("post removeEdgeAndGlue \n");
			//gr_.printGraphAllNodeEdge();
		}
		
		
		
	}
	public boolean treeCheck(){
		boolean ret;
		ret = true;
		int numnode;
		int numedge;
		if(nodes == null){
			ret = false;
		}else{
			numnode = nodes.length;
			if(numnode>0){
				if(edges == null){
					
				}else{
					numedge=edges.length;
				}
			}
			
		}
		return ret;
	}
	/*
	public boolean connectionCheck(){
		boolean ret;
		ret =true;
		if(nodes == null){
			ret = false;
		}else{
			//edgeを持たないノードがあれば、連結ではない
			for(int i=0;i<nodes.length;i++){
				if(nodes[i].edge == null){
					ret =false;
					break;
				}else if(nodes[i].edge.length == 0){
					ret = false;
					break;
				}
			}
			//nodes[0]から始めて、連結ノードを格納し、連結ノード数が全ノード数に達するかどうかで判断する
			Node conn[];
			conn = new Node[1];
			conn[0]=nodes[0];
			Node nwconn[];
			nwconn = new Node[1];
			nwconn[0] = nodes[0];
			while(conn.length<nodes.length){
				//System.out.println("conn len " + conn.length);
				for(int k=0;k<nwconn.length;k++){
					Node tmpconn[];
					tmpconn = new Node[0];
					for(int i=0;i<nwconn[k].edge.length;i++){
						Node tmpnd;
						if(nwconn[k].edge[i].start == nwconn[k]){
							tmpnd = nwconn[k].edge[i].end;
						}else{
							tmpnd = nwconn[k].edge[i].start;
						}
						int tmpflag=0;
						for(int j=0;j<conn.length;j++){
							if(conn[j] == tmpnd){
								tmpflag=1;
							}
						}
						if(tmpflag == 0){
							Node tmptmpconn[];
							tmptmpconn = new Node[tmpconn.length+1];
							for(int j=0;j<tmpconn.length;j++){
								tmptmpconn[j]=tmpconn[j];
							}
							tmptmpconn[tmpconn.length]=tmpnd;
							tmpconn = new Node[tmptmpconn.length];
							for(int j=0;j<tmptmpconn.length;j++){
								tmpconn[j]=tmptmpconn[j];
							}
						}
					}
					nwconn = new Node[tmpconn.length];
					for(int j=0;j<tmpconn.length;j++){
						nwconn[j]=tmpconn[j];
					}
				}
				
				Node tmptmptmpconn[];
				tmptmptmpconn = new Node[conn.length + nwconn.length];
				for(int k=0;k<conn.length;k++){
					tmptmptmpconn[k]=conn[k];
				}
				for(int k=0;k<nwconn.length;k++){
					tmptmptmpconn[conn.length+k]=nwconn[k];
				}
				conn = new Node[tmptmptmpconn.length];
				for(int k=0;k<tmptmptmpconn.length;k++){
					conn[k]=tmptmptmpconn[k];
				}
				
				//if(nwconn.length == 0){
					//break;
				//}
			}
			if(conn.length == nodes.length){
				
			}else{
				ret = false;
			}
			//System.out.println("conn ");
			for(int i=0;i<conn.length;i++){
				System.out.println(conn[i]);
			}
			
		}
		
		return ret;
	}
	*/
	//Tree
	//Treeとは新たにノードを付け加えるときにあらたにエッジを付け加えることで成長する
	//このような成長の仕方をさせることで、かならず連結であり、サイクルを有さない
	//Treeが１直線と異なる点は、分岐があることである。分岐は、２分岐、３分岐、・・・である
	public void growTree(Node nd_,Edge ed_){
		int ndcheck=0;
		int ndstartcheck=1;
		int ndendcheck=0;
		int edgecheck=0;
		int nodeinedgecheck=0;
		if(ed_.end == nd_){
			
		}else{
			nodeinedgecheck=1;
		}
		for(int i=0;i<nodes.length;i++){
			if(nodes[i] == nd_){//成長させる木に追加するノードがないこと
				ndcheck=1;
				
				break;
			}
			if(nodes[i]==ed_.start){//追加するエッジの始点が木のノードであること
				ndstartcheck=0;
			}
			if(nodes[i]==ed_.end){//追加するエッジの終点が木のノードでないこと
				ndendcheck=1;
				break;
			}
		}

		
		
		if(ndcheck==1){
			System.out.println("node error");
		}else if(ndstartcheck == 1){
			System.out.println("node start error");
		}else if(ndendcheck ==1){
			System.out.println("node end error");
		}else if(nodeinedgecheck==1){
			System.out.println("node in edge error");
		}else{
				
			Node tmpnd[];
			tmpnd = new Node[nodes.length+1];
			for(int i=0;i<nodes.length;i++){
				tmpnd[i]=nodes[i];
			}
			tmpnd[nodes.length]=nd_;
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
			}
			Edge tmped[];
			tmped = new Edge[edges.length+1];
			for(int i=0;i<edges.length;i++){
				tmped[i]=edges[i];
			}
			tmped[edges.length]=ed_;
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
			}
		}
	}
	public void addTree(Graph gr_,Node nd1_, Node nd2_, Edge ed_){//加えるべきグラフとつなぐべきノード２点、そこに発生する新エッジ
		int nd1check=1;
		int nd2check=0;
		int ndstartcheck=0;
		int ndendcheck=0;
		int nooverlapndcheck=0;
		int nodeinedgecheck=0;
		//edgeの始点終点が引数ノードと整合性があるかどうかのチェック
		if(ed_.start == nd1_){
			
		}else{
			nodeinedgecheck=1;
		}
		if(ed_.end == nd2_){
			
		}else{
			nodeinedgecheck=1;
		}
		//元グラフには始点があり、終点がないこと
		//追加グラフには始点がなく、終点があること
		for(int i=0;i<nodes.length;i++){
			if(nodes[i] == nd1_){
				nd1check=0;
				
				break;
			}
			if(nodes[i]==ed_.end){
				ndendcheck=1;
				break;
			}
		}
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == nd2_){
				nd2check=0;
				
				break;
			}
			if(nodes[i]==ed_.start){
				ndstartcheck=1;
				break;
			}
		}
		//元グラフと追加グラフのノードにオーバーラップがないこと
		for(int i=0;i<nodes.length;i++){
			for(int j=0;j<gr_.nodes.length;j++){
				if(nodes[i] == gr_.nodes[j]){
					nooverlapndcheck=1;
					break;
				}
			}
		}
		
		if(nd1check==1){
			System.out.println("node1 error");
		}else if(nd2check==1){
			System.out.println("node2 error");
		}else if(ndstartcheck == 1){
			System.out.println("node start error");
		}else if(ndendcheck ==1){
			System.out.println("node snd error");
		}else if(nodeinedgecheck==1){
			System.out.println("node in edge error");
		}else if(nooverlapndcheck==1){
			System.out.println("node overlap error");
		}else{
				
			Node tmpnd[];
			tmpnd = new Node[nodes.length+gr_.nodes.length];
			for(int i=0;i<nodes.length;i++){
				tmpnd[i]=nodes[i];
			}
			for(int i=0;i<gr_.nodes.length;i++){
				tmpnd[nodes.length+i] = gr_.nodes[i];
			}
			
			nodes = new Node[nodes.length+gr_.nodes.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
			}
			Edge tmped[];
			tmped = new Edge[edges.length+gr_.edges.length];
			for(int i=0;i<edges.length;i++){
				tmped[i]=edges[i];
			}
			for(int i=0;i<gr_.edges.length;i++){
				tmped[edges.length+i]=gr_.edges[i];
			}
			
			edges = new Edge[edges.length+gr_.edges.length + 1];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
			}
			edges[edges.length+gr_.edges.length]=ed_;
		}
	}
	
	public void rmEdge(Edge ed_){
		
		int edgecheck=1;
		for(int i=0;i<edges.length;i++){
			if(edges[i]==ed_){
				edgecheck=0;
				break;
			}
		}
		if(edgecheck == 1){
			//System.out.println("reEdge::edge check error");
		}else{
			//System.out.println("IN rmEdge pre edge list書き換え" + edges.length);
			Edge tmped[];
			tmped = new Edge[edges.length-1];
			int counter=0;
			for(int i=0;i<edges.length;i++){
				if(edges[i]==ed_){
					
				}else{
					tmped[counter]=edges[i];
					counter++;
				}
			}
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
				edges[i].id_gr=i;
			}
			//System.out.println("IN rmEdge post edge list書き換え" + edges.length);
			
			for(int i=0;i<nodes.length;i++){
				//System.out.println("IN rmEdge pre NODE EDGE list書き換え" + nodes[i].edge.length);
				Edge tmp[];
				boolean ari=false;
				for(int j=0;j<nodes[i].edge.length;j++){
					if(nodes[i].edge[j]==ed_){
						ari = true;
						//System.out.println("書き換えあり");
						break;
					}
				}
				if(ari == true){
					tmp = new Edge[nodes[i].edge.length-1];
					int cnt=0;
					for(int j=0;j<nodes[i].edge.length;j++){
						if(nodes[i].edge[j]!=ed_){
							tmp[cnt]=nodes[i].edge[j];
							//System.out.println("edge id " + tmp[cnt].id);
							cnt++;
						}
					}
					nodes[i].edge = new Edge[tmp.length];
					for(int j=0;j<tmp.length;j++){
						nodes[i].edge[j]=tmp[j];
					}
				}
				//System.out.println("IN rmEdge post NODE EDGE list書き換え" + nodes[i].edge.length);
			}
			//System.out.println("edge rm ssssss");
		}		
	}
	public boolean rmEdge_bl(Edge ed_){
		boolean ret=true;
		int edgecheck=1;
		for(int i=0;i<edges.length;i++){
			if(edges[i]==ed_){
				edgecheck=0;
				break;
			}
		}
		if(edgecheck == 1){
			//System.out.println("rmEdge_bl*****edge check error");
			ret = false;
		}else{
			Edge tmped[];
			tmped = new Edge[edges.length-1];
			int counter=0;
			//System.out.println("IN rmEdge_bl pre edge list書き換え" + edges.length);
			for(int i=0;i<edges.length;i++){
				if(edges[i]==ed_){
					
				}else{
					tmped[counter]=edges[i];
					counter++;
				}
			}
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
				edges[i].id_gr=i;
			}
			//System.out.println("IN rmEdge_bl post edge list書き換え" + edges.length);
			for(int i=0;i<nodes.length;i++){
				//System.out.println("IN rmEdge_bl pre NODE-EDGE list書き換え" + nodes[i].edge.length);
				Edge tmp[];
				boolean ari=false;
				for(int j=0;j<nodes[i].edge.length;j++){
					if(nodes[i].edge[j]==ed_){
						ari = true;
						//System.out.println("書き換えあり");
						break;
					}
				}
				
				if(ari == true){
				
					tmp = new Edge[nodes[i].edge.length-1];
					int cnt=0;
					for(int j=0;j<nodes[i].edge.length;j++){
						if(nodes[i].edge[j]==ed_){
								
						}else{
							tmp[cnt]=nodes[i].edge[j];
							cnt++;
						}
					}
					nodes[i].edge = new Edge[tmp.length];
					for(int j=0;j<tmp.length;j++){
						nodes[i].edge[j]=tmp[j];
					}

					
				}
				//System.out.println("IN rmEdge_bl post NODE-EDGE list書き換え" + nodes[i].edge.length);
			}
			//System.out.println("edge rm ssssss");
			
			
		}		
		return ret;
	}
	public void rmNode(Node nd_){
		
		int nodecheck=1;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i]==nd_){
				nodecheck=0;
				break;
			}
		}
		if(nodecheck == 1){
			//System.out.println("rmNode+++++++++++++node check error");
		}else{
			Node tmpnd[];
			tmpnd = new Node[nodes.length-1];
			int counter=0;
			for(int i=0;i<nodes.length;i++){
				if(nodes[i]==nd_){
					
				}else{
					tmpnd[counter]=nodes[i];
					counter++;
				}
			}
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
				nodes[i].id_gr=i;
			}
			//System.out.println("node rm sssss");
		}		
	}
	public boolean rmNode_bl(Node nd_){
		boolean ret=true;
		int nodecheck=1;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i]==nd_){
				nodecheck=0;
				break;
			}
		}
		if(nodecheck == 1){
			//System.out.println("rmNode_blLLLLLLLLLLLLLLnode check error");
			ret = false;
		}else{
			Node tmpnd[];
			tmpnd = new Node[nodes.length-1];
			int counter=0;
			for(int i=0;i<nodes.length;i++){
				if(nodes[i]==nd_){
					
				}else{
					tmpnd[counter]=nodes[i];
					counter++;
				}
			}
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
				nodes[i].id_gr=i;
			}
			//System.out.println("node rm sssss");
		}
		return ret;
	}

	public void copy(Log lg_,Graph nwgr, Graph oldgr){
		/*
		 * コピーのエッジをそのまま登録
		 * ノードのコピーを作りそのまま登録
		 * エッジに登録されているノード、ノードに登録されているエッジを書き換える
		 */
		Edge tmped;
		Node tmpnd;
		//nwgr.printGraphAllNodeEdge();
		nwgr.edges = new Edge[oldgr.edges.length];
		for(int i=0;i<oldgr.edges.length;i++){
			nwgr.edges[i]=oldgr.edges[i];
		}
		//System.out.println("A old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<oldgr.edges.length;i++){
			
			tmpnd = new Node(-1);
			
			tmped = new Edge(lg_.ed.length,tmpnd,tmpnd);
			lg_.addEdge(tmped);
			tmped.copy(oldgr.edges[i]);
			nwgr.edges[i] = tmped;
			nwgr.edges[i].gr=nwgr;
		}
		//System.out.println("B old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		nwgr.nodes = new Node[oldgr.nodes.length];
		for(int i=0;i<oldgr.nodes.length;i++){
			nwgr.nodes[i]=oldgr.nodes[i];
		}
		for(int i=0;i<oldgr.nodes.length;i++){
			tmpnd = new Node(lg_.nd.length);
			lg_.addNode(tmpnd);
			tmpnd.copy(oldgr.nodes[i]);
			nwgr.nodes[i]=tmpnd;
			nwgr.nodes[i].gr=nwgr;
		}
		//System.out.println("C old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<nwgr.nodes.length;i++){
			for(int j=0;j<nwgr.nodes[i].edge.length;j++){
				for(int k=0;k<oldgr.edges.length;k++){
					if(nwgr.nodes[i].edge[j] == oldgr.edges[k]){
						nwgr.nodes[i].edge[j]=nwgr.edges[k];
					}
				}
				
			}
		}
		//System.out.println("D old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<nwgr.edges.length;i++){
			for(int k=0;k<oldgr.nodes.length;k++){
				if(nwgr.edges[i].start == oldgr.nodes[k]){
					nwgr.edges[i].start=nwgr.nodes[k];
				}
				if(nwgr.edges[i].end == oldgr.nodes[k]){
					nwgr.edges[i].end=nwgr.nodes[k];
				}				
			}
		}
		//System.out.println("E old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		
		nwgr.direction = oldgr.direction;//0 undirected,1 directed
		nwgr.type = oldgr.type;//0 ARG, 1 Forest, 2 Tree
		nwgr.tr = oldgr.tr;//tree:1,non forests:2,それ以外:3
		
		nwgr.name=oldgr.name;
		nwgr.label=oldgr.label;
		nwgr.or_x =oldgr.or_x;
		nwgr.or_y = oldgr.or_y;
		nwgr.or_z = oldgr.or_z;//グラフの原点　ノードの表示はこの原点とノードの持つ座標とで決める
		nwgr.len_x = oldgr.len_x;
		nwgr.len_y = oldgr.len_y;
		nwgr.len_z = oldgr.len_z;//無限３時限空間におけるグラフの存在範囲
		//Hashtable hs;
		
	}
	public void recSeparation_less(Log lg_,Graph gr_,int recpoint){
		
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i].label>recpoint){
					looper =0;
				}
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr_.nodes.length];
				tmpedges = new Edge[gr_.edges.length];
				
				for(int i=0;i<gr_.nodes.length;i++){
					tmpnodes[i]=gr_.nodes[i];
				}
				for(int i=0;i<gr_.edges.length;i++){
					tmpedges[i]=gr_.edges[i];
				}
				for(int i=0;i<tmpedges.length;i++){
					
					if(tmpedges[i].label >recpoint){
						gr_.removeEdgeAndGlue(lg_,gr_,tmpedges[i]);
						break;
					}else{
						
					}
				}
			}
			
		}
		
	}
	public void recSeparation_more(Log lg_,Graph gr_,int recpoint){
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i].label<=recpoint){
					if(gr_.edges[i].label>=0){
						looper =0;
					}
					
				}
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr_.nodes.length];
				tmpedges = new Edge[gr_.edges.length];
				
				for(int i=0;i<gr_.nodes.length;i++){
					tmpnodes[i]=gr_.nodes[i];
				}
				for(int i=0;i<gr_.edges.length;i++){
					tmpedges[i]=gr_.edges[i];
				}
				for(int i=0;i<tmpedges.length;i++){
					if(tmpedges[i].label <=recpoint){
						if(tmpedges[i].label >=0){
							gr_.removeEdgeAndGlue(lg_,gr_,tmpedges[i]);
							break;
						}
					}else{
						
					}
				}
			}
		}
		
	}
	public void sortNode(){
		for(int i=0;i<nodes.length;i++){
			nodes[i].id_gr=nodes[i].id;
		}
	}
	public void sortEdge(){
		for(int i=0;i<edges.length;i++){
			edges[i].id_gr=edges[i].id;
		}
	}

	public void tree2forest(){
		if(type ==1){
			
			for(int i=0;i<nodes.length;i++){
				nodes[i].hp[nodes[i].hp.length-1].addForestNode(nodes[i]);
				
			}
		}else{
			//System.out.println("Not Forest");
		}
	}
	public void cleanNode(){
		/*
		 * ノードに登録されたエッジIDのうち、存在しないものを除去
		 * グラフに登録されたノード数、エッジ数は不変
		 */
		for(int i=0;i<nodes.length;i++){
			Edge tmpe[];
			int cnte=0;
			int check=0;
			for(int j=0;j<nodes[i].edge.length;j++){
				for(int k=0;k<edges.length;k++){
					if(nodes[i].edge[j]==edges[k]){
						cnte++;
					}
				}
			}
			if(cnte==nodes[i].edge.length){
				
			}else{
				System.out.println("CCCCCCCCCCCCCCCCCCCClean Node " );
			}
			tmpe = new Edge[cnte];
			cnte=0;
			for(int j=0;j<nodes[i].edge.length;j++){
				for(int k=0;k<edges.length;k++){
					if(nodes[i].edge[j]==edges[k]){
						tmpe[cnte]=edges[k];
						cnte++;
					}
				}
			}
			nodes[i].edge = new Edge[tmpe.length];
			for(int j=0;j<tmpe.length;j++){
				nodes[i].edge[j]=tmpe[j];
			}
		}
	}
	public void cleanEdge(){
		/*
		 * グラフのエッジのうち、始点終点に対応するノードをもたないものを除去
		 * グラフのエッジ数が減少する
		 */
		Edge tmpe[];
		int cnte=0;
		for(int i=0;i<edges.length;i++){
			int startflag=0;
			int endflag=0;
			for(int j=0;j<nodes.length;j++){
				if(edges[i].start==nodes[j]){
					startflag=1;
				}
				if(edges[i].end==nodes[j]){
					endflag=1;
				}
				
			}
			if(startflag*endflag == 1){
				cnte++;
			}
		}
		tmpe = new Edge[cnte];
		cnte=0;
		for(int j=0;j<edges.length;j++){
			int startflag=0;
			int endflag=0;
			for(int k=0;k<nodes.length;k++){
				if(edges[j].start==nodes[k]){
					startflag=1;
				}
				if(edges[j].end==nodes[k]){
					endflag=1;
				}
				
			}
			if(startflag*endflag == 1){
				tmpe[cnte]=edges[j];
				cnte++;
			}else{
				System.out.println("CCCCCCCCCCCCCCCCCCCClean Edge");
			}
		}
		edges = new Edge[tmpe.length];
		for(int i=0;i<tmpe.length;i++){
			edges[i]=tmpe[i];
		}
	}
	/*
	 * Retrograde関係
	 */


	public Graph[] addToGraphArr(Graph grarr[],Graph gr){
		Graph tmp[];
		tmp = new Graph[grarr.length+1];
		for(int i=0;i<grarr.length;i++){
			tmp[i] = grarr[i];
		}
		tmp[grarr.length]=gr;
		System.out.println("pre " + grarr.length);
		grarr = new Graph[tmp.length];
		return tmp;
	}
	public Graph[] retroUp(Log lg,Graph gr1, Graph gr2){
		
		System.out.println("####################retroUP");
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		
		int preBackMut=tmparr[0].nodes.length;
		int preBackNodenum = tmparr[1].nodes.length;
		int preBackEdgenum = tmparr[1].edges.length;
		int preBackNodenum0 = tmparr[0].nodes.length;
		int preRetroMR = lg.retroMR.length;
		System.out.println("BackMut プレ　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
		tmparr = tmparr[0].backMut(lg,tmparr[0],tmparr[1]);//mutationを減らす
		int postRetroMR = lg.retroMR.length;
		preBackNodenum = tmparr[1].nodes.length;
		preBackEdgenum = tmparr[1].edges.length;
		preBackNodenum0 = tmparr[0].nodes.length;
		System.out.println("BackMut ポスト　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
		int postBackMut=tmparr[0].nodes.length;
		
		//System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCcc");
		//boolean connected2 = tmparr[1].connectedGraph();
		//System.out.println("connection hanntei " + connected2);
		//System.out.println("connectionGraph " + tmparr[1].connectedGraph());
		boolean judge = true;//BackMutが成功したらBackRecすることなく、登録情報を更新して、サイド、BackMutできるかどうかを試す
		//if(preBackMut-postBackMut==0){
		if(preRetroMR-postRetroMR==0){
			judge=false;//BackMutでlgのretroMRに新要素が付け加わらなければ
			System.out.println("BackMut失敗");
			if(tmparr[0].nodes.length==2){
				System.out.println("ここが変!");
				System.out.println("tmparr[0].nodes[0]" + tmparr[0].nodes[0].id);
				System.out.println("tmparr[0].nodes[1]" + tmparr[0].nodes[1].id);
				tmparr[0].nodes[0].hp[0].printHaplotypeElem();
				tmparr[0].nodes[1].hp[0].printHaplotypeElem();
			}
		}else{
			System.out.println("BackMut成功");
			//lg.addRetroMR(0);//retro処理でBackMutしたので0を登録
		}
		/*
		if(postBackMut==1){
			judge=false;
		}
		
		*/
		if(judge==false){//BackMutができる限りは連結チェック・BackRecには入らない
//			グラフ全体が連結かどうかをチェック
			boolean connected = tmparr[1].connectedGraph();
			//connectedしていてもrecでの結合は無視
			connected = false;
			if(connected){
			}else if(postBackMut <3){
				//２ノードにまで減ったら、recombinationの考慮は不要、かつ、親子３ノードを選べない
			}else{
//				judge = tmparr[1].judgeARGComplete();
				//System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
				preBackMut=tmparr[0].nodes.length;
				preBackNodenum = tmparr[1].nodes.length;
				preBackEdgenum = tmparr[1].edges.length;
				preBackNodenum0 = tmparr[0].nodes.length;
				System.out.println("BackRec プレ　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				preRetroMR = lg.retroMR.length;
				//tmparr = tmparr[0].backRec(lg,tmparr[0],tmparr[1]);
				tmparr = tmparr[0].backRec2(lg,tmparr[0],tmparr[1]);
				postRetroMR = lg.retroMR.length;
				preBackNodenum = tmparr[1].nodes.length;
				preBackEdgenum = tmparr[1].edges.length;
				preBackNodenum0 = tmparr[0].nodes.length;
				System.out.println("BackRec ポスト　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				postBackMut=tmparr[0].nodes.length;
				//judge = true;
				//tmparr = tmp1.backRec(lg,tmparr[0],tmparr[1]);
				//if(preBackMut-postBackMut==0){
				if(preRetroMR-postRetroMR==0){
					//retorMR処理不成立
					//lg.addRetroMR(3);//retro処理でBackRecが不成立なので2を登録	
				}else{
					//retroMR処理成立
					//lg.addRetroMR(2);//retro処理でBackRecしたので1を登録
				}
			}
		}
		
		
		
		return tmparr;
	}
	public Graph[] backMut(Log lg,Graph gr1, Graph gr2){
		//System.out.println("$$$$$$$$$$$$$$$$$$$backMut");
		/*
		for(int i=0;i<gr2.nodes.length;i++){
			System.out.println("node's i & edge len " + i + " " + gr2.nodes[i].edge.length);
		}
		*/
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		/*
		
		Node tmpnodesgr1[];
		tmpnodesgr1 = new Node[gr1.nodes.length];
		for(int i=0;i<tmpnodesgr1.length;i++){
			tmpnodesgr1[i]=gr1.nodes[i];
		}
		*/
		int hpmat[][];
		int numnd = tmparr[0].nodes.length;
		//int numsnp = tmp1.nodes[0].hp[0].hp.length;
		//System.out.println("numsnp " + numsnp);
		int numsnp = lg.numsnp;
		hpmat = new int[numnd][numsnp];
		for(int i=0;i<numnd;i++){
			for(int j=0;j<numsnp;j++){
				hpmat[i][j]=0;
				//hpmat[i][j]=tmp1.nodes[i].hp[0].hp[j];
			}
		}
		for(int i=0;i<numnd;i++){
			for(int j=0;j<tmparr[0].nodes[i].hp[0].hp.length;j++){
				hpmat[i][tmparr[0].nodes[i].hp[0].hp[j]]=1;
			}
		}
		
		boolean looper=true;
		if(tmparr[0].nodes.length==1){
			looper=false;
		}
		//int onlyonemut=0;
		for(int j=0;j<numsnp;j++){
			/*
			//１度に１SNP mutationの処理だけして戻す
			if(j==1){
				System.out.println("break j=" + j);
				break;
			}
			System.out.println("not break j=" + j);
			onlyonemut=1;
			*/
			if(looper==false){
				break;
			}else{
				int tmpcount=0;
				int maf=0;
				int Maf=1;
				for(int i=0;i<numnd;i++){
					if(hpmat[i][j]==0){
						tmpcount++;
					}
				}
				//System.out.println("tmpcount.numnd " + tmpcount + "/" + numnd);
				if(tmpcount==numnd-1){
					tmpcount=1;
					if(numnd==2){
							
					}else{
						maf=1;//numndが２より大で、tmpcountがnumnd-1な場合に、minor allele freqは１、それ以外は０
						Maf=0;
					}
						
				}
				//System.out.println("tmpcount " + tmpcount);
				if(tmpcount==1){
						//このSNPは単独SNP
						//mafが０か１かの情報は保持している
						//この単独アレルを持つノード・ハプロタイプを見つけること
						//そのアレルをmajorに転じたハプロタイプが親であり、その親が、現在のハプロタイプセットに存在していなければ
						//追加する。存在していれば、そのノードの子とする
					int findchild=0;
					int child=0;
					int parenthp[];
					parenthp=new int[numsnp];
					for(int i=0;i<numnd;i++){
						if(hpmat[i][j]==maf){
							child=i;
							findchild=1;
							for(int k=0;k<numsnp;k++){
								if(k==j){
									parenthp[k]=Maf;
								}else{
									parenthp[k]=hpmat[i][k];
								}
									
							}
							looper = false;
							break;
							
						}
					}
					if(findchild==0){
						//System.out.println("no child found");
						looper = false;
						
					}else{
//						childのgr2該当ノードを選ぶ
						Node gr2child;
						//System.out.println("child " + child);
						//System.out.println("gr1.nodes[child].hp.length " + gr1.nodes[child].hp.length);
						//System.out.println("gr1.nodes[child].hp[gr1.nodes[child].hp.length-1].forestnd.length " + gr1.nodes[child].hp[gr1.nodes[child].hp.length-1].forestnd.length);
						gr2child=tmparr[0].nodes[child].hp[tmparr[0].nodes[child].hp.length-1].argnd;
						Haplotype childHp=tmparr[0].nodes[child].hp[tmparr[0].nodes[child].hp.length-1];
						//gr2child=tmpnodesgr1[child].hp[tmpnodesgr1[child].hp.length-1].forestnd[tmpnodesgr1[child].hp[tmpnodesgr1[child].hp.length-1].forestnd.length-1];
							
						int oya=-9;
						Node gr2oya;
						for(int i=0;i<numnd;i++){
							int jd=0;
							for(int k=0;k<numsnp;k++){
								if(hpmat[i][k] == parenthp[k]){
									
								}else{
									jd =1;
								}
							}
							if(jd==0){//これがparent
								oya=i;
								break;
								/*
								 * このgr2の方該当ノードを探す
								 */
									
								//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
							}
						}
							
						if(oya==-9){//parent なし
								/*
								 * gr2にノードを加えて、それにノードjをつなぐ
								 * gr1にもノードを加える
								 * gr1から、ノードjをのぞく
								 */
							//System.out.println("Parentなし");
							Haplotype tmphp;
							//{0,1}表記のhaplotype表記から、{1}のみ表記へ
							int snps[]={};
							for(int k=0;k<parenthp.length;k++){
								if(parenthp[k]==1){
									int tmp[];
									tmp = new int[snps.length+1];
									for(int l=0;l<snps.length;l++){
										tmp[l]=snps[l];
									}
									tmp[snps.length]=k;
									snps = new int[tmp.length];
									for(int l=0;l<tmp.length;l++){
										snps[l]=tmp[l];
									}
								}
							}
							//System.out.println("snps len " + snps.length);
							//tmphp = new Haplotype(lg.hp.length,parenthp);
							tmphp = new Haplotype(lg.hp.length,snps);
							lg.addHaplotype(tmphp);
							int st[]={0};
							int end[]={lg.numsnp-1};
							tmphp.addStEnd(st,end);
							Node tmpnd1,tmpnd2;
							tmpnd1=new Node(lg.nd.length,tmparr[0],tmparr[0].nodes.length);
							lg.addNode(tmpnd1);
							tmpnd1.addHaplotype(tmphp);
							tmphp.addForestNode(tmpnd1);
							//gr1のノード数は変わらない
							tmparr[0].addNode(tmparr[0],tmpnd1);
							tmparr[0].removeNode(tmparr[0],tmparr[0].nodes[child]);
								
							tmpnd2=new Node(lg.nd.length,tmparr[1],tmparr[1].nodes.length);
							//tmpnd2.copy(tmpnd1);
							lg.addNode(tmpnd2);
							tmpnd2.addHaplotype(tmphp);
							tmphp.addArgNode(tmpnd2);
//							//gr2のノード数は1個増える
							tmparr[1].addNode(tmparr[1],tmpnd2);
								
							Edge tmped;
							tmped = new Edge(lg.ed.length,tmparr[1],tmpnd2,gr2child,1);//jはmutation
							tmped.label=j;
							//System.out.println("parent なし　pre addEdge " + tmparr[1].nodes.length);
							tmparr[1].addEdge(tmparr[1],tmped);//両端点のエッジ情報はここで更新
							//System.out.println("parent なし　post addEdge " + tmparr[1].nodes.length);
							lg.addEdge(tmped);
							//tmpnd2.addEdge(tmped);
							//gr2child.addEdge(tmped);
							lg.addRetroMR(1);//retro処理で親なしBackMutしたので1を登録
							lg.addRetroHpChild(childHp);
							lg.addRetroHpParent1(tmphp);
							lg.addRetroHpParent2(null);
							
							return tmparr;//parentなしのときは、複数SNPの違いがあっても１mutationについて親を作ったら終わりにする
							//break;
							
						}else{//parentあり
								/*
								 * tmparr[1]のノードjdとノードjをつなぐ
								 * tmparr[0]から、ノードjをのぞく
								 */
							//gr2oya=tmparr[0].nodes[oya].hp[0].forestnd[tmparr[0].nodes[oya].hp[0].forestnd.length-1];
							gr2oya=tmparr[0].nodes[oya].hp[0].argnd;
							Haplotype parent1Hp = tmparr[0].nodes[oya].hp[0];
							Edge tmped;
							tmped = new Edge(lg.ed.length,tmparr[1],gr2oya,gr2child,j);//jはmutation
							tmped.label=j;
							//System.out.println("parent あり　pre addEdge " + tmparr[1].nodes.length);
							tmparr[1].addEdge(tmparr[1],tmped);//両端点のエッジ情報はここで更新
							//System.out.println("parent あり　post addEdge " + tmparr[1].nodes.length);
							lg.addEdge(tmped);
							//gr2oya.addEdge(tmped);
							//gr2child.addEdge(tmped);
							tmparr[0].removeNode(tmparr[0],tmparr[0].nodes[child]);
							lg.addRetroMR(0);//retro処理でBackMutしたので0を登録
							lg.addRetroHpChild(childHp);
							lg.addRetroHpParent1(parent1Hp);
							lg.addRetroHpParent2(null);
								
						}
					}
					
				}
			}
				
		}

		return tmparr;
	}
	//この親子関係でrecombinationが可能かどうかを調べる
	//親子関係を満たすために必要な交差箇所数に制限はない
	public Graph[] backRec(Log lg,Graph gr1,Graph gr2){
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!backRec");
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		;
		int hpmat[][];
		int numnd = tmparr[0].nodes.length;
		//int numsnp = tmp1.nodes[0].hp[0].hp.length;
		//System.out.println("numsnp " + numsnp);
		int numsnp = lg.numsnp;
		hpmat = new int[numnd][numsnp];
		for(int i=0;i<numnd;i++){
			for(int j=0;j<numsnp;j++){
				hpmat[i][j]=0;
				//hpmat[i][j]=tmp1.nodes[i].hp[0].hp[j];
			}
		}
		for(int i=0;i<numnd;i++){
			for(int j=0;j<tmparr[0].nodes[i].hp[0].hp.length;j++){
				hpmat[i][tmparr[0].nodes[i].hp[0].hp[j]]=1;
			}
		}
		//recombinantをランダムに選ぶ
		int judge=0;
		int rand1,rand2,rand3;
		rand1=rand2=rand3=0;
		while(judge==0){//oya1,2,koの３者が異なるまで乱数を発生させる
			rand1 = (int)(Math.random()*tmparr[0].nodes.length);
			rand2 = (int)(Math.random()*tmparr[0].nodes.length);
			rand3 = (int)(Math.random()*tmparr[0].nodes.length);
			int dif1,dif2,dif3;
			dif1=rand1-rand2;
			dif2=rand2-rand3;
			dif3=rand3-rand1;
			judge=dif1*dif2*dif3;
		}
		
		Node rec = tmparr[0].nodes[rand1];
		Node oya1 = tmparr[0].nodes[rand2];
		Node oya2 = tmparr[0].nodes[rand3];
		//この親子関係でrecombinationが可能かどうかを調べる
		//親子関係を満たすために必要な交差箇所数に制限はない
		int judgerec=0;
		for(int i=0;i<lg.numsnp;i++){
			int judgeallele=1;
			/*
			System.out.println("i " + i);
			System.out.println("rec " + hpmat[rand1][i]);
			System.out.println("oya1 " + hpmat[rand2][i]);
			System.out.println("oya2 " + hpmat[rand3][i]);
			*/
			
			if(hpmat[rand1][i]==hpmat[rand2][i]){
			//if(rec.hp[0].hp[i]==oya1.hp[0].hp[i]){
				judgeallele=0;
			}
			if(hpmat[rand1][i]==hpmat[rand3][i]){
			//if(rec.hp[0].hp[i]==oya2.hp[0].hp[i]){
				judgeallele=0;
			}
			if(judgeallele==1){
				judgerec=1;
				break;
			}
		}
		if(judgerec==0){
			//recを消せる
			/*
			 * tmparr[1]のノード rec とノードoya1, oya2をつなぐ
			 * tmparr[0]から、ノードrecをのぞく
			 */
			System.out.println("backRec 成功");
			//System.out.println("rand123 " + rand1 + " " + rand2 + " " + rand3);
			int preBackNodenum = tmparr[1].nodes.length;
			int preBackEdgenum = tmparr[1].edges.length;
			int preBackNodenum0 = tmparr[0].nodes.length;
			//System.out.println("BackRec内　プレ　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
			//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
			//rec,oya1,oya2はgr1のもの、gr2の対応ノードを選ぶ
			Node rec_gr2,oya1_gr2,oya2_gr2;
			/*
			rec_gr2 = rec.hp[rec.hp.length-1].forestnd[rec.hp[rec.hp.length-1].forestnd.length-1];
			oya1_gr2 = oya1.hp[oya1.hp.length-1].forestnd[oya1.hp[oya1.hp.length-1].forestnd.length-1];
			oya2_gr2 = oya2.hp[oya2.hp.length-1].forestnd[oya2.hp[oya2.hp.length-1].forestnd.length-1];
			*/
			rec_gr2 = rec.hp[0].argnd;
			oya1_gr2 = oya1.hp[0].argnd;
			oya2_gr2 = oya2.hp[0].argnd;
			Edge tmped;
			tmped = new Edge(lg.ed.length,tmparr[1],oya1_gr2,rec_gr2,-9);
			tmparr[1].addEdge(tmparr[1],tmped);
			lg.addEdge(tmped);
			//rec_gr2.addEdge(tmped);
			//oya1_gr2.addEdge(tmped);
			tmped = new Edge(lg.ed.length,tmparr[1],oya2_gr2,rec_gr2,-9);
			tmp2.addEdge(tmparr[1],tmped);
			lg.addEdge(tmped);
			//rec_gr2.addEdge(tmped);
			//oya2_gr2.addEdge(tmped);
			
			tmparr[0].removeNode(tmparr[0],rec);
			preBackNodenum = tmparr[1].nodes.length;
			preBackEdgenum = tmparr[1].edges.length;
			preBackNodenum0 = tmparr[0].nodes.length;
			//System.out.println("BackRec内　ポスト　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
			lg.addRetroMR(2);//retroMR成功
			lg.addRetroHpChild(rec.hp[0]);
			lg.addRetroHpParent1(oya1.hp[0]);
			lg.addRetroHpParent2(oya2.hp[0]);
		}else{
			System.out.println("backRec 失敗");
			//lg.addRetroMR(3);//retroMR成功
		}
		return tmparr;
	}
	
	/*
	 * ハプロタイプ集合から１ハプロタイプをランダムに選択し、
	 * その１ハプロタイプを除いた、残りのハプロタイプの集合(元のハプロタイプ集合の部分集合)、
	 * および、全長について、処理Ｘをかける
	 * 処理Ｘは、再帰的な処理であり、ハプロタイプ集合とその１つのハプロタイプと処理対象線分(部分線分の集合)
	 * を引数として受け取り
	 * その線分を説明する線分がハプロタイプ集合中にあるかどうかを判断し
	 * あれば、それを親とし、しかるべきグラフ処理をする一方、
	 * なければ、ハプロタイプを線分に分解し、その線分集合に対し、再度
	 * ハプロタイプとハプロタイプ集合について処理Ｘを施す
	 * 今、はじめに与えられたハプロタイプについて、すべての多型サイトのアレルについて、
	 * かならず自身以外からなるハプロタイプ集合の中に同一アレルを持つハプロタイプが存在しているところを
	 * 初期入力値とすれば、再帰的処理を最多で多型サイトの数だけ行うことで必ず解が求められる
	 */
	public Graph[] backRec2(Log lg,Graph gr1,Graph gr2){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!backRec2");

		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		
		//randomly nominate recombinant
		int rand1;
		rand1 = (int)(Math.random()*tmparr[0].nodes.length);
		
		//recombinantを除いたノードのリストを作る(haplotypeのリストと同じだが、
		//処理を要するハプロタイプはgr1のノードリストとして管理しているので、ノードで処理する
		
		Node rest[];
		rest = new Node[tmparr[0].nodes.length-1];
		int cnt=0;
		for(int i=0;i<rest.length+1;i++){
			if(i==rand1){
			}else{
				rest[cnt] = tmparr[0].nodes[i];
				cnt++;
			}
			
		}
		//処理範囲のリスト
		int st_seg[] = {0};
		int end_seg[] = {lg.numsnp-1};
		
		//int maxnumiter=5;
		
		int numiter=0;

		//再帰的処理
		//有効組換え位置を番号指定
		//有効組換え位置の乱順列を作って渡す
		//深さにより、どこに組換えを起こすかを指定する
		int allsnps[]={};
		int toaddsnps[]={};
		allsnps = new int[rest[0].hp[0].hp.length];
		for(int i=0;i<rest[0].hp[0].hp.length;i++){
			allsnps[i]=rest[0].hp[0].hp[i];
		}
		//allsnps=rest[0].hp[0].hp;
		for(int i=1;i<rest.length;i++){
			toaddsnps=MiscUtil.rmIdentical(rest[i].hp[0].hp,allsnps);
			allsnps=MiscUtil.addintarray(allsnps,toaddsnps);
		}
		String chRbp="snps 比較";
		for(int i=0;i<tmparr[0].nodes[rand1].hp[0].hp.length;i++){
			chRbp+=tmparr[0].nodes[rand1].hp[0].hp[i] + " ";
		}
		chRbp+="\nallsnps";
		for(int i=0;i<allsnps.length;i++){
			chRbp+=allsnps[i] + " ";
		}
		//System.out.println(chRbp);
		//MiscUtil.quick_sort_int(allsnps);
		int maxdepth=allsnps.length-1;//組換え点は、多型間に発生する
		//int maxdepth=allsnps.length;
		for(int i=0;i<allsnps.length;i++){
			System.out.println("i valus" + i + " " + allsnps[i]);
		}
		System.out.println("allsnps len " + allsnps.length);
		MiscUtil2 m;
		m = new MiscUtil2();
		m.quickSort(allsnps,0,allsnps.length-1);
		int snpinterval[]=new int[allsnps.length-1];
		//allsnpsのうち最大のものを除く
		for(int i=0;i<allsnps.length-1;i++){
			snpinterval[i]=allsnps[i];
		}
		MiscUtil.shuffle(snpinterval);
		//numiterは組換え歴の深さの指標なので、再帰関数backRecNoParentの実施にあたっては、「深さ」をカウントしていく
		//backRecNoParent(lg,tmparr[0],tmparr[1],tmparr[0].nodes[rand1],rest,st_seg,end_seg,lg.backrecdepth,numiter);
		int rec1=9;int rec2=8;//初回のrec位置はなんでもよい
		backRecNoParent(lg,tmparr[0],tmparr[1],tmparr[0].nodes[rand1].hp[0].argnd,rest,st_seg,end_seg,maxdepth,numiter,snpinterval,rec1,rec2,allsnps);
		tmparr[0].removeNode(tmparr[0],nodes[rand1]);
		
		return tmparr;
	}
	
	/*
	 * 
	 */
	public void backRecNoParent(Log lg, Graph gr1,Graph gr2,Node child,Node[] rest,
			int[] st_seg,int[] end_seg,int maxnumiter, int numiter,int[] allsnps,int rec1,int rec2,int[] allsnps_or){
		System.out.println("maxnumiter " + maxnumiter + " depth " + numiter);
		System.out.println("rec1 rec2 " + rec1 + " " + rec2);
		if(numiter>=maxnumiter+1){
			//再帰処理回数に上限を与え、それより余計には行わない
		}else{
			for(int i=0;i<st_seg.length;i++){
				System.out.println("st end " + st_seg[i] + " " + end_seg[i]);
			}
			int depth;
			System.out.println("pre depth " + numiter);
			depth = numiter;
			System.out.println("post depth " + depth);
			int numnd=rest.length;
			int kohp[] = child.hp[0].hp;
			int okParent[];
			okParent = new int[0];
			String childhp="kohp ";
			for(int i=0;i<child.hp[0].hp.length;i++){
				childhp+=child.hp[0].hp[i] + " ";
			}
			System.out.println(childhp);
			for(int i=0;i<numnd;i++){
				//該当セグメントの範囲においては、親ハプロタイプと子ハプロタイプとでアレルが完全に一致する
				//それは、今、oyahp[],kohp[]としたときに、
				/*
				 * int[]からint[]にある要素を取り除く
				 * public static int[] rmIdentical(int[] ar1, int[] ar2)
				 * にてoyahp[]-kohp[]にも
				 * kohp[]-oyahp[]にも要素がないことが必要十分である
				*/ 
				int oyahp[] = rest[i].hp[0].hp;
				String parenthp="oyahp ";
				for(int j=0;j<rest[i].hp[0].hp.length;j++){
					parenthp+=rest[i].hp[0].hp[j] + " ";
				}
				System.out.println(parenthp);
				int judge=0;
				String oyako="oya ";
				for(int j=0;j<oyahp.length;j++){
					oyako +=oyahp[j] + " ";
				}
				oyako+=" ko ";
				for(int j=0;j<kohp.length;j++){
					oyako +=kohp[j] + " ";
				}
				int oya_ko[] = MiscUtil.rmIdentical(oyahp,kohp);
				oyako+=" oya_ko ";
				for(int j=0;j<oya_ko.length;j++){
					oyako +=oya_ko[j] + " ";
				}
				int ko_oya[] = MiscUtil.rmIdentical(kohp,oyahp);
				oyako+=" ko_oya ";
				for(int j=0;j<ko_oya.length;j++){
						oyako +=ko_oya[j] + " ";
				}
				//System.out.println(oyako);
				
				for(int j=0;j<oya_ko.length;j++){
					for(int k=0;k<st_seg.length;k++){
						if(oya_ko[j]>=st_seg[k]){
							if(oya_ko[j]<=end_seg[k]){
								judge=1;
								break;
							}
						}
					}
					if(judge==1){
						break;
					}
				}
				if(judge==1){
					//break;
				}else{
					for(int j=0;j<ko_oya.length;j++){
						for(int k=0;k<st_seg.length;k++){
							if(ko_oya[j]>=st_seg[k]){
								if(ko_oya[j]<=end_seg[k]){
									judge=1;
									break;
								}
							}
						}
						if(judge==1){
							break;
						}
					}
				}
				if(judge==1){
					System.out.println("NG");
				}
				if(judge==0){
					System.out.println("OK");
					int tmp[];
					tmp = new int[okParent.length+1];
					for(int j=0;i<okParent.length;j++){
						tmp[j]=okParent[j];
					}
					tmp[okParent.length]=i;
					okParent = new int[tmp.length];
					for(int j=0;j<tmp.length;j++){
						okParent[j]=tmp[j];
						
					}
				}
				
			}
			if(okParent.length>0){
				/*
				 * tmparr[1]のoyaノードとkoノードとの間にエッジを結ぶ
				 * tmparr[0]から、koノードを削除
				 */
				//親候補からランダムに親を選ぶ
				int randoya = (int)(Math.random()*okParent.length);
				Node oya = rest[okParent[randoya]];
				
				System.out.println("backRec 片親成功");
				//System.out.println("rand123 " + rand1 + " " + rand2 + " " + rand3);
				int preBackNodenum = gr2.nodes.length;
				int preBackEdgenum = gr2.edges.length;
				int preBackNodenum0 = gr1.nodes.length;
				//System.out.println("BackRec内　プレ　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
				//rec,oya1,oya2はgr1のもの、gr2の対応ノードを選ぶ
				Node rec_gr2,oya1_gr2;
				
				
				rec_gr2 = child.hp[0].argnd;
				oya1_gr2 = oya.hp[0].argnd;
				
				Edge tmped;
				tmped = new Edge(lg.ed.length,gr2,oya1_gr2,rec_gr2,-9);
				System.out.println("tmped.rec1 rec2 " + rec1 + " " + rec2);
				tmped.rec1=rec1;
				tmped.rec2=rec2;
				gr2.addEdge(gr2,tmped);
				lg.addEdge(tmped);
				//rec_gr2.addEdge(tmped);
				//oya1_gr2.addEdge(tmped);
				//tmped = new Edge(lg.ed.length,tmparr[1],oya2_gr2,rec_gr2,-9);
				//tmp2.addEdge(tmparr[1],tmped);
				//lg.addEdge(tmped);
				//rec_gr2.addEdge(tmped);
				//oya2_gr2.addEdge(tmped);
				
				//gr2において仮の子供は親そのものであり、親とつないだ後に除去する
				gr2.removeEdgeAndGluePT4Retro(lg,gr2,tmped);
				//残ったエッジの組換え位置の情報(labelは繰り返し処理の１世代前のそれなので、書き換えが必要
				
				
				//gr2.removeNode(gr2,rec_gr2);
				
				preBackNodenum = gr2.nodes.length;
				preBackEdgenum = gr2.edges.length;
				preBackNodenum0 = gr1.nodes.length;
				//System.out.println("BackRec内　ポスト　nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				lg.addRetroMR(4);//retroMR片側成功
				lg.addRetroHpChild(child.hp[0]);
				lg.addRetroHpParent1(oya.hp[0]);
				lg.addRetroHpParent2(null);
			}else{
				//再分割は片親判定よりも１回り少ない回数しか行わない
				if(numiter<maxnumiter){
//					指定されたcross_locはallsnpsの0から、allsnps.length-2番目まで
					System.out.println("allsnps len " + allsnps.length + " depth " + depth  + "loc" + allsnps[depth]);
					int loc_cross[]={allsnps[depth]};
					depth = numiter+1;
					/*
					//組換え分割(組換え箇所を発生させ、２親に分離して再帰的にbackRecParentをかける
					int num_cross = 1;//defaultで1箇所
					*/
					/*
					//組換え箇所はセグメント内じゃないと、親探しが進展しないので、セグメント内に発生させる
					//セグメントの総長は
					int seg_len=0;
					for(int i=0;i<st_seg.length;i++){
						seg_len += (end_seg[i]-st_seg[i]);
					}
					System.out.println("seg_len " + seg_len);
					*/
					/*
					int loc_cross[];
					loc_cross = new int[num_cross];
					for(int i = 0;i<num_cross;i++){
						loc_cross[i] = (int)(Math.random()*lg.numsnp);
					*/
						/*
						int tmploc,tmptmploc;
						tmploc=(int)(Math.random()*seg_len);
						System.out.println("tmploc " + tmploc);
						tmptmploc = tmploc;
						//tmplocをセグメント上の塩基番号に変換する
						int accum=0;
						for(int j=0;j<st_seg.length;j++){
							int preaccum = accum;
							int pretmptmploc = tmptmploc;
							accum+=(end_seg[j]-st_seg[j]);
							tmptmploc-=(end_seg[j]-st_seg[j]);
							if(accum>=tmploc){
								loc_cross[i]=pretmptmploc+st_seg[j];
								System.out.println("loc_cross " + loc_cross[i]);
								break;
							}
						}
						//loc_cross[i] = (int)(Math.random()*seg_len);
						 *
						 */
					/*
					}
					//組換え箇所をソートする
					MiscUtil.quick_sort_int(loc_cross);
					*/
					//組換えパターンに基づき、2親のsegmentリストを作成する
					int st_seg1all[]={};
					int end_seg1all[]={};
					int st_seg2all[]={};
					int end_seg2all[]={};
					
					for(int i=0;i<st_seg.length;i++){
						int st_seg1[]={};
						int st_seg2[]={};
						int end_seg1[]={};
						int end_seg2[]={};
						int sep_loc[] ={};//seg上の組換え点を登録
						int oyaselect=0;//segの最初の部分の帰属親を規定
						
						for(int j=0;j<loc_cross.length;j++){//segment開始点よりも手前にあるloc_crossの数を数え、segの最初の部分の親を０か１か決める
							if(loc_cross[j]<st_seg[i]){
								if(oyaselect ==0){
									oyaselect = 1;
								}else{
									oyaselect = 0;
								}
							}else if(loc_cross[j]<end_seg[i]){//segment の範囲内にあるcross_loc
								int tmp[];
								tmp = new int[sep_loc.length+1];
								for(int k=0;k<sep_loc.length;k++){
									tmp[k]=sep_loc[k];
								}
								tmp[sep_loc.length]=loc_cross[j];
								sep_loc = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									sep_loc[k]=tmp[k];
								}
							}else{//end_segより大きいcross_locは何の関係もない
								break;
							}
						}
						//新セグメントはst_seg[i]-1, sep_loc[],end_seg[i]が作る要素数sep_loc.length+2の地点が作る
						//sep_loc.length+1このセグメントに分けられる
						int tmpsep[];
						tmpsep = new int[sep_loc.length+2];
						tmpsep[0]=st_seg[i];
						for(int j=0;j<sep_loc.length;j++){
							tmpsep[j+1]=sep_loc[j];
						}
						//tmpsep[sep_loc.length+1]=end_seg[i]+1;
						tmpsep[sep_loc.length+1]=end_seg[i];
						//sep_locの塩基番号は新セグメントの終点、sep_locの塩基番号+１は新セグメントの始点である
						//親１、親２に交互に新セグメントを割り振る
						//最初の親が親１か親２かは、oyaselectが定める
						
						for(int j=0;j<tmpsep.length-1;j++){
							if(oyaselect==0){
								//親１に
								int tmp[];
								int tmp2[];
								tmp=new int[st_seg1.length+1];
								tmp2=new int[st_seg1.length+1];
								for(int k=0;k<st_seg1.length;k++){
									tmp[k]=st_seg1[k];
									tmp2[k]=end_seg1[k];
								}
								if(j==0){
									tmp[st_seg1.length]=tmpsep[j];
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg1.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg1.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg1.length]=tmpsep[j+1];
									}
								}else{
									tmp[st_seg1.length]=tmpsep[j]+1;
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg1.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg1.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg1.length]=tmpsep[j+1];
									}
								}
								st_seg1 = new int[tmp.length];
								end_seg1 = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									st_seg1[k]=tmp[k];
									end_seg1[k]=tmp2[k];
								}
								//次のセグメントは親２に
								oyaselect=1;
							}else{
								//親２に
								int tmp[];
								int tmp2[];
								tmp=new int[st_seg2.length+1];
								tmp2=new int[st_seg2.length+1];
								for(int k=0;k<st_seg2.length;k++){
									tmp[k]=st_seg2[k];
									tmp2[k]=end_seg2[k];
								}
								if(j==0){
									tmp[st_seg2.length]=tmpsep[j];
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg2.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg2.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg2.length]=tmpsep[j+1];
									}
								}else{
									tmp[st_seg2.length]=tmpsep[j]+1;
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg2.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg2.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg2.length]=tmpsep[j+1];
									}
								}
								st_seg2 = new int[tmp.length];
								end_seg2 = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									st_seg2[k]=tmp[k];
									end_seg2[k]=tmp2[k];
								}
								//次のセグメントは親１に
								oyaselect=0;
							}
						}
						st_seg1all=MiscUtil.addintarray(st_seg1all,st_seg1);
						end_seg1all=MiscUtil.addintarray(end_seg1all,end_seg1);
						st_seg2all=MiscUtil.addintarray(st_seg2all,st_seg2);
						end_seg2all=MiscUtil.addintarray(end_seg2all,end_seg2);
						
						
					}
					
					//	st_segxxxはnullだと不都合かも
					if(st_seg1all.length==0){
						st_seg1all=new int[1];
						end_seg1all=new int[1];
						st_seg1all[0]=lg.numsnp;
						end_seg1all[0]=lg.numsnp;
					}
					if(st_seg2all.length==0){
						st_seg2all=new int[1];
						end_seg2all=new int[1];
						st_seg2all[0]=lg.numsnp;
						end_seg2all[0]=lg.numsnp;
					}
				
					//oya1
					System.out.println("oya1 number of seg " + st_seg1all.length);
					//oya1をgr2に加える
					
					////////////////////////////
//					System.out.println("snps len " + snps.length);
					//tmphp = new Haplotype(lg.hp.length,parenthp);

					int snps1[]={};
					int snps2[]={};
					
					for(int j=0;j<child.hp[0].hp.length;j++){
						int judge12=2;
						for(int k=0;k<st_seg1all.length;k++){
							if(child.hp[0].hp[j]>=st_seg1all[k] && child.hp[0].hp[j]<=end_seg1all[k]){
								judge12=1;
								break;
							}
						}
						if(judge12==1){
							int tmpsnps1[];
							tmpsnps1 = new int[snps1.length+1];
							for(int l=0;l<snps1.length;l++){
								tmpsnps1[l]=snps1[l];
							}
							tmpsnps1[snps1.length]=child.hp[0].hp[j];
							snps1 = new int[tmpsnps1.length];
							for(int l=0;l<tmpsnps1.length;l++){
								snps1[l]=tmpsnps1[l];
							}
						}else if(judge12 ==2){
							int tmpsnps2[];
							tmpsnps2 = new int[snps2.length+1];
							for(int l=0;l<snps2.length;l++){
								tmpsnps2[l]=snps2[l];
							}
							tmpsnps2[snps2.length]=child.hp[0].hp[j];
							snps2 = new int[tmpsnps2.length];
							for(int l=0;l<tmpsnps2.length;l++){
								snps2[l]=tmpsnps2[l];
							}
						}
					}
					
					
					Haplotype tmphp1 = new Haplotype(lg.hp.length,snps1);
					tmphp1.addStEnd(st_seg1all,end_seg1all);
					lg.addHaplotype(tmphp1);
					
					Haplotype tmphp2 = new Haplotype(lg.hp.length,snps2);
					tmphp2.addStEnd(st_seg2all,end_seg2all);
					lg.addHaplotype(tmphp2);
					
					Node tmpnd1, tmpnd2;
					tmpnd1=new Node(lg.nd.length,gr2,gr2.nodes.length);
					lg.addNode(tmpnd1);
					gr2.addNode(gr2,tmpnd1);
					tmpnd1.addHaplotype(tmphp1);
					tmphp1.addArgNode(tmpnd1);
					
					tmpnd2=new Node(lg.nd.length,gr2,gr2.nodes.length);
					lg.addNode(tmpnd2);
					gr2.addNode(gr2,tmpnd2);
					tmpnd2.addHaplotype(tmphp2);
					tmphp2.addArgNode(tmpnd2);
					
					
					
					
					//Node gr2child = child.hp[0].argnd;
					
					Edge tmped1;
					//tmped1 = new Edge(lg.ed.length,gr2,tmpnd1,gr2child,-9);//jはmutation
					tmped1 = new Edge(lg.ed.length,gr2,tmpnd1,child,-9);//jはmutation
					System.out.println("bifurcation rec1 rec2 "+ rec1 + " " + rec2);
					tmped1.rec1=rec1;
					tmped1.rec2=rec2;
					//System.out.println("parent なし　pre addEdge " + tmparr[1].nodes.length);
					gr2.addEdge(gr2,tmped1);//両端点のエッジ情報はここで更新
					//System.out.println("parent なし　post addEdge " + tmparr[1].nodes.length);
					lg.addEdge(tmped1);
					
					Edge tmped2;
					//tmped2 = new Edge(lg.ed.length,gr2,tmpnd2,gr2child,-9);//jはmutation
					tmped2 = new Edge(lg.ed.length,gr2,tmpnd2,child,-9);//jはmutation
					tmped2.rec1=rec1;
					tmped2.rec2=rec2;
					//System.out.println("parent なし　pre addEdge " + tmparr[1].nodes.length);
					gr2.addEdge(gr2,tmped2);//両端点のエッジ情報はここで更新
					//System.out.println("parent なし　post addEdge " + tmparr[1].nodes.length);
					lg.addEdge(tmped2);
					//tmpnd2.addEdge(tmped);
					//gr2child.addEdge(tmped);
					lg.addRetroMR(3);//retro処理で親なしBackMutしたので1を登録
					lg.addRetroHpChild(child.hp[0]);
					lg.addRetroHpParent1(tmphp1);
					lg.addRetroHpParent2(tmphp2);
					
					
					
					
					////////////////////////////
					int tmprec1=loc_cross[0];
					int tmprec2=0;
					for(int j=0;j<allsnps_or.length;j++){
						if(tmprec1==allsnps_or[j]){
							tmprec2 = allsnps_or[j+1];
						}
					}
					
					backRecNoParent(lg,gr1,gr2,tmpnd1,rest,st_seg1all,end_seg1all,maxnumiter,depth,allsnps,tmprec1,tmprec2,allsnps_or);
					//oya2
					System.out.println("oya2 number of seg " + st_seg2all.length);
					backRecNoParent(lg,gr1,gr2,tmpnd2,rest,st_seg2all,end_seg2all,maxnumiter,depth,allsnps,tmprec1,tmprec2,allsnps_or);
					
				}
			}
				
			
		}
	}
	public boolean judgeARGComplete(){
		boolean ret;
		//ret = true;
		ret = false;
		
		return ret;
	}

	//グラフの連結性をチェック
	public boolean connectedGraph(){
		/*
		 * グラフの横型探索を行い、訪問ノードを増やせるだけ増やす、
		 * 増えなくなったときにグラフ上のすべてのノードを訪問していたら連結であるとする
		 */
		//System.out.println("connection check start");
		boolean ret=true;
		
		//edgeの数はnodeの数-1未満だったら、連結ではありえない
		if(nodes.length-1>edges.length){
			ret = false;
			return ret;
		}
		
		int visited[]=null;//訪問済みノードidを登録
		int tovisit[]=null;//訪問することが決まりながら、まだ訪問していないノードidを登録
		if(nodes.length==0){
			//連結であるとする
		}else{
			//訪問済みノードID、これから訪問するべきノードIDの初期値を与える
			visited = new int[1];
			visited[0]=nodes[0].id;
			tovisit = new int[nodes[0].edge.length];
			Node tmp = nodes[0];
			for(int i=0;i<tovisit.length;i++){
				if(tmp.edge[i].start==tmp){
					tovisit[i]=tmp.edge[i].end.id;
				}else{
					tovisit[i]=tmp.edge[i].start.id;
				}
				//System.out.println("tovisit id " + tovisit[i]);
			}
			//tovisitからvisitedの要素を除く
			MiscUtil.rmIdentical(tovisit,visited);
			//System.out.println("tovisit nodes num " + tovisit.length);
			//System.out.println("visited nodes num " + visited.length);
			
			while(tovisit.length>0){
			//int test=0;
			//while(test>0){
				//System.out.println("while loop ");
				//System.out.println("tovisit nodes num " + tovisit.length);
				//System.out.println("visited nodes num " + visited.length);
				int tmpvisited[];
				tmpvisited = new int[visited.length];
				for(int i=0;i<visited.length;i++){
					tmpvisited[i]=visited[i];
				}
				int tmptovisit[];
				tmptovisit = new int[tovisit.length];
				for(int i=0;i<tovisit.length;i++){
					tmptovisit[i]=tovisit[i];
				}
				
				for(int i=0;i<tovisit.length;i++){
					//System.out.println("tovisit i " + tovisit[i]);
					int toadd[];
					tmp = null;
					for(int j=0;j<nodes.length;j++){
						if(tovisit[i] == nodes[j].id){
							tmp=nodes[j];
						}
					}
					//System.out.println("tmp node " + tmp.id);
					if(tmp == null){
						
					}else{
						toadd = new int[tmp.edge.length];
						for(int k=0;k<tmp.edge.length;k++){
							if(tmp.edge[k].start==tmp){
								toadd[k]=tmp.edge[k].end.id;
							}else{
								toadd[k]=tmp.edge[k].start.id;
							}
						}
						//System.out.println("toadd len 1 " + toadd.length);
						toadd = MiscUtil.rmIdentical(toadd,tmptovisit);//すでに訪問済みのものはこれから訪問ノードに加えない
						//System.out.println("toadd len 2 " + toadd.length);
						tmptovisit = MiscUtil.addintarray(tmptovisit,toadd);//重複を除いた後のtoaddを加える
						//System.out.println("tmptovisit len " + tmptovisit.length);
						int justvisited[]={tovisit[i]};
						//System.out.println("justvisited len 1 " + justvisited.length);
						justvisited = MiscUtil.rmIdentical(justvisited,tmpvisited);
						//System.out.println("justvisited len 2 " + justvisited.length);
						tmpvisited = MiscUtil.addintarray(tmpvisited,justvisited);//訪問済みでないところを訪問したら、訪問済みに加える
						//System.out.println("tmpvisited " + tmpvisited.length);
						tmptovisit = MiscUtil.rmIdentical(tmptovisit,tmpvisited);
						//System.out.println("tmptovisit " + tmptovisit.length);
						//tmptovisitが更新された、tovisitは更新されていない
						//tmpvisitedが更新された、visitedは更新されていない
					}
					
				}
				visited = new int[tmpvisited.length];
				tovisit = new int[tmptovisit.length];
				for(int i=0;i<tmpvisited.length;i++){
					visited[i]=tmpvisited[i];
				}
				for(int i=0;i<tmptovisit.length;i++){
					tovisit[i]=tmptovisit[i];
				}
			}
		}
		//System.out.println("visited nodes num " + visited.length);
		if(visited.length == nodes.length){
			
		}else{
			ret = false;
		}
		return ret;
	}
	//Root nodeの検出
	//自身が終点となるエッジを持たないノードをroot nodeとする
	//自身のみで構成される連結グラフにおいて、「自身」はroot nodeであるとする
	public Node[] listRootNode(){
		Node list[]={};
		for(int i=0;i<nodes.length;i++){
			boolean judge=true;
			for(int j=0;j<nodes[i].edge.length;j++){
				if(nodes[i].edge[j].end == nodes[i]){
					judge=false;
					break;
				}
			}
			if(judge){
				Node tmp[];
				tmp = new Node[list.length+1];
				for(int j=0;j<list.length;j++){
					tmp[j]=list[j];
				}
				tmp[list.length]=nodes[i];
				list = new Node[tmp.length];
				for(int j=0;j<tmp.length;j++){
					list[j]=tmp[j];
				}
			}
		}
		return list;
	}

	/*
	 * 出力method
	 */
	public void printGraphAll(){
		String st ="";
		st += " graph ";
		st += " id " + id;
		st += " nodes ";
		for(int i=0;i<nodes.length;i++){
			st += nodes[i].id + " ";
		}
		st += " edges ";
		for(int i=0;i<edges.length;i++){
			st += edges[i].id + " ";
		}
		st += " direction " + direction ;
		st += " type " + type;
		st += " event " ;
		for(int i=0;i<ev.length;i++){
			st += ev[i].id + " ";
		}
		st += " tr " + tr;
		st += " label " + label;
		st += " location " + or_x + " " + len_x + " " + or_y + " " + len_y + " "+ or_z + " " + len_z;
		
		System.out.println(st);
	}
	public void printGraphAllNodeEdge(){
		String st ="";
		st += " graph ";
		st += " id " + id + "\n";
		st += " nodes " + nodes.length + "個\n";
		for(int i=0;i<nodes.length;i++){
			st += "node " + nodes[i].id + "\n";
			st += "edge \n";
			for(int j=0;j<nodes[i].edge.length;j++){
				st += nodes[i].edge[j].id + " ";
			}
			st += "\n";
			st += "haplotype \n";
			for(int k=0;k<nodes[i].hp.length;k++){
				st += nodes[i].hp[k].id + " ";
			}
			st += "\n";
		}
		st += "\n edges " + edges.length + "個\n";
		for(int i=0;i<edges.length;i++){
			st += "edge " + edges[i].id + " ";
			st += "start " + edges[i].start.id + " ";
			st += "end " + edges[i].end.id + " \n";
		}
		st += "\n";
		st += " direction " + direction ;
		st += " type " + type;
		st += " event " ;
		for(int i=0;i<ev.length;i++){
			st += ev[i].id + " ";
		}
		st += " tr " + tr;
		st += " label " + label;
		st += " location " + or_x + " " + len_x + " " + or_y + " " + len_y + " "+ or_z + " " + len_z;
		st += "\n nodes " + nodes.length + "個\n";
		st += "\n edges " + edges.length + "個\n";
		System.out.println(st);
	}

	public void outPajek(String st_){
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//vertices header
			out += "*Vertices ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			out += numbernode + "\n";
			//vertices
			
			for(int i=1;i<numbernode+1;i++){
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
			}
			//edges header
			out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id +1;
				int outendid = edges[i].end.id +1;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				out += outstid + " " + outendid + " " + "1";
				if(i<numberedge-1){
					out += "\n";
				}
			}
			System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	
	public void outVGJ(String st_){
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "node [ \n id " 
					+ nodes[i].id + "\n" 
					+ "label " + "\"" + nodes[i].name + "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 0.5 height 0.5 depth 0.5 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id ;
				int outendid = edges[i].end.id ;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				//out += outstid + " " + outendid + " " + "1";
				out += "edge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
				     + "source " + edges[i].start.id
					 + "target " + edges[i].end.id + "]";
				//if(i<numberedge-1){
					//out += "\n";
				//}
			}
			//footer
			out += "]";
			System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public String outVGJtoSt(){
		
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			//bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "node [ \n id " 
					+ nodes[i].id + "\n" 
					+ "label " + "\"" + nodes[i].name + "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 0.5 height 0.5 depth 0.5 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id ;
				int outendid = edges[i].end.id ;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				//out += outstid + " " + outendid + " " + "1";
				out += "edge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
				     + "source " + edges[i].start.id
					 + "target " + edges[i].end.id + "]";
				//if(i<numberedge-1){
					//out += "\n";
				//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			//bw1.write(out);
			//bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		return out;
		
	}
	public void outVGJ_noLabel(String st_){//Node No Label
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "\nnode [ \n id " 
					+ nodes[i].id + "\n" 
					//+ "label " + "\"" + nodes[i].name + "\""
					+ "label " + "\"" +  "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 1 height 1 depth 1 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			
				for(int i=0;i<numberedge;i++){
					int outstid = edges[i].start.id ;
					int outendid = edges[i].end.id ;
					//out += edges[i].start.id + " " + edges[i].end.id + "\n";
					//out += outstid + " " + outendid + " " + "1";
					out += "\nedge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
						+ "source " + edges[i].start.id
							+ "target " + edges[i].end.id + "]";
					//if(i<numberedge-1){
					//out += "\n";
				}	//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public void outVGJ_noLabel2(String st_){//Node & Edge No label
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "\nnode [ \n id " 
					+ nodes[i].id + "\n" 
					//+ "label " + "\"" + nodes[i].name + "\""
					+ "label " + "\"" +  "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 1 height 1 depth 1 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			
				for(int i=0;i<numberedge;i++){
					int outstid = edges[i].start.id ;
					int outendid = edges[i].end.id ;
					//out += edges[i].start.id + " " + edges[i].end.id + "\n";
					//out += outstid + " " + outendid + " " + "1";
					out += "\nedge [\nlinestyle \"solid\"\nlabel \"" +  "\""
						+ "source " + edges[i].start.id
							+ "target " + edges[i].end.id + "]";
					//if(i<numberedge-1){
					//out += "\n";
				}	//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public void circle(){
		//与えられたｘ範囲を直径とする円周上に配置する
		for(int i=0;i<nodes.length;i++){
			double theta;
			theta=(2*i*Math.PI)/nodes.length;
			//System.out.println("theta " +theta);
			nodes[i].x=Math.sin(theta)*(Math.abs(or_x-(len_x+1))/2)+(or_x+len_x+1)/2;
			//System.out.println("x " + nodes[i].x);
			//nodes[i].y=Math.cos(theta)*(Math.abs(or_x-len_x)/2)+(or_y+len_y)/2;
			nodes[i].y=Math.cos(theta)*(Math.abs(or_x-(len_x+1))/2);
			//System.out.println("y " + nodes[i].y);
		}
	}
	public int[][] edgeMatrix(){
		int mat[][];
		mat = new int[nodes.length][nodes.length];
		int nd[];
		int max=0;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i].id > max){
				max=nodes[i].id;
			}
		}
		nd = new int[max+1];
		for(int i=0;i<nodes.length;i++){
			nd[nodes[i].id]=i;
			for(int j=0;j<nodes.length;j++){
				mat[i][j]=0;
			}
		}
		for(int i=0;i<edges.length;i++){
			mat[nd[edges[i].start.id]][nd[edges[i].end.id]]=1;
		}
		return mat;
		
	}
	
	public void plotTree(double width,double height,double rx, double ry){
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		nodes[0].x= width/2;//中央
		nodes[0].y=0;
		dx=2*width/nodes.length;
		dy=height;
		int dir=1;
		for(int i=1;i<n;i++){
			dy*=ry;
			dx*=rx;
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<i;j++){
				if(mat[j][i]==1){
					nodes[i].x+=dir*dx+ nodes[j].x;
					numpat++;
					dir*=(-1);
				}
			}
			nodes[i].x/=numpat;
		}
	}
	public void plotTreeHP(){
		double width,height,rx,ry;
		width=Math.abs(or_x-len_x+1);
		height = width/nodes.length;
		rx =1;
		ry = 0.95;
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		//System.out.println("or_x " + or_x );
		System.out.println("or_x " + or_x );
		System.out.println("width " + width );
		System.out.println("nodes[0].id " + nodes[0].id);
		System.out.println("nodes[0].x " + nodes[0].x );
		nodes[0].x= or_x + width/2;//中央
		nodes[0].y=0;
		dx=2*width/nodes.length;
		dy=height;
		int dir=1;
		for(int i=1;i<n;i++){
			dy*=ry;
			dx*=rx;
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<n;j++){
				if(mat[j][i]==1){
					nodes[i].x+=dir*dx+ nodes[j].x;
					numpat++;
					
					dir*=(-1);
				}
			}
			//System.out.println("numpat" + numpat);
			nodes[i].x/=numpat;
		}
	}
	public void plotTreeHPinfinite(double ratio,double angle){
		//初期半径length、世代ごとの半径減少率ratioとすると
		//無限回数の分岐において到達遠点が領域半径rangeとすると
		//range*(1-ratio)=lengthの関係がある
		//range = width/2;
		angle *= 0.5 * Math.PI;
		
		double range,length,theta;
		 
		double width,height,rx,ry;
		width=Math.abs(or_x-len_x+1);
		height = width/nodes.length;
		
		range = width/2;
		length = range * (1-ratio);
		theta = 0.5* Math.PI;
		
		rx =1;
		ry = 0.95;
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		
		nodes[0].x= or_x + width/2;//中央
		nodes[0].y=0;
		
		dx=length*Math.sin(theta);
		dy=length*Math.cos(theta);
		//int dir=1;
		int dir[];
		dir = new int[n];
		dir[0]=1;
		
		for(int i=1;i<n;i++){
			theta -= angle;
			length*=ratio;
			dy = length*Math.cos(theta);
			dx = length*Math.sin(theta);
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<n;j++){
				if(mat[j][i]==1){
					
					nodes[i].x+=dir[j]*dx+ nodes[j].x;
					numpat++;
					
					dir[j]*=(-1);
					dir[i]=dir[j];
					//dir*=(-1);
				}
			}
			//System.out.println("numpat" + numpat);
			nodes[i].x/=numpat;
		}
	}
	public void sortRooted(){
		Node tmp[],tmp2[];
		tmp=new Node[0];
		tmp2 =new Node[0];
		Node tmpst,tmpend;
		if(edges==null){
			
		}else if(edges.length==0){
			
		}else{
			for(int i=0;i<edges.length;i++){
				tmpst=edges[i].start;
				tmpend=edges[i].end;
				int numadd=2;
				int stflag=0;
				int endflag=0;
				int storder=0;
				int endorder=tmp.length;
				for(int j=0;j<tmp.length;j++){
					if(tmp[j]==tmpst){
						numadd--;
						endflag=1;
						storder=j;
					}
					if(tmp[j]==tmpend){
						numadd--;
						stflag=1;
						endorder=j;
					}
				}
//				
				tmp2 =new Node[tmp.length+numadd];
				if(numadd==2){//末尾に追加
					tmp2[0]=tmpst;
					for(int j=0;j<tmp.length;j++){
						tmp2[j+1]=tmp[j];
					}
					//tmp2[tmp.length]=tmpst;
					tmp2[tmp.length+1]=tmpend;
				}else if(numadd==1){
					if(stflag==1){//tmpstを先頭に加える
						tmp2[0]=tmpst;
						for(int j=0;j<tmp.length;j++){
							tmp2[j+1]=tmp[j];
						}
					}else if(endflag==1){
						for(int j=0;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[tmp.length]=tmpend;
					}
				}else if(numadd==0){
					if(storder<=endorder){
						
					}else{
						
						for(int j=0;j<endorder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[endorder]=tmpend;
						for(int j=endorder+1;j<storder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[storder]=tmpst;
						for(int j=storder+1;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
					}
				}
				
				tmp = new Node[tmp2.length];
				for(int j=0;j<tmp2.length;j++){
					tmp[j]=tmp2[j];
				}
			}
			nodes = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				nodes[i]=tmp[i];
			}
		}
		
	}
	
	public boolean TreeChecker(){
		//Treeと仮定してエッジ情報を使ってノードを上流から下流に向かって並び替える
		//並び替えには、エッジを1回ずつ使う
		//並び替え終了後にもう一度、すべてのエッジでの上流下流関係があっていれば、それはグラフが木だったことを意味する
		//そうでなければ、矛盾点(サイクル)が存在する
		//ただし、ループはこの矛盾を回避するので、ループを持つかどうかは別の機構でチェックする必要がある
		
		Node tmp[],tmp2[];
		tmp=new Node[0];
		tmp2 =new Node[0];
		Node tmpst,tmpend;
		if(edges==null){
			
		}else if(edges.length==0){
			
		}else{
			for(int i=0;i<edges.length;i++){
				tmpst=edges[i].start;
				tmpend=edges[i].end;
				int numadd=2;
				int stflag=0;
				int endflag=0;
				int storder=0;
				int endorder=tmp.length;
				for(int j=0;j<tmp.length;j++){
					if(tmp[j]==tmpst){
						numadd--;
						endflag=1;
						storder=j;
					}
					if(tmp[j]==tmpend){
						numadd--;
						stflag=1;
						endorder=j;
					}
				}
//				
				tmp2 =new Node[tmp.length+numadd];
				if(numadd==2){//末尾に追加
					tmp2[0]=tmpst;
					for(int j=0;j<tmp.length;j++){
						tmp2[j+1]=tmp[j];
					}
					//tmp2[tmp.length]=tmpst;
					tmp2[tmp.length+1]=tmpend;
				}else if(numadd==1){
					if(stflag==1){//tmpstを先頭に加える
						tmp2[0]=tmpst;
						for(int j=0;j<tmp.length;j++){
							tmp2[j+1]=tmp[j];
						}
					}else if(endflag==1){
						for(int j=0;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[tmp.length]=tmpend;
					}
				}else if(numadd==0){
					if(storder<=endorder){
						
					}else{
						
						for(int j=0;j<endorder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[endorder]=tmpend;
						for(int j=endorder+1;j<storder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[storder]=tmpst;
						for(int j=storder+1;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
					}
				}
				
				tmp = new Node[tmp2.length];
				for(int j=0;j<tmp2.length;j++){
					tmp[j]=tmp2[j];
				}
			}
			//nodes = new Node[tmp.length];この関数はチェッカーであってノードのならび替えはしない
			//for(int i=0;i<tmp.length;i++){
				//nodes[i]=tmp[i];
			//}
		}
		int st,end;
		st = 0;
		end = 0;
		boolean ret = true;
		for(int i=0;i<edges.length;i++){
			for(int j=0;j<tmp.length;j++){
				if(tmp[j]==edges[i].start){
					st = j;
				}
				if(tmp[j]==edges[i].end){
					end = j;
				}
			}
			if(st>end){
				ret = false;
			}
		}
		return ret;
	}
	/*
	 *constructor
	 */
	public Graph(int id_){
		id = id_;
		nodes = new Node[0];
		edges = new Edge[0];
		direction = 1;//default
		tr=1;//空は木とみなす
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	public Graph(int id_,int type_){
		id = id_;
		type = type_;
		nodes = new Node[0];
		edges = new Edge[0];
		direction = 1;//default
		tr=1;//空は木とみなす
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	public Graph(int id_,Node nd_){
		id = id_;
		nodes = new Node[1];
		nodes[0]=nd_;
		edges = new Edge[0];
		direction = 1;
		tr=1;//点は木である
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	
	public Graph(int id_,int type_,Node nd_){
		id = id_;
		type = type_;
		nodes = new Node[1];
		nodes[0]=nd_;
		edges = new Edge[0];
		tr=1;//点は木である
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	
}
