#!/usr/local/bin/perl
# 上の１行の前には空行も空白文字もはいらないようにしてください。
# perlのパス名はプロバイダや環境に合わせて変更してください。

#
# CGIのヘッダを書き出します。通常は text/html を指定します。
#
print "Content-type: text/html\n";
print "\n";

#
# HTMLを書き出します。
# "～\n" の ～ の部分に HTML を記述しますが、ダブルクォーテーション( " )
# を用いる際は " の代わりに ' を用いるか、\" のように、バックスラッシュ( \ )
# を前に置いてください。
#
print "<HTML>\n";
print "<HEAD>\n";
print "<TITLE>CGI TEST</TITLE>\n";
print "<meta http-equiv=Content-Type content='text/html; charset=shift_jis'>";
print "</HEAD>\n";
print "<BODY BGCOLOR='#FFFFFF' TEXT='#000000'>\n";
print "<XMP>\n";

#
# フォームに指定した値を読み込んで、書き出します。
#
#print "=================================\n";
#print "フォーム変数\n";
#print "=================================\n";
if ($ENV{'REQUEST_METHOD'} eq "POST") {
	# POSTであれば標準入力から読込みます
	read(STDIN, $query_string, $ENV{'CONTENT_LENGTH'});
} else {
	# GETであれば環境変数から読込みます
	$query_string = $ENV{'QUERY_STRING'};
}
# 「変数名1=値1&変数名2=値2」の形式をアンパサンド( & )で分解します
@a = split(/&/, $query_string);
# それぞれの「変数名=値」について
foreach $a (@a) {
	# イコール( = )で分解します
	($name, $value) = split(/=/, $a);
	# + や %8A などをデコードします
	$value =~ tr/+/ /;
	$value =~ s/%([0-9a-fA-F][0-9a-fA-F])/pack("C", hex($1))/eg;
	# 変数名と値を書き出します
	#print "$name = $value\n";
	# 後で使用する場合は、$FORM{'変数名'} に代入しておきます
	$FORM{$name} = $value;
}

# textareaからの入力は\n \tなどで区切られた情報が$valueに１文字列で入っているので、それを分解する

@haplotypekata = split(/\s+/,$FORM{haplotype});

@snpid = split(/\s+/,$FORM{frequency});
#print "分解後\n";
#print "@haplotypekata\n";
#print "@snpid\n";

=head

#########################
############
#####
#　対話式 taggy.pl
#　入力引数はファイル名とコメント

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
#　入出力ファイル管理
$rootdir ="./";
$outfile ="tag"."$infile\_"."$date\_$$";
open (OUT, ">>$rootdir/$outfile");
print OUT "$infile\t$comment\n";

########################
###########
#####
# haplotypeの読み込み
# haplotype数
# snp数をカウントする

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

#haplotypeの本数は$nh0
#$nh0=$i;
$nh0=@hapline0;
#SNP数$ns0をカウントする
$nscounter=$hapline0[0];
$ns0=length $nscounter;

#haplotypeが文字列になっているのを１SNPアレルごとに管理しなおす
#@hapbaseはhaplotypeの構成SNPアレルを保有するリスト
for($i=0;$i<$nh0;$i++){
 $haplinecut[$i]=$hapline0[$i];
 for($j=0;$j<$ns0;$j++){
  $hapbase0[$i][$j]=substr($haplinecut[$i],0,1);
  $haplinecut[$i]=substr($haplinecut[$i],1)
 }
}

#@hapbaseの　0 1を変更して、メジャーアレルが0となるようにする

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


#同一のSNPを検出する
#そのために、文字列で表された@snplineを作る

for($i=0;$i<$ns0;$i++){
 for($j=0;$j<$nh0;$j++){
  $snpline0[$i].="$hapbase0[$j][$i]";
 }
}

################################
#################
########
#$nh0,$ns0からtagSNPの最少必要数$minreq、最多必要数を$maxreqを計算する

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

#tagSNPの最多必要数$maxreqを計算する
if($nh0>$ns0){
 $maxreq0=$ns0;
}else{
 $maxreq0=$nh0;
}

print "################################################################\n#####    haplotype本数は$nh0本,SNP数は$ns0個です。\n#    #   tagSNPとして実現可能な最少数は$minreq0個です\n";

#同一SNPをグループ化して%group0に格納する。
#グループの代表を%の値に、グループ化されて以降の解析から
#省かれるSNPがキーに登録される
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


#グループ化された後のハプロタイプ数SNP数$nh1,$ns1からtagSNPの最少必要数$minreq1、最多必要数を$maxreq1を計算する

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

#tagSNPの最多必要数$maxreqを計算する
if($nh1>$ns1){
 $maxreq1=$ns1;
}else{
 $maxreq1=$nh1;
}

