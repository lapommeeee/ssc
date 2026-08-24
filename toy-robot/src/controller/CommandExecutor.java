package controller;

import model.Robot;

public class CommandExecutor {

    private final Robot robot;


    public CommandExecutor(Robot robot) {
        this.robot = robot;
    }

    public void execute(String command) {
        String[] parts = command.trim().split("\\s+");
        String action = parts[0].toUpperCase();

        switch (action) {
            case "PLACE":
                if (parts.length == 2) {
                    String[] params = parts[1].split(",");
                    if (params.length == 3) {
                        try {
                            int x = Integer.parseInt(params[0]);
                            int y = Integer.parseInt(params[1]);
                            String dirStr = params[2].toUpperCase();
                            robot.place(x, y, model.Direction.valueOf(dirStr));
                        } catch (IllegalArgumentException e) {
                            // Invalid PLACE parameters, ignore the command
                        }
                    }
                }
                break;
            case "MOVE":
                robot.move();
                break;
            case "LEFT":
                robot.left();
                break;
            case "RIGHT":
                robot.right();
                break;
            case "REPORT":
                if (robot.isPlaced()) {
                    System.out.println(robot.report());
                }
                break;
            default:
                // Unknown command, ignore
                break;
        }
    }
}
