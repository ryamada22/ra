/*
 * �쐬��: 2005/07/15
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package treeANDvine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Hashtable;


/**
 * @author yamada
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 * �O���t�͖؂���(�����̖؂̏W��)��
 * �T�C�N�����������Ȃ�
 * �ɂ��ẮA�쐬�̉ߒ��ŋL�^���Ă����ƕ֗��Ȃ̂ŁA�L�^����
 */
public class Graph {
	/*
	 * field
	 */
	int id;
	Node nodes[];
	Edge edges[];
	int direction;//0 undirected,1 directed
	int type;//0 ARG, 1 Forest, 2 Tree
	int tr;//tree:1,non forests:2,����ȊO:3
	Event ev[];
	String name;
	int label;
	double or_x,or_y,or_z;//�O���t�̌��_�@�m�[�h�̕\���͂��̌��_�ƃm�[�h�̎����W�ƂŌ��߂�
	double len_x,len_y,len_z;//�����R������Ԃɂ�����O���t�̑��ݔ͈�
	Hashtable hs;

	
	/*
	 * method
	 */
	public void shift_or(double x_,double y_,double z_){
		or_x += x_;or_y += y_;or_z+=z_;
	}
	public void shift(double x_,double y_,double z_){
		for(int i=0;i<nodes.length;i++){
			nodes[i].x +=x_;nodes[i].y +=y_;nodes[i].z +=z_;
		}
	}
	public void scale(double x_,double y_,double z_){
		for(int i=0;i<nodes.length;i++){
			nodes[i].x *=x_;nodes[i].y *=y_;nodes[i].z *=z_;
		}
	}
	public void range(double x1,double x2,double y1,double y2,double z1,double z2){
		or_x = x1;or_y = y1;or_z=z1;
		len_x=x2;len_y=y2;len_z=z2;
	}
	public void addDirection(int di){
		direction = di;
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
	/*
	 * �O���t��{����
	 */
	public void addNode(Graph gr_,Node node_){
		/*
		 * �����̃O���t�ɂ���Ƃ͘A�����Ă��Ȃ��A��(�������_�̂�)���������Ƃɂ�����
		 * �m�[�h���O���t�̃m�[�h���X�g�ɉ�����
		 * �m�[�h�̏����O���t���L�ڂ���
		 * �m�[�h�O���t��ID��t�^����
		 * �����O���t�Ɨ^����ꂽ�_�Ƃ̊ԂɃG�b�W���Ȃ��̂ŁA�m�[�h�ɂȂ���G�b�W���̏��������͕s�v
		 * �_�̕t�^�ɂ��tree�������ω�����
		 * �_�������Ă��̂��ƂɁAplusEdge�����Ȃ��Ȃ�Atree�����͊m��AplusEdge�ƃZ�b�g�ōs���Ƃ��́A�A���̉\������
		 * 
		 */
		node_.gr=gr_;
		node_.id_gr=gr_.nodes.length;
		if(gr_.nodes == null){
			gr_.nodes = new Node[1];
			gr_.nodes[0]=node_;
		}else{
			Node tmp[];
			tmp = new Node[gr_.nodes.length +1];
			for(int i=0;i<gr_.nodes.length;i++){
				tmp[i] = gr_.nodes[i];
			}
			tmp[gr_.nodes.length]=node_;
			gr_.nodes = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.nodes[i] = tmp[i];
			}
			
		}
		if(gr_.nodes.length==1){
			gr_.tr=1;
		}else{
			if(gr_.tr ==1){
				gr_.tr=2;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
		}
		
		
	}
	public void takeawayNode(Graph gr_,Node nd_){//�P�Ƀm�[�h�̓o�^����
		Node tmp[];
		tmp= new Node[gr_.nodes.length-1];
		int counter=0;
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i]==nd_){
				gr_.nodes[i].gr=null;
				
			}else{
				tmp[counter]=gr_.nodes[i];
				tmp[counter].id_gr=counter;
				counter++;
				
			}
		}
		gr_.nodes=new Node[tmp.length];
		for(int i=0;i<tmp.length;i++){
			gr_.nodes[i]=tmp[i];
		}
		
	}
	public void takeawayEdge(Graph gr_,Edge edge_){//�P�ɃG�b�W�̓o�^����
		Edge tmp[];
		tmp= new Edge[gr_.edges.length-1];
		int counter=0;
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i]==edge_){
				gr_.edges[i].gr=null;
			}else{
				tmp[counter]=gr_.edges[i];
				tmp[counter].id_gr=counter;
				counter++;
				
			}
		}
		gr_.edges=new Edge[tmp.length];
		for(int i=0;i<tmp.length;i++){
			gr_.edges[i]=tmp[i];
		}
		
	}
	public void plusEdge(Graph gr_,Edge edge_){//�P��edge���G�b�W���X�g�ɉ����邾��
		edge_.gr=gr_;
		edge_.id_gr=gr_.edges.length;
		if(gr_.edges == null){
			gr_.edges = new Edge[1];
			gr_.edges[0]=edge_;
		}else{
			Edge tmp[];
			tmp = new Edge[gr_.edges.length +1];
			for(int i=0;i<gr_.edges.length;i++){
				tmp[i] = gr_.edges[i];
			}
			tmp[gr_.edges.length]=edge_;
			gr_.edges = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.edges[i] = tmp[i];
			}
		}
		
		
	}
	public void addEdge(Graph gr_,Edge edge_){
		/*
		 * edge�͗��[�_�Ƃ��������edge����Ȃ�
		 * ���̃G�b�W�͂܂��O���t�ɖ��o�^�Ȃ̂ŁA�����O���t���ɑS���������͈ꕔ�����݂���\��������
		 * ����������̃O���t�ɉ�����
		 * ���[�_�������̃O���t�ɂ��łɑ��݂��Ă��邩�ǂ����ŕς��
		 * ���[�_�Ƃ��ɑ��݂��Ȃ��ꍇ�ɂ́A2�m�[�h�P�G�b�W�̔�A���̖؂̒ǉ��ɑ�������
		 * �P�[�_�����݂��A�����P�[�_�����݂��Ȃ��ꍇ�ɂ́A�A�����邱�ƂɂȂ�
		 * ���[�_�����݂��A��������ԃG�b�W�����łɑ��݂��Ă���ꍇ�ɂ́A
		 *     ������G�b�W�������̃G�b�W�Ɠ����֌W��\���Ă���Ȃ�Ή������Ȃ�
		 * ���[�_�����݂��A��������ԃG�b�W�����݂��Ă��Ȃ��ꍇ�ɂ́A�G�b�W�݂̂�������
		 * ���[�_�����݂��A��������ԃG�b�W�����݂��Ă��邪�A2�d�G�b�W���������邱�Ƃ��K�؂ȏꍇ�ɂ́A�ǉ�����
		 * ���A�����O���t�̃G�b�W�̂��ׂĂƉ����悤�Ƃ��Ă���G�b�W�Ƃ̊Ԃ�Edge.eaquality�`�F�b�N���s���Atrue���Ԃ�Βǉ����Ȃ�
		 * false���Ԃ�΁A�ǉ�����
		 */
		boolean blconn = false;
		boolean dblconn = false;
		boolean blexist = false;
		blconn=edge_.connection(gr_);
		if(blconn == true){
			dblconn = edge_.double_connection(gr_);
			if(dblconn == true){
				blexist= edge_.exist(gr_);
			}
		}
		
		if(blexist == true){
			//System.out.println("already exist");
			//�������Ȃ�
		}else if(dblconn==true){
			//System.out.println("double connection");
			/*
			 * node�͑��₳���ɃG�b�W��������
			 * �G�b�W�̏����O���t��^����
			 * �G�b�W�ɂ́A�V���ȃO���t��ID��U��
			 * ������G�b�W�̗��[�_�̃G�b�W�����X�V����
			 * ������O���t�͕K���A�؂ł��тł��Ȃ�
			 */
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			gr_.tr=3;
			
			
		}else if(blconn == true){
			//System.out.println("connection");
			/*
			 * 1�_�ŘA��
			 * �P�_��ǉ�
			 * �m�[�h�̏����O���t��^����
			 * �G�b�W��ǉ�
			 * �G�b�W�̏����O���t��^����
			 * ���ꂼ��O���t��ID��U��
			 * �A���G�b�W�����X�V����
			 * ������O���t�͖؂ւ̒ǉ��Ȃ�A�؁A�тւ̒ǉ��Ȃ�сA����ȊO�ւ̒ǉ��Ȃ炻��ȊO
			 */
			Node tmpnd;
			tmpnd = new Node(-1);//��
			for(int i=0;i<gr_.nodes.length;i++){
				if(gr_.nodes[i] == edge_.start){
					tmpnd=edge_.end;
				}
				if(gr_.nodes[i] == edge_.end){
					tmpnd=edge_.start;
				}
			}
			tmpnd.gr=gr_;
			tmpnd.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,tmpnd);
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			if(gr_.tr==1){
				gr_.tr=1;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			
		}else{
			//System.out.println("independent");
			/*
			 * �Q�m�[�h�ǉ��A�P�G�b�W�ǉ�
			 * �m�[�h�ƃG�b�W�̏����O���t��^����
			 * �O���t��ID�̍X�V
			 * ���[�_�m�[�h�̘A���G�b�W���ɒ���
			 * ������O���t�͖؂ւ̒ǉ��Ȃ�A�сA�тւ̒ǉ��Ȃ�сA����ȊO�ւ̒ǉ��Ȃ炻��ȊO
			 */
			edge_.start.gr=gr_;
			edge_.end.gr=gr_;
			edge_.start.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,edge_.start);
			edge_.end.id_gr=gr_.nodes.length;
			gr_.addNode(gr_,edge_.end);
			edge_.gr=gr_;
			edge_.id_gr=gr_.edges.length;
			gr_.plusEdge(gr_,edge_);
			edge_.start.addEdge(edge_);
			edge_.end.addEdge(edge_);
			if(gr_.tr==1){
				gr_.tr=2;
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			
		}
	}

	public void addGraph(Graph graph1,Graph graph2){
		/*
		 * ��1�����O���t�ɑ�2�����O���t�̃m�[�h�ƃG�b�W���t�������
		 * �m�[�h�ƃG�b�W�ɂ̓O���t��ID���U��Ȃ������
		 * �m�[�h�ƃG�b�W�̓O���t�ɏ�������̂ŁA�m�[�h�ƃG�b�W�̏�����������������
		 * �����O���t�Ɨ^����ꂽ�_�Ƃ̊ԂɃG�b�W���Ȃ��̂ŁA�m�[�h�ɂȂ���G�b�W���̏��������͕s�v
		 * �����Ƃ��ė^����ꂽ�O���t�͋�ɂȂ�A��ɂ���
		 * �O���t�ƃO���t�����킹��Ƃ�
		 * �؁{�؁���
		 * �с{�؁���
		 * �с{�с���
		 * ��/�с{���̑������̑�
		 * ���̑��{���̑������̑��ł���
		 * */
		//�m�[�h�̕t������
		if(graph2.nodes == null){
		}else{
			if(graph1.nodes == null){
				graph1.nodes = new Node[graph2.nodes.length];
				for(int i=0;i<graph2.nodes.length;i++){
					graph1.nodes[i]=graph2.nodes[i];
				}
			}else{
				Node tmpnodes[];
				tmpnodes = new Node[graph1.nodes.length + graph2.nodes.length];
				for(int i=0;i<graph1.nodes.length;i++){
					tmpnodes[i]=graph1.nodes[i];
				}
				for(int i=graph1.nodes.length;i<graph1.nodes.length+graph2.nodes.length;i++){
					tmpnodes[i] = graph2.nodes[i-graph1.nodes.length];
				}
				graph1.nodes = new Node[tmpnodes.length];
				for(int i=0;i<tmpnodes.length;i++){
					graph1.nodes[i] = tmpnodes[i];
				}
			}
		}
		//�G�b�W�̕t������
		if(graph2.edges == null){
		}else{
			if(graph1.edges == null){
				graph1.edges = new Edge[graph2.edges.length];
				for(int i=0;i<graph2.edges.length;i++){
					graph1.edges[i]=graph2.edges[i];
				}

			}else{
				Edge tmpedges[];
				tmpedges = new Edge[graph1.edges.length + graph2.edges.length];
				for(int i=0;i<graph1.edges.length;i++){
					tmpedges[i]=graph1.edges[i];
				}
				for(int i=graph1.edges.length;i<graph1.edges.length+graph2.edges.length;i++){
					tmpedges[i] = graph2.edges[i-graph1.edges.length];
				}
				graph1.edges = new Edge[tmpedges.length];
				for(int i=0;i<tmpedges.length;i++){
					graph1.edges[i] = tmpedges[i];
				}
			}
		}
		//�O���t��ID�A�G�b�W��ID�̕t�������A���̏����O���t�̕t������
		for(int i=0;i<graph1.nodes.length;i++){
			graph1.nodes[i].id_gr=i;
			graph1.nodes[i].gr=graph1;
		}
		for(int i=0;i<graph1.edges.length;i++){
			graph1.edges[i].id_gr=i;
			graph1.edges[i].gr=graph1;
		}
		//tree���
		if(graph1.tr == 1){
			if(graph2.tr ==1){
				graph1.tr=2;
			}else if(graph2.tr ==2){
				graph1.tr =2;
			}else if(graph2.tr==3){
				graph1.tr=3;
			}
		}else if(graph1.tr==2){
			if(graph2.tr ==1){
				graph1.tr=2;
			}else if(graph2.tr ==2){
				graph1.tr =2;
			}else if(graph2.tr==3){
				graph1.tr=3;
			}
		}else if(graph1.tr==3){
			graph1.tr=3;
		}
	}
