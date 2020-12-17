package si.fri.rso.samples.images.lib;

import java.time.Instant;

public class Image {

    private Integer imageId;
    private Integer foreignKey;
    private String entity;
    private String uri;
    private Float nsfw;
    private Integer faces;
    private Instant created;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
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
