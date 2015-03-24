package model;


public abstract interface Configuration {
	
	void addHistory(StHistory element);

	void removeHistory(int index);

	int sizeHistory();

	void backUpColor();

	void restoreColor();

	/**
	 *@since imposta condizione di stallo  
	 */
	boolean getStall();

	StHistory getHistory(int index);

	boolean getColor();
	
	/**
	 *@since backup scacchiera e record promozioni  
	 */
	void backUpAt();

	/**
	 *@since restore scacchiera e record promozioni  
	 *@param noRemove false se record backup non serve successivamente
	 */
	void restoreAt(boolean noRemove);

	/**
    *@since controlla disponibilita backup scacchiera  
    */
	boolean isBackUpAvailable();
	
	/**
	 * @since controlla se il pezzo era un pedone
	 */
	boolean isPawn(byte chess);

	byte getConvProm(byte chess);
	
	/**
	 * @since reset scacchiera
	 */
	void reset();

	/**
	 * @since controlla se il pezzo e' ancora un pedone
	 */
	boolean isPawnAgain(byte chess);

	void resetCntl();
 

	

	boolean isPrintSysOut();

	/**
	 * @since determina se finire partita per regola 50
	 */
	boolean isPattaRoules50();

	/**
	 *@since imposta contatore regola50 per colore
	 */
	void setRoules50(boolean color);

	/**
     *@since reset regola 50 per colore 
     */
	void resetRoules50(boolean color);
     /**
      *@since stato attuale regola 50 
      *@return byte[2] valore nero,bianco
      */
	byte[] getRoules50();
    /**
     *@since restituisce la modalita di input dei dati al processo 
     */
	boolean isDemo();

	/**
	 *@since imposta valore promozione da schermata selezione con output (0,1,2,3)  
	 */
	void setProm03(byte chessBefore, byte chessAfterPos);

	/**
	 * @since imposta valore promozione pedone
	 */
	void setProm(byte chessBefore, byte chessAfter);
	void setSelect(boolean value);

	void setHistory(int index, StHistory element);
	
	void setColor(boolean value);
	
	void setStall(boolean value);

	
}