/*
 * 作成日: 2005/08/02
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;
import java.io.IOException;
import java.io.StringBufferInputStream;

import auburn.VGJ.*;
import auburn.VGJ.algorithm.GraphUpdate;
import auburn.VGJ.algorithm.cartegw.BiconnectGraph;
import auburn.VGJ.algorithm.cgd.CGDAlgorithm;
import auburn.VGJ.algorithm.shawn.Spring;
import auburn.VGJ.algorithm.tree.TreeAlgorithm;
import auburn.VGJ.examplealg.ExampleAlg2;
import auburn.VGJ.graph.GMLobject;
import auburn.VGJ.graph.Graph;
import auburn.VGJ.graph.ParseError;
import auburn.VGJ.gui.MessageDialog;
import auburn.VGJ.gui.GraphWindow;
import auburn.VGJ.graph.*;

import tAvEnter.*;
/**
 * @author yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class PostSimRetro1 {

//	public static void main(String[] args) {
	public static void main(String file_){
		Log lg_sim;
		//String st[];
		//st = new String[0];
		int numsnp = 50;//SNPの数、配列長にも相当
		int iteration =50;//mutation + recombinationの回数
		int num_iteration=0;
		double recratio = 0.2;//recombination - mutation の分配確率　閾値未満はrec
		
		lg_sim = simulation3_Retro.main(numsnp,iteration,num_iteration,recratio);
		System.out.println("PostSimRetro1");
		lg_sim.printAll();
		
		int max_iter = 1000;//retrograde iterationの回数
		
		int backrecdepth = 100;//backRecNoParentの深さ
		
		/*
		 * 
		 */
