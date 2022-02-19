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
 * Graph�ɂ����āANode��Node�Ƃ̊֌W�̑��݂�\��
 */
public class Edge {
	/*
	 * field
	 */
	
	int id;
	Graph gr;//�Y���O���t
	int id_gr;//�O���t��id
	int direction;//0 undirected,1 directed
	int type;//-5��vine
	Node start;
	Node end;
	double length;
	String name;
	int label;//int�^�̖��� mutation��������
	int rec1;//recombination �̍�5'�[
	int rec2;//recombination �̍�3'�[
	Event ev[];
	Hashtable hs;//�������̏����i�[����
	Path pt;
				
	

	public void addId_gr(int id_){//id_gr�̒ǉ�
		id_gr=id;
	}
	public void copy(Edge ed_){
		gr = ed_.gr;
		direction = ed_.direction;
		type = ed_.direction;
		start = ed_.start;
		end = ed_.end;
		length = ed_.length;
		name = ed_.name;
		label = ed_.label;
		ev = new Event[ed_.ev.length];
		for(int i=0;i<ed_.ev.length;i++){
			ev[i]=ed_.ev[i];
		}
		
		pt = ed_.pt;
		
		//hs
	}
	public void addPath(Path pt_){
		pt = pt_;
	}
	public void addEvent(Event ev_){
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
	public boolean eaquality(Edge ed_){
		boolean bl=true;
		/*
		 * int direction;//0 undirected,1 directed
		 * int type;
		 * Node start;
		 * Node end;
		 * double length;
		 * ����炪��v���Ă�����A����Ƃ݂Ȃ�
		 */
		
		if(direction == ed_.direction){
		}else{
			bl=false;
		}
		if(type == ed_.type){
		}else{
			bl=false;
		}
		if(start == ed_.start){
		}else{
			bl=false;
		}
		if(end == ed_.end){
		}else{
			bl=false;
		}
		if(length == ed_.length){
		}else{
			bl=false;
		}
		return bl;
	}
	public boolean connection(Graph gr_){
		boolean bl=false;
		
		//System.out.println("R" + gr_.nodes.length);
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == start){
				bl = true;
			}
			if(gr_.nodes[i] == end){
				bl = true;
			}
		}
		return bl;
	}
	public boolean double_connection(Graph gr_){
		boolean bl;
		boolean bl1=false;
		boolean bl2=false;
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == start){
				bl1 = true;
			}
			if(gr_.nodes[i] == end){
				bl2 = true;
			}
		}
		bl = bl1 && bl2;
		return bl;
	}
	public boolean exist(Graph gr_){
		boolean bl=false;
		for(int i=0;i<gr_.edges.length;i++){
			bl=eaquality(gr_.edges[i]);
			if(bl == true){
				break;
			}
		}
		return bl;
	}

	public void printEdge(){
		String st ="";
		st += " Edge " ;
		st += " id " + id;
		st += " gr ";
		st += gr.id ;
		st += " direction " + direction;
		st += " label " + label;
		st += " start " + start.id;
		st += " end " + end.id;
		st += " length " + length;
		st += " event ";
		if(ev == null){
		}else{
			for(int i=0;i<ev.length;i++){
				st += ev[i].id + " ";
			}
		}
		System.out.println(st);
	}
	/*
	 * constructor
	 */
	public Edge(int id_,Node start_,Node end_){
		id = id_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		length=1;//default
		ev = new Event[0];
		hs = new Hashtable();
		
		
	}
	
	public Edge(int id_,Graph gr_,Node start_,Node end_,int type_){
		id = id_;
		gr = gr_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		type = type_;
		length=1;//default
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	public Edge(int id_,Graph gr_,int gr_id_,Node start_,Node end_,int type_){
		id = id_;
		gr = gr_;
		id_gr = gr_id_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		type = type_;
		length=1;//default
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	public Edge(int id_,Node start_,Node end_,int label_){
		id = id_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		length=1;//default
		label = label_;
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	public Edge(int id_,Node start_,Node end_,int type_,int label_){
		id = id_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		type = type_;
		length=1;//default
		label = label_;
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	public Edge(int id_,Graph gr_,Node start_,Node end_,int type_,int label_){
		id = id_;
		gr = gr_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		type = type_;
		length=1;//default
		label = label_;
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	public Edge(int id_,Graph gr_,int gr_id_,Node start_,Node end_,int type_,int label_){
		id = id_;
		gr = gr_;
		id_gr = gr_id_;
		start = start_;
		end = end_;
		direction=1;//�L����default�Ƃ���
		type = type_;
		length=1;//default
		label = label_;
		ev = new Event[0];
		hs = new Hashtable();
		
	}
	
}
