package classes;

import javax.swing.JFrame;

public class AppFrame extends JFrame{
    
    private TitleBar title;
    private List list;
    private ButtonPanel btnPanel;

    //constructor
    AppFrame(){
        this.setSize(400,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
