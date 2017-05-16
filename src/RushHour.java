import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class RushHour {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        String input = args[0];
        if (input.charAt(input.length() - 1) == '/') { // Solve board files in a directory`.
            ArrayList<String> puzzles = getBoardFiles(input);
            for (int i = 0; i < puzzles.size(); i++) {
                Solver boardGame = new Solver(input + puzzles.get(i));
                boardGame.solve();
                RushHourGUI animatedBoardGame = new RushHourGUI(boardGame.parseArgs(input + puzzles.get(i)));
                animatedBoardGame.animate(input + puzzles.get(i) + "_sol");
            }

        } else { // Solve a single board file.
            Solver boardGame = new Solver(input);
            boardGame.solve();
            RushHourGUI animatedBoardGame = new RushHourGUI(boardGame.parseArgs(input));
            animatedBoardGame.animate(input + "_sol");
        }

    }

    private static ArrayList<String> getBoardFiles(String path) {
        ArrayList <String> boardFiles =  new ArrayList<String>();
        File[] fileList = new File(path).listFiles();
        for (File file: fileList) {
            if (file.isFile() && !isSolutionFile(file.getName())) {
                boardFiles.add(file.getName());
            }
        }

        return boardFiles;
    }

    private static boolean isSolutionFile(String fileName) {
        String identifier = fileName.substring(fileName.length() - 3
                , fileName.length());

        return identifier.equals("sol");
    }
}
