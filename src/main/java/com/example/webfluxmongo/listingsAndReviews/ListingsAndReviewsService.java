package com.example.webfluxmongo.listingsAndReviews;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@Slf4j
public class ListingsAndReviewsService {
    private final ListingsAndReviewsRepository listingsAndReviewsRepository;

    public ListingsAndReviewsService(ListingsAndReviewsRepository listingsAndReviewsRepository) {
        this.listingsAndReviewsRepository = listingsAndReviewsRepository;
    }

//    public Flux<ListingsAndReviews> getAllListingsAndReviews() {
//        Flux<ListingsAndReviews> listingsAndReviews = listingsAndReviewsRepository.findAll();
//        return listingsAndReviews;
//    }

    public Mono<ListingsAndReviews> findById(String id) throws InterruptedException {
        Mono<ListingsAndReviews> listingsAndReviewsMono = listingsAndReviewsRepository.findById(id);
        return listingsAndReviewsMono;
    }

    public Mono<PageSupport<ListingsAndReviews>> getAllListingsAndReviews(Pageable page) {
        return listingsAndReviewsRepository.findAll()
                .collectList().map(
                list -> new PageSupport<>(
                        list.stream().skip(page.getPageNumber() * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList()), page.getPageNumber(), page.getPageSize(), list.size()));
    }
}
