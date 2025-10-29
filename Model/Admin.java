package Model;

public class Admin extends Pengguna {
    private String akses;

    public Admin(int id, String nama, String password, String noTelp, String akses) {
        super(id, nama, password, noTelp);
        this.akses = akses;
    }

    public String getAkses() { return akses; }
    public void setAkses(String akses) { this.akses = akses; }

    @Override
    public String getRole() { return "Admin"; }

    @Override
    public void printInfo() {
        System.out.println("[Admin] " + nama + " | akses: " + akses);
    }
}
