package com.krzem.fischertechnic_instruction_builder;



import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;



public abstract class ClickObject extends Constants{
	public Main.Main_ cls;
	public boolean sel=false;



	public abstract double[][] get_triangles();



	public abstract double[] get_bounding_sphere();



	public double calc_hover(GL2 gl,double[] off){
		double[][] tl=this.get_triangles();
		for (int i=0;i<tl.length;i+=3){
			double[][] pt=new double[][]{this._project_2d(gl,new double[]{tl[i][0]+off[0],tl[i][1]+off[1],tl[i][2]+off[2]}),this._project_2d(gl,new double[]{tl[i+1][0]+off[0],tl[i+1][1]+off[1],tl[i+1][2]+off[2]}),this._project_2d(gl,new double[]{tl[i+2][0]+off[0],tl[i+2][1]+off[1],tl[i+2][2]+off[2]})};
			if (!(pt[0][2]>1&&pt[1][2]>1&&pt[2][2]>1)&&this._collision_tri_point(pt,this.cls.cam.mp)==true){
				return this._calc_distance(this.get_bounding_sphere(),off);
			}
		}
		return -1;
	}



	private double[] _project_2d(GL2 gl,double[] p){
		float[] pp=new float[3];
		float[] mm=new float[16];
		float[] pm=new float[16];
		int[] vp=new int[]{0,0,WINDOW_SIZE.width,WINDOW_SIZE.height};
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX,mm,0);
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX,pm,0);
		this.cls.glu.gluProject((float)p[0],(float)p[1],(float)p[2],mm,0,pm,0,vp,0,pp,0);
		return new double[]{pp[0],pp[1],pp[2]};
	}



	private boolean _collision_tri_point(double[][] tr,double[] p){
		return (Math.abs((tr[0][0]-p[0])*(tr[1][1]-p[1])-(tr[1][0]-p[0])*(tr[0][1]-p[1]))+Math.abs((tr[1][0]-p[0])*(tr[2][1]-p[1])-(tr[2][0]-p[0])*(tr[1][1]-p[1]))+Math.abs((tr[2][0]-p[0])*(tr[0][1]-p[1])-(tr[0][0]-p[0])*(tr[2][1]-p[1]))==Math.abs((tr[1][0]-tr[0][0])*(tr[2][1]-tr[0][1])-(tr[2][0]-tr[0][0])*(tr[1][1]-tr[0][1])));
	}


	private double _calc_distance(double[] bs,double[] off){
		return ((bs[0]+off[0]-this.cls.cam.x)*(bs[0]+off[0]-this.cls.cam.x)+(bs[1]+off[1]-this.cls.cam.y)*(bs[1]+off[1]-this.cls.cam.y)+(bs[2]+off[2]-this.cls.cam.z)*(bs[2]+off[2]-this.cls.cam.z))-bs[3]*bs[3];
	}



	// private double[][] _convex_hull(GL2 gl,double[][] pl3d){
	// 	List<double[]> ol=new ArrayList<double[]>();
	// 	double[][] pl=new double[pl3d.length][2];
	// 	int min=0;
	// 	int max=0;
	// 	for (int i=0;i<pl.length;i++){
	// 		pl[i]=this._project_2d(gl,pl3d[i]);
	// 		if (pl[i][0]<pl[min][0]){
	// 			min=i+0;
	// 		}
	// 		if (pl[i][0]>pl[max][0]){
	// 			max=i+0;
	// 		}
	// 	}
	// 	this._quickhull(ol,pl,min,max,1);
	// 	this._quickhull(ol,pl,min,max,-1);
	// 	return ol.toArray(new double[ol.size()][2]);
	// }



	// private void _quickhull(List<double[]> h,double[][] pl,int min,int max,int s){
	// 	int i=-1;
	// 	double md=0;
	// 	for (int j=0;j<pl.length;j++){
	// 		double v=(pl[j][1]-pl[min][1])*(pl[max][0]-pl[min][0])-(pl[max][1]-pl[min][0])*(pl[j][0]-pl[min][0]);
	// 		if (this._sign(v)==s&&Math.abs(v)>md){
	// 			i=j+0;
	// 			md=Math.abs(v)+0;
	// 		}
	// 	}
	// 	if (i==-1){
	// 		h.add(pl[min]);
	// 		h.add(pl[max]);
	// 	}
	// 	else{
	// 		this._quickhull(h,pl,i,min,-this._sign((pl[max][1]-pl[i][1])*(pl[min][0]-pl[i][0])-(pl[min][1]-pl[i][0])*(pl[max][0]-pl[i][0])));
	// 		this._quickhull(h,pl,i,max,-this._sign((pl[min][1]-pl[i][1])*(pl[max][0]-pl[i][0])-(pl[max][1]-pl[i][0])*(pl[min][0]-pl[i][0])));
	// 	}
	// }



	// private int _sign(double v){
	// 	return (v==0?0:(v<0?-1:1));
	// }
}