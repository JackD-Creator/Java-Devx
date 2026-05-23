package com.enterprise.theme;

import com.enterprise.theme.model.Theme;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class ThemeService {

    public List<Theme> listAll() {
        return Theme.listAll();
    }

    public Theme findById(Long id) {
        return Theme.<Theme>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Theme not found: " + id));
    }

    public Theme findActive() {
        Theme theme = Theme.findActive();
        if (theme == null) throw new NotFoundException("No active theme found");
        return theme;
    }

    @Transactional
    public Theme create(Theme theme) {
        if (theme.active) deactivateAll();
        theme.persist();
        return theme;
    }

    @Transactional
    public Theme update(Long id, Theme payload) {
        Theme existing = findById(id);
        existing.name = payload.name;
        existing.slug = payload.slug;
        existing.mode = payload.mode;
        existing.tokensJson = payload.tokensJson;
        existing.tenantId = payload.tenantId;
        if (payload.active && !existing.active) deactivateAll();
        existing.active = payload.active;
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Theme existing = findById(id);
        existing.delete();
    }

    @Transactional
    public Theme activate(Long id) {
        deactivateAll();
        Theme theme = findById(id);
        theme.active = true;
        return theme;
    }

    private void deactivateAll() {
        Theme.update("active = false where active = true");
    }
}
