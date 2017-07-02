package com.mathutil.grapher;

import java.awt.Color;

import com.mathutil.operations.ExpReader;

/**
 * The Grapher class can draw the function on the graph. There are several configurations that can be modified such as <code>graph_pane_width</code> and 
 * <code>graph_pane_height</code>.
 * 
 * @author danielxu
 *
 */
public class Grapher {

	/**Show grids on the function graph or not, default is true**/
	public static boolean show_grid = true;
	
	/**Show numbers on the axises or not, default is false. Please note that enable this option may lead to the initialization slow down(Bug?).**/
	public static boolean show_number = false;
	
	/**The width of the graph panel, default is 600**/
	public static int graph_pane_width = 600;
	
	/**The height of the graph panel, default is 600**/
	public static int graph_pane_height = 600;
	
	/**The maximum x value on the function graph, default is 10. Please note that this value must greater than graph_x_min**/
	public static int graph_x_max = 10;
	
	/**The minimum x value on the function graph, default is -10. Please note that this value must smaller than graph_x_max**/
	public static int graph_x_min = -10;
	
	/**The maximum y value on the function graph, default is 10. Please note that this value must greater than graph_y_min**/
	public static int graph_y_max = 10;
	
	/**The minimum y value on the function graph, default is -10. Please note that this value must smaller than graph_y_max**/
	public static int graph_y_min = -10;
	
	/**How many points will be calculated between two points, default is 5. Greater number will cost the initialization slower but produce smoother graph**/
	public static int points_between = 5;
	
	/**The color of the function, default is blue**/
	public static Color function_color = Color.BLUE;
	
	//Static class, no instance
	private Grapher(String exp){}
	
	/**
	 * Draw the function on the graph, see {@link ExpReader#calculate(String)} to check out the available operations.<br>
	 * 
	 * @param exp - The expression of the function
	 */
	public static void plot(String exp){
		new GraphWindow("Grapher" , new GraphPane(graph_pane_width , graph_pane_height , exp));
	}
}
