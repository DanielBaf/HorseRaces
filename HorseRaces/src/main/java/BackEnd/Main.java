package BackEnd;

import FrontEnd.AdminPanel;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        AdminPanel view = new AdminPanel();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // fullscreen
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
