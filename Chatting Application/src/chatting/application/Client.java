package chatting.application;

import static chatting.application.Server.dout;
import static chatting.application.Server.formatLabel;
import static chatting.application.Server.t;
import static chatting.application.Server.vertical;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{

JTextField text;
static JPanel a1;
static Box vertical =  Box.createVerticalBox();
static DataOutputStream dout;
static JFrame t = new JFrame();


Client(){
    t.setLayout(null);
    JPanel p1 = new JPanel();
    p1.setBackground(new Color(0,92,75));
    p1.setLayout(null);
    p1.setBounds(0, 0, 450, 55);
    t.add(p1);
    
    ImageIcon  i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
    Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel back = new JLabel(i3);
    back.setBounds(5, 15, 25, 25);
    p1.add(back);
    
    back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae){
        System.exit(0);
        }
    });
    
    ImageIcon  i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
    Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
    ImageIcon i6 = new ImageIcon(i5);
    JLabel profile = new JLabel(i6);
    profile.setBounds(35, 8, 40, 40);
    p1.add(profile);
    
    ImageIcon  i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
    Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    ImageIcon i9 = new ImageIcon(i8);
    JLabel video = new JLabel(i9);
    video.setBounds(330, 13, 30, 30);
    p1.add(video);
    
    ImageIcon  i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
    Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    ImageIcon i12 = new ImageIcon(i11);
    JLabel call = new JLabel(i12);
    call.setBounds(377, 13 ,30, 30);
    p1.add(call);
  
    ImageIcon  i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
    Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
    ImageIcon i15 = new ImageIcon(i14);
    JLabel dots = new JLabel(i15);
    dots.setBounds(420, 15 ,10, 25);
    p1.add(dots);
    
    
    JLabel name = new JLabel("Bunty");
    name.setBounds(80, 14, 100, 15);
    name.setForeground(Color.white);
    name.setFont(new Font("Helvetica", Font.BOLD, 15));
    p1.add(name);

    JLabel status = new JLabel("Active Now");
    status.setBounds(82, 30, 100, 11);
    status.setForeground(Color.white);
    status.setFont(new Font("Helvetica", Font.ITALIC, 11));
    p1.add(status);


    a1 = new JPanel();
    a1.setBounds(5,60,440,634);
    a1.setBackground(new Color(238,238,238));
    t.add(a1);
 
    text = new JTextField();
    text.setBounds(5, 655, 325, 40);
    text.setBackground(Color.LIGHT_GRAY);
    text.setFont(new Font("Helvetica", Font.PLAIN, 14));
    t.add(text);
    
    JButton send = new JButton("Send");
    send.setBounds(326, 655, 120, 40);
    send.setBackground(new Color(0,92,75));
    send.setForeground(Color.white);
    send.addActionListener(this);
    send.setFont(new Font("Helvetica", Font.ITALIC, 14));
    t.add(send);
    
    t.setSize(450,700);
    t.setLocation(800,50);
    t.setUndecorated(true);
    t.getContentPane().setBackground(Color.WHITE);
    t.setVisible(true);
}


    public void actionPerformed(ActionEvent ae){
        try{
        String out = text.getText();
        JPanel p2= formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(14));
        a1.add(vertical,BorderLayout.PAGE_START);
        dout.writeUTF(out);
        
        text.setText("");
        
        t.repaint();
        t.invalidate();
        t.validate();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    public static JPanel formatLabel(String out){
       JPanel panel = new JPanel();
       
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       
       JLabel output = new JLabel(out);
       output.setFont(new Font("Helvetica", Font.PLAIN,14));
       output.setBackground(new Color(29, 170, 97));
       output.setBorder(new EmptyBorder(15,15,15,50));
       output.setOpaque(true);
       panel.add(output);
       
       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
       JLabel time = new JLabel();
       time.setText(sdf.format(cal.getTime()));
       panel.add(time);
       
       return panel;
    }
    
    public static void main(String[] args)
    {
        new Client();
        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    t.validate();
                }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

