package fh.fa.data;

import fh.fa.data.model.Material;
import fh.fa.data.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AppRouter {

    @Autowired
    private MaterialRepository materialRepository;

    @Bean
    RouterFunction<ServerResponse> updateEmployeeRoute() {
        return RouterFunctions.route()
                              .GET("/hello", request -> ok().syncBody("Hello World!"))
                              .GET("/hello2", request -> ok().syncBody("Hello World 2!"))

                              .GET("/materials", request -> ok().contentType(APPLICATION_JSON).body(Flux.fromIterable(materialRepository.findAll()), Material.class))
                              .POST("/materials", request -> ok().contentType(APPLICATION_JSON)
                                                                 .body(Mono.just(materialRepository.save(request.bodyToMono(Material.class).block())), Material.class))

                              .DELETE("/materials/{id}", serverRequest -> ok().contentType(APPLICATION_JSON)
                                                                              .body(Mono.just(materialRepository.findById(Long.valueOf(serverRequest.pathVariable("id"))))
                                                                                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Not found")))
                                                                                        .map(Optional::get)
                                                                                        .doOnNext(material -> materialRepository.deleteById(
                                                                                                Long.valueOf(serverRequest.pathVariable("id"))))
                                                                                      , Material.class))
                              .build();
    }
}