import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MVC_MAIN {
    private static Controller c;
    private static final int BoundWidth = 300, BoundHeight = 300;
    JFrame Frame;
    private Color BricksColor = Color.WHITE, BackGroundColor = Color.BLACK, PaddleColor = Color.WHITE, BallColor = Color.WHITE;
    private int BricksColorIndex = 0, BackGroundColorIndex = 0, PaddleColorIndex = 0, BallColorIndex = 0;

    public static void main(String[] args) throws Exception {
        new MVC_MAIN();
    }

    MVC_MAIN(){
        //Screen 1
        Frame = new JFrame();

        /* Frame settings */
        Frame.setSize(BoundWidth, BoundHeight);
        Frame.setTitle("Bricks Breaker Java Game");
        Frame.setResizable(true);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);

        PlayButton_function();
        SettingButton_function();

        Frame.setVisible(true);
    }

    void PlayButton_function(){
        JButton PlayButton=new JButton("Play");
        PlayButton.setBounds(BoundWidth/2 - 50,50,100,30);
        PlayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                c = new Controller(BricksColor, BackGroundColor, PaddleColor, BallColor);
                //Frame.setVisible(false);
            }
        });
        Frame.add(PlayButton);
    }

    void SettingButton_function(){/*Screen2*/
        JButton SettingButton=new JButton("Settings");
        SettingButton.setBounds(BoundWidth/2 - 50,100,100,30);
        SettingButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Frame.setEnabled(false);
                JFrame Frame2;
                Frame2 = new JFrame();
                /* Frame settings */
                Frame2.setSize(BoundWidth, BoundHeight);
                Frame2.setTitle("Settings");
                Frame2.setResizable(false);
                Frame2.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        Frame.setEnabled(true);
                        Frame2.dispose();
                    }
                });
                Frame2.setLayout(null);
                Frame2.setVisible(true);

                String[] backcolorStrings = { "Black", "Green", "Blue", "Gray", "DARK_GRAY", "RED", "Yellow" };
                Color [] backcolors = {Color.BLACK, Color.GREEN, Color.BLUE, Color.GRAY, Color.DARK_GRAY, Color.RED, Color.YELLOW};

                JLabel BackGroundcolorLabel = new JLabel();
                BackGroundcolorLabel.setText("BackGround color");
                BackGroundcolorLabel.setBounds(BoundWidth/2 - 140,30,140,30);
                JComboBox BackGroundcolorList = new JComboBox(backcolorStrings);
                BackGroundcolorList.setSelectedIndex(BackGroundColorIndex);
                BackGroundcolorList.addActionListener(new ActionListener() {//add actionlistner to listen for change
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BackGroundColor = backcolors[BackGroundcolorList.getSelectedIndex()];
                        BackGroundColorIndex = BackGroundcolorList.getSelectedIndex();
                    }
                });
                BackGroundcolorList.setBounds(BoundWidth/2 - 20,30,100,30);

                String[] colorStrings = { "White", "Green", "Blue", "Gray", "Black", "DARK_GRAY" };
                Color []colors = {Color.WHITE, Color.GREEN, Color.BLUE, Color.GRAY, Color.BLACK, Color.DARK_GRAY};
                JLabel BrickcolorLabel = new JLabel();
                BrickcolorLabel.setText("Bricks color");
                BrickcolorLabel.setBounds(BoundWidth/2 - 140,80,100,30);
                JComboBox BrickcolorList = new JComboBox(colorStrings);
                BrickcolorList.setSelectedIndex(BricksColorIndex);
                BrickcolorList.addActionListener(new ActionListener() {//add actionlistner to listen for change
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BricksColor = colors[BrickcolorList.getSelectedIndex()];
                        BricksColorIndex = BrickcolorList.getSelectedIndex();
                    }
                });
                BrickcolorList.setBounds(BoundWidth/2 - 50,80,100,30);

                JLabel PaddlecolorLabel = new JLabel();
                PaddlecolorLabel.setText("Paddle color");
                PaddlecolorLabel.setBounds(BoundWidth/2 - 140,130,100,30);
                JComboBox PaddlecolorList = new JComboBox(colorStrings);
                PaddlecolorList.setSelectedIndex(PaddleColorIndex);
                PaddlecolorList.addActionListener(new ActionListener() {//add actionlistner to listen for change
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            PaddleColor = colors[PaddlecolorList.getSelectedIndex()];
                            PaddleColorIndex = PaddlecolorList.getSelectedIndex();
                    }
                });
                PaddlecolorList.setBounds(BoundWidth/2 - 50,130,100,30);

                JLabel BallcolorLabel = new JLabel();
                BallcolorLabel.setText("Ball color");
                BallcolorLabel.setBounds(BoundWidth/2 - 140,180,100,30);
                JComboBox BallcolorList = new JComboBox(colorStrings);
                BallcolorList.setSelectedIndex(BallColorIndex);
                BallcolorList.addActionListener(new ActionListener() {//add actionlistner to listen for change
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BallColor = colors[BallcolorList.getSelectedIndex()];
                        BallColorIndex = BallcolorList.getSelectedIndex();
                    }
                });
                BallcolorList.setBounds(BoundWidth/2 - 50,180,100,30);

                Frame2.add(BackGroundcolorLabel);
                Frame2.add(BackGroundcolorList);
                Frame2.add(BrickcolorLabel);
                Frame2.add(BrickcolorList);
                Frame2.add(PaddlecolorLabel);
                Frame2.add(PaddlecolorList);
                Frame2.add(BallcolorLabel);
                Frame2.add(BallcolorList);
            }
        });
        Frame.add(SettingButton);
    }

}