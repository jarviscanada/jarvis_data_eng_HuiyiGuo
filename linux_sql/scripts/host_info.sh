#! /bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
  echo 'host_info.sh needs 5 arguments'
  exit 1
fi

lscpu_out=`lscpu`
hostname=$(hostname -f)

cpu_number=$(lscpu  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "Model name:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "CPU MHz:" | awk '{print $3}' | xargs) 
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print $3}' | sed 's/.$//' | xargs)
total_mem=$(grep MemTotal /proc/meminfo | awk '{print $2}' | xargs)

timestamp=$(vmstat -t | awk '{print $18,$19}' | tail -1| xargs)

insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp')";

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?


