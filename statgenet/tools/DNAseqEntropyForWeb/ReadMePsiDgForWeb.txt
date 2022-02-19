PsiDgForWeb.

There are two ways to use. One is to calculate Psi and Dg from given haplotype frequency (Downward usage). The other is in the opposite direction (Upward usage), that is to infer haplotype frequency from unphased genotype data and calculate Psi and Dg.

(1) Downward usage.
<Input>

A text file of two colomns. The first column is for haplotypes expressed with 0 and 1. The second column is for frequency of haplotype. 
They are separated with a tab. 
No redundant space or vacant lines are allowed.
A sample file for input is included in this package as "InForDown.txt".

<Output>

A sample output file, "OutForDown.txt", is included in this package, that is a result of "java recHap.HapFreqDown -if InForDown.txt -of OutForDown.txt -nsnp 5 -nhap 5 -order 5 ".
From 1 to No.SNP, haplotype frequency of subsets of SNPs and corresponding psi value is displayed. The last section starts with "Psi" displays all the elements of Psi.

<How to run>
 
Example:
java recHap.HapFreqDown -if InForDown.txt -of OutForDown.txt -nsnp 5 -nhap 5 -order 5 

The arguments -if, -of, -nsnp, and -nhap are for input file, output file, number of SNPs and number of haplotypes. They are mandatory arguments.
-order is option in case you need psi for combination of limited number of SNPs. Number for -order option should be eaqual or less than -nsnp.

(1) Upward usage.
<Input>

Two formats, "Input file type 1" and "Input file type 2", are acceptable.
 
(Input file type 1)
For each genotypes two lines are used.
The left M columns of upper and lower lines for each genotype are alleles of M SNPs. 0 and 1 represent for homozygotes and 2 for heterozygote.
The upper line of each genotype has an extra column on the right, which is a number of individuals who had the genotype.
All the data are separated with a tab. No redundancy is allowed.

(Input file type 2)
Difference from type 1 is that all the individual is recorded in the same way with type 1. 
The column for the number of samples doesn't exist.

# Program recognizes the file type with an argument "-gfreq". "-gfreq 0" is for type 2 and "-gfreq 1" is for type 1.

<Output>

A sample output file of "InForUp1.txt" is included in this package as "OutForUp1.txt".
From 1 to No.SNP, haplotype frequency of individual combination is displayed with the corresponding psi value.
On the bottom, Psi tensor is displayed.

In case where no haplotype frequency can not explain the lower-order global maximum estimates, inference is terminated and output so far is printed out with caution; "OutForUp2.txt" is an example of such case.

<How to run>

java recHap.HapFreqUp -if InForUp1.txt -of OutForUp1.txt -nsnp 4 -nsample 897 -order 4 -gfreq 1
java recHap.HapFreqUp -if InForUp2.txt -of OutForUp2.txt -nsnp 4 -nsample 5 -order 4 -gfreq 0 

Arguments -if -of -nsnp are for input file, outputfile, No. SNPs. -nsample argument is for No. genotypes for type 1 input file and No. individuals for type 2 input file. All the arguments mentioned are mandatory.
-order is option to limit the inference to combinations of limited number of SNPs. The number for -order option should be eaqual or less than the number for -nsnp.
As mentioned in a section of input file, -gfreq option is for input type file discrimination. 1 is for type1 input file and 0 for type2 file. Default is 0.


