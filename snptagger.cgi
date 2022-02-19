#!/usr/local/bin/perl
# ��̂P�s�̑O�ɂ͋�s���󔒕������͂���Ȃ��悤�ɂ��Ă��������B
# perl�̃p�X���̓v���o�C�_����ɍ��킹�ĕύX���Ă��������B

#
# CGI�̃w�b�_�������o���܂��B�ʏ�� text/html ���w�肵�܂��B
#
print "Content-type: text/html\n";
print "\n";

#
# HTML�������o���܂��B
# "�`\n" �� �` �̕����� HTML ���L�q���܂����A�_�u���N�H�[�e�[�V����( " )
# ��p����ۂ� " �̑���� ' ��p���邩�A\" �̂悤�ɁA�o�b�N�X���b�V��( \ )
# ��O�ɒu���Ă��������B
#
print "<HTML>\n";
print "<HEAD>\n";
print "<TITLE>CGI TEST</TITLE>\n";
print "<meta http-equiv=Content-Type content='text/html; charset=shift_jis'>";
print "</HEAD>\n";
print "<BODY BGCOLOR='#FFFFFF' TEXT='#000000'>\n";
print "<XMP>\n";

#
# �t�H�[���Ɏw�肵���l��ǂݍ���ŁA�����o���܂��B
#
#print "=================================\n";
#print "�t�H�[���ϐ�\n";
#print "=================================\n";
if ($ENV{'REQUEST_METHOD'} eq "POST") {
	# POST�ł���ΕW�����͂���Ǎ��݂܂�
	read(STDIN, $query_string, $ENV{'CONTENT_LENGTH'});
} else {
	# GET�ł���Ί��ϐ�����Ǎ��݂܂�
	$query_string = $ENV{'QUERY_STRING'};
}
# �u�ϐ���1=�l1&�ϐ���2=�l2�v�̌`�����A���p�T���h( & )�ŕ������܂�
@a = split(/&/, $query_string);
# ���ꂼ��́u�ϐ���=�l�v�ɂ���
foreach $a (@a) {
	# �C�R�[��( = )�ŕ������܂�
	($name, $value) = split(/=/, $a);
	# + �� %8A �Ȃǂ��f�R�[�h���܂�
	$value =~ tr/+/ /;
	$value =~ s/%([0-9a-fA-F][0-9a-fA-F])/pack("C", hex($1))/eg;
	# �ϐ����ƒl�������o���܂�
	#print "$name = $value\n";
	# ��Ŏg�p����ꍇ�́A$FORM{'�ϐ���'} �ɑ�����Ă����܂�
	$FORM{$name} = $value;
}

# textarea����̓��͂�\n \t�Ȃǂŋ�؂�ꂽ���$value�ɂP������œ����Ă���̂ŁA����𕪉�����

@haplotypekata = split(/\s+/,$FORM{haplotype});

@snpid = split(/\s+/,$FORM{frequency});
#print "������\n";
#print "@haplotypekata\n";
#print "@snpid\n";

=head

#########################
############
#####
#�@�Θb�� taggy.pl
#�@���͈����̓t�@�C�����ƃR�����g

print "Name of input file?\n";
$infile = <STDIN>;
print "Any comment to be appeared in the output file?\n";
$comment = <STDIN>;

chomp $infile;
chomp $comment;

#########################
###########
#####
$date = `date +%Y%m%d`;
chomp $date;
#�@���o�̓t�@�C���Ǘ�
$rootdir ="./";
$outfile ="tag"."$infile\_"."$date\_$$";
open (OUT, ">>$rootdir/$outfile");
print OUT "$infile\t$comment\n";

########################
###########
#####
# haplotype�̓ǂݍ���
# haplotype��
# snp�����J�E���g����

open(IN,"$rootdir/$infile")
    or die "cannot open $infile :$!";
$i=0;
while(<IN>){
 chomp $_;
 unless($_  eq ''){
  $hapline0[$i]=$_;
  $i++;
 }
}

=cut

@hapline0=@haplotypekata;

#print "@hapline0\n";

#haplotype�̖{����$nh0
#$nh0=$i;
$nh0=@hapline0;
#SNP��$ns0���J�E���g����
$nscounter=$hapline0[0];
$ns0=length $nscounter;

