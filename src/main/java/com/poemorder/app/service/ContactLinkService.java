package com.poemorder.app.service;

import com.poemorder.app.domain.settings.ContactLink;
import com.poemorder.app.repo.ContactLinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactLinkService {

    private final ContactLinkRepository repo;

    public ContactLinkService(ContactLinkRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<ContactLink> publicList() {
        return repo.findAllByEnabledTrueOrderBySortOrderAsc();
    }

    @Transactional(readOnly = true)
    public List<ContactLink> adminList() {
        return repo.findAllByOrderBySortOrderAsc();
    }

    @Transactional
    public ContactLink upsert(Long id,
                              String label,
                              String value,
                              String href,
                              int sortOrder,
                              boolean enabled) {

        ContactLink entity = (id == null)
                ? new ContactLink()
                : repo.findById(id).orElseThrow(() -> new IllegalArgumentException("ContactLink not found: " + id));

        entity.setLabel(label);
        entity.setValue(value);
        entity.setHref(href);
        entity.setSortOrder(sortOrder);
        entity.setEnabled(enabled);

        return repo.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
