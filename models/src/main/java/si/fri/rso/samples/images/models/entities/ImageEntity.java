package si.fri.rso.samples.images.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
@NamedQueries(value =
        {
                @NamedQuery(name = "ImageEntity.getAll",
                        query = "SELECT u FROM ImageEntity u")
        })
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "foreignKey")
    private Integer foreignKey;

    @Column(name = "entity")
    private String entity;

    @Column(name = "uri", length = 1024)
    private String uri;

    @Column(name = "nsfw")
    private Float nsfw;

    @Column(name = "faces")
    private Integer faces;

    @Column(name = "created")
    private Instant created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Float getNsfw() {
        return nsfw;
    }

    public void setNsfw(Float nsfw) {
        this.nsfw = nsfw;
    }

    public Integer getFaces() {
        return faces;
    }

    public void setFaces(Integer faces) {
        this.faces = faces;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}