package si.fri.rso.samples.images.models.converters;

import si.fri.rso.samples.images.lib.Image;
import si.fri.rso.samples.images.models.entities.ImageEntity;

public class ImageConverter {

    public static Image toDto(ImageEntity entity) {

        Image dto = new Image();
        dto.setImageId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLocation(entity.getLocation());

        return dto;

    }

    public static ImageEntity toEntity(Image dto) {

        ImageEntity entity = new ImageEntity();
        entity.setCreated(dto.getCreated());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setLocation(dto.getLocation());

        return entity;

    }

}
