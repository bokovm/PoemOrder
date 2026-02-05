package com.poemorder.app.repo;

import com.poemorder.app.domain.poem.PortfolioItem;
import com.poemorder.app.domain.poem.PoemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioItem, Long> {
    List<PortfolioItem> findByStatusOrderByUpdatedAtDesc(PoemStatus status, Pageable pageable);
    List<PortfolioItem> findByStatusOrderByUpdatedAtDesc(PoemStatus status);
}
