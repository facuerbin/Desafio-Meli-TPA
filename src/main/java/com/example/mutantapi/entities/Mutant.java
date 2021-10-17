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
                i++;

                while (i < row.length - 1 && row[i] == row[i + 1]) {
                    repeatedLetters++;
                    i++;
                }

                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch+= repeatedLetters / cantidadLetrasMutante;
                    System.out.println("Row");
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
                j++;

                while (j < dna.length - 1 && dna[j].charAt(i) == dna[j + 1].charAt(i)) {
                    repeatedLetters++;
                    j++;
                }

                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch+= repeatedLetters / cantidadLetrasMutante;
                    System.out.println("Column");
                    if (mutantDNAMatch >= cantidadCoincidencias) {
                        return true;
                    }
                }
            }
        }


        // Diagonal Check
        for (int j = dna.length - cantidadLetrasMutante; j >= -dna.length + cantidadLetrasMutante; j--) {
            // Reviso la diagonal \
            for (int i = 0; i < dna.length && j + i < dna.length - 1; i++) {
                if (j + i < 0) {
                    continue;
                }
                if (i + 1 == dna.length || dna[j + i].charAt(i) != dna[j + i + 1].charAt(i + 1)) {
                    continue;
                }

                int repeatedLetters = 2;
                i++;
                while ((j + i < dna.length - 1 && i < dna.length - 1) && dna[j + i].charAt(i) == dna[j + i + 1].charAt(i + 1)) {
                    repeatedLetters++;
                    i++;
                }

                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch+= repeatedLetters / cantidadLetrasMutante;
                    System.out.println("Diagonal 1");
                    if (mutantDNAMatch >= cantidadCoincidencias) {
                        return true;
                    }
                }
            }
        }

        // Reviso la diagonal /
        for (int sum = dna.length; sum > cantidadCoincidencias / 2; sum--) {
            for (int j = sum;j > 0; j--) {
                if (j >= dna.length) {
                    continue;
                }

                int i = sum - j;

                if ( i >= dna.length - 1 || dna[j].charAt(i) != dna[j-1].charAt(i+1)) {
                    continue;
                }

                int repeatedLetters = 2;
                i++;
                j--;

                while ((j > 0 && i < dna.length - 1) && dna[j].charAt(i) == dna[j-1].charAt(i+1)) {
                    repeatedLetters++;
                    i++;
                    j--;
                }

                if (repeatedLetters >= cantidadLetrasMutante) {
                    mutantDNAMatch+= repeatedLetters / cantidadLetrasMutante;
                    System.out.println("Diagonal 2");
                    if (mutantDNAMatch >= cantidadCoincidencias) {
                        return true;
                    }
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
