package archiverodeproyectos;

public class ProyectoEntidad {
    private int proyectoID; //Identificador INT (10) NOT NULL AUTO_INCREMENT
    private String nomProy; //Nombre del proyecto VARCHAR (50) NOT NULL
    private String fechaInic; //Fecha de inicio del proyect DATE NOT NULL
    private String fechaFin; //Fecha de finalizacion del proyect DATE NULL
    private String descrProy; //Descripción del proyecto TEXT NULL
    //Constructor Default
    public ProyectoEntidad (){
        this.proyectoID = 0;
        this.nomProy = null;
        this.descrProy = null;
        this.fechaInic = null;
        this.fechaFin = null;
    }

    public ProyectoEntidad(int proyectoID, String nomProy, String fechaInic, String fechaFin, String descrProy) {
        this.proyectoID = proyectoID;
        this.nomProy = nomProy;
        this.fechaInic = fechaInic;
        this.fechaFin = fechaFin;
        this.descrProy = descrProy;
    }
    
    public int getProyectoID () {
        return this.proyectoID;
    }

    public String getNomProy() {
        return this.nomProy;
    }

    public String getDescrProy() {
        return this.descrProy;
    }

    public String getFechaInic() {
        return this.fechaInic;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setProyectoID(int proyectoID) {
        this.proyectoID = proyectoID;
    }

    public void setNomProy(String nomProy) {
        this.nomProy = nomProy;
    }

    public void setDescrProy(String descrProy) {
        this.descrProy = descrProy;
    }

    public void setFechaInic(String fechaInic) {
        this.fechaInic = fechaInic;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
