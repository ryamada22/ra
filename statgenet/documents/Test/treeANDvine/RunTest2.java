/*
 * 作成日: 2005/07/25
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
public class RunTest2 {

	public static void main(String[] args) {
		Graph gr1,gr2,gr3;
		gr1 = new Graph(0);
		gr2=new Graph(1);
		gr3=new Graph(2);
		
		gr1.or_x=10;
		gr2.or_x=15;
		gr3.or_x=12;
		
		Graph trees[];
		trees = new Graph[3];
		trees[0]=gr1;
		trees[1]=gr2;
		trees[2]=gr3;
		
		for(int i=0;i<trees.length;i++){
			System.out.println(trees[i].id);
			
		}
		MiscUtil ms;
		ms = new MiscUtil();
		trees = ms.sortTreesByOr_x(trees);
		
		for(int i=0;i<trees.length;i++){
			System.out.println(trees[i].or_x);
			
		}
	}
}
