CREATE TABLE location (
	location_name TEXT NOT NULL,
	launch_location TEXT NOT NULL,
	rockets_launched TEXT NOT NULL,
	PRIMARY KEY (location_name)
);

CREATE TABLE mission (
    mission_name TEXT NOT NULL,
    mission_launch_cost TEXT NOT NULL,
    mission_type TEXT NOT NULL,
    mission_description TEXT NOT NULL,
    PRIMARY KEY (mission_name)
);

CREATE TABLE rocket (
    rocket_name TEXT NOT NULL,
    rocket_family TEXT NOT NULL,
    rocket_length TEXT NOT NULL,
    rocket_diameter TEXT NOT NULL,
    rocket_low_earth_orbit_capacity TEXT NOT NULL,
    rocket_launch_mass TEXT NOT NULL,
    rocket_description TEXT NOT NULL,
    PRIMARY KEY (rocket_name)
);

ALTER TABLE mission ADD COLUMN rocket_name TEXT NOT NULL
CONSTRAINT rocket_name REFERENCES rocket (rocket_name)
ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE agency (
    agency_name TEXT NOT NULL,
    agency_type TEXT NOT NULL,
    agency_abbreviation TEXT NOT NULL,
    agency_administration TEXT NOT NULL,
    agency_founded TEXT NOT NULL,
    agency_country TEXT NOT NULL,
    agency_spacecraft TEXT NOT NULL,
    agency_launchers TEXT NOT NULL,
    agency_description TEXT NOT NULL,
    PRIMARY KEY (agency_name)
);

ALTER TABLE rocket ADD COLUMN agency_name TEXT NOT NULL
CONSTRAINT agency_name REFERENCES agency (agency_name)
ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE launch (
	launch_title TEXT NOT NULL,
	launch_status TEXT NOT NULL,
	launch_date TEXT NOT NULL,
	PRIMARY KEY (launch_title)
);

ALTER TABLE launch ADD COLUMN rocket_name TEXT NOT NULL
CONSTRAINT rocket_name REFERENCES rocket (rocket_name)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE launch ADD COLUMN agency_name TEXT NOT NULL
CONSTRAINT agency_name REFERENCES agency (agency_name)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE launch ADD COLUMN location_name TEXT NOT NULL
CONSTRAINT location_name REFERENCES location (location_name)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE launch ADD COLUMN mission_name TEXT NOT NULL
CONSTRAINT mission_name REFERENCES mission (mission_name)
ON UPDATE CASCADE ON DELETE CASCADE;