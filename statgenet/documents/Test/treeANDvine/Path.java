/*
 * �쐬��: 2005/07/30
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package treeANDvine;

/**
 * @author Ryo Yamada
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class Path {
	//{2,4,5,0,6}�Ƃ����p�X�̈ꕔ�����������Ƃ���
	//2,4,-9,5,-9,0,6,-9�Ƃ����v�f�ɂ���
	int id;
	int elem[];//���̗v�f����path�̃G�b�W�̖{���v���X�A�������Ă���Z�O�����g�̐�
	int numseg;//�������Ă���Z�O�����g��
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
