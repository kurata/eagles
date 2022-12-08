package com.eagles.booking.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BookingApiSchema implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    private final Long clientId;

    private final Long roomId;

    @JsonProperty("startDate")
    @NotEmpty(message = "attribute.validation.not_empty.message")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startDate;

    @JsonProperty("endDate")
    @NotEmpty(message = "attribute.validation.not_empty.message")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endDate;

    public BookingApiSchema() {
        this(null, null, null, null, null);
    }

}
