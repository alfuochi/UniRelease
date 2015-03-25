package osUtil;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import model.Default;

public class IOsUtil implements OsUtil {
	
	@Override
	public void wait( int delay){  
	try {
		  Thread.sleep(delay);                
	  } catch(InterruptedException ex) {
		  Thread.currentThread().interrupt();
	  }
	}
	
	@Override
	public void beep(){
		Toolkit.getDefaultToolkit().beep();
	}
	
	@Override 
	public long memory(){
		System.gc();
		return (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024);
	}
	
	@Override 
	public String fileSeparator(){
		return File.separator;
	}
	
	@Override 
	public String defaultAppDir(boolean relative){
		String d=Default.iconPathDir;
		if (!relative )
		d=System.getProperty("user.dir")+"?"+d;
		return d.replace("?", fileSeparator());
	}
	
	@Override
	public boolean mkdir(String dir){
		boolean t=false;
		File  f = new File(dir);
		if (!f.exists()){
		f.mkdir();
		t=true;
		}
	    return t;	
	}
	
	/**
     * @since crea lista demo da file
      */
	  @Override
	   public void loadCsv(String path,ArrayList<String[]> list,String separator){
		File file =null;
		ArrayList<String> listToken=new ArrayList<String>();
		try {
		  
		   file = new File(path);
		   if(!file.exists()) {
			   printOutError( " file " +path +" non trovato ",1,true);
			   } else {
			   BufferedReader br = new BufferedReader( new FileReader(path));
			   String strLine = "";
			   StringTokenizer st = null;
			   while( (strLine = br.readLine()) != null)
			   {
				   st = new StringTokenizer(strLine,separator);
				   listToken.clear();
				   while(st.hasMoreTokens())
				   {
					   listToken.add(st.nextToken());
				   }
				   list.add( listToken.toArray(new String[listToken.size()]));
			   }
			   br.close();
		   } 
		   		}catch (Exception ex) {
		   			ex.printStackTrace();
		   			System.exit(-1);
		   		}
	  }
	  /**
       * @since aggiunge demo  
       * @param append record csv formato nome ,selezione ,mossa,...
       */
	  @Override
	   public void appendFile(String path ,String append)	{
		  try
		  {
	        File file = new File(path);
	        if(!file.exists())
	        	printOutError( " file " +path +" non trovato ",1,true);
	         else{
	        FileWriter fileWriter = new FileWriter(file,true);
	        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
	        fileWriter.append(append);
	        bufferFileWriter.close();
	        }
		  	}catch(Exception ex){
			  ex.printStackTrace();
	   			System.exit(-1);
	   		}
	  	}
		/**
	    * @since  ritorna image da file
	    */
	   	@Override
	   	public  Image ldImage(String path){
		   File file =null;
		   Image img=null;
		   try {
			   file = new File(path);
			   if(!file.exists()) 
				   printOutError( " file " +path +" non trovato ",1,true);
			   else
				   img = ImageIO.read(file);
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-1);
		   }
		   return img;
	   	}   
	   /**
	    * @since stampa (se previsto da default o in caso di uscita dal programma o sempre ) in sysout 
	    * @since messaggio con percorso con classi metodi e linee codice  
	    */
	   	@Override
	   	public void printOutError(String s,int eCode,boolean optionalPrint){
		    if ((optionalPrint == Default.printSysOut) || eCode < 0){
		    	System.out.println("\n ["+Default.title+"]\n\n ERROR  ["+ s +" ] \n\n Stack\n");
		    	StackTraceElement[] se=new Exception().getStackTrace();
		    	for (int n=0;n< se.length;n++)
		    		System.out.println(" Class ["+se[n].getClassName()+"] Method [" +se[n].getMethodName() + "]  Line " + se[n].getLineNumber()+"\n");
		    	System.out.println("\n Exit with code " + eCode + "\n");
		    	if (eCode < 0)
		    		System.exit(eCode);
		    }
	   	}
	 }
