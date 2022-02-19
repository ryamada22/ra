/*
 * �쐬��: 2005/07/15
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package treeANDvine;

import java.util.Hashtable;


/**
 * @author yamada
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 * Node�̓O���t�̒��ő��݂Ɋ֌W��L�����͂̒P�ʂł���
 * Node�͂����P�̃O���t�ɑ��݂���
 */
public class Node {
	/*
	 * field
	 */
	int id;
	Graph gr;//�Y���O���t
	int id_gr;//�O���t��id
	double x,y,z;//�ʒu��L����
	double r;//�傫����L����
	Edge edge[];
	String name;
	int label;//int�^�̖���
	int type;//0 root, 1 coalescence, 2 recombinant
	Event ev[];
	Hashtable hs;//�������̏����i�[����
	
	//�n�v���^�C�v�t�F�[�W���O���L�ݒ�
	Haplotype hp[];
	
	/*
	 * method
	 */
	public void shift(double x_,double y_,double z_){//���s�ړ�
		x+=x_;y+=y_;z+=z_;
	}

	public void addId_gr(int id_){//id_gr�ύX
		id_gr = id_;
	}
	
	public void copy(Node nd_){
		gr = nd_.gr;
		id_gr = nd_.id_gr;
		x=nd_.x;y=nd_.y;z=nd_.z;
		r = nd_.r;//�傫����L����
		edge =new Edge[nd_.edge.length];
		for(int i=0;i<nd_.edge.length;i++){
			edge[i] = nd_.edge[i];
		}
		name = nd_.name;
		label = nd_.label;//int�^�̖���
		type = nd_.type;//0 root, 1 coalescence, 2 recombinant
		ev = new Event[nd_.ev.length];
		for(int i=0;i<nd_.ev.length;i++){
			ev[i]=nd_.ev[i];
		}
		//Hashtable hs;//�������̏����i�[����
		
		hp = new Haplotype[nd_.hp.length];
		for(int i=0;i<nd_.hp.length;i++){
			hp[i]=nd_.hp[i];
		}
	}
	
	public void addEdge(Edge edge_){//edge��ǉ�
		if(edge == null){
			edge = new Edge[1];
			edge[0] = edge_;
		}else{
			Edge tmpedge[];
			tmpedge = new Edge[edge.length +1];
			for(int i=0;i<edge.length;i++){
				tmpedge[i]=edge[i];
			}
			tmpedge[edge.length] = edge_;
			edge = new Edge[tmpedge.length];
			for(int i=0;i<tmpedge.length;i++){
				edge[i]=tmpedge[i];
			}
		}
	}


	public void addEvent(Event ev_){//event��ǉ�
		if(ev == null){
			ev = new Event[1];
			ev[0] = ev_;
		}else{
			Event tmpev[];
			tmpev = new Event[ev.length +1];
			for(int i=0;i<ev.length;i++){
				tmpev[i]=ev[i];
			}
			tmpev[ev.length] = ev_;
			ev = new Event[tmpev.length];
			for(int i=0;i<tmpev.length;i++){
				ev[i]=tmpev[i];
			}
		}
	}

	public void addType(int type_){//type��ǉ�
		type = type_;
	}
	
	public void addHaplotype(Haplotype hp_){
		if(hp == null){
			hp = new Haplotype[1];
			hp[0] = hp_;
		}else{
			Haplotype tmphp[];
			tmphp = new Haplotype[hp.length +1];
			for(int i=0;i<hp.length;i++){
				tmphp[i]=hp[i];
			}
			tmphp[hp.length] = hp_;
			hp = new Haplotype[tmphp.length];
			for(int i=0;i<tmphp.length;i++){
				hp[i]=tmphp[i];
			}
		}
	}
	public void printNodeAll(){
		String st = "�@Node ";
		st += "�@id" + id;
		st += "�@gr " ;
		st += id_gr ;
		st += "�@x,y,z " + x + " " + y + " " + z + " ";
		st += "�@edge ";
		if(edge == null){
		}else{
			for(int i=0;i<edge.length;i++){
				st += edge[i].id + " ";
			}
		}
		st += " event ";
		if(ev == null){
		}else{
			for(int i=0;i<ev.length;i++){
				st += ev[i].id + " ";
			}
		}
		
	}
	public void addHashdata(String key,String value){
		hs = new Hashtable();
		hs.put("key","value");
	}
	public void takeawayEdge(Edge ed_){
		if(edge.length>0){
			int numregistry=0;
			for(int i=0;i<edge.length;i++){
				if(edge[i]==ed_){
					numregistry++;
				}
			}
			Edge tmp[];
			//tmp = new Edge[edge.length-1];
			tmp = new Edge[edge.length-numregistry];
			int counter=0;
			for(int i=0;i<edge.length;i++){
				if(edge[i]==ed_){
					
				}else{
					tmp[counter]=edge[i];
					counter++;
				}
			}
			edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				edge[i]=tmp[i];
			}
		}
		
	}

	/*
	 * constructor
	 */
	
	public Node(int id_){
		id = id_;
		x = 0;y=0;z=0;//default�ňʒu��^����
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_){
		id = id_;
		gr=gr_;
		x = 0;y=0;z=0;//default�ňʒu��^����
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_){
		id = id_;
		gr=gr_;
		id_gr=id_gr_;
		x = 0;y=0;z=0;//default�ňʒu��^����
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,double x_,double y_,double z_){
		id = id_;
		x = x_;y=y_;z=z_;//default�ňʒu��^����
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,double x_,double y_,double z_){
		id = id_;
		gr = gr_;
		x = x_;y=y_;z=z_;
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_,double x_,double y_,double z_){
		id = id_;
		gr = gr_;
		id_gr=id_gr_;
		x = x_;y=y_;z=z_;
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = -1;//default
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
	public Node(int id_,Graph gr_,int id_gr_,double x_,double y_,double z_,int type_){
		id = id_;
		gr = gr_;
		id_gr=id_gr_;
		x = x_;y=y_;z=z_;
		r =1;//default�ŃT�C�Y��^����
		edge = new Edge[0];
		ev = new Event[0];
		name = "";
		type = type_;
		hs = new Hashtable();
		hp = new Haplotype[0];
	}
}
