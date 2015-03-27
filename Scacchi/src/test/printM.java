package test;


import static org.junit.Assert.*;

import javax.swing.JFrame;

import model.Model;


public class printM {
    Model model;
	int r=1;

	/**
	 *@author Alessandro Fuochi (UNIVR) ID083311
	 *@since  color of chess 
	 */
	    public printM(Model model){
	    	this.model=model;
	    }
		
		public void print( String Title){
			   System.out.println("---------------------"+ Title+"--------------------------------------");
			   byte yd=0;
			   while (yd < 8){
				String s= "["+pCv((byte)0,yd)+ "]  [" + pCv((byte)1,yd)+"]  ["+pCv((byte)2,yd)+ "]  [" +pCv((byte)3,yd)+ "]  ["+pCv((byte)4,yd)+ "]  [" +pCv((byte)5,yd)+"]  ["+pCv((byte)6,yd)+ "] ["+pCv((byte)7,yd)+ "] ";
					 s+= " * ["+cCv((byte)0,yd)+ "]  [" + cCv((byte)1,yd)+"]  ["+cCv((byte)2,yd)+ "]  [" +cCv((byte)3,yd)+ "]  ["+cCv((byte)4,yd)+ "]  [" +cCv((byte)5,yd)+"]  ["+cCv((byte)6,yd)+ "] ["+cCv((byte)7,yd)+ "] ";
			if(	model.isBackUpAvailable() )	 s+= " * ["+bCv((byte)0,yd)+ "]  [" + bCv((byte)1,yd)+"]  ["+bCv((byte)2,yd)+ "]  [" +bCv((byte)3,yd)+ "]  ["+bCv((byte)4,yd)+ "]  [" +bCv((byte)5,yd)+"]  ["+bCv((byte)6,yd)+ "] ["+bCv((byte)7,yd)+ "] ";
					 System.out.println( s );
					 yd++;
				 }
				 System.out.println("----------------------"+ Title+"-------------------------------------");
		}


		/**
		 *@author Alessandro Fuochi (UNIVR) ID083311
		 *@since  value in chessboard
		 */
			private String pCv(byte x,byte y){   
				String s=model.at(x,y)[0]+"";
				return nForm2(s);
			}
		/**
		 *@author Alessandro Fuochi (UNIVR) ID083311
		 *@since   value in cntlChessboard
		 */
			private String cCv(byte x,byte y){   
				String s=model.cntlAt(x,y)+"";
				return nForm2(s);
			}
			
		/**
		*@author Alessandro Fuochi (UNIVR) ID083311
		*@since  value in buChessboard
		*/
			private String bCv(byte x,byte y){   
				String s=model.buAt(x,y)+"";
				return nForm2(s);
			}
		/**
		*@author Alessandro Fuochi (UNIVR) ID083311
		*@since  formatter for display 
		*/
			private String nForm2(String s ){
				if (s.length() < 2) s="0"+s;
				if (s.equals("-1")) s="  ";
				return s;
			}







}



