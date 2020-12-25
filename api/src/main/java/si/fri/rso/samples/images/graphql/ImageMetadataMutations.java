package si.fri.rso.samples.images.graphql;


import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.samples.images.lib.Image;
import si.fri.rso.samples.images.services.beans.ImagesBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class ImageMetadataMutations {

    @Inject
    private ImagesBean imagesBean;

    @GraphQLMutation
    public Image addImage(@GraphQLArgument(name = "image") Image image) {
        imagesBean.createImage(image);
        return image;
    }

    @GraphQLMutation
    public DeleteResponse deleteImage(@GraphQLArgument(name = "imageId") Integer id) {
        return new DeleteResponse(imagesBean.deleteImage(id));
    }

}