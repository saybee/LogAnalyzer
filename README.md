# LogAnalyzer
## Developed a tool which generates Log Data for past 12 hours for 1000 Servers having 2 CPUS each.
Data contains UnixTimestamp IP CPU_ID Usage
Example:
1555954632 152.110.96.193 0 65
1555954632 152.110.96.193 1 33
1555954632 87.191.122.41 0 81
1555954632 87.191.122.41 1 66

## Developed a tool which Queries data from the log file based on IP, CPU_ID,start time and end time.
Example:
QUERY 152.110.96.193 0 2019-04-22 13:36 2019-04-22 13:39

OUTPUT:
CPU0 usage on 152.110.96.193:
(2019-04-22 13:38, 148%),(2019-04-22 13:37, 65%),
