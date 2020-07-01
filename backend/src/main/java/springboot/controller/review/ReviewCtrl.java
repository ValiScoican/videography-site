package springboot.controller.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.domain.entity.Review;
import springboot.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewCtrl {
    @Autowired
    private ReviewRepository repository;

    @GetMapping("/reviews")
    public List<Review> getAllReviews(){
        return repository.findAllByOrderByIdDesc();
    }

    // TODO: Use username.
    @PostMapping("/review/add/{content}/{userId}")
    public void addReview(@PathVariable("content") String content, @PathVariable("userId") Integer userId) {
        Review review = new Review(content, userId);
        repository.save(review);
    }

    @PostMapping("/review/delete/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> byId = repository.findById(reviewId);
        Review review = byId.get();
        repository.delete(review);
    }
}
