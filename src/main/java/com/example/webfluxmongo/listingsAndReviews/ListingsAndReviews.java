package com.example.webfluxmongo.listingsAndReviews;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "listingsAndReviews")
public class ListingsAndReviews {

    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String listing_url;

}
