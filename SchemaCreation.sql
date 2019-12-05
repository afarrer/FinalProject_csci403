--Creating table for AirB&B listings
drop table if exists listings;
drop table if exists listing_ratings;
drop table if exists crimes;

create table city (name text primary key);
insert into city (name) values ('Austin');
insert into city (name) values ('Boston');
insert into city (name) values ('Chicago');
insert into city (name) values ('Denver');
insert into city (name) values ('Boston');
insert into city (name) values ('Hawaii');
insert into city (name) values ('Los Angeles');
insert into city (name) values ('New York');
insert into city (name) values ('Portland');
insert into city (name) values ('Salem');
insert into city (name) values ('San Francisco');
insert into city (name) values ('Minneapolis');
insert into city (name) values ('Washington');

create table listings(
	id integer Primary Key,
	listing_url text,
	picture_url text,
	neighborhood text,
	city text,
	state text,
	latitude numeric(12,9),
	longitude numeric(12,9),
	price text);

--Addings data from csv files with listing data from specific cities
\copy listings from 'listingsAustin.csv' Delimiters ',' CSV;
\copy listings from 'listingsBoston.csv' Delimiters ',' CSV;
\copy listings from 'listingsChicago.csv' Delimiters ',' CSV;
\copy listings from 'listingsDenver.csv' Delimiters ',' CSV;
\copy listings from 'listingsHawaii.csv' Delimiters ',' CSV;
\copy listings from 'listingsLosAngeles.csv' Delimiters ',' CSV;
\copy listings from 'listingsNewYork.csv' Delimiters ',' CSV;
\copy listings from 'listingsPortland.csv' Delimiters ',' CSV;
\copy listings from 'listingsSalem.csv' Delimiters ',' CSV;
\copy listings from 'listingsSanFrancisco.csv' Delimiters ',' CSV;
\copy listings from 'listingsTwinCities.csv' Delimiters ',' CSV;
\copy listings from 'listingsWashingtonDC.csv' Delimiters ',' CSV;

--Creating table to hold generated ratings. Will be populated using java code
create table listing_ratings(listing_id integer references listings(id), rating integer);

--Creating Crimes table and populating
create table crimes (city text, latitude numeric(12,9), longitude numeric(12,9));
\copy crimes (city, latitude, longitude) from '/crimes.csv' delimiter ',' csv header;
