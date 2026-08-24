package test.model;

import model.Direction;
import model.Robot;
import model.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    private Table table;
    private Robot robot;

    @Before
    public void setUp() {
        table = new Table(5, 5);
        robot = new Robot(table);
    }

    // ========== PLACEMENT TESTS ==========
    @Test
    public void testPlaceRobotOnValidPosition() {
        robot.place(0, 0, Direction.NORTH);
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testPlaceRobotAtDifferentValidPositions() {
        robot.place(3, 4, Direction.SOUTH);
        assertEquals("3,4,SOUTH", robot.report());
        
        robot.place(2, 1, Direction.EAST);
        assertEquals("2,1,EAST", robot.report());
    }

    @Test
    public void testPlaceRobotOutsideTableBoundaries() {
        robot.place(5, 5, Direction.NORTH);
        assertNull(robot.report());
        
        robot.place(-1, 0, Direction.NORTH);
        assertNull(robot.report());
        
        robot.place(0, -1, Direction.NORTH);
        assertNull(robot.report());
    }

    @Test
    public void testRobotNotPlaced() {
        assertNull(robot.report());
    }

    @Test
    public void testPlaceRobotOverPreviouslyPlacedRobot() {
        robot.place(0, 0, Direction.NORTH);
        assertEquals("0,0,NORTH", robot.report());
        
        robot.place(4, 4, Direction.SOUTH);
        assertEquals("4,4,SOUTH", robot.report());
    }

    // ========== MOVEMENT TESTS ==========
    @Test
    public void testMoveNorth() {
        robot.place(0, 0, Direction.NORTH);
        robot.move();
        assertEquals("0,1,NORTH", robot.report());
    }

    @Test
    public void testMoveEast() {
        robot.place(0, 0, Direction.EAST);
        robot.move();
        assertEquals("1,0,EAST", robot.report());
    }

    @Test
    public void testMoveSouth() {
        robot.place(2, 2, Direction.SOUTH);
        robot.move();
        assertEquals("2,1,SOUTH", robot.report());
    }

    @Test
    public void testMoveWest() {
        robot.place(2, 2, Direction.WEST);
        robot.move();
        assertEquals("1,2,WEST", robot.report());
    }

    @Test
    public void testMoveMultipleSteps() {
        robot.place(0, 0, Direction.NORTH);
        robot.move();
        robot.move();
        robot.move();
        assertEquals("0,3,NORTH", robot.report());
    }

    @Test
    public void testMovePreventsFallingOffTableNorth() {
        robot.place(0, 4, Direction.NORTH);
        robot.move();
        assertEquals("0,4,NORTH", robot.report());
    }

    @Test
    public void testMovePreventsFallingOffTableEast() {
        robot.place(4, 0, Direction.EAST);
        robot.move();
        assertEquals("4,0,EAST", robot.report());
    }

    @Test
    public void testMovePreventsFallingOffTableSouth() {
        robot.place(0, 0, Direction.SOUTH);
        robot.move();
        assertEquals("0,0,SOUTH", robot.report());
    }

    @Test
    public void testMovePreventsFallingOffTableWest() {
        robot.place(0, 0, Direction.WEST);
        robot.move();
        assertEquals("0,0,WEST", robot.report());
    }

    @Test
    public void testMoveBeforePlacing() {
        robot.move();
        assertNull(robot.report());
    }

    // ========== ROTATION TESTS ==========
    @Test
    public void testTurnLeftFromNorth() {
        robot.place(0, 0, Direction.NORTH);
        robot.left();
        assertEquals("0,0,WEST", robot.report());
    }

    @Test
    public void testTurnLeftFromWest() {
        robot.place(0, 0, Direction.WEST);
        robot.left();
        assertEquals("0,0,SOUTH", robot.report());
    }

    @Test
    public void testTurnLeftFromSouth() {
        robot.place(0, 0, Direction.SOUTH);
        robot.left();
        assertEquals("0,0,EAST", robot.report());
    }

    @Test
    public void testTurnLeftFromEast() {
        robot.place(0, 0, Direction.EAST);
        robot.left();
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testTurnLeftMultipleTimes() {
        robot.place(0, 0, Direction.NORTH);
        robot.left();
        robot.left();
        robot.left();
        robot.left();
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testTurnRightFromNorth() {
        robot.place(0, 0, Direction.NORTH);
        robot.right();
        assertEquals("0,0,EAST", robot.report());
    }

    @Test
    public void testTurnRightFromEast() {
        robot.place(0, 0, Direction.EAST);
        robot.right();
        assertEquals("0,0,SOUTH", robot.report());
    }

    @Test
    public void testTurnRightFromSouth() {
        robot.place(0, 0, Direction.SOUTH);
        robot.right();
        assertEquals("0,0,WEST", robot.report());
    }

    @Test
    public void testTurnRightFromWest() {
        robot.place(0, 0, Direction.WEST);
        robot.right();
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testTurnRightMultipleTimes() {
        robot.place(0, 0, Direction.NORTH);
        robot.right();
        robot.right();
        robot.right();
        robot.right();
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testTurnLeftBeforePlacing() {
        robot.left();
        assertNull(robot.report());
    }

    @Test
    public void testTurnRightBeforePlacing() {
        robot.right();
        assertNull(robot.report());
    }

    // ========== COMBINED MOVEMENT TESTS ==========
    @Test
    public void testComplexSequence() {
        robot.place(1, 2, Direction.NORTH);
        robot.left();
        robot.move();
        assertEquals("0,2,WEST", robot.report());
    }

    @Test
    public void testSquarePattern() {
        robot.place(2, 2, Direction.NORTH);
        
        // Move north
        robot.move();
        assertEquals("2,3,NORTH", robot.report());
        
        // Turn right and move east
        robot.right();
        robot.move();
        assertEquals("3,3,EAST", robot.report());
        
        // Turn right and move south
        robot.right();
        robot.move();
        assertEquals("3,2,SOUTH", robot.report());
        
        // Turn right and move west
        robot.right();
        robot.move();
        assertEquals("2,2,WEST", robot.report());
    }

    // ========== BOUNDARY TESTS ==========
    @Test
    public void testIsOnTableAtBoundaries() {
        assertTrue(robot.isOnTable(0, 0));
        assertTrue(robot.isOnTable(4, 4));
        assertTrue(robot.isOnTable(0, 4));
        assertTrue(robot.isOnTable(4, 0));
    }

    @Test
    public void testIsOnTableOutsideBoundaries() {
        assertFalse(robot.isOnTable(-1, 0));
        assertFalse(robot.isOnTable(5, 0));
        assertFalse(robot.isOnTable(0, -1));
        assertFalse(robot.isOnTable(0, 5));
        assertFalse(robot.isOnTable(5, 5));
    }

    @Test
    public void testPlaceAtAllCorners() {
        // Top-left
        robot.place(0, 0, Direction.NORTH);
        assertEquals("0,0,NORTH", robot.report());
        
        // Top-right
        robot.place(4, 4, Direction.NORTH);
        assertEquals("4,4,NORTH", robot.report());
        
        // Bottom-left (y=0 is bottom)
        robot.place(0, 0, Direction.NORTH);
        assertEquals("0,0,NORTH", robot.report());
        
        // Bottom-right
        robot.place(4, 0, Direction.NORTH);
        assertEquals("4,0,NORTH", robot.report());
    }
}
