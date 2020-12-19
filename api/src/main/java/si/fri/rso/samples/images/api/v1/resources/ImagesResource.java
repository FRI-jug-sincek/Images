package si.fri.rso.samples.images.api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.net.URI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import si.fri.rso.samples.images.config.AppProperties;
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

    @Inject
    private AppProperties appProperties;

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

        if ((image.getForeignKey() == null || image.getEntity() == null || image.getUri() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            if (appProperties.getUseApis()) {
                image.setFaces(this.getNumberOfFaces(image.getUri()));
                image.setNsfw(this.checkIfNsfw(image.getUri()));
            } else {
                image.setFaces(0);
                image.setNsfw((float) 0);
            }
            image = imagesBean.createImage(image);
        }

        return Response.status(Response.Status.OK).entity(image).build();
    }

    @PUT
    @Path("{imageId}")
    public Response putImage(@PathParam("imageId") Integer imageId, Image image) {
        if (appProperties.getUseApis()) {
            image.setFaces(this.getNumberOfFaces(image.getUri()));
            image.setNsfw(this.checkIfNsfw(image.getUri()));
        } else {
            image.setFaces(0);
            image.setNsfw((float) 0);
        }

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

    public Integer getNumberOfFaces(String url) {
        // make an api call to get number of faces in the image
        HttpResponse<String> response = Unirest.post("https://face-detection6.p.rapidapi.com/img/face")
                .header("content-type", "application/json")
                .header("x-rapidapi-key", appProperties.getRecognitionKey()) //moj kluč ne ga leakat
                .header("x-rapidapi-host", "face-detection6.p.rapidapi.com")
                .body("{\"url\": \"" + url + " \",\"accuracy_boost\": 2 }")
                .asString();

        JsonObject jsonObject = new JsonParser().parse(response.getBody().toString()).getAsJsonObject();
        JsonArray obrazi = jsonObject.get("detected_faces").getAsJsonArray(); //seznam obrazov
        return obrazi.size();
    }

    public Float checkIfNsfw(String url) {
        // make an api call to get if image is nsfw
        HttpResponse<String> response = Unirest.post("https://nsfw-image-classification1.p.rapidapi.com/img/nsfw")
                .header("content-type", "application/json")
                .header("x-rapidapi-key", appProperties.getRecognitionKey()) //moj ključ ne ga leakat
                .header("x-rapidapi-host", "nsfw-image-classification1.p.rapidapi.com")
                .body("{\"url\": \""+url+"\"}").asString();

        JsonObject jsonObject = new JsonParser().parse(response.getBody().toString()).getAsJsonObject();
        Float nsfwProb = jsonObject.get("NSFW_Prob").getAsFloat(); //seznam obrazov
        return nsfwProb;
    }
}
