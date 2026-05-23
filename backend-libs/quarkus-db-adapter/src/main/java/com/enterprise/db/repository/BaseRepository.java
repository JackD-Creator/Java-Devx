package com.enterprise.db.repository;

import com.enterprise.db.model.BaseEntity;
import com.enterprise.db.model.PageResult;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<E extends BaseEntity> implements PanacheRepository<E> {

    public PageResult<E> findAllPaginated(int page, int size) {
        long total = count();
        List<E> data = findAll(Sort.descending("createdAt"))
                .page(Page.of(page, size))
                .list();
        return PageResult.of(data, total, page, size);
    }

    public PageResult<E> findByQueryPaginated(String query, int page, int size, Object... params) {
        long total = count(query, params);
        List<E> data = find(query, Sort.descending("createdAt"), params)
                .page(Page.of(page, size))
                .list();
        return PageResult.of(data, total, page, size);
    }

    public Optional<E> findByIdSafe(Long id) {
        return findByIdOptional(id);
    }

    @Transactional
    public E saveOrUpdate(E entity) {
        if (entity.id == null) {
            persist(entity);
        } else {
            entity = getEntityManager().merge(entity);
        }
        return entity;
    }

    @Transactional
    public boolean deleteById(Long id) {
        return deleteById((Object) id);
    }

    public boolean existsById(Long id) {
        return findByIdOptional(id).isPresent();
    }
}
