package com.example.webfluxmongo.listingsAndReviews;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.example.webfluxmongo.listingsAndReviews.PageSupport.DEFAULT_PAGE_SIZE;
import static com.example.webfluxmongo.listingsAndReviews.PageSupport.FIRST_PAGE_NUM;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class ListingsAndReviewsController {
    private final ListingsAndReviewsService listingsAndReviewsService;

    public ListingsAndReviewsController(ListingsAndReviewsService listingsAndReviewsService) {
        this.listingsAndReviewsService = listingsAndReviewsService;
    }

    @GetMapping
    public Mono<PageSupport<ListingsAndReviews>> getAllListingsAndReviews(
            @RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        return listingsAndReviewsService.getAllListingsAndReviews(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ListingsAndReviews>> getListingsAndReviewsById(@PathVariable String id) throws InterruptedException {
        return listingsAndReviewsService.findById(id).log().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


//    @GetMapping("/create")
//    public String create() {
//        Customer customer = new Customer();
//        customer.setId("cust001");
//        customer.setName("Chiwa Kantawong");
//        customer.setEmail("kchiwa@gmail.com");
//        customerRepository.save(customer).subscribe(result -> log.info("Entity has been saved: {}", result));
//
//        customer = new Customer();
//        customer.setId("cust002");
//        customer.setName("Pee Kantawong");
//        customer.setEmail("pee@gmail.com");
//        customerRepository.save(customer).subscribe(result -> log.info("Entity has been saved: {}", result));
//        return "Done";
//    }

}