#haplotype��������ɂȂ��Ă���̂��PSNP�A�������ƂɊǗ����Ȃ���
#@hapbase��haplotype�̍\��SNP�A������ۗL���郊�X�g
for($i=0;$i<$nh0;$i++){
 $haplinecut[$i]=$hapline0[$i];
 for($j=0;$j<$ns0;$j++){
  $hapbase0[$i][$j]=substr($haplinecut[$i],0,1);
  $haplinecut[$i]=substr($haplinecut[$i],1)
 }
}

#@hapbase�́@0 1��ύX���āA���W���[�A������0�ƂȂ�悤�ɂ���

for($i=0;$i<$ns0;$i++){
 $allele0[$i]=0;
 for($j=0;$j<$nh0;$j++){
  if($hapbase0[$j][$i]==0){
   $allele0[$i]++;
  }
 }
 if($allele0[$i]<$nh0/2){
  for($j=0;$j<$nh0;$j++){
   $hapbase0[$j][$i]=$hapbase0[$j][$i]+(0.5-$hapbase0[$j][$i])*2;
  }
 }
}


#�����SNP�����o����
#���̂��߂ɁA������ŕ\���ꂽ@snpline�����

for($i=0;$i<$ns0;$i++){
 for($j=0;$j<$nh0;$j++){
  $snpline0[$i].="$hapbase0[$j][$i]";
 }
}

################################
#################
########
#$nh0,$ns0����tagSNP�̍ŏ��K�v��$minreq�A�ő��K�v����$maxreq���v�Z����

$nhreq=$nh0;
$bekijyou=0;
$amaricounter=0;
while($nhreq>=2){
 $nhreqamari=$nhreq%2;
 if($nhreqamari>0){
  $amaricounter=1;
 }
 $nhreqsyou=($nhreq-$nhreqamari)/2;
 $bekijyou++;
 $nhreq=$nhreqsyou;
}
if($amaricounter==0){
 $minreq0=$bekijyou;
}else{
 $minreq0=$bekijyou+1;
}

#tagSNP�̍ő��K�v��$maxreq���v�Z����
if($nh0>$ns0){
 $maxreq0=$ns0;
}else{
 $maxreq0=$nh0;
}

print "################################################################\n#####    haplotype�{����$nh0�{,SNP����$ns0�ł��B\n#    #   tagSNP�Ƃ��Ď����\�ȍŏ�����$minreq0�ł�\n";

#����SNP���O���[�v������%group0�Ɋi�[����B
#�O���[�v�̑�\��%�̒l�ɁA�O���[�v������Ĉȍ~�̉�͂���
#�Ȃ����SNP���L�[�ɓo�^�����
$gr0=0;
for($i=0;$i<$ns0;$i++){
 unless(defined $group0{$i}){
  for($j=$i+1;$j<$ns0;$j++){
   if($snpline0[$i] eq $snpline0[$j]){
    $group0{$j}=$i;
    $gr0++;
   }
  }
 }
}


$ns1=$ns0-$gr0;
$nh1=$nh0;

print "#    # \n\#####\n#  #\n#   #       #\n#    #     #\n";


#�O���[�v�����ꂽ��̃n�v���^�C�v��SNP��$nh1,$ns1����tagSNP�̍ŏ��K�v��$minreq1�A�ő��K�v����$maxreq1���v�Z����

$nhreq=$nh1;
$bekijyou=0;
$amaricounter=0;
while($nhreq>=2){
 $nhreqamari=$nhreq%2;
 if($nhreqamari>0){
  $amaricounter=1;
 }
 $nhreqsyou=($nhreq-$nhreqamari)/2;
 $bekijyou++;
 $nhreq=$nhreqsyou;
}
if($amaricounter==0){
 $minreq1=$bekijyou;
}else{
 $minreq1=$bekijyou+1;
}

#tagSNP�̍ő��K�v��$maxreq���v�Z����
if($nh1>$ns1){
 $maxreq1=$ns1;
}else{
 $maxreq1=$nh1;
}

#print "�O���[�v�����ꂽ���tagSNP�K�v�ŏ�����$minreq1,�K�v�ő吔��$maxreq1\n";

