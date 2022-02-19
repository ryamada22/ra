/*
 * 作成日: 2005/07/24
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
public class RunTest {

	public static void main(String[] args) {
		Node n0,n1,n2,n3,n4,n5;
		Edge e0,e1,e2,e3,e4,e5,e6;
		Graph g0,g1;
		Log lg_;
		lg_=new Log();
		
		g0 = new Graph(0);
		g1 = new Graph(1);
		n0 = new Node(0,g0,0,0.0,0.0,0.0,0);
		n1 = new Node(1,g0,1,1,1,1,0);
		n2 = new Node(2,g0,2,2,3,4,0);
		n3 = new Node(3,g0,3,3,6,9,0);
		n4 = new Node(4,g0,4,4,10,16,0);
		n5 = new Node(5,g0,5,10,10,10,0);
		
		e0=new Edge(0,g0,0,n0,n1,0,0);
		e1=new Edge(1,g0,1,n0,n2,0,0);
		e2=new Edge(2,g0,2,n2,n3,0,0);
		e3=new Edge(3,g0,3,n1,n3,0,0);
		e4=new Edge(4,g0,4,n1,n4,0,0);
		e5=new Edge(5,g0,5,n2,n4,0,0);
		e6=new Edge(6,g0,6,n2,n0,0,0);
		lg_.addGraph(g0);
		lg_.addGraph(g1);
		lg_.addNode(n0);
		lg_.addNode(n1);
		lg_.addNode(n2);
		lg_.addNode(n3);
		lg_.addNode(n4);
		lg_.addNode(n5);
		lg_.addEdge(e0);
		lg_.addEdge(e1);
		lg_.addEdge(e2);
		lg_.addEdge(e3);
		lg_.addEdge(e4);
		lg_.addEdge(e5);
		lg_.addEdge(e6);
		
		g0.addNode(g0,n0);
		g0.addNode(g0,n1);
		g0.addNode(g0,n2);
		g0.addNode(g0,n3);
		g0.addNode(g0,n4);
		
		g0.addEdge(g0,e0);
		g0.addEdge(g0,e1);
		g0.addEdge(g0,e2);
		g0.addEdge(g0,e3);
		g0.addEdge(g0,e4);
		g0.addEdge(g0,e5);
		g0.addEdge(g0,e6);
		
		g0.printGraphAllNodeEdge();
		g0.removeEdgeAndGlue(lg_,g0,e4);
		
		/*
		 * 
		 g0.printGraphAllNodeEdge();
		g0.addNode(g0,n5);
		g0.printGraphAllNodeEdge();
		g0.changeNode(g0,n5,n1);
		g0.printGraphAllNodeEdge();
		g0.changeNode(g0,n5,n4);
		g0.printGraphAllNodeEdge();
		g0.takeawayNode(g0,n1);	
		g0.printGraphAllNodeEdge();
		g0.takeawayNode(g0,n4);	
		g0.printGraphAllNodeEdge();
		*/
	}
}
