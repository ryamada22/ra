/*
 * 作成日: 2005/07/30
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
public class simulationRetro {

	public static void main(String[] args) {
//		実行ログ
		Log lg;
		lg=new Log();
		Event tmpev;
		Haplotype tmphaplotype;
		Node tmpnd;
		Edge tmped;
		Graph tmpgr;
		Path tmppath;
		
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
		
//haplotypesを発生
		int numsnp=6;
		int numhap=50;
		
		for(int i=0;i<numhap;i++){
			int tmphp[];
			tmphp = new int[numsnp];
			double maf = 0.5;
			for(int j=0;j<tmphp.length;j++){
				tmphp[j]=(int)(Math.random()+maf);
			}
			tmphaplotype = new Haplotype(lg.hp.length,tmphp);
			lg.addHaplotype(tmphaplotype);
		}
		
		
		for(int i=0;i<lg.hp.length;i++){
			for(int j=i+1;j<lg.hp.length;j++){
				tmppath = new Path(lg.pt.length,lg.hp[i],lg.hp[j]);
				lg.addPath(tmppath);
			}
		}
		//すべてのhaplotypeにノードを、すべてのhaplotypeペア間にエッジを張る
		Graph arg, forest,tmptree;
		
		arg = new Graph(lg.gr.length);
		arg.range(0,numsnp-1,0,0,0,0);
		
		arg.type=0;//ARG type
		lg.addGraph(arg);
		for(int i=0;i<lg.hp.length;i++){
			tmpnd = new Node(lg.nd.length,arg,arg.nodes.length);
			arg.addNode(arg,tmpnd);
			lg.addNode(tmpnd);
			tmpnd.addHaplotype(lg.hp[i]);
			lg.hp[i].addArgNode(tmpnd);
		}
		for(int i=0;i<lg.pt.length;i++){
			tmped = new Edge(lg.ed.length,arg,arg.edges.length,lg.pt[i].hap1.argnd,lg.pt[i].hap2.argnd,0);
			
			tmped.addPath(lg.pt[i]);
			arg.plusEdge(arg,tmped);
			lg.addEdge(tmped);
			lg.pt[i].hap1.argnd.addEdge(tmped);
			lg.pt[i].hap2.argnd.addEdge(tmped);
			
		}
		
		
		//
		lg.printAll();
		//arg.printGraphAllNodeEdge();
		//arg.outVGJ_noLabel2(outarg);
		
		//unityVertex
		tmpgr = new Graph(lg.gr.length);
		lg.addGraph(tmpgr);
		tmpgr.copy(lg,tmpgr,arg);
		//RetroProcess.unityVertex(lg,arg);
		tmpgr.printGraphAllNodeEdge();
		//同一ハプロタイプ型を持つノードを融合する
		RetroProcess.unityVertex(lg,tmpgr);
		/*
		 * unityVertexの結果、作成された同一ノードペア間の複数のエッジについて、
		 * そのパス要素が同一であれば、１本化する
		 */
		RetroProcess.unityEdge(lg,tmpgr);
		
		for(int i=0;i<lg.gr.length;i++){
			outtrees_sf = outtrees + i +".txt";
			lg.gr[i].circle();
			lg.gr[i].outVGJ_noLabel(outtrees_sf);
			lg.gr[i].printGraphAllNodeEdge();
		}
		//arg.printGraphAllNodeEdge();
	}
}
