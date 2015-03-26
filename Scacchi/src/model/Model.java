package model;


import java.util.ArrayList;

import osUtil.OsUtil;


public interface Model extends Configuration  {

	Configuration getConfiguration();

	boolean colorCh(byte ch);


	StHistory lastHistory(int index);

	void setSelectState();

	void setMoveState();

	boolean isMoveState();

	boolean isSelectState();

	boolean isNoState();



	boolean resetColor();

	boolean blackColor();

	boolean changeColor();

	byte[] at(byte p);

	byte[] at(byte x, byte y);

	void set(byte x, byte y, byte value);

	boolean colorAt(byte x, byte y);

	/**
	*@since range dei pezzi del re   
	*@param kingCh true se sono i pezzi del re // false se i pezzi dell'altro re 
	*/
	byte[] rangeAt(byte king, boolean kingCh);

	

	byte[] xyFromPos(byte pos);
	/**
	 *@since 	lista delle mosse autorizzate da board  controllo  
	 *@return 	array of byte[2] 0 is x 1 is y 
	 */
	ArrayList<byte[]> listMove();

	byte posFromXY(byte x, byte y);

	void checkXY(byte x, byte y);

	boolean xyOk(byte x, byte y);

	void checkCh(byte ch);

	void cntlSet(byte x, byte y, byte value);

	byte cntlAt(byte x, byte y);

	byte stdXC(String s);

	byte stdYC(String s);

	void savePlay(String name);

	ArrayList<String[]> loadDemo();

	void testFileImage();

	void resetAll();

	void runDemo();

	OsUtil getOsUtil();

	String getSColor();

	String getSColor(Byte ch);

	void addMapsL();

	boolean isRepeatMove();


}
