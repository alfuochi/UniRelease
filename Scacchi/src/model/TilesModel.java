package model;



import java.io.InputStream;
import java.util.ArrayList;

import osUtil.OsUtil;


public class TilesModel extends model.IConfiguration implements Model {
	Configuration configuration;
	OsUtil osUtil;
	public TilesModel(){
		configuration=new model.IConfiguration();
		this.osUtil=new osUtil.IOsUtil();
	}
		
	/**
	* @author Alessandro Fuochi (UNIVR) ID083311
	* @since  colore del pezzo
	*/
	@Override
	public boolean	colorCh(byte ch){
		return ! (ch > Default.endBlack);
	}
	/**
	* @author Alessandro Fuochi (UNIVR) ID083311
	* @since  get configuration
	*/
	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return configuration;
	}
	/**
	* @author Alessandro Fuochi (UNIVR) ID083311
	* @since  get configuration
	*/
	@Override
	public OsUtil getOsUtil() {
		// TODO Auto-generated method stub
		return osUtil;
	}

	@Override 
	public void runDemo()	{
	   setDemo(true);
	   setStopDemo(false);
	}
	/**
	 * @since memorizza lo stato 
	 */
	@Override 
	public void addMapsL(){
		ArrayList<String> e= new ArrayList<String>();
		String t="";
		for(byte n=0;n< Default.lChessboard;n++)
        	if (at(n)[0] != Default.posFree){
        		e.add(n+".");
        		t+=n+".";
        		e.add(getConvProm(at(n)[0])+".");
        	    t+=  getConvProm(at(n)[0])+".";
        	}
        
		addMapsLR(osUtil.MD5(t));
		
	
        setRepeatMove();
	}
	/**
	 * @since memorizza lo stato 
	 */
	@Override 
	public boolean isRepeatMove(){
		return getRepeatMove();
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  ultimo array con index -1
	*/
	@Override
	public StHistory lastHistory(int index){
		return (StHistory) getHistory(sizeHistory()+index);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  set stato 'select' 
	*/
	@Override 
	public void setSelectState()	{
	   setSelect(true);
	   setMove(false);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since set stato 'move' 
	*/
	@Override 
	public void setMoveState()	{
		 setSelect(false);
		 setMove(true);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since test move state 	 
	 */
	@Override 
	public boolean isMoveState()	{
	   return !getSelect() && getMove() ;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since test selected state
	*/
	@Override 
	public boolean isSelectState()	{
	   return getSelect() && !getMove() ;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since test invalid state
	*/
	@Override 
	public boolean isNoState()	{
	   return !getSelect() && !getMove() ;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since reset a Default color
	*/
	@Override  
	public boolean resetColor() {
		setColor(Default.colorWhite);
		return getColor();
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since reset a nero
	*/
	@Override
	public boolean blackColor() {
		setColor(Default.colorBlack);
		return getColor();
	}
	/**
	 *@author Alessandro Fuochi (UNIVR) ID083311
	 *@since cambia il colore e ritorna corrente
	 */
	@Override
	public boolean changeColor() {
		setColor(!getColor());
		return getColor();
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  colore in formato stringa
	*/
	@Override
	public String getSColor() {
		String c="bianco";
		if (getColor())
			c="nero";
		return c;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  valore pezzo nella posizione array
	*/
	@Override
	public byte[] at(byte p) {
		byte [] c=new byte [2];
		c[0]= ((byte []) getChessboard(chessboard))[p];
	    if (isPawn(c[0])) c[1]=  getProm(c[0]) ;
	    else
	    	c[1]=Default.posFree;
	    return c;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since valore pezzo nella posizione x,y 
	*/
	@Override
	public byte[] at(byte x, byte y) {
			return at(posFromXY(x,y));
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since set valore pezzo nella posizione x,y 
	*/
	@Override
	public void set(byte x, byte y, byte value) {
		setXY(chessboard,x,y,value);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  color of chess in x y position (Waring: stop program if chess not present  )
	*/
	@Override
	public boolean colorAt(byte x, byte y) {
		if (at(x,y)[0] < 0) {
			System.out.println(" Error at  " + Default.xCor[x] +" "+ Default.yCor[y] + " chess not found !!!!! " );
			throw new FNDException(new Exception());
		}
		return !( at(x,y)[0] >  Default.endBlack);
	}
	

	
	@Override
	public byte[] rangeAt(byte king, boolean kingCh) {
		boolean sel=false;
		byte [] chRange= new byte [2];
		if (kingCh){
			if ( (king == Default.whiteKing) ) sel=false;
			 else sel=true;
			} else{
				if ((king == Default.blackKing) ) sel=true;
					else 
					sel=false;
				}
		if (sel){
			chRange[0]=Default.startWhite;
			chRange[1]=Default.endWhite;
		}	else {
			chRange[0]=Default.startBlack;
			chRange[1]=Default.endBlack;
		} 
		return chRange;
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since value control type in cntl chessboard 
	*/
	@Override
	public byte cntlAt(byte x, byte y) {
		return atXY(cntlChessboard,x,y);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since set value control type in cntl chessboard 
	*/
	@Override
	public void cntlSet(byte x, byte y, byte value) {
		setXY(cntlChessboard,x,y, value);
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  test if valid id chess (Warning : if not valid stop execution program !!)
	*/
	@Override
	public void checkCh(byte ch){
		if ( ch <  0 || ch >= Default.sPos.length ){ 
			System.out.println("[checkCh] Errore numero pezzo scacchiera " + ch);
			throw new CHException(new Exception());
		}
	}
	/**
	*@author Alessandro Fuochi (UNIVR) ID083311
	*@since  test true/false  if x y coordinate is legal   
	*/
	@Override
	public	 boolean xyOk(byte x,byte y){
		return !(x < 0|| y <0 || x > 7 || y > 7 );
	}
		
	
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  convert x y position in position (in chessboard array type )
	 * @return position (byte)
	 */
	@Override
	public byte posFromXY(byte x,byte y){
		checkXY( x, y);
		return	(byte) ((y*Default.lY) +x);
	}
	/**
	 *@since 	lista delle mosse autorizzate da board  controllo  
	 *@return 	array of byte[2] 0 is x 1 is y 
	 */
	@Override
	public ArrayList<byte[] > listMove(){
		ArrayList<byte []> ind=new ArrayList<byte[]>(); 
		for (byte y=0;y< Default.lY;y++)
			for (byte x=0;x< Default.lX;x++)
				if(	cntlAt(x,y) < Default.posFree) {
					byte [] cord=new byte [2];	
					cord[0]=x;
					cord[1]=y;
					ind.add(cord);
				}
		return ind;
	}
		
	
	
		
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since chess standard x coordinate	 
	 */
		@Override
		public byte stdXC(String s){
				byte i=0;
				byte v=-1;
				while(i<8){
					if (s.toUpperCase().equals(Default.xCor[i])) v=i;
					i++;
				}
			if (v==-1) System.out.print(" Sx [" + s + "] NON VALIDA " );
			return  v;
			}
	/**
	 *@author Alessandro Fuochi (UNIVR) ID083311
	 *@since  chess standard y coordinate
	 */
		@Override
		public	 byte stdYC(String s){
				byte i=0;
				byte v=-1;
				while(i<8){
					if (s.equals(Default.yCor[i])) v=i;
					i++;
				}
				return  v;
			}
	
	/**
	 *@author Alessandro Fuochi (UNIVR) ID083311
	 *@since reset game history parameter
	 */
		@Override
		public void resetAll() {
			clrHistory();
		    resetState(); 
		    resetColor();
		    reset();
		}
				
	/**
	* @author Alessandro Fuochi (UNIVR) ID083311
	* @since get value in x,y of chessboard (board)
	* @param board
	* @param x
	* @param y
	* @return
	*/
	private byte atXY(byte board,byte x,byte y){
			return	getChessboard(board)[posFromXY(x,y)];
	}
		/**
		* @author Alessandro Fuochi (UNIVR) ID083311
		* @since set value in x,y at chessboard (board)
		* @param board
		* @param x
		* @param y
		* @param value
		*/
		private void setXY(byte board,byte x,byte y,byte value){
			byte [] e=getChessboard(board);
			e[posFromXY(x,y)]=value;
			setChessboard(board,e);
		}
		/**
		 * 
		 *@author Alessandro Fuochi (UNIVR) ID083311
		 *@since  convert address 'pos' in adddress x,y
		 *@return byte[2] [0] x [1] y 
		 */
		@Override
		public byte[] xyFromPos(byte pos){
			checkPos( pos);
			byte d=8;
			byte[] posA=new byte[2];
			posA[1]= (byte) (pos/d);
			posA[0]=(byte) (pos - (posA[1]*d)); 
			return posA;
		}
		/**
		 *@author Alessandro Fuochi (UNIVR) ID083311
		 *@since exit with pos invalid values
		 */
		private void checkPos(byte pos){
			if (pos < 0 || pos > Default.lChessboard){
				System.out.println("[checkPos] Error position in chessboard " + pos);
				throw new POSException(new Exception());
			}
		}
		
		/**
		 *@author Alessandro Fuochi (UNIVR) ID083311
		 *@since exit with x,y invalid values
		 */
		@Override
		public void checkXY(byte x,byte y){
			if (x < 0 || y < 0 || x >= Default.lX  || y >= Default.lY ){ 
			 System.out.println("[checkXY] Errore coordinate x " + x  +" y " + y);
			 throw new CXY_Exception(new Exception());
			}
		}
		
		/**
		 * @author Alessandro Fuochi (UNIVR) ID083311
		 * @since ffff
		 */
		
		private class CXY_Exception extends RuntimeException {
					/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

					public CXY_Exception(Exception e1) throws  CXY_Exception  { 
						e1.printStackTrace();
					    System.exit(-1);
					 }
				}
			
			private class FNDException extends RuntimeException {
			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			public FNDException( Exception e2) throws FNDException{ 
				 e2.printStackTrace();
			     System.exit(-1);
			 }
		}
		
		private class POSException extends RuntimeException {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public POSException(Exception e3) throws POSException  { 
				 e3.printStackTrace();
			     System.exit(-1);
			 }
		}
		
		private class CHException extends RuntimeException {
		
				/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				public CHException(Exception e) throws CHException { 
				 e.printStackTrace();
			     System.exit(-1);
			 }
		}


	/**
	 *@author Alessandro Fuochi (UNIVR) ID083311
	 *@since  crea unica lista con demo  
	 */
    
	@Override
	public ArrayList<String[]> loadDemo(){
		ArrayList<String[]> list=new  ArrayList<String[]> ();
		list.add(Default.DemoS0);
	    list.add(Default.DemoS1);
	    osUtil.loadCsv( osUtil.defaultAppDir(true)+Default.DemoFileName+Default.csvType, list,Default.csvSep);
	    return list;
	}
 
	/**
	 * @since legge storico e crea nuova demo
	 */
	@Override
	public void savePlay(String name){
	    String s=name+Default.csvSep;
	    for(int n=0;n< sizeHistory();n++){
			StHistory a =getHistory(n);
		    if(a.promReq)
		    s+=a.valueProm+Default.csvSep;
			s+=Default.xCor[a.x].toString()+Default.yCor[a.y].toString()+Default.csvSep;
	    }
	
	    String path = osUtil.defaultAppDir(true)+"Demo"+Default.csvType;
	    osUtil.appendFile( path ,s+"\n");
	 }	
	  /**
	    * @author Alessandro
	    * @since  test se esistono tutti i file
	    */
	@Override   
	public void testFileImage(){
		  for(byte n=0;n<Default.path.length;n++ )
		   osUtil.ldImage(osUtil.defaultAppDir(true)+Default.path[n]+Default.iconType);
		  if (isPrintSysOut())
		  System.out.println("\n[Scacchi  run on "+ System.getProperty("os.name")+" Arc. "+ System.getProperty("os.arch")+" Base dir "+System.getProperty("user.dir")+" ]\n Image file from "+osUtil.defaultAppDir(true)+" OK\n");
	     }

	@Override
	public String getSColor(Byte ch) {
		if (colorCh(ch)) return "nero";
		else return "bianco";
	}

}



