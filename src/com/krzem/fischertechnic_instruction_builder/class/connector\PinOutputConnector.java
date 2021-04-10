import com.jogamp.opengl.GL2;
import com.krzem.fischertechnic_instruction_builder.OutputConnector;



public class PinOutputConnector extends OutputConnector{
	public String get_name(){
		return "pin_output_connector";
	}



	public String[] get_sockets(){
		return new String[]{"pin","round"};
	}



	public double get_width(){
		return 4;
	}



	public void init(){

	}



	public void update(){

	}



	public void draw(GL2 gl){
		this.draw_model(gl,this.pos,this.rot);
	}
}