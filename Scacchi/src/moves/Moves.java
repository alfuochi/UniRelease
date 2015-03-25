package moves;

import java.util.ArrayList;

import model.StAttach;

public interface Moves extends Chess {
	void grantedArea(byte i, byte j, byte k);
	void grantedAreaSelect();
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  move a chess
	 */
	String rollbackMove();
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  test for scacco 	 
	 */
	ArrayList<StAttach> testScacco();
	boolean moveChessboard(byte xFrom, byte yFrom, byte xTo, byte yTo);

}
	
	
	
