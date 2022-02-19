/*
 * 作成日: 2005/08/21
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;

import auburn.VGJ.algorithm.GraphUpdate;
import auburn.VGJ.algorithm.cartegw.BiconnectGraph;
import auburn.VGJ.algorithm.cgd.CGDAlgorithm;
import auburn.VGJ.algorithm.shawn.Spring;
import auburn.VGJ.algorithm.tree.TreeAlgorithm;
import auburn.VGJ.examplealg.ExampleAlg2;
import auburn.VGJ.graph.GMLlexer;
import auburn.VGJ.graph.GMLobject;
import auburn.VGJ.graph.Graph;
import auburn.VGJ.graph.ParseError;
import auburn.VGJ.gui.GraphWindow;

/**
 * @author Ryo Yamada
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class TaVtoGML {
	/*
	 * １　VaTのGraphオブジェクトから、GML書式の文字列を作成する
	 * 　　　　色わけやらオプションにより分ける
	 * ２　GML書式の文字列からauburn.VGJ.Graphオブジェクトを作る
	 * ３　新しいウィンドウで表示する
	 * @author Ryo Yamada
	 *
	 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
	 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
	 */ 
	/*
	 * GML Stringの作成メニュー
	 * ノードの色分け
	 * 	mutantの色、recombinantの色、rootの色
	 * ノードのラベル
	 *  なし、ハプロタイプ型、ハプロタイプID
	 * エッジのタイプ
	 *  mutation、recombination
	 * エッジの表示
	 *  変異位置、組換え位置
	 * エッジの経過点
	 */
	/*
	 * defaultの出力
	 */
	public String outVGJtoSt(treeANDvine.Graph gr){
		
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			//bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(gr.nodes == null){
				
			}else{
				numbernode = gr.nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "node [ \n id " 
					+ gr.nodes[i].id + "\n" 
					+ "label " + "\"" + gr.nodes[i].name + "\""
					+ "graphics [ center [ x " + gr.nodes[i].x + " "
					+ "y " + gr.nodes[i].y + " "
					+ "z " + gr.nodes[i].z + "]"
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
			if(gr.edges == null){
				
			}else{
				numberedge = gr.edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = gr.edges[i].start.id ;
				int outendid = gr.edges[i].end.id ;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				//out += outstid + " " + outendid + " " + "1";
				out += "edge [\nlinestyle \"solid\"\nlabel \"" + gr.edges[i].label + "\""
				     + "source " + gr.edges[i].start.id
					 + "target " + gr.edges[i].end.id + "]";
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
	/*
	 * 引数により、GML表示を制御
	 * NdLb : 0はデフォルト、1はhaplotype 型、2はハプロタイプID、3はハプロタイプ型(0001010)
	 * EdLb : 0はデフォルト、1はmutation/recombination位置
	 * rootCol,mutCol,recCol : 0 blue,1 brown,2 pink,3 green,4 yellow
	 * mutEdcol, recEdcol : 0 black,1 blue, 2 green, 3 orange
	 */
	public static String outVGJtoSt2(treeANDvine.Graph gr,int NdLb,int EdLb,int 
			rootCol,int mutCol,int recCol,int mutEdcol,int recEdcol,int len){
		
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			//bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(gr.nodes == null){
				
			}else{
				numbernode = gr.nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				//nodeの判定
				int nodetype = nodeType(gr.nodes[i]);
				
				String haplotypekata="";
				int hapkata01[];
				hapkata01 = new int[len];
				for(int j=0;j<len;j++){
					hapkata01[j]=0;
				}
				for(int j=0;j<gr.nodes[i].hp[0].hp.length;j++){
					haplotypekata+=gr.nodes[i].hp[0].hp[j] + " ";
					hapkata01[gr.nodes[i].hp[0].hp[j]]=1;
				}
				String haplotypekata01="";
				for(int j=0;j<len;j++){
					haplotypekata01+=hapkata01[j];
				}
				out += "node [ \n id " 
					+ gr.nodes[i].id + "\n" ;
					if(NdLb==0){
						out += "label " + "\"" + gr.nodes[i].name + "\"";
					}else if(NdLb==1){
						out += "label " + "\"" + haplotypekata + "\"";
					}else if(NdLb==2){
						out += "label " + "\"" + gr.nodes[i].hp[0].id + "\"";
					}else if(NdLb==3){
						out += "label " + "\""+ haplotypekata01 + "\"";
						
					}
					
				out += "graphics [ ";
				
				//label
				String label=" Image [ Type \"File\" Location \"images\\ball_";
				int color=0;
				if(nodetype==0){
					color = rootCol;
				}else if(nodetype==1){
					color = mutCol;
				}else{
					color = recCol;
				}
				String color_st="";
				if(color==0){
					color_st = "blue";
				}else if(color==1){
					color_st = "brown";
				}else if(color==2){
					color_st = "pink";
				}else if(color==3){
					color_st = "green";
				}else if(color==4){
					color_st = "yellow";
				}
				label += color_st;
				label += ".gif\"]";
				
				out += label;
				//System.out.println("label \n" + label);
				double size = len/50;
				String sizest ="";
				sizest+= size;
				out += "  center [ x " + gr.nodes[i].x + " "
					+ "y " + gr.nodes[i].y + " "
					+ "z " + gr.nodes[i].z + "]"
					+ "width " + size + " height " + size + " depth " + size  +" ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ]";
				
				out += "]";
				
			}
			
			int numberedge =0;
			if(gr.edges == null){
				
			}else{
				numberedge = gr.edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = gr.edges[i].start.id ;
				int outendid = gr.edges[i].end.id ;
				int nodetype=nodeType(gr.edges[i].end);
				
				out += "edge [\nlinestyle \"";
				int color=0;
				if(nodetype==0){
					color = mutEdcol;
				}else if(nodetype==1){
					color = mutEdcol;
				}else{
					color = recEdcol;
				}
				String color_st="";
				if(color==0){
					color_st = "solid";
				}else if(color==1){
					color_st = "dashed";
				}else if(color==2){
					color_st = "dotted";
				}else if(color==3){
					color_st = "dashdot";
				}
				out += color_st;
				
				out += "\"\nlabel \""; 
				
				String edlb="";
				if(EdLb==0){
					edlb+=gr.edges[i].type;
				}else if(EdLb==1){
					if(nodetype==0){
						edlb+=gr.edges[i].label;
					}else if(nodetype==1){
						edlb+=gr.edges[i].label;
					}else if(nodetype==2){
						edlb+=gr.edges[i].rec1 + " " + gr.edges[i].rec2;
					}
					
				}
				out += edlb;
				out += "\"";
				
				out += "source " + gr.edges[i].start.id
					 + "target " + gr.edges[i].end.id + "]";
				
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
	
	/*
	 * String を与え、GML, aubrun.VGJ.Graph を作成、GraphWindowに表示
	 */
	public static void String2GraphWindow(String st){
//		GMLlexer
		String text=st;
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
           	
           }
           catch(IOException error)
           {
           	
           }
        
        
        
        //GraphWindowを開く
        
        //GraphWindow gw=new GraphWindow(newgraph);
           GraphWindow gw;
           gw = buildWindow_(newgraph);
        gw.pack();
        gw.show();
	}
	/*
	 * auburn.VGJ.Graphを与え、GraphWindowに表示
	 */
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

	/*
	 * treeANDvineのノードのタイプを調べる
	 * @author Ryo Yamada
	 */
	private static int nodeType(treeANDvine.Node nd){
		int nodetype=0;//0 root,1 mutant, 2 recombinant
		int ednum = nd.edge.length;
		if(ednum==0){
			//root
			nodetype=0;
		}else{
			int inednum=0;
			for(int j=0;j<ednum;j++){
				if(nd.edge[j].end == nd){
					inednum++;
				}
			}
			if(inednum==0){
				nodetype=0;
			}else if(inednum==1){
				nodetype=1;
			}else{
				nodetype=2;
			}
		}
		return nodetype;
	}
}
