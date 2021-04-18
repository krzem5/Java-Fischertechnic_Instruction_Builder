package com.krzem.fischertechnic_instruction_builder.connector;



import com.jogamp.opengl.GL2;
import com.krzem.fischertechnic_instruction_builder.OutputConnector;



public class PinOutputConnector extends OutputConnector{
	@Override
	public String get_name(){
		return "pin_output_connector";
	}



	@Override
	public String[] get_sockets(){
		return new String[]{"pin","round"};
	}



	@Override
	public double get_width(){
		return 4;
	}



	@Override
	public void init(){

	}



	@Override
	public void update(){

	}



	@Override
	public void draw(GL2 gl){
		this.draw_model(gl,this.pos,this.rot);
	}
}
