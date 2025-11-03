package Model;

public class Penumpang extends Pengguna {
    private int saldo;

    public Penumpang(int id, String nama, String password, String noTelp, int saldo) {
        super(id, nama, password, noTelp);
        this.saldo = saldo;
    }

    public int getSaldo() { return saldo; }
    public void setSaldo(int saldo) { this.saldo = saldo; }

    public void tambahSaldo(int nominal) { this.saldo += nominal; }

    public boolean kurangiSaldo(int nominal) {
        if (nominal <= saldo) {
            saldo -= nominal;
            return true;
        }
        return false;
    }

    @Override
    public String getRole() { return "Penumpang"; }

    @Override
    public void printInfo() {
        System.out.println("[Penumpang] " + nama + " | Saldo: Rp" + saldo);
    }
}
