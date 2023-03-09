import processing.core.PApplet;

public class Main extends PApplet {
    private static final int CELL_SIZE = 20;

    public static final int NUM_ROWS = 30;
    public static final int NUM_COLUMNS = 60;
    public static Main app;
    private CellState cellState = CellState.DEAD;
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

    public void setup(){
        Rules rules = new MooreRules(new int[]{3}, new int[]{2, 3});
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLUMNS; col++){
                Cell c = new Cell(col*CELL_SIZE,row*CELL_SIZE,CELL_SIZE,row,col,cellState,rules);
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
    }/*
    Define applyRules, which iterates over cells and calls applyRules on each element.
    In order to avoid incorporating special cases for cells on the edge of the grid,
    do not iterate over the first and last rows and columns.

    Define evolve, which iterates over cells and calls evolve on each element.
    Add an instance variable (initially false) for starting and suspending evolutions
    private boolean doEvolve;
    Define keyPressed to toggle doEvolve.
    Update draw to call applyRules followed by evolve if doEvolve is true.
    */
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