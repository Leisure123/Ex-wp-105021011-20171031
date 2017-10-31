import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class MainFrame extends JFrame {
    private LoginFrame login = new LoginFrame();
    private int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int frmW = 800, frmH = 750;
    private JMenuBar mbr = new JMenuBar();
    private JMenu mFile = new JMenu("File");
    private JMenu mSet = new JMenu("Set");
    private JMenu mGame = new JMenu("Game");
    private JMenu mAbout = new JMenu("About");
    private JMenuItem miFileExit = new JMenuItem("Exit");
    private JMenuItem miFileBook = new JMenuItem("Book");
    private JMenuItem miFileCategory = new JMenuItem("Category");
    private JMenuItem miGameLotto = new JMenuItem("Lotto");
    private JMenuItem miGameKey = new JMenuItem("Keyboard");
    private JMenuItem miSetFont = new JMenuItem("Font");
    private Random rnd = new Random(System.currentTimeMillis());

    private JDesktopPane jdp = new JDesktopPane();
    private JInternalFrame infLotto = new JInternalFrame("Lotto");
    private JPanel panUP = new JPanel(new GridLayout(1,6,3,3));
    private JPanel panDOWN = new JPanel(new GridLayout(1,2,3,3));
    private JLabel labs [] = new JLabel [6];
    private int labList[] = new int [6];
    private JButton btnClose = new JButton("Close");
    private JButton btnRe = new JButton("Re-Generated");

    private JInternalFrame inkey = new JInternalFrame("keyboard");
    private JButton btns[] = new JButton[12];
    private JTextField tf = new JTextField();
    private JPanel panKey = new JPanel(new GridLayout(4,3,3,3));
    private int btnList[] = new int [10];
    private JButton btnExit = new JButton("Exit");

    private JPanel panOption = new JPanel(new GridLayout(2,3,5,5));
    private JLabel labFamily = new JLabel("Family");
    private JLabel labFont = new JLabel("Font");
    private JLabel labSize = new JLabel("Size");
    private JTextField tfFamily = new JTextField("Times  new Romans");
    private JTextField tfSize = new JTextField("16");
    private String options[] = {"PLAIN","BOLD","ITALIC","BOLD+ITALIC"};
    private JComboBox cbOptions = new JComboBox(options);

    private JInternalFrame inBook = new JInternalFrame("Book");
    private Container inBookCP ;
    private JMenuBar mbrBook = new JMenuBar();
    private JMenu mBookNew = new JMenu("New");
    private JMenu mBookLoad = new JMenu("Load");
    private JMenu mBookClose = new JMenu("Close");
    private JTextArea ta = new JTextArea();
    private JScrollPane sp = new JScrollPane(ta);

    public MainFrame(LoginFrame loginframe){
        login = loginframe;
        initComp();
    }
    private void initComp(){
        this.setBounds(screenW/2 - frmW/2, screenH/2 - frmH/2, frmW, frmH);
        this.setJMenuBar(mbr);
        this.setContentPane(jdp);
        infLotto.setBounds(0,0,400,150);
        inkey.setBounds(0,150,350,500);
        inBook.setBounds(0,0,600,600);
        inBookCP = new Container();
        inBook.setContentPane(inBookCP);
        tf.setEnabled(false);
        // MenuBar
        mbr.add(mFile);
        mbr.add(mSet);
        mbr.add(mGame);
        mbr.add(mAbout);
        // MenuItem
        mFile.add(miFileBook);
        mFile.add(miFileCategory);
        mFile.add(miFileExit);
        mSet.add(miSetFont);
        mGame.add(miGameLotto);
        mGame.add(miGameKey);

        jdp.add(inBook);
        jdp.add(infLotto);
        jdp.add(inkey);
        infLotto.setLayout(new BorderLayout(3,3));
        inkey.setLayout(new BorderLayout(3,3));
        //internalFrame : Lotto
        panDOWN.add(btnClose);
        panDOWN.add(btnRe);
        infLotto.add(panUP, BorderLayout.CENTER);
        infLotto.add(panDOWN, BorderLayout.SOUTH);
        //internalFrame : keyboard
        inkey.add(tf, BorderLayout.NORTH);
        inkey.add(panKey, BorderLayout.CENTER);
        inkey.add(btnExit, BorderLayout.SOUTH);
        regenerate();
        panUP.setBackground(new Color(53, 255, 155));
        //Font sit
        panOption.add(labFamily);
        panOption.add(labFont);
        panOption.add(labSize);
        panOption.add(tfFamily);
        panOption.add(cbOptions);
        panOption.add(tfSize);

        miFileBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        miSetFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showConfirmDialog(MainFrame.this,
                        panOption,"Font",JOptionPane.OK_CANCEL_OPTION);
                int fontStyle = 0;
                switch (cbOptions.getSelectedIndex()){
                    case 0:
                        fontStyle = Font.PLAIN;
                        break;
                    case 1:
                        fontStyle = Font.BOLD;
                        break;
                    case 2:
                        fontStyle = Font.ITALIC;
                        break;
                    case 3:
                        fontStyle = Font.BOLD + Font.ITALIC;
                        break;
                }
                if(result == JOptionPane.OK_OPTION){
                    UIManager.put("Menu.font",new Font(tfFamily.getText(),fontStyle,Integer.parseInt(tfSize.getText())));
                }
            }
        });
        miSetFont.setAccelerator(KeyStroke.getKeyStroke('F',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        for(int i = 0; i < 6; i++){
            labs[i] = new JLabel();
            panUP.add(labs[i]);
            labs[i].setFont(new Font(null,Font.BOLD,16));
            labs[i].setHorizontalAlignment(SwingConstants.CENTER);
            labs[i].setText(Integer.toString(labList[i]));
        }
        keyRandom();
        for(int i = 0; i < 12; i++){
            btns[i] = new JButton();
            panKey.add(btns[i]);
        }

        for(int i = 0; i < 10;i++){
            if(i != 9){
                btns[i].setText(Integer.toString(btnList[i]));
                btns[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton tmpBtn = (JButton) e.getSource();
                        tf.setText(tf.getText() + tmpBtn.getText());
                    }
                });
            }else{
                btns[10].setText(Integer.toString(btnList[i]));
                btns[10].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton tmpBtn = (JButton) e.getSource();
                        tf.setText(tf.getText() + tmpBtn.getText());
                    }
                });
            }
        }

        btns[9].setText(".");
        btns[11].setText("C");
        btns[9].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton tmpBtn = (JButton) e.getSource();
                tf.setText(tf.getText() + tmpBtn.getText());
            }
        });
        btns[11].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("");
            }
        });

        miFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        miFileExit.setAccelerator(KeyStroke.getKeyStroke('X',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        miGameLotto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infLotto.setVisible(true);
            }
        });
        miGameLotto.setAccelerator(KeyStroke.getKeyStroke('G',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        miGameKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkey.setVisible(true);
            }
        });
        miGameKey.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infLotto.setVisible(false);
                regenerate();
                for(int i = 0; i < 6; i++){
                    labs[i].setText(Integer.toString(labList[i]));
                }
            }
        });

        btnRe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regenerate();
                for(int i = 0; i < 6; i++){
                    labs[i].setText(Integer.toString(labList[i]));
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkey.setVisible(false);
                tf.setText("");
                keyRandom();
                reRandom();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                login.setVisible(true);
                dispose();
            }
        });
    }

    private void regenerate(){
        boolean check ;
        for(int i = 0; i < 6;i++){
            check = true;
            while(check){
                check = false;
                int tmp = rnd.nextInt(49) + 1;
                for(int j = 0; j < i; j++){
                    if(labList[j] == tmp){
                        check = true;
                    }
                }
                labList[i] = tmp;
            }
        }
    }

    private void keyRandom(){
        for(int i = 0; i < 10; i++){
            btnList[i] = 10;
        }
        boolean check ;
        for(int i = 0; i < 10;i++){
            check = true;
            while(check){
                check = false;
                int tmp = rnd.nextInt(10);
                for(int j = 0; j < i; j++){
                    if(btnList[j] == tmp){
                        check = true;
                    }
                }
                btnList[i] = tmp;
            }
        }
    }
    private void reRandom(){
        for(int i = 0; i < 10;i++){
            if(i != 9){
                btns[i].setText(Integer.toString(btnList[i]));
            }else{
                btns[10].setText(Integer.toString(btnList[i]));
            }
        }
    }
}
