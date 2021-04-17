package com.krzem.fischertechnic_instruction_builder.block;



import com.jogamp.opengl.GL2;
import com.krzem.fischertechnic_instruction_builder.Block;
import com.krzem.fischertechnic_instruction_builder.InputConnector;
import com.krzem.fischertechnic_instruction_builder.OutputConnector;
import java.lang.Math;
import org.w3c.dom.Element;



public class BuildingBlock15 extends Block{
	public String get_name(){
		return "building_block_15";
	}



	public String get_user_name(){
		return "Building Block 15mm";
	}



	public String[] get_colors(){
		return new String[]{"black","red"};
	}



	public double[] get_size(){
		return new double[]{15,15,18};
	}



	public void init(Element e){
		this.output_connectors.add(OutputConnector.create("pin",this.cls,this.e,this,new double[]{0,0,7.5},new double[]{0,0,0},new double[]{0,0,1}));
		this.input_connectors.add(InputConnector.create("pin11",this.cls,this.e,this,new double[]{0,5.5,0.75},new double[]{Math.PI,0,0},new double[]{0,1,0}));
		this.input_connectors.add(InputConnector.create("pin11",this.cls,this.e,this,new double[]{0,-5.5,0.75},new double[]{0,0,0},new double[]{0,-1,0}));
		this.input_connectors.add(InputConnector.create("pin15",this.cls,this.e,this,new double[]{5.5,0,-1.2},new double[]{Math.PI*1.5,0,0},new double[]{1,0,0}));
		this.input_connectors.add(InputConnector.create("pin15",this.cls,this.e,this,new double[]{-5.5,0,-1.2},new double[]{Math.PI/2,0,0},new double[]{-1,0,0}));
	}



	public void update(){

	}



	public void draw(GL2 gl){
		this.draw_model(gl,"black",this.pos);
	}
}
