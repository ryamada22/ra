DgDrawer.pl

This program calculates Dg values for SNP pairs and tandem SNPs from haplotype frequency input file and outputs a Dg plot in SVG format.

<Input>

[-if] : input file. 
Each line has two columns. The first column is haplotype that is expressed as a string of 0 or 1 and the second column is the number of chromosomes. The two columns are separated with a tab.

[-of] : output filename. Suffix should be ".svg".

option:
[-s] : size(px) for output file.


<Output>

Output file is a svg file. Open with browser or other applications appropriate for svg formats.
Information on SVG(Scalable VectorGraphics) is available at http://www.w3.org/Graphics/SVG/ .  

<How to run>

DgDrawer.pl -if InForDgDrawer.txt -of OutForDgDrawer.svg