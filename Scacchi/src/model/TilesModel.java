package model;

import java.util.ArrayList;

import osUtil.OsUtil;


public class TilesModel extends model.IConfiguration implements Model {
	
	public TilesModel(){
		configuration=new model.IConfiguration();
		this.osUtil=new osUtil.IOsUtil();
	}
		
	/**
	* @since  colore del pezzo
	*/
	@Override
	public boolean	colorCh(byte ch){
		return ! (ch > Default.endBlack);
	}
	/**
	* @since  get configuration
	*/
	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return configuration;
	}
	/**
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
		*@since test move state 	 
	 */
	@Override 
	public boolean isMoveState()	{
	   return !getSelect() && getMove() ;
	}
	/**
		*@since test selected state
	*/
	@Override 
	public boolean isSelectState()	{
	   return getSelect() && !getMove() ;
	}
	/**
	*@since test invalid state
	*/
	@Override 
	public boolean isNoState()	{
	   return !getSelect() && !getMove() ;
	}
	/**
	*@since reset a Default color
	*/
	@Override  
	public boolean resetColor() {
		setColor(Default.colorWhite);
		return getColor();
	}
	/**
	*@since reset a nero
	*/
	@Override
	public boolean blackColor() {
		setColor(Default.colorBlack);
		return getColor();
	}
	/**
	 *@since cambia il colore e ritorna corrente
	 */
	@Override
	public boolean changeColor() {
		setColor(!getColor());
		return getColor();
	}
	/**
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
	*@since valore pezzo nella posizione x,y 
	*/
	@Override
	public byte[] at(byte x, byte y) {
			return at(posFromXY(x,y));
	}
	/**
	*@since set valore pezzo nella posizione x,y 
	*/
	@Override
	public void set(byte x, byte y, byte value) {
		setXY(chessboard,x,y,value);
	}
	/**
	*@since  colore dei pezzi in x y posizione 
	*/
	@Override
	public boolean colorAt(byte x, byte y) {
		if (at(x,y)[0] < 0) {
			osUtil.printOutError(" Error at  " + Default.xCor[x] +" "+ Default.yCor[y] + " chess not found !!!!! ",-1,true );
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
	*@since value control type in cntl chessboard 
	*/
	@Override
	public byte cntlAt(byte x, byte y) {
		return atXY(cntlChessboard,x,y);
	}
	/**
	*@since set value control type in cntl chessboard 
	*/
	@Override
	public void cntlSet(byte x, byte y, byte value) {
		setXY(cntlChessboard,x,y, value);
	}
	/**
	*@since  test if valid id chess (Warning : if not valid stop execution program !!)
	*/
	@Override
	public void checkCh(byte ch){
		if ( ch <  0 || ch >= Default.sPos.length ){ 
			osUtil.printOutError("[checkCh] Errore numero pezzo scacchiera " + ch,-1,true);
		}
	}
	/**
	*@since  test true/false  if x y coordinate is legal   
	*/
	@Override
	public	 boolean xyOk(byte x,byte y){
		return !(x < 0|| y <0 || x > 7 || y > 7 );
	}
		
	
	/**
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
				if(	cntlAt(x,y) < Default.posBusy) {
					byte [] cord=new byte [2];	
					cord[0]=x;
					cord[1]=y;
					ind.add(cord);
				}
		return ind;
	}
		
	/**
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
		 *@since exit with pos invalid values
		 */
		private void checkPos(byte pos){
			if (pos < 0 || pos > Default.lChessboard){
				osUtil.printOutError("[checkPos] Error position in chessboard " + pos,-1,true);
			}
		}
		
		/**
		 *@since exit with x,y invalid values
		 */
		@Override
		public void checkXY(byte x,byte y){
			if (x < 0 || y < 0 || x >= Default.lX  || y >= Default.lY ){ 
			osUtil.printOutError("[checkXY] Errore coordinate x " + x  +" y " + y,-1,true);
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



