package moves;

import java.util.ArrayList;

import model.StAttach;

public   interface Scacco extends Chess{

	boolean moveChessboard(byte xFrom, byte yFrom, byte xTo, byte yTo);

	ArrayList<StAttach> testScacco();

}
