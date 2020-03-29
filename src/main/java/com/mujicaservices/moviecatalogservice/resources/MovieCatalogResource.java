package com.mujicaservices.moviecatalogservice.resources;

import com.mujicaservices.moviecatalogservice.models.CatalogItem;
import com.mujicaservices.moviecatalogservice.models.Movie;
import com.mujicaservices.moviecatalogservice.models.Rating;
import com.mujicaservices.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${ratings-data-service.url}")
    private String ratingsServiceURL;

    @Value("${movie-info-service.url}")
    private String movieInfoServiceURL;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating ratings = restTemplate.getForObject("http://" + ratingsServiceURL + "/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://" + movieInfoServiceURL + "/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "Awesome movie", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}



//                    Movie movie = webClientBuilder.build()
//                            .get()
//                            .uri("http://localhost:8083/movies/" + rating.getMovieId())
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();
