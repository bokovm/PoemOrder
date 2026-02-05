package com.poemorder.app.service;

import com.poemorder.app.domain.poem.PortfolioItem;
import com.poemorder.app.domain.poem.PoemStatus;
import com.poemorder.app.repo.PortfolioRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository repo;

    public PortfolioService(PortfolioRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<PortfolioItem> publishedForHomepage(int limit) {
        return repo.findByStatusOrderByUpdatedAtDesc(PoemStatus.PUBLISHED, PageRequest.of(0, limit));
    }

    @Transactional(readOnly = true)
    public List<PortfolioItem> publishedAll() {
        return repo.findByStatusOrderByUpdatedAtDesc(PoemStatus.PUBLISHED);
    }

    @Transactional(readOnly = true)
    public List<PortfolioItem> adminAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public PortfolioItem get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Portfolio item not found: " + id));
    }

    @Transactional
    public PortfolioItem save(PortfolioItem item) {
        item.setUpdatedAt(Instant.now());
        return repo.save(item);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
