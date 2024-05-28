import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;

public class Main extends JFrame {

    JLabel login;

    public Main() {
        setLayout(null);
        setTitle("Main");


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/main.png"));
        Image i2 = i1.getImage();
        double scaleFactor = Math.min(1.0 * 800 / i2.getWidth(null), 1.0 * 500 / i2.getHeight(null));
        Image i3 = i2.getScaledInstance((int)(scaleFactor * i2.getWidth(null)), (int)(scaleFactor * i2.getHeight(null)), Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(i3);
        JLabel image = new JLabel(icon);
        image.setBounds(0, 0, 800, 500);
        add(image);

        Color greyColor = Color.decode("#DEE2E6");

        login = new JLabel("Login");
        login.setBounds(710, 65, 235, 50);
        login.setFont(new Font("Montserrat", Font.BOLD, 20));
        login.setForeground(Color.BLACK);
        login.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(login);
        login.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                login.setForeground(greyColor);
            }

            public void mouseExited(MouseEvent e) {
                login.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                login.setForeground(Color.BLACK);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new user.Login().setVisible(true);
            }
        });

        String[] columnNames = {"Product ID", "Product Name", "Category", "Quantity", "Price", "Image", "View Image"};

        DefaultTableModel model = new DefaultTableModel(new Object[0][], columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 200, 480, 240); // adjust these values as needed
        table.setFont(new Font("Montserrat", Font.PLAIN, 12));
        image.add(scrollPane);

        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(0);

        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);

        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel("View Image");
                label.setForeground(Color.BLACK);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });

        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                String imagePath = table.getValueAt(row, 5).toString();
                JFrame imageFrame = new JFrame();
                imageFrame.setLayout(new BorderLayout());
                int imageWidth = 500;
                int imageHeight = 500;

                ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));

                JLabel imageLabel = new JLabel(imageIcon);
                imageFrame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
                imageFrame.setSize(imageWidth, imageHeight);
                imageFrame.setLocationRelativeTo(null);
                imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                imageFrame.setVisible(true);

                JLabel label = new JLabel("View Image");
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                // Check if the clicked column is not the "View Image" column
                if (col != 6) {
                    JOptionPane.showMessageDialog(null, "Please login or signup to purchase item");
                }
            }
        });

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("pid");
                row[1] = rs.getString("pname");
                row[2] = rs.getString("cname");
                row[3] = rs.getInt("pqty");
                row[4] = rs.getFloat("pprice");
                row[5] = rs.getString("pimage");
                row[6] = "View Image";
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(800, 500);
        setLocation(250, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}