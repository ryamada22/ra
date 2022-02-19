/*
 * �쐬��: 2005/08/17
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package treeANDvine;

/**
 * @author yamada
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class MiscUtil2 {
	  /*
	   * ���v�f�̑I��
	   * ���Ɍ��āA�ŏ��Ɍ��������قȂ�2�̗v�f�̂����A
	   * �傫���ق��̔ԍ���Ԃ��܂��B
	   * �S�������v�f�̏ꍇ�� -1 ��Ԃ��܂��B
	   */
	  int pivot(int[] a,int i,int j){
	    int k=i+1;
	    while(k<=j && a[i]==a[k]) k++;
	    if(k>j) return -1;
	    if(a[i]>=a[k]) return i;
	    return k;
	  }

	  /*
	   * �p�[�e�B�V��������
	   * a[i]�`a[j]�̊ԂŁAx �����Ƃ��ĕ������܂��B
	   * x ��菬�����v�f�͑O�ɁA�傫���v�f�͂�����ɗ��܂��B
	   * �傫���v�f�̊J�n�ԍ���Ԃ��܂��B
	   */
	  int partition(int[] a,int i,int j,int x){
	    int l=i,r=j;

	    // ��������������܂ŌJ��Ԃ��܂�
	    while(l<=r){

	      // ���v�f�ȏ�̃f�[�^��T���܂�
	      while(l<=j && a[l]<x)  l++;

	      // ���v�f�����̃f�[�^��T���܂�
	      while(r>=i && a[r]>=x) r--;

	      if(l>r) break;
	      int t=a[l];
	      a[l]=a[r];
	      a[r]=t;
	      l++; r--;
	    }
	    return l;
	  }

	  /*
	   * �N�C�b�N�\�[�g�i�ċA�p�j
	   * �z��a�́Aa[i]����a[j]����בւ��܂��B
	   */
	  public void quickSort(int[] a,int i,int j){
	    if(i==j) return;
	    int p=pivot(a,i,j);
	    if(p!=-1){
	      int k=partition(a,i,j,a[p]);
	      quickSort(a,i,k-1);
	      quickSort(a,k,j);
	    }
	  }

	  /*
	   * �\�[�g
	   */
	  public void sort(int[] a){
	    quickSort(a,0,a.length-1);
	  }


}
