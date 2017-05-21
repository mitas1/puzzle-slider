package Renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tile extends Thread{
        private int row,column;
        private double dimension;
        private int number;
        private GraphicsContext gc;
        private int style;

        public Tile(int row,int column,int dimension,int number){
            this.row=row;
            this.column=column;
            this.dimension=dimension;
            this.number=number;
        }

        public void setRow(int row){
            this.row=row;
        }

        public void setColumn(int column){	
        	this.column=column; 
        }
        
        public int getColumn(){
        	return this.column;
        }
        
        public int getRow(){
        	return this.row;
        }
        
        public void setStyle(int style){
        	this.style = style;
        }
        
        
        public void draw(GraphicsContext gc){
            double x = row*dimension;
            double y = column*dimension;
            
            gc.clearRect(x, y, dimension, dimension);
            if (number==0)
                gc.setFill(Color.DARKRED);
            else
                gc.setFill(Color.DARKSLATEGRAY);
            gc.fillRect(x,y,dimension,dimension);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x,y,dimension,dimension);

            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Permanent Marker", 22));
            gc.fillText(Integer.toString(number),x+dimension/2,y+dimension/2);

        }

        public void drawZeroTile(GraphicsContext gc){
            double x = row*dimension;
            double y = column*dimension;
            
            gc.clearRect(x, y, dimension, dimension);
            gc.setFill(Color.DARKRED);
            gc.fillRect(x,y,dimension,dimension);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x,y,dimension,dimension);

            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Permanent Marker", 22));
            gc.fillText(Integer.toString(0),x+dimension/2,y+dimension/2);

        }
        
        @SuppressWarnings("deprecation")
		public void run(){
        	int FPS = 60;
        	int animationDuration = 1000;
            double x = row*dimension;
            double y = column*dimension;
            for (int i = 0; i < FPS; i++){
            	if (style == 0){
            		y -= dimension/FPS;
            	}
            	else if (style == 1){
            		y += dimension/FPS;
            	}
            	else if (style == 2){
            		x -= dimension/FPS;
            	}
            	else if (style == 3){
            		x += dimension/FPS;
            	}
            	
            	gc.clearRect(x, y, dimension, dimension);
            	this.drawZeroTile(gc);
                if (number==0)
                    gc.setFill(Color.DARKRED);
                else
                    gc.setFill(Color.DARKSLATEGRAY);
                gc.fillRect(x,y,dimension,dimension);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x,y,dimension,dimension);

                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Permanent Marker", 22));
                gc.fillText(Integer.toString(number),x+dimension/2,y+dimension/2);
                
                try {
					Thread.sleep(animationDuration/FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            this.stop();
        }
        
        public void setGc(GraphicsContext gc){
        	this.gc = gc;
        }


}

