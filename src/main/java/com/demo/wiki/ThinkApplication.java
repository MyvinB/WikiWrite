package com.demo.wiki;

import com.demo.wiki.entity.Movie;
import com.demo.wiki.repository.MovieRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThinkApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ThinkApplication.class, args);
	}
	@Autowired
    MovieRepo movieRepo;
	Movie movie = new Movie();
	@Override
	public void run(String... args) throws Exception {
	Document movieListDoc=	Jsoup.connect("https://en.wikipedia.org/w/api.php?format=xml&action=query&list=embeddedin&einamespace=0&eilimit=60&eititle=Template:Infobox_film").get();
	Elements movieListElemnets = movieListDoc.select("api").select("query").select("embeddedin").select("ei");

	for (Element movie1:movieListElemnets){
		String movieUrl=movie1.attr("title");
		Movie movie = new Movie();
		movie.setTitle(movieUrl);
		Document htmldoc =Jsoup.connect("https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=xml&page="+movieUrl).get();

		Elements elements = htmldoc.select("api").select("parse").select("text");

		Document doc=Jsoup.parse(elements.text());
		Elements table=doc.select("table");

		for (Element row : table.select("tr")) {
			if(row.select("th").text().equals("Directed by")){
				movie.setDirector(row.select("td").text());
			};
			if(row.select("th").text().equals("Music by")){
				movie.setMusicComposer(row.select("td").text());
			};
			if(row.select("th").text().equals("Production companies"))
				movie.setProductionHouse(row.select("td").text());

			if(row.select("th").text().equals("Produced by"))
				movie.setProducer(row.select("td").text());

			if(row.select("th").text().equals("Starring"))
				movie.setActor(row.select("td").text());
			if(row.select("th").text().equals("Release date"))
				movie.setReleaseDate(row.select("td").text());

			if(row.select("th").text().equals("Running time")){
				movie.setDuration(row.select("td").text());
			};
			if(row.select("th").text().equals("Language")){
				movie.setLanguage(row.select("td").text());
			};
			if(row.select("th").text().equals("Budget")){
				movie.setBudget(row.select("td").text());
			};
			if(row.select("th").text().equals("Box office"))
				movie.setBudgetOfficeCollection(row.select("td").text());


		}
		movieRepo.save(movie);
	}



	}
}
