#### Create an external table, called wdi_csv_text
```
DROP TABLE IF EXISTS wdi_csv_text;
CREATE EXTERNAL TABLE wdi_csv_text
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
LOCATION 'hdfs:///user/sherry/hive/wdi/wdi_csv_text';
```

#### INSERT query that loads data from wdi_gs table to wdi_csv_text table
```
INSERT OVERWRITE TABLE wdi_csv_text
SELECT * FROM wdi_gs
```

#### Check HDFS file size for wdi_csv_text file 
```
hdfs fs -du -s -h /user/sherry/hive/wdi/wdi_csv_text
```

#### Parsing issue with the indicatorcode column
```
SELECT distinct(indicatorcode)
FROM wdi_csv_text
ORDER BY indicatorcode
LIMIT 20;
```

#### Identify issue by creating a debug table
```
CREATE EXTERNAL TABLE wdi_gs_debug
LOCATION 'gs://user/jarvis_data_eng_huiyi/datasets/wdi_2016';

SELECT * FROM wdi_gs_debug
WHERE line like "%\(\% of urban population\)\"%";
```

#### Create a view on top of wdi_opencsv_text
```
DROP VIEW IF EXISTS wdi_opencsv_text_view;

CREATE VIEW IF NOT EXISTS wdi_opencsv_text_view
AS
SELECT CAST(year AS INT), countryname, countrycode, indicatorname, indicatorcode, CAST(indicatorvalue AS FLOAT)
FROM wdi_opencsv_text;
```

#### 2015 Canada GDP Growth HQL
```
SELECT indicatorvalue AS gdp_growth_value, year, countryname 
FROM wdi_opencsv_text
WHERE indicatorname LIKE '%GDP growth%';

SELECT indicatorvalue AS gdp_growth_value, year, countryname
FROM wdi_opencsv_text
WHERE indicatorname LIKE '%GDP growth' AND year=2015 AND countryname='Canada';
```

#### Hive partitions
```
DROP TABLE IF EXISTS wdi_opencsv_text_partitions;

CREATE EXTERNAL TABLE IF NOT EXISTS wdi_opencsv_text_partitions 
(countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT) 
PARTITIONED BY(year INTEGER) 
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = "\t",
   "quoteChar"     = "'",
   "escapeChar"    = "\\"
)  
LOCATION 'hdfs:///user/sherry/hive/wdi/wdi_opencsv_text_partitions'";

SELECT indicatorvalue AS gdp_growth_value, year, countryname 
FROM wdi_opencsv_text_partitions
WHERE indicatorname LIKE'%GDP growth' AND year=2015 AND countryname='Canada';
```

####
```
```

####
```
```

####
```
```

####
```
```
