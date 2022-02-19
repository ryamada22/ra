/*
 * 作成日: 2005/04/22
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.util.Date;

/**
 * @author kuma
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class MiscUtil {

	/**
	 * ハプロタイプオブジェクト用、通し番号
	 */
	private static int hap_sequence_id = 0;
	
	/**
	 * Individualオブジェクト用、通し番号
	 */
	private static int ind_sequence_id = 0;
	
	/**
	 * 組み換え＆変異に関する定数
	 * AVERAGE_RECOMB_DISTANCE : 平均組み換え物理距離 (一度の配偶子作成時に、この範囲に平均一度組み換えが起こる)
	 * MINIMUM_RECOMB_RATE : 隣接するSNP間の最低組み換え率（距離非依存)
	 * SD_AVERAGE_RECOMBINATION : 組み換え時のSD（論理的におかしいので、要修正)
	 */
	public static final double AVERAGE_RECOMB_DISTANCE = 100000000d;
	public static final double SD_AVERAGE_RECOMB_DISTANCE = 20000000d;
	//public static final double MINIMUM_RECOMB_RATE = 0.000000001d;
	
	/**
	 * SNPあたりの点変異の確率
	 */
	public static final double MUTATION_RATIO = 0.000001d;

	/**
	 * 世代数
	 */
	public static final int GENERATION_CNT = 1;
	
	/**
	 * SNP数
	 */
	public static final int SNP_NUM = 50000;
	
	/**
	 * SNP間距離
	 */
	public static final double AVERAGE_SNP_DISTANCE = 5000d;
	
	/**
	 * SNP間距離(SD)
	 */
	public static double SD_SNP_DISTANCE = 2000d;


	/**
	 *　人口増加率(一組のペアが何人の子供を作るか。ただし生殖可能な年齢に達するもののみ)を総人口でかえるかどうか
	 *  true かえる  この場合、STGn_UPPERLIMIT, AVERAGE_CHILD_NUMBER_STGn, SD_CHILD_NUMBER_STGnで
	 * 　　　　　　　人口増加率を決める
	 *  false かえない　 この場合、STGn_UPPERLIMIT, AVERAGE_CHILD_NUMBER_STG1, SD_CHILD_NUMBER_STG1で
	 * 　　　　　　　人口増加率を決める
	 */
	public static boolean IF_CHANGE_CHILD_AVERAGE_NUMBER = true;

	/**
	 * 最初の出発人数
	 */
	public static int INIT_EFFECTIVE_NUMBER = 2;
	
	/**
	 * STG1の同一世代内人口の上限
	 */
	public static double STG1_UPPER_LIMIT = 10000;
	public static double AVERAGE_CHILD_NUMBER_STG1 = 2.5d;
	public static double SD_CHILD_NUMBER_STG1 = 0.4d;
	/**
	 * STG2の同一世代内人口の上限
	 */
	public static double STG2_UPPER_LIMIT = 11000;
	public static double AVERAGE_CHILD_NUMBER_STG2 = 2.4d;
	public static double SD_CHILD_NUMBER_STG2 = 0.3d;
	/**
	 * STG3の同一世代内人口の上限
	 */
	public static double AVERAGE_CHILD_NUMBER_STG3 = 2.0d;
	public static double SD_CHILD_NUMBER_STG3 = 0.3d;

	/**
	 * GBM geometric brownian modelの定数(drift = μ)
	 */
	public static double DRIFT = 0.00000003074;
	
	/**
	 * GBM geometric brownian modelの定数(volatility = σ)
	 */
	public static double VOLATILITY = Math.sqrt(0.00000002503);
	
	/**
	 * 疾患の人が、子を残せない確率　(未使用)
	 */
	public static double SELECTION_RATIO = 0.0005;

	/**
	 * pedファイルに出力する各世代あたりの最大個人数
	 * (これ以上の個人がその世代に存在すれば、ランダムにこの人数だけ選ぶ)
	 */
	public static final int MAX_OUTPUT_INDIVIDUAL = 500;

	/**
	 * vgjフォーマットでデータを出力するか
	 */
	public static final boolean IF_PRINT_VGJ = false;
	
	/**
	 * historyフォーマットでデータを出力するか
	 */
	public static final boolean IF_PRINT_HISTORY = false;
	
	/**
	 * haploviewの入力ファイル(*.ped, *info)を出力するか
	 */
	public static final boolean IF_PRINTO_HAPLOVIEW = true;

	/**
	 * ハプロタイプオブジェクト用、とおし番号を取得する
	 * @return 通し番号
	 */
	public synchronized static int hap_sequence_next(){
		hap_sequence_id++;
		return hap_sequence_id;
	}
	
	/**
	 * ハプロタイプオブジェクト用、とおし番号を取得する
	 * @return 通し番号
	 */
	public synchronized static int ind_sequence_next(){
		ind_sequence_id++;
		return ind_sequence_id;
	}
	
	
	
	
	/**
	 * 配列をシャッフルする 
	 * shuffle2より約2倍早い
	 * 
	 * @param arr　シャッフルされる配列
	 */
	public static void shuffle(int[] arr){
		for(int i= arr.length-1; i>0; i--){
			int t = (int)(Math.random() * i);
			
			int tmp = arr[i];
			arr[i] = arr[t];
			arr[t] = tmp;
		}
	}

	/**
	 * 配列をシャッフルする shuffle関数と異なり、乱数で発生させた値をキーにソートをかける
	 * 
	 * @param arr　シャッフルされる配列
	 */
	public static void shuffle2(int[] arr){
		
		//乱数を要素に持つ配列を作る
		double[] rand_arr = new double[arr.length];
		for(int i=0; i<rand_arr.length; i++){
			rand_arr[i] = Math.random();
		}
		
		//乱数をソートするとともに、引数の配列もソートする
		for(int i= arr.length-1; i>0; i--){
			int t = (int)(Math.random() * i);
			
			int tmp = arr[i];
			arr[i] = arr[t];
			arr[t] = tmp;
		}
	}
	
	/**
	 * keysに基づいて、valuesの値をクイックソートする。
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort(double[] keys, int[] values, int first, int last){//keyがdouble

		int i, j;
		double mid;
		double tmp_key;
		int tmp_value;
		
		i = first;
		j = last;
		
		mid = keys[(int)((first+last)/2)];
		
		for (;;){
			while(keys[i] < mid){i++;}
			while(mid < keys[j]){j--;}
			if(i>=j){break;}
			tmp_key = keys[i]; keys[i] = keys[j]; keys[j] = tmp_key;
			tmp_value = values[i]; values[i] = values[j]; values[j] = tmp_value;
			i++; j--;
		}
		
		if(first < i-1){ quick_sort(keys, values, first, i-1);}
		if(j+1 < last){ quick_sort(keys, values, j+1, last);}		
	}
	
	/**
	 * keysに基づいて、valuesの値をクイックソートする。
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort2(int[] keys, int[] values, int first, int last){//keyがint

		int i, j;
		double mid;
		int tmp_key;
		int tmp_value;
		
		i = first;
		j = last;
		
		mid = keys[(int)((first+last)/2)];
		
		for (;;){
			while(keys[i] < mid){i++;}
			
			while(mid < keys[j]){j--;}
			if(i>=j){break;}
			tmp_key = keys[i]; keys[i] = keys[j]; keys[j] = tmp_key;
			tmp_value = values[i]; values[i] = values[j]; values[j] = tmp_value;
			i++; j--;
		}
		
		if(first < i-1){ quick_sort2(keys, values, first, i-1);}
		if(j+1 < last){ quick_sort2(keys, values, j+1, last);}		
	}
	/**
	 * keysに基づいて、valuesの値をクイックソートする。
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort3(int[] keys, double[] values, int first, int last){//keyがint

		int i, j;
		double mid;
		int tmp_key;
		double tmp_value;
		
		i = first;
		j = last;
		
		mid = keys[(int)((first+last)/2)];
		
		for (;;){
			while(keys[i] < mid){i++;}
			while(mid < keys[j]){j--;}
			if(i>=j){break;}
			tmp_key = keys[i]; keys[i] = keys[j]; keys[j] = tmp_key;
			tmp_value = values[i]; values[i] = values[j]; values[j] = tmp_value;
			i++; j--;
		}
		
		if(first < i-1){ quick_sort3(keys, values, first, i-1);}
		if(j+1 < last){ quick_sort3(keys, values, j+1, last);}		
	}

	public static void quick_sort_int(int[] keys){
		
		//int values[];
		//values = new int[keys.length];
		int max,min;
		max=0;min=keys.length-1;
		for(int i=0;i<keys.length;i++){
			//values[i]=keys[i];
			if(keys[i]>max){
				//max=keys[i];
			}
			if(keys[i]<min){
				//min=keys[i];
			}
		}
		for(int i = 0;i<keys.length;i++){
			//System.out.println("keys[" + i + "]" + keys[i]);
		}
		System.out.println("keys len " + keys.length);
		System.out.println("max" + max + " min " + min);
		quick_sort2(keys,keys,max,min);
		for(int i = 0;i<keys.length;i++){
			//System.out.println("keys[" + i + "]" + keys[i]);
		}
	}
	public static void quick_sort_int2(int[] a,int first,int last){
		int i,j;
		int t;
		double x;
		x = ((first+last)/2);
		i=first;j=last;
		for(;;){
			while(a[i]<x){i++;}
			while(x<a[j]){j--;}
			if(i>=j){break;}
			t=a[i];a[i]=a[j];a[j]=t;
			i++;j--;
		}
		if(first<i-1)quick_sort_int2(a,first,i-1);
		if(j+1<last)quick_sort_int2(a,j+1,last);
	}
	
	/**
	 * 配列の要素の何番目にキーがあるかを返す
	 * 
	 * @param values　値の配列
	 * @param key　 　検索キー
	 * @return        検索キーの値が最初に見つかった位置
	 */
	public static int find(int[] values, int key){
		for(int i=0; i<values.length; i++){
			if(values[i] == key){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * ランダムなアレルを取得する
	 * @return
	 */
	public static char get_random_allele(){
		
		double rand = Math.random();
	
		if(rand<0.25){
			return 'A';
		}else if(rand<0.5){
			return 'C';
		}else if(rand<0.75){
			return 'G';
		}else{
			return 'T';
		}			
	}
	
	/**
	 * 指定したアレル以外のアレルをそれぞれ1/3の確率で取り出す
	 * @param except_allele
	 * @return　
	 */
	public static char get_random_allele(char except_allele){
		
		double rand = Math.random();

		//123 na
		//CGT A
		//AGT C
		//ACT G
		//ACG T

		if(rand<1.0d/3.0d){
			if(except_allele == 'A'){
				return 'C';
			}else{
				return 'A';
			}
		}else if(rand<2.0d/3.0d){
			if(except_allele == 'C' || except_allele == 'A'){
				return 'G';
			}else{
				return 'C';
			}
		}else{
			if(except_allele == 'T'){
				return 'G';
			}else{
				return 'T';
			}
		}		
	}
	
	
	/**
	 * vgj形式のヘッダーを出力する
	 * @param bw
	 * @throws Exception
	 */
	public static void print_vgj_header(BufferedWriter bw) throws Exception{
		bw.write("graph [\n");
		bw.write("	directed 1\n");
	}
	
	/**
	 * vgj形式のフッターを出力する
	 * @param bw
	 * @throws Exception
	 */
	public static void print_vgj_footer(BufferedWriter bw) throws Exception{
		bw.write("]\n");
	}
	
	/**
	 * 平均値mの指数乱数の発生
	 * @param m　平均
	 * @return　指数分布から得られるランダムな値
	 */
	public static double exp_d(double m)
	{
		return -m * Math.log(Math.random());
	}
	
	/**
	 * 正規変量分布の発生(ボックスマーラー法）
	 * 
	 * @param m　平均
	 * @param sd　標準偏差
	 * @return　分布から得られるランダムな値
	 */
	public static double norm_d(double m, double sd){
		
		double r1, r2, s;
		
		do{
			r1 = 2*Math.random() - 1;
			r2 = 2*Math.random() - 1;
			s = r1*r1 + r2*r2;
		}while(s > 1 || s == 0);
		
		s = Math.sqrt((-2) * Math.log(s)/s);
		return sd * (r1 * s) + m;		
	}

	/**
	 * 正規変量分布乱数の発生(12個サム法）
	 * =>分布の形が微妙に違う。（プロットしてもあまり実感できなかったが)
	 * 
	 * @param m　平均
	 * @param sd　標準偏差
	 * @return　分布から得られるランダムな値
	 */
	public static double norm_d2(double m, double sd)	{
		double x;
		int i1;
		
		x = 0.0;
		
		for (i1 = 0; i1 < 12; i1++)
		x += Math.random();
		
		x = sd * (x - 6.0) + m;
		
		return x;
	}
	
	
	/**
	 * 幾何変量分布の発生
	 * 　確率pであたる懸賞にn回目の応募でやっとあたる確率はPn = p(1-p)^(n-1)
	 *   n回目までにあたる確率はこれの∑になる
	 * 
	 * @param p　確率
	 * @return 幾何分布から発生した乱数
	 */
	public static int geometric_d(double p){
		
		int n;
		
		n=1;
		while(Math.random()>p){
			n++;
		}
		
		return n;
	}
	
	
	/**
	 * GBM (geometric brownian modelを実装する）
	 * @param prev_rec_rate_
	 * @param distance_
	 * @return
	 */
	public static double gbm_recombination_rate(double prev_rec_rate_, int distance_){
	
		return prev_rec_rate_ + (prev_rec_rate_ * (MiscUtil.DRIFT*distance_ + MiscUtil.VOLATILITY*MiscUtil.norm_d(0,1)*Math.sqrt(distance_)));
	}
	
	

	/**
	 * 以下、テスト関数
	 *
	 */
	//	shuffle1 vs shuffle2比較
	public void test_shuffle(){
		
		
		int size=100;
		int[] test = new int[size];
		for(int i=0; i<size; i++){
			test[i] = i+1;
		}
	
		Date date = new Date();
		
		long start = date.getTime();
		int times = 1000000;
		int[] appear_num = new int[size];
		for(int j=0; j<times; j++){
			MiscUtil.shuffle2(test);
			appear_num[MiscUtil.find(test, 100)]++;
		}
		
		for(int k=0; k<appear_num.length; k++){
			System.out.println(k + "\t" + appear_num[k]);
		}
		date = new Date();
		System.out.println(date.getTime()-start);
		
	}

	//get_random_allele(char)のtest
	public void test_get_random_allele(){
		
		int[] cnt = new int[4];
		for(int i=0; i<1000; i++){
			char allele = MiscUtil.get_random_allele('A');
			if( allele == 'A'){
				cnt[0]++;
			}else if(allele == 'C'){
				cnt[1]++;
			}else if(allele == 'G'){
				cnt[2]++;
			}else if(allele == 'T'){
				cnt[3]++;
			}
		}
		for(int i=0; i<cnt.length; i++){
			System.out.println(i+"\t"+ cnt[i]);
		}
	}
	
	//normdのtest
	public static void test_normd2(){
		int num = 5000;
		//double[] norm = new double[num];
		for(int i=0; i<num; i++){
			
			System.out.println(MiscUtil.norm_d2(3, 0.5));
		}
	}

	public Graph[] sortTreesByOr_x(Graph[] trees_){
		int id[];
		double or_x[];
		id = new int[trees_.length];
		or_x = new double[trees_.length];
		
		for(int i=0;i<trees_.length;i++){
			id[i]=i;
			or_x[i]=trees_[i].or_x;
		}
		for(int i =0;i<trees_.length;i++){
			//System.out.println(id[i]);
		}
		quick_sort(or_x,id,0,trees_.length-1);
		for(int i =0;i<trees_.length;i++){
			//System.out.println(id[i]);
		}
		Graph tmp[];
		tmp= new Graph[trees_.length];
		for(int i=0;i<id.length;i++){
			tmp[i]=trees_[id[i]];
			//System.out.println("id " + id[i] + " " + "trees_[id[i]] x" + trees_[id[i]].or_x);
		}
		return tmp;
		
	}
	
	//GrahpをGraph[]に追加
	public static Graph[] addGraphArr(Graph[] grarr, Graph gr){
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
	
	//int[]からint[]にある要素を取り除く
	public static int[] rmIdentical(int[] ar1, int[] ar2){//ar1 を小さくして返す
		int tmp[];
		int tmp2[];
		tmp = new int[ar1.length];
		int counter=0;
		for(int i=0;i<ar1.length;i++){
			boolean judge=true;
			for(int j=0;j<ar2.length;j++){
				if(ar1[i]==ar2[j]){
					judge = false;
					break;
				}
			}
			if(judge){
				tmp[counter]=ar1[i];
				counter++;
			}
			
		}
		
		
		tmp2 = new int[counter];
		for(int i=0;i<counter;i++){
			tmp2[i]=tmp[i];
		}
		return tmp2;
	}
//	int[]にint[]の要素を付け加える
	public static int[] addintarray(int[] ar1, int[] ar2){//ar1 を大きくして返す
		int tmp[];
		tmp = new int[ar1.length+ar2.length];
		int counter=0;
		for(int i=0;i<ar1.length;i++){
			tmp[i]=ar1[i];
		}
		for(int i=0;i<ar2.length;i++){
			tmp[i+ar1.length]=ar2[i];
		}
		return tmp;
		/*
		ar1 = new int[tmp.length];
		for(int i=0;i<tmp.length;i++){
			ar1[i]=tmp[i];
		}
		*/
	}
	
	/*
	 * 同一ハプロタイプ型を持つが、別のハプロタイプオブジェクトに登録されているハプロタイプの同一性を判定し、
	 * 一意なハプロタイプ型のみのリストとする
	 * @author yamada
	 */
	public static Haplotype[] specifyHpList(Haplotype[] hp){
		//System.out.println("pre " +hp.length);
		Haplotype tmp[]={};
		for(int i=0;i<hp.length;i++){
			boolean judge=true;
			for(int j=0;j<tmp.length;j++){
				int dif1[]=MiscUtil.rmIdentical(hp[i].hp,tmp[j].hp);
				int dif2[]=MiscUtil.rmIdentical(hp[i].hp,tmp[j].hp);
				if(dif1.length==0 && dif2.length==0){
					judge = false;
					break;
				}
			}
			if(judge){
				Haplotype tmp2[];
				tmp2=new Haplotype[tmp.length+1];
				for(int j=0;j<tmp.length;j++){
					tmp2[j]=tmp[j];
				}
				tmp2[tmp.length] = hp[i];
				tmp = new Haplotype[tmp2.length];
				for(int j=0;j<tmp2.length;j++){
					tmp[j]=tmp2[j];
				}
			}
		}
		hp = new Haplotype[tmp.length];
		for(int i=0;i<tmp.length;i++){
			hp[i]=tmp[i];
		}
		//System.out.println("post " +hp.length);
		return hp;
	}
	/*
	 * 以下、obsolate
	 */
	
	/*
	 * 点変異のパターン
	 *   1:一本の染色体にMUTATION_CNT数分、必ず点変異を発生させる
	 *   2:組み換え率にしたがって点変異を発生させる
	public static int MUTATION_TYPE = 2;
	
	/*
	 * 点変異発生の個数
	 * 　もし、MUTATION_TYPEが1だった場合、 一本の染色体あたり、必ずこれだけの点変異を発生させる
	
	public static int MUTATION_CNT = 1;
	 */
		
}
