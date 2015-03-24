package model;


import java.util.ArrayList;

import osUtil.OsUtil;


public interface Model extends Configuration  {

	Configuration getConfiguration();

	boolean colorCh(byte ch);

	void resetState();

	StHistory lastHistory(int index);

	void setSelectState();

	void setMoveState();

	boolean isMoveState();

	boolean isSelectState();

	boolean isNoState();



	boolean resetColor();

	boolean blackColor();

	boolean changeColor();

	String sColorNow();

	byte[] at(byte p);

	byte[] at(byte x, byte y);

	void set(byte x, byte y, byte value);

	boolean colorAt(byte x, byte y);

	byte[] rangeAt(byte king, boolean kingCh);

	

	byte[] xyFromPos(byte pos);

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

	void printM(String Title);

	void resetAll();

	void runDemo();

	OsUtil getOsUtil();

	


}
