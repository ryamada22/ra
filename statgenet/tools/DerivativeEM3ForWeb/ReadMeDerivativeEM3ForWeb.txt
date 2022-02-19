<Input>

A text file of 9 genotype numbers separated with a tab for individual SNP pair.
Genotype data of multiple SNP pairs are separated by a return.
A sample file for input is included in this package as "Inputtest.txt".

<Output>

A sample file of output is included in this package as "Outputtest.txt", that is output of "Inputtest.txt".
The left-most 9 columns are genotype counts, followed by EM result then result of derivative-based method follow on the right.
Result for a SNP pair is in a line.
EM result contains 4 haplotype frequency and the corresponding Log(L) and number of iterations, D', r^2 and Dg value follow.
Derivative-based method can give multiple global maximum estimates. Therefore initially number of the global maximum estimates and number of iterations are given.
Then average value of 4 haplotype frequency and the corresponding Log(L), D', r^2 and Dg are displayed.
Thereafter haplotype frequency and Log(L), D' r^2 and Dg for individual global maximum estimate are repeatedly attached.
The fifth example had two global maxima, therefore the column, "DerivResult-NumGlobals", in the sample output file is 2 and the line displays two sets of estimated haplotype frequencies.

<How to run>

java derivEM.DerivEMcomparison -if inputfile -of outputfile

Options.
Convergence threshold for EM method and derivative method is 1x10^-12.
"-emTh 9" for EM method or "-drvTh 15" for derivative method will change the threshold to 1x10^-9 and 1x10^-15, respectively.
Usually default value is adequate for both methods. Derivative method is known to be fast for convergence even with more stringent threshold, it returns answer with reasonable number of iterations.
EM method is sometimes very slow to converge, and with stricter convergence threshold may require unrealistic number of iterations.



