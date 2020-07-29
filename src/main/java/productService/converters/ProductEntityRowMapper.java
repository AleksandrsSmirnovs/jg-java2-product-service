package productService.converters;

import org.springframework.jdbc.core.RowMapper;
import productService.domain.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductEntityRowMapper implements RowMapper<ProductEntity> {

    @Override
    public ProductEntity mapRow(ResultSet resultSet, int i) throws SQLException {

        return new ProductEntity.ProductBuilder(
                resultSet.getString("name"), resultSet.getBigDecimal("price"))
                .buildId(resultSet.getLong("id"))
                .buildCategory(resultSet.getString("category"))
                .buildDiscount(resultSet.getBigDecimal("discount"))
                .buildDescription(resultSet.getString("description"))
                .build();
    }
}
