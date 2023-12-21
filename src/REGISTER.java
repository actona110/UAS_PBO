import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class REGISTER {
    private JPanel RegisterPanel;
    private JLabel Judul_Register;
    private JLabel First_Name;
    private JLabel Last_Name;
    private JLabel Email;
    private JLabel Password;
    private JLabel Re_Password;
    private JTextField Fname_Input;
    private JTextField Lname_Input;
    private JTextField Email_Input;
    private JPasswordField Password_Input;
    private JPasswordField Repassword_Input;
    private JButton Register_Button;

    public JPanel getRegisterPanel() {
        return RegisterPanel;
    }

    public REGISTER() {
        Register_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = Fname_Input.getText();
                String lname = Lname_Input.getText();
                String email = Email_Input.getText();
                String password = new String(Password_Input.getPassword());

                // Simpan data ke database
                Connection connection = DatabaseUtil.getConnection();
                try {
                    String sql = "INSERT INTO pengguna (fname, lname, email, password) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, fname);
                    statement.setString(2, lname);
                    statement.setString(3, email);
                    statement.setString(4, password);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(RegisterPanel, "Registrasi berhasil!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterPanel, "Registrasi gagal!");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration Form");
        REGISTER registerForm = new REGISTER();
        frame.setContentPane(registerForm.getRegisterPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600)); // Set the minimum size here
        frame.pack();
        frame.setVisible(true);
    }
}