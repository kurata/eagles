package com.eagles.components.utilities;

import org.springframework.data.domain.Pageable;

public interface PagingRequest {

    Pageable toPageRequest();

}
