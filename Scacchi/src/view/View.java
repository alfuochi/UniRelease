package view;

import java.awt.Color;

import controller.Controller;
import model.Model;


public interface View {

	void ldStart();

	void moveChD(byte xFrom, byte yFrom, byte xTo, byte yTo, byte eat,byte moved);

	byte reqProm(byte chess);

	void printScacco(boolean auto, boolean sm, boolean end, boolean king);

	void markBorderArea();

	void clearAllCenterBorder();

	void syncChessboard();

	void writeMessage(String m, Color c);

	void ldIconCe(byte chess);

	void clrIconCe(byte chess);

	Model getModel();

	Controller getController();

	Controller getConfiguration();

	void setController(Controller controller);

	void selCenterBorder(byte x, byte y);


	void writeAddInfo(String m, Color c);

	void setIconChess(byte chess, byte x, byte y);



}