//		output setting C:\pajek\Pajek\Data
		String rootdir = "C:\\pajek\\Pajek\\Data\\ry2";
		String outarg = rootdir + "\\out_arg.txt";
		String outforest = rootdir + "\\out_forest.txt";
		String outforestTr = rootdir + "\\out_forestTr.txt";
		String outfr_vine = rootdir + "\\out_fr_vine.txt";
		String outtrees = rootdir + "\\out_trees_";
		String outtrees_sf ="";
		String outpajek2 = rootdir + "\\out2.txt";
		String outevent = rootdir + "\\event.txt";
		/*
		 * ここでlgが持つハプロタイプのセットは、途中でハプロタイプがdrift outしていない
		 * 実際にretrogradeにARGを再構築するためには、drift outして欠落のあるハプロタイプセットからスタートする必要がある
		 */
		
		/*
		 * 全ハプロタイプのうち、あるSNPのアレルが１対その他の場合には、そのマイナーアレルはそのハプロタイプに生じたものとし、
		 * そのアレルを反転させたハプロタイプの子とする
		 * 親ハプロタイプがなければ、登録する
		 */
		Log lg;
		lg = new Log();//retrogradeを記録するためのLog
		Event tmpev;
		Haplotype tmphaplotype;
		Node tmpnd;
		Edge tmped;
		treeANDvine.Graph tmpgr;
		//graph setting
		int edgetype =1;//edgeは有向
		
		
		
		lg.numsnp=numsnp;
		lg.iteration=iteration;
		lg.num_iteration = num_iteration;
		lg.recratio = recratio;
		lg.backrecdepth = backrecdepth;
		
		//retrogradeにグラフの世代ごとに記録する
		treeANDvine.Graph tmpgr2[];
		tmpgr2 = new treeANDvine.Graph[2];
		//すべての経緯の総合図を作るためのグラフがgrall tmparr[1]
		treeANDvine.Graph grall;
		grall = new treeANDvine.Graph(lg.gr.length);
		lg.addGraph(grall);
		
		
		//はじめはハプロタイプをエッジなしのノードとして登録する
		
		tmpgr=new treeANDvine.Graph(lg.gr.length);//tmparr[0]
		tmpgr.range(0,numsnp,0,0,0,0);
		lg.addGraph(tmpgr);
		treeANDvine.Graph tmpgr_2;
		tmpgr_2 =new treeANDvine.Graph(lg.gr.length);
		tmpgr_2.range(0,numsnp,0,0,0,0);
		lg.addGraph(tmpgr_2);

	/* 20050822 yoshizumi*/ 
		//Haplotype specifiedHp[];
		//specifiedHp = new Haplotype[lg_sim.hp.length];
		//for(int i=0;i<lg_sim.hp.length;i++){
		//	specifiedHp[i]=lg_sim.hp[i];
		//}
	
		int specifiedHp[][];	
		//String file = "/home/zumiaki/java/RetroGradetHP3toROOTnoFileAug22/tAvEnter/poolhaplotype";
		//String file = args[0];
		String file = file_;
		System.out.println(file);
		tAvEnter.PoolhapRead phread= new tAvEnter.PoolhapRead();	
		specifiedHp = new int[0][0];
		try{
			specifiedHp = phread.poolhap(file);
		}catch(IOException e){
			System.out.println(e);
		}

		/*System.out.println("Check##############################################");
		for(int i=0;i<specifiedHp.length;i++){
			System.out.println(i+"banme");
			for(int j=0; j<specifiedHp[i].length;j++){
				System.out.print(specifiedHp[i][j]+"; ");
			}
			System.out.println("");
		}*/

		//System.out.println("pre function " +specifiedHp.length);
		//specifiedHp = MiscUtil.specifyHpList(specifiedHp);
		//System.out.println("post function " +specifiedHp.length);
		
		for(int i=0;i<specifiedHp.length;i++){
			//tmphaplotype=new Haplotype(lg.hp.length,specifiedHp[i].hp);
			tmphaplotype=new Haplotype(lg.hp.length,specifiedHp[i]);
			lg.addHaplotype(tmphaplotype);
			int st[]={0};
			int end[]={lg.numsnp-1};
			tmphaplotype.addStEnd(st,end);
			tmpnd = new Node(lg.nd.length,tmpgr,tmpgr.nodes.length);
			tmphaplotype.addForestNode(tmpnd);
			tmpnd.addHaplotype(tmphaplotype);
			//int numsnp = tmpnd.hp[0].hp.length;
			//System.out.println("!!!!!!!!!!!numsnp " + numsnp);
			//lg.addHaplotype(tmphaplotype);
			lg.addNode(tmpnd);
			tmpgr.addNode(tmpgr,tmpnd);
			
			Node tmpnd2;
			tmpnd2 = new Node(lg.nd.length,tmpgr_2,tmpgr_2.nodes.length);
			tmphaplotype.addArgNode(tmpnd2);
			tmpnd2.addHaplotype(tmphaplotype);
			//int numsnp = tmpnd.hp[0].hp.length;
			//System.out.println("!!!!!!!!!!!numsnp " + numsnp);
			//lg.addHaplotype(tmphaplotype);
			lg.addNode(tmpnd2);
			tmpgr_2.addNode(tmpgr_2,tmpnd2);
			
		}
		/*
		//同一ハプロタイプのノードを１つに
		System.out.println("pre$$$$$$$$$$$$$ " + tmpgr.nodes.length);
		RetroProcess.removeVertexSameHp(lg,tmpgr);
		RetroProcess.removeVertexSameHp(lg,tmpgr_2);
		System.out.println("post$$$$$$$$$$$$$ " + tmpgr.nodes.length);
		/*
		//grhist=MiscUtil.addGraphArr(grhist, tmpgr);
		//tmpgr.copy(lg,grall,tmpgr);
		//grall.range(0,numsnp,0,0,0,0);
		/*
		for(int i=0;i<tmpgr.nodes.length;i++){
			tmpgr.nodes[i].hp[0].addArgNode(grall.nodes[i]);
			
			//System.out.println("node's edge len " + tmpgr.nodes[i].edge.length);
		}
		*/
		//grhist=tmpgr.addToGraphArr(grhist,tmpgr);
		//System.out.println("Haplotypeセット後" + grhist.length);
		
		//grhist[0].printGraphAllNodeEdge();
		tmpgr2[0]=tmpgr;
		
		//tmpgr2[1]=grall;
		tmpgr2[1]=tmpgr_2;
		
		System.out.println("kkkkkkkkkkkkkkkkkkk");
		//lg.printAll();
		//tmpgr2[0].printGraphAll();
		//lg.printAll();
		//tmpgr2[1].printGraphAll();
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwww");
		boolean loop = false;
		//loop = tmpgr2[1].judgeARGComplete();
		for(int i=0;i<tmpgr2[1].nodes.length;i++){
			tmpgr2[1].nodes[i].hp[0].printHaplotypeElem();
		}
		loop = tmpgr2[1].connectedGraph();
		int cnt_iter=0;
		int onemore=0;
		while(loop==false){
			
			
			tmpgr=new treeANDvine.Graph(lg.gr.length);
			tmpgr.range(0,numsnp,0,0,0,0);
			lg.addGraph(tmpgr);
			tmpgr2 = tmpgr2[0].retroUp(lg,tmpgr2[0],tmpgr2[1]);
			System.out.println("iteration num " + cnt_iter + "loop " + loop);
			//tmpgr2[1].printGraphAllNodeEdge();
			//tmpgr2[0].printGraphAllNodeEdge();
			tmpgr2[1].circle();
			tmpgr2[0].circle();
			
				outtrees_sf = outtrees + cnt_iter + "_0" + ".txt";//"xxx0_0.txt"ファイルは初期ノードより１ノード減じたグラフ
				//tmpgr2[0].outVGJ_noLabel(outtrees_sf);
				outtrees_sf = outtrees + cnt_iter + "_1" + ".txt";//初めのBackがBackMutだった場合、"xxx0_1.txt"ファイルは初期ノードと同数のノードと１エッジのグラフ
				//tmpgr2[1].outVGJ_noLabel(outtrees_sf);
			//grhist=tmpgr.addToGraphArr(grhist,tmpgr);
			//System.out.println("grhistの長さ　"+ grhist.length);
			//loop = tmpgr2[1].judgeARGComplete();//今のところ判断はせず必ずfalseで返しているAug13-2005
			//connectedになってもrecombinationの親がrecでつながっているだけでは、ARGは完成しない
				//loop = tmpgr2[1].connectedGraph();//今のところ判断はせず必ずfalseで返しているAug13-2005
			
			if(onemore==0){
				if(tmpgr2[0].nodes.length==1){
					max_iter=cnt_iter+1;
					onemore=1;
				}
			}
			
			
			
			
			cnt_iter++;//収束まで回し続けるときは、ここをコメントアウト
			if(cnt_iter>=max_iter){
				loop=true;
			}
		}
		
		lg.printAll();
		//lg.printRetroMR();
		lg.printRetroHp();
		boolean connection = tmpgr2[1].connectedGraph();
		System.out.println("final ARG is connected? " + connection);
		Node rootNd[] = tmpgr2[1].listRootNode();
		String st = "root node num " + rootNd.length + "\n";
		for(int i=0;i<rootNd.length;i++){
			st += rootNd[i] +" ";
			
		}
		System.out.println(st);
		
