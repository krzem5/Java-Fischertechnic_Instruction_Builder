package com.krzem.fischertechnic_instruction_builder;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class Editor extends Constants{
	public Main.Main_ cls;
	public List<Block> bl;
	public Menu mn;
	private boolean _md=false;
	private Texture _tx=null;
	private BufferedImage _img=null;



	public Editor(Main.Main_ cls){
		this.cls=cls;
		this.mn=new Menu(this.cls,this);
		this.bl=new ArrayList<Block>();
		this.bl.add(Block.create("buildingblock15",cls,this,new double[]{0,0,0},new double[]{0,Math.PI/2,0},null));
		this.bl.add(Block.create("buildingblock15",cls,this,new double[]{20,0,0},new double[]{0,0,0},null));
	}



	public void update(GL2 gl){
		if (this.mn.update_mouse()==true){
			if (this.cls.MOUSE==1&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_COUNT==1&&this.cls.cam.mp!=null&&this._md==false){
				this._md=true;
				double md=Double.MAX_VALUE;
				InputConnector sic=null;
				for (Block b:this.bl){
					for (InputConnector ic:b.input_connectors){
						if (ic.h_oc!=null){
							double d=ic.calc_hover(gl);
							if (d!=-1&&d<md){
								md=d;
								sic=ic;
							}
						}
					}
				}
				if (sic!=null){
					sic.select();
					sic.h_oc.sel=false;
					for (Block b:this.bl){
						for (InputConnector ic:b.input_connectors){
							ic.h_oc=null;
						}
					}
				}
				else{
					md=Double.MAX_VALUE;
					Block sb=null;
					for (Block b:this.bl){
						double d=b.calc_hover(gl);
						if (d!=-1&&d<md){
							md=d;
							sb=b;
						}
					}
					if (sb!=null&&sb.sel==false){
						for (Block b:this.bl){
							b.sel=false;
							for (OutputConnector oc:b.output_connectors){
								oc.sel=false;
							}
							for (InputConnector ic:b.input_connectors){
								ic.h_oc=null;
							}
						}
						sb.sel=true;
					}
					else if (sb!=null){
						for (Block b:this.bl){
							if (b.sel==false){
								for (Block ib:this.bl){
									for (InputConnector ic:b.input_connectors){
										ic.h_oc=null;
									}
								}
								continue;
							}
							md=Double.MAX_VALUE;
							OutputConnector ocs=null;
							for (OutputConnector oc:b.output_connectors){
								oc.sel=false;
								double d=oc.calc_hover(gl);
								if (d!=-1&&d<md){
									md=d;
									ocs=oc;
								}
							}
							if (ocs!=null){
								ocs.sel=true;
							}
						}
					}
					else{
						for (Block b:this.bl){
							b.sel=false;
							for (OutputConnector oc:b.output_connectors){
								oc.sel=false;
							}
							for (InputConnector ic:b.input_connectors){
								ic.h_oc=null;
							}
						}
					}
				}
			}
			else if (this.cls.MOUSE==0){
				this._md=false;
			}
		}
		for (Block b:this.bl){
			b._update(gl);
		}
		this.mn.update();
	}



	public void draw(GL2 gl){
		if (this._img==null){
			this._img=new BufferedImage(WINDOW_SIZE.width,WINDOW_SIZE.height,BufferedImage.TRANSLUCENT);
		}
		Graphics2D g=(Graphics2D)this._img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		double[] min=new double[2];
		double[] max=new double[2];
		for (Block b:this.bl){
			b._draw(gl);
			b._min_max(min,max);
		}
		min[0]=(Math.ceil(min[0]/GRID_SQ_SIZE)-GRID_BUFF_SIZE)*GRID_SQ_SIZE;
		min[1]=(Math.ceil(min[1]/GRID_SQ_SIZE)-GRID_BUFF_SIZE)*GRID_SQ_SIZE;
		max[0]=(Math.ceil(max[0]/GRID_SQ_SIZE)+GRID_BUFF_SIZE)*GRID_SQ_SIZE;
		max[1]=(Math.ceil(max[1]/GRID_SQ_SIZE)+GRID_BUFF_SIZE)*GRID_SQ_SIZE;
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(GRID_COLOR[0],GRID_COLOR[1],GRID_COLOR[2]);
		for (double i=min[0];i<=max[0];i+=GRID_SQ_SIZE){
			gl.glVertex3d(i,0,min[1]);
			gl.glVertex3d(i,0,max[1]);
		}
		for (double i=min[1];i<=max[1];i+=GRID_SQ_SIZE){
			gl.glVertex3d(min[0],0,i);
			gl.glVertex3d(max[0],0,i);
		}
		gl.glColor3d(GRID_MAIN_COLOR[0],GRID_MAIN_COLOR[1],GRID_MAIN_COLOR[2]);
		gl.glVertex3d(min[0],0,0);
		gl.glVertex3d(max[0],0,0);
		gl.glVertex3d(0,0,min[1]);
		gl.glVertex3d(0,0,max[1]);
		gl.glEnd();
		this.mn.draw(g);
		g.dispose();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		this.cls.glu.gluOrtho2D(0,WINDOW_SIZE.width,0,WINDOW_SIZE.height);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
		if (this._tx==null){
			this._tx=AWTTextureIO.newTexture(gl.getGLProfile(),this._img,false);
		}
		else{
			this._tx.updateImage(gl,AWTTextureIO.newTextureData(gl.getGLProfile(),this._img,false));
		}
		this._tx.enable(gl);
		this._tx.bind(gl);
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4d(1,1,1,1);
		gl.glTexCoord2d(0,1);
		gl.glVertex2d(0,0);
		gl.glTexCoord2d(1,1);
		gl.glVertex2d(WINDOW_SIZE.width,0);
		gl.glTexCoord2d(1,0);
		gl.glVertex2d(WINDOW_SIZE.width,WINDOW_SIZE.height);
		gl.glTexCoord2d(0,0);
		gl.glVertex2d(0,WINDOW_SIZE.height);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}
}
