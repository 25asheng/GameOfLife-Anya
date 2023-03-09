import processing.core.PApplet;
import java.util.Random;

public class Main extends PApplet {
    private static final int CELL_SIZE = 20;
    public static final int NUM_ROWS = 30;
    public static final int NUM_COLUMNS = 60;
    public static Main app;
    private CellState cellState;
    private Cell[][] cells = new Cell[NUM_ROWS][NUM_COLUMNS];
    private boolean doEvolve = false;
    public static void main(String[] args){
        PApplet.main("Main");
    }

    public Main(){
        super();
        app = this;
    }

    public void settings(){
        size(NUM_COLUMNS * CELL_SIZE,NUM_ROWS * CELL_SIZE);
    }

    //I chose to do the extension where I randomize the starting state of my Cells
    //The implementation can be seen in setup

    public void setup(){
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3});
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLUMNS; col++){
                if(row == 0 || row == cells.length-1 || col == 0 || col == cells[0].length-1){
                    cellState = CellState.DEAD; //Cell must start out as DEAD
                } else {
                    float rand = random(0,1);
                    if(rand <= 0.5){ //half the time, make the Cell dead
                        cellState = CellState.DEAD;
                    } else { //the other half the time make the Cell alive
                        cellState = CellState.ALIVE;
                    }
                }
                Cell c = new Cell(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, row, col, cellState, rules);
                cells[row][col] = c;
            }
        }
        // create a 2d array
        // create a nested loop to iterate over your cells by row and column
        //create an instance of a Cell, and add to the array

    }

    public void draw(){
        if(doEvolve){
            applyRules();
            evolve();
        }

        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLUMNS; col++){
                cells[row][col].display();
            }
        }

    }

    public void mouseClicked(){
        cells[mouseY/CELL_SIZE][mouseX/CELL_SIZE].handleClick();

        //and even more stuff
    }

    public void applyRules(){
        for(int i = 1; i < NUM_ROWS - 1; i++) {
            for (int j = 1; j < NUM_COLUMNS - 1; j++) {
                cells[i][j].applyRules(cells);
            }
        }
    }

    public void evolve(){
        for(int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                cells[i][j].evolve();
            }
        }
    }

    public void keyPressed(){
        doEvolve = !doEvolve;
    }
}