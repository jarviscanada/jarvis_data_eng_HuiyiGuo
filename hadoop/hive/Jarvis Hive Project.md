#### Create an external table, called wdi_csv_text
```
DROP TABLE IF EXISTS wdi_csv_text;
CREATE EXTERNAL TABLE wdi_csv_text
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
LOCATION 'hdfs:///user/sherry/hive/wdi/wdi_csv_text';
```
