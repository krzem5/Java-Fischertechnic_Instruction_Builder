package com.krzem.fischertechnic_instruction_builder;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;



public class CompileClassLoader extends ClassLoader{
	@Override
	public Class<?> findClass(String nm) throws ClassNotFoundException{
		try{
			byte[] b=this._load(nm);
			return this.defineClass(nm.substring(nm.lastIndexOf("\\")+1,nm.length()-5),b,0,b.length);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	private byte[] _load(String fn) throws FileNotFoundException,IOException,InterruptedException{
		String bp=new File("./").getAbsolutePath();
		ProcessBuilder b=new ProcessBuilder("cmd.exe","/c",String.format("cd \""+fn.substring(0,fn.lastIndexOf("\\"))+"\"&&javac -cp %s/com/krzem/fischertechnic_instruction_builder/modules/jogl-all.jar;%s/com/krzem/fischertechnic_instruction_builder/modules/jogl-all-natives-windows-amd64.jar;%s/com/krzem/fischertechnic_instruction_builder/modules/gluegen-rt.jar;%s/com/krzem/fischertechnic_instruction_builder/modules/gluegen-rt-natives-windows-amd64.jar; -sourcepath %s %s",bp,bp,bp,bp,fn.substring(0,fn.lastIndexOf("\\com\\")),fn.substring(fn.lastIndexOf("\\")+1)));
		b.inheritIO();
		Process p=b.start();
		p.waitFor();
		if (new File(fn.replace(".java",".class")).exists()==false){
			System.exit(1);
		}
		InputStream inpS=new FileInputStream(new File(fn.replace(".java",".class")));
		ByteArrayOutputStream bS=new ByteArrayOutputStream();
		int nv=0;
		try{
			while ((nv=inpS.read())!=-1){
				bS.write(nv);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return bS.toByteArray();
	}



	public Map<String,Constructor> _load_map(String dir,String rpl){
		try{
			Map<String,Constructor> bl=new HashMap<String,Constructor>();
			for (File f:new File(dir).listFiles()){
				if (f.isFile()&&f.getName().endsWith(".java")){
					Class<?> o=this.loadClass(f.getAbsolutePath());
					if (o!=null){
						bl.put(f.getName().split("\\.")[0].toLowerCase().replace(rpl,""),o.getConstructor());
					}
				}
			}
			return bl;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}