DNA sequence entropy-based haplotype frequency evaluations.
There are two ways to use. One is to calculate Psi from given haplotype frequency (Downward). The other is in the opposite direction (upward). It is to infer haplotype frequency from unphased genotype data.

(1) Downward.
- Input.
A text file of two colomns. The first column is for haplotyps expressed with 0 and 1. The second column is for frequency of haplotype. 
They are separated with a tab. 
No redundant space or vacant lines are allowed.
A sample file for input is included in this package as "InForDown.txt".

- Output.
A sample output file of "InForDown.txt" is included in this package as "OutForDown.txt".
From 1 to No.SNP, haplotype frequency of subsets of SNPs and corresponding psi is displayed. The last section starts with "Psi" displays all the elements of Psi.

How to run.
 
java recHap.HapFreqDown -if InForDown.txt -of OutForDown.txt -nsnp 5 -nhap 5 -order 5 

-if, -of, -nsnp, and -nhap are for input file, output file, number of SNPs and number of haplotypes and they are required.
-order is option in case you need psi for combination of limited number of SNPs. Number for -order option is or less than -nsnp.

(1) Upward.
- Input.
Two formats are acceptable.
One is like "InForUp1.txt". For N genotypes with M SNPs, number of lines is Nx2. 
(Input file type 1)
For each genotypes two lines are used.
The left M columns of top and botom lines for each genotype is allele of SNP. 0 and 1 is for homozygoute and 2 is for heterozygote.
The top line of each genotype has an extra number on the right, which is number of individuals who had the genotype.
all the data are separated with a tab. No redundancy is allowed.
(Input file type 2)
Difference from type 1 is that all the individual is recorded in the same way with type 1. 
The column for number of samples doesn't exist.
# Program recognize file type with option "-gfreq". "-gfreq 0" is for type 2 an "-gfreq 1" is for type1.

- Output.
A sample output file of "InForUp1.txt" is included in this package as "OutForUp1.txt".
From 1 to No.SNP, haplotype frequency of individual combination is displayed with the corresponding psi value.
On the bottom, Psi matrix is displayed.

In case where no haplotype frequency can not explain the lower-order global maximum estimates, inference is terminated and output so far is printed out with caution.
"OutForUp2.txt" is such case.

- How to run.

java recHap.HapFreqUp -if InForUp1.txt -of OutForUp1.txt -nsnp 4 -nsample 897 -order 4 -gfreq 1
java recHap.HapFreqUp -if InForUp2.txt -of OutForUp2.txt -nsnp 4 -nsample 5 -order 4 -gfreq 0

Options -if -of -nsnp are for input file, outputfile, No. SNPs. -nsample option is No. genotypes for type 1 input file and No. individuals for type 2 input file. All the options mentioned are compulsory.
-order is option to limit inference to combinations of limited number of SNPs. Number for -order option is or less than one for -nsnp.
As mentioned in a section of input file, -gfreq option is for input type file discrimination. 1 is for type1 input file and 0 for type2 file. Default is 0.


