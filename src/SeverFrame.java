import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SeverFrame extends JFrame {
    private Container CP;
    private JPanel panBtn = new JPanel(new GridLayout(3,3,3,3));
    private JButton[] btns = new JButton[9];

    private JPanel panOption = new JPanel(new GridLayout(7,1,3,3));
    private JLabel labIPtext = new JLabel("Sever IP:");
    private JLabel labIP = new JLabel("");
    private JLabel labPort = new JLabel("Port:");
    private JTextField tfPort = new JTextField();
    private JButton btnStart = new JButton("Start");
    private JButton btnStop = new JButton("Stop");
    private JButton btnExit = new JButton("Exit");

    private JPanel panDialog = new JPanel(new GridLayout(1,2,3,3));
    private JTextArea ta = new JTextArea();
    private JScrollPane sp = new JScrollPane(ta);
    private JPanel panInput = new JPanel();
    private JTextField tfInput = new JTextField();
    private JButton btnSend = new JButton("Send");

    public SeverFrame(){
        initComp();
    }

    private void initComp(){
        this.setBounds(200,200,600,600);
        CP = new Container();
        this.setContentPane(CP);
        CP.setLayout(new BorderLayout(3,3));

        CP.add(panBtn, BorderLayout.CENTER);
        CP.add(panOption , BorderLayout.EAST);
        CP.add(ta, BorderLayout.WEST);
        CP.add(panDialog, BorderLayout.SOUTH);

        panOption.add(labIPtext);
        panOption.add(labIP);
        panOption.add(labPort);
        panOption.add(tfPort);
        panOption.add(btnStart);
        panOption.add(btnStop);
        panOption.add(btnExit);
        btnExit.setBackground(new Color(106, 255, 125));

        ta.setPreferredSize(new Dimension(150,500));
        ta.setLineWrap(true);
        panDialog.add(tfInput);
        panDialog.add(btnSend);
        btnSend.setPreferredSize(new Dimension(100,50));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

}
