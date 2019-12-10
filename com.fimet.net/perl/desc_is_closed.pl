#perl /export/home/desarrollo/msb5814/perl/desc_is_closed.pl 'desc100319.pos.LR.3.AD.txt'

my $descName = undef;
if ($#ARGV + 1 > 0) {
	$descName= $ARGV[0];
}

if (defined($descName)) {
	my $descTxt = "/posprodu/standin/runtime/desc_linea/$descName.desa";
	if (-e $descTxt) {
		print "true";
	} else {
		print "false";
	}
}