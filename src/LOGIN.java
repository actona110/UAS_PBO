import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LOGIN {
    private JPanel LoginPanel;
    private JLabel Judul_Login;
    private JLabel Email;
    private JLabel Password;
    private JTextField Email_Input;
    private JPasswordField Password_Input;
    private JCheckBox CheckBox_Remember;
    private JButton Login_Tombol;
    private JButton Register_Tombol;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");
        LOGIN loginForm = new LOGIN();
        frame.setContentPane(loginForm.LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);

        // Tambahkan ActionListener untuk tombol Login
        loginForm.Login_Tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginForm.Email_Input.getText();
                String password = new String(loginForm.Password_Input.getPassword());

                // Periksa kredensial pengguna dengan database
                if (checkUserCredentials(email, password)) {
                    JOptionPane.showMessageDialog(frame, "Login berhasil!");
                    // Lanjutkan dengan logika aplikasi setelah login
                } else {
                    JOptionPane.showMessageDialog(frame, "Login gagal. Cek email dan password Anda.");
                }
            }
        });
    }

    private static boolean checkUserCredentials(String email, String password) {
        Connection connection = DatabaseUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM pengguna WHERE email = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.next(); // Jika ada hasil, kredensial benar
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Jika terjadi kesalahan, atau kredensial tidak cocok
    }
}
