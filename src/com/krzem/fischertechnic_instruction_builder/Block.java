package com.krzem.fischertechnic_instruction_builder;



import com.jogamp.opengl.GL2;
import java.lang.Exception;
import java.lang.Math;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.w3c.dom.Element;



public abstract class Block extends ClickObject{
	public Editor e;
	public double[] pos;
	public double[] rot;
	public List<OutputConnector> output_connectors;
	public List<InputConnector> input_connectors;
	private static Map<String,Constructor> _bcl=null;



	abstract public String get_name();



	abstract public String get_user_name();



	abstract public String[] get_colors();



	abstract public double[] get_size();



	abstract public void init(Element e);



	abstract public void update();



	abstract public void draw(GL2 gl);



	public final void draw_model(GL2 gl,String cnm,double[] off){
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3d(BLOCK_COLORS.get(cnm)[0],BLOCK_COLORS.get(cnm)[1],BLOCK_COLORS.get(cnm)[2]);
		for (double[] f:this._rot(MDLFileLoader.get_tr(this.get_name()),this.rot)){
			gl.glVertex3d(f[0]+off[0],f[1]+off[1],f[2]+off[2]);
		}
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		if (this.sel==false){
			gl.glColor3d(BLOCK_OUTLINE_COLOR[0],BLOCK_OUTLINE_COLOR[1],BLOCK_OUTLINE_COLOR[2]);
		}
		else{
			gl.glColor3d(BLOCK_SELECTED_OUTLINE_COLOR[0],BLOCK_SELECTED_OUTLINE_COLOR[1],BLOCK_SELECTED_OUTLINE_COLOR[2]);
		}
		for (double[] l:this._rot(MDLFileLoader.get_ln(this.get_name()),this.rot)){
			gl.glVertex3d(l[0]+off[0],l[1]+off[1],l[2]+off[2]);
		}
		gl.glEnd();
	}



	@Override
	public final double[][] get_triangles(){
		return this._rot(MDLFileLoader.get_tr(this.get_name()),this.rot);
	}



	@Override
	public final double[] get_bounding_sphere(){
		return MDLFileLoader.get_bs(this.get_name());
	}



	public final double calc_hover(GL2 gl){
		return super.calc_hover(gl,this.pos);
	}



	public final void _update(GL2 gl){
		this.update();
		for (OutputConnector oc:this.output_connectors){
			oc._update(gl);
		}
		for (InputConnector ic:this.input_connectors){
			ic._update(gl);
		}
	}



	public final void _draw(GL2 gl){
		this.draw(gl);
		for (OutputConnector oc:this.output_connectors){
			oc._draw(gl);
		}
		for (InputConnector ic:this.input_connectors){
			ic._draw(gl);
		}
	}



	public final void _min_max(double[] a,double[] b){
		a[0]=Math.min(a[0],this.pos[0]-this.get_size()[0]/2);
		a[1]=Math.min(a[1],this.pos[2]-this.get_size()[2]/2);
		b[0]=Math.max(b[0],this.pos[0]+this.get_size()[0]/2);
		b[1]=Math.max(b[1],this.pos[2]+this.get_size()[2]/2);
	}



	private void _constructor(Main.Main_ cls,Editor e,double[] pos,double[] rot,Element el){
		this.cls=cls;
		this.e=e;
		this.pos=pos;
		this.rot=rot;
		this.output_connectors=new ArrayList<OutputConnector>();
		this.input_connectors=new ArrayList<InputConnector>();
		this.init(el);
	}



	private double[][] _rot(double[][] pl,double[] ...rl){
		double[][] o=new double[pl.length][3];
		for (int i=0;i<pl.length;i++){
			o[i]=pl[i];
			for (double[] rot:rl){
				o[i]=new double[]{o[i][0]*Math.cos(rot[0])*Math.cos(rot[1])+o[i][1]*(Math.cos(rot[0])*Math.sin(rot[1])*Math.sin(rot[2])-Math.sin(rot[0])*Math.cos(rot[2]))+o[i][2]*(Math.cos(rot[0])*Math.sin(rot[1])*Math.cos(rot[2])-Math.sin(rot[0])*Math.sin(rot[2])),o[i][0]*Math.sin(rot[0])*Math.cos(rot[1])+o[i][1]*(Math.sin(rot[0])*Math.sin(rot[1])*Math.sin(rot[2])-Math.cos(rot[0])*Math.cos(rot[2]))+o[i][2]*(Math.sin(rot[0])*Math.sin(rot[1])*Math.cos(rot[2])-Math.cos(rot[0])*Math.sin(rot[2])),-o[i][0]*Math.sin(rot[1])+o[i][1]*Math.cos(rot[1])*Math.sin(rot[2])+o[i][2]*Math.cos(rot[1])*Math.cos(rot[2])};
			}
		}
		return o;
	}



	public static final Block create(String nm,Main.Main_ cls,Editor e,double[] pos,double[] rot,Element el){
		try{
			if (Block._bcl==null){
				try{
					Block._bcl=new HashMap<String,Constructor>();
					Block._bcl.put("buildingblock15",com.krzem.fischertechnic_instruction_builder.block.BuildingBlock15.class.getConstructor());
				}
				catch (NoSuchMethodException ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			Object _b=Block._bcl.get(nm.toLowerCase()).newInstance();
			Block b=(Block)_b;
			b._constructor(cls,e,pos,rot,el);
			return b;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
