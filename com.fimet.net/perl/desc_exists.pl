#perl /export/home/desarrollo/msb5814/perl/desc_exists.pl 'desc100319.pos.LR.3'

my $descName = undef;
if ($#ARGV + 1 > 0) {
	$descName= $ARGV[0];
}

if (defined($descName)) {
	my $descTxt = "/posprodu/standin/runtime/desc_linea/$descName.txt";
	if (-e $descTxt) {
		print "true";
	} else {
		print "false";
	}
}