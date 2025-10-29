package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Jadwal implements Cetak {
    private int id;
    private LocalDateTime jamBerangkat;
    private LocalDateTime jamTiba;
    private String tanggal; // yyyy-MM-dd
    private int idRute;

    public Jadwal(int id, LocalDateTime jamBerangkat, LocalDateTime jamTiba, String tanggal, int idRute) {
        this.id = id;
        this.jamBerangkat = jamBerangkat;
        this.jamTiba = jamTiba;
        this.tanggal = tanggal;
        this.idRute = idRute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getJamBerangkat() {
        return jamBerangkat;
    }

    public void setJamBerangkat(LocalDateTime jamBerangkat) {
        this.jamBerangkat = jamBerangkat;
    }

    public LocalDateTime getJamTiba() {
        return jamTiba;
    }

    public void setJamTiba(LocalDateTime jamTiba) {
        this.jamTiba = jamTiba;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getIdRute() {
        return idRute;
    }

    public void setIdRute(int idRute) {
        this.idRute = idRute;
    }   

    @Override
    public void printInfo() {
        String jb = jamBerangkat == null ? "-" : jamBerangkat.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        String jt = jamTiba == null ? "-" : jamTiba.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        System.out.printf("%-3d | %s -> %s | Tanggal: %s | RuteId:%d\n", id, jb, jt, tanggal, idRute);
    }
}
