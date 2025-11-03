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

    // "07:30 23/10/2025"
    private String jadwalStr;
    // "09:15"
    private String jamTibaStr;

    // --- ctor lengkap (pakai di findById) ---
    public Tiket(int id, int harga, int idJadwal, int idTransportasi, int idRute) {
        this.id = id;
        this.harga = harga;
        this.idJadwal = idJadwal;
        this.idTransportasi = idTransportasi;
        this.idRute = idRute;
    }

    // --- ctor kosong (dipakai getAllWithDetail) ---
    public Tiket() {}

    // ===== getters =====
    public int getId() { return id; }
    public int getHarga() { return harga; }
    public int getIdJadwal() { return idJadwal; }
    public int getIdTransportasi() { return idTransportasi; }
    public int getIdRute() { return idRute; }

    public String getTitikAwal() { return titikAwal; }
    public String getTitikAkhir() { return titikAkhir; }
    public String getJenisTransportasi() { return jenisTransportasi; }
    public String getNamaTransportasi() { return namaTransportasi; }
    public String getJadwalStr() { return jadwalStr; }
    public String getJamTibaStr() { return jamTibaStr; }

    // ===== setters (WAJIB ada, jangan lempar exception) =====
    public void setId(int id) { this.id = id; }
    public void setHarga(int harga) { this.harga = harga; }
    public void setIdJadwal(int idJadwal) { this.idJadwal = idJadwal; }
    public void setIdTransportasi(int idTransportasi) { this.idTransportasi = idTransportasi; }
    public void setIdRute(int idRute) { this.idRute = idRute; }

    public void setTitikAwal(String titikAwal) { this.titikAwal = titikAwal; }
    public void setTitikAkhir(String titikAkhir) { this.titikAkhir = titikAkhir; }
    public void setJenisTransportasi(String jenisTransportasi) { this.jenisTransportasi = jenisTransportasi; }
    public void setNamaTransportasi(String namaTransportasi) { this.namaTransportasi = namaTransportasi; }
    public void setJadwalStr(String jadwalStr) { this.jadwalStr = jadwalStr; }
    public void setJamTibaStr(String jamTibaStr) { this.jamTibaStr = jamTibaStr; }

    // Bantuan untuk set tampilan sekaligus (dipakai di findById)
    public void setDisplayInfo(String titikAwal, String titikAkhir,
                               String jenisTransportasi, String namaTransportasi,
                               String jadwalStr) {
        this.titikAwal = titikAwal;
        this.titikAkhir = titikAkhir;
        this.jenisTransportasi = jenisTransportasi;
        this.namaTransportasi = namaTransportasi;
        this.jadwalStr = jadwalStr;
    }

    @Override
    public void printInfo() {
        String rute = (titikAwal == null ? "-" : titikAwal) + " - " +
                      (titikAkhir == null ? "-" : titikAkhir);
        String trans = (jenisTransportasi == null ? "-" : jenisTransportasi) + " " +
                       (namaTransportasi == null ? "-" : namaTransportasi);
        String jad = (jadwalStr == null ? "-" : jadwalStr);
        System.out.printf("%-3d | %-25s | %-35s | Rp%-8d | %-20s%n",
                id, rute, trans, harga, jad);
    }
}
