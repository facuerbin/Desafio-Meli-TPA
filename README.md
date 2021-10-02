# Desafio-Meli-TPA
Desarrollo de una api con Java 11 y Spring Boot para el parcial de la materia Taller de Programación Avanzada. UTN FRM
## Acerca de
El proyecto se realizó con Java 11 y Spring Boot. Consta de una API Rest que expone dos endpoints "api/v1/mutants" y "api/v1/mutants/:size". El primero se conecta mediante el verbo HTTP POST y requiere que en el body del request se envie una matriz que represente una secuencia de ADN. Está matriz debe ser de NxN con N mayor o igual a 4. En el documento "Examen Mercadolibre BE.pdf" se podrá encontrar un ejemplo de cómo se espera esté formateada la matriz. Luego se puede comunicar mediante GET con el endpoint "api/mutant/:size" con size siendo un número entero mayor o igual a 4. Este endpoint responderá generando una matriz aleatoria del tamaño solicitado.
## Ejemplos
GET localhost:8080/api/v1/mutant/20 =>
[
"CGAATTACCCCGTGAGAGAA",
"CACACCATGGTCGTTACCAA",
"GCAATGCGAAGCATAAACTT",
"ACGAGAATGGCATGGGTCGA",
"GAACATAAAGATAGGGTTAG",
"TCGCATTGAGATAACTATGC",
"TTACACAAAAACGGGAGCCG",
"GATACCCGGCCTCAACCCGG",
"GTCTTGGCGCAGTTCTAAAT",
"TTAAAGCCCTTTTAACAGTC",
"GATCTAATATGTGCAGGCTT",
"GCGCTAGTACCGTAATGTGT",
"TGCCATCGGCCAGGTACCAT",
"CACTTAGTCACTGTCTGTTG",
"GCGAATCTTTTCCCGTACTT",
"GGCCACTTAACTTGCTGAAG",
"TGAGGATGACCATAGCGTAG",
"GTTGTGTAACATTGTCTCAT",
"GCAGGCTAGCCGTTACATCT",
"TTGGAGATCGATGGGGTATC"
]

POST localhost:8080/api/v1/mutant/ +
{
"dna": 	[
"CGAATTACCCCGTGAGAGAA",
"CACACCATGGTCGTTACCAA",
"GCAATGCGAAGCATAAACTT",
"ACGAGAATGGCATGGGTCGA",
"GAACATAAAGATAGGGTTAG",
"TCGCATTGAGATAACTATGC",
"TTACACAAAAACGGGAGCCG",
"GATACCCGGCCTCAACCCGG",
"GTCTTGGCGCAGTTCTAAAT",
"TTAAAGCCCTTTTAACAGTC",
"GATCTAATATGTGCAGGCTT",
"GCGCTAGTACCGTAATGTGT",
"TGCCATCGGCCAGGTACCAT",
"CACTTAGTCACTGTCTGTTG",
"GCGAATCTTTTCCCGTACTT",
"GGCCACTTAACTTGCTGAAG",
"TGAGGATGACCATAGCGTAG",
"GTTGTGTAACATTGTCTCAT",
"GCAGGCTAGCCGTTACATCT",
"TTGGAGATCGATGGGGTATC"
]
}
=> Status: 200