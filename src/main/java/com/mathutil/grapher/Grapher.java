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
	
	/**For different functions on the same graph, show them in different colors or not, default is true. The colors are {@link #function_colors}, 
	 * If this set to be false, all functions will be shown in the first color in {@link #function_colors}, which is Color.BLUE**/
	public static boolean show_different_color = true;
	
	/**The colors of the functions, default is all colors in {@link Color} class expect for {@link Color#BLACK} and {@link Color#WHITE}. 
	 * If the functions are more than the colors, the color will be set back to the first and keep repeating. 
	 * The colors can be customized by setting to new values**/
	public static Color[] function_colors = new Color[]{
			Color.BLUE , Color.CYAN , Color.DARK_GRAY , Color.GRAY , Color.GREEN, Color.LIGHT_GRAY, 
			Color.MAGENTA , Color.ORANGE , Color.PINK , Color.RED , Color.YELLOW
	};
	
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
	public static int points_between = 10;
	
	
	//Static class, no instance
	private Grapher(){}
	
	/**
	 * Draw the function on the graph, see {@link ExpReader#calculate(String)} to check out the available operations.<br>
	 * 
	 * @param exp - The expressions of the functions, there can be multiple functions or no functions
	 */
	public static void plot(String... exp){
		new GraphWindow("Grapher" , new GraphPane(graph_pane_width , graph_pane_height , exp));
	}
}
