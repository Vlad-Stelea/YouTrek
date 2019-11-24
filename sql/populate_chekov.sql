-- Add initial values to DB

-- TLP Inserts
-- Note that the first value (id=1) will refer to our local library (TODO discuss)
INSERT INTO tlps (id, base_url) values (NULL, 'https://xscratch-videos.s3.us-east-2.amazonaws.com');


-- Video Inserts (TODO after TA verifies our segments)
-- Test video for initial DB test (TODO remove after getting the rest of them
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'testVid', false, false, '/C2-Mental_Disease.ogg', 'Mental disease has affected the entire crew', NULL);

-- Character Inserts
-- TODO inserts for potential missing characters
INSERT INTO characters (id, name) values (NULL, 'Chekov');
INSERT INTO characters (id, name) values (NULL, 'Spock');
INSERT INTO characters (id, name) values (NULL, 'Kirk');
INSERT INTO characters (id, name) values (NULL, 'Uhura');
INSERT INTO characters (id, name) values (NULL, 'Scotty');
INSERT INTO characters (id, name) values (NULL, 'Bones');
INSERT INTO characters (id, name) values (NULL, 'Sulu');


