
-- INSERT INTO MEMBER(MEMBER_ID, CREATED_AT, MODIFIED_AT,EMAIL,IMAGE_URL,INTRODUCE,PASSWORD,USERNAME) VALUES(1,now(),now(),'gahee@naver.com','thumb','hi','123','gahee');
-- CREATE TABLE TEST AS SELECT * FROM CSVREAD('test.csv');
-- INSERT INTO SONG AS SELECT * FROM CSVREAD('melon_data.csv');
-- CREATE TABLE TEST AS SELECT * FROM CSVREAD('classpath:melon_data.csv');
-- INSERT INTO SONG('title','singer','image_url') AS SELECT * FROM CSVREAD('/Users/gaheechoi/Desktop/project/spring-project/songmelier/src/main/resources/melon_data.csv');
INSERT into SONG (SONG_ID ,IMAGE_URL , published_date,singer,title) SELECT * FROM CSVREAD('/Users/gaheechoi/Desktop/project/spring-project/songmelier/src/main/resources/melon_data.csv');
UPDATE SONG set created_at = now(), modified_at =now(), bookmark_count=0, comment_count=0, favor_count=0,  HIGH_DIFFICULT='',LOW_DIFFICULT='',MOOD='', RAP_DIFFICULT='';