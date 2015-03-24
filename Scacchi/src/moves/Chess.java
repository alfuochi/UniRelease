package moves;

public abstract interface Chess {

	
    /**
     * @since primo caricamento pezzi scacchiera
     */
	public	void loadChessboard();
	/**
	 * @since test se esiste pezzo in posizione scacchiera
	 */
	public	boolean chExist(byte x, byte y);
	/**
	 * @since test se valore pezzo e' valido
	 */
	public	boolean isChValue(byte chess);
	/**
	 * @since test se all'indirizzo corrisponde un percorso valido per mangiare / muoversi
	 */
	boolean cntlGrantedOrKilled(byte x, byte y);
	/**
	 * @since numero dei pezzi esistenti sulla scacchiera per colore 
	 */
	byte numberOfChess(boolean color);

	
	/**
	 *@since crea in array di controllo le possibili mosse del pezzo coinvolto 
	 */
	void grantedArea(byte obj, byte x, byte y);
	/**
	 * @since dove e' questo pezzo byte[0] x byte[1] y
	 */
	byte[] whereIsCh(byte ch);

}
