package controller;

import moves.Moves;

public interface Controller {
	/**
	 * @since esegue comandi da dati file + demo std
	 */
	void runDemo(int gameIndex);
	
	/**
	 * @since esegue comandi da listener scacchiera
	 */
	void onClick(byte x, byte y, byte value);

	Moves getMoves();
	
	}
