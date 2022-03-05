package BackEnd;

import FrontEnd.MainView1;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        MainView1 view = new MainView1();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // fullscreen
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }
}
