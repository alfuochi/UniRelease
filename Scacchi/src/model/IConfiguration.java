package model;

import java.util.ArrayList;
import java.util.Collections;

import osUtil.OsUtil;

/**
 * 
 *@author Alessandro Fuochi (UNIVR) ID083311
 *@since  configuration 
 */
public  class IConfiguration implements Configuration {
	protected Configuration configuration;
	protected OsUtil osUtil;
	
	private static ArrayList<String> mapsL;
	private static ArrayList<byte[]> chessboardL;
	protected final static byte chessboard=0;
	protected final static byte cntlChessboard=1;
	protected final static byte buChessboard=2;
	private static ArrayList<byte[]> promL; 
	private final static byte prom=0;
	private final static byte buProm=1;
	private static boolean color;
	private static boolean bColor;
	private static ArrayList<StHistory> History; 
	private static boolean select;
	private static boolean move;
	
	private static int  stallCounter;
	
	private static int fewChessW;
	private static int fewChessB;
	
	private static boolean repeatMove;
	
	private static byte pattaW;
	private static byte pattaB;
	
	private static boolean printSysOut;
	private static boolean demo;
	private static boolean stopDemo;
	
	/**
	 * @since  configuration, caricamento classe	 
	 */
	public   IConfiguration() {
		mapsL=new  ArrayList<String>();
		History=new  ArrayList<StHistory>();
		chessboardL= new ArrayList<byte[]>();
		promL=new ArrayList<byte[]>();
		for (byte n=0;n<2;n++)
			chessboardL.add(new byte[Default.lChessboard]);
		for (byte n=0;n<1;n++)
			promL.add(new byte[Default.lProm]);
		reset();
	    resetState();
		printSysOut=Default.printSysOut;
	  
	}
	/** 
	*@since reset array e contatory
	*/
	@Override
	public void reset(){
		resetCB(chessboardL,chessboard,Default.lChessboard);
		resetCntl();
		resetCB(promL,prom,Default.lProm);
		clrHistory();
		clrMapsL();
		resetRoules50(true);
		resetRoules50(false);
	}
	/** 
	*@since reset stati di controllo
	*/
	@Override 
	public void resetState()	{
	   setSelect(false);
	   setMove(false);
	   resetStall();
	   setDemo(false);
	   setStopDemo(true);
	}
	
	/**
	*@since reset controllo scacchiera 
	*/
	 @Override
	public  void resetCntl() {
		 resetCB(chessboardL,cntlChessboard,Default.lChessboard);
	}
	
	 /**
	 * @since  get array di una 'bord' della scacchiera (vedi costanti classe configurazione )
	 */
	protected byte[] getChessboard(byte index){
		byte [] granted={0,1,2};
		checkNCB(granted,index);
		return chessboardL.get(index);
	}
	
	/**
	 * @since  set array di una 'bord' della scacchiera (vedi costanti classe configurazione )
	 */
	protected byte[] setChessboard(byte index,byte[] e){
		byte [] granted={0,1};
		checkNCB(granted,index);
		return chessboardL.set(index,e);
	}
	
	/**
	 * @since backup array scacchiera
	 */
	@Override
	public void backUpAt(){
		if (chessboardL.size()< buChessboard+1)
			chessboardL.add(new byte[Default.lChessboard]);
		chessboardL.set(buChessboard, chessboardL.get(chessboard).clone());
		if (promL.size()< buProm+1)
			promL.add(new byte[Default.lProm]);
		promL.set(buProm, promL.get(prom).clone());
	}	
	
	/**
	 * @since ripristina valori scacchiera e rimuove array backup se richiesto 
	 */
	@Override
	public 	void restoreAt( boolean noRemove){
		chessboardL.set(chessboard, chessboardL.get(buChessboard).clone());
		promL.set(prom, promL.get(buProm).clone());
		if (chessboardL.size()== (buChessboard+1) && !noRemove)
		chessboardL.remove(buChessboard);
		if (promL.size()== (buProm+1) && !noRemove)
		 promL.remove(buProm);
	}	
	/**
	 * @since verifica la presenza del backup dati scacchiera
	 */

	@Override
	public boolean isBackUpAvailable(){
		return chessboardL.size()== (buChessboard+1) && promL.size()== (buProm+1);
	}
	/**
	 * @since restituisce contatori regola 50 mosse
	 */
	@Override
	public byte[] getRoules50(){
		byte[] e=new byte[2];
		e[0]=pattaB;
		e[1]=pattaW;
	    return e;
	}
	
	/**
	 * @since valori iniziali per colore
	 */
	@Override
	public void resetRoules50(boolean color){
		if (color)
		pattaB=Default.rules50Move;
		else
		pattaW=Default.rules50Move;
	}
	
	/**
	 * @since applicazione regola 50 mosse
	 */
	@Override
	public void setRoules50(boolean color){
		if (color){
			if (pattaB >0)  pattaB--;
		}else{
			if (pattaW > 0) pattaW--;
		}
	}
	/**
	 * @since determina patta regola 50 mosse
	 */
	@Override
	public boolean isPattaRoules50(){
		return pattaW == 0 && pattaB == 0;
	}

