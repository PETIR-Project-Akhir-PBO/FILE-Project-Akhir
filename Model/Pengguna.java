package Model;

public abstract class Pengguna {
    protected int id;
    protected String nama;
    protected String password;
    protected String noTelp;

    public Pengguna(int id, String nama, String password, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.noTelp = noTelp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public abstract String getRole();
    public abstract void printInfo();
}
