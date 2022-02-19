#! /usr/bin/perl -w
use strict;


&main(@ARGV);
exit;

=head1 main
 arg1	$input_file 
	00000	20	
	01111	15
	10111	23
	11010	2
	haplotype	num

 arg2	$output_file  .svg
=cut

sub main(@){
	my @args = @_;
	my ($infile,$outfile,$svgsize) = &checkArgs(@args);

	# calc each Snp Psi
	my ($r_psi,$r_haploSnp,$r_numHaplo,$numSnp,$numHaploTotal) = &calcPsi($infile);
	my $baseLength = int($svgsize/($numSnp-1));

	# calc snp Pair Psi&Dg
	my ($r_psiPair,$r_dgPair)
	 = &calcPairPsiDg($r_psi,$r_haploSnp,$r_numHaplo,$numSnp,$numHaploTotal);

	# snp Neighbor Dg
	my ($r_dgCombi)
	 = &calcNeighborDg($r_psi,$r_haploSnp,$r_numHaplo,$numSnp,$numHaploTotal);

	my $object = &SVGobject($numSnp,$r_dgPair,$r_dgCombi,$baseLength);
	&SVGfile($outfile,$object,$svgsize);
}

sub calcPsi{
	my ($infile) = @_;

	my $numHaploTotal=0;
	my @numHaplo;
	my @haploSnp;
	my @allele0;
	my @allele1;
	my $numSnp;
	open(IN,$infile) or die "can't open $infile";
	my $l=0;
	while(<IN>){
		chomp;
		my ($haplo,$numHaplo)=split(/\t/);
		$numHaplo[$l] = $numHaplo;
		$numHaploTotal += $numHaplo;

		my @alleles = split(//,$haplo);
		if($l==0){
			$numSnp = @alleles;
			for(my $i=0;$i<@alleles;$i++){
				$allele0[$i]=0;
				$allele1[$i]=0;
			}
		}
		for(my $i=0;$i<@alleles;$i++){
			$haploSnp[$l][$i] = $alleles[$i];
			if($alleles[$i] == 0){
				$allele0[$i] += $numHaplo;
			}elsif($alleles[$i] == 1){
				$allele1[$i] += $numHaplo;
			}else{
				die "infile format error\n";
			}
		}
		$l++;
	}
	close IN;

	my @psi;
	for(my $i=0;$i<@allele0;$i++){
		$psi[$i] = ($allele0[$i]-$allele1[$i])/$numHaploTotal;
		#print "i:$i psi:$psi[$i]\n";
	}
	return(\@psi,\@haploSnp,\@numHaplo,$numSnp,$numHaploTotal);
}

sub calcPairPsiDg{
	my ($r_psi,$r_haploSnp,$r_numHaplo,$numSnp,$numHaploTotal) = @_;

	# snp Pair
	my @psiPair;
	my @dgPair;
	for(my $i=0;$i<$numSnp-1;$i++){
			for(my $j=$i+1;$j<$numSnp;$j++){
			my @countPair = (0,0,0,0);	# count of pair genotype (00,01,10,11)
			for(my $h=0;$h<@$r_numHaplo;$h++){
				my $snp1 = $$r_haploSnp[$h][$i];
				my $snp2 = $$r_haploSnp[$h][$j];
				$snp1 = 2 if($snp1==1);
				$countPair[$snp1+$snp2] += $$r_numHaplo[$h];
			}
			$psiPair[$i][$j]
			 = ($countPair[0]-$countPair[1]-$countPair[2]+$countPair[3])/$numHaploTotal;

			# calc Dg
			my $snp1psi = $$r_psi[$i];
			my $snp2psi = $$r_psi[$j];
			my ($Dg,$Dg2);
			if($snp1psi*$snp2psi+1==0){
				$Dg = -10000;
				$Dg2 = 1-(($psiPair[$i][$j]-1)/($snp1psi*$snp2psi-1));
			}elsif($snp1psi*$snp2psi-1==0){
				$Dg = 1-(($psiPair[$i][$j]+1)/($snp1psi*$snp2psi+1));
				$Dg2 = -10000;
			}else{
				$Dg = 1-(($psiPair[$i][$j]+1)/($snp1psi*$snp2psi+1));
				$Dg2 = 1-(($psiPair[$i][$j]-1)/($snp1psi*$snp2psi-1));
			}
			$Dg = $Dg2 if($Dg < $Dg2);
			#print "i:$i j:$j psii:$snp1psi psij:$snp2psi psiij:$psiPair[$i][$j] dg:$Dg\n";
			$dgPair[$i][$j] = $Dg;
		}
	}
	return(\@psiPair,\@dgPair);
}

sub calcNeighborDg{
	my ($r_psi,$r_haploSnp,$r_numHaplo,$numSnp,$numHaploTotal) = @_;
	#my %dgCombi;
	my @dgCombi;
	my $r=0;
	# M : number of combi snps
	for(my $M=2;$M<=$numSnp;$M++){
		my $dgDenoPlusORminus=1;
		my $dgCombikey;
		for(my $i=0;$i<=$numSnp-$M;$i++){
			my $combiHaplo=0;
			#my $dgDeno=1;
			my $dgDenoLog=0;
			for(my $h=0;$h<@$r_numHaplo;$h++){
				my $haplotype;
				my $plusORminus=1;
				for(my $j=$i;$j<$i+$M;$j++){
					if($$r_haploSnp[$h][$j]==1){
						$plusORminus *= -1;
					}
					if($j==$i){
						$haplotype = $$r_haploSnp[$h][$j]
					}else{
						$haplotype .= $$r_haploSnp[$h][$j]
					}

					if($h==0){
						if($j==$i){
							#print "j=i=$j init dgDenoPlusOrminus $dgDenoPlusORminus\n";
							$dgDenoPlusORminus=1; 
							$dgCombikey=$j;
						}else{
							$dgCombikey.=",".$j;
						}
						# calc denominator of combi psi
						#$dgDeno *= $$r_psi[$j];
						if($$r_psi[$j] < 0){
							$dgDenoPlusORminus *= -1;
							$dgDenoLog += log(-1*$$r_psi[$j]);
							#print $$r_psi[$j],":",$dgDenoPlusORminus,"xxxxx\n";
						}else{
							$dgDenoLog += log($$r_psi[$j]);
						}
						#print "i=$i j=$j plus or minus :$dgDenoPlusORminus\n";
					}
				}
				#print "m:",$M,"haplotype:",$haplotype,"\n+-:",$plusORminus,"\n";
				$combiHaplo += $plusORminus*$$r_numHaplo[$h];
			}
			# calc combi psi
			my $combiPsi=$combiHaplo/$numHaploTotal;
			# calc combi dg
			my $dgDeno = exp($dgDenoLog) * $dgDenoPlusORminus;
			#print "M:",$M," dgDeno:",$dgDeno," key:",$dgCombikey," r:",$r,"\n";
			my ($Dg,$Dg2);
			if($dgDeno+1==0){
				$Dg = -10000;
				$Dg2 = 1-(($combiPsi-1)/($dgDeno-1));
			}elsif($dgDeno-1==0){
				$Dg = 1-(($combiPsi+1)/($dgDeno+1));
				$Dg2 = -10000;
			}else{
				$Dg = 1-(($combiPsi+1)/($dgDeno+1));
				$Dg2 = 1-(($combiPsi-1)/($dgDeno-1));
			}
			$Dg = $Dg2 if($Dg < $Dg2);
			#$dgCombi{$dgCombikey} = $Dg;
			$dgCombi[$r] = $Dg;
			$r++;
			#print "dgCombi r=$r $Dg\n";
		}
	}
	return(\@dgCombi);
	#return(\%dgCombi);
}

sub checkArgs{
	my @args = @_;
	my $usage = "Usage: [-if] infile [-of] outfile ([-s] svg size)";
	my ($infile,$outfile);
	my $svgsize = 500;
	for(my $i=0;$i<@args;$i++){
		if($args[$i] eq "-if"){
			$infile = $args[$i+1];
		}elsif($args[$i] eq "-of"){
			$outfile = $args[$i+1];
		}elsif($args[$i] eq "-s"){
			$svgsize = $args[$i+1];
		}
	}
	if(!defined $infile || !defined $outfile){
		die $usage,"\n";
	}
	return($infile,$outfile,$svgsize);
}

sub SVGfile{
	my ($file,$object,$svgsize) = @_;
	#my ($boxmin,$boxmax)=(-15.86,824.72);
	#my ($daishimin,$daishimax)=(-7.93,808.86);
	my ($boxmin,$boxmax)=(0,$svgsize);
	my ($daishimin,$daishimax)=(0,$svgsize);

	open(OUT,">$file") or die "can't open $file:$!\n";

	print OUT 
"<?xml version=\"1.0\" standalone=\"no\"?>
<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">
<svg width='$svgsize' height='$svgsize' viewBox='$boxmin $boxmin $boxmax $boxmax' xmlns=\"http://www.w3.org/2000/svg\">
<title>$file</title>
<desc>$file</desc>
<g font-family=\"MingLiU\" glyph-orientation-vertical=\"0\"  writing-mode=\"tb\">
  <rect style=\"fill:#fcf3f3;\" x='$daishimin' y='$daishimin' width='$daishimax' height='$daishimax'/>

$object
</g>
</svg>
"
	;
	close OUT;
}

sub SVGobject{
	my ($numSnp,$r_dgPair,$r_dgCombi,$baseLength) = @_;
	my $object="";

	# for Pair Dg
	my $startx = 0;
	my $starty = $baseLength/2 * ($numSnp-1);
	my $pointX1 = $startx;
	my $pointY1 = $starty;
	for(my $i=0;$i<$numSnp-1;$i++){
        for(my $j=$i+1;$j<$numSnp;$j++){
			if($j>$i+1){
				$pointX1 += $baseLength/2;
				$pointY1 -= $baseLength/2;
			}elsif($j==$i+1){
				$pointX1 = $startx + $i*$baseLength;
				$pointY1 = $baseLength/2 * ($numSnp-1);
			}
			my $dg = $$r_dgPair[$i][$j];
			$object = &svgPolygon($pointX1,$pointY1,$object,$dg,$baseLength);
		}
	}

	# for Combi Dg
	$startx += $baseLength/2;
	$starty += $baseLength/2;
	$pointX1 = $startx;
	$pointY1 = $starty;
	my $start = $numSnp-1;
	my $endnum = $numSnp-2;
	my $r=$start;
	$object = &svgPointCombi($pointX1,$pointY1,$object,$start,$endnum,$r_dgCombi,$r,$baseLength);
	#print $object,"\n";

	return($object);
}

sub svgPolygon{
	my ($pointX1,$pointY1,$object,$dg,$baseLength) = @_;
	my $pointX2 = $pointX1 + $baseLength/2;
	my $pointY2 = $pointY1 - $baseLength/2;
	my $pointX3 = $pointX1 + $baseLength;
	my $pointY3 = $pointY1;
	my $pointX4 = $pointX1 + $baseLength/2;
	my $pointY4 = $pointY1 + $baseLength/2;

	# Get color
	#my @color = ("#000000","#696969","#808080","#A9A9A9","#C0C0C0","#D3D3D3","#DCDCDC","#F5F5F5","#FFFFFF");
	my @color = ("#FFFFFF","#F5F5F5","#DCDCDC","#D3D3D3","#C0C0C0","#A9A9A9","#808080","#696969","#000000");
	my $color;
	if(0<=$dg && $dg<0.125){
		$color = $color[0];
	}elsif(0.125<=$dg && $dg<0.25){
		$color = $color[1];
	}elsif(0.25<=$dg && $dg<0.375){
		$color = $color[2];
	}elsif(0.375<=$dg && $dg<0.5){
		$color = $color[3];
	}elsif(0.5<=$dg && $dg<0.625){
		$color = $color[4];
	}elsif(0.625<=$dg && $dg<0.75){
		$color = $color[5];
	}elsif(0.75<=$dg && $dg<0.875){
		$color = $color[6];
	}elsif(0.875<=$dg && $dg<1){
		$color = $color[7];
	}elsif($dg==1){
		$color = $color[8];
	}

	$object .=" <polygon style=\'fill:$color;\' points=\'$pointX1,$pointY1,$pointX2,$pointY2,$pointX3,$pointY3,$pointX4,$pointY4\' stroke=\'black\' stroke-width=\'1\'/>\n";

	return($object);
}

sub svgPointCombi{
	my ($pointX1,$pointY1,$object,$start,$endnum,$r_dgCombi,$r,$baseLength) = @_;
	my $startx = $pointX1;
	my $starty = $pointY1;
	my $end = $start+$endnum-1;
	for(my $i=$start;$i<=$end;$i++){
		if($i>$start){
			$pointX1 += $baseLength;
		}
		my $dg = $$r_dgCombi[$r];
		$object = &svgPolygon($pointX1,$pointY1,$object,$dg,$baseLength);
		$r++;
	}

	$pointX1 = $startx + $baseLength/2;
	$pointY1 = $starty + $baseLength/2;

	$start += $endnum;
	$endnum--;
	if($endnum < 1){
		return($object);
	}else{
		&svgPointCombi($pointX1,$pointY1,$object,$start,$endnum,$r_dgCombi,$r,$baseLength);
	}
}
