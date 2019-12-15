#perl /export/home/desarrollo/msb5814/perl/log_read2.pl 'p$INT-BR-M2-01.log' 0
use Fcntl qw( SEEK_SET );

my $logName = undef;
my $logSeek = 0;
if ($#ARGV + 1 > 0) {
	$logName= $ARGV[0];
}
if ($#ARGV + 1 > 1) {
	$logSeek= $ARGV[1]-0;
}
if (defined($logName)) {
	open (DATA, '<', "/posprodu/standin/runtime/logs/$logName") or die " ERROR ";
 	seek(DATA, $logSeek, SEEK_SET);

	while(read(DATA, $buffer, 1024)) {
		print $buffer;
	}
	close DATA;
}