package si.fri.rso.samples.images.models.converters;

import si.fri.rso.samples.images.lib.Image;
import si.fri.rso.samples.images.models.entities.ImageEntity;

public class ImageConverter {

    public static Image toDto(ImageEntity entity) {

        Image dto = new Image();
        dto.setImageId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setForeignKey(entity.getForeignKey());
        dto.setEntity(entity.getEntity());
        dto.setUri(entity.getUri());

        return dto;

    }

    public static ImageEntity toEntity(Image dto) {

        ImageEntity entity = new ImageEntity();
        entity.setCreated(dto.getCreated());
        entity.setForeignKey(dto.getForeignKey());
        entity.setEntity(dto.getEntity());
        entity.setUri(dto.getUri());

        return entity;

    }

}