	@Override
	public void setFewChess(int value,boolean color){
		if (color)
		fewChessB=value;
		else
		fewChessW=value;
	}
	
	@Override
	public boolean isFewChess(){
		return ((fewChessB <= Default.rulesFewChess) && (fewChessW <= Default.rulesFewChess));
	}
	
	
	protected void addMapsLR(String e){
		mapsL.add(e);
	}
	
	 /**
     * @since calcola ripetizioni configurazioni
     */
	 protected void setRepeatMove() {
		Collections.sort(mapsL);
		int repeat=0;
		repeatMove=false;
		if (! (mapsL.size()< Default.rulesRepeatMove)) 
		for (int n=0;n< mapsL.size()-1;n++){
			if (((String) mapsL.get(n+1)).equals((String) mapsL.get(n)))  repeat++;	
			else repeat=0;
			if (repeat >= Default.rulesRepeatMove) {
			 	repeatMove=true;
			    break;
			 }
		}
	  
	}
	
	protected boolean getRepeatMove() {
		 return repeatMove;
	 }
	
	@Override
	public void setStopDemo(boolean value){
		stopDemo=value;
	}
	
	@Override
	public boolean isStopDemo(){
		return stopDemo;
	}
	@Override
	public void setSelect(boolean value){
		select=value;
	}
	
	protected void setDemo(boolean value){
		demo=value;
	}
	
	@Override
	public boolean isDemo(){
		return demo;
	}
	protected boolean getSelect(){
		return select;
	}
	
	protected void setMove(boolean value){
		move=value;
	}
	
	protected boolean getMove(){
		return move;
	}
	

	@Override
	public 	void addStall(int value){
		stallCounter+=value;
	}
	
	@Override
	public 	void resetStall(){
		stallCounter=0;
	}
	
	@Override
	public 	boolean isStall(){
		return stallCounter == 0;
	}
	
	@Override
	public 
	void setColor(boolean value){
		color=value;
	}
	
	@Override
	public 
	 boolean getColor(){
		return color;
	}
	
	protected void clrHistory(){
		History.clear();
	}
	
	protected void clrMapsL(){
		mapsL.clear();
	}
	
	protected void clrPromL(){
		promL.clear();
	}
	
	@Override
	public void setHistory(int index,StHistory element){
		History.set(index, element);
	}
	
	@Override
	public void addHistory(StHistory element){
		History.add( element);
	}
	
	@Override
	public 	void removeHistory(int index){
		History.remove(index);
	}
	
	@Override
	public 	 StHistory getHistory(int index){
		return History.get(index);
	}
	
	@Override
	public 	 int sizeHistory(){
		return History.size();
	}
	
	@Override
	public 	void backUpColor(){
		bColor=color;
	}
	
	@Override
	public  void restoreColor(){
		color=bColor;
	}
	/**
	 * @since promozione pedone valore prima valore dopo da schermata video
	 */
	@Override 
	public void setProm03(byte chessBefore,byte chessAfterPos){
		checkPawn( chessBefore);
		if (chessBefore >Default.endBlack)
			setProm(chessBefore,(byte)((int) chessAfterPos + Default.promWhite));
		else
			setProm(chessBefore,(byte)((int) chessAfterPos+ Default.promBlack));
	}
	
	/**
	 * @since promozione pedone valore prima valore dopo
	 */
	@Override 
	public void setProm(byte chessBefore,byte chessAfter){
		checkPawn( chessBefore);
		byte[] e=promL.get(prom);
			e[chessBefore -8]=chessAfter;
	    promL.set(prom, e);
	}
	
	protected byte getProm(byte chess){
		checkPawn( chess);
		return  promL.get(prom)[chess -8];	
	}
	@Override
	public byte getConvProm(byte chess){
		if (isPawn(chess) && getProm( chess) != Default.posFree)
			return getProm( chess);
		return chess;	
	}
	/**
	 * verifica che il pezzo sia o sia rimasto un pedone
	 */
	@Override 
	public boolean isPawnAgain(byte chess){
		return isPawn (getConvProm(chess));
	}
	
	@Override 
	public boolean isPawn(byte chess){
		return  ( chess >=Default.isPawn[0] &&  chess <=Default.isPawn[1]); 
	} 
	@Override 
	public boolean isPrintSysOut(){
	    return  printSysOut;
	}

	/**
	 * @since reset single chessboard element configuration 	 
	 */
	protected  void resetCB(ArrayList<byte[]> boardSet,byte board,byte len ) {
		byte[] e= new byte[len];
		for (byte c = 0; c < len; c++){
			e[c]=Default.posFree;
		}
		boardSet.set(board,e);	
	}
	
	private void checkNCB(byte[] granted,byte index){
		boolean g=false;
		for(byte n=0;n<granted.length;n++ )
			if (granted[n]== index) g=true;
				if (!g) osUtil.printOutError(" Errore posizione sulla scacchiera " + index, -1 ,true);
	}
			
	private void checkPawn(byte chess){
		if (!isPawn( chess)) osUtil.printOutError(" Non e' un pedone " + chess, -1 ,true);
	}
				
			
	}