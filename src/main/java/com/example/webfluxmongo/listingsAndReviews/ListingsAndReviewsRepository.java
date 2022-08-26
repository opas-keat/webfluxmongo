package com.example.webfluxmongo.listingsAndReviews;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingsAndReviewsRepository extends ReactiveMongoRepository<ListingsAndReviews, String> {
}
