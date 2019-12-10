-- Add initial values to DB

-- TLP Inserts
INSERT INTO tlps (id, base_url) values (NULL, 'https://xscratch-videos.s3.us-east-2.amazonaws.com');

-- Video Inserts
-- Note that we are now using FULL urls instead of relative to our bucket
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Computer-Working', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C1-Computer_Working.ogg', 'Computer Working', NULL);  -- 1
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Mental-Disease', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C2-Mental_Disease.ogg', 'Mental disease has affected the entire crew', NULL);  -- 2
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Kinky-Hand', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C3-Kinky_Hand.ogg', 'My hand just passed through a man and a table', NULL);  -- 3
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Chekov-Screaming', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C4-Chekov_Screaming.ogg', 'Screaming (AHHH)', NULL);  -- 4
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'I-Like-Him', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C5-I_Like_Him.ogg', 'I like him', NULL);  -- 5
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Chekov', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C6-Chekov.ogg', 'Chekov', NULL);  -- 6
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Dismissed', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C7-Dismissed.ogg', 'Dismissed', NULL);  -- 7
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Captains-Quarters', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C8-Captains_Quarters.ogg', 'There is a duty to be performed in the Captains quarters, which requires our presence', NULL);  -- 8
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Scotch', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C9-Scotch.ogg', 'Does it make a good mix with Scotch?', NULL);  -- 9
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Oral', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C10-Oral.ogg', 'Ive ordered it orally', NULL);  -- 10
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'SPOCK', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C11-SPOCK.ogg', 'Spock', NULL);  -- 11
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Chekov-Screaming-Again', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C12-Chekov_Screams_Again.ogg', 'Screaming (AHHH)', NULL);  -- 12
INSERT INTO videos (id, name, is_remote, is_available, url, dialogue, tlp_id) values (NULL, 'Space Breaking', false, false, 'https://xscratch-videos.s3.us-east-2.amazonaws.com/C13-Space_Breaking.ogg', 'Space itself is literally breaking up', NULL);  -- 13


-- Playlist Inserts
INSERT INTO playlists (id, name) values (NULL, 'Best-of-Chekov'); -- 1

-- Character Inserts
INSERT INTO characters (id, name) values (NULL, 'Chekov');  -- 1
INSERT INTO characters (id, name) values (NULL, 'Spock');  -- 2
INSERT INTO characters (id, name) values (NULL, 'Kirk');  -- 3
INSERT INTO characters (id, name) values (NULL, 'Uhura');  -- 4
INSERT INTO characters (id, name) values (NULL, 'Scotty');  -- 5
INSERT INTO characters (id, name) values (NULL, 'Bones');  -- 6
INSERT INTO characters (id, name) values (NULL, 'Sulu');  -- 7
INSERT INTO characters (id, name) values (NULL, 'McCoy');  -- 8
INSERT INTO characters (id, name) values (NULL, 'Computer');  -- 9

-- VCjoin table inserts
INSERT INTO vcjoin (video_id, character_id) values (1, 9);
INSERT INTO vcjoin (video_id, character_id) values (2, 3);
INSERT INTO vcjoin (video_id, character_id) values (2, 8);
INSERT INTO vcjoin (video_id, character_id) values (3, 8);
INSERT INTO vcjoin (video_id, character_id) values (4, 1);
INSERT INTO vcjoin (video_id, character_id) values (5, 1);
INSERT INTO vcjoin (video_id, character_id) values (5, 2);
INSERT INTO vcjoin (video_id, character_id) values (6, 2);
INSERT INTO vcjoin (video_id, character_id) values (7, 5);
INSERT INTO vcjoin (video_id, character_id) values (8, 8);
INSERT INTO vcjoin (video_id, character_id) values (9, 5);
INSERT INTO vcjoin (video_id, character_id) values (10, 8);
INSERT INTO vcjoin (video_id, character_id) values (11, 8);
INSERT INTO vcjoin (video_id, character_id) values (12, 1);
INSERT INTO vcjoin (video_id, character_id) values (13, 2);


-- PVjoin table inserts
INSERT INTO pvjoin (playlist_id, video_id, video_order) values (1, 4, 1);
INSERT INTO pvjoin (playlist_id, video_id, video_order) values (1, 12, 2);
INSERT INTO pvjoin (playlist_id, video_id, video_order) values (1, 5, 3);