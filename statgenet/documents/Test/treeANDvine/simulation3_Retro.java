/*
 * 作成日: 2005/07/15
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 * @author yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class simulation3_Retro {
		
		
	
	
	
	//public static Log main(String[] args) {
	public static Log main(int numsnp_,int iteration_,int num_iteration_,double recratio_) {
		//実行ログ
		Log lg;
		lg=new Log();
		Event tmpev;
		Haplotype tmphaplotype;
		Node tmpnd;
		Edge tmped;
		Graph tmpgr;
	
		
		
		//graph setting
		int edgetype =1;//edgeは有向
		
		//output setting C:\pajek\Pajek\Data
		String rootdir = "C:\\pajek\\Pajek\\Data\\ry";
		String outarg = rootdir + "\\out_arg.txt";
		String outforest = rootdir + "\\out_forest.txt";
		String outforestTr = rootdir + "\\out_forestTr.txt";
		String outfr_vine = rootdir + "\\out_fr_vine.txt";
		String outtrees = rootdir + "\\out_trees_";
		String outtrees_sf ="";
		String outpajek2 = rootdir + "\\out2.txt";
		String outevent = rootdir + "\\event.txt";
		
		/*
		//simulation setting
		int numsnp = 10;//SNPの数、配列長にも相当
		int iteration =20;//mutation + recombinationの回数
		int num_iteration=0;
		double recratio = 0.0;//recombination - mutation の分配確率　閾値未満はrec
		*/
		int numsnp = numsnp_;
		int iteration = iteration_;
		int num_iteration = num_iteration_;
		double recratio = recratio_;
		
		//infinite-sites 仮定
		//mutationは最大numsnp回、それらがどういう順序でおきるかを(0..numsnp)の順列で規定
		int mutsequence[];
		mutsequence = new int[numsnp];
		for(int i=0;i<numsnp;i++){
			mutsequence[i]=i;
		}
		MiscUtil ms;
		ms = new MiscUtil();
		for(int i=0;i<mutsequence.length;i++){
			//System.out.println(mutsequence[i]);
		}
		ms.shuffle(mutsequence);
		for(int i=0;i<mutsequence.length;i++){
			//System.out.println(mutsequence[i]);
		}
		
		//Root
		//simulation開始のハプロタイプ発生
		/*
		 * 作成すべきもの
		 * Haplotype
		 * Root node for ARG, Forest and Root
		 * ARG graph
		 * Forest graph
		 * Tree graph
		 * Trees graph[]
		 * Edgeはなし
		 */
		Graph arg, forest,tmptree;
		Graph trees[];//treeの集合
		
		arg = new Graph(lg.gr.length);
		arg.range(0,numsnp-1,0,0,0,0);
		
		arg.type=0;//ARG type
		lg.addGraph(arg);
		forest = new Graph(lg.gr.length);
		forest.range(0,numsnp-1,0,0,0,0);
		
		arg.type=1;//FOREST TYPE
		lg.addGraph(forest);
		tmptree = new Graph(lg.gr.length);
		tmptree.range(0,numsnp-1,0,0,0,0);
		
		tmptree.type=2;//Tree type
		lg.addGraph(tmptree);
		trees = new Graph[1];
		trees[0]=tmptree;
		
		System.out.println("Start " );
		//とりあえず１ハプロタイプ
		//Node をrootとして作成するevent,grに登録
		Node root_arg,root_tr;
		root_arg = new Node(lg.nd.length,arg,arg.nodes.length,0,0,0);//id = 0,x=0,y=0,z=0
		root_arg.addType(3);//root type
		
		lg.addNode(root_arg);
		arg.addNode(arg,root_arg);
		
		
		root_tr = new Node(lg.nd.length,tmptree,tmptree.nodes.length,0,0,0);
		root_tr.addType(3);
		lg.addNode(root_tr);
		tmptree.addNode(tmptree,root_tr);
		
		//haplotypeはALL-0
		int tmphap[];
		tmphap = new int[numsnp];
		for(int i=0;i<numsnp;i++){
			tmphap[i]=0;
		}
		
		/*
		 * 000を登録しないでメモリを節約する
		 */
		int mutationcnt=0;
		for(int i=0;i<numsnp;i++){
			if(tmphap[i]==1){
				mutationcnt++;
			}
		}
		int muthap[];
		muthap = new int[mutationcnt];
		int countermut=0;
		for(int i=0;i<mutationcnt;i++){
			for(int j=0;j<numsnp;j++){
				if(tmphap[j]==1){
					muthap[countermut]=tmphap[j];
					countermut++;
				}
				
			}
			
		}
		//tmphaplotype = new Haplotype(lg.hp.length,tmphap,root_arg,root_tr);
		tmphaplotype = new Haplotype(lg.hp.length,muthap,root_arg,root_tr);
		lg.addHaplotype(tmphaplotype);
		root_arg.addHaplotype(tmphaplotype);
		root_tr.addHaplotype(tmphaplotype);
		
		//Event 定義してログに登録
		tmpev = new Event(lg.ev.length,3,arg,forest,tmptree,root_arg,root_tr,root_tr);
		lg.addEvent(tmpev);
		tmpev.addChildHaplotype(tmphaplotype);
		arg.addEvent(tmpev);
		forest.addEvent(tmpev);
		tmptree.addEvent(tmpev);
		
		
		num_iteration++;
		//ノードにイベント登録
		root_arg.addEvent(tmpev);
		root_tr.addEvent(tmpev);		
		
		//ハプロタイプをイベント、ノードに登録、ハプロタイプにイベントを登録
		tmphaplotype.addEvent(tmpev);
		
		forest = new Graph(lg.gr.length);
		forest.range(0,numsnp-1,0,0,0,0);
		forest.type=1;
		
		for(int j=0;j<trees.length;j++){//Tree->Forest変換
			
			forest.addGraph(forest,trees[j]);
			
		}
		forest.type=1;//type forest
		for(int j=0;j<lg.hp.length;j++){
			lg.hp[j].forestnd = new Node[lg.hp[j].treend.length];
			for(int k=0;k<lg.hp[j].treend.length;k++){
				lg.hp[j].forestnd[k]=lg.hp[j].treend[k];
			}
		}
		//forest.tree2forest();	
