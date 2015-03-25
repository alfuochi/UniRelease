package moves;

import java.util.ArrayList;

import model.StAttach;

public interface Moves extends Scacco {
	void grantedArea(byte i, byte j, byte k);
	void grantedAreaSelect();
	/**
	 * @author Alessandro Fuochi (UNIVR) ID083311
	 * @since  move a chess
	 */
	String rollbackMove();
	boolean moveChessboard(byte xFrom, byte yFrom, byte xTo, byte yTo);

	
	
}
	
	
	
