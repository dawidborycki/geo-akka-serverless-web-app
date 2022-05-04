package com.akka.weatherdemo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherAlertingController {    
    @Value("${akka.serverless.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public WeatherAlertingController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/submit")
    public String submit(Geolocation geolocation) {
        return "submit";
    }

    @PostMapping("/handleSubmit")
    public String handleSubmit(@Valid Geolocation geolocation, BindingResult bindingResult) {
        
        String url = endpoint + geolocation.getUserId() + "/items/add";        

        this.restTemplate.postForObject(url, geolocation, Geolocation.class);
        
        return "index";
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)     
    @ResponseBody
    public String handleExternalNotification(
        @RequestParam(value = "lat", required = true) Double lat, 
        @RequestParam(value = "long", required = true) Double lng,
        @RequestParam(value = "userId", required = true) Integer userId) {        
            String url = endpoint + "{userId}/items";

            // Get all items
            ResponseEntity<Geolocation[]> response = this.restTemplate.getForEntity(
                url, Geolocation[].class);
                
            Geolocation[] items = response.getBody();
            
            // TODO: Check if current item exists in the list, if so send notification
            
            return "OK";
    }
}