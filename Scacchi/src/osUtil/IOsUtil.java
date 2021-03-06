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
	public String memoryDesc(){
		return "Memoria usata jvm  ( " + javaVersionDesc() + " )   "+ memory()+"  KB ";
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
		try {
		   file = new File(path);
		   if(!file.exists()) {
			   printOutError( " file " +path +" non trovato ",1,true);
			   } else {
			   BufferedReader br = new BufferedReader( new FileReader(path));
			   String strLine = "";
			   while( (strLine = br.readLine()) != null)   list.add(  splitToken(strLine,separator));
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
		   Image img=null;
		   try {
			   File file = new File(path);
			   if(!file.exists())  printOutError( " file " +path +" non trovato ",1,true);
			   else   img = ImageIO.read(file);
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-1);
		   }
		   return img;
	   	}   
	 
	 
       @Override
       public void testJVM(){
    	   int[] e=javaVersion();
    	   if(  e[0] < Default.JVM_Minimum_Value[0] ||( e[0] == Default.JVM_Minimum_Value[0] && e[1] < Default.JVM_Minimum_Value[1] ))
    		   printOutError("This is java version " + e[0] +"."+ e[1]
    				   +" but is required java version " + Default.JVM_Minimum_Value[0] +"."+ Default.JVM_Minimum_Value[1],-1,true) ;
       }
     	@Override
        public String javaVersionDesc(){
          return System.getProperty("java.version");
     	}
	   	@Override
       public int[] javaVersion(){
	   		int[] e=null;
	   		try{
	   		String[] jvmv =splitToken(System.getProperty("java.version").substring(0, System.getProperty("java.version").indexOf("_")),"."); 
            e= new int[jvmv.length];
            for(int n=0;n< e.length;n++)
        	  e[n]= Integer.parseInt(jvmv[n]);
	   		}catch (Exception ex){
	   			printOutError("Problem in control java version " + System.getProperty("java.version"),1,true,ex);
	   		}
           return e;
	   	}
	    
	   	private String[] splitToken(String s,String separator){
	   		String[] sa=null;
	   		try {
	   				ArrayList<String> listToken = new ArrayList<String>();
	   				StringTokenizer st = new StringTokenizer(s,separator);
	   				while(st.hasMoreTokens())  listToken.add(st.nextToken());
	   				sa=new String[listToken.size()];
	   				for (int n =0;n< sa.length;n++)
	   					sa[n]=(String) listToken.get(n);
	   		}catch (Exception ex){
	  			printOutError("Problem in string conversion " ,1,true,ex);
	  		}
	 		  return sa;
	 	  }
	   
	 
	 		/**
		    * @since stampa (se previsto da default o in caso di uscita dal programma o sempre ) in sysout 
		    * @since messaggio con percorso con classi metodi e linee codice  
		    */
		   
		@Override
	   	public void printOutError(String s,int eCode,boolean optionalPrint,Exception e){
	 		System.out.println("\n" + Default.title +"\n\n"+ e.toString());
	 		printOutError( s, eCode, false);
	 	}
	   	
	   	@Override
		   	public void printOutError(String s,int eCode,boolean optionalPrint){
			    if ((optionalPrint == Default.printSysOut) || eCode < 0){
			    	System.out.println("\n ["+Default.title+"]\n ERROR  ["+ s +" ] \n Stack\n");
			    	StackTraceElement[] se=new Exception().getStackTrace();
			    	for (int n=0;n< se.length;n++)
			    		System.out.println(" Class ["+se[n].getClassName()+"] Method [" +se[n].getMethodName() + "]  Line " + se[n].getLineNumber());
			    	System.out.println("\n Exit with code " + eCode + "\n");
			    	if (eCode < 0)
			    		System.exit(eCode);
			    }
		   	}
	   	/**
	   	 * @since calcola md5 per una stringa
	   	 */
	   	@Override
	   	public String MD5(String md5) {
	   	   try {
	   	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	   	        byte[] array = md.digest(md5.getBytes());
	   	        StringBuffer sb = new StringBuffer();
	   	        for (int i = 0; i < array.length; ++i) {
	   	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	   	       }
	   	        return sb.toString();
	   	    } catch (java.security.NoSuchAlgorithmException e) {
	   	    	printOutError("Errore interno md5" ,-1,true,e);
	   	    }
	   	    return null;
	   	}

}
