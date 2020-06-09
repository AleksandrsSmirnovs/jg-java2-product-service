package converters;

import domain.ProductEntity;
import dto.ProductDto;

public class ProductDtoEntityConverter {

    public ProductEntity toEntity(ProductDto dto) {
        return new ProductEntity.ProductBuilder(dto.getName(), dto.getPrice())
                .buildId(dto.getId())
                .buildCategory(dto.getCategory())
                .buildDiscount(dto.getDiscount())
                .buildDescription(dto.getDescription())
                .build();
    }

    public ProductDto toDto (ProductEntity entity) {
        if (entity == null) throw new ProductConvertionException("Item not found.");
        return new ProductDto.Builder()
                .buildId(entity.getId())
                .buildName(entity.getName())
                .buildPrice(entity.getPrice())
                .buildCategory(entity.getCategory())
                .buildDiscount(entity.getDiscount())
                .buildDescription(entity.getDescription())
                .build();
    }
}
