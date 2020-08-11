package productService.converters;

import productService.domain.ProductCategory;
import productService.domain.ProductEntity;
import productService.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ProductDtoEntityConverter {

    public ProductEntity toEntity(ProductDto dto) {
        if (dto == null) throw new ProductConvertionException("Can't convert null.");
        if (dto.getName() == null) throw new ProductConvertionException("Name must be not null.");
        if (dto.getPrice() == null) throw new ProductConvertionException("Price must be not null.");
        return new ProductEntity.ProductBuilder(dto.getName(), dto.getPrice())
                .buildId(dto.getId())
                .buildCategory(dto.getCategory() == null ? null : dto.getCategory().name())
                .buildDiscount(dto.getDiscount())
                .buildDescription(dto.getDescription())
                .build();
    }

    public ProductDto toDto(ProductEntity entity) {
        if (entity == null) throw new ProductConvertionException("Item not found.");
        return new ProductDto.Builder()
                .buildId(entity.getId())
                .buildName(entity.getName())
                .buildPrice(entity.getPrice().setScale(2, RoundingMode.HALF_UP))
                .buildCategory(entity.getCategory() == null ? ProductCategory.UNDEFINED : ProductCategory.valueOf(entity.getCategory()))
                .buildDiscount(entity.getDiscount() == null ? BigDecimal.ZERO : entity.getDiscount().setScale(2, RoundingMode.HALF_UP))
                .buildDescription(entity.getDescription())
                .build();
    }
}
