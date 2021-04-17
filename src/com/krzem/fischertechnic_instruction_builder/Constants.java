package com.krzem.fischertechnic_instruction_builder;



import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();

	public static final double EPSILON=Math.ulp(1d);

	public static final String MODEL_DIR="/rsrc/models/";
	public static final String BLOCK_CLASS_DIR="./com/krzem/fischertechnic_instruction_builder/class/block/";
	public static final String OUTPUT_CONNECTOR_CLASS_DIR="./com/krzem/fischertechnic_instruction_builder/class/connector/";
	public static final String INPUT_CONNECTOR_CLASS_DIR="./com/krzem/fischertechnic_instruction_builder/class/connector/";

	public static final int CROSSHAIR_SIZE=40;

	public static final double CAMERA_MOVE_SPEED=0.5d;
	public static final double CAMERA_ROT_SPEED=0.075d;
	public static final double CAMERA_MIN_EASE_DIFF=0.05d;
	public static final double CAMERA_EASE_PROC=0.45d;
	public static final double CAMERA_CAM_NEAR=0.05d;
	public static final double CAMERA_CAM_FAR=1000d;

	public static final double[] GRID_COLOR=new double[]{116/255d,116/255d,116/255d};
	public static final double[] GRID_MAIN_COLOR=new double[]{11/255d,11/255d,11/255d};
	public static final double GRID_SQ_SIZE=15;
	public static final double GRID_BUFF_SIZE=5;

	public static final Map<String,double[]> BLOCK_COLORS=new HashMap<String,double[]>(){{
		this.put("black",new double[]{58/255d,58/255d,58/255d});
		this.put("yellow",new double[]{250/255d,227/255d,12/255d});
		this.put("red",new double[]{237/255d,80/255d,36/255d});
		this.put("blue",new double[]{0/255d,140/255d,209/255d});
		this.put("green",new double[]{37/255d,178/255d,75/255d});
		this.put("gray",new double[]{191/255d,191/255d,191/255d});
		this.put("white",new double[]{253/255d,253/255d,253/255d});
	}};
	public static final double[] BLOCK_OUTLINE_COLOR=new double[]{0,0,0};
	public static final double[] BLOCK_SELECTED_OUTLINE_COLOR=new double[]{0,0.5,1};
	public static final double[] BLOCK_OUTPUT_CONNECTOR_SELECTED_OUTLINE_COLOR=new double[]{128/255f,241/255f,52/255f};
	public static final double[] BLOCK_INPUT_CONNECTOR_SELECTED_OUTLINE_COLOR=new double[]{243/255f,186/255f,53/255f};

	public static final Font MENU_FONT=new Font("Consolas",Font.PLAIN,50);
	public static final int MENU_WINDOW_SIZE_BUFFOR=100;
	public static final Color MENU_BACKGROUND_COLOR=new Color(235,235,235);
	public static final int MENU_WINDOW_BORDER_RADIUS=30;
	public static final int MENU_WINDOW_BORDER_SIZE=10;
	public static final Color MENU_BORDER_COLOR=new Color(15,140,215);
	public static final Color MENU_OVERLAY_COLOR=new Color(0,0,0,115);
	public static final int MENU_OFFSET_SELECT_SIZE_MULT=100;
	public static final Color MENU_OFFSET_SELECT_LINE_COLOR=new Color(243,186,53);
	public static final int MENU_OFFSET_SELECT_LINE_WEIGHT=20;
}
