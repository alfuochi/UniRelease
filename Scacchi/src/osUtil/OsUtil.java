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

	Image ldImage(String name);

	void appendFile(String path, String append);

	void loadCsv(ArrayList<String[]> list);

	boolean mkdir(String dir);

	
	void printOutError(String s, int eCode);
}
