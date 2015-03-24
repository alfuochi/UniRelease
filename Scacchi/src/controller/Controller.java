package controller;

public interface Controller {
	/**
	 * @since richiama consecutivamente startprocess passandogli lista mosse da ( classe + file (se esiste) )
	 */
	void runDemo(int gameIndex);
	/**
	 * @since chiama startprocess passandogli input da scacchiera
	 */
	void onClick(byte x, byte y, byte value);
	
	}
