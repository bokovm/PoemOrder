package com.poemorder.app.service;

import com.poemorder.app.domain.settings.ContactLink;
import com.poemorder.app.repo.ContactLinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactLinkService {

    private final ContactLinkRepository repo;

    public ContactLinkService(ContactLinkRepository repo) {
        this.repo = repo;
    }

    public List<ContactLink> publicList() {
        return repo.findAllByEnabledTrueOrderBySortOrderAsc();
    }

    public List<ContactLink> adminList() {
        return repo.findAllByOrderBySortOrderAsc();
    }

    public ContactLink get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public ContactLink save(ContactLink c) {
        return repo.save(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