//		forest node edgeのグラフ内IDの付け直し
		forest.sortNode();
		forest.sortEdge();
		
		//simulation start
		int cnt_mut=0;//累積mutation数のカウント
		int num_rec=0;
		int num_no_rec=0;
		int num_mut=0;
		
		for(int i=num_iteration;i<iteration;i++){
			num_iteration++;
			System.out.println("loop " + i +"/" +iteration);
			String tmpgraph="";
			tmpgraph += num_mut + " mutaions " + num_rec +" recombinations " + num_no_rec + " fake recombinations\n";
			tmpgraph += "number of trees " + trees.length +"\n";
			tmpgraph += "ARG\t\tG(" + arg.nodes.length + "," + arg.edges.length + ")\n";
			tmpgraph += "Forest\t\tG(" + forest.nodes.length + "," + forest.edges.length + ")\n";
			
			
			for(int j=0;j<trees.length;j++){
				tmpgraph += "tree " + j + "\t\tG("+ trees[j].nodes.length + "," + trees[j].edges.length + ")" + " seg " + trees[j].or_x + " - " + trees[j].len_x +"\n";
			}
			System.out.println(tmpgraph);
			//arg.printGraphAllNodeEdge();
			//forest.printGraphAllNodeEdge();

			double rand = Math.random();//mutation かレコンビネーションか?
			if(rand<recratio){//recombinationの場合
				//oya x 2を決める	
				double rand2 = Math.random();
				double rand3 = Math.random();
				
				int rec1 = (int)(rand2 * arg.nodes.length);//rec parent1(5' oya)
				int rec2 = (int)(rand3 * arg.nodes.length);//rec parent2(3' oya)
				
				if(rec1==rec2){//oya x 2 が同一
					//System.out.println("Rec between identical haplotype pair");
					//recombinationじゃない
					/*
					 * Eventのみ存在し、ハプロタイプも生まれなければ、ノード・エッジ・グラフの変化もない
					 */
					//System.out.println("oya5=oya3");
					//Eventはnone type
					tmpev = new Event(lg.ev.length,2);
					lg.addEvent(tmpev);
					num_no_rec++;
				}else{//oya x 2 が異なる
					num_rec++;
					//組換えサイトを決定
					//組換えは１箇所の仮定
					//組換えに伴い、その組換え点の前後の塩基間で、treeは２本に分断される
					//forestとtreesではノードは増えない
					
					double rand4 = Math.random();
					//組換え位置は、SNP(k)-SNP(k+1)の間の組換えをkで与える
					int recpoint = (int)(rand4 * (numsnp-1));
					//System.out.println("Recombination recpoint " + recpoint + " /numsnp");
					//System.out.println("recpoint " + recpoint);
					
					//切るか否かの判定
					Graph cuttree;
					cuttree = trees[0];//仮
					int cutcheck=1;
					int memory=-1;
					for(int j=0;j<trees.length;j++){
						if(recpoint>=trees[j].or_x){
							if(recpoint<trees[j].len_x){
								cuttree= trees[j];
								cutcheck=0;
								memory =j;
								//cuttree.printGraphAllNodeEdge();
								
								break;
							}
						}
					}
					if(cutcheck==1){//cutしない
						//処理はmutation のときと一緒
					}else if(memory == -1){
						
					}else{//cutする
						//System.out.println("num trees " + trees.length);
						//System.out.println("Recombination");
						Graph tmpforest_less;
						tmpforest_less = new Graph(lg.gr.length);
						lg.addGraph(tmpforest_less);
						
						tmpforest_less.copy(lg,tmpforest_less,cuttree);
						//tmpforest_less.printGraphAll();
						tmpforest_less.type=2;
						
						tmpforest_less.recSeparation_less(lg,tmpforest_less,recpoint);
						
						/*
						tmpforest_less.cleanEdge();
						tmpforest_less.cleanNode();
						*/

						
						Graph tmpforest_more;
						tmpforest_more = new Graph(lg.gr.length);
						lg.addGraph(tmpforest_more);
						tmpforest_more.copy(lg,tmpforest_more,cuttree);
						tmpforest_more.type=2;
						
						tmpforest_more.recSeparation_more(lg,tmpforest_more,recpoint);
						
						
						tmpforest_less.or_x = cuttree.or_x;
						tmpforest_less.len_x = recpoint;
						trees[memory]=tmpforest_less;
						/*
						for(int j=0;j<trees[memory].nodes.length;j++){
							trees[memory].nodes[j].hp[trees[memory].nodes[j].hp.length-1].addTreeNode(trees[memory].nodes[j]);
						}
						*/
						tmpforest_more.or_x=recpoint +1;
						tmpforest_more.len_x=cuttree.len_x;
						Graph tmptrees[];
						tmptrees=new Graph[trees.length+1];
						for(int j=0;j<trees.length;j++){
							tmptrees[j]=trees[j];
						}
						tmptrees[trees.length]=tmpforest_more;
						/*
						for(int j=0;j<tmptrees[trees.length].nodes.length;j++){
							tmptrees[trees.length].nodes[j].hp[tmptrees[trees.length].nodes[j].hp.length-1].addTreeNode(tmptrees[trees.length].nodes[j]);
						}
						*/
						trees = new Graph[tmptrees.length];
						for(int j=0;j<tmptrees.length;j++){
							trees[j]=tmptrees[j];
							trees[j].type=2;//type はtree
						}
						
						forest = new Graph(lg.gr.length);
						forest.range(0,numsnp-1,0,0,0,0);
						forest.type=1;
						
						for(int j=0;j<trees.length;j++){//Tree->Forest変換
							
							forest.addGraph(forest,trees[j]);
							
						}
						forest.type=1;//type forest
						//System.out.println("tree2forest直前");
						//forest.printGraphAllNodeEdge();
						for(int j=0;j<lg.hp.length;j++){
							lg.hp[j].forestnd = new Node[lg.hp[j].treend.length];
							for(int k=0;k<lg.hp[j].treend.length;k++){
								lg.hp[j].forestnd[k]=lg.hp[j].treend[k];
							}
						}
						//forest.tree2forest();			
//						forest node edgeのグラフ内IDの付け直し
						forest.sortNode();
						forest.sortEdge();
					}
					
					Node nwnode;
					//座標規則
					double x_,y_,z_;
					x_=arg.nodes.length*10;//新規のノードはx軸方向に定数距離進む
					y_=(arg.nodes[rec1].y+arg.nodes[rec2].y)/2;//新規ノードは親の中間y座標
					z_=(arg.nodes[rec1].z+arg.nodes[rec2].z)/2+10;//親の中間＋定数距離z座標
					nwnode = new Node(lg.nd.length,arg,arg.nodes.length,x_,y_,z_,0);
					nwnode.type=1;
					arg.addNode(arg,nwnode);
					//ノードをログに登録
					lg.addNode(nwnode);
					//haplotype情報の追加,ノードとイベントに
					/*
					int tmphap2[];
					tmphap2 = new int[numsnp];
					for(int j=0;j<numsnp;j++){
						if(j<=recpoint){
							tmphap2[j]=arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp[j];
						}else{
							tmphap2[j]=arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp[j];
						}
					}
					*/
					int tmphap2[];
					int mutcnt=0;
					for(int j=0;j<arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp.length;j++){
						if(arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp[j]<=recpoint){
							mutcnt++;
						}
					}
					for(int j=0;j<arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp.length;j++){
						if(arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp[j]>recpoint){
							mutcnt++;
						}
					}
					tmphap2 = new int[mutcnt];
					int countermut2=0;
					for(int j=0;j<arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp.length;j++){
						if(arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp[j]<=recpoint){
							tmphap2[countermut2]=arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1].hp[j];
							countermut2++;
						}
					}
					for(int j=0;j<arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp.length;j++){
						if(arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp[j]>recpoint){
							tmphap2[countermut2]=arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1].hp[j];
							countermut2++;
						}
					}
					//tmphap2 = new int[numsnp];
					/*
					tmphap2 = new int[arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length+1];
					//for(int j=0;j<numsnp;j++){
					for(int j=0;j<arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length;j++){
						//tmphap2[j]=arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp[j];
						tmphap2[j]=arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp[j];
					}
					//if(cnt_mut<numsnp){
						//tmphap2[mutsequence[cnt_mut]]=1;
					//}
					tmphap2[arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length]=mutsequence[cnt_mut];
					*/
					
					tmphaplotype = new Haplotype(lg.hp.length,tmphap2);
					lg.addHaplotype(tmphaplotype);
					nwnode.addHaplotype(tmphaplotype);
					tmphaplotype.addArgNode(nwnode);
					
					
					
					//argに追加された新Haplotypeと新Nodeに対応して、Treesにも加える
					//組換え点より５'側ではrec1のノードに、3'側ではrec2のノードに加える
					//これにより、組換え体としてのHaplotypeはすべてのtreeにノードをただひとつもつこととなる
					Haplotype selectedhp1,selectedhp2;
					selectedhp1 = arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1];
					selectedhp2 = arg.nodes[rec1].hp[arg.nodes[rec2].hp.length-1];
					int cntaddhp=0;
					for(int j=0;j<trees.length;j++){
						if(recpoint>=trees[j].len_x){
							for(int k=0;k<trees[j].nodes.length;k++){
								for(int l=0;l<trees[j].nodes[k].hp.length;l++){
									if(trees[j].nodes[k].hp[l]==selectedhp1){
										trees[j].nodes[k].addHaplotype(tmphaplotype);
										tmphaplotype.addTreeNode(trees[j].nodes[k]);
										cntaddhp++;
									}
								}
								
							}
						}else if(recpoint<trees[j].or_x){
							for(int k=0;k<trees[j].nodes.length;k++){
								for(int l=0;l<trees[j].nodes[k].hp.length;l++){
									if(trees[j].nodes[k].hp[l]==selectedhp2){
										trees[j].nodes[k].addHaplotype(tmphaplotype);
										tmphaplotype.addTreeNode(trees[j].nodes[k]);
										cntaddhp++;
									}
								}
								
							}
						}
					}
					System.out.println("\n%%%%%%%%%%%%%%%%\n$$$$$$$$$$$$$$$$$$\n!!!!!!!!!!!!!!!!!!!\ntree num/added hp num " + trees.length + " " + cntaddhp);
					
					forest = new Graph(lg.gr.length);
					forest.range(0,numsnp-1,0,0,0,0);
					forest.type=1;
					
					for(int j=0;j<trees.length;j++){//Tree->Forest変換
						
						forest.addGraph(forest,trees[j]);
						
					}
					forest.type=1;//type forest
					for(int j=0;j<lg.hp.length;j++){
						lg.hp[j].forestnd = new Node[lg.hp[j].treend.length];
						for(int k=0;k<lg.hp[j].treend.length;k++){
							lg.hp[j].forestnd[k]=lg.hp[j].treend[k];
						}
					}
					//forest node edgeのグラフ内IDの付け直し
					forest.sortNode();
					forest.sortEdge();
					//forest.tree2forest();		

					//Event はrecombination 
					int tmpsites[];
					tmpsites = new int[1];
					tmpsites[0] = recpoint;
					tmpev = new Event(lg.ev.length,1,arg,nwnode,arg.nodes[rec1].hp[arg.nodes[rec1].hp.length-1],arg.nodes[rec2].hp[arg.nodes[rec2].hp.length-1],tmphaplotype,tmpsites);
					if(cutcheck == 1){
					}else{
						tmpev.fr = forest;
						tmpev.adTree = new Graph[1];
						tmpev.adTree[0] = cuttree;
						trees[memory].addEvent(tmpev);
						trees[trees.length-1].addEvent(tmpev);
						forest.addEvent(tmpev);
					}
					//イベントをログとノードに登録
					lg.addEvent(tmpev);
					
					tmpev.addHaplotype(tmphaplotype);
					//ハプロタイプにイベントを登録
					tmphaplotype.addEvent(tmpev);
					
					nwnode.addEvent(tmpev);
					
					//Edgeの追加　組換えについては、親２から子１を連結
					Edge tmpedge;
					int tmpedgenum=0;
					if(arg.edges == null){
						
					}else{
						tmpedgenum=arg.edges.length;
					}
					int edge_mut=-9;//recによるエッジは-9(mutation点はない)
					//edge x 2 をargaphに追加
					//このエッジはvine type
					//ageはない
					tmpedge = new Edge(lg.ed.length,arg,arg.edges.length,arg.nodes[rec1],nwnode,edgetype,edge_mut);
					//ログにエッジを登録
					lg.addEdge(tmpedge);

					//エッジにイベントを登録、イベントにエッジを登録
					tmpedge.addEvent(tmpev);
					tmpev.addEdge(tmpedge);
					//エッジをグラフに登録
					arg.addEdge(arg,tmpedge);
					tmpedge = new Edge(lg.ed.length,arg,arg.edges.length,arg.nodes[rec2],nwnode,edgetype,edge_mut);
					//ログにエッジを登録
					lg.addEdge(tmpedge);
					//エッジにイベントを登録、イベントにエッジを登録
					tmpedge.addEvent(tmpev);
					tmpev.addEdge(tmpedge);
					//エッジをグラフに登録
					arg.addEdge(arg,tmpedge);
					
					//nodeのedgeを登録
					//arg.nodes[arg.nodes.length-1].addEdge(arg.edges[arg.edges.length-2]);
					//arg.nodes[rec1].addEdge(arg.edges[arg.edges.length-2]);
					//arg.nodes[arg.nodes.length-1].addEdge(arg.edges[arg.edges.length-1]);
					//arg.nodes[rec2].addEdge(arg.edges[arg.edges.length-1]);
					
					
					
					//
					/*
					System.out.println("<<<<<<<<<<<<<rec>>>");
					System.out.println("arg num nodes " + arg.nodes.length);
					System.out.println("arg num edges " + arg.edges.length);
					System.out.println("fr num nodes " + forest.nodes.length);
					System.out.println("fr num edges " + forest.edges.length);
					System.out.println("trees " + trees.length);
					for(int j=0;j<trees.length;j++){
						System.out.println("tree " + j);
						System.out.println("tr nodes " + trees[j].nodes.length);
						System.out.println("tr edges " + trees[j].edges.length);
					}
					*/
					//System.out.println("rec oya " + arg.nodes[rec1].id + " " + arg.nodes[rec2].id);
					//System.out.println("recpoint " + recpoint);
					
				}
				
				
				
				
				
			}else{
				/*
				 * Haplotypeを作る
				 * ARGの枝を伸ばす（ノード１、エッジ１）
				 * Forestの１本のtreeの枝を伸ばす（ノード１、エッジ１）
				 * Haplotypeを現すtree間エッジを作る隣のtree(1 or 2)へブリッジを渡す
				 * 対応treeの枝を伸ばす（ノード１、エッジ１）
				 */
				//mutationのおきるハプロタイプと塩基が決まったら、その情報をもとに、改変するtree,node in tree, node in forestを調べる
				num_mut++;
				
				double rand2 = Math.random();//どのハプロタイプにmutationを起こすか
				int choice = (int)(rand2 * arg.nodes.length);
				Haplotype selectedhp;
				selectedhp = arg.nodes[choice].hp[arg.nodes[choice].hp.length-1];
				Graph selectedtree;
				selectedtree = trees[0];//仮入力
				int checkaritree=0;
				for(int j=0;j<trees.length;j++){
					if(mutsequence[cnt_mut]>=trees[j].or_x){
						if(mutsequence[cnt_mut]<=trees[j].len_x){
							
							selectedtree = trees[j];
							checkaritree =1;
							break;
						}
					}
				}
				if(checkaritree==0){
					System.out.println("ERRRRRR " );
					break;
				}
				
				Node selectednd_tr = selectedtree.nodes[0];//仮
				//すべてのtreeにselectedhpが１度ずつ登録?
				int checking[];
				checking = new int[trees.length];
				int checkingac=0;
				for(int j=0;j<trees.length;j++){
					checking[j]=0;
					for(int k=0;k<trees[j].nodes.length;k++){
						for(int l=0;l<trees[j].nodes[k].hp.length;l++){
							if(trees[j].nodes[k].hp[l]==selectedhp){
								checking[j]++;
							}
						}
					}

					if(checking[j]==0){
						System.out.println("個別treehaplo登録なしエラー");
					}
					if(checking[j]>1){
						System.out.println("個別treehaplo登録過剰エラー");
					}
					if(checking[j]==1){
						//System.out.println("個別treehaplo登録OK");
					}
					checkingac+=checking[j];
				}
				if(checkingac<trees.length){
					System.out.println("haplo登録なしエラー");
				}
				if(checkingac>trees.length){
					System.out.println("haplo登録過剰エラー");
				}
				if(checkingac==trees.length){
					System.out.println("haplo登録OK");
				}
				
				int checking2=0;
				for(int j=0;j<selectedtree.nodes.length;j++){
					
					for(int k=0;k<selectedtree.nodes[j].hp.length;k++){
						if(selectedtree.nodes[j].hp[k]==selectedhp){
							selectednd_tr=selectedtree.nodes[j];
							checking2++;
						}
					}
				}
				if(checking2<1){
					System.out.println("tree　ノードなし");
				}
				if(checking2==1){
					//System.out.println("tree node OK");
				}
				if(checking2>1){
					System.out.println("treeノード過剰");
				}
				
				
				Node nwnode_arg,nwnode_fr,nwnode_tr;
				double x_,y_,z_;
				x_=arg.nodes.length*10;
				y_=arg.nodes[choice].y+10;
				z_=arg.nodes[choice].z;
				
				nwnode_arg = new Node(lg.nd.length,arg,arg.nodes.length,x_,y_,z_,0);
				nwnode_arg.type=0;
				arg.addNode(arg,nwnode_arg);
				lg.addNode(nwnode_arg);
				
				nwnode_tr = new Node(lg.nd.length,selectedtree,selectedtree.nodes.length,x_,y_,z_,0);
				
				nwnode_tr.type=0;
				selectedtree.addNode(selectedtree,nwnode_tr);
				lg.addNode(nwnode_tr);
				
//				haplotype情報入力
				int tmphap2[];
				//tmphap2 = new int[numsnp];
				tmphap2 = new int[arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length+1];
				//for(int j=0;j<numsnp;j++){
				for(int j=0;j<arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length;j++){
					//tmphap2[j]=arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp[j];
					tmphap2[j]=arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp[j];
				}
				//if(cnt_mut<numsnp){
					//tmphap2[mutsequence[cnt_mut]]=1;
				//}
				tmphap2[arg.nodes[choice].hp[arg.nodes[choice].hp.length-1].hp.length]=mutsequence[cnt_mut];
				//ハプロタイプをログとイベントとノードに登録、ハプロタイプにイベントを登録
				
				tmphaplotype = new Haplotype(lg.hp.length,tmphap2,nwnode_arg,nwnode_tr);
				lg.addHaplotype(tmphaplotype);
				nwnode_arg.addHaplotype(tmphaplotype);
				nwnode_tr.addHaplotype(tmphaplotype);
				
//				Event 定義してログに登録
				int tmpsites[];
				tmpsites = new int[1];
				if(cnt_mut<numsnp){
					tmpsites[0] = mutsequence[cnt_mut];
				}
				tmpev = new Event(lg.ev.length,0,arg,forest,selectedtree,nwnode_arg,nwnode_tr,nwnode_tr,selectedhp,tmphaplotype,tmpsites);
				lg.addEvent(tmpev);
				
				
				//ノードにイベント登録
				nwnode_arg.addEvent(tmpev);
				nwnode_tr.addEvent(tmpev);
			
				tmpev.addHaplotype(tmphaplotype);
				tmphaplotype.addEvent(tmpev);
				
				Edge tmpedge_arg,tmpedge_fr,tmpedge_tr;
				int tmpedgenum=0;
				if(arg.edges == null){
					
				}else{
					tmpedgenum=arg.edges.length;
				}
				int edge_mut=-1;
				if(cnt_mut<numsnp){
					edge_mut=mutsequence[cnt_mut];
				}
				tmpedge_arg = new Edge(lg.ed.length,arg,arg.edges.length,arg.nodes[choice],nwnode_arg,edgetype,edge_mut);
//				グラフにエッジを登録
				
				arg.addEdge(arg,tmpedge_arg);
				lg.addEdge(tmpedge_arg);
				
				tmpedge_tr=new Edge(lg.ed.length,selectedtree,selectedtree.edges.length,selectednd_tr,nwnode_tr,edgetype,edge_mut);
				
				selectedtree.addEdge(selectedtree,tmpedge_tr);
				lg.addEdge(tmpedge_tr);
				
				//ログ、ノードにエッジを登録、エッジにイベントを登録、イベントにエッジを登録
				
				tmpedge_arg.addEvent(tmpev);
				tmpev.addEdge(tmpedge_arg);
				//nwnode_arg.addEdge(tmpedge_arg);
				//arg.nodes[choice].addEdge(tmpedge_arg);
				
				tmpedge_tr.addEvent(tmpev);
				tmpev.addEdge(tmpedge_tr);

				//nwnode_tr.addEdge(tmpedge_tr);
				//selectednd_tr.addEdge(tmpedge_tr);
				//selected tree以外のtreeには、ノード　エッジは増やさないが、Haplotypeを追加する
				//System.out.println("post mut arg \n");
				//arg.printGraphAllNodeEdge();
				for(int j=0;j<trees.length;j++){
					if(trees[j]==selectedtree){
					}else{
						for(int k=0;k<trees[j].nodes.length;k++){
							for(int l=0;l<trees[j].nodes[k].hp.length;l++){
								if(trees[j].nodes[k].hp[l]==selectedhp){
									trees[j].nodes[k].addHaplotype(tmphaplotype);
									tmphaplotype.addTreeNode(trees[j].nodes[k]);
									//System.out.println("非選択木への追加");
								}
							}
							
						}
					}
				}
				
				forest = new Graph(lg.gr.length);
				forest.range(0,numsnp-1,0,0,0,0);
				forest.type=1;
				
				for(int j=0;j<trees.length;j++){//Tree->Forest変換
					
					forest.addGraph(forest,trees[j]);
					
				}
				forest.type=1;//type forest
				for(int j=0;j<lg.hp.length;j++){
					lg.hp[j].forestnd = new Node[lg.hp[j].treend.length];
					for(int k=0;k<lg.hp[j].treend.length;k++){
						lg.hp[j].forestnd[k]=lg.hp[j].treend[k];
					}
				}
//				forest node edgeのグラフ内IDの付け直し
				forest.sortNode();
				forest.sortEdge();
				//forest.tree2forest();		
				//System.out.println("post mut forest \n");
				//forest.printGraphAllNodeEdge();
				for(int j=0;j<lg.hp.length;j++){
					String stt="haplotype " + j +"\n";
					stt += "arg node " + lg.hp[j].argnd.id+ "\n";
					for(int k=0;k<lg.hp[j].forestnd.length;k++){
						stt += "forest node " + k + "番" + lg.hp[j].forestnd[k].id + "\n";
					}
					for(int k=0;k<lg.hp[j].treend.length;k++){
						stt += "tree node " + k + "番" + lg.hp[j].treend[k].id + "\n";
					}
					//System.out.println(stt);
				}
				//
				/*
				System.out.println("<<<<<<<<<<<<<mut>>>");
				System.out.println("num nodes " + arg.nodes.length);
				System.out.println("num edges " + arg.edges.length);
				System.out.println("fr num nodes " + forest.nodes.length);
				System.out.println("fr num edges " + forest.edges.length);
				System.out.println("trees " + trees.length);
				for(int j=0;j<trees.length;j++){
					System.out.println("tree " + j);
					System.out.println("tr nodes " + trees[j].nodes.length);
					System.out.println("tr edges " + trees[j].edges.length);
				}
				*/
				//System.out.println("new mut on " + choice);
				
				if(cnt_mut<numsnp){
					
				}else{
					System.out.println("no mutation ");
				}
				//lg.printAll();
				
				cnt_mut++;
				if(cnt_mut>=numsnp){
					break;
				}
				
				
			}
			
		}
		
		//treesのsegmentによるソート
		MiscUtil ms2;
		ms2 = new MiscUtil();
		trees = ms2.sortTreesByOr_x(trees);
		
		
		
		//forestの座標変え
		forest = new Graph(lg.gr.length);
		forest.range(0,numsnp-1,0,0,0,0);
		forest.type=1;
		//Forestをvineでつむぐために、treeとforestのNodeを同一にする
		//位置置換をtreeでしなければよい
		for(int j=0;j<trees.length;j++){//Tree->Forest変換
			//座標変換
			Graph tmp;
			//tmp = new Graph(lg.gr.length);
			//tmp.copy(lg,tmp,trees[j]);
			//tmp.circle();
			trees[j].circle();
			//forest.addGraph(forest,tmp);
			forest.addGraph(forest,trees[j]);
			//forest.addGraph(forest,trees[j]);
			
		}
		forest.type=1;//type forest
		//forest.tree2forest();
		
