package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.MyConnection;


public class ForgotPassword extends JFrame implements ActionListener {

    JTextField emialtextfield, answertextfield;
    JPasswordField newpasswordtextfield;
    JButton showPasswordButton,reset,backButton;
    ForgotPassword(){

        setLayout(null);
        setTitle("FORGOT PASSWORD");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/forgot.png"));
        Image i2 = i1.getImage().getScaledInstance(700, 450, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,700,450);
        add(image);


        JLabel forgotPassword = new JLabel("Forgot Password");
        forgotPassword.setFont(new Font("Montserrat",Font.BOLD,30));
        forgotPassword.setBounds(220,30,250,40);
        image.add(forgotPassword);

        JLabel email = new JLabel("Email");
        email.setFont(new Font("Montserrat",Font.BOLD,14));
        email.setBounds(330,90,200,30);
        image.add(email);
        emialtextfield = new JTextField();
        emialtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        emialtextfield.setBounds(330,120,250,25);
        image.add(emialtextfield);


        JLabel question = new JLabel("Whats your favourite color?");
        question.setFont(new Font("Montserrat",Font.BOLD,14));
        question.setBounds(330,150,200,30);
        image.add(question);
        answertextfield = new JTextField();
        answertextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        answertextfield.setBounds(330,180,250,25);
        image.add(answertextfield);

        JLabel newpassword = new JLabel("New Password");
        newpassword.setFont(new Font("Montserrat",Font.BOLD,14));
        newpassword.setBounds(330,210,200,30);
        image.add(newpassword);
        newpasswordtextfield = new JPasswordField();
        newpasswordtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        newpasswordtextfield.setBounds(330,240,250,25);
        image.add(newpasswordtextfield);

        showPasswordButton = new JButton();
        showPasswordButton.setBounds(580, 240, 30, 25); // Adjust the position as needed
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")); // Replace with your image path
        showPasswordButton.setIcon(icon);
        showPasswordButton.setBackground(Color.black);
        image.add(showPasswordButton);

        showPasswordButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        if (newpasswordtextfield.getEchoChar() == (char) 0) {
            newpasswordtextfield.setEchoChar('*');
            // Optionally, change the button icon to a "hide password" icon
            // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")));
        } else {
            newpasswordtextfield.setEchoChar((char) 0);
            // Optionally, change the button icon back to a "show password" icon
            // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/show.png")));
        }
    }
});


        Color color = Color.decode("#495057");

        reset = new JButton("Reset Password");
        reset.setBackground(color);
        reset.setForeground(Color.white);
        reset.setFont(new Font("Montserrat",Font.BOLD,14));
        reset.setBounds(350,290,200,30);
        reset.addActionListener(this);
        image.add(reset);
        reset.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                reset.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        backButton = new JButton("Back");
        backButton.setBackground(color);
        backButton.setForeground(Color.white);
        backButton.setFont(new Font("Montserrat",Font.BOLD,14));
        backButton.setBounds(350,340,200,30);
        backButton.addActionListener(this);
        image.add(backButton);
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });


        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(700,450);
        setLocation(300,100);
        setVisible(true);
    }

@Override
public void actionPerformed(ActionEvent ae){
    if(ae.getSource() == reset){
        String email = emialtextfield.getText();
        String answer = answertextfield.getText();
        String newPassword = new String(newpasswordtextfield.getPassword());

        if(email.isEmpty() || answer.isEmpty() || newPassword.isEmpty()){
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
            return;
        }

        // Check if email or answer contains any special characters or numbers
        if(email.matches(".*[^a-zA-Z0-9@.].*") || answer.matches(".*[^a-zA-Z ].*")){
            JOptionPane.showMessageDialog(null, "Colour should not contain any special characters or numbers");
            return;
        }

        try {
            Connection conn = MyConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM [user] WHERE uemail = ? AND uans = ?");
            ps.setString(1, email);
            ps.setString(2, answer);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PreparedStatement psUpdate = conn.prepareStatement("UPDATE [user] SET upassword = ? WHERE uemail = ?");
                psUpdate.setString(1, newPassword);
                psUpdate.setString(2, email);
                psUpdate.executeUpdate();
                JOptionPane.showMessageDialog(null, "Password has been reset successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect email or answer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == backButton){
        this.setVisible(false);
        new Login().setVisible(true);
    }
}

    public static void main(String[] args) {
        new ForgotPassword().setVisible(true);

    }
}