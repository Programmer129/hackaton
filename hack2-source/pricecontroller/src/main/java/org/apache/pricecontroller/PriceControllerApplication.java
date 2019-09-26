package org.apache.pricecontroller;

import lombok.extern.log4j.Log4j2;
import org.apache.pricecontroller.impl.PriceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class PriceControllerApplication implements CommandLineRunner {

    @Autowired
    private PriceController priceController;

    public static void main(String[] args) {
        SpringApplication.run(PriceControllerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        priceController.doControl(args[0], args[1]);
    }
}
