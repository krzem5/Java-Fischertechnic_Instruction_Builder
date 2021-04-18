package com.krzem.fischertechnic_instruction_builder;



import com.jogamp.opengl.GL2;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.HashMap;



public abstract class OutputConnector extends ClickObject{
	public Editor e;
	public Block b;
	public double[] _off;
	public double[] off;
	public double[] rot;
	public double[] pos;
	public double[] norm;
	private static Map<String,Constructor> _occl=null;



	public abstract String get_name();



	public abstract String[] get_sockets();



	public abstract double get_width();



	public abstract void init();



	public abstract void update();



	public abstract void draw(GL2 gl);



	public final void draw_model(GL2 gl,double[] off,double[] rot){
		if (this.sel==false){
			return;
		}
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(BLOCK_OUTPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[0],BLOCK_OUTPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[1],BLOCK_OUTPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[2]);
		for (double[] l:this._rot(MDLFileLoader.get_ln(this.get_name()),this.rot,this.b.rot)){
			gl.glVertex3d(l[0]+off[0],l[1]+off[1],l[2]+off[2]);
		}
		gl.glEnd();
	}



	public final double calc_hover(GL2 gl){
		return super.calc_hover(gl,this.pos);
	}



	@Override
	public final double[][] get_triangles(){
		return this._rot(MDLFileLoader.get_tr(this.get_name()),this.rot,this.b.rot);
	}



	@Override
	public final double[] get_bounding_sphere(){
		return MDLFileLoader.get_bs(this.get_name());
	}



	public final void _update(GL2 gl){
		this.off=this._rot(new double[][]{this._off},this.b.rot)[0];
		this.pos=new double[]{this.off[0]+this.b.pos[0],this.off[1]+this.b.pos[1],this.off[2]+this.b.pos[2]};
		this.update();
		if (this.sel==true){
			for (Block b:this.e.bl){
				if (b!=this.b){
					for (InputConnector ic:b.input_connectors){
						if (ic._can_connect(this)){
							ic.h_oc=this;
						}
					}
				}
			}
		}
	}



	public final void _draw(GL2 gl){
		this.draw(gl);
	}



	private void _constructor(Main.Main_ cls,Editor e,Block b,double[] off,double[] rot,double[] norm){
		this.cls=cls;
		this.e=e;
		this.b=b;
		this._off=off;
		this.rot=rot;
		this.norm=norm;
		this.init();
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



	public static final OutputConnector create(String nm,Main.Main_ cls,Editor e,Block b,double[] off,double[] rot,double[] norm){
		try{
			if (OutputConnector._occl==null){
				try{
					OutputConnector._occl=new HashMap<String,Constructor>();
					OutputConnector._occl.put("pin",com.krzem.fischertechnic_instruction_builder.connector.PinOutputConnector.class.getConstructor());
				}
				catch (NoSuchMethodException ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			Object _oc=OutputConnector._occl.get(nm.toLowerCase()).newInstance();
			OutputConnector oc=(OutputConnector)_oc;
			oc._constructor(cls,e,b,off,rot,norm);
			return oc;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
