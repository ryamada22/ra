package tAvEnter;
import java.io.*;
import java.lang.*;

public class PoolhapRead{
//	public static void main(String[] args) throws IOException{
	public int[][] poolhap(String hapfile) throws IOException{
		
		int hp[];
		int haplotype[][];
//		String file = args[0];
		String file = hapfile;
		
		GetNumber gn=new GetNumber();
		int num_haplotype=gn.getnum(file);
		//haplotype = new int[num_haplotype][1];
		haplotype = new int[num_haplotype][];

		FileReader infile = new FileReader(file);
		BufferedReader in = new BufferedReader(infile);
		String line;
		int counthaptype=0;
		while((line = in.readLine()) !=null){
//			System.out.println("line"+counthaptype+":"+line);
			SetNumber sn=new SetNumber();
			int hn = sn.setnum(line);
			hp = new int[hn];
			int count=0;
			for(int i=0;i<line.length();i++){
				if(line.charAt(i)=='1'){
					hp[count]=i;
					count++;
				}
			}
			haplotype[counthaptype]=hp;
			counthaptype++;
		}

/*	 test print
 * 		for(int i=0; i<counthaptype; i++){
			System.out.println(i+"banme");
			for(int j=0; j<haplotype[i].length; j++){
				System.out.print(haplotype[i][j]+"; ");
			}
			System.out.println("");
		}
*/
		in.close();
		infile.close();
		//return haplotype;
		return haplotype;
	}

/* #snp 
*	int snpnum;
*
*	public int getsnp(String file) throws IOException{
*		FileReader infile = new FileReader(file);
*		BufferedReader in = new BufferedReader(infile);
*		String line;
*		int x=0;
*		if(x==0){
*			while((line = in.readLine()) !=null){
*				snpnum=line.length();
*				x++;
*			}
*		}
*		snpnum = snpnum-1;//\n\r
*		in.close();
*		infile.close();
*		return snpnum;
*	}
*/
}

/* read haplotype file and count #haplotype */
class GetNumber{
	private int numhap = 0;
	
	public int getnum(String inf) throws IOException{
		FileReader infile = new FileReader(inf);
		BufferedReader in = new BufferedReader(infile);
		String line;
		while((line = in.readLine()) != null){
			numhap++;
		}
		in.close();
		infile.close();
		return numhap;
	}
}

/* hp[xxx]  -->set xxx*/
class SetNumber{
	private int hnum=0;

	public int setnum(String ln){
		String line_ = ln;
		for(int i=0;i<line_.length();i++){
			if(line_.charAt(i)=='1'){
				hnum++;
			}
		}
		return hnum;
	}
}

