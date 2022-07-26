# Linux Cluster Monitoring Agent

## Introduction
The Linux Cluster Monitoring Agent is a minimum viable product (MVP) that provides metrics that can be used to get information about system activities. 
It can be used by the LCA team to meet their business needs (e.g. collect hardware specification data, resource usage data, find the possible causes of a performance problem, etc.)
This project includes 3 bash scripts. One of those is used to create, stop, and start the PSQL Docker instance. The other two are used to gather data. Collected data will automatically insert into a RDBSM database.
Additionally, this project has some sql scripts that let the LCA team do various administrative queries to more effectively track cluster consumption. 
These queries give the user the ability to categorize hosts based on hardware information, the average memory usage over a 5-minute period for each host, and check for host failures using the crontab process.

The technologies used:

* bash scripts (used to gather the hardware specification data, resource usage data, and manage docker)
* Postgres v9.6 database (stores the collected data)
* psql v9.2.24 (PostgreSQL client application used to administer the Postgres database)
* Crontab (monitors resource usage every minute and check failures)
* git v1.8.3.1 (version control software)
* Docker v20.10.17 (container running the Postgres database)
* IntelliJ IDEA (used as the IDE to edit files)
* Jarvis Remote Desktop (JRD) (development and testing environment running CentOS 7)
* Google Cloud Platform (GCP) (provided the instance to run the JRD)


## Quick Start
* Start a psql instance using `psql_docker.sh`
```bash
# create a psql docker container with the given username and password.
./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```

* Create tables using `ddl.sql`
```bash
# Execute ddl.sql script on the host_agent database againse the psql instance
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

* Insert hardware specs data into the DB using `host_info.sh`
```bash
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```

* Insert hardware usage data into the DB using `host_usage.sh`
```bash
./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```

* Crontab setup
```bash
bash> crontab -e
#add this to crontab
* * * * * bash /absolute_path_of_script/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
```


## Implemenation
