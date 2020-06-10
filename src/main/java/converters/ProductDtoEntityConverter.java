package converters;

import domain.ProductEntity;
import dto.ProductDto;

public class ProductDtoEntityConverter {

    public ProductEntity toEntity(ProductDto dto) {
        if (dto == null) throw new ProductConvertionException("Can't convert null.");
        if (dto.getName() == null) throw new ProductConvertionException("Name must be not null.");
        if (dto.getPrice() == null) throw new ProductConvertionException("Price must be not null.");
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