/*
 * �����܂Ń��\�b�h�͊m�FJuly23
 * constructor���m�F�ς�
 */
	public void removeEdge(Graph gr_,Edge ed){
		/*
		 * exist�`�F�b�N�����đ��݂��Ă�����A�m�[�h���c���Ď�苎��
		 * �m�[�h�̃G�b�W�o�^���C������
		 * �G�b�W�̃O���t��������ύX����
		 * tree�����͖؂���тցA�т���тցA
		 * ���̑�����͗т�؂ւ̕ω��̉\�������邪�A
		 * ���̊֐����ł͊m�F���Ȃ�
		 */
		boolean ex=false;
		ex= ed.exist(gr_);
		if(ex == false){
			//System.out.println("No exist");
		}else{
			//ed�o�^����
			Edge tmp[];
			tmp = new Edge[gr_.edges.length-1];
			int counter=0;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i] == ed){
				}else{
					tmp[counter]=gr_.edges[i];
					tmp[counter].id_gr=counter;
					counter++;
				}
			}
			gr_.edges = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				gr_.edges[i]=tmp[i];
			}
			ed.gr=null;
			//node�̃G�b�W���C��
			tmp = new Edge[ed.start.edge.length-1];
			counter=0;
			for(int i=0;i<ed.start.edge.length;i++){
				if(ed.start.edge[i] == ed){
				}else{
					tmp[counter]=ed.start.edge[i];
					counter++;
				}
			}
			ed.start.edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed.start.edge[i]=tmp[i];
			}
			tmp = new Edge[ed.end.edge.length-1];
			counter=0;
			for(int i=0;i<ed.end.edge.length;i++){
				if(ed.end.edge[i] == ed){
				}else{
					tmp[counter]=ed.end.edge[i];
					counter++;
				}
			}
			ed.end.edge = new Edge[tmp.length];
			for(int i=0;i<tmp.length;i++){
				ed.end.edge[i]=tmp[i];
			}
		}
		if(gr_.tr==1){
			gr_.tr=2;
		}else if(gr_.tr==2){
			gr_.tr=2;
		}else if(gr_.tr==3){
			gr_.tr=3;
		}
		
	}
	public void removeNode(Graph gr_,Node nd_){
		/*
		 * �m�[�h����苎��Ƃ́A�m�[�h�ƂƂ��ɂ���ɘA�����Ă���G�b�W����苎�邱��
		 * �m�[�h�����X�g����͂����A
		 * ���̃m�[�h�̃O���t������ύX
		 * ���ŁA�m�[�h�ɘA�����Ă����G�b�W�����ׂĂƂ肳��
		 * tr������
		 * �؂̗t(�G�b�W��{�łȂ����Ă���m�[�h)�̏����̏ꍇ�͖؂̂܂�
		 * �؂���t�ȊO�̃m�[�h����苎�������
		 * �т����苎��ƕ��ʂ͗т����A����ȏꍇ(��苎�邱�ƂłQ�{�̖؂���Ȃ�т̒��P�{�̖؂�
		 * �܂邲�Ǝ�苎������A�؁A�Ȃǂ̓���ȏꍇ�́A���ƂŁA�c�肪�؂��ǂ����̔��������΂悵
		 * ���̑�����̎�苎��͂��̑��̂܂�(�m���߂Ă݂Ȃ��ƕs��������j
		 * 
		 */
		
		//node�̑��݃`�F�b�N
		int check=0;
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == nd_){
				check=1;
			}
		}
		if(check==0){
			System.out.println("node not exist");
		}else{
			if(gr_.tr==1){
				if(nd_.edge.length>=2){
					gr_.tr=2;
				}else if(nd_.edge.length<2){
					gr_.tr=1;
				}
			}else if(gr_.tr==2){
				gr_.tr=2;
			}else if(gr_.tr==3){
				gr_.tr=3;
			}
			gr_.takeawayNode(gr_,nd_);
		
			for(int i=0;i<nd_.edge.length;i++){
				gr_.removeEdge(gr_,nd_.edge[i]);
			}
		}		
		
	}
	public void changeNode(Graph gr_,Node nw,Node old){
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i].start == old){
				gr_.edges[i].start = nw;
				//gr_.edges[i].start.takeawayEdge(gr_.edges[i]);
				nw.addEdge(gr_.edges[i]);
				Edge tmp[];
				tmp=new Edge[old.edge.length-1];
				int counter=0;
				for(int j=0;j<old.edge.length;j++){
					if(old.edge[j]==gr_.edges[i]){
						
					}else{
						tmp[counter]=old.edge[j];
						counter++;
					}
				}
				old.edge = new Edge[tmp.length];
				for(int j=0;j<tmp.length;j++){
					old.edge[j]=tmp[j];
				}
			}
			if(gr_.edges[i].end == old){
				gr_.edges[i].end = nw;
				//gr_.edges[i].end.takeawayEdge(gr_.edges[i]);
				nw.addEdge(gr_.edges[i]);
				Edge tmp[];
				tmp=new Edge[old.edge.length-1];
				int counter=0;
				for(int j=0;j<old.edge.length;j++){
					if(old.edge[j]==gr_.edges[i]){
						
					}else{
						//System.out.println("old node " + old.id);
						//System.out.println("j of node " + j);
						tmp[counter]=old.edge[j];
						counter++;
					}
				}
				old.edge = new Edge[tmp.length];
				for(int j=0;j<tmp.length;j++){
					old.edge[j]=tmp[j];
				}
			}
		}
	}
	//Tree�̏ꍇ
	public void removeEdgeAndGlue(Log lg_,Graph gr_,Edge ed_){
		/*
		 * �^����ꂽ�G�b�W���폜��
		 * ���̗��[�_��takeaway��
		 * �V�K�̃m�[�h���쐬�o�^��
		 * ���[�_�ɐڑ����Ă���G�b�W�����̐V�K�m�[�h�ɕt���ւ���
		 * tr�����͕ω����Ȃ�
		 */
		//gr_.printGraphAll();
		//System.out.println("removeEdge�O");
		gr_.removeEdge(gr_,ed_);
		//System.out.println("removeEdge��addNode�O");
		//gr_.printGraphAll();
		//gr_.takeawayNode(gr_,ed_.start);
		//gr_.takeawayNode(gr_,ed_.end);
		Node nwnd;
		double x_,y_,z_;
		x_=(ed_.start.x+ed_.end.x)/2+100;
		y_=(ed_.start.y+ed_.end.y)/2+100;
		z_=(ed_.start.z+ed_.end.z)/2+100;
		nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
		lg_.addNode(nwnd);
		//gr_.printGraphAllNodeEdge();
		gr_.addNode(gr_,nwnd);
		//System.out.println("addNode��changeNode1�O");
		//gr_.printGraphAllNodeEdge();
		gr_.changeNode(gr_,nwnd,ed_.start);
		
		//System.out.println("changeNode1��changeNode2�O");
		//gr_.printGraphAllNodeEdge();
		
		gr_.changeNode(gr_,nwnd,ed_.end);
		//System.out.println("changeNode2��takeawayNode1�O");
		//gr_.printGraphAllNodeEdge();
		gr_.removeNode(gr_,ed_.start);	
		//System.out.println("takeawayNode1��takeawayNode2�O");
		//gr_.printGraphAllNodeEdge();
		gr_.removeNode(gr_,ed_.end);	
		//System.out.println("takeawayNode2��haplotype���������O");
		//gr_.printGraphAllNodeEdge();
		
		//�n�v���^�C�v�֌W�̌p��
		
		for(int i=0;i<ed_.start.hp.length;i++){
			//�V�m�[�h�ւ̓o�^
			nwnd.addHaplotype(ed_.start.hp[i]);
			//�n�v���^�C�v�ւ̐V�m�[�h�̓o�^
			ed_.start.hp[i].addTreeNode(nwnd);
		}
		for(int i=0;i<ed_.end.hp.length;i++){
			nwnd.addHaplotype(ed_.end.hp[i]);
			ed_.end.hp[i].addTreeNode(nwnd);
		}
		//System.out.println("post removeEdgeAndGlue \n");
		//gr_.printGraphAllNodeEdge();
		
		
	}
	//�T�C�N���E���[�v����̏ꍇ
	public void removeEdgeAndGluePT(Log lg_,Graph gr_,Edge ed_){
		/*
		 * �^����ꂽ�G�b�W���폜��
		 * ���̗��[�_��takeaway��
		 * �V�K�̃m�[�h���쐬�o�^��
		 * ���[�_�ɐڑ����Ă���G�b�W�����̐V�K�m�[�h�ɕt���ւ���
		 * tr�����͕ω����Ȃ�
		 */
		/*
		 * tree�ł̏��u�ƃT�C�N���E���[�v���������ꍇ�̏��u�͈Ⴂ�����Ȃ̂ŁA
		 * �ʊ֐��Ƃ���
		 */
		/*
		 * �n�_�E�I�_�̓���̃G�b�W�̏����́A�Y���G�b�W�̓o�^���O���t�Ǝn�_�E�I�_�m�[�h���疕�����邾��
		 * 
		 */
		
		if(ed_.start==ed_.end){
			gr_.takeawayEdge(gr_,ed_);
			ed_.start.takeawayEdge(ed_);
		}else{
//			gr_.printGraphAll();
			//System.out.println("removeEdge�O");
			gr_.removeEdge(gr_,ed_);
			//System.out.println("removeEdge��addNode�O");
			//gr_.printGraphAll();
			//gr_.takeawayNode(gr_,ed_.start);
			//gr_.takeawayNode(gr_,ed_.end);
			Node nwnd;
			double x_,y_,z_;
			x_=(ed_.start.x+ed_.end.x)/2+100;
			y_=(ed_.start.y+ed_.end.y)/2+100;
			z_=(ed_.start.z+ed_.end.z)/2+100;
			nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
			lg_.addNode(nwnd);
			nwnd.addHaplotype(ed_.start.hp[0]);
			//gr_.printGraphAllNodeEdge();
			gr_.addNode(gr_,nwnd);
			//System.out.println("addNode��changeNode1�O");
			//gr_.printGraphAllNodeEdge();
			
			/*
			 * tree�̏ꍇ�Ƃ̈Ⴂ�́A�t���ւ���G�b�W�̒��ɁA
			 * ��苎��G�b�W�̎n�_�E�I�_���A���̎n�_�E�I�_�Ƃ��Ă�����̂�
			 * ���݂��邱�Ƃł���
			 * ������\�ߕʏ������Ă���
			 * edge�̎n�_�I�_�̕ύX�ƁA��苎��G�b�W�̋��n�_�E�I�_�m�[�h�̃G�b�W���̏�������
			 * 
			 */
			for(int i=0;i<gr_.edges.length;i++){
				//System.out.println("haitta");
				Node st=gr_.edges[i].start;
				Node end=gr_.edges[i].end;
				int point=0;
				if(st == ed_.start){
					point++;
				}else if(st == ed_.end){
					point++;
				}
				if(end == ed_.start){
					point++;
				}else if(end == ed_.end){
					point++;
				}
				//System.out.println("point " + point);
				if(point ==2){//�V�m�[�h����n�܂�A�V�m�[�h�ɏI���G�b�W�ł���
					gr_.edges[i].start=nwnd;
					gr_.edges[i].end=nwnd;
					nwnd.addEdge(edges[i]);
					nwnd.addEdge(edges[i]);
					ed_.start.takeawayEdge(edges[i]);
					ed_.end.takeawayEdge(edges[i]);
				}
				
			}
			
			gr_.changeNode(gr_,nwnd,ed_.start);
			
			//System.out.println("changeNode1��changeNode2�O");
			//gr_.printGraphAllNodeEdge();
			
			gr_.changeNode(gr_,nwnd,ed_.end);
			//System.out.println("changeNode2��takeawayNode1�O");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.start);	
			//System.out.println("takeawayNode1��takeawayNode2�O");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.end);	
			//System.out.println("takeawayNode2��haplotype���������O");
			//gr_.printGraphAllNodeEdge();
			
			//�n�v���^�C�v�֌W�̌p��
			
			for(int i=0;i<ed_.start.hp.length;i++){
				//�V�m�[�h�ւ̓o�^
				nwnd.addHaplotype(ed_.start.hp[i]);
				//�n�v���^�C�v�ւ̐V�m�[�h�̓o�^
				ed_.start.hp[i].addTreeNode(nwnd);
			}
			for(int i=0;i<ed_.end.hp.length;i++){
				nwnd.addHaplotype(ed_.end.hp[i]);
				ed_.end.hp[i].addTreeNode(nwnd);
			}
			//System.out.println("post removeEdgeAndGlue \n");
			//gr_.printGraphAllNodeEdge();
		}
		
		
		
	}
	public void removeEdgeAndGluePT4Retro(Log lg_,Graph gr_,Edge ed_){
		/*
		 * ���Ȃ������n�v���^�C�v��Tree�ɕt��������̂ł͂Ȃ��Aarg�ɕt��������
		 */
		/*
		 * �^����ꂽ�G�b�W���폜��
		 * ���̗��[�_��takeaway��
		 * �V�K�̃m�[�h���쐬�o�^��
		 * ���[�_�ɐڑ����Ă���G�b�W�����̐V�K�m�[�h�ɕt���ւ���
		 * tr�����͕ω����Ȃ�
		 */
		/*
		 * tree�ł̏��u�ƃT�C�N���E���[�v���������ꍇ�̏��u�͈Ⴂ�����Ȃ̂ŁA
		 * �ʊ֐��Ƃ���
		 */
		/*
		 * �n�_�E�I�_�̓���̃G�b�W�̏����́A�Y���G�b�W�̓o�^���O���t�Ǝn�_�E�I�_�m�[�h���疕�����邾��
		 * 
		 */
		/*
		 * ��苎��G�b�W�̎q�m�[�h��e�Ƃ���m�[�h(�P�{�̂͂�)�ɂ��Ă�
		 * �G�b�W�̃^�C�v�E���x���́A��苎�邻��ŏ㏑��
		 */
		for(int i=0;i<gr_.edges.length;i++){
			if(gr_.edges[i].start == ed_.end){
				//edge���x���̕t���ւ�
				gr_.edges[i].rec1=ed_.rec1;
				gr_.edges[i].rec2=ed_.rec2;
				gr_.edges[i].label=ed_.label;
				gr_.edges[i].type=ed_.type;
			}
		}
		if(ed_.start==ed_.end){
			gr_.takeawayEdge(gr_,ed_);
			ed_.start.takeawayEdge(ed_);
		}else{
//			gr_.printGraphAll();
			//System.out.println("removeEdge�O");
			gr_.removeEdge(gr_,ed_);
			//System.out.println("removeEdge��addNode�O");
			//gr_.printGraphAll();
			//gr_.takeawayNode(gr_,ed_.start);
			//gr_.takeawayNode(gr_,ed_.end);
			Node nwnd;
			double x_,y_,z_;
			x_=(ed_.start.x+ed_.end.x)/2+100;
			y_=(ed_.start.y+ed_.end.y)/2+100;
			z_=(ed_.start.z+ed_.end.z)/2+100;
			nwnd = new Node(lg_.nd.length,gr_,gr_.nodes.length,x_,y_,z_,0);
			lg_.addNode(nwnd);
			nwnd.addHaplotype(ed_.start.hp[0]);
			//gr_.printGraphAllNodeEdge();
			gr_.addNode(gr_,nwnd);
			//System.out.println("addNode��changeNode1�O");
			//gr_.printGraphAllNodeEdge();
			
			/*
			 * tree�̏ꍇ�Ƃ̈Ⴂ�́A�t���ւ���G�b�W�̒��ɁA
			 * ��苎��G�b�W�̎n�_�E�I�_���A���̎n�_�E�I�_�Ƃ��Ă�����̂�
			 * ���݂��邱�Ƃł���
			 * ������\�ߕʏ������Ă���
			 * edge�̎n�_�I�_�̕ύX�ƁA��苎��G�b�W�̋��n�_�E�I�_�m�[�h�̃G�b�W���̏�������
			 * 
			 */
			
			for(int i=0;i<gr_.edges.length;i++){
				//System.out.println("haitta");
				Node st=gr_.edges[i].start;
				Node end=gr_.edges[i].end;
				int point=0;
				if(st == ed_.start){
					point++;
				}else if(st == ed_.end){
					point++;
				}
				if(end == ed_.start){
					point++;
				}else if(end == ed_.end){
					point++;
				}
				//System.out.println("point " + point);
				if(point ==2){//�V�m�[�h����n�܂�A�V�m�[�h�ɏI���G�b�W�ł���
					gr_.edges[i].start=nwnd;
					gr_.edges[i].end=nwnd;
					nwnd.addEdge(edges[i]);
					nwnd.addEdge(edges[i]);
					ed_.start.takeawayEdge(edges[i]);
					ed_.end.takeawayEdge(edges[i]);
				}
				
			}
			
			gr_.changeNode(gr_,nwnd,ed_.start);
			
			//System.out.println("changeNode1��changeNode2�O");
			//gr_.printGraphAllNodeEdge();
			
			gr_.changeNode(gr_,nwnd,ed_.end);
			//System.out.println("changeNode2��takeawayNode1�O");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.start);	
			//System.out.println("takeawayNode1��takeawayNode2�O");
			//gr_.printGraphAllNodeEdge();
			gr_.removeNode(gr_,ed_.end);	
			//System.out.println("takeawayNode2��haplotype���������O");
			//gr_.printGraphAllNodeEdge();
			
			//�n�v���^�C�v�֌W�̌p��
			
			for(int i=0;i<ed_.start.hp.length;i++){
				//�V�m�[�h�ւ̓o�^
				nwnd.addHaplotype(ed_.start.hp[i]);
//				//ForRetro�o�[�W�����̈Ⴂ
				//�n�v���^�C�v�ւ̐V�m�[�h�̓o�^
				ed_.start.hp[i].addArgNode(nwnd);
				//ed_.start.hp[i].addTreeNode(nwnd);
			}
			for(int i=0;i<ed_.end.hp.length;i++){
				nwnd.addHaplotype(ed_.end.hp[i]);
				//ForRetro�o�[�W�����̈Ⴂ
				ed_.end.hp[i].addArgNode(nwnd);
				//ed_.end.hp[i].addTreeNode(nwnd);
			}
			//System.out.println("post removeEdgeAndGlue \n");
			//gr_.printGraphAllNodeEdge();
		}
		
		
		
	}
	public boolean treeCheck(){
		boolean ret;
		ret = true;
		int numnode;
		int numedge;
		if(nodes == null){
			ret = false;
		}else{
			numnode = nodes.length;
			if(numnode>0){
				if(edges == null){
					
				}else{
					numedge=edges.length;
				}
			}
			
		}
		return ret;
	}
	/*
	public boolean connectionCheck(){
		boolean ret;
		ret =true;
		if(nodes == null){
			ret = false;
		}else{
			//edge�������Ȃ��m�[�h������΁A�A���ł͂Ȃ�
			for(int i=0;i<nodes.length;i++){
				if(nodes[i].edge == null){
					ret =false;
					break;
				}else if(nodes[i].edge.length == 0){
					ret = false;
					break;
				}
			}
			//nodes[0]����n�߂āA�A���m�[�h���i�[���A�A���m�[�h�����S�m�[�h���ɒB���邩�ǂ����Ŕ��f����
			Node conn[];
			conn = new Node[1];
			conn[0]=nodes[0];
			Node nwconn[];
			nwconn = new Node[1];
			nwconn[0] = nodes[0];
			while(conn.length<nodes.length){
				//System.out.println("conn len " + conn.length);
				for(int k=0;k<nwconn.length;k++){
					Node tmpconn[];
					tmpconn = new Node[0];
					for(int i=0;i<nwconn[k].edge.length;i++){
						Node tmpnd;
						if(nwconn[k].edge[i].start == nwconn[k]){
							tmpnd = nwconn[k].edge[i].end;
						}else{
							tmpnd = nwconn[k].edge[i].start;
						}
						int tmpflag=0;
						for(int j=0;j<conn.length;j++){
							if(conn[j] == tmpnd){
								tmpflag=1;
							}
						}
						if(tmpflag == 0){
							Node tmptmpconn[];
							tmptmpconn = new Node[tmpconn.length+1];
							for(int j=0;j<tmpconn.length;j++){
								tmptmpconn[j]=tmpconn[j];
							}
							tmptmpconn[tmpconn.length]=tmpnd;
							tmpconn = new Node[tmptmpconn.length];
							for(int j=0;j<tmptmpconn.length;j++){
								tmpconn[j]=tmptmpconn[j];
							}
						}
					}
					nwconn = new Node[tmpconn.length];
					for(int j=0;j<tmpconn.length;j++){
						nwconn[j]=tmpconn[j];
					}
				}
				
				Node tmptmptmpconn[];
				tmptmptmpconn = new Node[conn.length + nwconn.length];
				for(int k=0;k<conn.length;k++){
					tmptmptmpconn[k]=conn[k];
				}
				for(int k=0;k<nwconn.length;k++){
					tmptmptmpconn[conn.length+k]=nwconn[k];
				}
				conn = new Node[tmptmptmpconn.length];
				for(int k=0;k<tmptmptmpconn.length;k++){
					conn[k]=tmptmptmpconn[k];
				}
				
				//if(nwconn.length == 0){
					//break;
				//}
			}
			if(conn.length == nodes.length){
				
			}else{
				ret = false;
			}
			//System.out.println("conn ");
			for(int i=0;i<conn.length;i++){
				System.out.println(conn[i]);
			}
			
		}
		
		return ret;
	}
	*/
	//Tree
	//Tree�Ƃ͐V���Ƀm�[�h��t��������Ƃ��ɂ��炽�ɃG�b�W��t�������邱�ƂŐ�������
	//���̂悤�Ȑ����̎d���������邱�ƂŁA���Ȃ炸�A���ł���A�T�C�N����L���Ȃ�
	//Tree���P�����ƈقȂ�_�́A���򂪂��邱�Ƃł���B����́A�Q����A�R����A�E�E�E�ł���
	public void growTree(Node nd_,Edge ed_){
		int ndcheck=0;
		int ndstartcheck=1;
		int ndendcheck=0;
		int edgecheck=0;
		int nodeinedgecheck=0;
		if(ed_.end == nd_){
			
		}else{
			nodeinedgecheck=1;
		}
		for(int i=0;i<nodes.length;i++){
			if(nodes[i] == nd_){//����������؂ɒǉ�����m�[�h���Ȃ�����
				ndcheck=1;
				
				break;
			}
			if(nodes[i]==ed_.start){//�ǉ�����G�b�W�̎n�_���؂̃m�[�h�ł��邱��
				ndstartcheck=0;
			}
			if(nodes[i]==ed_.end){//�ǉ�����G�b�W�̏I�_���؂̃m�[�h�łȂ�����
				ndendcheck=1;
				break;
			}
		}

		
		
		if(ndcheck==1){
			System.out.println("node error");
		}else if(ndstartcheck == 1){
			System.out.println("node start error");
		}else if(ndendcheck ==1){
			System.out.println("node end error");
		}else if(nodeinedgecheck==1){
			System.out.println("node in edge error");
		}else{
				
			Node tmpnd[];
			tmpnd = new Node[nodes.length+1];
			for(int i=0;i<nodes.length;i++){
				tmpnd[i]=nodes[i];
			}
			tmpnd[nodes.length]=nd_;
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
			}
			Edge tmped[];
			tmped = new Edge[edges.length+1];
			for(int i=0;i<edges.length;i++){
				tmped[i]=edges[i];
			}
			tmped[edges.length]=ed_;
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
			}
		}
	}
	public void addTree(Graph gr_,Node nd1_, Node nd2_, Edge ed_){//������ׂ��O���t�ƂȂ��ׂ��m�[�h�Q�_�A�����ɔ�������V�G�b�W
		int nd1check=1;
		int nd2check=0;
		int ndstartcheck=0;
		int ndendcheck=0;
		int nooverlapndcheck=0;
		int nodeinedgecheck=0;
		//edge�̎n�_�I�_�������m�[�h�Ɛ����������邩�ǂ����̃`�F�b�N
		if(ed_.start == nd1_){
			
		}else{
			nodeinedgecheck=1;
		}
		if(ed_.end == nd2_){
			
		}else{
			nodeinedgecheck=1;
		}
		//���O���t�ɂ͎n�_������A�I�_���Ȃ�����
		//�ǉ��O���t�ɂ͎n�_���Ȃ��A�I�_�����邱��
		for(int i=0;i<nodes.length;i++){
			if(nodes[i] == nd1_){
				nd1check=0;
				
				break;
			}
			if(nodes[i]==ed_.end){
				ndendcheck=1;
				break;
			}
		}
		for(int i=0;i<gr_.nodes.length;i++){
			if(gr_.nodes[i] == nd2_){
				nd2check=0;
				
				break;
			}
			if(nodes[i]==ed_.start){
				ndstartcheck=1;
				break;
			}
		}
		//���O���t�ƒǉ��O���t�̃m�[�h�ɃI�[�o�[���b�v���Ȃ�����
		for(int i=0;i<nodes.length;i++){
			for(int j=0;j<gr_.nodes.length;j++){
				if(nodes[i] == gr_.nodes[j]){
					nooverlapndcheck=1;
					break;
				}
			}
		}
		
		if(nd1check==1){
			System.out.println("node1 error");
		}else if(nd2check==1){
			System.out.println("node2 error");
		}else if(ndstartcheck == 1){
			System.out.println("node start error");
		}else if(ndendcheck ==1){
			System.out.println("node snd error");
		}else if(nodeinedgecheck==1){
			System.out.println("node in edge error");
		}else if(nooverlapndcheck==1){
			System.out.println("node overlap error");
		}else{
				
			Node tmpnd[];
			tmpnd = new Node[nodes.length+gr_.nodes.length];
			for(int i=0;i<nodes.length;i++){
				tmpnd[i]=nodes[i];
			}
			for(int i=0;i<gr_.nodes.length;i++){
				tmpnd[nodes.length+i] = gr_.nodes[i];
			}
			
			nodes = new Node[nodes.length+gr_.nodes.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
			}
			Edge tmped[];
			tmped = new Edge[edges.length+gr_.edges.length];
			for(int i=0;i<edges.length;i++){
				tmped[i]=edges[i];
			}
			for(int i=0;i<gr_.edges.length;i++){
				tmped[edges.length+i]=gr_.edges[i];
			}
			
			edges = new Edge[edges.length+gr_.edges.length + 1];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
			}
			edges[edges.length+gr_.edges.length]=ed_;
		}
	}
	
	public void rmEdge(Edge ed_){
		
		int edgecheck=1;
		for(int i=0;i<edges.length;i++){
			if(edges[i]==ed_){
				edgecheck=0;
				break;
			}
		}
		if(edgecheck == 1){
			//System.out.println("reEdge::edge check error");
		}else{
			//System.out.println("IN rmEdge pre edge list��������" + edges.length);
			Edge tmped[];
			tmped = new Edge[edges.length-1];
			int counter=0;
			for(int i=0;i<edges.length;i++){
				if(edges[i]==ed_){
					
				}else{
					tmped[counter]=edges[i];
					counter++;
				}
			}
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
				edges[i].id_gr=i;
			}
			//System.out.println("IN rmEdge post edge list��������" + edges.length);
			
			for(int i=0;i<nodes.length;i++){
				//System.out.println("IN rmEdge pre NODE EDGE list��������" + nodes[i].edge.length);
				Edge tmp[];
				boolean ari=false;
				for(int j=0;j<nodes[i].edge.length;j++){
					if(nodes[i].edge[j]==ed_){
						ari = true;
						//System.out.println("������������");
						break;
					}
				}
				if(ari == true){
					tmp = new Edge[nodes[i].edge.length-1];
					int cnt=0;
					for(int j=0;j<nodes[i].edge.length;j++){
						if(nodes[i].edge[j]!=ed_){
							tmp[cnt]=nodes[i].edge[j];
							//System.out.println("edge id " + tmp[cnt].id);
							cnt++;
						}
					}
					nodes[i].edge = new Edge[tmp.length];
					for(int j=0;j<tmp.length;j++){
						nodes[i].edge[j]=tmp[j];
					}
				}
				//System.out.println("IN rmEdge post NODE EDGE list��������" + nodes[i].edge.length);
			}
			//System.out.println("edge rm ssssss");
		}		
	}
	public boolean rmEdge_bl(Edge ed_){
		boolean ret=true;
		int edgecheck=1;
		for(int i=0;i<edges.length;i++){
			if(edges[i]==ed_){
				edgecheck=0;
				break;
			}
		}
		if(edgecheck == 1){
			//System.out.println("rmEdge_bl*****edge check error");
			ret = false;
		}else{
			Edge tmped[];
			tmped = new Edge[edges.length-1];
			int counter=0;
			//System.out.println("IN rmEdge_bl pre edge list��������" + edges.length);
			for(int i=0;i<edges.length;i++){
				if(edges[i]==ed_){
					
				}else{
					tmped[counter]=edges[i];
					counter++;
				}
			}
			edges = new Edge[tmped.length];
			for(int i=0;i<tmped.length;i++){
				edges[i]=tmped[i];
				edges[i].id_gr=i;
			}
			//System.out.println("IN rmEdge_bl post edge list��������" + edges.length);
			for(int i=0;i<nodes.length;i++){
				//System.out.println("IN rmEdge_bl pre NODE-EDGE list��������" + nodes[i].edge.length);
				Edge tmp[];
				boolean ari=false;
				for(int j=0;j<nodes[i].edge.length;j++){
					if(nodes[i].edge[j]==ed_){
						ari = true;
						//System.out.println("������������");
						break;
					}
				}
				
				if(ari == true){
				
					tmp = new Edge[nodes[i].edge.length-1];
					int cnt=0;
					for(int j=0;j<nodes[i].edge.length;j++){
						if(nodes[i].edge[j]==ed_){
								
						}else{
							tmp[cnt]=nodes[i].edge[j];
							cnt++;
						}
					}
					nodes[i].edge = new Edge[tmp.length];
					for(int j=0;j<tmp.length;j++){
						nodes[i].edge[j]=tmp[j];
					}

					
				}
				//System.out.println("IN rmEdge_bl post NODE-EDGE list��������" + nodes[i].edge.length);
			}
			//System.out.println("edge rm ssssss");
			
			
		}		
		return ret;
	}
	public void rmNode(Node nd_){
		
		int nodecheck=1;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i]==nd_){
				nodecheck=0;
				break;
			}
		}
		if(nodecheck == 1){
			//System.out.println("rmNode+++++++++++++node check error");
		}else{
			Node tmpnd[];
			tmpnd = new Node[nodes.length-1];
			int counter=0;
			for(int i=0;i<nodes.length;i++){
				if(nodes[i]==nd_){
					
				}else{
					tmpnd[counter]=nodes[i];
					counter++;
				}
			}
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
				nodes[i].id_gr=i;
			}
			//System.out.println("node rm sssss");
		}		
	}
	public boolean rmNode_bl(Node nd_){
		boolean ret=true;
		int nodecheck=1;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i]==nd_){
				nodecheck=0;
				break;
			}
		}
		if(nodecheck == 1){
			//System.out.println("rmNode_blLLLLLLLLLLLLLLnode check error");
			ret = false;
		}else{
			Node tmpnd[];
			tmpnd = new Node[nodes.length-1];
			int counter=0;
			for(int i=0;i<nodes.length;i++){
				if(nodes[i]==nd_){
					
				}else{
					tmpnd[counter]=nodes[i];
					counter++;
				}
			}
			nodes = new Node[tmpnd.length];
			for(int i=0;i<tmpnd.length;i++){
				nodes[i]=tmpnd[i];
				nodes[i].id_gr=i;
			}
			//System.out.println("node rm sssss");
		}
		return ret;
	}

	public void copy(Log lg_,Graph nwgr, Graph oldgr){
		/*
		 * �R�s�[�̃G�b�W�����̂܂ܓo�^
		 * �m�[�h�̃R�s�[����肻�̂܂ܓo�^
		 * �G�b�W�ɓo�^����Ă���m�[�h�A�m�[�h�ɓo�^����Ă���G�b�W������������
		 */
		Edge tmped;
		Node tmpnd;
		//nwgr.printGraphAllNodeEdge();
		nwgr.edges = new Edge[oldgr.edges.length];
		for(int i=0;i<oldgr.edges.length;i++){
			nwgr.edges[i]=oldgr.edges[i];
		}
		//System.out.println("A old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<oldgr.edges.length;i++){
			
			tmpnd = new Node(-1);
			
			tmped = new Edge(lg_.ed.length,tmpnd,tmpnd);
			lg_.addEdge(tmped);
			tmped.copy(oldgr.edges[i]);
			nwgr.edges[i] = tmped;
			nwgr.edges[i].gr=nwgr;
		}
		//System.out.println("B old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		nwgr.nodes = new Node[oldgr.nodes.length];
		for(int i=0;i<oldgr.nodes.length;i++){
			nwgr.nodes[i]=oldgr.nodes[i];
		}
		for(int i=0;i<oldgr.nodes.length;i++){
			tmpnd = new Node(lg_.nd.length);
			lg_.addNode(tmpnd);
			tmpnd.copy(oldgr.nodes[i]);
			nwgr.nodes[i]=tmpnd;
			nwgr.nodes[i].gr=nwgr;
		}
		//System.out.println("C old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<nwgr.nodes.length;i++){
			for(int j=0;j<nwgr.nodes[i].edge.length;j++){
				for(int k=0;k<oldgr.edges.length;k++){
					if(nwgr.nodes[i].edge[j] == oldgr.edges[k]){
						nwgr.nodes[i].edge[j]=nwgr.edges[k];
					}
				}
				
			}
		}
		//System.out.println("D old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		for(int i=0;i<nwgr.edges.length;i++){
			for(int k=0;k<oldgr.nodes.length;k++){
				if(nwgr.edges[i].start == oldgr.nodes[k]){
					nwgr.edges[i].start=nwgr.nodes[k];
				}
				if(nwgr.edges[i].end == oldgr.nodes[k]){
					nwgr.edges[i].end=nwgr.nodes[k];
				}				
			}
		}
		//System.out.println("E old and new\n");
		//oldgr.printGraphAllNodeEdge();
		//nwgr.printGraphAllNodeEdge();
		
		nwgr.direction = oldgr.direction;//0 undirected,1 directed
		nwgr.type = oldgr.type;//0 ARG, 1 Forest, 2 Tree
		nwgr.tr = oldgr.tr;//tree:1,non forests:2,����ȊO:3
		
		nwgr.name=oldgr.name;
		nwgr.label=oldgr.label;
		nwgr.or_x =oldgr.or_x;
		nwgr.or_y = oldgr.or_y;
		nwgr.or_z = oldgr.or_z;//�O���t�̌��_�@�m�[�h�̕\���͂��̌��_�ƃm�[�h�̎����W�ƂŌ��߂�
		nwgr.len_x = oldgr.len_x;
		nwgr.len_y = oldgr.len_y;
		nwgr.len_z = oldgr.len_z;//�����R������Ԃɂ�����O���t�̑��ݔ͈�
		//Hashtable hs;
		
	}
	public void recSeparation_less(Log lg_,Graph gr_,int recpoint){
		
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i].label>recpoint){
					looper =0;
				}
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr_.nodes.length];
				tmpedges = new Edge[gr_.edges.length];
				
				for(int i=0;i<gr_.nodes.length;i++){
					tmpnodes[i]=gr_.nodes[i];
				}
				for(int i=0;i<gr_.edges.length;i++){
					tmpedges[i]=gr_.edges[i];
				}
				for(int i=0;i<tmpedges.length;i++){
					
					if(tmpedges[i].label >recpoint){
						gr_.removeEdgeAndGlue(lg_,gr_,tmpedges[i]);
						break;
					}else{
						
					}
				}
			}
			
		}
		
	}
	public void recSeparation_more(Log lg_,Graph gr_,int recpoint){
		int looper=0;
		while(looper ==0){
			looper =1;
			for(int i=0;i<gr_.edges.length;i++){
				if(gr_.edges[i].label<=recpoint){
					if(gr_.edges[i].label>=0){
						looper =0;
					}
					
				}
			}
			if(looper ==0){
				Node tmpnodes[];
				Edge tmpedges[];
				tmpnodes = new Node[gr_.nodes.length];
				tmpedges = new Edge[gr_.edges.length];
				
				for(int i=0;i<gr_.nodes.length;i++){
					tmpnodes[i]=gr_.nodes[i];
				}
				for(int i=0;i<gr_.edges.length;i++){
					tmpedges[i]=gr_.edges[i];
				}
				for(int i=0;i<tmpedges.length;i++){
					if(tmpedges[i].label <=recpoint){
						if(tmpedges[i].label >=0){
							gr_.removeEdgeAndGlue(lg_,gr_,tmpedges[i]);
							break;
						}
					}else{
						
					}
				}
			}
		}
		
	}
	public void sortNode(){
		for(int i=0;i<nodes.length;i++){
			nodes[i].id_gr=nodes[i].id;
		}
	}
	public void sortEdge(){
		for(int i=0;i<edges.length;i++){
			edges[i].id_gr=edges[i].id;
		}
	}

	public void tree2forest(){
		if(type ==1){
			
			for(int i=0;i<nodes.length;i++){
				nodes[i].hp[nodes[i].hp.length-1].addForestNode(nodes[i]);
				
			}
		}else{
			//System.out.println("Not Forest");
		}
	}
	public void cleanNode(){
		/*
		 * �m�[�h�ɓo�^���ꂽ�G�b�WID�̂����A���݂��Ȃ����̂�����
		 * �O���t�ɓo�^���ꂽ�m�[�h���A�G�b�W���͕s��
		 */
		for(int i=0;i<nodes.length;i++){
			Edge tmpe[];
			int cnte=0;
			int check=0;
			for(int j=0;j<nodes[i].edge.length;j++){
				for(int k=0;k<edges.length;k++){
					if(nodes[i].edge[j]==edges[k]){
						cnte++;
					}
				}
			}
			if(cnte==nodes[i].edge.length){
				
			}else{
				System.out.println("CCCCCCCCCCCCCCCCCCCClean Node " );
			}
			tmpe = new Edge[cnte];
			cnte=0;
			for(int j=0;j<nodes[i].edge.length;j++){
				for(int k=0;k<edges.length;k++){
					if(nodes[i].edge[j]==edges[k]){
						tmpe[cnte]=edges[k];
						cnte++;
					}
				}
			}
			nodes[i].edge = new Edge[tmpe.length];
			for(int j=0;j<tmpe.length;j++){
				nodes[i].edge[j]=tmpe[j];
			}
		}
	}
	public void cleanEdge(){
		/*
		 * �O���t�̃G�b�W�̂����A�n�_�I�_�ɑΉ�����m�[�h�������Ȃ����̂�����
		 * �O���t�̃G�b�W������������
		 */
		Edge tmpe[];
		int cnte=0;
		for(int i=0;i<edges.length;i++){
			int startflag=0;
			int endflag=0;
			for(int j=0;j<nodes.length;j++){
				if(edges[i].start==nodes[j]){
					startflag=1;
				}
				if(edges[i].end==nodes[j]){
					endflag=1;
				}
				
			}
			if(startflag*endflag == 1){
				cnte++;
			}
		}
		tmpe = new Edge[cnte];
		cnte=0;
		for(int j=0;j<edges.length;j++){
			int startflag=0;
			int endflag=0;
			for(int k=0;k<nodes.length;k++){
				if(edges[j].start==nodes[k]){
					startflag=1;
				}
				if(edges[j].end==nodes[k]){
					endflag=1;
				}
				
			}
			if(startflag*endflag == 1){
				tmpe[cnte]=edges[j];
				cnte++;
			}else{
				System.out.println("CCCCCCCCCCCCCCCCCCCClean Edge");
			}
		}
		edges = new Edge[tmpe.length];
		for(int i=0;i<tmpe.length;i++){
			edges[i]=tmpe[i];
		}
	}
	/*
	 * Retrograde�֌W
	 */


	public Graph[] addToGraphArr(Graph grarr[],Graph gr){
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
	public Graph[] retroUp(Log lg,Graph gr1, Graph gr2){
		
		System.out.println("####################retroUP");
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		
		int preBackMut=tmparr[0].nodes.length;
		int preBackNodenum = tmparr[1].nodes.length;
		int preBackEdgenum = tmparr[1].edges.length;
		int preBackNodenum0 = tmparr[0].nodes.length;
		int preRetroMR = lg.retroMR.length;
		System.out.println("BackMut �v���@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
		tmparr = tmparr[0].backMut(lg,tmparr[0],tmparr[1]);//mutation�����炷
		int postRetroMR = lg.retroMR.length;
		preBackNodenum = tmparr[1].nodes.length;
		preBackEdgenum = tmparr[1].edges.length;
		preBackNodenum0 = tmparr[0].nodes.length;
		System.out.println("BackMut �|�X�g�@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
		int postBackMut=tmparr[0].nodes.length;
		
		//System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCcc");
		//boolean connected2 = tmparr[1].connectedGraph();
		//System.out.println("connection hanntei " + connected2);
		//System.out.println("connectionGraph " + tmparr[1].connectedGraph());
		boolean judge = true;//BackMut������������BackRec���邱�ƂȂ��A�o�^�����X�V���āA�T�C�h�ABackMut�ł��邩�ǂ���������
		//if(preBackMut-postBackMut==0){
		if(preRetroMR-postRetroMR==0){
			judge=false;//BackMut��lg��retroMR�ɐV�v�f���t�������Ȃ����
			System.out.println("BackMut���s");
			if(tmparr[0].nodes.length==2){
				System.out.println("��������!");
				System.out.println("tmparr[0].nodes[0]" + tmparr[0].nodes[0].id);
				System.out.println("tmparr[0].nodes[1]" + tmparr[0].nodes[1].id);
				tmparr[0].nodes[0].hp[0].printHaplotypeElem();
				tmparr[0].nodes[1].hp[0].printHaplotypeElem();
			}
		}else{
			System.out.println("BackMut����");
			//lg.addRetroMR(0);//retro������BackMut�����̂�0��o�^
		}
		/*
		if(postBackMut==1){
			judge=false;
		}
		
		*/
		if(judge==false){//BackMut���ł������͘A���`�F�b�N�EBackRec�ɂ͓���Ȃ�
//			�O���t�S�̂��A�����ǂ������`�F�b�N
			boolean connected = tmparr[1].connectedGraph();
			//connected���Ă��Ă�rec�ł̌����͖���
			connected = false;
			if(connected){
			}else if(postBackMut <3){
				//�Q�m�[�h�ɂ܂Ō�������Arecombination�̍l���͕s�v�A���A�e�q�R�m�[�h��I�ׂȂ�
			}else{
//				judge = tmparr[1].judgeARGComplete();
				//System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
				preBackMut=tmparr[0].nodes.length;
				preBackNodenum = tmparr[1].nodes.length;
				preBackEdgenum = tmparr[1].edges.length;
				preBackNodenum0 = tmparr[0].nodes.length;
				System.out.println("BackRec �v���@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				preRetroMR = lg.retroMR.length;
				//tmparr = tmparr[0].backRec(lg,tmparr[0],tmparr[1]);
				tmparr = tmparr[0].backRec2(lg,tmparr[0],tmparr[1]);
				postRetroMR = lg.retroMR.length;
				preBackNodenum = tmparr[1].nodes.length;
				preBackEdgenum = tmparr[1].edges.length;
				preBackNodenum0 = tmparr[0].nodes.length;
				System.out.println("BackRec �|�X�g�@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				postBackMut=tmparr[0].nodes.length;
				//judge = true;
				//tmparr = tmp1.backRec(lg,tmparr[0],tmparr[1]);
				//if(preBackMut-postBackMut==0){
				if(preRetroMR-postRetroMR==0){
					//retorMR�����s����
					//lg.addRetroMR(3);//retro������BackRec���s�����Ȃ̂�2��o�^	
				}else{
					//retroMR��������
					//lg.addRetroMR(2);//retro������BackRec�����̂�1��o�^
				}
			}
		}
		
		
		
		return tmparr;
	}
	public Graph[] backMut(Log lg,Graph gr1, Graph gr2){
		//System.out.println("$$$$$$$$$$$$$$$$$$$backMut");
		/*
		for(int i=0;i<gr2.nodes.length;i++){
			System.out.println("node's i & edge len " + i + " " + gr2.nodes[i].edge.length);
		}
		*/
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		/*
		
		Node tmpnodesgr1[];
		tmpnodesgr1 = new Node[gr1.nodes.length];
		for(int i=0;i<tmpnodesgr1.length;i++){
			tmpnodesgr1[i]=gr1.nodes[i];
		}
		*/
		int hpmat[][];
		int numnd = tmparr[0].nodes.length;
		//int numsnp = tmp1.nodes[0].hp[0].hp.length;
		//System.out.println("numsnp " + numsnp);
		int numsnp = lg.numsnp;
		hpmat = new int[numnd][numsnp];
		for(int i=0;i<numnd;i++){
			for(int j=0;j<numsnp;j++){
				hpmat[i][j]=0;
				//hpmat[i][j]=tmp1.nodes[i].hp[0].hp[j];
			}
		}
		for(int i=0;i<numnd;i++){
			for(int j=0;j<tmparr[0].nodes[i].hp[0].hp.length;j++){
				hpmat[i][tmparr[0].nodes[i].hp[0].hp[j]]=1;
			}
		}
		
		boolean looper=true;
		if(tmparr[0].nodes.length==1){
			looper=false;
		}
		//int onlyonemut=0;
		for(int j=0;j<numsnp;j++){
			/*
			//�P�x�ɂPSNP mutation�̏����������Ė߂�
			if(j==1){
				System.out.println("break j=" + j);
				break;
			}
			System.out.println("not break j=" + j);
			onlyonemut=1;
			*/
			if(looper==false){
				break;
			}else{
				int tmpcount=0;
				int maf=0;
				int Maf=1;
				for(int i=0;i<numnd;i++){
					if(hpmat[i][j]==0){
						tmpcount++;
					}
				}
				//System.out.println("tmpcount.numnd " + tmpcount + "/" + numnd);
				if(tmpcount==numnd-1){
					tmpcount=1;
					if(numnd==2){
							
					}else{
						maf=1;//numnd���Q����ŁAtmpcount��numnd-1�ȏꍇ�ɁAminor allele freq�͂P�A����ȊO�͂O
						Maf=0;
					}
						
				}
				//System.out.println("tmpcount " + tmpcount);
				if(tmpcount==1){
						//����SNP�͒P��SNP
						//maf���O���P���̏��͕ێ����Ă���
						//���̒P�ƃA���������m�[�h�E�n�v���^�C�v�������邱��
						//���̃A������major�ɓ]�����n�v���^�C�v���e�ł���A���̐e���A���݂̃n�v���^�C�v�Z�b�g�ɑ��݂��Ă��Ȃ����
						//�ǉ�����B���݂��Ă���΁A���̃m�[�h�̎q�Ƃ���
					int findchild=0;
					int child=0;
					int parenthp[];
					parenthp=new int[numsnp];
					for(int i=0;i<numnd;i++){
						if(hpmat[i][j]==maf){
							child=i;
							findchild=1;
							for(int k=0;k<numsnp;k++){
								if(k==j){
									parenthp[k]=Maf;
								}else{
									parenthp[k]=hpmat[i][k];
								}
									
							}
							looper = false;
							break;
							
						}
					}
					if(findchild==0){
						//System.out.println("no child found");
						looper = false;
						
					}else{
//						child��gr2�Y���m�[�h��I��
						Node gr2child;
						//System.out.println("child " + child);
						//System.out.println("gr1.nodes[child].hp.length " + gr1.nodes[child].hp.length);
						//System.out.println("gr1.nodes[child].hp[gr1.nodes[child].hp.length-1].forestnd.length " + gr1.nodes[child].hp[gr1.nodes[child].hp.length-1].forestnd.length);
						gr2child=tmparr[0].nodes[child].hp[tmparr[0].nodes[child].hp.length-1].argnd;
						Haplotype childHp=tmparr[0].nodes[child].hp[tmparr[0].nodes[child].hp.length-1];
						//gr2child=tmpnodesgr1[child].hp[tmpnodesgr1[child].hp.length-1].forestnd[tmpnodesgr1[child].hp[tmpnodesgr1[child].hp.length-1].forestnd.length-1];
							
						int oya=-9;
						Node gr2oya;
						for(int i=0;i<numnd;i++){
							int jd=0;
							for(int k=0;k<numsnp;k++){
								if(hpmat[i][k] == parenthp[k]){
									
								}else{
									jd =1;
								}
							}
							if(jd==0){//���ꂪparent
								oya=i;
								break;
								/*
								 * ����gr2�̕��Y���m�[�h��T��
								 */
									
								//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
							}
						}
							
						if(oya==-9){//parent �Ȃ�
								/*
								 * gr2�Ƀm�[�h�������āA����Ƀm�[�hj���Ȃ�
								 * gr1�ɂ��m�[�h��������
								 * gr1����A�m�[�hj���̂���
								 */
							//System.out.println("Parent�Ȃ�");
							Haplotype tmphp;
							//{0,1}�\�L��haplotype�\�L����A{1}�̂ݕ\�L��
							int snps[]={};
							for(int k=0;k<parenthp.length;k++){
								if(parenthp[k]==1){
									int tmp[];
									tmp = new int[snps.length+1];
									for(int l=0;l<snps.length;l++){
										tmp[l]=snps[l];
									}
									tmp[snps.length]=k;
									snps = new int[tmp.length];
									for(int l=0;l<tmp.length;l++){
										snps[l]=tmp[l];
									}
								}
							}
							//System.out.println("snps len " + snps.length);
							//tmphp = new Haplotype(lg.hp.length,parenthp);
							tmphp = new Haplotype(lg.hp.length,snps);
							lg.addHaplotype(tmphp);
							int st[]={0};
							int end[]={lg.numsnp-1};
							tmphp.addStEnd(st,end);
							Node tmpnd1,tmpnd2;
							tmpnd1=new Node(lg.nd.length,tmparr[0],tmparr[0].nodes.length);
							lg.addNode(tmpnd1);
							tmpnd1.addHaplotype(tmphp);
							tmphp.addForestNode(tmpnd1);
							//gr1�̃m�[�h���͕ς��Ȃ�
							tmparr[0].addNode(tmparr[0],tmpnd1);
							tmparr[0].removeNode(tmparr[0],tmparr[0].nodes[child]);
								
							tmpnd2=new Node(lg.nd.length,tmparr[1],tmparr[1].nodes.length);
							//tmpnd2.copy(tmpnd1);
							lg.addNode(tmpnd2);
							tmpnd2.addHaplotype(tmphp);
							tmphp.addArgNode(tmpnd2);
//							//gr2�̃m�[�h����1������
							tmparr[1].addNode(tmparr[1],tmpnd2);
								
							Edge tmped;
							tmped = new Edge(lg.ed.length,tmparr[1],tmpnd2,gr2child,1);//j��mutation
							tmped.label=j;
							//System.out.println("parent �Ȃ��@pre addEdge " + tmparr[1].nodes.length);
							tmparr[1].addEdge(tmparr[1],tmped);//���[�_�̃G�b�W���͂����ōX�V
							//System.out.println("parent �Ȃ��@post addEdge " + tmparr[1].nodes.length);
							lg.addEdge(tmped);
							//tmpnd2.addEdge(tmped);
							//gr2child.addEdge(tmped);
							lg.addRetroMR(1);//retro�����Őe�Ȃ�BackMut�����̂�1��o�^
							lg.addRetroHpChild(childHp);
							lg.addRetroHpParent1(tmphp);
							lg.addRetroHpParent2(null);
							
							return tmparr;//parent�Ȃ��̂Ƃ��́A����SNP�̈Ⴂ�������Ă��Pmutation�ɂ��Đe���������I���ɂ���
							//break;
							
						}else{//parent����
								/*
								 * tmparr[1]�̃m�[�hjd�ƃm�[�hj���Ȃ�
								 * tmparr[0]����A�m�[�hj���̂���
								 */
							//gr2oya=tmparr[0].nodes[oya].hp[0].forestnd[tmparr[0].nodes[oya].hp[0].forestnd.length-1];
							gr2oya=tmparr[0].nodes[oya].hp[0].argnd;
							Haplotype parent1Hp = tmparr[0].nodes[oya].hp[0];
							Edge tmped;
							tmped = new Edge(lg.ed.length,tmparr[1],gr2oya,gr2child,j);//j��mutation
							tmped.label=j;
							//System.out.println("parent ����@pre addEdge " + tmparr[1].nodes.length);
							tmparr[1].addEdge(tmparr[1],tmped);//���[�_�̃G�b�W���͂����ōX�V
							//System.out.println("parent ����@post addEdge " + tmparr[1].nodes.length);
							lg.addEdge(tmped);
							//gr2oya.addEdge(tmped);
							//gr2child.addEdge(tmped);
							tmparr[0].removeNode(tmparr[0],tmparr[0].nodes[child]);
							lg.addRetroMR(0);//retro������BackMut�����̂�0��o�^
							lg.addRetroHpChild(childHp);
							lg.addRetroHpParent1(parent1Hp);
							lg.addRetroHpParent2(null);
								
						}
					}
					
				}
			}
				
		}

		return tmparr;
	}
	//���̐e�q�֌W��recombination���\���ǂ����𒲂ׂ�
	//�e�q�֌W�𖞂������߂ɕK�v�Ȍ����ӏ����ɐ����͂Ȃ�
	public Graph[] backRec(Log lg,Graph gr1,Graph gr2){
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!backRec");
		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		;
		int hpmat[][];
		int numnd = tmparr[0].nodes.length;
		//int numsnp = tmp1.nodes[0].hp[0].hp.length;
		//System.out.println("numsnp " + numsnp);
		int numsnp = lg.numsnp;
		hpmat = new int[numnd][numsnp];
		for(int i=0;i<numnd;i++){
			for(int j=0;j<numsnp;j++){
				hpmat[i][j]=0;
				//hpmat[i][j]=tmp1.nodes[i].hp[0].hp[j];
			}
		}
		for(int i=0;i<numnd;i++){
			for(int j=0;j<tmparr[0].nodes[i].hp[0].hp.length;j++){
				hpmat[i][tmparr[0].nodes[i].hp[0].hp[j]]=1;
			}
		}
		//recombinant�������_���ɑI��
		int judge=0;
		int rand1,rand2,rand3;
		rand1=rand2=rand3=0;
		while(judge==0){//oya1,2,ko�̂R�҂��قȂ�܂ŗ����𔭐�������
			rand1 = (int)(Math.random()*tmparr[0].nodes.length);
			rand2 = (int)(Math.random()*tmparr[0].nodes.length);
			rand3 = (int)(Math.random()*tmparr[0].nodes.length);
			int dif1,dif2,dif3;
			dif1=rand1-rand2;
			dif2=rand2-rand3;
			dif3=rand3-rand1;
			judge=dif1*dif2*dif3;
		}
		
		Node rec = tmparr[0].nodes[rand1];
		Node oya1 = tmparr[0].nodes[rand2];
		Node oya2 = tmparr[0].nodes[rand3];
		//���̐e�q�֌W��recombination���\���ǂ����𒲂ׂ�
		//�e�q�֌W�𖞂������߂ɕK�v�Ȍ����ӏ����ɐ����͂Ȃ�
		int judgerec=0;
		for(int i=0;i<lg.numsnp;i++){
			int judgeallele=1;
			/*
			System.out.println("i " + i);
			System.out.println("rec " + hpmat[rand1][i]);
			System.out.println("oya1 " + hpmat[rand2][i]);
			System.out.println("oya2 " + hpmat[rand3][i]);
			*/
			
			if(hpmat[rand1][i]==hpmat[rand2][i]){
			//if(rec.hp[0].hp[i]==oya1.hp[0].hp[i]){
				judgeallele=0;
			}
			if(hpmat[rand1][i]==hpmat[rand3][i]){
			//if(rec.hp[0].hp[i]==oya2.hp[0].hp[i]){
				judgeallele=0;
			}
			if(judgeallele==1){
				judgerec=1;
				break;
			}
		}
		if(judgerec==0){
			//rec��������
			/*
			 * tmparr[1]�̃m�[�h rec �ƃm�[�hoya1, oya2���Ȃ�
			 * tmparr[0]����A�m�[�hrec���̂���
			 */
			System.out.println("backRec ����");
			//System.out.println("rand123 " + rand1 + " " + rand2 + " " + rand3);
			int preBackNodenum = tmparr[1].nodes.length;
			int preBackEdgenum = tmparr[1].edges.length;
			int preBackNodenum0 = tmparr[0].nodes.length;
			//System.out.println("BackRec���@�v���@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
			//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
			//rec,oya1,oya2��gr1�̂��́Agr2�̑Ή��m�[�h��I��
			Node rec_gr2,oya1_gr2,oya2_gr2;
			/*
			rec_gr2 = rec.hp[rec.hp.length-1].forestnd[rec.hp[rec.hp.length-1].forestnd.length-1];
			oya1_gr2 = oya1.hp[oya1.hp.length-1].forestnd[oya1.hp[oya1.hp.length-1].forestnd.length-1];
			oya2_gr2 = oya2.hp[oya2.hp.length-1].forestnd[oya2.hp[oya2.hp.length-1].forestnd.length-1];
			*/
			rec_gr2 = rec.hp[0].argnd;
			oya1_gr2 = oya1.hp[0].argnd;
			oya2_gr2 = oya2.hp[0].argnd;
			Edge tmped;
			tmped = new Edge(lg.ed.length,tmparr[1],oya1_gr2,rec_gr2,-9);
			tmparr[1].addEdge(tmparr[1],tmped);
			lg.addEdge(tmped);
			//rec_gr2.addEdge(tmped);
			//oya1_gr2.addEdge(tmped);
			tmped = new Edge(lg.ed.length,tmparr[1],oya2_gr2,rec_gr2,-9);
			tmp2.addEdge(tmparr[1],tmped);
			lg.addEdge(tmped);
			//rec_gr2.addEdge(tmped);
			//oya2_gr2.addEdge(tmped);
			
			tmparr[0].removeNode(tmparr[0],rec);
			preBackNodenum = tmparr[1].nodes.length;
			preBackEdgenum = tmparr[1].edges.length;
			preBackNodenum0 = tmparr[0].nodes.length;
			//System.out.println("BackRec���@�|�X�g�@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
			lg.addRetroMR(2);//retroMR����
			lg.addRetroHpChild(rec.hp[0]);
			lg.addRetroHpParent1(oya1.hp[0]);
			lg.addRetroHpParent2(oya2.hp[0]);
		}else{
			System.out.println("backRec ���s");
			//lg.addRetroMR(3);//retroMR����
		}
		return tmparr;
	}
	
	/*
	 * �n�v���^�C�v�W������P�n�v���^�C�v�������_���ɑI�����A
	 * ���̂P�n�v���^�C�v���������A�c��̃n�v���^�C�v�̏W��(���̃n�v���^�C�v�W���̕����W��)�A
	 * ����сA�S���ɂ��āA�����w��������
	 * �����w�́A�ċA�I�ȏ����ł���A�n�v���^�C�v�W���Ƃ��̂P�̃n�v���^�C�v�Ə����Ώې���(���������̏W��)
	 * �������Ƃ��Ď󂯎��
	 * ���̐������������������n�v���^�C�v�W�����ɂ��邩�ǂ����𔻒f��
	 * ����΁A�����e�Ƃ��A������ׂ��O���t�������������A
	 * �Ȃ���΁A�n�v���^�C�v������ɕ������A���̐����W���ɑ΂��A�ēx
	 * �n�v���^�C�v�ƃn�v���^�C�v�W���ɂ��ď����w���{��
	 * ���A�͂��߂ɗ^����ꂽ�n�v���^�C�v�ɂ��āA���ׂĂ̑��^�T�C�g�̃A�����ɂ��āA
	 * ���Ȃ炸���g�ȊO����Ȃ�n�v���^�C�v�W���̒��ɓ���A���������n�v���^�C�v�����݂��Ă���Ƃ����
	 * �������͒l�Ƃ���΁A�ċA�I�������ő��ő��^�T�C�g�̐������s�����ƂŕK���������߂���
	 */
	public Graph[] backRec2(Log lg,Graph gr1,Graph gr2){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!backRec2");

		Graph tmp1,tmp2;
		tmp1 = gr1;
		tmp2 = gr2;
		Graph tmparr[];
		tmparr = new Graph[2];
		tmparr[0]=tmp1;
		tmparr[1]=tmp2;
		
		//randomly nominate recombinant
		int rand1;
		rand1 = (int)(Math.random()*tmparr[0].nodes.length);
		
		//recombinant���������m�[�h�̃��X�g�����(haplotype�̃��X�g�Ɠ��������A
		//������v����n�v���^�C�v��gr1�̃m�[�h���X�g�Ƃ��ĊǗ����Ă���̂ŁA�m�[�h�ŏ�������
		
		Node rest[];
		rest = new Node[tmparr[0].nodes.length-1];
		int cnt=0;
		for(int i=0;i<rest.length+1;i++){
			if(i==rand1){
			}else{
				rest[cnt] = tmparr[0].nodes[i];
				cnt++;
			}
			
		}
		//�����͈͂̃��X�g
		int st_seg[] = {0};
		int end_seg[] = {lg.numsnp-1};
		
		//int maxnumiter=5;
		
		int numiter=0;

		//�ċA�I����
		//�L���g�����ʒu��ԍ��w��
		//�L���g�����ʒu�̗����������ēn��
		//�[���ɂ��A�ǂ��ɑg�������N���������w�肷��
		int allsnps[]={};
		int toaddsnps[]={};
		allsnps = new int[rest[0].hp[0].hp.length];
		for(int i=0;i<rest[0].hp[0].hp.length;i++){
			allsnps[i]=rest[0].hp[0].hp[i];
		}
		//allsnps=rest[0].hp[0].hp;
		for(int i=1;i<rest.length;i++){
			toaddsnps=MiscUtil.rmIdentical(rest[i].hp[0].hp,allsnps);
			allsnps=MiscUtil.addintarray(allsnps,toaddsnps);
		}
		String chRbp="snps ��r";
		for(int i=0;i<tmparr[0].nodes[rand1].hp[0].hp.length;i++){
			chRbp+=tmparr[0].nodes[rand1].hp[0].hp[i] + " ";
		}
		chRbp+="\nallsnps";
		for(int i=0;i<allsnps.length;i++){
			chRbp+=allsnps[i] + " ";
		}
		//System.out.println(chRbp);
		//MiscUtil.quick_sort_int(allsnps);
		int maxdepth=allsnps.length-1;//�g�����_�́A���^�Ԃɔ�������
		//int maxdepth=allsnps.length;
		for(int i=0;i<allsnps.length;i++){
			System.out.println("i valus" + i + " " + allsnps[i]);
		}
		System.out.println("allsnps len " + allsnps.length);
		MiscUtil2 m;
		m = new MiscUtil2();
		m.quickSort(allsnps,0,allsnps.length-1);
		int snpinterval[]=new int[allsnps.length-1];
		//allsnps�̂����ő�̂��̂�����
		for(int i=0;i<allsnps.length-1;i++){
			snpinterval[i]=allsnps[i];
		}
		MiscUtil.shuffle(snpinterval);
		//numiter�͑g�������̐[���̎w�W�Ȃ̂ŁA�ċA�֐�backRecNoParent�̎��{�ɂ������ẮA�u�[���v���J�E���g���Ă���
		//backRecNoParent(lg,tmparr[0],tmparr[1],tmparr[0].nodes[rand1],rest,st_seg,end_seg,lg.backrecdepth,numiter);
		int rec1=9;int rec2=8;//�����rec�ʒu�͂Ȃ�ł��悢
		backRecNoParent(lg,tmparr[0],tmparr[1],tmparr[0].nodes[rand1].hp[0].argnd,rest,st_seg,end_seg,maxdepth,numiter,snpinterval,rec1,rec2,allsnps);
		tmparr[0].removeNode(tmparr[0],nodes[rand1]);
		
		return tmparr;
	}
	
	/*
	 * 
	 */
	public void backRecNoParent(Log lg, Graph gr1,Graph gr2,Node child,Node[] rest,
			int[] st_seg,int[] end_seg,int maxnumiter, int numiter,int[] allsnps,int rec1,int rec2,int[] allsnps_or){
		System.out.println("maxnumiter " + maxnumiter + " depth " + numiter);
		System.out.println("rec1 rec2 " + rec1 + " " + rec2);
		if(numiter>=maxnumiter+1){
			//�ċA�����񐔂ɏ����^���A������]�v�ɂ͍s��Ȃ�
		}else{
			for(int i=0;i<st_seg.length;i++){
				System.out.println("st end " + st_seg[i] + " " + end_seg[i]);
			}
			int depth;
			System.out.println("pre depth " + numiter);
			depth = numiter;
			System.out.println("post depth " + depth);
			int numnd=rest.length;
			int kohp[] = child.hp[0].hp;
			int okParent[];
			okParent = new int[0];
			String childhp="kohp ";
			for(int i=0;i<child.hp[0].hp.length;i++){
				childhp+=child.hp[0].hp[i] + " ";
			}
			System.out.println(childhp);
			for(int i=0;i<numnd;i++){
				//�Y���Z�O�����g�͈̔͂ɂ����ẮA�e�n�v���^�C�v�Ǝq�n�v���^�C�v�ƂŃA���������S�Ɉ�v����
				//����́A���Aoyahp[],kohp[]�Ƃ����Ƃ��ɁA
				/*
				 * int[]����int[]�ɂ���v�f����菜��
				 * public static int[] rmIdentical(int[] ar1, int[] ar2)
				 * �ɂ�oyahp[]-kohp[]�ɂ�
				 * kohp[]-oyahp[]�ɂ��v�f���Ȃ����Ƃ��K�v�\���ł���
				*/ 
				int oyahp[] = rest[i].hp[0].hp;
				String parenthp="oyahp ";
				for(int j=0;j<rest[i].hp[0].hp.length;j++){
					parenthp+=rest[i].hp[0].hp[j] + " ";
				}
				System.out.println(parenthp);
				int judge=0;
				String oyako="oya ";
				for(int j=0;j<oyahp.length;j++){
					oyako +=oyahp[j] + " ";
				}
				oyako+=" ko ";
				for(int j=0;j<kohp.length;j++){
					oyako +=kohp[j] + " ";
				}
				int oya_ko[] = MiscUtil.rmIdentical(oyahp,kohp);
				oyako+=" oya_ko ";
				for(int j=0;j<oya_ko.length;j++){
					oyako +=oya_ko[j] + " ";
				}
				int ko_oya[] = MiscUtil.rmIdentical(kohp,oyahp);
				oyako+=" ko_oya ";
				for(int j=0;j<ko_oya.length;j++){
						oyako +=ko_oya[j] + " ";
				}
				//System.out.println(oyako);
				
				for(int j=0;j<oya_ko.length;j++){
					for(int k=0;k<st_seg.length;k++){
						if(oya_ko[j]>=st_seg[k]){
							if(oya_ko[j]<=end_seg[k]){
								judge=1;
								break;
							}
						}
					}
					if(judge==1){
						break;
					}
				}
				if(judge==1){
					//break;
				}else{
					for(int j=0;j<ko_oya.length;j++){
						for(int k=0;k<st_seg.length;k++){
							if(ko_oya[j]>=st_seg[k]){
								if(ko_oya[j]<=end_seg[k]){
									judge=1;
									break;
								}
							}
						}
						if(judge==1){
							break;
						}
					}
				}
				if(judge==1){
					System.out.println("NG");
				}
				if(judge==0){
					System.out.println("OK");
					int tmp[];
					tmp = new int[okParent.length+1];
					for(int j=0;i<okParent.length;j++){
						tmp[j]=okParent[j];
					}
					tmp[okParent.length]=i;
					okParent = new int[tmp.length];
					for(int j=0;j<tmp.length;j++){
						okParent[j]=tmp[j];
						
					}
				}
				
			}
			if(okParent.length>0){
				/*
				 * tmparr[1]��oya�m�[�h��ko�m�[�h�Ƃ̊ԂɃG�b�W������
				 * tmparr[0]����Ako�m�[�h���폜
				 */
				//�e��₩�烉���_���ɐe��I��
				int randoya = (int)(Math.random()*okParent.length);
				Node oya = rest[okParent[randoya]];
				
				System.out.println("backRec �Аe����");
				//System.out.println("rand123 " + rand1 + " " + rand2 + " " + rand3);
				int preBackNodenum = gr2.nodes.length;
				int preBackEdgenum = gr2.edges.length;
				int preBackNodenum0 = gr1.nodes.length;
				//System.out.println("BackRec���@�v���@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				//gr2oya=gr1.nodes[oya].hp[0].forestnd[gr1.nodes[oya].hp[0].forestnd.length-1];
				//rec,oya1,oya2��gr1�̂��́Agr2�̑Ή��m�[�h��I��
				Node rec_gr2,oya1_gr2;
				
				
				rec_gr2 = child.hp[0].argnd;
				oya1_gr2 = oya.hp[0].argnd;
				
				Edge tmped;
				tmped = new Edge(lg.ed.length,gr2,oya1_gr2,rec_gr2,-9);
				System.out.println("tmped.rec1 rec2 " + rec1 + " " + rec2);
				tmped.rec1=rec1;
				tmped.rec2=rec2;
				gr2.addEdge(gr2,tmped);
				lg.addEdge(tmped);
				//rec_gr2.addEdge(tmped);
				//oya1_gr2.addEdge(tmped);
				//tmped = new Edge(lg.ed.length,tmparr[1],oya2_gr2,rec_gr2,-9);
				//tmp2.addEdge(tmparr[1],tmped);
				//lg.addEdge(tmped);
				//rec_gr2.addEdge(tmped);
				//oya2_gr2.addEdge(tmped);
				
				//gr2�ɂ����ĉ��̎q���͐e���̂��̂ł���A�e�ƂȂ�����ɏ�������
				gr2.removeEdgeAndGluePT4Retro(lg,gr2,tmped);
				//�c�����G�b�W�̑g�����ʒu�̏��(label�͌J��Ԃ������̂P����O�̂���Ȃ̂ŁA�����������K�v
				
				
				//gr2.removeNode(gr2,rec_gr2);
				
				preBackNodenum = gr2.nodes.length;
				preBackEdgenum = gr2.edges.length;
				preBackNodenum0 = gr1.nodes.length;
				//System.out.println("BackRec���@�|�X�g�@nodes & edges / rest nodes " + preBackNodenum +" " + preBackEdgenum + " " + preBackNodenum0);
				lg.addRetroMR(4);//retroMR�Б�����
				lg.addRetroHpChild(child.hp[0]);
				lg.addRetroHpParent1(oya.hp[0]);
				lg.addRetroHpParent2(null);
			}else{
				//�ĕ����͕Аe��������P��菭�Ȃ��񐔂����s��Ȃ�
				if(numiter<maxnumiter){
//					�w�肳�ꂽcross_loc��allsnps��0����Aallsnps.length-2�Ԗڂ܂�
					System.out.println("allsnps len " + allsnps.length + " depth " + depth  + "loc" + allsnps[depth]);
					int loc_cross[]={allsnps[depth]};
					depth = numiter+1;
					/*
					//�g��������(�g�����ӏ��𔭐������A�Q�e�ɕ������čċA�I��backRecParent��������
					int num_cross = 1;//default��1�ӏ�
					*/
					/*
					//�g�����ӏ��̓Z�O�����g������Ȃ��ƁA�e�T�����i�W���Ȃ��̂ŁA�Z�O�����g���ɔ���������
					//�Z�O�����g�̑�����
					int seg_len=0;
					for(int i=0;i<st_seg.length;i++){
						seg_len += (end_seg[i]-st_seg[i]);
					}
					System.out.println("seg_len " + seg_len);
					*/
					/*
					int loc_cross[];
					loc_cross = new int[num_cross];
					for(int i = 0;i<num_cross;i++){
						loc_cross[i] = (int)(Math.random()*lg.numsnp);
					*/
						/*
						int tmploc,tmptmploc;
						tmploc=(int)(Math.random()*seg_len);
						System.out.println("tmploc " + tmploc);
						tmptmploc = tmploc;
						//tmploc���Z�O�����g��̉���ԍ��ɕϊ�����
						int accum=0;
						for(int j=0;j<st_seg.length;j++){
							int preaccum = accum;
							int pretmptmploc = tmptmploc;
							accum+=(end_seg[j]-st_seg[j]);
							tmptmploc-=(end_seg[j]-st_seg[j]);
							if(accum>=tmploc){
								loc_cross[i]=pretmptmploc+st_seg[j];
								System.out.println("loc_cross " + loc_cross[i]);
								break;
							}
						}
						//loc_cross[i] = (int)(Math.random()*seg_len);
						 *
						 */
					/*
					}
					//�g�����ӏ����\�[�g����
					MiscUtil.quick_sort_int(loc_cross);
					*/
					//�g�����p�^�[���Ɋ�Â��A2�e��segment���X�g���쐬����
					int st_seg1all[]={};
					int end_seg1all[]={};
					int st_seg2all[]={};
					int end_seg2all[]={};
					
					for(int i=0;i<st_seg.length;i++){
						int st_seg1[]={};
						int st_seg2[]={};
						int end_seg1[]={};
						int end_seg2[]={};
						int sep_loc[] ={};//seg��̑g�����_��o�^
						int oyaselect=0;//seg�̍ŏ��̕����̋A���e���K��
						
						for(int j=0;j<loc_cross.length;j++){//segment�J�n�_������O�ɂ���loc_cross�̐��𐔂��Aseg�̍ŏ��̕����̐e���O���P�����߂�
							if(loc_cross[j]<st_seg[i]){
								if(oyaselect ==0){
									oyaselect = 1;
								}else{
									oyaselect = 0;
								}
							}else if(loc_cross[j]<end_seg[i]){//segment �͈͓̔��ɂ���cross_loc
								int tmp[];
								tmp = new int[sep_loc.length+1];
								for(int k=0;k<sep_loc.length;k++){
									tmp[k]=sep_loc[k];
								}
								tmp[sep_loc.length]=loc_cross[j];
								sep_loc = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									sep_loc[k]=tmp[k];
								}
							}else{//end_seg���傫��cross_loc�͉��̊֌W���Ȃ�
								break;
							}
						}
						//�V�Z�O�����g��st_seg[i]-1, sep_loc[],end_seg[i]�����v�f��sep_loc.length+2�̒n�_�����
						//sep_loc.length+1���̃Z�O�����g�ɕ�������
						int tmpsep[];
						tmpsep = new int[sep_loc.length+2];
						tmpsep[0]=st_seg[i];
						for(int j=0;j<sep_loc.length;j++){
							tmpsep[j+1]=sep_loc[j];
						}
						//tmpsep[sep_loc.length+1]=end_seg[i]+1;
						tmpsep[sep_loc.length+1]=end_seg[i];
						//sep_loc�̉���ԍ��͐V�Z�O�����g�̏I�_�Asep_loc�̉���ԍ�+�P�͐V�Z�O�����g�̎n�_�ł���
						//�e�P�A�e�Q�Ɍ��݂ɐV�Z�O�����g������U��
						//�ŏ��̐e���e�P���e�Q���́Aoyaselect����߂�
						
						for(int j=0;j<tmpsep.length-1;j++){
							if(oyaselect==0){
								//�e�P��
								int tmp[];
								int tmp2[];
								tmp=new int[st_seg1.length+1];
								tmp2=new int[st_seg1.length+1];
								for(int k=0;k<st_seg1.length;k++){
									tmp[k]=st_seg1[k];
									tmp2[k]=end_seg1[k];
								}
								if(j==0){
									tmp[st_seg1.length]=tmpsep[j];
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg1.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg1.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg1.length]=tmpsep[j+1];
									}
								}else{
									tmp[st_seg1.length]=tmpsep[j]+1;
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg1.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg1.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg1.length]=tmpsep[j+1];
									}
								}
								st_seg1 = new int[tmp.length];
								end_seg1 = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									st_seg1[k]=tmp[k];
									end_seg1[k]=tmp2[k];
								}
								//���̃Z�O�����g�͐e�Q��
								oyaselect=1;
							}else{
								//�e�Q��
								int tmp[];
								int tmp2[];
								tmp=new int[st_seg2.length+1];
								tmp2=new int[st_seg2.length+1];
								for(int k=0;k<st_seg2.length;k++){
									tmp[k]=st_seg2[k];
									tmp2[k]=end_seg2[k];
								}
								if(j==0){
									tmp[st_seg2.length]=tmpsep[j];
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg2.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg2.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg2.length]=tmpsep[j+1];
									}
								}else{
									tmp[st_seg2.length]=tmpsep[j]+1;
									if(j+1<tmpsep.length-1){
										if(tmpsep[j+1]==tmpsep[tmpsep.length-1]){
											tmp2[st_seg2.length]=tmpsep[j+1]-1;
										}else{
											tmp2[st_seg2.length]=tmpsep[j+1];
										}
									}else{
										tmp2[st_seg2.length]=tmpsep[j+1];
									}
								}
								st_seg2 = new int[tmp.length];
								end_seg2 = new int[tmp.length];
								for(int k=0;k<tmp.length;k++){
									st_seg2[k]=tmp[k];
									end_seg2[k]=tmp2[k];
								}
								//���̃Z�O�����g�͐e�P��
								oyaselect=0;
							}
						}
						st_seg1all=MiscUtil.addintarray(st_seg1all,st_seg1);
						end_seg1all=MiscUtil.addintarray(end_seg1all,end_seg1);
						st_seg2all=MiscUtil.addintarray(st_seg2all,st_seg2);
						end_seg2all=MiscUtil.addintarray(end_seg2all,end_seg2);
						
						
					}
					
					//	st_segxxx��null���ƕs�s������
					if(st_seg1all.length==0){
						st_seg1all=new int[1];
						end_seg1all=new int[1];
						st_seg1all[0]=lg.numsnp;
						end_seg1all[0]=lg.numsnp;
					}
					if(st_seg2all.length==0){
						st_seg2all=new int[1];
						end_seg2all=new int[1];
						st_seg2all[0]=lg.numsnp;
						end_seg2all[0]=lg.numsnp;
					}
				
					//oya1
					System.out.println("oya1 number of seg " + st_seg1all.length);
					//oya1��gr2�ɉ�����
					
					////////////////////////////
//					System.out.println("snps len " + snps.length);
					//tmphp = new Haplotype(lg.hp.length,parenthp);

					int snps1[]={};
					int snps2[]={};
					
					for(int j=0;j<child.hp[0].hp.length;j++){
						int judge12=2;
						for(int k=0;k<st_seg1all.length;k++){
							if(child.hp[0].hp[j]>=st_seg1all[k] && child.hp[0].hp[j]<=end_seg1all[k]){
								judge12=1;
								break;
							}
						}
						if(judge12==1){
							int tmpsnps1[];
							tmpsnps1 = new int[snps1.length+1];
							for(int l=0;l<snps1.length;l++){
								tmpsnps1[l]=snps1[l];
							}
							tmpsnps1[snps1.length]=child.hp[0].hp[j];
							snps1 = new int[tmpsnps1.length];
							for(int l=0;l<tmpsnps1.length;l++){
								snps1[l]=tmpsnps1[l];
							}
						}else if(judge12 ==2){
							int tmpsnps2[];
							tmpsnps2 = new int[snps2.length+1];
							for(int l=0;l<snps2.length;l++){
								tmpsnps2[l]=snps2[l];
							}
							tmpsnps2[snps2.length]=child.hp[0].hp[j];
							snps2 = new int[tmpsnps2.length];
							for(int l=0;l<tmpsnps2.length;l++){
								snps2[l]=tmpsnps2[l];
							}
						}
					}
					
					
					Haplotype tmphp1 = new Haplotype(lg.hp.length,snps1);
					tmphp1.addStEnd(st_seg1all,end_seg1all);
					lg.addHaplotype(tmphp1);
					
					Haplotype tmphp2 = new Haplotype(lg.hp.length,snps2);
					tmphp2.addStEnd(st_seg2all,end_seg2all);
					lg.addHaplotype(tmphp2);
					
					Node tmpnd1, tmpnd2;
					tmpnd1=new Node(lg.nd.length,gr2,gr2.nodes.length);
					lg.addNode(tmpnd1);
					gr2.addNode(gr2,tmpnd1);
					tmpnd1.addHaplotype(tmphp1);
					tmphp1.addArgNode(tmpnd1);
					
					tmpnd2=new Node(lg.nd.length,gr2,gr2.nodes.length);
					lg.addNode(tmpnd2);
					gr2.addNode(gr2,tmpnd2);
					tmpnd2.addHaplotype(tmphp2);
					tmphp2.addArgNode(tmpnd2);
					
					
					
					
					//Node gr2child = child.hp[0].argnd;
					
					Edge tmped1;
					//tmped1 = new Edge(lg.ed.length,gr2,tmpnd1,gr2child,-9);//j��mutation
					tmped1 = new Edge(lg.ed.length,gr2,tmpnd1,child,-9);//j��mutation
					System.out.println("bifurcation rec1 rec2 "+ rec1 + " " + rec2);
					tmped1.rec1=rec1;
					tmped1.rec2=rec2;
					//System.out.println("parent �Ȃ��@pre addEdge " + tmparr[1].nodes.length);
					gr2.addEdge(gr2,tmped1);//���[�_�̃G�b�W���͂����ōX�V
					//System.out.println("parent �Ȃ��@post addEdge " + tmparr[1].nodes.length);
					lg.addEdge(tmped1);
					
					Edge tmped2;
					//tmped2 = new Edge(lg.ed.length,gr2,tmpnd2,gr2child,-9);//j��mutation
					tmped2 = new Edge(lg.ed.length,gr2,tmpnd2,child,-9);//j��mutation
					tmped2.rec1=rec1;
					tmped2.rec2=rec2;
					//System.out.println("parent �Ȃ��@pre addEdge " + tmparr[1].nodes.length);
					gr2.addEdge(gr2,tmped2);//���[�_�̃G�b�W���͂����ōX�V
					//System.out.println("parent �Ȃ��@post addEdge " + tmparr[1].nodes.length);
					lg.addEdge(tmped2);
					//tmpnd2.addEdge(tmped);
					//gr2child.addEdge(tmped);
					lg.addRetroMR(3);//retro�����Őe�Ȃ�BackMut�����̂�1��o�^
					lg.addRetroHpChild(child.hp[0]);
					lg.addRetroHpParent1(tmphp1);
					lg.addRetroHpParent2(tmphp2);
					
					
					
					
					////////////////////////////
					int tmprec1=loc_cross[0];
					int tmprec2=0;
					for(int j=0;j<allsnps_or.length;j++){
						if(tmprec1==allsnps_or[j]){
							tmprec2 = allsnps_or[j+1];
						}
					}
					
					backRecNoParent(lg,gr1,gr2,tmpnd1,rest,st_seg1all,end_seg1all,maxnumiter,depth,allsnps,tmprec1,tmprec2,allsnps_or);
					//oya2
					System.out.println("oya2 number of seg " + st_seg2all.length);
					backRecNoParent(lg,gr1,gr2,tmpnd2,rest,st_seg2all,end_seg2all,maxnumiter,depth,allsnps,tmprec1,tmprec2,allsnps_or);
					
				}
			}
				
			
		}
	}
	public boolean judgeARGComplete(){
		boolean ret;
		//ret = true;
		ret = false;
		
		return ret;
	}

	//�O���t�̘A�������`�F�b�N
	public boolean connectedGraph(){
		/*
		 * �O���t�̉��^�T�����s���A�K��m�[�h�𑝂₹�邾�����₷�A
		 * �����Ȃ��Ȃ����Ƃ��ɃO���t��̂��ׂẴm�[�h��K�₵�Ă�����A���ł���Ƃ���
		 */
		//System.out.println("connection check start");
		boolean ret=true;
		
		//edge�̐���node�̐�-1������������A�A���ł͂��肦�Ȃ�
		if(nodes.length-1>edges.length){
			ret = false;
			return ret;
		}
		
		int visited[]=null;//�K��ς݃m�[�hid��o�^
		int tovisit[]=null;//�K�₷�邱�Ƃ����܂�Ȃ���A�܂��K�₵�Ă��Ȃ��m�[�hid��o�^
		if(nodes.length==0){
			//�A���ł���Ƃ���
		}else{
			//�K��ς݃m�[�hID�A���ꂩ��K�₷��ׂ��m�[�hID�̏����l��^����
			visited = new int[1];
			visited[0]=nodes[0].id;
			tovisit = new int[nodes[0].edge.length];
			Node tmp = nodes[0];
			for(int i=0;i<tovisit.length;i++){
				if(tmp.edge[i].start==tmp){
					tovisit[i]=tmp.edge[i].end.id;
				}else{
					tovisit[i]=tmp.edge[i].start.id;
				}
				//System.out.println("tovisit id " + tovisit[i]);
			}
			//tovisit����visited�̗v�f������
			MiscUtil.rmIdentical(tovisit,visited);
			//System.out.println("tovisit nodes num " + tovisit.length);
			//System.out.println("visited nodes num " + visited.length);
			
			while(tovisit.length>0){
			//int test=0;
			//while(test>0){
				//System.out.println("while loop ");
				//System.out.println("tovisit nodes num " + tovisit.length);
				//System.out.println("visited nodes num " + visited.length);
				int tmpvisited[];
				tmpvisited = new int[visited.length];
				for(int i=0;i<visited.length;i++){
					tmpvisited[i]=visited[i];
				}
				int tmptovisit[];
				tmptovisit = new int[tovisit.length];
				for(int i=0;i<tovisit.length;i++){
					tmptovisit[i]=tovisit[i];
				}
				
				for(int i=0;i<tovisit.length;i++){
					//System.out.println("tovisit i " + tovisit[i]);
					int toadd[];
					tmp = null;
					for(int j=0;j<nodes.length;j++){
						if(tovisit[i] == nodes[j].id){
							tmp=nodes[j];
						}
					}
					//System.out.println("tmp node " + tmp.id);
					if(tmp == null){
						
					}else{
						toadd = new int[tmp.edge.length];
						for(int k=0;k<tmp.edge.length;k++){
							if(tmp.edge[k].start==tmp){
								toadd[k]=tmp.edge[k].end.id;
							}else{
								toadd[k]=tmp.edge[k].start.id;
							}
						}
						//System.out.println("toadd len 1 " + toadd.length);
						toadd = MiscUtil.rmIdentical(toadd,tmptovisit);//���łɖK��ς݂̂��̂͂��ꂩ��K��m�[�h�ɉ����Ȃ�
						//System.out.println("toadd len 2 " + toadd.length);
						tmptovisit = MiscUtil.addintarray(tmptovisit,toadd);//�d�������������toadd��������
						//System.out.println("tmptovisit len " + tmptovisit.length);
						int justvisited[]={tovisit[i]};
						//System.out.println("justvisited len 1 " + justvisited.length);
						justvisited = MiscUtil.rmIdentical(justvisited,tmpvisited);
						//System.out.println("justvisited len 2 " + justvisited.length);
						tmpvisited = MiscUtil.addintarray(tmpvisited,justvisited);//�K��ς݂łȂ��Ƃ����K�₵����A�K��ς݂ɉ�����
						//System.out.println("tmpvisited " + tmpvisited.length);
						tmptovisit = MiscUtil.rmIdentical(tmptovisit,tmpvisited);
						//System.out.println("tmptovisit " + tmptovisit.length);
						//tmptovisit���X�V���ꂽ�Atovisit�͍X�V����Ă��Ȃ�
						//tmpvisited���X�V���ꂽ�Avisited�͍X�V����Ă��Ȃ�
					}
					
				}
				visited = new int[tmpvisited.length];
				tovisit = new int[tmptovisit.length];
				for(int i=0;i<tmpvisited.length;i++){
					visited[i]=tmpvisited[i];
				}
				for(int i=0;i<tmptovisit.length;i++){
					tovisit[i]=tmptovisit[i];
				}
			}
		}
		//System.out.println("visited nodes num " + visited.length);
		if(visited.length == nodes.length){
			
		}else{
			ret = false;
		}
		return ret;
	}
	//Root node�̌��o
	//���g���I�_�ƂȂ�G�b�W�������Ȃ��m�[�h��root node�Ƃ���
	//���g�݂̂ō\�������A���O���t�ɂ����āA�u���g�v��root node�ł���Ƃ���
	public Node[] listRootNode(){
		Node list[]={};
		for(int i=0;i<nodes.length;i++){
			boolean judge=true;
			for(int j=0;j<nodes[i].edge.length;j++){
				if(nodes[i].edge[j].end == nodes[i]){
					judge=false;
					break;
				}
			}
			if(judge){
				Node tmp[];
				tmp = new Node[list.length+1];
				for(int j=0;j<list.length;j++){
					tmp[j]=list[j];
				}
				tmp[list.length]=nodes[i];
				list = new Node[tmp.length];
				for(int j=0;j<tmp.length;j++){
					list[j]=tmp[j];
				}
			}
		}
		return list;
	}

	/*
	 * �o��method
	 */
	public void printGraphAll(){
		String st ="";
		st += " graph ";
		st += " id " + id;
		st += " nodes ";
		for(int i=0;i<nodes.length;i++){
			st += nodes[i].id + " ";
		}
		st += " edges ";
		for(int i=0;i<edges.length;i++){
			st += edges[i].id + " ";
		}
		st += " direction " + direction ;
		st += " type " + type;
		st += " event " ;
		for(int i=0;i<ev.length;i++){
			st += ev[i].id + " ";
		}
		st += " tr " + tr;
		st += " label " + label;
		st += " location " + or_x + " " + len_x + " " + or_y + " " + len_y + " "+ or_z + " " + len_z;
		
		System.out.println(st);
	}
	public void printGraphAllNodeEdge(){
		String st ="";
		st += " graph ";
		st += " id " + id + "\n";
		st += " nodes " + nodes.length + "��\n";
		for(int i=0;i<nodes.length;i++){
			st += "node " + nodes[i].id + "\n";
			st += "edge \n";
			for(int j=0;j<nodes[i].edge.length;j++){
				st += nodes[i].edge[j].id + " ";
			}
			st += "\n";
			st += "haplotype \n";
			for(int k=0;k<nodes[i].hp.length;k++){
				st += nodes[i].hp[k].id + " ";
			}
			st += "\n";
		}
		st += "\n edges " + edges.length + "��\n";
		for(int i=0;i<edges.length;i++){
			st += "edge " + edges[i].id + " ";
			st += "start " + edges[i].start.id + " ";
			st += "end " + edges[i].end.id + " \n";
		}
		st += "\n";
		st += " direction " + direction ;
		st += " type " + type;
		st += " event " ;
		for(int i=0;i<ev.length;i++){
			st += ev[i].id + " ";
		}
		st += " tr " + tr;
		st += " label " + label;
		st += " location " + or_x + " " + len_x + " " + or_y + " " + len_y + " "+ or_z + " " + len_z;
		st += "\n nodes " + nodes.length + "��\n";
		st += "\n edges " + edges.length + "��\n";
		System.out.println(st);
	}

	public void outPajek(String st_){
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//vertices header
			out += "*Vertices ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			out += numbernode + "\n";
			//vertices
			
			for(int i=1;i<numbernode+1;i++){
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
			}
			//edges header
			out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id +1;
				int outendid = edges[i].end.id +1;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				out += outstid + " " + outendid + " " + "1";
				if(i<numberedge-1){
					out += "\n";
				}
			}
			System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	
	public void outVGJ(String st_){
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "node [ \n id " 
					+ nodes[i].id + "\n" 
					+ "label " + "\"" + nodes[i].name + "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 0.5 height 0.5 depth 0.5 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id ;
				int outendid = edges[i].end.id ;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				//out += outstid + " " + outendid + " " + "1";
				out += "edge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
				     + "source " + edges[i].start.id
					 + "target " + edges[i].end.id + "]";
				//if(i<numberedge-1){
					//out += "\n";
				//}
			}
			//footer
			out += "]";
			System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public String outVGJtoSt(){
		
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			//bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "node [ \n id " 
					+ nodes[i].id + "\n" 
					+ "label " + "\"" + nodes[i].name + "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 0.5 height 0.5 depth 0.5 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			}
			for(int i=0;i<numberedge;i++){
				int outstid = edges[i].start.id ;
				int outendid = edges[i].end.id ;
				//out += edges[i].start.id + " " + edges[i].end.id + "\n";
				//out += outstid + " " + outendid + " " + "1";
				out += "edge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
				     + "source " + edges[i].start.id
					 + "target " + edges[i].end.id + "]";
				//if(i<numberedge-1){
					//out += "\n";
				//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			//bw1.write(out);
			//bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		return out;
		
	}
	public void outVGJ_noLabel(String st_){//Node No Label
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "\nnode [ \n id " 
					+ nodes[i].id + "\n" 
					//+ "label " + "\"" + nodes[i].name + "\""
					+ "label " + "\"" +  "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 1 height 1 depth 1 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			
				for(int i=0;i<numberedge;i++){
					int outstid = edges[i].start.id ;
					int outendid = edges[i].end.id ;
					//out += edges[i].start.id + " " + edges[i].end.id + "\n";
					//out += outstid + " " + outendid + " " + "1";
					out += "\nedge [\nlinestyle \"solid\"\nlabel \"" + edges[i].label + "\""
						+ "source " + edges[i].start.id
							+ "target " + edges[i].end.id + "]";
					//if(i<numberedge-1){
					//out += "\n";
				}	//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public void outVGJ_noLabel2(String st_){//Node & Edge No label
		String file = st_;
		String out;
		out = "";
		BufferedWriter bw1 = null;
		try{
			bw1 = new BufferedWriter(new FileWriter(file));
			
			//header
			out += "graph [\ndirected 1\n ";
			int numbernode =0;
			if(nodes == null){
				
			}else{
				numbernode = nodes.length;
			}
			//num vertices
			//out += numbernode + "\n";
			//vertices
			
			for(int i=0;i<numbernode;i++){
				out += "\nnode [ \n id " 
					+ nodes[i].id + "\n" 
					//+ "label " + "\"" + nodes[i].name + "\""
					+ "label " + "\"" +  "\""
					+ "graphics [ center [ x " + nodes[i].x + " "
					+ "y " + nodes[i].y + " "
					+ "z " + nodes[i].z + "]"
					+ "width 1 height 1 depth 1 ]"
					+ "vgj [ labelPosition \"below\" shape \"Oval\"  ] ]";
				
				/*
				//int outid = nodes[i].id +1;
				out += i + " " + "\"" + nodes[i-1].id + "\" ";
				//out += i + " " + "\"" + outid + "\" ";
				out += nodes[i-1].y + " " + nodes[i-1].z + " " + "0.5";//nodes[i].z;
				out += "\n";
				*/
			}
			//edges header
			//out += "*Edges\n";
			//edges
			int numberedge =0;
			if(edges == null){
				
			}else{
				numberedge = edges.length;
			
				for(int i=0;i<numberedge;i++){
					int outstid = edges[i].start.id ;
					int outendid = edges[i].end.id ;
					//out += edges[i].start.id + " " + edges[i].end.id + "\n";
					//out += outstid + " " + outendid + " " + "1";
					out += "\nedge [\nlinestyle \"solid\"\nlabel \"" +  "\""
						+ "source " + edges[i].start.id
							+ "target " + edges[i].end.id + "]";
					//if(i<numberedge-1){
					//out += "\n";
				}	//}
			}
			//footer
			out += "]";
			//System.out.println(out);
			bw1.write(out);
			bw1.close();

		}catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	public void circle(){
		//�^����ꂽ���͈͂𒼌a�Ƃ���~����ɔz�u����
		for(int i=0;i<nodes.length;i++){
			double theta;
			theta=(2*i*Math.PI)/nodes.length;
			//System.out.println("theta " +theta);
			nodes[i].x=Math.sin(theta)*(Math.abs(or_x-(len_x+1))/2)+(or_x+len_x+1)/2;
			//System.out.println("x " + nodes[i].x);
			//nodes[i].y=Math.cos(theta)*(Math.abs(or_x-len_x)/2)+(or_y+len_y)/2;
			nodes[i].y=Math.cos(theta)*(Math.abs(or_x-(len_x+1))/2);
			//System.out.println("y " + nodes[i].y);
		}
	}
	public int[][] edgeMatrix(){
		int mat[][];
		mat = new int[nodes.length][nodes.length];
		int nd[];
		int max=0;
		for(int i=0;i<nodes.length;i++){
			if(nodes[i].id > max){
				max=nodes[i].id;
			}
		}
		nd = new int[max+1];
		for(int i=0;i<nodes.length;i++){
			nd[nodes[i].id]=i;
			for(int j=0;j<nodes.length;j++){
				mat[i][j]=0;
			}
		}
		for(int i=0;i<edges.length;i++){
			mat[nd[edges[i].start.id]][nd[edges[i].end.id]]=1;
		}
		return mat;
		
	}
	
	public void plotTree(double width,double height,double rx, double ry){
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		nodes[0].x= width/2;//����
		nodes[0].y=0;
		dx=2*width/nodes.length;
		dy=height;
		int dir=1;
		for(int i=1;i<n;i++){
			dy*=ry;
			dx*=rx;
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<i;j++){
				if(mat[j][i]==1){
					nodes[i].x+=dir*dx+ nodes[j].x;
					numpat++;
					dir*=(-1);
				}
			}
			nodes[i].x/=numpat;
		}
	}
	public void plotTreeHP(){
		double width,height,rx,ry;
		width=Math.abs(or_x-len_x+1);
		height = width/nodes.length;
		rx =1;
		ry = 0.95;
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		//System.out.println("or_x " + or_x );
		System.out.println("or_x " + or_x );
		System.out.println("width " + width );
		System.out.println("nodes[0].id " + nodes[0].id);
		System.out.println("nodes[0].x " + nodes[0].x );
		nodes[0].x= or_x + width/2;//����
		nodes[0].y=0;
		dx=2*width/nodes.length;
		dy=height;
		int dir=1;
		for(int i=1;i<n;i++){
			dy*=ry;
			dx*=rx;
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<n;j++){
				if(mat[j][i]==1){
					nodes[i].x+=dir*dx+ nodes[j].x;
					numpat++;
					
					dir*=(-1);
				}
			}
			//System.out.println("numpat" + numpat);
			nodes[i].x/=numpat;
		}
	}
	public void plotTreeHPinfinite(double ratio,double angle){
		//�������alength�A���ゲ�Ƃ̔��a������ratio�Ƃ����
		//�����񐔂̕���ɂ����ē��B���_���̈攼�arange�Ƃ����
		//range*(1-ratio)=length�̊֌W������
		//range = width/2;
		angle *= 0.5 * Math.PI;
		
		double range,length,theta;
		 
		double width,height,rx,ry;
		width=Math.abs(or_x-len_x+1);
		height = width/nodes.length;
		
		range = width/2;
		length = range * (1-ratio);
		theta = 0.5* Math.PI;
		
		rx =1;
		ry = 0.95;
		int mat[][];
		mat = edgeMatrix();
		int n=nodes.length;
		double dx, dy;
		
		nodes[0].x= or_x + width/2;//����
		nodes[0].y=0;
		
		dx=length*Math.sin(theta);
		dy=length*Math.cos(theta);
		//int dir=1;
		int dir[];
		dir = new int[n];
		dir[0]=1;
		
		for(int i=1;i<n;i++){
			theta -= angle;
			length*=ratio;
			dy = length*Math.cos(theta);
			dx = length*Math.sin(theta);
			//angle+=angle;
			//dy=dx*Math.tan(angle);
			nodes[i].y=nodes[i-1].y+dy;
			nodes[i].x=0;
			int numpat=0;
			for(int j=0;j<n;j++){
				if(mat[j][i]==1){
					
					nodes[i].x+=dir[j]*dx+ nodes[j].x;
					numpat++;
					
					dir[j]*=(-1);
					dir[i]=dir[j];
					//dir*=(-1);
				}
			}
			//System.out.println("numpat" + numpat);
			nodes[i].x/=numpat;
		}
	}
	public void sortRooted(){
		Node tmp[],tmp2[];
		tmp=new Node[0];
		tmp2 =new Node[0];
		Node tmpst,tmpend;
		if(edges==null){
			
		}else if(edges.length==0){
			
		}else{
			for(int i=0;i<edges.length;i++){
				tmpst=edges[i].start;
				tmpend=edges[i].end;
				int numadd=2;
				int stflag=0;
				int endflag=0;
				int storder=0;
				int endorder=tmp.length;
				for(int j=0;j<tmp.length;j++){
					if(tmp[j]==tmpst){
						numadd--;
						endflag=1;
						storder=j;
					}
					if(tmp[j]==tmpend){
						numadd--;
						stflag=1;
						endorder=j;
					}
				}
//				
				tmp2 =new Node[tmp.length+numadd];
				if(numadd==2){//�����ɒǉ�
					tmp2[0]=tmpst;
					for(int j=0;j<tmp.length;j++){
						tmp2[j+1]=tmp[j];
					}
					//tmp2[tmp.length]=tmpst;
					tmp2[tmp.length+1]=tmpend;
				}else if(numadd==1){
					if(stflag==1){//tmpst��擪�ɉ�����
						tmp2[0]=tmpst;
						for(int j=0;j<tmp.length;j++){
							tmp2[j+1]=tmp[j];
						}
					}else if(endflag==1){
						for(int j=0;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[tmp.length]=tmpend;
					}
				}else if(numadd==0){
					if(storder<=endorder){
						
					}else{
						
						for(int j=0;j<endorder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[endorder]=tmpend;
						for(int j=endorder+1;j<storder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[storder]=tmpst;
						for(int j=storder+1;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
					}
				}
				
				tmp = new Node[tmp2.length];
				for(int j=0;j<tmp2.length;j++){
					tmp[j]=tmp2[j];
				}
			}
			nodes = new Node[tmp.length];
			for(int i=0;i<tmp.length;i++){
				nodes[i]=tmp[i];
			}
		}
		
	}
	
	public boolean TreeChecker(){
		//Tree�Ɖ��肵�ăG�b�W�����g���ăm�[�h���㗬���牺���Ɍ������ĕ��ёւ���
		//���ёւ��ɂ́A�G�b�W��1�񂸂g��
		//���ёւ��I����ɂ�����x�A���ׂẴG�b�W�ł̏㗬�����֌W�������Ă���΁A����̓O���t���؂��������Ƃ��Ӗ�����
		//�����łȂ���΁A�����_(�T�C�N��)�����݂���
		//�������A���[�v�͂��̖������������̂ŁA���[�v�������ǂ����͕ʂ̋@�\�Ń`�F�b�N����K�v������
		
		Node tmp[],tmp2[];
		tmp=new Node[0];
		tmp2 =new Node[0];
		Node tmpst,tmpend;
		if(edges==null){
			
		}else if(edges.length==0){
			
		}else{
			for(int i=0;i<edges.length;i++){
				tmpst=edges[i].start;
				tmpend=edges[i].end;
				int numadd=2;
				int stflag=0;
				int endflag=0;
				int storder=0;
				int endorder=tmp.length;
				for(int j=0;j<tmp.length;j++){
					if(tmp[j]==tmpst){
						numadd--;
						endflag=1;
						storder=j;
					}
					if(tmp[j]==tmpend){
						numadd--;
						stflag=1;
						endorder=j;
					}
				}
//				
				tmp2 =new Node[tmp.length+numadd];
				if(numadd==2){//�����ɒǉ�
					tmp2[0]=tmpst;
					for(int j=0;j<tmp.length;j++){
						tmp2[j+1]=tmp[j];
					}
					//tmp2[tmp.length]=tmpst;
					tmp2[tmp.length+1]=tmpend;
				}else if(numadd==1){
					if(stflag==1){//tmpst��擪�ɉ�����
						tmp2[0]=tmpst;
						for(int j=0;j<tmp.length;j++){
							tmp2[j+1]=tmp[j];
						}
					}else if(endflag==1){
						for(int j=0;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[tmp.length]=tmpend;
					}
				}else if(numadd==0){
					if(storder<=endorder){
						
					}else{
						
						for(int j=0;j<endorder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[endorder]=tmpend;
						for(int j=endorder+1;j<storder;j++){
							tmp2[j]=tmp[j];
						}
						tmp2[storder]=tmpst;
						for(int j=storder+1;j<tmp.length;j++){
							tmp2[j]=tmp[j];
						}
					}
				}
				
				tmp = new Node[tmp2.length];
				for(int j=0;j<tmp2.length;j++){
					tmp[j]=tmp2[j];
				}
			}
			//nodes = new Node[tmp.length];���̊֐��̓`�F�b�J�[�ł����ăm�[�h�̂Ȃ�ёւ��͂��Ȃ�
			//for(int i=0;i<tmp.length;i++){
				//nodes[i]=tmp[i];
			//}
		}
		int st,end;
		st = 0;
		end = 0;
		boolean ret = true;
		for(int i=0;i<edges.length;i++){
			for(int j=0;j<tmp.length;j++){
				if(tmp[j]==edges[i].start){
					st = j;
				}
				if(tmp[j]==edges[i].end){
					end = j;
				}
			}
			if(st>end){
				ret = false;
			}
		}
		return ret;
	}
	/*
	 *constructor
	 */
	public Graph(int id_){
		id = id_;
		nodes = new Node[0];
		edges = new Edge[0];
		direction = 1;//default
		tr=1;//��͖؂Ƃ݂Ȃ�
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	public Graph(int id_,int type_){
		id = id_;
		type = type_;
		nodes = new Node[0];
		edges = new Edge[0];
		direction = 1;//default
		tr=1;//��͖؂Ƃ݂Ȃ�
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	public Graph(int id_,Node nd_){
		id = id_;
		nodes = new Node[1];
		nodes[0]=nd_;
		edges = new Edge[0];
		direction = 1;
		tr=1;//�_�͖؂ł���
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	
	public Graph(int id_,int type_,Node nd_){
		id = id_;
		type = type_;
		nodes = new Node[1];
		nodes[0]=nd_;
		edges = new Edge[0];
		tr=1;//�_�͖؂ł���
		ev = new Event[0];
		hs = new Hashtable();
		or_x=0;or_y=0;or_z=0;
	}
	
}
