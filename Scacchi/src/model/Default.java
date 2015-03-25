package model;

import java.awt.Color;

/**
*@author Alessandro Fuochi (UNIVR) ID083311
*@since  valori di default applicazione
*/
public  class Default {
	public static final String title="Scacchi                         da Alessandro Fuochi  ID083311 ( Only for UniVr  )";
	
	public static final int[] JVM_Minimum_Value={1, 8};
	
	public static final String[] path 			= {"torre_n","cavallo_n","alfiere_n","regina_n","re_n","alfiere_n","cavallo_n","torre_n","pedone_n","pedone_n","pedone_n","pedone_n","pedone_n","pedone_n","pedone_n","pedone_n","pedone_b","pedone_b","pedone_b","pedone_b","pedone_b","pedone_b","pedone_b","pedone_b","torre_b","cavallo_b","alfiere_b","regina_b","re_b","alfiere_b","cavallo_b","torre_b"}; 
	public static final byte[] sPos 			= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,56,57,58,59,60,61,62,63,48,49,50,51,52,53,54,55}; 
	
	public static final String[] itLabels		={"torre","cavallo","alfiere","regina","re","pedone"};
	public  static final String[] enLabels		={"rook","knight","bishop","queen","king","pawn"};
	
	public  static final String[] roules		={"r","h","b","q","k","p"};   
	public static  final String[] roulesTypes 	={"r","h","b","q","k","b","h","r","p","p","p","p","p","p","p","p","p","p","p","p","p","p","p","p","r","h","b","q","k","b","h","r"};   

	public static  final byte promBlack =0;
	public static  final byte promWhite =24;
	public static  final byte promRange=4;
	public static final byte[] isPawn         ={8,23};
	 
	public static  final String[] xCor 			={"A","B","C","D","E","F","G","H"};
    public static  final String[] yCor 			={"8","7","6","5","4","3","2","1"};
     
    public static  final byte blackKing=4;
    public static  final byte whiteKing=28;
    
    public static  final byte posFree= -1;
    public static  final byte posBusy = -2;
    public static  final byte posGranted = -3;
    public static  final byte posKill = -4;
    
    public static final byte lX=8;
    public static final byte lY=8;
    public static final byte lChessboard=lX*lY;
    public static final byte lProm=lX+lY;
    
    public static final byte startWhite=16;
    public static final byte endWhite=31;
    public static final byte startBlack=0;
    public static final byte endBlack=15;
    public static final byte startPWhite=48;
    public static final byte endPWhite=63;
    public static final byte startPBlack=0;
    public static final byte endPBlack=15;

    public static final boolean colorBlack = true;
    public static final boolean colorWhite = false;
    
    public static String iconPathDir="data?";
    public static String iconType=".bmp";
    
    public static String csvType=".cvs";
    public static String csvSep=",";
    public static String DemoFileName="Demo";
    
    public static String pdfType=".pdf";
    public static String manualName="ReadMe";
    
    public static final int frame_w =1200; 
    public static final int frame_h =700; 
      
    public static final Color[] busychess ={Color.YELLOW,Color.YELLOW}; 
    public static final Color[] grantedchess ={Color.GREEN,Color.GREEN}; 
    public static final Color[] killchess ={Color.RED,Color.RED}; 
    public static final boolean printSysOut=false;  
    
    public static byte rules50= 50; 
    
    public static final int runDisplayDelay = 200;
    public static final int runDemoDelay = 800;
    public static final String[] DemoS0={"Barbiere","E2","E3","E7","E6","E3","E4","E6","E5","D1","H5","B8","C6","F1","C4","G8","F6","H5","F7"};
	public static final String[] DemoS1={"Scacco Semplice","E2","E3","E7","E6","E3","E4","E6","E5","D1","H5","B8","C6","F1","B5","G8","F6","H5","F7"};
	
}
