#perl /export/home/desarrollo/msb5814/perl/desc_read.pl 'desc100319.pos.LR.4' 0


my $descName = undef;
my $descLine = undef;
if ($#ARGV + 1 > 0) {
	$descName= $ARGV[0];
}
if ($#ARGV + 1 > 1) {
	$descLine= $ARGV[1]-0;
}
if (defined($descName)) {
	my $descTxt = "/posprodu/standin/runtime/desc_linea/$descName.txt";
	my $cmd = undef;
	if (-e $descTxt) {
		$cmd = "cat '/posprodu/standin/runtime/desc_linea/$descName.txt' |";
	} else {
		$descTxt = "/posprodu/standin/runtime/desc_linea/$descName.desa";
		$cmd = "cat '$descTxt' |";
	}
	open DATA, "$cmd" or die "";

	if (!defined($descLine)) {
		foreach $line (<DATA>) {
			print $line;
		}
	} else {
		my $lineNumber = 0;
		foreach $line (<DATA>) {
			if ($lineNumber >= $descLine){
				print $line;
			}
			$lineNumber++;
		}
	}

	close DATA;
}