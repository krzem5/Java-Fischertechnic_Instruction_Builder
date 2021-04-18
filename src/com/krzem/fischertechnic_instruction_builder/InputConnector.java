package com.krzem.fischertechnic_instruction_builder;



import com.jogamp.opengl.GL2;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public abstract class InputConnector extends ClickObject{
	public Editor e;
	public Block b;
	public double[] _off;
	public double[] off;
	public double[] rot;
	public double[] pos;
	public double[] norm;
	public List<OutputConnector> coc;
	public OutputConnector h_oc=null;
	private static Map<String,Constructor> _iccl=null;



	public abstract String get_name();



	public abstract String get_socket_name();



	public abstract int get_width();



	public abstract void init();



	public abstract void update();



	public abstract void select();



	public abstract boolean can_connect(OutputConnector oc);



	public abstract void draw(GL2 gl);



	public final void draw_model(GL2 gl,double[] off,double[] rot){
		if (this.h_oc==null){
			return;
		}
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(BLOCK_INPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[0],BLOCK_INPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[1],BLOCK_INPUT_CONNECTOR_SELECTED_OUTLINE_COLOR[2]);
		double[][] ll=this._rot(MDLFileLoader.get_ln(this.get_name()),rot,this.b.rot);
		for (double[] l:ll){
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
	}



	public final void _draw(GL2 gl){
		this.draw(gl);
	}



	public final boolean _can_connect(OutputConnector oc){
		for (String snm:oc.get_sockets()){
			if (snm.equals(this.get_socket_name())){
				return (this.can_connect(oc)==true);
			}
		}
		return false;
	}



	private void _constructor(Main.Main_ cls,Editor e,Block b,double[] off,double[] rot,double[] norm){
		this.cls=cls;
		this.e=e;
		this.b=b;
		this._off=off;
		this.rot=rot;
		this.norm=norm;
		this.coc=new ArrayList<OutputConnector>();
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



	public static final InputConnector create(String nm,Main.Main_ cls,Editor e,Block b,double[] off,double[] rot,double[] norm){
		try{
			if (InputConnector._iccl==null){
				try{
					InputConnector._iccl=new HashMap<String,Constructor>();
					InputConnector._iccl.put("pin11",com.krzem.fischertechnic_instruction_builder.connector.PinInputConnector11.class.getConstructor());
					InputConnector._iccl.put("pin15",com.krzem.fischertechnic_instruction_builder.connector.PinInputConnector15.class.getConstructor());
				}
				catch (NoSuchMethodException ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
			Object _ic=InputConnector._iccl.get(nm.toLowerCase()).newInstance();
			InputConnector ic=(InputConnector)_ic;
			ic._constructor(cls,e,b,off,rot,norm);
			return ic;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
