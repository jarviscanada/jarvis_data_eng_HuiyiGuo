###### Question 1: Create tables
CREATE TABLE cd.members(
  memid INTEGER NOT NULL, 
  surname character varying(200) NOT NULL, 
  firstname character varying(200) NOT NULL, 
  address character varying(300) NOT NULL, 
  zipcode INTEGER NOT NULL, 
  telephone character varying(20) NOT NULL, 
  recommendedby INTEGER, 
  joindate timestamp NOT NULL, 
  CONSTRAINT members_pk PRIMARY KEY (memid), 
  CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby) 
      REFERENCES cd.members(memid) ON DELETE SET NULL
);

CREATE TABLE cd.bookings(
  bookid INTEGER NOT NULL, 
  facid INTEGER NOT NULL,
  memid INTEGER NOT NULL,
  starttime timestamp NOT NULL,
  slots INTEGER NOT NULL,
  CONSTRAINT bookings_pk PRIMARY KEY (bookid),
  CONSTRAINT fk_bookings_facid FOREIGN KEY (facid)
      REFERENCES cd.facilities(facid),
  CONSTRAINT fk_bookings_memid FOREIGN KEY (memid)
      REFERENCES cd.members(memid),
);

CREATE TABLE cd.facilities(
  facid INTEGER NOT NULL, 
  name character varying(100),
  membercost NUMERIC NOT NULL,
  guestcost NUMERIC NOT NULL,
  initialoutlay NUMERIC NOT NULL,
  monthlymaintenance NUMERIC NOT NULL,
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);



###### Questions 2: Modifying Data
a) Insert some data into a table
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
  
b) Insert calculated data into a table
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
SELECT 
  (
    SELECT 
      max(facid) 
    from 
      cd.facilities
  )+ 1, 
  'Spa', 
  20, 
  30, 
  100000, 
  800;
  
c) Update some existing data
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  name = 'Tennis Court 2';
  
d) Update a row based on the contents of another row
UPDATE 
  cd.facilities facs 
SET 
  membercost = (
    SELECT 
      membercost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      name = 'Tennis Court 1'
  ), 
  guestcost = (
    SELECT 
      guestcost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) 
WHERE 
  facs.facid = 1;
  
e) Delete all bookings
TRUNCATE cd.bookings; || DELETE FROM cd.bookings;

f) Delete a member from the cd.members table
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
  
g) Delete based on a subquery
DELETE FROM 
  cd.members 
WHERE 
  memid NOT IN (
    SELECT 
      memid 
    FROM 
      cd.bookings
  );


###### Questions 3: Basic
a) Classify results into buckets
SELECT 
  name, 
  CASE WHEN monthlymaintenance > 100 THEN 'expensive' 
  ELSE 'cheap' 
  END AS cost 
FROM 
  cd.facilities;

b) Combining results from multiple queries
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;



###### Questions 4: Join
a) Retrieve the start times of members' bookings
SELECT 
  starttime 
FROM 
  cd.bookings 
  INNER JOIN cd.members ON cd.bookings.memid = cd.members.memid 
WHERE 
  cd.members.surname = 'Farrell' 
  AND cd.members.firstname = 'David';
  
b) Work out the start times of bookings for tennis courts
SELECT 
  cd.bookings.starttime AS start, 
  cd.facilities.name AS name 
FROM 
  cd.bookings 
  INNER JOIN cd.facilities ON cd.bookings.facid = cd.facilities.facid 
WHERE 
  cd.facilities.name in (
    'Tennis Court 2', 'Tennis Court 1'
  ) 
  AND cd.bookings.starttime > '2012-09-21' 
  AND cd.bookings.starttime < '2012-09-22' 
ORDER BY 
  cd.bookings.starttime;
  
c) Produce a list of all members who have recommended another member
SELECT 
  DISTINCT memb.firstname, 
  memb.surname 
FROM 
  cd.members memb 
  INNER JOIN cd.members memb2 ON memb.memid = memb2.recommendedby 
ORDER BY 
  surname, 
  firstname;

d) Produce a list of all members, along with their recommender
SELECT 
  mem.firstname AS memfname, 
  mem.surname AS memsname, 
  recf.firstname AS recfname, 
  recf.surname AS recsname 
FROM 
  cd.members mem 
  LEFT OUTER JOIN cd.members recf ON recf.memid = mem.recommendedby 
ORDER BY 
  memsname, 
  memfname;

e) Produce a list of all members who have used a tennis court
SELECT 
  DISTINCT CONCAT(firstname, ' ', surname) AS member, 
  fac.name AS facility 
FROM 
  cd.members mem 
  INNER JOIN cd.bookings bookings ON mem.memid = bookings.memid 
  INNER JOIN cd.facilities fac ON bookings.facid = fac.facid 
WHERE 
  fac.name IN (
    'Tennis Court 1', 'Tennis Court 2'
  ) 
ORDER BY 
  member, 
  facility;

f) Produce a list of all members, along with their recommender, using no joins
SELECT 
  DISTINCT CONCAT(firstname, ' ', surname) AS member, 
  (
    SELECT 
      CONCAT(firstname, ' ', surname) AS recommender 
    FROM 
      cd.members rec 
    WHERE 
      rec.memid = mem.recommendedby
  ) 
FROM 
  cd.members mem 
ORDER BY 
  member;

###### Questions 5: Aggregation
a) Count the number of recommendations each member makes
SELECT 
  recommendedby, 
  COUNT(*) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER BY 
  recommendedby;

b) List the total slots booked per facility
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER by 
  facid;

c) List the total slots booked per facility in a given month
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  starttime > '2012-09-01' 
  AND starttime < '2012-10-31' 
GROUP BY 
  facid 
ORDER BY 
  SUM(slots);

d) List the total slots booked per facility per month

e) Find the count of members who have made at least one booking



###### Questions 6: String
a) Format the names of members
SELECT CONCAT(surname, ', ', firstname) AS name FROM cd.members; || SELECT surname || ', ' || firstname AS name FROM cd.members;

b) Perform a case-insensitive search
SELECT * FROM cd.facilities WHERE UPPER(name) LIKE UPPER('TENNIS%');

c) Find telephone numbers with parentheses
SELECT memid, telephone FROM cd.members WHERE telephone ~ '[()]'; 

d) Count the number of members whose surname starts with each letter of the alphabet
SELECT 
  SUBSTR(surname, 1, 1) AS letter, 
  COUNT(*) AS count 
FROM 
  cd.members 
GROUP BY 
  letter 
ORDER BY 
  letter;

