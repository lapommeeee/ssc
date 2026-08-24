import controller.CommandExecutor;
import model.Robot;
import model.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Robot robot = new Robot(new Table(5, 5));
        CommandExecutor executor = new CommandExecutor(robot);

        if (args.length > 0) {
            List<String> lines = Files.readAllLines(Path.of(args[0]), StandardCharsets.UTF_8);
            for (String line : lines) {
                executor.execute(line);
            }
        } else {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    executor.execute(line);
                }
            }
        }
    }
}