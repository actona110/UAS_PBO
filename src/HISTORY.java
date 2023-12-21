import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class HISTORY {
    private JTable Table_History;
    private JLabel Judul_History;

    public HISTORY() {
        // Buat model tabel
        DefaultTableModel tableModel = new DefaultTableModel();

        // Tambahkan kolom-kolom ke model tabel
        tableModel.addColumn("Nama Lengkap");
        tableModel.addColumn("Berat Badan");
        tableModel.addColumn("Tinggi Badan");
        tableModel.addColumn("Umur");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Nilai IMT");
        tableModel.addColumn("Keterangan");

        // Hubungkan ke database dan ambil data dari tabel IMT_User dan Main_User
        Connection connection = DatabaseUtil.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT CONCAT(m.fname, ' ', m.lname) AS NamaLengkap, i.berat, i.tinggi, i.umur, i.gender, i.nilai_imt, i.keterangan " +
                        "FROM imtpengguna i, pengguna m "; // Gantilah SomeColumn dengan kolom yang sesuai

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Isi data dari ResultSet ke model tabel
                while (resultSet.next()) {
                    tableModel.addRow(new Object[] {
                            resultSet.getString("NamaLengkap"),
                            resultSet.getDouble("berat"),
                            resultSet.getDouble("tinggi"),
                            resultSet.getInt("umur"),
                            resultSet.getString("gender"),
                            resultSet.getDouble("nilai_imt"),
                            resultSet.getString("keterangan")
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Set model tabel ke JTable
        Table_History.setModel(tableModel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HISTORY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));

        // Membuat instance HISTORY dan menampilkan JTable
        HISTORY history = new HISTORY();
        frame.add(new JScrollPane(history.Table_History));

        frame.pack();
        frame.setVisible(true);
    }
}
