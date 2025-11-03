package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transportasi implements Cetak {
    private int id;
    private String nama;
    private String jenis;
    private int kapasitas;
    private LocalDateTime jadwalKeberangkatan;

    public Transportasi(int id, String nama, String jenis, int kapasitas, LocalDateTime jadwalKeberangkatan) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.kapasitas = kapasitas;
        this.jadwalKeberangkatan = jadwalKeberangkatan;
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public LocalDateTime getJadwalKeberangkatan() {
        return jadwalKeberangkatan;
    }

    public void setJadwalKeberangkatan(LocalDateTime jadwalKeberangkatan) {
        this.jadwalKeberangkatan = jadwalKeberangkatan;
    }
    
    @Override
    public void printInfo() {
        String jam = jadwalKeberangkatan == null ? "-" : jadwalKeberangkatan.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        System.out.printf("%-3d | %-30s | %-8s | Kapasitas:%-3d | %s\n", id, nama, jenis, kapasitas, jam);
    }
}
