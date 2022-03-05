/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import BackEnd.Objects.Bet;
import BackEnd.Objects.List.Node;
import BackEnd.Objects.List.NodeList;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author jefemayoneso
 */
public class CSVExporter {

    public void exportInvalidBets(NodeList<Bet> bets) {
        JOptionPane.showMessageDialog(null,
                "Algunas apuestas fueron invalidas, por favor elige dònde exportar el archivo");
        String path = getPath();
        if (path == null) {
            JOptionPane.showMessageDialog(null,
                    "No se puede guardar el archivo en la ruta especificada, si quieres volver a exportar los archivos vuelve a correr el analisis");
        } else {
            writeToFile(bets, path + ".csv");
        }
    }

    private String getPath() {
        try {
            JFileChooser fChooser = new JFileChooser();
            fChooser.showSaveDialog(null);
            File file = fChooser.getSelectedFile();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }

    }

    private void writeToFile(NodeList<Bet> bets, String path) {
        try {
            File file = new File(path);
            FileWriter writer;
            Node<Bet> current = bets.getTail();
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);
            while (current != null) {
                if (!current.getData().isValid()) {
                    String horses = "";
                    for (int i = 0; i < current.getData().getHorses().length; i++) {
                        horses += "," + current.getData().getHorses()[i];
                    }
                    writer.write(
                            current.getData().getGambler().getName() + "," + current.getData().getAmount() + horses
                            + "\n");
                }
                current = current.getNext();
            }
            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el archivo en la ruta especificada, se usará la ruta por defecto");
        }
    }

}
