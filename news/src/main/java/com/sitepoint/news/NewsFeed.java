package com.sitepoint.news;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import javax.inject.Inject;
//import javax.inject.Named;
//import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.Maps;
import com.atlassian.templaterenderer.TemplateRenderer;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;

public class NewsFeed extends HttpServlet {
    private final TemplateRenderer templateRenderer;

    public NewsFeed(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    private static final Logger log = LoggerFactory.getLogger(NewsFeed.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {
        Map<String, Object> context = Maps.newHashMap();

        try {
            String rawURL = "https://www.sitepoint.com/feed";
            URL feedUrl = new URL(rawURL);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List entries = feed.getEntries();

            context.put("entries", entries);
            resp.setContentType("text/html");

            templateRenderer.render("admin.vm", context, resp.getWriter());

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

}