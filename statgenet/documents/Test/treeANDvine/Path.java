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
public class Path {
	//{2,4,5,0,6}というパスの一部が判明したときは
	//2,4,-9,5,-9,0,6,-9という要素にする
	int id;
	int elem[];//この要素数はpathのエッジの本数プラス、判明しているセグメントの数
	int numseg;//判明しているセグメント数
	Haplotype hap1;
	Haplotype hap2;
	
	public Path(int id_,Haplotype hap1_,Haplotype hap2_){
		id = id_;
		elem = new int[0];
		numseg = 0;
		hap1=hap1_;
		hap2=hap2_;
		for(int i=0;i<hap1.hp.length;i++){
			if(hap1.hp[i] == hap2.hp[i]){
			}else{
				int tmp[];
				tmp = new int[elem.length+1];
				for(int j=0;j<elem.length;j++){
					tmp[j]=elem[j];
				}
				tmp[elem.length]=i;
				elem = new int[tmp.length];
				for(int j=0;j<tmp.length;j++){
					elem[j]=tmp[j];
				}
			}
		}
	}
	//method
	public void printPathAll(){
		String st ="";
		st = "path ";
		st += "id " + id;
		st += " hap1 " + hap1.id;
		st += " hap2 " + hap2.id;
		st += " path * ";
		for(int i=0;i<elem.length;i++){
			st += elem[i] + " ";
		}
		
		System.out.println(st);
	
	}
	
	public boolean comparePath(Path pt_){
		boolean ret = true;
		int elem1[];
		int elem2[];
		elem1 = new int[elem.length];
		for(int i=0;i<elem.length;i++){
			elem1[i]=elem[i];
		}
		elem2 = new int[pt_.elem.length];
		for(int i=0;i<pt_.elem.length;i++){
			elem2[i]=pt_.elem[i];
		}
		MiscUtil.quick_sort_int(elem1);
		MiscUtil.quick_sort_int(elem2);
		if(elem1.length==elem2.length){
			for(int i=0;i<elem1.length;i++){
				if(elem1[i]==elem2[i]){
					
				}else{
					ret = false;
				}
			}
		}else{
			ret = false;
		}
		return ret;
	}

}