#���A�ۑ�́ANs��SNP�ɂ���Č`�������Nh�̃n�v���^�C�v����ʂ��邽�߂̍ŏ���SNP�Z�b�g��T�����邱�Ƃł���

#���ł���SNP�Z�b�g�̗v�f�ł���SNP�ɂ���āA���ׂẴn�v���^�C�v�y�A�͕ٕʂł���
#������������邽�߂ɁA�n�v���^�C�v�����y�A(����$paicount1)���ׂĂɂ��āA�����ٕʂ�����SNP��o�^����

#$paircount1=combination($nh,2)�ƂȂ��Ă���B�y�A���C�Y�Ńn�v���^�C�v���r���邻�̑g�ݍ��킹���A$paircount�ł���A��r�ԍ��̃y�ASNP�̔ԍ���$hap1,$hap2�ƂɃ��X�g�Ŏ��߂�

$paircount1=0;
for($i=0;$i<$nh1;$i++){
 for($j=$i+1;$j<$nh1;$j++){
  $hap1[$paircount1]=$i;
  $hap2[$paircount1]=$j;
  $paircount1++;
 }
}

print "#     #####\n##\n# #      �S����$paircount1�̃n�v���^�C�v�y�A��ٕʂ���K�v������܂�\n";

#��r�g�ݍ��킹ID���Ƃɂ��̂Q�n�v���^�C�v��ٕʂ��邱�Ƃ̂ł���SNP�ԍ���@discr1[��rID]�Ɋi�[
#��r�g�ݍ��킹ID���Ƃɂ��̂Q�n�v���^�C�v��ٕʂ��邱�Ƃ̂ł���SNP����@discrnum1�Ɋi�[
#snp���ٕʂ���n�v���^�C�v�y�A��@discrsnp�Ɋi�[
for($i=0;$i<$paircount1;$i++){
 for($j=0;$j<$ns0;$j++){
  unless(defined $group0{$j}){
   if($hapbase0[$hap1[$i]][$j] != $hapbase0[$hap2[$i]][$j]){
    unless(defined $discrnum1[$i]){
     $discrnum1[$i]=0;
    }
    unless(defined $discrsnpnum1[$j]){
     $discrsnpnum1[$j]=0;
    }
    $discr1[$i][$discrnum1[$i]]=$j;#�ٕ�SNP�ԍ���ۊ�
    $discrnum1[$i]++;#�ٕ�SNP����ۊ�
    $discrsnp1[$j][$discrsnpnum1[$j]]=$i;
    $discrsnpnum1[$j]++;
   }
  }
 }
}

=head

# �n�v���^�C�v�y�A��ID�����ăn�b�V���ɂĊǗ�����
for($i=0;$i<$paircount1;$i++){
 $pairhash1{$i}=1;
}

=cut

#�B���SNP�ɂ���ĕٕʂ����悤�ȃn�v���^�C�v�y�A��ٕʂ���SNP�͖�������tagSNP�ł���B

#���̏�����p���āA�K��tagsnp�ɂȂ�Ȃ��Ă͂Ȃ�Ȃ�SNP�����������B�����%mustsnp�Ɋi�[����

for($i=0;$i<$paircount1;$i++){
 if($discrnum1[$i]==1){
  $mustsnp{$discr1[$i][0]}=1;
  #$rmhappair{$i}=1;
 }
}
$nummustsnp=0;
$i=0;
foreach $key (keys %mustsnp){
 #$mustsnplist[$i]=$mustsnp{$key};
 $nummustsnp++;
}

print "#  #     ������1��SNP�ɂ���ĕٕʂ����n�v���^�C�v�y�A�����݂�\n#####    ���̃n�v���^�C�v�y�A��ٕʂ��邽�߂�tagSNP�Ƃ��đI�΂ꂽSNP�̌���$nummustsnp�ł�\n";

#%mustsnp�ɓo�^���ꂽSNP�ɂ���ĕٕʂ����y�A�́A�ȍ~�̉�͂��r������

=head

foreach $key (keys %pairhash1){
 unless(defined $rmhappair{$key}){
  $pairhash2{$key}=1;
 }
}