//		Haplotype vineの投入
		Graph vineFr;
		vineFr = new Graph(lg.gr.length);
		lg.addGraph(vineFr);
		vineFr.copy(lg,vineFr,forest);
		
		
		for(int i=0;i<lg.hp.length;i++){
			Haplotype selectedhp=lg.hp[i];
			Node tmp[];
			tmp = new Node[trees.length];
			for(int j=0;j<trees.length;j++){
			
				for(int k=0;k<trees[j].nodes.length;k++){
					for(int l=0;l<trees[j].nodes[k].hp.length;l++){
						if(trees[j].nodes[k].hp[l]==selectedhp){
							//trees[j].nodes[k].addHaplotype(tmphaplotype);
							//tmphaplotype.addTreeNode(trees[j].nodes[k]);
							//System.out.println("非選択木への追加");
							tmp[j]=trees[j].nodes[k];
						}
					}
					
				}
			
			}
			for(int j=0;j<tmp.length-1;j++){
				Edge tmpe;
				tmpe = new Edge(lg.ed.length,forest,forest.edges.length,tmp[j],tmp[j+1],-5);
				lg.addEdge(tmpe);
				//System.out.println("Forest nodes " + forest.nodes.length);
				//System.out.println("tree num " + trees.length);
				forest.addEdge(forest,tmpe);
			}
		}
		//座標変換
		//circle
		
		arg.circle();
		
		//座標変換scale
		double scale=1000;
		arg.scale(scale/numsnp,scale/numsnp,scale/numsnp);
		forest.scale(scale/numsnp,scale/numsnp,scale/numsnp);
		vineFr.scale(scale/numsnp,scale/numsnp,scale/numsnp);
		//for(int i=0;i<trees.length;i++){
			//trees[i].scale(scale/numsnp,scale/numsnp,scale/numsnp);
		//}
		//座標変換 shift
		double x=-500;
		arg.shift(-500,0,0);
		forest.shift(-500,0,0);
		vineFr.shift(-500,0,0);
		//for(int i=0;i<trees.length;i++){
			//trees[i].shift(-500,0,0);
		//}
		//arg.trans1();
		//arg.outVGJ_noLabel(outpajek2);
		//arg.rmEdgeAndGlue(lg,arg,arg.edges[3]);
		//arg.outVGJ_noLabel(outarg);
		//forest.outVGJ_noLabel2(outfr_vine);
		//vineFr.outVGJ_noLabel2(outforest);
		
		for(int i=0;i<trees.length;i++){
			outtrees_sf = outtrees + i +".txt";
			//trees[i].outVGJ_noLabel(outtrees_sf);
		}
		//arg.outpaths();
		String checkout;
		//lg.printAll();
		lg.printEventLess();
		lg.outEventLess(outevent);
		for(int i=0;i<lg.gt.length;i++){
			//lg.gt[i].printAll();
		}
