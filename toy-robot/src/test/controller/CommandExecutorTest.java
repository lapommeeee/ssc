package test.controller;

import controller.CommandExecutor;
import model.Direction;
import model.Robot;
import model.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CommandExecutorTest {

    private Robot robot;
    private CommandExecutor executor;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() {
        robot = new Robot(new Table(5, 5));
        executor = new CommandExecutor(robot);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    // ========== PLACE COMMAND TESTS ==========
    @Test
    public void testPlaceCommandValidPosition() {
        executor.execute("PLACE 0,0,NORTH");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testPlaceCommandWithVariousDirections() {
        executor.execute("PLACE 1,1,NORTH");
        assertEquals("1,1,NORTH", robot.report());
        
        executor.execute("PLACE 2,2,EAST");
        assertEquals("2,2,EAST", robot.report());
        
        executor.execute("PLACE 3,3,SOUTH");
        assertEquals("3,3,SOUTH", robot.report());
        
        executor.execute("PLACE 4,4,WEST");
        assertEquals("4,4,WEST", robot.report());
    }

    @Test
    public void testPlaceCommandOutsideBoundaries() {
        executor.execute("PLACE 5,5,NORTH");
        assertNull(robot.report());
        
        executor.execute("PLACE -1,0,NORTH");
        assertNull(robot.report());
        
        executor.execute("PLACE 0,-1,NORTH");
        assertNull(robot.report());
    }

    @Test
    public void testPlaceCommandInvalidDirection() {
        executor.execute("PLACE 0,0,INVALID");
        assertNull(robot.report());
    }

    @Test
    public void testPlaceCommandInvalidFormat() {
        executor.execute("PLACE 0,0");
        assertNull(robot.report());
        
        executor.execute("PLACE 0,0,NORTH,EXTRA");
        assertNull(robot.report());
    }

    @Test
    public void testPlaceCommandNonIntegerCoordinates() {
        executor.execute("PLACE a,b,NORTH");
        assertNull(robot.report());
        
        executor.execute("PLACE 1.5,2.5,NORTH");
        assertNull(robot.report());
    }

    @Test
    public void testPlaceCommandCaseInsensitive() {
        executor.execute("place 0,0,north");
        assertEquals("0,0,NORTH", robot.report());
        
        executor.execute("PLACE 1,1,SOUTH");
        assertEquals("1,1,SOUTH", robot.report());
    }

    @Test
    public void testPlaceCommandWithExtraSpaces() {
        executor.execute("PLACE   2,2,NORTH");
        assertEquals("2,2,NORTH", robot.report());
    }

    // ========== MOVE COMMAND TESTS ==========
    @Test
    public void testMoveCommandBeforePlacing() {
        executor.execute("MOVE");
        assertNull(robot.report());
    }

    @Test
    public void testMoveCommandAfterPlacing() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("MOVE");
        assertEquals("0,1,NORTH", robot.report());
    }

    @Test
    public void testMoveCommandMultipleTimes() {
        executor.execute("PLACE 0,0,EAST");
        executor.execute("MOVE");
        executor.execute("MOVE");
        executor.execute("MOVE");
        assertEquals("3,0,EAST", robot.report());
    }

    @Test
    public void testMoveCommandPreventsFallingOff() {
        executor.execute("PLACE 4,4,NORTH");
        executor.execute("MOVE");
        assertEquals("4,4,NORTH", robot.report());
    }

    @Test
    public void testMoveCommandCaseInsensitive() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("move");
        assertEquals("0,1,NORTH", robot.report());
        
        executor.execute("MOVE");
        assertEquals("0,2,NORTH", robot.report());
    }

    // ========== LEFT COMMAND TESTS ==========
    @Test
    public void testLeftCommandBeforePlacing() {
        executor.execute("LEFT");
        assertNull(robot.report());
    }

    @Test
    public void testLeftCommandAfterPlacing() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("LEFT");
        assertEquals("0,0,WEST", robot.report());
    }

    @Test
    public void testLeftCommandFullRotation() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("LEFT");
        executor.execute("LEFT");
        executor.execute("LEFT");
        executor.execute("LEFT");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testLeftCommandFromAllDirections() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("LEFT");
        assertEquals("0,0,WEST", robot.report());
        
        executor.execute("LEFT");
        assertEquals("0,0,SOUTH", robot.report());
        
        executor.execute("LEFT");
        assertEquals("0,0,EAST", robot.report());
        
        executor.execute("LEFT");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testLeftCommandCaseInsensitive() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("left");
        assertEquals("0,0,WEST", robot.report());
        
        executor.execute("LeftR");
        assertEquals("0,0,WEST", robot.report());
    }

    // ========== RIGHT COMMAND TESTS ==========
    @Test
    public void testRightCommandBeforePlacing() {
        executor.execute("RIGHT");
        assertNull(robot.report());
    }

    @Test
    public void testRightCommandAfterPlacing() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("RIGHT");
        assertEquals("0,0,EAST", robot.report());
    }

    @Test
    public void testRightCommandFullRotation() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("RIGHT");
        executor.execute("RIGHT");
        executor.execute("RIGHT");
        executor.execute("RIGHT");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testRightCommandFromAllDirections() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("RIGHT");
        assertEquals("0,0,EAST", robot.report());
        
        executor.execute("RIGHT");
        assertEquals("0,0,SOUTH", robot.report());
        
        executor.execute("RIGHT");
        assertEquals("0,0,WEST", robot.report());
        
        executor.execute("RIGHT");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testRightCommandCaseInsensitive() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("right");
        assertEquals("0,0,EAST", robot.report());
    }

    // ========== REPORT COMMAND TESTS ==========
    @Test
    public void testReportCommandBeforePlacing() {
        executor.execute("REPORT");
        // When robot is not placed, report() returns null, and println(null) prints "null"
        assertFalse(robot.isPlaced());
    }

    @Test
    public void testReportCommandAfterPlacing() {
        outContent.reset();
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("REPORT");
        assertEquals("0,0,NORTH", outContent.toString().trim());
    }

    @Test
    public void testReportCommandAfterMovement() {
        outContent.reset();
        executor.execute("PLACE 1,1,EAST");
        executor.execute("MOVE");
        executor.execute("REPORT");
        assertEquals("2,1,EAST", outContent.toString().trim());
    }

    @Test
    public void testReportCommandCaseInsensitive() {
        outContent.reset();
        executor.execute("PLACE 2,3,WEST");
        executor.execute("report");
        assertEquals("2,3,WEST", outContent.toString().trim());
    }

    @Test
    public void testReportCommandMultipleTimes() {
        outContent.reset();
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("REPORT");
        String firstReport = outContent.toString().trim();
        
        outContent.reset();
        executor.execute("MOVE");
        executor.execute("REPORT");
        String secondReport = outContent.toString().trim();
        
        assertEquals("0,0,NORTH", firstReport);
        assertEquals("0,1,NORTH", secondReport);
    }

    // ========== UNKNOWN COMMAND TESTS ==========
    @Test
    public void testUnknownCommand() {
        executor.execute("INVALID");
        assertNull(robot.report());
    }

    @Test
    public void testUnknownCommandDoesNotAffectRobot() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("UNKNOWN");
        assertEquals("0,0,NORTH", robot.report());
    }

    @Test
    public void testEmptyCommand() {
        executor.execute("");
        assertNull(robot.report());
    }

    @Test
    public void testCommandWithOnlySpaces() {
        executor.execute("   ");
        assertNull(robot.report());
    }

    // ========== INTEGRATION TESTS ==========
    @Test
    public void testComplexSequence1() {
        executor.execute("PLACE 1,2,NORTH");
        executor.execute("LEFT");
        executor.execute("MOVE");
        assertEquals("0,2,WEST", robot.report());
    }

    @Test
    public void testComplexSequence2() {
        executor.execute("PLACE 3,3,EAST");
        executor.execute("MOVE");
        executor.execute("MOVE");
        executor.execute("LEFT");
        executor.execute("MOVE");
        assertEquals("4,4,NORTH", robot.report());
    }

    @Test
    public void testSequenceWithMultipleReplacements() {
        executor.execute("PLACE 0,0,NORTH");
        outContent.reset();
        executor.execute("REPORT");
        assertEquals("0,0,NORTH", outContent.toString().trim());
        
        executor.execute("PLACE 4,4,SOUTH");
        outContent.reset();
        executor.execute("REPORT");
        assertEquals("4,4,SOUTH", outContent.toString().trim());
    }

    @Test
    public void testExampleSequenceFromSpec() {
        executor.execute("PLACE 0,0,NORTH");
        executor.execute("MOVE");
        outContent.reset();
        executor.execute("REPORT");
        assertEquals("0,1,NORTH", outContent.toString().trim());
    }

    @Test
    public void testExampleSequenceWithRotations() {
        executor.execute("PLACE 1,2,EAST");
        executor.execute("MOVE");
        executor.execute("MOVE");
        executor.execute("LEFT");
        executor.execute("MOVE");
        outContent.reset();
        executor.execute("REPORT");
        assertEquals("3,3,NORTH", outContent.toString().trim());
    }

    @Test
    public void testSequencePreventingFallOff() {
        executor.execute("PLACE 0,1,SOUTH");
        executor.execute("MOVE");
        executor.execute("MOVE");
        executor.execute("MOVE");
        assertEquals("0,0,SOUTH", robot.report());
    }

    @Test
    public void testPlaceCommandWithoutRequiredParameter() {
        executor.execute("PLACE");
        assertNull(robot.report());
    }

    @Test
    public void testPlaceCommandWithIncompleteCoordinates() {
        executor.execute("PLACE 0");
        assertNull(robot.report());
    }
}
