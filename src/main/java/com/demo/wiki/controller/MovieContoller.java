package com.demo.wiki.controller;

import com.demo.wiki.entity.Movie;
import com.demo.wiki.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class MovieContoller {
    @Autowired
    MovieRepo movieRepo;
    @GetMapping("/movieList")
    public ResponseEntity<?> getAllMovie(){

        return  new ResponseEntity<>(movieRepo.findAll(), HttpStatus.OK);
    }
    @GetMapping("/movieByTitle/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title){

        return  new ResponseEntity<Movie>(movieRepo.findByTitle(title), HttpStatus.OK);
    }
    @GetMapping("/getMovie/{sortBy}/{OrderBy}")
    public ResponseEntity<?> getMovieDescendingOrder(@PathVariable String sortBy,
                                                     @PathVariable String OrderBy){
        List<Movie> movieList = movieRepo.findAll();
        if(sortBy.equals("title")) {
            if(OrderBy.equals("asc")) {
                TitleComparator tc = new TitleComparator();
                Collections.sort(movieList, tc);
                return new ResponseEntity<>(movieList, HttpStatus.OK);
            }
            if(OrderBy.equals("desc")) {
                TitleComparator comparator = new TitleComparator();
                Collections.sort(movieList, comparator.reversed());
                return new ResponseEntity<>(movieList, HttpStatus.OK);
            }
        }
return new ResponseEntity<>(movieList,HttpStatus.OK);
    }
}
class TitleComparator implements Comparator<Movie>
{
    public int compare(Movie ma, Movie mb)
    {
        return ma.getTitle().compareTo(mb.getTitle());
    }
}

