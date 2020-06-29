package converters;

import domain.ProductCategory;
import domain.ProductEntity;
import dto.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductDtoEntityConverter {

    public ProductEntity toEntity(ProductDto dto) {
        if (dto == null) throw new ProductConvertionException("Can't convert null.");
        if (dto.getName() == null) throw new ProductConvertionException("Name must be not null.");
        if (dto.getPrice() == null) throw new ProductConvertionException("Price must be not null.");
        return new ProductEntity.ProductBuilder(dto.getName(), dto.getPrice())
                .buildId(dto.getId())
                .buildCategory(dto.getCategory() == null ? ProductCategory.UNDEFINED : dto.getCategory())
                .buildDiscount(dto.getDiscount() == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : dto.getDiscount())
                .buildDescription(dto.getDescription() == null ? "" : dto.getDescription())
                .build();
    }

    public ProductDto toDto(ProductEntity entity) {
        if (entity == null) throw new ProductConvertionException("Item not found.");
        return new ProductDto.Builder()
                .buildId(entity.getId())
                .buildName(entity.getName())
                .buildPrice(entity.getPrice().setScale(2, RoundingMode.HALF_UP))
                .buildCategory(entity.getCategory() == null ? ProductCategory.UNDEFINED : entity.getCategory())
                .buildDiscount(entity.getDiscount() == null ? BigDecimal.ZERO : entity.getDiscount().setScale(2, RoundingMode.HALF_UP))
                .buildDescription(entity.getDescription() == null ? "" : entity.getDescription())
                .build();
    }
}
