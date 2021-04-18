package com.krzem.fischertechnic_instruction_builder.connector;



import com.jogamp.opengl.GL2;
import com.krzem.fischertechnic_instruction_builder.InputConnector;
import com.krzem.fischertechnic_instruction_builder.OutputConnector;



public class PinInputConnector15 extends InputConnector{
	@Override
	public String get_name(){
		return "pin_input_connector_15";
	}



	@Override
	public String get_socket_name(){
		return "pin";
	}



	@Override
	public int get_width(){
		return 15;
	}



	@Override
	public void init(){

	}



	@Override
	public void update(){

	}



	@Override
	public void select(){
		this.e.mn.show_offset_select(this);
	}



	@Override
	public boolean can_connect(OutputConnector oc){
		if (this.coc.contains(oc)){
			return false;
		}
		return true;
	}



	@Override
	public void draw(GL2 gl){
		this.draw_model(gl,this.pos,this.rot);
	}
}
