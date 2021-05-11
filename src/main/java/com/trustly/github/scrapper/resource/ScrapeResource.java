package com.trustly.github.scrapper.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.trustly.github.scrapper.domain.FilesGroupedByExtension;
import com.trustly.github.scrapper.service.ScrapeService;

import java.util.List;

@Path("/scrape")
public class ScrapeResource {

    private final ScrapeService scrapeService;

    public ScrapeResource(ScrapeService scrapeService) {
        this.scrapeService = scrapeService;
    }

    @GET
    public List<FilesGroupedByExtension> scrape(@QueryParam("uri") String path) {
        return scrapeService.scrape(path);
    }

}
