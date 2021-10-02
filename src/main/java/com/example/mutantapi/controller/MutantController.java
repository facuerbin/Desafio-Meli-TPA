package com.example.mutantapi.controller;


import com.example.mutantapi.entities.Mutant;
import com.example.mutantapi.repository.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/mutant")
public class MutantController {
    @Autowired
    MutantRepository mutantRepository;

    @PostMapping("")
    public ResponseEntity<?> isMutant (@RequestBody Mutant mutant ) {
        try {
            if (Mutant.isMutant(mutant.getDna())) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Es ADN Mutante");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No es ADN mutante");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: \n"+e.getMessage());
        }
    }

    @GetMapping("/{size}")
    public ResponseEntity<?> generateDNA (@PathVariable int size) {
        try {
            if (size < 4) {
                throw new Exception("El tamaño mínimo es de 4");
            }
            String[] generatedDNA = Mutant.generateRandom(size);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(generatedDNA);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: \n"+e.getMessage());
        }
    }

}
