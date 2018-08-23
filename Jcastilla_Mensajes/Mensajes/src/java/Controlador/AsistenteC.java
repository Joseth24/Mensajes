package Controlador;

import DAO.AsistenteD;
import Modelo.AsistenteM;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;

@Named(value = "asistenteC")
@SessionScoped
@Data
public class AsistenteC implements Serializable {

    private AsistenteM asistente = new AsistenteM();
    private List<AsistenteM> lstAsistente;
    private String accion = "Defecto";

    @PostConstruct
    public void Iniciar() {
        try {
            Listar();
        } catch (Exception e) {
        }
    }

    public void operar() throws Exception {
        switch (accion) {
            case "Registrar":
                this.guardar();
                break;
            case "Modificar":
                this.modificar();
                break;
        }
    }

    public void Limpiar() {
        asistente = new AsistenteM();
    }

    private void guardar() throws Exception {
        AsistenteD DAO;
        try {
            DAO = new AsistenteD();
            DAO.registrar(asistente);
            Limpiar();
            Listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("AGREGADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR"));
            throw e;
        }
    }

    public void modificar() throws Exception {
        AsistenteD DAO;
        try {
            DAO = new AsistenteD();
            DAO.Modificar(asistente);
            Listar();
            Limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ACTUALIZADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR"));
            throw e;
        }
    }

    public void eliminar(AsistenteM asis) throws Exception {
        AsistenteD DAO;
        try {
            DAO = new AsistenteD();
            DAO.Eliminar(asis);
            Listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ELIMINADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR"));
            throw e;
        }
    }

    public void Listar() throws Exception {
        AsistenteD DAO;
        try {
            DAO = new AsistenteD();
            lstAsistente = DAO.Listar();
        } catch (Exception e) {
            throw e;
        }

    }

    public void leerID(String Codigo) throws Exception {
        AsistenteD DAO;
        try {
            DAO = new AsistenteD();
            this.asistente = DAO.LeerId(Codigo);
            this.accion = "Modificar";
        } catch (Exception e) {
            throw e;
        }
    }

}
