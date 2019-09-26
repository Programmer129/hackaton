package org.apache.pricecontroller.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.pricecontroller.api.MessageService;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class PriceController {

    private static final String SEARCH_PRDUCT = "Samsung Galaxy Note 10+";

    private final MessageService messageService;
    private final HtmlParser parser;
    private final PriceFileProvider priceFileProvider;

    public PriceController(MessageService messageService,
                           HtmlParser parser,
                           PriceFileProvider priceFileProvider) {
        this.messageService = messageService;
        this.parser = parser;
        this.priceFileProvider = priceFileProvider;
    }

    public void doControl(String from, String to) {
        String newLine = getNewPrice();
        String lastLine = priceFileProvider.getLastPrice();
        String newPrice = newLine.split("\\s+")[0];
        String lastPrice = lastLine == null ? "" : lastLine.split("\\s+")[5];

        if (!newPrice.equals(lastPrice)) {
            boolean messageSent = messageService.send(from, to, "Price changed: Last " + lastPrice + ", new " + newPrice);
            if (!messageSent) {
                log.error("Message not sent " + from + " " + to);
            }
            String logText = LocalDateTime.now() + "    " + SEARCH_PRDUCT + "   " + newLine;
            priceFileProvider.appendPrice(logText);
        } else {
            log.info("Price is same");
        }
    }

    private String getNewPrice() {
        Element element = parser.getElementById("product");

        List<Element> lis = parser.getElementsByTag(element, "li");

        Element product = lis
                .stream()
                .map(li -> parser.getElementsByTag(li, "a").get(0))
                .filter(a -> parser.getElementsByTag(a, "h2").get(0).text().contains(SEARCH_PRDUCT))
                .findAny()
                .orElseThrow(IllegalStateException::new);

        return Optional
                .of(product)
                .map(a -> parser.getElementsByTag(a, "span").get(0))
                .map(span -> parser.getElementsByTag(span, "span").get(0))
                .map(span -> span.text().trim())
                .get();
    }
}
