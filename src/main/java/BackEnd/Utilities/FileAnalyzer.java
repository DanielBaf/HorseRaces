package BackEnd.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import BackEnd.Objects.Bet;
import BackEnd.Objects.Gambler;
import BackEnd.Objects.List.NodeList;
import BackEnd.Reports.ReportStatus;

public class FileAnalyzer {

    public FileAnalyzer() {
    }

    public ReportStatus analyzeFile(String fileName, NodeList<Bet> list) {
        File file = new File(fileName);
        try ( Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",");
                saveDataFromVector(data, list);
            }
            return ReportStatus.SUCCESS;
        } catch (FileNotFoundException e) {
            return ReportStatus.FILE_NOT_FOUND;
        }

    }

    private void saveDataFromVector(String[] data, NodeList<Bet> list) {
        try {
            Gambler gambler = new Gambler(data[0].trim()); // always the 1st val is the name
            Double ammount = Double.valueOf(data[1].trim()); // always the 2nd val is the ammount
            int[] horses = new int[data.length - 2]; // there must be 10 horses\
            for (int i = 0; i < data.length - 2; i++) {
                horses[i] = Integer.valueOf(data[i + 2].trim());
            }
            list.addAtHead(new Bet(gambler, ammount, horses));
        } catch (Exception e) {
            System.out.println("Error in saveDataFromVector " + e.getMessage());
        }
    }

}
