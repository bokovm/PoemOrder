package com.poemorder.app.repo;

import com.poemorder.app.domain.review.Review;
import com.poemorder.app.domain.review.ReviewStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Оставляем твои методы — пригодятся для страницы отзывов/админки
    List<Review> findByStatusOrderByCreatedAtDesc(ReviewStatus status);

    List<Review> findByStatusOrderByCreatedAtAsc(ReviewStatus status);

    /**
     * Для главной страницы: последние APPROVED, но с лимитом.
     * Pageable позволяет сделать "top N" без отдельного LIMIT в SQL вручную.
     */
    @Query("""
           select r
           from Review r
           where r.status = com.poemorder.app.domain.review.ReviewStatus.APPROVED
           order by r.createdAt desc
           """)
    List<Review> findApprovedForHomepage(Pageable pageable);
}
