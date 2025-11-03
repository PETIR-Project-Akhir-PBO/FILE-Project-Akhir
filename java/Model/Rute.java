package Model;

public class Rute implements Cetak {
    private int id;
    private String namaRute;
    private String titikAwal;
    private String titikAkhir;
    private int idTransportasi;

    public Rute(int id, String namaRute, String titikAwal, String titikAkhir, int idTransportasi) {
        this.id = id;
        this.namaRute = namaRute;
        this.titikAwal = titikAwal;
        this.titikAkhir = titikAkhir;
        this.idTransportasi = idTransportasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaRute() {
        return namaRute;
    }

    public void setNamaRute(String namaRute) {
        this.namaRute = namaRute;
    }

    public String getTitikAwal() {
        return titikAwal;
    }

    public void setTitikAwal(String titikAwal) {
        this.titikAwal = titikAwal;
    }

    public String getTitikAkhir() {
        return titikAkhir;
    }

    public void setTitikAkhir(String titikAkhir) {
        this.titikAkhir = titikAkhir;
    }

    public int getIdTransportasi() {
        return idTransportasi;
    }

    public void setIdTransportasi(int idTransportasi) {
        this.idTransportasi = idTransportasi;
    }

    @Override
    public void printInfo() {
        System.out.printf("%d | %s - %s | transportasiId:%d\n", id, titikAwal, titikAkhir, idTransportasi);
    }
}
