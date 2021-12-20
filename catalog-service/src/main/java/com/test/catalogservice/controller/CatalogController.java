package com.test.catalogservice.controller;

import com.test.catalogservice.dto.ResponseCatalog;
import com.test.catalogservice.entity.CatalogEntity;
import com.test.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;
    private final ModelMapper modelMapper;

/*    @Value("${greeting.message}")
    private String welcomeMessage;*/

    @GetMapping("/health_check")
    public String status(){
        return String.format("working...port : %s",env.getProperty("local.server.port"));
    }
    @GetMapping("/welcome")
    public String welcome(){
        return env.getProperty("greeting.message");
    }

    @GetMapping("/catalogs")
    public ResponseEntity<?> getCatalogs(){
        List<CatalogEntity> catalogList = catalogService.getAllCatalogs();
        List<ResponseCatalog> responseCatalogs = new ArrayList<>();
        catalogList.forEach(i->{
            responseCatalogs.add(modelMapper.map(i,ResponseCatalog.class));
        });
        return ResponseEntity.ok().body(responseCatalogs);
    }


}
