package com.example.mutantapi.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;

@Entity
@Table(name = "mutant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mutant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "dna")
    @Type(type = "binary")
    private String[] dna;

    private static final char[] dnaLetters = {'A', 'C', 'T', 'G'}; // Letras válidas para ADN mutante
    private static final int cantidadLetrasMutante = 4; // la cantidad de letras que se deben repetir para ADN mutante
    private static final int cantidadCoincidencias = 2;// la cantidad de lineas de ADN mutante que se deben encontrar

    @Column(name = "is_mutant")
    private boolean isMutant;


    public static boolean isMutant(String[] dna) {
        long startTime = System.currentTimeMillis();
        if (!isDNA(dna)) {
            System.out.println("El adn no es válido");
            return false;
        }


        int mutantDNAMatch = 0;

        // Row check
        for (String line : dna) {
            char[] row = line.toUpperCase().toCharArray();
            for (int i = 0; i < row.length - 2; i += 2) {
                if (row[i] != row[i + 1]) {
                    continue;
                }

                int repeatedLetters = 2;
                if (i > 0 && row[i] == row[i - 1]) {
                    repeatedLetters++;
                }

                for (int j = i + 1; j < row.length - 1; j++) {
                    if (row[j] == row[j + 1]) {
                        repeatedLetters++;
                    } else {
                        break;
                    }
                }

                i += repeatedLetters;
                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch++;
                    if (mutantDNAMatch >= cantidadCoincidencias) {
                        return true;
                    }
                }
            }
        }

        // Column Check
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna.length - 2; j += 2) {
                if (dna[j].charAt(i) != dna[j + 1].charAt(i)) {
                    continue;
                }

                int repeatedLetters = 2;
                if (j > 0 && dna[j].charAt(i) == dna[j - 1].charAt(i)) {
                    repeatedLetters++;
                }

                for (int k = j; k < dna.length - 2; k++) {
                    if (dna[k].charAt(i) == dna[k + 1].charAt(i)) {
                        repeatedLetters++;
                    } else {
                        break;
                    }
                }

                j += repeatedLetters;
                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch++;
                    if (mutantDNAMatch >= cantidadCoincidencias) {
                        return true;
                    }
                }
            }
        }

        // Diagonal Check
        for (int x = 0; x < dna.length - 2; x+= 2) {
            // diagonal central
            if ( dna[x].charAt(x) != dna[x+1].charAt(x+1)) {
                continue;
            }

            int repeatedLetters = 2;
            if (x > 0 && dna[x-1].charAt(x-1) == dna[x].charAt(x) ) {
                repeatedLetters++;
            }
            x++;
            while (x < dna.length -1 && dna[x].charAt(x) == dna[x+1].charAt(x+1)) {
                repeatedLetters++;
                x++;
            }

            if (repeatedLetters >= 4) {
                mutantDNAMatch++;

                if (mutantDNAMatch >= 2) {
                    return true;
                }
            }
        }

        return false;
    }


    public static String[] generateRandom(int size) {
        String[] generatedDna = new String[size];
        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            String row = "";
            for (int j = 0; j < size; j++) {
                char letter = dnaLetters[rand.nextInt(dnaLetters.length)];
                row += letter;
            }
            generatedDna[i] = row;
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Se tardó " + totalTime + " milisegundo en generar una matriz de ADN de " + size + "X" + size);
        return generatedDna;
    }

    public static boolean isDNA(String[] dna) {
        int size = dna.length;
        if (size < 4) {
            System.out.println("Matriz de tamaño " + size + "X" + size + ", debe ser de 4X4 como mínimo");
            return false;
        }
        for (String line : dna) {
            char[] row = line.toUpperCase().toCharArray();
            if (row.length != size) {
                System.out.println("Matriz de tamaño inválido obtuvo una columna de " + row.length + " letras pero esperaban " + size);
                return false;
            }
            for (char letter : row) {
                if (!isValidLetter(letter)) {
                    System.out.println("Letra inválida en ADN " + letter);
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValidLetter(char letter) {
        boolean result = false;

        for (char validLetter : dnaLetters) {
            if (letter == validLetter) {
                result = true;
                break;
            }
        }

        return result;
    }

}