=cut

#foreach $key (keys %pairhash2){
 #$pairnum2++;
#}

#��mustsnp�ɂ���āA%pairhash2�̃n�v���^�C�v�y�A��ٕʂ��邱�ƂɂȂ�
#���̂Ƃ��ɑΏۂƂ����SNP����$ns2,haplotype�{����$nh2,�n�v���^�C�v�y�A��%pairhash2,���̐���$pairnum2,tagSNP�̍ŏ�����$minreq2,�ő�����$maxreq2�ƂȂ�
$ns2=$ns1-$nummustsnp;
$nh2=$nh1;
#$nh2,$ns2����tagSNP�̍ŏ��K�v��$minreq2�A�ő��K�v����$maxreq2���v�Z����

$nhreq=$nh2;
$bekijyou=0;
$amaricounter=0;
while($nhreq>=2){
 $nhreqamari=$nhreq%2;
 if($nhreqamari>0){
  $amaricounter=1;
 }
 $nhreqsyou=($nhreq-$nhreqamari)/2;
 $bekijyou++;
 $nhreq=$nhreqsyou;
}
if($amaricounter==0){
 $minreq2=$bekijyou;
}else{
 $minreq2=$bekijyou+1;
}

#tagSNP�̍ő��K�v��$maxreq���v�Z����
if($nh2>$ns2){
 $maxreq2=$ns2;
}else{
 $maxreq2=$nh2;
}

print "#    #   �����茟���ɂ���ēK����SNP�Z�b�g�𓾂邽�߂̌������J�n���܂��B\n#     #  ���̉�͑Ώ�SNP����$ns2�ł�\n################################################################\n";

#�Q���@��S������tagsnp�T��
#�T���Ώۂ�:
#�@%pairhash2�̑S�Ă�ٕʂł���
#�@$ns2��SNP�̒�����I�΂ꂽSNP�Z�b�g�ɂ���ĕٕʂ���
#�@�ŏ���$minreq2����T�����J�n����
#�@��������ꂽ��A���̂Ƃ���tagSNP����SNP�g�ݍ��킹�ɂ��ẮA���ׂĂ�SNP�̑g�ݍ��킹�ɂ��ĒT�����A
#�@���ꂪ�I���������_�ŒT���͏I������

#�@���̂悤�ȑg�ݍ��킹�����T�u���[�`���@combination_up(�{�\�[�X�̖���)��p����
#�@combination_up�́A�����Ƃ��Ď��R�� N �ƁA�퐔��Z�b�g�A�퐔��Z�b�g�̐��񒷂��1����
#�@����Z�b�g��Ԃ��B�������A����̔����́A{1,2,3,...,N}�̐���͈͓̔��Ŕ�����������̂Ƃ���

#�n�b�V��������Ă����͑Ώ�SNP�����X�g������
$j=0;
for($i=0;$i<$ns0;$i++){
 unless(defined $group0{$i}){
  unless(defined $mustsnp{$i}){
   $analsnp[$j]=$i;
   $j++;
  }
 }
}

#�T�u���[�`��combination_up�ɗ^���鏉���l��$ns2,@comblist
#$minreq2�̂����Amustsnp�̐���������


#�����̒T���́A�K�v�ŏ�����SNP�Z�b�g����J�n����ׂ��ł���B
#�������A�K���܂܂�Ȃ��Ă͂Ȃ�Ȃ�SNP��($nummustsnp)���A�K�v�ŏ������������Ƃ��́A
#���łɕK�v�ŏ���SNP�ɂāA�ٕʂ��I�����Ă��邩�ǂ������ɔ��f����
#�܂��A�K�v�ŏ����ɂ��炽��SNP��ǉ����āA�S�n�v���^�C�v�y�A��ٕʂ��Ă��邩�̌�����������ɂ������āA
#��͑Ώ�SNP�Z�b�g���牽��SNP��I�Ԓi�K����n�߂�ׂ�����$minstart���Ƃ��āA��`����
#����$numustsnp�����łɕK�v�ŏ����ȏゾ������Amust�̃Z�b�g�ɂĕٕʂł��Ă��Ȃ����ǂ������m�F��
#���̏�ŁA�ǉ�SNP���P����J�n����($minstar=1)

