package modelo;

public class Vuelo {
    private String codigo;      
    private String aerolinea;
    private String origen;      
    private String destino;     
    private String fechaSalida; 
    private String horaSalida;  
    private String horaLlegada; 
    private int cupos;          
    private String estado;
    private double precioBase;
    private int idAeronave;

    public Vuelo() {
    }
    public Vuelo(String codigo, String aerolinea, String origen, String destino, 
                 String fechaSalida, String horaSalida, String horaLlegada, int cupos, String estado, double precioBase, int idAeronave) {
        this.codigo = codigo;
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.cupos = cupos;
        this.estado = estado;
        this.precioBase = precioBase;
        this.idAeronave = idAeronave;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
    public String getCodigo() {
        return codigo;
    }

    public int getIdAeronave() {
        return idAeronave;
    }

    public void setIdAeronave(int idAeronave) {
        this.idAeronave = idAeronave;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}