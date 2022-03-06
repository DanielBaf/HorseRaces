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
            Double ammount = data.length - 1 > 0 ? Double.valueOf(data[1].trim()) : 0; // always the 2nd val is the ammount
            int horsesLength = data.length - 2 < 0 ? (data.length - 1 < 0 ? 0 : data.length - 1) : data.length - 2;
            int[] horses = new int[horsesLength]; // there must be 10 horses\
            for (int i = 0; i < data.length - 2; i++) {
                try {
                    horses[i] = Integer.valueOf(data[i + 2].trim());
                } catch (NumberFormatException e) {
                    horses[i] = -1;
                }
            }
            list.addAtHead(new Bet(gambler, ammount, horses));
        } catch (NumberFormatException e) {
            System.out.println("Error in saveDataFromVector " + e.getMessage());
        }
    }

}
