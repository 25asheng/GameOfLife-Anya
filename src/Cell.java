import processing.core.PApplet;

public class Cell {
    private int x;
    private int y;
    private int size;
    private int row;
    private int column;
    private CellState cellState;
    private Rules rules;

    public Cell(int x, int y, int size, int row, int column, CellState cellState, Rules rules){
        //constructor
        //constructs a Cell object with its location and size on the canvas, position within the 2D grid,
        //current state (alive or dead), and the rules that govern its next state
        super();
        this.x = x;
        this.y = y;
        this.size = size;
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.rules = rules;
    }

    public void applyRules(Cell[][] cells){
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }

    public void evolve(){
        if (cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }


    public void display(){
        if(cellState == CellState.ALIVE || cellState == CellState.WILL_DIE) {
            Main.app.fill(0);
        } else if (cellState == CellState.DEAD || cellState == CellState.WILL_REVIVE){
            Main.app.fill(255);
        }
        Main.app.rect(x,y,size,size);
        //draw itself as a rectangle on the canvas with the fill color that corresponds to its state
    }

    public void handleClick(){
        if(cellState == CellState.DEAD || cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.ALIVE || cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
        //enters the opposite state
    }

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
