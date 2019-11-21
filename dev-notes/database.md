# Database dev notes

## Resetting schema

First copy the absolute path to `init_chekov.sql` and `populate_chekov.sql` located in the sql folder.

```
mysql -h chekovdb.cgfw4tm7ipq1.us-east-2.rds.amazonaws.com -P 3306 -u admin -p
```
enter password
```
source <path_to_init_chekov.sql>
source <path_to_populate_chekov.sql>
```