public abstract class Rules {
    public abstract boolean shouldBeBorn(int liveNeighbors);

    public abstract boolean shouldSurvive(int liveNeighbors);

    public CellState applyRules(CellState cellState, int liveNeighbors) {
        if (cellState == CellState.DEAD && shouldBeBorn(liveNeighbors) == true) {
            return CellState.WILL_REVIVE;
        } else if (cellState == CellState.ALIVE && shouldSurvive(liveNeighbors) == false) {
            return CellState.WILL_DIE;
        } else {
            return cellState;
        }
    }
}