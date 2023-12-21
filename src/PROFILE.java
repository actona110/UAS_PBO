import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PROFILE {
    private JButton Edit_Tombol;
    private JTextField Field_FirstName;  // Updated field name for first name
    private JTextField Field_LastName;   // Updated field name for last name
    private JTextField Field_Email;
    private JPasswordField Field_Password;
    private JButton Hapus_Tombol;
    private JLabel fname_label;
    private JLabel lname_label;
    private JLabel email_label;
    private JLabel password_label;
    private JPanel ProfilePanel; // Initialize the ProfilePanel

    // Assuming you have a method to get the database connection
    private Connection connection = DatabaseUtil.getConnection();

    public PROFILE() {
        Edit_Tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
        Hapus_Tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    private void updateProfile() {
        // Get values from the input fields
        String newFirstName = Field_FirstName.getText();  // Updated variable name for first name
        String newLastName = Field_LastName.getText();    // Updated variable name for last name
        String newEmail = Field_Email.getText();
        char[] newPassword = Field_Password.getPassword();
        String newPasswordString = new String(newPassword);

        // Assuming you have a user ID or some identifier to identify the user in the database
        int userId = getUserId(); // Implement this method to get the user ID

        // Update only the non-empty fields
        if (!newFirstName.isEmpty()) {
            updateField("fname", newFirstName, userId);
        }
        if (!newLastName.isEmpty()) {
            updateField("lname", newLastName, userId);
        }
        if (!newEmail.isEmpty()) {
            updateField("email", newEmail, userId);
        }
        if (!newPasswordString.isEmpty()) {
            updateField("password", newPasswordString, userId);
        }

        // Show a message to the user
        JOptionPane.showMessageDialog(null, "Profile updated successfully!");

        // You may also add some feedback to the user indicating that the update was successful
    }

    private void updateField(String fieldName, String value, int userId) {
        try {
            String columnName = getColumnName(fieldName);
            String query = "UPDATE pengguna SET " + columnName + " = ? WHERE id = ?";
            System.out.println("Generated SQL Query: " + query);  // Print the generated query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message to the user)
        }
    }

    // Method to map field names to column names
    private String getColumnName(String fieldName) {
        // Implement your logic to map field names to column names
        // Retrieve the actual column names from the database
        // For simplicity, assuming field names are the same as column names
        try {
            // Assuming 'pengguna' is the name of your table
            java.sql.DatabaseMetaData meta = connection.getMetaData();
            java.sql.ResultSet res = meta.getColumns(null, null, "pengguna", null);
            System.out.println("Actual column names in the 'pengguna' table:");
            while (res.next()) {
                String columnName = res.getString("COLUMN_NAME");
                System.out.println(columnName);
                if (columnName.equals(fieldName)) {
                    return columnName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldName;
    }

    private void deleteUser() {
        String userEmail = Field_Email.getText();
        char[] userPassword = Field_Password.getPassword();
        String userPasswordString = new String(userPassword);

        // Assuming you have a user ID or some identifier to identify the user in the database
        int userId = getUserId(); // Implement this method to get the user ID

        // Check if email and password match before deletion
        if (validateUser(userEmail, userPasswordString)) {
            try {
                String query = "DELETE FROM pengguna WHERE id = ?";
                System.out.println("Generated SQL Query: " + query);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();

                // Show a message to the user
                JOptionPane.showMessageDialog(null, "User deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle the exception (e.g., show an error message to the user)
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password. Deletion failed.");
        }
    }

    private boolean validateUser(String userEmail, String userPassword) {
        try {
            String query = "SELECT id FROM pengguna WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, userPassword);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if there is a user with the provided email and password
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message to the user)
        }
        return false;
    }
    // Implement this method to get the user ID from your application
    private int getUserId() {
        // Return the user ID
        return 2; // Change this to your actual logic
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Profile Form");
        PROFILE profileForm = new PROFILE();
        frame.setContentPane(profileForm.ProfilePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
