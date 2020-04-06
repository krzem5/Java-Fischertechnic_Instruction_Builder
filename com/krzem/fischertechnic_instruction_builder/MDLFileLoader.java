package com.krzem.fischertechnic_instruction_builder;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
			MDLFileLoader._load(new File(MODEL_DIR+nm+".mdl").getAbsolutePath());
		}
		return MDLFileLoader._tr_data.get(nm);
	}



	public static double[][] get_ln(String nm){
		if (MDLFileLoader._ln_data.get(nm)==null){
			MDLFileLoader._load(new File(MODEL_DIR+nm+".mdl").getAbsolutePath());
		}
		return MDLFileLoader._ln_data.get(nm);
	}



	public static double[] get_bs(String nm){
		if (MDLFileLoader._bs_data.get(nm)==null){
			MDLFileLoader._load(new File(MODEL_DIR+nm+".mdl").getAbsolutePath());
		}
		return MDLFileLoader._bs_data.get(nm);
	}



	private static void _load(String fn){
		try{
			BufferedReader br=new BufferedReader(new FileReader(new File(fn)));
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
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	private static double[] _parse_vector(String vs){
		return new double[]{Double.parseDouble(vs.split(",")[0]),Double.parseDouble(vs.split(",")[1]),Double.parseDouble(vs.split(",")[2])};
	}




	// private static void _load(String fn){
	// 	try{
	// 		BufferedReader br=new BufferedReader(new FileReader(new File(fn)));
	// 		String l;
	// 		ArrayList<double[][]> ol=new ArrayList<double[][]>();
	// 		ArrayList<double[]> vl=new ArrayList<double[]>();
	// 		ArrayList<double[]> vnl=new ArrayList<double[]>();
	// 		while ((l=br.readLine())!=null){
	// 			if (l.startsWith("v ")){
	// 				String[] sl=l.split(" ");
	// 				vl.add(new double[]{Double.parseDouble(sl[1]),Double.parseDouble(sl[2]),Double.parseDouble(sl[3])});
	// 			}
	// 			else if (l.startsWith("vn ")){
	// 				String[] sl=l.split(" ");
	// 				vnl.add(new double[]{Double.parseDouble(sl[1]),Double.parseDouble(sl[2]),Double.parseDouble(sl[3])});
	// 			}
	// 		}
	// 		br.close();
	// 		br=new BufferedReader(new FileReader(new File(fn)));
	// 		while ((l=br.readLine())!=null){
	// 			if (l.startsWith("f ")){
	// 				String[] sl=l.split(" ");
	// 				ol.add(new double[][]{vl.get(Integer.parseInt(sl[1].split("/")[0])-1),vnl.get(Integer.parseInt(sl[1].split("/")[2])-1),vl.get(Integer.parseInt(sl[2].split("/")[0])-1),vnl.get(Integer.parseInt(sl[2].split("/")[2])-1),vl.get(Integer.parseInt(sl[3].split("/")[0])-1),vnl.get(Integer.parseInt(sl[3].split("/")[2])-1)});
	// 			}
	// 		}
	// 		br.close();
	// 		MDLFileLoader._data.put(fn.substring(fn.replace("/","\\").lastIndexOf("\\")+1,fn.length()-4),ol.toArray(new double[ol.size()][6][3]));
	// 	}
	// 	catch (Exception e){
	// 		e.printStackTrace();
	// 	}
	// }
}