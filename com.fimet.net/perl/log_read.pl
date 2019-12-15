#perl log.pl 'p$INT-BR-M2-01.log' '2019-06-14 14:36:18.481'

my $logName = undef;
my $timestamp = undef;
if ($#ARGV + 1 > 0) {
	$logName= $ARGV[0];
}
if ($#ARGV + 1 > 1) {
	$timestamp= $ARGV[1];
}
if (defined($logName)) {
	my $cmd = "cat '/posprodu/standin/runtime/logs/$logName' |";
	open DATA, "$cmd" or die "";

	my $matches = 0;
	if (!defined($timestamp) || isBeforeToday($timestamp)) {
		$matches = 1;
		foreach $line (<DATA>) {
			print $line;
		}
	} else {
		my $prevLine;
		foreach $line (<DATA>) {
			if ($matches > 0){
				print $line;
			} else {
				$matches = index($line,$timestamp) > 0 ? 1 : 0;
				if ($matches > 0){
					print "\n\n";
					if (defined($prevLine)){
						print $prevLine;
					}
					print $line;
				}
				$prevLine = $line;
			}
		}
	}
	close DATA;
	if ($matches == 0) {
		open DATA, "$cmd" or die "";
		foreach $line (<DATA>) {
			print $line;
		}
		close DATA;
	}
}

sub isBeforeToday {
	my ($timestamp) = @_;
	# print ("Hello-$timestamp-\n");
	$date = substr($timestamp,0,10);# yyyy-mm-dd
	($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime();
	my $now = sprintf("%04d-%02d-%02d",$year+1900,$mon+1,$mday);
	# print ("$date=$now\n");
	return $date lt $now;
}