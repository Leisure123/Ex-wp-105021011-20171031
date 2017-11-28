import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import  javax.swing.JFileChooser;

public class MainFrame extends JFrame {
//  排版
    private LoginFrame login = new LoginFrame();
    private int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int frmW = 1200, frmH = 800;
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
    private JMenuItem miGameTictactoe = new JMenuItem("Tic-tac-toe game");
    private JMenuItem miSetFont = new JMenuItem("Font");
    private Random rnd = new Random(System.currentTimeMillis());
    private JDesktopPane jdp = new JDesktopPane();
//  Lotto
    private JInternalFrame infLotto = new JInternalFrame("Lotto");
    private JPanel panUP = new JPanel(new GridLayout(1,6,3,3));
    private JPanel panDOWN = new JPanel(new GridLayout(1,2,3,3));
    private JLabel labs [] = new JLabel [6];
    private int labList[] = new int [6];
    private JButton btnClose = new JButton("Close");
    private JButton btnRe = new JButton("Re-Generated");
//  keyboard
    private JInternalFrame inkey = new JInternalFrame("keyboard");
    private JButton btns[] = new JButton[12];
    private JTextField tf = new JTextField();
    private JPanel panKey = new JPanel(new GridLayout(4,3,3,3));
    private int btnList[] = new int [10];
    private JButton btnExit = new JButton("Exit");
//  Change Font
    private JPanel panOption = new JPanel(new GridLayout(2,3,5,5));
    private JLabel labFamily = new JLabel("Family");
    private JLabel labFont = new JLabel("Font");
    private JLabel labSize = new JLabel("Size");
    private JTextField tfFamily = new JTextField("Times  new Romans");
    private JTextField tfSize = new JTextField("16");
    private String options[] = {"PLAIN","BOLD","ITALIC","BOLD+ITALIC"};
    private JComboBox cbOptions = new JComboBox(options);
//  Book
    private JInternalFrame inBook = new JInternalFrame("Book");
    private Container inBookCP ;
    private JMenuBar mbrBook = new JMenuBar();
    private JMenu mBookFeatures = new JMenu("Features");
    private JMenuItem miBookNew = new JMenuItem("New");
    private JMenuItem miBookLoad = new JMenuItem("Load");
    private JMenuItem miBookClose = new JMenuItem("Close");
//    private JTextArea ta = new JTextArea();
//    private JScrollPane sp = new JScrollPane(ta);
    private JFileChooser fc = new JFileChooser();
    private JPanel panDataTop = new JPanel();
    private JPanel panDataCen = new JPanel();


    public MainFrame(LoginFrame loginframe){
        login = loginframe;
        initComp();
    }
    private void initComp(){
//      排版
        this.setBounds(screenW/2 - frmW/2, screenH/2 - frmH/2, frmW, frmH);
        this.setJMenuBar(mbr);
        this.setContentPane(jdp);
        infLotto.setBounds(0,0,400,150);
        inkey.setBounds(0,150,350,500);
        inBook.setBounds(0,0,900,600);
        inBookCP = new Container();
        inBookCP.setLayout(new BorderLayout(3,3));
        inBook.setContentPane(inBookCP);
        tf.setEnabled(false);
//      MenuBar
        mbr.add(mFile);
        mbr.add(mSet);
        mbr.add(mGame);
        mbr.add(mAbout);
//      MenuItem
        mFile.add(miFileBook);
        mFile.add(miFileCategory);
        mFile.add(miFileExit);
        mSet.add(miSetFont);
        mGame.add(miGameLotto);
        mGame.add(miGameKey);
        mGame.add(miGameTictactoe);
//      interalFrame to Desktop
        jdp.add(inBook);
        jdp.add(infLotto);
        jdp.add(inkey);
        //internalFrame : Lotto
        infLotto.setLayout(new BorderLayout(3,3));
        panDOWN.add(btnClose);
        panDOWN.add(btnRe);
        infLotto.add(panUP, BorderLayout.CENTER);
        infLotto.add(panDOWN, BorderLayout.SOUTH);
//      internalFrame : keyboard
        inkey.setLayout(new BorderLayout(3,3));
        inkey.add(tf, BorderLayout.NORTH);
        inkey.add(panKey, BorderLayout.CENTER);
        inkey.add(btnExit, BorderLayout.SOUTH);
        regenerate();
        panUP.setBackground(new Color(53, 255, 155));
//      Font sit
        panOption.add(labFamily);
        panOption.add(labFont);
        panOption.add(labSize);
        panOption.add(tfFamily);
        panOption.add(cbOptions);
        panOption.add(tfSize);
//      Book
        inBook.setJMenuBar(mbrBook);
//        ta.setLineWrap(true);
//        inBookCP.add(sp, BorderLayout.CENTER);
        mbrBook.add(mBookFeatures);
        mBookFeatures.add(miBookNew);
        mBookFeatures.add(miBookLoad);
        mBookFeatures.add(miBookClose);


        miGameTictactoe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeverFrame sFrame = new SeverFrame();
                sFrame.setVisible(true);
            }
        });

//      Book
        miBookLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    try{
                        File infile = fc.getSelectedFile();
                        BufferedReader br = new BufferedReader(new FileReader(infile));
                        String [] strList = (br.readLine()).split(" |\\|");
                        int labRow = (strList.length/5)-1;
                        inBookCP.add(panDataTop, BorderLayout.NORTH);
                        inBookCP.add(panDataCen, BorderLayout.CENTER);
                        panDataTop.setLayout(new GridLayout(1,5,3,3));
                        panDataCen.setLayout(new GridLayout(labRow,5,3,3));
                        JLabel [] labs = new JLabel[strList.length];
                        for(int i =0; i < strList.length; i++){
                            labs[i] = new JLabel();
                            labs[i].setHorizontalAlignment(SwingConstants.CENTER);
                            labs[i].setOpaque(true);
                            if(i < 5){
                                labs[i].setBackground(new Color(114, 106, 255));
                                panDataTop.add(labs[i]);
                            }else{
                                labs[i].setBackground(new Color(136, 214, 255));
                                panDataCen.add(labs[i]);
                            }
                            labs[i].setText(strList[i]);
                        }
                    }catch(Exception ioe){
                        JOptionPane.showMessageDialog(null,"Open file error" + ioe.toString());
                    }
                }
            }
        });

        miBookClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inBook.setVisible(false);
            }
        });

        miFileBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inBook.setVisible(true);
            }
        });
        miFileBook.setAccelerator(KeyStroke.getKeyStroke('B',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//      改字體
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
//      Lotto
        for(int i = 0; i < 6; i++){
            labs[i] = new JLabel();
            panUP.add(labs[i]);
            labs[i].setFont(new Font(null,Font.BOLD,16));
            labs[i].setHorizontalAlignment(SwingConstants.CENTER);
            labs[i].setText(Integer.toString(labList[i]));
        }
//      鍵盤
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
        miGameKey.setAccelerator(KeyStroke.getKeyStroke('K', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//      Lotto : Exit
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
//      Lotto : 亂數鍵
        btnRe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regenerate();
                for(int i = 0; i < 6; i++){
                    labs[i].setText(Integer.toString(labList[i]));
                }
            }
        });
//      Keyboard : Exit
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
//  產生亂數按鈕
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
//  亂數鍵盤
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
//  顯示Lotto數字
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
