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
	public String fileSeparator()
	{
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
     * @return
     */
	  @Override
	   public void loadCsv(ArrayList<String[]> list){
		File f =null;
		ArrayList<String> listToken=new ArrayList<String>();
		try {
		   String p=defaultAppDir(true)+"Demo"+Default.csvType;
		   f = new File(p);
		   if(!f.exists()) {
			   System.out.println(p + " non trovato \n");
		   }
	       BufferedReader br = new BufferedReader( new FileReader(p));
	       String strLine = "";
	       StringTokenizer st = null;
	       while( (strLine = br.readLine()) != null)
	       {
	           st = new StringTokenizer(strLine, Default.csvSep);
	           listToken.clear();
	           while(st.hasMoreTokens())
	           {
	        	   listToken.add(st.nextToken());
	           }
	          list.add( listToken.toArray(new String[listToken.size()]));
	       }
		  br.close();
		} catch (Exception ex) {
		   ex.printStackTrace();
		   System.exit(-1);
	}
	}
	/**
       * @since aggiunge demo  
       * @param append record csv formato nome ,selezione ,mossa,...
       */
	  @Override
	   public 
	 void appendFile(String path ,String append)
	{
	    try
	    {
	        File file = new File(path);
	        FileWriter fileWriter = new FileWriter(file,true);
	        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
	        fileWriter.append(append);
	        bufferFileWriter.close();
	    
	    }catch(Exception ex)
	    {
	        System.out.println(ex);
	    }
	}
		/**
	    * @since  ritorna image da file
	    */
	   @Override
	   public  Image ldImage(String name){
		   File f =null;
		   Image img=null;
		   try {
			   String p=name;
			   f = new File(p);
			   if(!f.exists()) 
				   System.out.println(p + " non trovato \n");
			   img = ImageIO.read(f);
		   } catch (Exception ex) {
			   ex.printStackTrace();
			   System.exit(-1);
		   }
		   return img;
	   }   

}
