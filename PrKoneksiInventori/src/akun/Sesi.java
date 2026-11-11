package akun;

public final class Sesi {
    private final String nama;
    private final String peran;

    Sesi(String nama, String peran) {
        this.nama = nama;
        this.peran = peran;
    }

    public String getNama() { return nama; }
    public String getPeran() { return peran; }
    public boolean bolehSimpan() { return "admin".equals(peran); }
}