package com.chibuisi.dailyinsightservice.featuredItems.controller;

import com.chibuisi.dailyinsightservice.featuredItems.service.FeaturedItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeaturedItemsController {

    @Autowired
    private FeaturedItemsService featuredItemsService;

    @GetMapping("/featuredItems")
    public ResponseEntity<?> getFeaturedItems() {
        return new ResponseEntity<>(featuredItemsService.getFeaturedItems(), HttpStatus.OK);
    }
}
