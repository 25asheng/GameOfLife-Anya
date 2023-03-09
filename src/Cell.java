import processing.core.PApplet;

/** This class, Cell, manages and displays Cell objects that each represent one block in the displayed grid.
 */

public class Cell {
    private int x;
    private int y;
    private int size;
    private int row;
    private int column;
    private CellState cellState;
    private Rules rules;

    /** Constructs a Cell object with location, size, location in terms of the grid, the state, and the rules
     * @param x x-coordinate of Cell in canvas
     * @param y y-coordinate of Cell in canvas
     * @param size dimension of the Cell when displayed
     * @param row row in array of Cells
     * @param column column in array of Cells
     * @param cellState enum state of Cell
     * @param rules rules that Cell abides and evolved by
     */
    public Cell(int x, int y, int size, int row, int column, CellState cellState, Rules rules){
        super();
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
    }

    /** Finds number of live neighbors, then assigns to new cell state
     */
    public void applyRules(Cell[][] cells){
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }

    /** Transforms will_revive state to alive and will_die state to dead
     */
    public void evolve(){
        if (cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }

    /** Draws Cell as a rectangle on the canvas with an assigned fill color
     */
    public void display(){
        if(cellState == CellState.ALIVE || cellState == CellState.WILL_DIE) {
            Main.app.fill(0);
        } else if (cellState == CellState.DEAD || cellState == CellState.WILL_REVIVE){
            Main.app.fill(255);
        }
        Main.app.rect(x,y,size,size);
    }

    /** Causes clicked Cells to enter the opposite state (including intermediate states)
     */
    public void handleClick(){
        if(cellState == CellState.DEAD || cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.ALIVE || cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }

    /** Iterates through each Moore neighbor of a Cell and counts then returns all alive or will_die neighbors, then
     * subtracts one if the center Cell is also alive, or we will have over-counted
     * @param cells array of Cells to count live neighbors within moore neighbors of
     * @return number of live neighbors
     */
    private int countLiveNeighbors(Cell[][] cells){
        int count = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(cells[row+i][column+j].cellState == CellState.ALIVE || cells[row+i][column+j].cellState == CellState.WILL_DIE){
                    count++;
                }
            }
        }

        if(cellState == CellState.ALIVE){
            count--;
        } //if the center Cell is already Alive, we've overcounted by 1

        //returns the number of live or transition to dead Moore neighbors
        return count;
    }

}
