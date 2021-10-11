package az.hrportal.hrportalapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class OffsetBasedPageRequest implements Pageable {

    private int limit;
    private int offset;
    private String sortDirection;
    private String[] sortParams;

    public OffsetBasedPageRequest(int skip, int offset) {
        if (skip < 0)
            throw new IllegalArgumentException("Skip must not be less than zero!");

        if (offset < 0)
            throw new IllegalArgumentException("Offset must not be less than zero!");

        this.limit = offset;
        this.offset = skip;
    }

    public OffsetBasedPageRequest(int skip, int offset, String sortDirection, String[] sortParams) {
        if (skip < 0)
            throw new IllegalArgumentException("Skip must not be less than zero!");

        if (offset < 0)
            throw new IllegalArgumentException("Offset must not be less than zero!");

        this.limit = offset;
        this.offset = skip;
        this.sortDirection = sortDirection;
        this.sortParams = sortParams;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
       return Sort.by(Sort.Direction.fromString(sortDirection),sortParams);
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    public Pageable first() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }
}