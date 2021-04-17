package com.krzem.fischertechnic_instruction_builder;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class MDLFileLoader extends Constants{
	private static Map<String,double[][]> _tr_data=new HashMap<String,double[][]>();
	private static Map<String,double[][]> _ln_data=new HashMap<String,double[][]>();
	private static Map<String,double[]> _bs_data=new HashMap<String,double[]>();



	public static double[][] get_tr(String nm){
		if (MDLFileLoader._tr_data.get(nm)==null){
			MDLFileLoader._load(nm+".mdl");
		}
		return MDLFileLoader._tr_data.get(nm);
	}



	public static double[][] get_ln(String nm){
		if (MDLFileLoader._ln_data.get(nm)==null){
			MDLFileLoader._load(nm+".mdl");
		}
		return MDLFileLoader._ln_data.get(nm);
	}



	public static double[] get_bs(String nm){
		if (MDLFileLoader._bs_data.get(nm)==null){
			MDLFileLoader._load(nm+".mdl");
		}
		return MDLFileLoader._bs_data.get(nm);
	}



	private static void _load(String fn){
		try{
			InputStream is=MDLFileLoader.class.getResourceAsStream(MODEL_DIR+fn);
			BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String dt=br.readLine();
			br.close();
			double ax=0;
			double ay=0;
			double az=0;
			ArrayList<double[]> tl=new ArrayList<double[]>();
			ArrayList<double[]> ll=new ArrayList<double[]>();
			for (String s:dt.split(";")){
				String[] ss=s.split(":");
				if (ss[0].equals("2")){
					ll.add(MDLFileLoader._parse_vector(ss[1]));
					ll.add(MDLFileLoader._parse_vector(ss[2]));
				}
				else if (ss[0].equals("3")){
					tl.add(MDLFileLoader._parse_vector(ss[1]));
					tl.add(MDLFileLoader._parse_vector(ss[2]));
					tl.add(MDLFileLoader._parse_vector(ss[3]));
					ax+=tl.get(tl.size()-1)[0]+tl.get(tl.size()-2)[0]+tl.get(tl.size()-3)[0];
					ay+=tl.get(tl.size()-1)[1]+tl.get(tl.size()-2)[1]+tl.get(tl.size()-3)[1];
					az+=tl.get(tl.size()-1)[2]+tl.get(tl.size()-2)[2]+tl.get(tl.size()-3)[2];
				}
			}
			ax/=tl.size()*3;
			ay/=tl.size()*3;
			az/=tl.size()*3;
			double md=0;
			for (double[] v:tl){
				double d=((ax-v[0])*(ax-v[0])+(ay-v[1])*(ay-v[1])+(az-v[2])*(az-v[2]));
				if (d>md){
					md=d;
				}
			}
			MDLFileLoader._tr_data.put(fn.substring(fn.replace("/","\\").lastIndexOf("\\")+1,fn.length()-4),tl.toArray(new double[tl.size()][3]));
			MDLFileLoader._ln_data.put(fn.substring(fn.replace("/","\\").lastIndexOf("\\")+1,fn.length()-4),ll.toArray(new double[ll.size()][3]));
			MDLFileLoader._bs_data.put(fn.substring(fn.replace("/","\\").lastIndexOf("\\")+1,fn.length()-4),new double[]{ax,ay,az,Math.sqrt(md)});
			is.close();
			br.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	private static double[] _parse_vector(String vs){
		return new double[]{Double.parseDouble(vs.split(",")[0]),Double.parseDouble(vs.split(",")[1]),Double.parseDouble(vs.split(",")[2])};
	}

}
