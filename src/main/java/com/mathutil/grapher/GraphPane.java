package com.mathutil.grapher;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import com.mathutil.operations.ExpReader;

/**
 * The JPanel that contains the function graph
 * @author danielxu
 *
 */
class GraphPane extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private String exp;
	
	public GraphPane(int width , int height , String exp){
		this.setSize(width , height);
		this.exp = exp;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		plot(g);
	}
	
	//Plot the function
	private void plot(Graphics g){
		Graphics2D g2d = (Graphics2D)g; //Cast to g2d
		
		//Graph info
		int width = Grapher.graph_pane_width;
		int height = Grapher.graph_pane_height;
		int x_max = Grapher.graph_x_max;
		int x_min = Grapher.graph_x_min;
		int y_max = Grapher.graph_y_max;
		int y_min = Grapher.graph_y_min;
		
		//How many piexls between two points in both x and y
		int x_scale = width / (x_max - x_min);
		int y_scale = height / (y_max - y_min);
		
		//Origin
		int x_zero = 0 , y_zero = 0;
		//No x_axis base on max-min settings
		if(x_min > 0 || x_max < 0){
			x_zero = -10; //x-axis not showing
		}
		//No y-axis base on max-min settings
		if(y_min > 0 || y_max < 0){
			y_zero = -10; //y-axis not showing
		}
		//Find the origin
		else{
			for(int i=x_min; i<x_max; i++){
				x_zero ++;
				if(i == 0)
					break;
			}
			for(int i=y_min; i<y_max; i++){
				y_zero ++;
				if(i == 0)
					break;
			}
			x_zero -= 1;
			y_zero -= 1;
		}
		
		//Draw grids if necessary
		if(Grapher.show_grid){
			for(int i=0; i<=x_max-x_min; i++){
				g2d.drawLine(i*x_scale, 0, i*x_scale, height);
			}
			for(int i=0; i<=y_max-y_min; i++){
				g2d.drawLine(0, i*y_scale, width, i*y_scale);
			}
		}
		
		//Draw axises
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(x_zero*x_scale, 0, x_zero*x_scale, height);
		g2d.drawLine(0, y_zero*y_scale, width, y_zero*y_scale);
		
		//Draw origin
		g2d.setColor(Color.RED);
		g2d.fillRect(x_zero*x_scale, y_zero*y_scale, 3, 3);
		
		//Draw points on axises
		g2d.setFont(new Font("Arial" , Font.PLAIN , 10));
		for(int i=x_min; i<=x_max; i++){
			g2d.fillRect(i*x_scale-x_min*x_scale-1, y_zero*y_scale-1, 2, 2);
			if(Grapher.show_number){
				g2d.drawString(""+i, i*x_scale-x_min*x_scale-5, y_zero*y_scale+10);
			}
		}
		for(int i=y_min; i<=y_max; i++){
			g2d.fillRect(x_zero*x_scale, i*y_scale-y_min*y_scale-1, 2, 2);
			if(Grapher.show_number){
				if(i != 0){
					g2d.drawString(""+i, x_zero*x_scale+5, i*y_scale-y_min*y_scale+5);
				}
			}
		}
		
		//For every two values, choose few points
		double[] px = new double[(x_max - x_min)*Grapher.points_between];
		double[] py = new double[(x_max - x_min)*Grapher.points_between];
		int index = 0;
		double sep = 1.0/Grapher.points_between;
		
		for(double i=x_min; i<=x_max; i+=sep){
			if(index >= px.length)
				break;
			px[index] = i*x_scale;
			py[index] = -ExpReader.calculate(exp.replace("x" , "("+String.valueOf(i)+")"))*y_scale;
			index++;
		}
		
		g2d.setColor(Grapher.function_color);
		//Draw the function
		Path2D path = new Path2D.Double();
		for(int i=0;i<py.length;i++){
			if(i == 0)
				path.moveTo(px[i], py[i]);
			path.lineTo(px[i], py[i]);
		}
		g2d.translate(x_zero*x_scale, y_zero*y_scale); //translate the origin
		g2d.draw(path);
	}
	
}
