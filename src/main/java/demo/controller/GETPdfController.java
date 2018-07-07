package demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import demo.domain.Person;
import demo.service.GeneratorService;

@Controller
public class GETPdfController {

    @Autowired
    private GeneratorService service;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getPDF() throws Exception {
        
        byte[] contents = service.createPDF("template",examplePerson()); 
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }
    
    private HashMap<String,Object> examplePerson() {
        HashMap<String,Object> data = new HashMap<String,Object>();

        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Snow");
        p.setStreet("Winterfell street, Ñuñoa");
        p.setZipCode("12345");
        p.setCity("Example City");
        
        data.put("person", p);
        
        return data;
    }
    
}
