/*
 * �쐬��: 2005/04/22
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.util.Date;

/**
 * @author kuma
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class MiscUtil {

	/**
	 * �n�v���^�C�v�I�u�W�F�N�g�p�A�ʂ��ԍ�
	 */
	private static int hap_sequence_id = 0;
	
	/**
	 * Individual�I�u�W�F�N�g�p�A�ʂ��ԍ�
	 */
	private static int ind_sequence_id = 0;
	
	/**
	 * �g�݊������ψقɊւ���萔
	 * AVERAGE_RECOMB_DISTANCE : ���ϑg�݊����������� (��x�̔z��q�쐬���ɁA���͈̔͂ɕ��ψ�x�g�݊������N����)
	 * MINIMUM_RECOMB_RATE : �אڂ���SNP�Ԃ̍Œ�g�݊������i������ˑ�)
	 * SD_AVERAGE_RECOMBINATION : �g�݊�������SD�i�_���I�ɂ��������̂ŁA�v�C��)
	 */
	public static final double AVERAGE_RECOMB_DISTANCE = 100000000d;
	public static final double SD_AVERAGE_RECOMB_DISTANCE = 20000000d;
	//public static final double MINIMUM_RECOMB_RATE = 0.000000001d;
	
	/**
	 * SNP������̓_�ψق̊m��
	 */
	public static final double MUTATION_RATIO = 0.000001d;

	/**
	 * ���㐔
	 */
	public static final int GENERATION_CNT = 1;
	
	/**
	 * SNP��
	 */
	public static final int SNP_NUM = 50000;
	
	/**
	 * SNP�ԋ���
	 */
	public static final double AVERAGE_SNP_DISTANCE = 5000d;
	
	/**
	 * SNP�ԋ���(SD)
	 */
	public static double SD_SNP_DISTANCE = 2000d;


	/**
	 *�@�l��������(��g�̃y�A�����l�̎q������邩�B���������B�\�ȔN��ɒB������̂̂�)�𑍐l���ł����邩�ǂ���
	 *  true ������  ���̏ꍇ�ASTGn_UPPERLIMIT, AVERAGE_CHILD_NUMBER_STGn, SD_CHILD_NUMBER_STGn��
	 * �@�@�@�@�@�@�@�l�������������߂�
	 *  false �����Ȃ��@ ���̏ꍇ�ASTGn_UPPERLIMIT, AVERAGE_CHILD_NUMBER_STG1, SD_CHILD_NUMBER_STG1��
	 * �@�@�@�@�@�@�@�l�������������߂�
	 */
	public static boolean IF_CHANGE_CHILD_AVERAGE_NUMBER = true;

	/**
	 * �ŏ��̏o���l��
	 */
	public static int INIT_EFFECTIVE_NUMBER = 2;
	
	/**
	 * STG1�̓��ꐢ����l���̏��
	 */
	public static double STG1_UPPER_LIMIT = 10000;
	public static double AVERAGE_CHILD_NUMBER_STG1 = 2.5d;
	public static double SD_CHILD_NUMBER_STG1 = 0.4d;
	/**
	 * STG2�̓��ꐢ����l���̏��
	 */
	public static double STG2_UPPER_LIMIT = 11000;
	public static double AVERAGE_CHILD_NUMBER_STG2 = 2.4d;
	public static double SD_CHILD_NUMBER_STG2 = 0.3d;
	/**
	 * STG3�̓��ꐢ����l���̏��
	 */
	public static double AVERAGE_CHILD_NUMBER_STG3 = 2.0d;
	public static double SD_CHILD_NUMBER_STG3 = 0.3d;

	/**
	 * GBM geometric brownian model�̒萔(drift = ��)
	 */
	public static double DRIFT = 0.00000003074;
	
	/**
	 * GBM geometric brownian model�̒萔(volatility = ��)
	 */
	public static double VOLATILITY = Math.sqrt(0.00000002503);
	
	/**
	 * �����̐l���A�q���c���Ȃ��m���@(���g�p)
	 */
	public static double SELECTION_RATIO = 0.0005;

	/**
	 * ped�t�@�C���ɏo�͂���e���゠����̍ő�l��
	 * (����ȏ�̌l�����̐���ɑ��݂���΁A�����_���ɂ��̐l�������I��)
	 */
	public static final int MAX_OUTPUT_INDIVIDUAL = 500;

	/**
	 * vgj�t�H�[�}�b�g�Ńf�[�^���o�͂��邩
	 */
	public static final boolean IF_PRINT_VGJ = false;
	
	/**
	 * history�t�H�[�}�b�g�Ńf�[�^���o�͂��邩
	 */
	public static final boolean IF_PRINT_HISTORY = false;
	
	/**
	 * haploview�̓��̓t�@�C��(*.ped, *info)���o�͂��邩
	 */
	public static final boolean IF_PRINTO_HAPLOVIEW = true;

	/**
	 * �n�v���^�C�v�I�u�W�F�N�g�p�A�Ƃ����ԍ����擾����
	 * @return �ʂ��ԍ�
	 */
	public synchronized static int hap_sequence_next(){
		hap_sequence_id++;
		return hap_sequence_id;
	}
	
	/**
	 * �n�v���^�C�v�I�u�W�F�N�g�p�A�Ƃ����ԍ����擾����
	 * @return �ʂ��ԍ�
	 */
	public synchronized static int ind_sequence_next(){
		ind_sequence_id++;
		return ind_sequence_id;
	}
	
	
	
	
	/**
	 * �z����V���b�t������ 
	 * shuffle2����2�{����
	 * 
	 * @param arr�@�V���b�t�������z��
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
	 * �z����V���b�t������ shuffle�֐��ƈقȂ�A�����Ŕ����������l���L�[�Ƀ\�[�g��������
	 * 
	 * @param arr�@�V���b�t�������z��
	 */
	public static void shuffle2(int[] arr){
		
		//������v�f�Ɏ��z������
		double[] rand_arr = new double[arr.length];
		for(int i=0; i<rand_arr.length; i++){
			rand_arr[i] = Math.random();
		}
		
		//�������\�[�g����ƂƂ��ɁA�����̔z����\�[�g����
		for(int i= arr.length-1; i>0; i--){
			int t = (int)(Math.random() * i);
			
			int tmp = arr[i];
			arr[i] = arr[t];
			arr[t] = tmp;
		}
	}
	
	/**
	 * keys�Ɋ�Â��āAvalues�̒l���N�C�b�N�\�[�g����B
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort(double[] keys, int[] values, int first, int last){//key��double

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
	 * keys�Ɋ�Â��āAvalues�̒l���N�C�b�N�\�[�g����B
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort2(int[] keys, int[] values, int first, int last){//key��int

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
	 * keys�Ɋ�Â��āAvalues�̒l���N�C�b�N�\�[�g����B
	 * @param keys
	 * @param values
	 * @param first
	 * @param last
	 */
	public static void quick_sort3(int[] keys, double[] values, int first, int last){//key��int

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
	 * �z��̗v�f�̉��ԖڂɃL�[�����邩��Ԃ�
	 * 
	 * @param values�@�l�̔z��
	 * @param key�@ �@�����L�[
	 * @return        �����L�[�̒l���ŏ��Ɍ��������ʒu
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
	 * �����_���ȃA�������擾����
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
	 * �w�肵���A�����ȊO�̃A���������ꂼ��1/3�̊m���Ŏ��o��
	 * @param except_allele
	 * @return�@
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
	 * vgj�`���̃w�b�_�[���o�͂���
	 * @param bw
	 * @throws Exception
	 */
	public static void print_vgj_header(BufferedWriter bw) throws Exception{
		bw.write("graph [\n");
		bw.write("	directed 1\n");
	}
	
	/**
	 * vgj�`���̃t�b�^�[���o�͂���
	 * @param bw
	 * @throws Exception
	 */
	public static void print_vgj_footer(BufferedWriter bw) throws Exception{
		bw.write("]\n");
	}
	
	/**
	 * ���ϒlm�̎w�������̔���
	 * @param m�@����
	 * @return�@�w�����z���瓾���郉���_���Ȓl
	 */
	public static double exp_d(double m)
	{
		return -m * Math.log(Math.random());
	}
	
	/**
	 * ���K�ϗʕ��z�̔���(�{�b�N�X�}�[���[�@�j
	 * 
	 * @param m�@����
	 * @param sd�@�W���΍�
	 * @return�@���z���瓾���郉���_���Ȓl
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
	 * ���K�ϗʕ��z�����̔���(12�T���@�j
	 * =>���z�̌`�������ɈႤ�B�i�v���b�g���Ă����܂�����ł��Ȃ�������)
	 * 
	 * @param m�@����
	 * @param sd�@�W���΍�
	 * @return�@���z���瓾���郉���_���Ȓl
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
	 * �􉽕ϗʕ��z�̔���
	 * �@�m��p�ł����錜�܂�n��ڂ̉���ł���Ƃ�����m����Pn = p(1-p)^(n-1)
	 *   n��ڂ܂łɂ�����m���͂���̇��ɂȂ�
	 * 
	 * @param p�@�m��
	 * @return �􉽕��z���甭����������
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
	 * GBM (geometric brownian model����������j
	 * @param prev_rec_rate_
	 * @param distance_
	 * @return
	 */
	public static double gbm_recombination_rate(double prev_rec_rate_, int distance_){
	
		return prev_rec_rate_ + (prev_rec_rate_ * (MiscUtil.DRIFT*distance_ + MiscUtil.VOLATILITY*MiscUtil.norm_d(0,1)*Math.sqrt(distance_)));
	}
	
	

	/**
	 * �ȉ��A�e�X�g�֐�
	 *
	 */
	//	shuffle1 vs shuffle2��r
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

	//get_random_allele(char)��test
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
	
	//normd��test
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
	
	//Grahp��Graph[]�ɒǉ�
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
	
	//int[]����int[]�ɂ���v�f����菜��
	public static int[] rmIdentical(int[] ar1, int[] ar2){//ar1 �����������ĕԂ�
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
//	int[]��int[]�̗v�f��t��������
	public static int[] addintarray(int[] ar1, int[] ar2){//ar1 ��傫�����ĕԂ�
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
	 * ����n�v���^�C�v�^�������A�ʂ̃n�v���^�C�v�I�u�W�F�N�g�ɓo�^����Ă���n�v���^�C�v�̓��ꐫ�𔻒肵�A
	 * ��ӂȃn�v���^�C�v�^�݂̂̃��X�g�Ƃ���
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
	 * �ȉ��Aobsolate
	 */
	
	/*
	 * �_�ψق̃p�^�[��
	 *   1:��{�̐��F�̂�MUTATION_CNT�����A�K���_�ψق𔭐�������
	 *   2:�g�݊������ɂ��������ē_�ψق𔭐�������
	public static int MUTATION_TYPE = 2;
	
	/*
	 * �_�ψٔ����̌�
	 * �@�����AMUTATION_TYPE��1�������ꍇ�A ��{�̐��F�̂�����A�K�����ꂾ���̓_�ψق𔭐�������
	
	public static int MUTATION_CNT = 1;
	 */
		
}
