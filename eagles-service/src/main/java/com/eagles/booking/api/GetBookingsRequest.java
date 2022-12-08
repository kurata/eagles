package com.eagles.booking.api;

import com.eagles.components.utilities.PagingRequest;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class GetBookingsRequest implements PagingRequest {

    private Integer page;

    private Integer size;

    public GetBookingsRequest() {
        this(0, 10);
    }

    public GetBookingsRequest(final Integer page, final Integer size) {
        this.page = page;
        this.size = size;
    }

    @Override
    public Pageable toPageRequest() {
        return PageRequest.of(this.page, this.size);
    }

}
