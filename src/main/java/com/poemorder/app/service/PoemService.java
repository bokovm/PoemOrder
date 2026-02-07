package com.poemorder.app.service;

import com.poemorder.app.domain.poem.Poem;
import com.poemorder.app.domain.poem.PoemStatus;
import com.poemorder.app.repo.PoemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoemService {

    private final PoemRepository poemRepository;

    public PoemService(PoemRepository poemRepository) {
        this.poemRepository = poemRepository;
    }

    @Transactional(readOnly = true)
    public List<Poem> listAll() {
        return poemRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Poem getOrThrow(Long id) {
        return poemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Poem not found: " + id));
    }

    @Transactional
    public Poem create(Poem poem) {
        poem.setId(null); // на всякий
        return poemRepository.save(poem);
    }

    @Transactional
    public Poem update(Long id, Poem updated) {
        Poem current = getOrThrow(id);

        current.setTitle(updated.getTitle());
        current.setExcerpt(updated.getExcerpt());
        current.setBody(updated.getBody());
        current.setStatus(updated.getStatus());

        return poemRepository.save(current);
    }

    @Transactional
    public void delete(Long id) {
        poemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Poem> publishedAll() {
        return poemRepository.findAllByStatusOrderByUpdatedAtDesc(PoemStatus.PUBLISHED);
    }

    @Transactional(readOnly = true)
    public List<Poem> publishedForHomepage(int limit) {
        return poemRepository.findAllByStatusOrderByUpdatedAtDesc(PoemStatus.PUBLISHED)
                .stream()
                .limit(limit)
                .toList();
    }
}