$minstart=$minreq2-$nummustsnp;
if($minstart<=0){
 $minstart=1;
}


# subroutin combination_up�́A�u�g�ݍ��킹�v�𔭐���������ƂƂȂ鎩�R���ƁA���郊�X�g���󂯎��
# ���̃��X�g�ɂ́A�g�ݍ��킹�����^�u��؂�̕�����ŏ��L�����@�O�Ԓn��
# ���̐���Ɋ܂܂��ő吔�ɂP���������l�i�P�Ԓn)
# ����сAN�̐�����M�̐������o���g�ݍ��킹�́u�g�ݍ��킹���v���Q�Ԓn�Ɏ���

$comblist[0][0]="\t";#�g�ݍ��킹�̃^�u��؂�i�[��
$comblist[0][1]=0;#�g�ݍ��킹����ɎQ�����Ă���ő吔+1
$comblist[0][2]=1;#�g�ݍ��킹�̐�

#�ȉ��̏����́A�ŏ�SNP����SNP�𓾂邽�߂́u�g�ݍ��킹�v�𔭐������邽�߂̃��[�v�����ł���
#subroutin combination_up�ɂ��1���[�v���Ƃɑg�ݍ��킹���\������SNP�����P������d�g�݂ł���
@arg2=@comblist;
for($i=0;$i<$minstart;$i++){
 $arg1=$ns2;
 @arg2=&combination_up($arg1,@arg2);
}


#mustsnp�ŕٕʂ����n�v���^�C�v�y�A��o�^
#�����A���̃n�v���^�C�v�y�A�̐����A�ٕʂ����ׂ��n�v���^�C�v�y�A�̒l�ɓ��������
#����́A���łɓ�����ׂ����ł���A�������B����ł���

