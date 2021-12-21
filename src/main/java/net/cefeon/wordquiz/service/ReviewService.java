package net.cefeon.wordquiz.service;

import net.cefeon.wordquiz.model.Review;
import net.cefeon.wordquiz.model.WordQuizUser;
import net.cefeon.wordquiz.repository.ReviewRepository;
import net.cefeon.wordquiz.repository.WordQuizUserRepository;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class ReviewService {
    final ReviewRepository reviewRepository;
    final WordQuizUserRepository wordQuizUserRepository;

    public ReviewService(ReviewRepository reviewRepository, WordQuizUserRepository wordQuizUserRepository) {
        this.reviewRepository = reviewRepository;
        this.wordQuizUserRepository = wordQuizUserRepository;
    }

    public Optional<Review> getFirstForCurrentUser() {
        return reviewRepository.findFirstByWordQuizUser_UserName(getAuthenticatedUser().getUserName());
    }

    public Review addOrUpdateForCurrentUser(LocalDateTime date) {
        Review review = Review.builder()
                .date(date)
                .wordQuizUser(getAuthenticatedUser())
                .build();
        getFirstForCurrentUser().ifPresent(x -> {
            if (date.isBefore(x.getDate().plusHours(8))){
                review.setId(x.getId());
            }
        });

        reviewRepository.save(review);
        return review;
    }

    private WordQuizUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<WordQuizUser> optionalWordQuizUser = wordQuizUserRepository.findByUserName(authentication.getName());
        return optionalWordQuizUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + authentication.getName()));
    }
}
