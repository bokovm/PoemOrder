package com.poemorder.app.service;

import com.poemorder.app.domain.review.Review;
import com.poemorder.app.domain.review.ReviewStatus;
import com.poemorder.app.dto.ReviewForm;
import com.poemorder.app.repo.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository repo;

    public ReviewService(ReviewRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void createPending(ReviewForm form) {
        Review r = new Review();
        r.setName(form.getName().trim());
        r.setText(form.getText().trim());

        String tg = form.getTelegramUsername();
        if (tg != null) {
            tg = tg.trim();
            if (tg.startsWith("@")) tg = tg.substring(1);
            if (tg.isBlank()) tg = null;
        }

        r.setTelegramUsername(tg);
        r.setTelegramPublic(form.isTelegramPublic());

        r.setStatus(ReviewStatus.PENDING);
        r.setCreatedAt(Instant.now());

        repo.save(r);
    }

    @Transactional(readOnly = true)
    public List<Review> getApproved() {
        return repo.findByStatusOrderByCreatedAtDesc(ReviewStatus.APPROVED);
    }

    @Transactional(readOnly = true)
    public List<Review> getPending() {
        return repo.findByStatusOrderByCreatedAtAsc(ReviewStatus.PENDING);
    }

    @Transactional
    public void approve(Long id) {
        Review r = repo.findById(id).orElseThrow();
        r.setStatus(ReviewStatus.APPROVED);
        repo.save(r);
    }

    @Transactional
    public void hideContact(Long id) {
        Review r = repo.findById(id).orElseThrow();
        r.setTelegramUsername(null);
        r.setTelegramPublic(false);
        repo.save(r);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Review> findApprovedForHomepage(int limit) {
        return repo.findApprovedForHomepage(PageRequest.of(0, limit));
    }
}
