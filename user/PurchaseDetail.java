package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PurchaseDetail extends JFrame implements ActionListener {

    JTextField searchtextfield;
    JButton back;
    JTable table;
    static int userId;

    private void loadPurchaseDetails(String searchText) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

            String sql = "SELECT p.*, c.cname AS category_name FROM purchase p " +
                         "JOIN product pr ON p.pid = pr.pid " +
                         "JOIN category c ON pr.cname = c.cname " +
                         "WHERE p.uid = ? AND (CAST(p.pid AS VARCHAR(10)) LIKE ? OR p.product_name LIKE ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, userId);
            stmt.setString(2, "%" + searchText + "%");
            stmt.setString(3, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("pid");
                row[2] = rs.getString("product_name");
                row[3] = rs.getInt("qty");
                row[4] = rs.getFloat("price");
                row[5] = rs.getFloat("total");
                row[6] = rs.getString("category_name");
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    PurchaseDetail(int userId){
        this.userId = userId;

        setLayout(null);
        setTitle("PURCHASE DETAIL");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bw.png"));
        Image i2 = i1.getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,700,600);
        add(image);

        JLabel title = new JLabel("Purchase Details");
        title.setFont(new Font("Montserrat",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        title.setBounds(250,10,500,40);
        image.add(title);

        JLabel search = new JLabel("Search Product:");
        search.setFont(new Font("Montserrat",Font.BOLD,14));
        search.setBounds(335,60,150,30);
        image.add(search);
        searchtextfield = new JTextField();
        searchtextfield.setFont(new Font("Montserrat", Font.BOLD, 12));
        searchtextfield.setBounds(450,60,200,30);
        image.add(searchtextfield);
        searchtextfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchText = searchtextfield.getText();
                loadPurchaseDetails(searchText);
            }
        });

        back = new JButton("Back");
        Color color = Color.decode("#495057");
        back.setBackground(color);
        back.setForeground(Color.WHITE);
        back.setBounds(550, 520, 100, 30);
        back.setFont(new Font("Montserrat", Font.BOLD, 16));
        back.addActionListener(this);
        image.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        Object[][] data = {};

        String[] columnNames1 = {"Purchase ID", "Product ID", "Product Name", "Quantity", "Price", "Total", "Category Name"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames1);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 600, 400);
        table.setFont(new Font("Montserrat", Font.PLAIN, 12));
        image.add(scrollPane);

        loadPurchaseDetails("");

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(700,600);
        setLocation(300,50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == back) {
            this.setVisible(false);
            new UserDashboard(userId).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PurchaseDetail(userId).setVisible(true);
    }
}