package org.apache.pricecontroller.impl;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Log4j2
@Component
public class HtmlParser {

    private Document dom;

    public HtmlParser(@Value("${page.url}") String pageUrl) {
        try {
            dom = Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load page " + pageUrl, e);
        }
    }

    public Element getElementById(String id) {
        return dom.getElementById(id);
    }

    public Element getElementById(Element element, String id) {
        return element.getElementById(id);
    }

    public List<Element> getElementsByTag(Element element, String tag) {
        return element.getElementsByTag(tag);
    }
}
