package akun;

import koneksi.KoneksiDB;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class AkunDAO {
    private static final int ITERASI = 600000;
    private static final String FORMAT = "pbkdf2-sha256";

    public Sesi masuk(String username, char[] password)
            throws SQLException, GeneralSecurityException {
        String sql = "SELECT nama_lengkap, peran, password_hash "
                   + "FROM pengguna WHERE username = ? AND aktif = 1";
        String nama, peran, hash;
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                nama = rs.getString("nama_lengkap");
                peran = rs.getString("peran");
                hash = rs.getString("password_hash");
            }
        }
        if (!"admin".equals(peran) && !"petugas".equals(peran)) return null;
        return cocok(password, hash) ? new Sesi(nama, peran) : null;
    }

    static boolean cocok(char[] password, String tersimpan)
            throws GeneralSecurityException {
        if (tersimpan == null) return false;
        String[] bagian = tersimpan.split(":", -1);
        if (bagian.length != 4 || !FORMAT.equals(bagian[0])
                || !Integer.toString(ITERASI).equals(bagian[1])) return false;
        byte[] salt, harapan;
        try {
            salt = Base64.getDecoder().decode(bagian[2]);
            harapan = Base64.getDecoder().decode(bagian[3]);
        } catch (IllegalArgumentException e) {
            return false;
        }
        if (salt.length != 16 || harapan.length != 32) return false;
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERASI, 256);
        byte[] hasil = null;
        try {
            hasil = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return MessageDigest.isEqual(harapan, hasil);
        } finally {
            spec.clearPassword();
            if (hasil != null) Arrays.fill(hasil, (byte) 0);
        }
    }
}