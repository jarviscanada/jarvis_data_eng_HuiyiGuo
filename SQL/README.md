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
a) 

b)

c)

###### Questions 5: Aggregation