foreach $key (keys %mustsnp){
 for($k=0;$k<$discrsnpnum1[$key];$k++){
  $discredmust{$discrsnp1[$key][$k]}=1;
 }
}
$mustdiscrd=0;
foreach $key(keys %discredmust){
 $mustdiscrd++;
}
$mustisans=0;
if($mustdiscrd==$paircount1){#must�Z�b�g�����ł��邩�̔���
 $mustisans=1;
}
if($mustisans==1){
 foreach $key (keys %mustsnp){
  push(@mustanswer, $key);
 }
 @finalanswer=@mustanswer;

 @finalanswer=sort { $a <=> $b }@finalanswer;
 print "\n\n-----------------------\n<<<<<<<<�œK���ł�>>>>>>>>\n\ntagSNP����$nummustsnp�ł�\nSNP�ԍ���0����n�܂鐔�l�ł��̂ł����ӂ�������\ntagSNP�̑g�ݍ��킹��\n-----------------------\n";
 #print "@finalanswer\n";
 #print "@snpid\n";
 print "SNP�ԍ�	SNP��\n";

 for($k=0;$k<@finalanswer;$k++){


  print "$finalanswer[$k]\t$snpid[$finalanswer[$k]]\n";
 }
 print "\n";
 print "-----------------------\n";
 print "��L�Z�b�g��tagSNP�̃Z�b�g�̈ꕔ�ł�\nRepresenting SNP�݂̂ō쐬����Ă��܂�\n�ȉ��ɂ���ȊO��SNP�Z�b�g��m�邽�߂̌����\��SNP�̃y�A�������܂�\n";
 print "RepSNP�Ǝ��ւ�����SNP�ԍ�\tRepresenting SNP�ԍ�\n";
 foreach $key(keys %group0){
  print "$key\t$group0{$key}\n";
 }
 print "RepSNP�Ǝ��ւ�����SNP��\tRepresenting SNP��\n";
 foreach $key(keys %group0){
  print "$snpid[$key]\t$snpid[$group0{$key}]\n";
 }


}elsif($mustisans!=1){#��͑Ώ�SNP�ɂ��T���ɓ���


 #$minreq�ȏ��SNP�̑g�ݍ��킹����́A�������tagSNP�Z�b�g�Ƃ��ēK�؂��ǂ����𔻒f����
 $endflag=0;
 $x=0;
 $numtagsnp=$minstart;
 while($endflag==0){

  for($i=0;$i<$arg2[0][2];$i++){
   %discred=%discredmust;
   $discrdnum=0;
   $snpcomb=$arg2[$i][0];
   @combination=split(/\t/,$snpcomb);
   if($combination[0] eq ''){
    shift @combination;
   }
   for($j=0;$j<@combination;$j++){
    unless(defined $discrsnpnum1[$analsnp[$combination[$j]]]){#0228RY
     $discrsnpnum1[$analsnp[$combination[$j]]]=0;
    }
    for($k=0;$k<$discrsnpnum1[$analsnp[$combination[$j]]];$k++){
     $discred{$discrsnp1[$analsnp[$combination[$j]]][$k]}=1;
    }

   }
   foreach $key (keys %discred){
    $discrdnum++;
   }
   if($discrdnum==$paircount1){
    $endflag=1;
    #print "answer was obtained. number of tagsnps is $numtagsnp\n";
    #for($j=0;$j<@combination;$j++){

    foreach $j(@combination){
     #$answer[$x]="$answer[$x]"."\t$analsnp[$j]";
     $answer[$x].="\t$analsnp[$j]";
    }
    $x++;
   }
  }
 #tagSNP�̐����P���₷�B

 @arg2=&combination_up($arg1,@arg2);
 $numtagsnp++;

  if($numtagsnp>$maxreq1){
   $noans="no_answer\n";
   print "$noans";
   last;
  }
 }

 #���̏o��

 foreach $key (keys %mustsnp){
  push(@mustanswer, $key);
 }

#tagSNP����
 $numberoftagsnp=$numtagsnp-1+$nummustsnp;
 print "\n\n-----------------------\n<<<<<<<<�œK���ł�>>>>>>>>\n\ntagSNP����$numberoftagsnp�ł�\nSNP�ԍ���0����n�܂鐔�l�ł��̂ł����ӂ�������\ntagSNP�̑g�ݍ��킹��\n-----------------------\n";

 for($i=0;$i<@answer;$i++){
  @finalanswer=@mustanswer;
  #print "@mustsnp\t$answer[$i]\n";
  @answersingle=split(/\t/,$answer[$i]);
  if($answersingle[0] eq ''){
   shift @answersingle;
  }
  for($j=0;$j<@answersingle;$j++){
   push(@finalanswer,$answersingle[$j]);
  }
  @finalanswer=sort { $a <=> $b }@finalanswer;
  #print "@finalanswer\n";
  #print "@snpid\n";
  print "SNP�ԍ��@SNP��\n";
  for($k=0;$k<@finalanswer;$k++){
   print "$finalanswer[$k]\t$snpid[$finalanswer[$k]]\n";
  }
  print "\n";

 }
 print "-----------------------\n";
 print "��L�Z�b�g��tagSNP�̃Z�b�g�̈ꕔ�ł�\nRepresenting SNP�݂̂ō쐬����Ă��܂�\n�ȉ��ɂ���ȊO��SNP�Z�b�g��m�邽�߂̌����\��SNP�̃y�A�������܂�\n";
 print "RepSNP�Ǝ��ւ�����SNP�ԍ�\tRepresenting SNP�ԍ�\n";
 foreach $key(keys %group0){
  print "$key\t$group0{$key}\n";
 }
 print "RepSNP�Ǝ��ւ�����SNP��\tRepresenting SNP��\n";
 foreach $key(keys %group0){
  print "$snpid[$key]\t$snpid[$group0{$key}]\n";
 }



}



sub combination_up{

my ($arg1,@arg2)=@_;
my ($i,@return,$j,$k);
$k=0;
for($i=0;$i<$arg2[0][2];$i++){
 for($j=$arg2[$i][1];$j<$arg1;$j++){
  $return[$k][0]="$arg2[$i][0]"."$j\t";
  $return[$k][1]=$j+1;
  $k++;
 }
}
for($i=0;$i<$k;$i++){
 $return[$i][2]=$k;
}
return @return;

}




#
# HTML�̏I���̕����������o���܂��B
#
print "</XMP>\n";
print "</BODY>\n";
print "</HTML>\n";
print "\n";


