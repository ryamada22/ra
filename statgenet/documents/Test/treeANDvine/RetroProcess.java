/*
 * 作成日: 2005/07/31
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
public class RetroProcess {
	public static void unityEdge(Log lg,Graph gr){
		/*
		 * unityVertexの結果、作成された同一ノードペア間の複数のエッジについて、
		 * そのパス要素が同一であれば、１本化する
		 */
		int looper=0;
		while(looper==0){
			looper=1;
			for(int i=0;i<gr.edges.length;i++){
				
				for(int j=i+1;j<gr.edges.length;j++){
					Edge tmped=gr.edges[j];
					int counter=0;
					if(gr.edges[i].start == gr.edges[j].start){
						counter++;
					}else if(gr.edges[i].start == gr.edges[j].end){
						counter++;
					}
					if(gr.edges[i].end == gr.edges[j].start){
						counter++;
					}else if(gr.edges[i].end == gr.edges[j].end){
						counter++;
					}
					if(counter==2){
						looper=0;
						break;
					}
				}
			}
			if(looper==0){
				for(int i=0;i<gr.edges.length;i++){
					
					for(int j=i+1;j<gr.edges.length;j++){
						Edge tmped=gr.edges[j];
						int counter=0;
						if(gr.edges[i].start == gr.edges[j].start){
							counter++;
						}else if(gr.edges[i].start == gr.edges[j].end){
							counter++;
						}
						if(gr.edges[i].end == gr.edges[j].start){
							counter++;
						}else if(gr.edges[i].end == gr.edges[j].end){
							counter++;
						}
						if(counter==2){
							Node tmpst = gr.edges[j].start;
							Node tmpend = gr.edges[j].end;
							boolean ptidentity;
							gr.edges[i].pt.printPathAll();
							gr.edges[j].pt.printPathAll();
							ptidentity = gr.edges[i].pt.comparePath(gr.edges[j].pt);
							if(ptidentity == true){
								gr.takeawayEdge(gr,tmped);
								tmpst.takeawayEdge(tmped);
								tmpend.takeawayEdge(tmped);
							}
						}
					}
				}	
			}
		}
		
	}
	public static void unityVertex(Log lg,Graph gr){
		//Edgeの両端のハプロタイプの一致を確認し、
		//同一ならエッジを取り去る
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr.edges.length;i++){
				int samehp=1;
				Node st = gr.edges[i].start;
				Node end = gr.edges[i].end;
				if(st.hp[0].hp.length == end.hp[0].hp.length){
					samehp=0;
					for(int k=0;k<st.hp[0].hp.length;k++){
						
						if(st.hp[0].hp[k]==end.hp[0].hp[k]){
						}else{
							samehp=1;	
						}
					}
				}else{
					samehp=1;
				}
				
				if(samehp==0){
					looper =0;
					break;
				}
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr.nodes.length];
				tmpedges = new Edge[gr.edges.length];
				
				for(int i=0;i<gr.nodes.length;i++){
					tmpnodes[i]=gr.nodes[i];
				}
				for(int i=0;i<gr.edges.length;i++){
					tmpedges[i]=gr.edges[i];
				}
				for(int i=0;i<tmpedges.length;i++){
					int samehp=1;
					Node st = tmpedges[i].start;
					Node end = tmpedges[i].end;
					if(st.hp[0].hp.length == end.hp[0].hp.length){
						samehp=0;
						for(int k=0;k<st.hp[0].hp.length;k++){
							
							if(st.hp[0].hp[k]==end.hp[0].hp[k]){
							}else{
								samehp=1;
								//System.out.println("samehp 1");
							}
						}
					}else{
						samehp=1;
					}
					if(samehp==0){
//						2ハプロタイプ間のエッジを取り去る
						System.out.println("removed edge " + tmpedges[i].id);
						tmpedges[i].printEdge();
						gr.removeEdgeAndGluePT(lg,gr,tmpedges[i]);
						break;
					}else{
						
					}
				}
			}
		}
	}
	
	public static void removeVertexSameHp(Log lg,Graph gr){
		//Edgeの両端のハプロタイプの型一致を確認し、
		//同一ならエッジを取り去る
		//すでに同一ハプロタイプ型に対応するノードの間にエッジがあることを前提としている
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr.nodes.length;i++){
				for(int j=i+1;j<gr.nodes.length;j++){
					int samehp=1;
					Node st = gr.nodes[i];
					Node end = gr.nodes[j];
					if(st.hp[0].hp.length == end.hp[0].hp.length){
						samehp=0;
						for(int k=0;k<st.hp[0].hp.length;k++){
							
							if(st.hp[0].hp[k]==end.hp[0].hp[k]){
							}else{
								samehp=1;	
							}
						}
					}else{
						samehp=1;
					}
					
					if(samehp==0){
						looper =0;
						break;
					}
				}
				
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr.nodes.length];
				tmpedges = new Edge[gr.edges.length];
				
				for(int i=0;i<gr.nodes.length;i++){
					tmpnodes[i]=gr.nodes[i];
				}
				for(int i=0;i<gr.edges.length;i++){
					tmpedges[i]=gr.edges[i];
				}
				for(int i=0;i<tmpnodes.length;i++){
					for(int j=i+1;j<tmpnodes.length;j++){
						int samehp=1;
						Node st = tmpnodes[i];
						Node end = tmpnodes[j];
						if(st.hp[0].hp.length == end.hp[0].hp.length){
							samehp=0;
							for(int k=0;k<st.hp[0].hp.length;k++){
								
								if(st.hp[0].hp[k]==end.hp[0].hp[k]){
								}else{
									samehp=1;
									//System.out.println("samehp 1");
								}
							}
						}else{
							samehp=1;
						}
						if(samehp==0){
//							2ハプロタイプ間のエッジを取り去る
							System.out.println("removed node " + tmpnodes[j].id);
							//tmpedges[i].printEdge();
							gr.removeNode(gr,tmpnodes[j]);
							break;
						}else{
							
						}
					}
					
				}
			}
		}
	}
}
