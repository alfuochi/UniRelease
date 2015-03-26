package osUtil;

import java.awt.Image;
import java.util.ArrayList;

public interface OsUtil {

	void wait(int delay);

	void beep();

	/**
	 * @since memoria usata da jvm 
	 * @return long kbyte
	 */
	long memory();

	
	 /**
     * @since separatore directory attuale  (da sistema operativo) 
     * @return string separator 
     */
	String fileSeparator();
    /**
     * @since directory attuale applicazione (da sistema operativo) 
     * @param relative false se richiesta path assoluta
     * @return string path 
     */
	String defaultAppDir(boolean relative);
    /**
     * @since creazione oggetto image da file
     * @param path String path file
     * @return Image (oggetto  image)
     */
	Image ldImage(String path);
    /**
     * @since aggiunta record a file
     * @param path String path file
     * @param append record in accodamento
     */
	void appendFile(String path, String append);

	/**
	 * @since carica in array list liste di string equivalenti a campi csv 
	 * @param path  path file csv
	 * @param list array list a cui verranno aggiunti record csv
	 * @param separator separatore csv
	 */
	void loadCsv(String path, ArrayList<String[]> list,String separator);

	boolean mkdir(String dir);

	/**
   	 *@since stampa messaggio e percorso ed esce con eCode negativo
   	 *@param s   string message
   	 *@param eCode error code
   	 */
	void printOutError(String s, int eCode, boolean optional);
	void printOutError(String s, int eCode, boolean optionalPrint, Exception e);

	int[] javaVersion();

	void testJVM();

	String javaVersionDesc();

	String memoryDesc();

	String MD5(String md5);

	
	

	
}
