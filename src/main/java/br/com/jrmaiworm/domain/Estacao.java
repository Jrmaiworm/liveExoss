package br.com.jrmaiworm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estacao.
 */
@Entity
@Table(name = "estacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Estacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "associado")
    private String associado;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "lente")
    private String lente;

    @Column(name = "camera")
    private String camera;

    @Column(name = "fov")
    private Double fov;

    @Column(name = "kml")
    private String kml;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "site")
    private String site;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "pareamento")
    private String pareamento;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "estacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estacao" }, allowSetters = true)
    private Set<Captura> capturas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Estacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAssociado() {
        return this.associado;
    }

    public Estacao associado(String associado) {
        this.setAssociado(associado);
        return this;
    }

    public void setAssociado(String associado) {
        this.associado = associado;
    }

    public String getEmail() {
        return this.email;
    }

    public Estacao email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Estacao telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Estacao cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public Estacao estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLente() {
        return this.lente;
    }

    public Estacao lente(String lente) {
        this.setLente(lente);
        return this;
    }

    public void setLente(String lente) {
        this.lente = lente;
    }

    public String getCamera() {
        return this.camera;
    }

    public Estacao camera(String camera) {
        this.setCamera(camera);
        return this;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Double getFov() {
        return this.fov;
    }

    public Estacao fov(Double fov) {
        this.setFov(fov);
        return this;
    }

    public void setFov(Double fov) {
        this.fov = fov;
    }

    public String getKml() {
        return this.kml;
    }

    public Estacao kml(String kml) {
        this.setKml(kml);
        return this;
    }

    public void setKml(String kml) {
        this.kml = kml;
    }

    public Double getLat() {
        return this.lat;
    }

    public Estacao lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public Estacao lng(Double lng) {
        this.setLng(lng);
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSite() {
        return this.site;
    }

    public Estacao site(String site) {
        this.setSite(site);
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public Estacao ativa(Boolean ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getPareamento() {
        return this.pareamento;
    }

    public Estacao pareamento(String pareamento) {
        this.setPareamento(pareamento);
        return this;
    }

    public void setPareamento(String pareamento) {
        this.pareamento = pareamento;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Estacao user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Captura> getCapturas() {
        return this.capturas;
    }

    public void setCapturas(Set<Captura> capturas) {
        if (this.capturas != null) {
            this.capturas.forEach(i -> i.setEstacao(null));
        }
        if (capturas != null) {
            capturas.forEach(i -> i.setEstacao(this));
        }
        this.capturas = capturas;
    }

    public Estacao capturas(Set<Captura> capturas) {
        this.setCapturas(capturas);
        return this;
    }

    public Estacao addCaptura(Captura captura) {
        this.capturas.add(captura);
        captura.setEstacao(this);
        return this;
    }

    public Estacao removeCaptura(Captura captura) {
        this.capturas.remove(captura);
        captura.setEstacao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estacao)) {
            return false;
        }
        return id != null && id.equals(((Estacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", associado='" + getAssociado() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", lente='" + getLente() + "'" +
            ", camera='" + getCamera() + "'" +
            ", fov=" + getFov() +
            ", kml='" + getKml() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", site='" + getSite() + "'" +
            ", ativa='" + getAtiva() + "'" +
            ", pareamento='" + getPareamento() + "'" +
            "}";
    }
}
