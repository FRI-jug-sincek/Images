package si.fri.rso.samples.images.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.samples.images.lib.Image;
import si.fri.rso.samples.images.models.converters.ImageConverter;
import si.fri.rso.samples.images.models.entities.ImageEntity;


@RequestScoped
public class ImagesBean {

    private Logger log = Logger.getLogger(ImagesBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Image> getImages() {

        TypedQuery<ImageEntity> query = em.createNamedQuery(
                "ImageEntity.getAll", ImageEntity.class);

        List<ImageEntity> resultList = query.getResultList();

        return resultList.stream().map(ImageConverter::toDto).collect(Collectors.toList());
    }

    public List<Image> getImagesFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, ImageEntity.class, queryParameters).stream()
                .map(ImageConverter::toDto).collect(Collectors.toList());
    }

    public Image getImage(Integer id) {

        ImageEntity imageEntity = em.find(ImageEntity.class, id);

        if (imageEntity == null) {
            throw new NotFoundException();
        }

        Image image = ImageConverter.toDto(imageEntity);

        return image;
    }

    public Image createImage(Image image) {

        ImageEntity imageEntity = ImageConverter.toEntity(image);

        try {
            beginTx();
            em.persist(imageEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (imageEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ImageConverter.toDto(imageEntity);
    }

    public Image putImage(Integer id, Image image) {

        ImageEntity c = em.find(ImageEntity.class, id);

        if (c == null) {
            return null;
        }

        ImageEntity updatedImageEntity = ImageConverter.toEntity(image);

        try {
            beginTx();
            updatedImageEntity.setId(c.getId());
            updatedImageEntity = em.merge(updatedImageEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ImageConverter.toDto(updatedImageEntity);
    }

    public boolean deleteImage(Integer id) {

        ImageEntity image = em.find(ImageEntity.class, id);

        if (image != null) {
            try {
                beginTx();
                em.remove(image);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
