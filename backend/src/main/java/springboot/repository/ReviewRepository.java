package springboot.repository;

import org.springframework.data.repository.CrudRepository;
import springboot.domain.entity.Review;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Integer> {
    List<Review> findAllByOrderByIdDesc();
}
