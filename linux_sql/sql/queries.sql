<<<<<<< HEAD
SELECT cpu_number,id,total_mem
=======
ELECT cpu_number,id,total_mem
>>>>>>> feature/psql_docker
FROM host_info
GROUP BY cpu_number, total_mem
ORDER BY cpu_number, total_mem DESC;

CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;


SELECT host_id,host_name,round5 (timestamp) AS timestamp, ((AVG(total_mem - memory_free)/ total_mem) * 100) AS avg_used_mem_percentage
FROM host_usage
JOIN host_info on host_usage.host_id = host_info.id
GROUP BY timestamp, host_id
ORDER BY timestamp, host_id;

SELECT host_id, round5 (timestamp) AS timestamp, COUNT(*) AS num_data_points
FROM host_usage
GROUP BY timestamp, host_id
HAVING COUNT(*) < 3
ORDER BY timestamp, host_id;