//		ForestのTree座標置換
		//length,angle,factor,turn
		/*
		double width,height,rx,ry;
		width=Math.abs(arg.or_x-arg.len_x+1);
		height = width/arg.nodes.length;
		rx =1;
		ry = 0.95;
		arg.plotTree(width,height,rx,ry);
		*/
		double ratio=0.97;
		double angle = 0.05;
		arg.plotTreeHPinfinite(ratio,angle);
		forest = new Graph(lg.gr.length);
		forest.range(0,numsnp-1,0,0,0,0);
		forest.type=1;
		//Forestをvineでつむぐために、treeとforestのNodeを同一にする
		//位置置換をtreeでしなければよい
		for(int j=0;j<trees.length;j++){//Tree->Forest変換
			//座標変換
			Graph tmp;
			//tmp = new Graph(lg.gr.length);
			//tmp.copy(lg,tmp,trees[j]);
			//tmp.circle();
			trees[j].sortRooted();
			trees[j].plotTreeHP();
			//forest.addGraph(forest,tmp);
			forest.addGraph(forest,trees[j]);
			//forest.addGraph(forest,trees[j]);
			
		}
		forest.type=1;//type forest
		//arg.shift((arg.or_x+arg.len_x-1)/2,0,0);
		forest.scale(scale/numsnp,scale/numsnp,scale/numsnp);
		//forest.outVGJ_noLabel2(outforestTr);
		//arg.outVGJ_noLabel2(outforestTr);
		
		//boolean cn = arg.connectionCheck();
		//System.out.println("conn " + cn);
		//System.out.println(stry3);
		/*
		for(int i=0;i<trees.length;i++){
			System.out.println("tree id "+ i + "range from " + trees[i].or_x + " to " + trees[i].len_x );
			for(int j=0;j<trees[i].edges.length;j++){
				System.out.println("edge " + j + " " + trees[i].edges[j].label);
			}
		}
		*/
		System.out.println("num trees " + trees.length);
		boolean treecheck;
		treecheck=arg.TreeChecker();
		System.out.println("arg = tree? " + treecheck);
		treecheck=forest.TreeChecker();
		System.out.println("forest =  tree? " + treecheck);
		
		return lg;
	}
}
