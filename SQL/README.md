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


