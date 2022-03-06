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
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jefemayoneso
 */
public class CSVExporter {

    public void exportCSV(boolean exportValids, NodeList<Bet> bets) {
        String path = getPath();
        if (path == null) {
            JOptionPane.showMessageDialog(null,
                    "No se puede guardar el archivo en la ruta especificada, si quieres volver a exportar los archivos vuelve a correr el analisis");
        } else {
            path = path.endsWith(".csv") ? path : path + ".csv";
            writeToFile(bets, path, exportValids);
        }
    }

    private String getPath() {
        try {
            JFileChooser fChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
            fChooser.setFileFilter(filter);
            fChooser.showSaveDialog(null);
            File file = fChooser.getSelectedFile();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }

    }

    private void writeToFile(NodeList<Bet> bets, String path, boolean exportValids) {
        try {
            File file = new File(path);
            FileWriter writer;
            Node<Bet> current = bets.getTail();
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);
            while (current != null) {
                if (current.getData().isValid() == exportValids) {
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
