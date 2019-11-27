--Creating table for AirB&B listings
drop table if exists listings;

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
