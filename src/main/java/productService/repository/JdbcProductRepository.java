package productService.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import productService.domain.ProductCategory;
import productService.domain.ProductEntity;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>();
    }

    @Override
    public ProductEntity findByID(Long id) {
        return null;
    }

    @Override
    public void save(ProductEntity entity) {
        String query = "INSERT INTO product (category, name, price, discount, description) VALUES (?, ?, ?, ? ,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getCategory());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.setBigDecimal(4, entity.getDiscount());
            preparedStatement.setString(5, entity.getDescription());
            return preparedStatement;
        }, keyHolder);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<String> getNameList() {
        return null;
    }
}