//		GMLlexer
		String text=tmpgr2[1].outVGJtoSt();
		text = TaVtoGML.outVGJtoSt2(tmpgr2[1],2,1,4,0,2,0,1,lg.numsnp);
//		ウィンドウ
        TaVtoGML.String2GraphWindow(text);
		/*
		//String text = textarea_.getText();
        StringBufferInputStream stream = new StringBufferInputStream(text);
        GMLlexer gmlLex = new GMLlexer(stream);
		//GMLlexer gmlLex=new GMLlexer(lexer);
        
        auburn.VGJ.graph.Graph graph_ = new auburn.VGJ.graph.Graph();
        boolean update = true;
        GraphUpdate update_;
        auburn.VGJ.graph.Graph newgraph = null;
        try
        {
           GMLobject GMLgraph = new GMLobject(gmlLex, null);
           GMLobject GMLtmp;
        
           // If the GML doesn't contain a graph, assume it is a graph.
           GMLtmp = GMLgraph.getGMLSubObject("graph", GMLobject.GMLlist, false);
           if(GMLtmp != null)
              GMLgraph = GMLtmp;
           newgraph = new Graph(GMLgraph);
        
           graph_.copy(newgraph);
           //update_.update(true);
        }
        
        
           catch(ParseError error)
           {
           */
           	/*
              MessageDialog dg = new MessageDialog(this,
                                                  "Error", error.getMessage() +
                                                  " at line " + lexer.getLineNumber() + " at or near \""
                                                  + lexer.getStringval() + "\".", true);
              return true;
              */
		/*
           }
           catch(IOException error)
           {
           */
           	/*
              MessageDialog dg = new MessageDialog(this,
                                                  "Error", error.getMessage(), true);
              return true;
              */
		/*
           }
        
        
        
        //GraphWindowを開く
        
        //GraphWindow gw=new GraphWindow(newgraph);
           GraphWindow gw;
           gw = buildWindow_(newgraph);
        gw.pack();
        gw.show();
        */
        
	}

	private static GraphWindow buildWindow_(Graph gr_)
    {
		int appCount_ = 0;  	
		// Bring up an undirected graph editor window.
          
          	// The parameter to GraphWindow() indicates directed
          	// or undirected.
             //GraphWindow graph_editing_window = new GraphWindow(true);
		GraphWindow graph_editing_window = new GraphWindow(gr_);
          	// Here the algorithms are added.
             graph_editing_window.addAlgorithmMenu("Tree");
          
             ExampleAlg2 alg2 = new ExampleAlg2();
             graph_editing_window.addAlgorithm(alg2, "Random");
          
             TreeAlgorithm talg = new TreeAlgorithm('d');
             graph_editing_window.addAlgorithm(talg, "Tree", "Tree Down");
          
             talg = new TreeAlgorithm('u');
             graph_editing_window.addAlgorithm(talg, "Tree", "Tree Up");
          
             talg = new TreeAlgorithm('l');
             graph_editing_window.addAlgorithm(talg, "Tree", "Tree Left");
          
             talg = new TreeAlgorithm('r');
             graph_editing_window.addAlgorithm(talg, "Tree", "Tree Right");
          
          
             graph_editing_window.addAlgorithmMenu("CGD");
          
             CGDAlgorithm calg = new CGDAlgorithm();
             graph_editing_window.addAlgorithm(calg, "CGD", "CGD");
          
             calg = new CGDAlgorithm(true);
             graph_editing_window.addAlgorithm(calg, "CGD",
                "show CGD parse tree");
          
             Spring spring = new Spring();
             graph_editing_window.addAlgorithm(spring, "Spring");
          
             graph_editing_window.addAlgorithmMenu("Biconnectivity");
             BiconnectGraph make_biconnect = new BiconnectGraph(true);
             graph_editing_window.addAlgorithm (make_biconnect, 
                "Biconnectivity", "Remove Articulation Points");
             BiconnectGraph check_biconnect = new BiconnectGraph(false);
             graph_editing_window.addAlgorithm (check_biconnect, 
                "Biconnectivity", "Find Articulation Points");
          
             if (appCount_++ == 0)
                graph_editing_window.setTitle("VGJ v1.03");
             else
                graph_editing_window.setTitle("VGJ v1.03" + ": "
                   + appCount_);
          
             graph_editing_window.pack();
             graph_editing_window.show();

             return graph_editing_window;
    }

}
