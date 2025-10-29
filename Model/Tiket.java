package Model;

public class Tiket implements Cetak {
    private int id;
    private int harga;
    private int idJadwal;
    private int idTransportasi;
    private int idRute;

    private String titikAwal;
    private String titikAkhir;
    private String jenisTransportasi;
    private String namaTransportasi;
    private String jadwalStr;

    public Tiket(int id, int harga, int idJadwal, int idTransportasi, int idRute) {
        this.id = id;
        this.harga = harga;
        this.idJadwal = idJadwal;
        this.idTransportasi = idTransportasi;
        this.idRute = idRute;
    }

    public int getId() { return id; }
    public int getHarga() { return harga; }
    public int getIdJadwal() { return idJadwal; }
    public int getIdTransportasi() { return idTransportasi; }
    public int getIdRute() { return idRute; }

    public void setDisplayInfo(String titikAwal, String titikAkhir, String jenisTransportasi, String namaTransportasi, String jadwalStr) {
        this.titikAwal = titikAwal;
        this.titikAkhir = titikAkhir;
        this.jenisTransportasi = jenisTransportasi;
        this.namaTransportasi = namaTransportasi;
        this.jadwalStr = jadwalStr;
    }

    public String getTitikAwal() { return titikAwal; }
    public String getTitikAkhir() { return titikAkhir; }
    public String getJenisTransportasi() { return jenisTransportasi; }
    public String getNamaTransportasi() { return namaTransportasi; }
    public String getJadwalStr() { return jadwalStr; }

    @Override
    public void printInfo() {
        String rute = (titikAwal == null ? "-" : titikAwal) + " - " + (titikAkhir == null ? "-" : titikAkhir);
        String trans = (jenisTransportasi == null ? "-" : jenisTransportasi + " ") + (namaTransportasi == null ? "-" : namaTransportasi);
        String jad = jadwalStr == null ? "-" : jadwalStr;
        System.out.printf("%-3d | %-25s | %-35s | Rp%-8d | %-20s\n", id, rute, trans, harga, jad);
    }
}
