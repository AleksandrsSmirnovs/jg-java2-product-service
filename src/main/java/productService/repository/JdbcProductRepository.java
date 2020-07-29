package productService.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import productService.converters.ProductEntityRowMapper;
import productService.domain.ProductEntity;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductEntity> findAll() {
        String query = "SELECT * FROM product";
        return jdbcTemplate.query(query, new ProductEntityRowMapper());
    }

    @Override
    public ProductEntity findByID(Long id) {
        String query = "SELECT * FROM product WHERE id = ?";
        List<ProductEntity> resultList = jdbcTemplate.query(query, new Object[]{id}, new ProductEntityRowMapper());
        return resultList.isEmpty() ? null : resultList.get(0);
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
        String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
    }

    @Override
    public List<String> getNameList() {
        String query = "SELECT name FROM product";
        return jdbcTemplate.queryForList(query, new Object[]{}, String.class);
    }

    @Override
    public void changeDiscount(ProductEntity entity, BigDecimal discount) {
        String query = "UPDATE product SET discount = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, discount);
            preparedStatement.setLong(2, entity.getId());
            return preparedStatement;
        });
    }
}
