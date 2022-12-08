package com.eagles.booking.api;

import com.eagles.components.utilities.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "bookings", tags = {"bookings"})
@RequestMapping("/api/bookings")
public interface BookingResource {

    @ApiOperation(value = "Get bookings", nickname = "getBookings", notes = "Retrieve a booking list",
            response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to retrieve bookings.", response = BookingApiSchema.class)})
    @GetMapping
    Mono<PageResult<BookingApiSchema>> getBooks(GetBookingsRequest getBookingsRequest);

    @ApiOperation(value = "Post new booking", nickname = "postBooking", notes = "Create a new booking",
            response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success to create a new booking.", response = Void.class)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<BookingApiSchema> postBook(@RequestBody BookingApiSchema postClientRequest);

    @ApiOperation(value = "Get booking", nickname = "getBooking", notes = "Retrieve a booking", response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to load a booking.", response = BookingApiSchema.class)})
    @GetMapping("/{key}")
    Mono<BookingApiSchema> getBook(@PathVariable Long key);

    @ApiOperation(value = "Put booking", nickname = "putBooking", notes = "Update a booking", response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to update a booking.", response = BookingApiSchema.class)})
    @PutMapping("/{key}")
    Mono<BookingApiSchema> putBook(@PathVariable Long key, @RequestBody BookingApiSchema postClientRequest);

    @ApiOperation(value = "Patch booking", nickname = "patchBooking", notes = "Update a booking", response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to update a booking.", response = BookingApiSchema.class)})
    @PatchMapping("/{key}")
    Mono<BookingApiSchema> patchBook(@PathVariable Long key, @RequestBody BookingApiSchema postClientRequest);

    @ApiOperation(value = "Delete booking", nickname = "deleteBooking", notes = "Delete a booking", response = BookingApiSchema.class, tags = {"bookings"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success to delete a booking.", response = Void.class)})
    @DeleteMapping("/{key}")
    Mono<Void> deleteBook(@PathVariable Long key);

}