#print "グループ化された後のtagSNP必要最少数は$minreq1,必要最大数は$maxreq1\n";

#今、課題は、Ns個のSNPによって形成されるNh個のハプロタイプを区別するための最少のSNPセットを探索することである

#解であるSNPセットの要素であるSNPによって、すべてのハプロタイプペアは弁別できる
#これを検索するために、ハプロタイプが作るペア(総数$paicount1)すべてについて、それを弁別しうるSNPを登録する

#$paircount1=combination($nh,2)となっている。ペアワイズでハプロタイプを比較するその組み合わせが、$paircountであり、比較番号のペアSNPの番号を$hap1,$hap2とにリストで収める

$paircount1=0;
for($i=0;$i<$nh1;$i++){
 for($j=$i+1;$j<$nh1;$j++){
  $hap1[$paircount1]=$i;
  $hap2[$paircount1]=$j;
  $paircount1++;
 }
}

print "#     #####\n##\n# #      全部で$paircount1個のハプロタイプペアを弁別する必要があります\n";

#比較組み合わせIDごとにその２ハプロタイプを弁別することのできるSNP番号を@discr1[比較ID]に格納
#比較組み合わせIDごとにその２ハプロタイプを弁別することのできるSNP数を@discrnum1に格納
#snpが弁別するハプロタイプペアを@discrsnpに格納
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
    $discr1[$i][$discrnum1[$i]]=$j;#弁別SNP番号を保管
    $discrnum1[$i]++;#弁別SNP数を保管
    $discrsnp1[$j][$discrsnpnum1[$j]]=$i;
    $discrsnpnum1[$j]++;
   }
  }
 }
}

=head

# ハプロタイプペアをID化してハッシュにて管理する
for($i=0;$i<$paircount1;$i++){
 $pairhash1{$i}=1;
}

=cut

#唯一のSNPによって弁別されるようなハプロタイプペアを弁別するSNPは無条件でtagSNPである。

#この条件を用いて、必ずtagsnpにならなくてはならないSNPが判明した。それを%mustsnpに格納する

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

print "#  #     たった1個のSNPによって弁別されるハプロタイプペアが存在し\n#####    そのハプロタイプペアを弁別するためにtagSNPとして選ばれたSNPの個数は$nummustsnpです\n";

#%mustsnpに登録されたSNPによって弁別されるペアは、以降の解析より排除する

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

#非mustsnpによって、%pairhash2個のハプロタイプペアを弁別することになる
#そのときに対象とされるSNP数が$ns2,haplotype本数が$nh2,ハプロタイプペアが%pairhash2,その数が$pairnum2,tagSNPの最少数が$minreq2,最多数が$maxreq2となる
$ns2=$ns1-$nummustsnp;
$nh2=$nh1;
#$nh2,$ns2からtagSNPの最少必要数$minreq2、最多必要数を$maxreq2を計算する

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

#tagSNPの最多必要数$maxreqを計算する
if($nh2>$ns2){
 $maxreq2=$ns2;
}else{
 $maxreq2=$nh2;
}

print "#    #   総当り検索によって適当なSNPセットを得るための検索を開始します。\n#     #  その解析対象SNP数は$ns2個です\n################################################################\n";

#漸増法･全件検索tagsnp探索
#探索対象は:
#　%pairhash2の全てを弁別できる
#　$ns2個のSNPの中から選ばれたSNPセットによって弁別する
#　最少数$minreq2から探索を開始する
#　解が得られたら、そのときのtagSNP数のSNP組み合わせについては、すべてのSNPの組み合わせについて探索し、
#　それが終了した時点で探索は終了する

#　次のような組み合わせ発生サブルーチン　combination_up(本ソースの末尾)を用いる
#　combination_upは、引数として自然数 N と、種数列セット、種数列セットの数列長より1つ長い
#　数列セットを返す。ただし、数列の発生は、{1,2,3,...,N}の数列の範囲内で発生させるものとする

#ハッシュ化されている解析対象SNPをリスト化する
$j=0;
for($i=0;$i<$ns0;$i++){
 unless(defined $group0{$i}){
  unless(defined $mustsnp{$i}){
   $analsnp[$j]=$i;
   $j++;
  }
 }
}

#サブルーチンcombination_upに与える初期値は$ns2,@comblist
#$minreq2のうち、mustsnpの数を減じる


#正解の探索は、必要最少数のSNPセットから開始するべきである。
#ただし、必ず含まれなくてはならないSNP数($nummustsnp)が、必要最少数よりも多いときは、
#すでに必要最少数SNPにて、弁別が終了しているかどうかを先に判断する
#また、必要最少数にあらたにSNPを追加して、全ハプロタイプペアを弁別しているかの検索をかけるにあたって、
#解析対象SNPセットから何個のSNPを選ぶ段階から始めるべきかを$minstart数として、定義する
#もし$numustsnpがすでに必要最少数以上だったら、mustのセットにて弁別できていないかどうかを確認し
#その上で、追加SNPを１個から開始する($minstar=1)

