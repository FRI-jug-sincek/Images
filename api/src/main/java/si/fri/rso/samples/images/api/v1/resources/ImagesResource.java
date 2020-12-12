package si.fri.rso.samples.images.api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

import si.fri.rso.samples.images.lib.Image;
import si.fri.rso.samples.images.services.beans.ImagesBean;

@ApplicationScoped
@Path("/images")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ImagesResource {

    private Logger log = Logger.getLogger(ImagesResource.class.getName());

    @Inject
    private ImagesBean imagesBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getImages() {

        List<Image> images = imagesBean.getImages();

        return Response.status(Response.Status.OK).entity(images).build();
    }

    @GET
    @Path("/filtered")
    public Response getImagesFiltered() {

        List<Image> images;

        images = imagesBean.getImagesFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(images).build();
    }

    @GET
    @Path("/{imageId}")
    public Response getImage(@PathParam("imageId") Integer imageId) {

        Image image = imagesBean.getImage(imageId);

        if (image == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(image).build();
    }

    @POST
    public Response createImage(Image image) {

        if ((image.getName() == null || image.getSurname() == null || image.getAge() == null || image.getLocation() == null )) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            image = imagesBean.createImage(image);
        }

        return Response.status(Response.Status.OK).entity(image).build();

    }

    @PUT
    @Path("{imageId}")
    public Response putImage(@PathParam("imageId") Integer imageId, Image image) {

        image = imagesBean.putImage(imageId, image);

        if (image == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @DELETE
    @Path("{imageId}")
    public Response deleteImage(@PathParam("imageId") Integer imageId) {

        boolean deleted = imagesBean.deleteImage(imageId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
