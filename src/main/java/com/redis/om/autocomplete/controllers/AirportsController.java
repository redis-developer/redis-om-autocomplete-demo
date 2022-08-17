package com.redis.om.autocomplete.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( //
    methods = { POST, GET, OPTIONS, DELETE, PATCH }, //
    maxAge = 3600, //
    allowedHeaders = { //
        "x-requested-with", "origin", "content-type", "accept", "accept-patch" //
    }, //
    origins = "*" //
)
@RestController
@RequestMapping("/airports")
public class AirportsController {
}