$minstart=$minreq2-$nummustsnp;
if($minstart<=0){
 $minstart=1;
}


# subroutin combination_upは、「組み合わせ」を発生させるもととなる自然数と、あるリストを受け取る
# そのリストには、組み合わせ数をタブ区切りの文字列で所有した　０番地と
# その数列に含まれる最大数に１を加えた値（１番地)
# および、N個の数からM個の数を取り出す組み合わせの「組み合わせ数」を２番地に持つ

$comblist[0][0]="\t";#組み合わせのタブ区切り格納庫
$comblist[0][1]=0;#組み合わせ数列に参加している最大数+1
$comblist[0][2]=1;#組み合わせの数

#以下の処理は、最少SNP数のSNPを得るための「組み合わせ」を発生させるためのループ処理である
#subroutin combination_upにより1ループごとに組み合わせを構成するSNP数が１個増える仕組みである
@arg2=@comblist;
for($i=0;$i<$minstart;$i++){
 $arg1=$ns2;
 @arg2=&combination_up($arg1,@arg2);
}


#mustsnpで弁別されるハプロタイプペアを登録
#もし、このハプロタイプペアの数が、弁別されるべきハプロタイプペアの値に等しければ
#それは、すでに得られるべき解であり、しかも唯一解である

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
if($mustdiscrd==$paircount1){#mustセットが解であるかの判定
 $mustisans=1;
}
if($mustisans==1){
 foreach $key (keys %mustsnp){
  push(@mustanswer, $key);
 }
 @finalanswer=@mustanswer;

 @finalanswer=sort { $a <=> $b }@finalanswer;
 print "\n\n-----------------------\n<<<<<<<<最適解です>>>>>>>>\n\ntagSNP数は$nummustsnp個です\nSNP番号は0から始まる数値ですのでご注意ください\ntagSNPの組み合わせは\n-----------------------\n";
 #print "@finalanswer\n";
 #print "@snpid\n";
 print "SNP番号	SNP名\n";

 for($k=0;$k<@finalanswer;$k++){


  print "$finalanswer[$k]\t$snpid[$finalanswer[$k]]\n";
 }
 print "\n";
 print "-----------------------\n";
 print "上記セットはtagSNPのセットの一部です\nRepresenting SNPのみで作成されています\n以下にそれ以外のSNPセットを知るための交換可能なSNPのペアを示します\n";
 print "RepSNPと取り替えられるSNP番号\tRepresenting SNP番号\n";
 foreach $key(keys %group0){
  print "$key\t$group0{$key}\n";
 }
 print "RepSNPと取り替えられるSNP名\tRepresenting SNP名\n";
 foreach $key(keys %group0){
  print "$snpid[$key]\t$snpid[$group0{$key}]\n";
 }


}elsif($mustisans!=1){#解析対象SNPによる探索に入る


 #$minreq以上のSNPの組み合わせからは、総当りでtagSNPセットとして適切かどうかを判断する
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
 #tagSNPの数を１つ増やす。

 @arg2=&combination_up($arg1,@arg2);
 $numtagsnp++;

  if($numtagsnp>$maxreq1){
   $noans="no_answer\n";
   print "$noans";
   last;
  }
 }

 #解の出力

 foreach $key (keys %mustsnp){
  push(@mustanswer, $key);
 }

#tagSNP数は
 $numberoftagsnp=$numtagsnp-1+$nummustsnp;
 print "\n\n-----------------------\n<<<<<<<<最適解です>>>>>>>>\n\ntagSNP数は$numberoftagsnp個です\nSNP番号は0から始まる数値ですのでご注意ください\ntagSNPの組み合わせは\n-----------------------\n";

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
  print "SNP番号　SNP名\n";
  for($k=0;$k<@finalanswer;$k++){
   print "$finalanswer[$k]\t$snpid[$finalanswer[$k]]\n";
  }
  print "\n";

 }
 print "-----------------------\n";
 print "上記セットはtagSNPのセットの一部です\nRepresenting SNPのみで作成されています\n以下にそれ以外のSNPセットを知るための交換可能なSNPのペアを示します\n";
 print "RepSNPと取り替えられるSNP番号\tRepresenting SNP番号\n";
 foreach $key(keys %group0){
  print "$key\t$group0{$key}\n";
 }
 print "RepSNPと取り替えられるSNP名\tRepresenting SNP名\n";
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
# HTMLの終わりの部分を書き出します。
#
print "</XMP>\n";
print "</BODY>\n";
print "</HTML>\n";
print "\n";


