package com.krzem.fischertechnic_instruction_builder;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;



public class Menu extends Constants{
	public Main.Main_ cls;
	public Editor e;
	private InputConnector _off_w_ic=null;
	private OutputConnector _off_w_oc=null;
	private boolean _ep=false;



	public Menu(Main.Main_ cls,Editor e){
		this.cls=cls;
		this.e=e;
	}



	public void show_offset_select(InputConnector ic){
		this._off_w_ic=ic;
		this._off_w_oc=ic.h_oc;
	}



	public boolean update_mouse(){
		if (this._off_w_ic!=null){
			return false;
		}
		return true;
	}



	public void update(){
		if (this.cls.KEYBOARD.pressed(27)&&this._ep==false){
			this._ep=true;
			if (this._off_w_ic!=null){
				this._off_w_ic=null;
				this._off_w_oc=null;
			}
			else{
				this.cls._quit();
			}
		}
		else if (this.cls.KEYBOARD.pressed(27)==false){
			this._ep=false;
		}
	}



	public void draw(Graphics2D g){
		g.setBackground(new Color(255,255,255,0));
		g.clearRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		if (this.cls.cam.mp==null){
			this.cls.canvas.setCursor(this.cls.canvas.getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(),null));
			return;
		}
		else{
			g.setFont(MENU_FONT);
			if (this._off_w_ic!=null){
				this.cls.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				g.setColor(MENU_OVERLAY_COLOR);
				g.fillRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
				int w=this._off_w_ic.get_width()*MENU_OFFSET_SELECT_SIZE_MULT+MENU_WINDOW_SIZE_BUFFOR*2;
				int h=MENU_OFFSET_SELECT_SIZE_MULT*2+MENU_WINDOW_SIZE_BUFFOR*2;
				this._draw_box(g,WINDOW_SIZE.width/2-w/2,WINDOW_SIZE.height/2-h/2,w,h);
				g.setColor(MENU_OFFSET_SELECT_LINE_COLOR);
				if (this._collision_line_circle(WINDOW_SIZE.width/2-w/2+MENU_WINDOW_SIZE_BUFFOR,WINDOW_SIZE.height/2,WINDOW_SIZE.width/2+w/2-MENU_WINDOW_SIZE_BUFFOR,WINDOW_SIZE.height/2,this.cls.cam.mp[0],this.cls.cam.mp[1],MENU_OFFSET_SELECT_LINE_WEIGHT/2)!=null){
					g.setColor(new Color(255,255,0));
				}
				g.setStroke(new BasicStroke(MENU_OFFSET_SELECT_LINE_WEIGHT,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				g.drawLine(WINDOW_SIZE.width/2-w/2+MENU_WINDOW_SIZE_BUFFOR,WINDOW_SIZE.height/2,WINDOW_SIZE.width/2+w/2-MENU_WINDOW_SIZE_BUFFOR,WINDOW_SIZE.height/2);
				FontMetrics fm=g.getFontMetrics();
				g.drawString("0",WINDOW_SIZE.width/2-w/2+MENU_WINDOW_SIZE_BUFFOR/2-fm.stringWidth("0")/2,WINDOW_SIZE.height/2-(fm.getAscent()+fm.getDescent())/2+fm.getAscent());
				g.drawString(Integer.toString(this._off_w_ic.get_width()),WINDOW_SIZE.width/2+w/2-MENU_WINDOW_SIZE_BUFFOR/2-fm.stringWidth("0")/2,WINDOW_SIZE.height/2-(fm.getAscent()+fm.getDescent())/2+fm.getAscent());
			}
			else{
				this.cls.canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
	}



	private void _draw_box(Graphics2D g,int x,int y,int w,int h){
		g.setColor(MENU_BORDER_COLOR);
		g.fillRoundRect(x-MENU_WINDOW_BORDER_SIZE,y-MENU_WINDOW_BORDER_SIZE,w+MENU_WINDOW_BORDER_SIZE*2,h+MENU_WINDOW_BORDER_SIZE*2,MENU_WINDOW_BORDER_RADIUS,MENU_WINDOW_BORDER_RADIUS);
		g.setColor(MENU_BACKGROUND_COLOR);
		g.fillRoundRect(x,y,w,h,MENU_WINDOW_BORDER_RADIUS,MENU_WINDOW_BORDER_RADIUS);
	}



	private double[] _collision_line_circle(double sx,double sy,double ex,double ey,double cx,double cy,double cr){
		if (this._collision_point_circle(cx,cy,cr,sx,sy)==true){
			return new double[]{sx,sy};
		}
		if (this._collision_point_circle(cx,cy,cr,ex,ey)==true){
			return new double[]{ex,ey};
		}
		double t=(((cx-sx)*(ex-sx))+((cy-sy)*(ey-sy)))/((sx-ex)*(sx-ex)+(sy-ey)*(sy-ey));
		double lx=sx+t*(ex-sx);
		double ly=sy+t*(ey-sy);
		if (this._collision_point_line(sx,sy,ex,ey,lx,ly)==false||this._collision_point_circle(cx,cy,cr,lx,ly)==false){
			return null;
		}
		return new double[]{lx,ly};
	}



	private boolean _collision_point_circle(double cx,double cy,double cr,double px,double py){
		return ((cx-px)*(cx-px)+(cy-py)*(cy-py)<=cr*cr);
	}



	private boolean _collision_point_line(double sx,double sy,double ex,double ey,double px,double py){
		return ((sx-px)*(sx-px)+(sy-py)*(sy-py)+(ex-px)*(ex-px)+(ey-py)*(ey-py)-(sx-ex)*(sx-ex)-(sy-ey)*(sy-ey)<=EPSILON);
	}
}