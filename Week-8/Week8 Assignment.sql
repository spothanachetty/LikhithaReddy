create table Person
(
per_id NUMBER(6),
per_name varchar(10),
per_age NUMBER(4),
primary key(per_id)
);
create SEQUENCE Persons_sequence MINVALUE 1 start with 1
  INCREMENT BY 1 MAXVALUE 1000;


create table MainTask
(
mt_id NUMBER(4),
mt_name varchar2(10),
mt_des varchar2(20),
per_id NUMBER(6),
primary key(mt_id),
foreign key(per_id)references Person(per_id)
);
create SEQUENCE MainTasks_sequence MINVALUE 1 start with 1
  INCREMENT BY 1 MAXVALUE 1000;


create table SubTask
(
mt_id NUMBER(4),
st_id NUMBER(4),
st_name varchar2(10),
st_ehours NUMBER(20),
per_id NUMBER(6),
primary key(st_id),
foreign key(per_id)references Person(per_id),
foreign key(mt_id)references  MainTask(mt_id)
);
create SEQUENCE SubTasks_sequence MINVALUE 1 start with 1
  INCREMENT BY 1 MAXVALUE 1000;
 
  
DROP TABLE Person;
DROP TABLE MainTask;
DROP TABLE SubTask;


--procedures
CREATE OR REPLACE PROCEDURE insert_person(pr_id out number,pr_name VARCHAR,pr_age NUMBER)
IS
BEGIN
select Persons_sequence.nextval into pr_id from dual;
INSERT  INTO PERSON(per_id,per_name,per_age) VALUES (pr_id,pr_name,pr_age);
COMMIT;
END; 
/


CREATE OR REPLACE PROCEDURE insert_maintask(mt1_id out number,mt1_name varchar2,mt1_des varchar2,pr_id in NUMBER)
IS
BEGIN
select MainTasks_sequence.nextval into mt1_id from dual;
INSERT  INTO MainTask(mt_id ,mt_name ,mt_des,per_id)
VALUES (mt1_id,mt1_name,mt1_des,pr_id);
COMMIT;
END; 
/
  
  
CREATE OR REPLACE PROCEDURE insert_subtask(mt1_id in NUMBER,st1_id out number,st1_name varchar2,st1_ehours NUMBER,pr_id in NUMBER)
IS
BEGIN
select SubTasks_sequence.nextval into st1_id from dual;
INSERT  INTO SubTask(mt_id,st_id,st_name ,st_ehours,per_id)
VALUES (mt1_id,st1_id,st1_name,st1_ehours,pr_id);
COMMIT;
END; 
/


CREATE OR REPLACE PROCEDURE insert_subtask(st2_id out number,st2_name varchar2,st2_ehours NUMBER,pr_id in NUMBER)
IS
BEGIN
select SubTasks_sequence.nextval into st2_id from dual;
INSERT  INTO SubTask(st_id,st_name ,st_ehours,per_id)
VALUES (st2_id,st2_name,st2_ehours,pr_id);
COMMIT;
END; 
/

drop SEQUENCE Persons_sequence;
drop SEQUENCE MainTasks_sequence;

SELECT * FROM person;
SELECT * FROM MainTask;
SELECT * FROM SubTask;