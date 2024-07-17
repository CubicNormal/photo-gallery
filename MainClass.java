package javagui_picturegallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

class MainPanel extends JPanel implements ActionListener
{
    private JLabel lblPicture;
    private JButton btnBrowse,btnFirst,btnPrev,btnNext,btnLast,btnStart,btnStop;
    private final int LBL_WIDTH = 650;
    private final int LBL_HEIGHT = 400;
    private int pictIndex;
    private ImageIcon[] images;
    private Timer   time;
    private JLabel makeLabel(int x,int y,int w,int h)
    {
        JLabel temp = new JLabel();
        temp.setOpaque(true);
        temp.setBackground(Color.ORANGE);
        Border br1 = BorderFactory.createLineBorder(Color.WHITE, 2);
        Border br2 = BorderFactory.createLineBorder(Color.BLUE, 3);
        Border br3 = BorderFactory.createCompoundBorder(br2, br1);
        temp.setBorder(br3);
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JButton makeButton(String cap,int x,int y,int w,int h)
    {
        JButton temp = new JButton(cap);
        temp.setFont(new Font("Verdana", 1, 14));
        temp.setBounds(x,y,w,h);
        temp.addActionListener(this); 
        
        add(temp);
        return temp;
    }
    public MainPanel()
    {
        lblPicture=makeLabel(20,10,LBL_WIDTH,LBL_HEIGHT);
        btnBrowse = makeButton("Browse",50,420,100,30);
        btnFirst = makeButton("First",150,420,100,30);
        btnPrev = makeButton("Prev",250,420,100,30);
        btnNext = makeButton("Next",350,420,100,30);
        btnLast = makeButton("Last",450,420,100,30);
        btnStart = makeButton("Start",550,420,100,30);
        btnStop = makeButton("Stop",550,420,100,30);
        btnStop.setVisible(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob=e.getSource();
        if(ob==btnBrowse)
        {
            try{
            JFileChooser fileChoose= new JFileChooser();
            fileChoose.setMultiSelectionEnabled(true);
            fileChoose.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChoose.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter1=new FileNameExtensionFilter("JPEG Images (*.jpg)","jpg");
            FileNameExtensionFilter filter2=new FileNameExtensionFilter("GIF Images (*.gif)","gif");
            FileNameExtensionFilter filter3=new FileNameExtensionFilter("PNG Images (*.png)","png");
            fileChoose.addChoosableFileFilter(filter1);
            fileChoose.addChoosableFileFilter(filter2);
            fileChoose.addChoosableFileFilter(filter3);
            fileChoose.setFileFilter(filter1);
            
            
            int status = fileChoose.showOpenDialog(this);
            
            if(status == JFileChooser.APPROVE_OPTION)
            {
                images = null;
                File[] files = fileChoose.getSelectedFiles();
                int fileCount=files.length;
                images = new ImageIcon[fileCount];
                for(int idx=0;idx<fileCount;idx++)
                {
                    BufferedImage bimg=ImageIO.read(files[idx]);
                    Image scale = bimg.getScaledInstance(LBL_WIDTH,LBL_HEIGHT,Image.SCALE_SMOOTH);
                    images[idx]=new ImageIcon(scale);
                }
                pictIndex=0;
                lblPicture.setIcon(images[0]);
            }
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null,ex);
            }
        }
       
        else if(ob==btnFirst)
        {
            pictIndex=0;
            lblPicture.setIcon(images[pictIndex]);
        }
        else if(ob==btnNext)
        {
            if(pictIndex!=images.length-1){
            pictIndex++;
            lblPicture.setIcon(images[pictIndex]);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"This is the last image.");
            }
        }
        else if(ob==btnLast)
        {
            pictIndex=images.length-1;
            lblPicture.setIcon(images[pictIndex]);
        }
        else if(ob==btnPrev)
        {
            if(pictIndex!=0){
            pictIndex--;
            lblPicture.setIcon(images[pictIndex]);
            }
            else
                JOptionPane.showMessageDialog(null,"This is the last image.");
        }
        else if(ob==btnStart)
        {
            time=new Timer(200, act);
            time.setRepeats(true);
            time.start();
            btnStop.setVisible(true);
            btnStart.setVisible(false);
        }
        else if(ob==btnStop)
        {
            time.stop();
            btnStart.setVisible(true);
        }
    }
    ActionListener act=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pictIndex!=images.length-1){
                pictIndex++;
                lblPicture.setIcon(images[pictIndex]);
            }
            else{
                pictIndex=-1;
            }

        }
    };
}
class MainFrame extends JFrame
{
    private MainPanel panel = null;
    public MainFrame()
    {
        panel = new MainPanel();
        panel.setBackground(new Color(240,250,200));
        panel.setLayout(new BorderLayout());
        add(panel);
    }
}
public class MainClass
{
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(700,500);
        frame.setTitle("Look at this photograph");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
