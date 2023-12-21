import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class BERANDA {
    private JFrame frame;
    private JLabel Judul_IMT;
    private JLabel Berat_Badan;
    private JLabel Tinggi_Badan;
    private JLabel IMT_Text;
    private JTextField Berat_Input;
    private JTextField Tinggi_Input;
    private JTextField IMT_Hasil;
    private JButton Hitung_Tombol;
    private JButton Profile_Tombol;
    private JButton Simpan_Tombol;
    private JPanel Beranda_Panel;
    private JLabel Ket_Text;
    private JTextField Ket_Input;
    private JLabel Umur_Text;
    private JLabel Gender_Text;
    private JTextField textField1;
    private JRadioButton RadioLaki;
    private JRadioButton RadioPeremp;

    public BERANDA() {
        frame = new JFrame("BERANDA");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(RadioLaki);
        genderGroup.add(RadioPeremp);

        Hitung_Tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double Berat, Tinggi, IMT, umur;
                String gender;

                Berat = Double.parseDouble(Berat_Input.getText().replace(",", "."));
                Tinggi = Double.parseDouble(Tinggi_Input.getText().replace(",", "."));
                umur = Double.parseDouble(textField1.getText().replace(",", "."));

                gender = "";
                if (RadioLaki.isSelected()) {
                    gender = "Laki-Laki";
                } else if (RadioPeremp.isSelected()) {
                    gender = "Perempuan";
                }

                IMT = Berat / (Tinggi * Tinggi);

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String hasilIMT = decimalFormat.format(IMT);

                IMT_Hasil.setText(hasilIMT);

                String keterangan = "";

                if (gender.equals("Laki-Laki")) {
                    if (umur <= 24) {
                        if (IMT < 18.5) {
                            keterangan = "Kurus";
                        } else if (IMT < 23.9) {
                            keterangan = "Normal";
                        } else if (IMT < 28.9) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    } else {
                        if (IMT < 20.7) {
                            keterangan = "Kurus";
                        } else if (IMT < 26.4) {
                            keterangan = "Normal";
                        } else if (IMT < 31.1) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    }
                } else if (gender.equals("Perempuan")) {
                    if (umur <= 24) {
                        if (IMT < 17.9) {
                            keterangan = "Kurus";
                        } else if (IMT < 22.9) {
                            keterangan = "Normal";
                        } else if (IMT < 27.3) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    } else {
                        if (IMT < 19.6) {
                            keterangan = "Kurus";
                        } else if (IMT < 27.1) {
                            keterangan = "Normal";
                        } else if (IMT < 32.9) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    }
                }

                Ket_Input.setText(keterangan);
            }
        });
        Simpan_Tombol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil nilai-nilai yang diperlukan dari inputan
                double Berat, Tinggi, IMT, umur;
                String gender;

                Berat = Double.parseDouble(Berat_Input.getText().replace(",", "."));
                Tinggi = Double.parseDouble(Tinggi_Input.getText().replace(",", "."));
                umur = Double.parseDouble(textField1.getText().replace(",", "."));

                gender = "";
                if (RadioLaki.isSelected()) {
                    gender = "Laki-Laki";
                } else if (RadioPeremp.isSelected()) {
                    gender = "Perempuan";
                }

                IMT = Berat / (Tinggi * Tinggi);

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String hasilIMT = decimalFormat.format(IMT);

                IMT_Hasil.setText(hasilIMT);

                String keterangan = "";

                if (gender.equals("Laki-Laki")) {
                    if (umur <= 24) {
                        if (IMT < 18.5) {
                            keterangan = "Kurus";
                        } else if (IMT < 23.9) {
                            keterangan = "Normal";
                        } else if (IMT < 28.9) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    } else {
                        if (IMT < 20.7) {
                            keterangan = "Kurus";
                        } else if (IMT < 26.4) {
                            keterangan = "Normal";
                        } else if (IMT < 31.1) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    }
                } else if (gender.equals("Perempuan")) {
                    if (umur <= 24) {
                        if (IMT < 17.9) {
                            keterangan = "Kurus";
                        } else if (IMT < 22.9) {
                            keterangan = "Normal";
                        } else if (IMT < 27.3) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    } else {
                        if (IMT < 19.6) {
                            keterangan = "Kurus";
                        } else if (IMT < 27.1) {
                            keterangan = "Normal";
                        } else if (IMT < 32.9) {
                            keterangan = "Gemuk";
                        } else {
                            keterangan = "Obesitas";
                        }
                    }
                }

                Ket_Input.setText(keterangan);

                // Sekarang, Anda dapat menyimpan data ini ke dalam tabel di database.
                // Pastikan Anda memiliki koneksi database yang benar di sini dan gantilah dengan query SQL INSERT yang sesuai.
                Connection connection = DatabaseUtil.getConnection();
                if (connection != null) {
                    try {
                        String insertQuery = "INSERT INTO imtpengguna (berat, tinggi, umur, gender, nilai_imt, keterangan) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setDouble(1, Berat);
                        preparedStatement.setDouble(2, Tinggi);
                        preparedStatement.setDouble(3, umur);
                        preparedStatement.setString(4, gender);
                        preparedStatement.setDouble(5, IMT);
                        preparedStatement.setString(6, keterangan);
                        preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(frame, "Data berhasil disimpan ke dalam database.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Gagal menyimpan data ke dalam database.");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new BERANDA().createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame.setContentPane(Beranda_Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
