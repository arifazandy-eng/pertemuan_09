package akun;

import akun.AkunDAO;
import akun.AkunDAO;
import akun.Sesi;
import akun.Sesi;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import tampilan.FormBarang;

public final class FormLogin extends JFrame {
    private final JTextField username = new JTextField(22);
    private final JPasswordField password = new JPasswordField(22);
    private final JButton masuk = new JButton("Masuk");
    private final JLabel status = new JLabel("Masukkan akun praktikum.");

    public FormLogin() {
        super("Inventori Laboratorium | Login P09");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 8));
        panel.setBorder(new EmptyBorder(22, 26, 22, 26));
        JLabel judul = new JLabel("INVENTORI LABORATORIUM");
        judul.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(judul);
        panel.add(new JLabel("Nama pengguna"));
        panel.add(username);
        panel.add(new JLabel("Kata sandi"));
        panel.add(password);
        panel.add(masuk);
        panel.add(status);
        setContentPane(panel);
        getRootPane().setDefaultButton(masuk);
        masuk.addActionListener(e -> login());
        pack();
        setMinimumSize(new Dimension(450, 310));
        setSize(450, 310);
        setLocationRelativeTo(null);
    }

    private void login() {
        if (!masuk.isEnabled()) return;
        final String nama = username.getText().trim();
        final char[] rahasia = password.getPassword();
        password.setText("");
        if (nama.isEmpty() || nama.length() > 50 || rahasia.length == 0
                || rahasia.length > 128) {
            Arrays.fill(rahasia, '\0');
            status.setText("Isi kedua kolom dengan nilai yang valid.");
            return;
        }
        masuk.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);
        status.setText("Memeriksa akun...");
        new SwingWorker<Sesi, Void>() {
            @Override protected Sesi doInBackground() throws Exception {
                try {
                    return new AkunDAO().masuk(nama, rahasia);
                } finally {
                    Arrays.fill(rahasia, '\0');
                }
            }
            @Override protected void done() {
                if (!isDisplayable()) return;
                try {
                    Sesi sesi = get();
                    if (sesi == null) {
                        status.setText("Login ditolak. Periksa akun dan kata sandi.");
                    } else {
                        new FormBarang(sesi).setVisible(true);
                        dispose();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    status.setText("Pemeriksaan akun terhenti.");
                } catch (ExecutionException e) {
                    status.setText("Login gagal. Periksa koneksi dan konfigurasi.");
                    e.getCause().printStackTrace();
                } finally {
                    masuk.setEnabled(true);
                    username.setEnabled(true);
                    password.setEnabled(true);
                    password.requestFocusInWindow();
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}