package model;

public class Robot {

    private int posX;
    private int posY;
    private Direction direction;
    private final Table table;
    private boolean placed;

    public Robot(Table table) {
        this.table = table;
    }

    public boolean isOnTable(int x, int y) {
        return x >= 0 && x < table.getWidth() && y >= 0 && y < table.getHeight();
    }

    public void place(int x, int y, Direction direction) {
        if (isOnTable(x, y)) {
            this.posX = x;
            this.posY = y;
            this.direction = direction;
            this.placed = true;
        }
    }

    public void move() {
        if (!placed) {
            return;
        }
        int newX = posX + direction.getDeltaX();
        int newY = posY + direction.getDeltaY();
        if (isOnTable(newX, newY)) {
            this.posX = newX;
            this.posY = newY;
        }
    }

    public void left(){
        if(!placed){
            return;
        }

        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
        }
    }

    public void right(){
        if(!placed){
            return;
        }

        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
        }
    }

    public String report() {
        if (!placed) {
            return null;
        }
        return posX + "," + posY + "," + direction;
    }

    public boolean isPlaced() {
        return placed;
    }
}
