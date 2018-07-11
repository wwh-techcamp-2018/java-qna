INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO QUESTION (id, writer_id, title, contents, created_date, deleted) VALUES (3, 1, 'test', 'testcontent', '2018-07-10 17:30:00.00', false);
INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, deleted) VALUES (1, 1, 3, 'contentsdasdsad', '2018-07-10 17:40:00.00', false);
INSERT INTO ANSWER (id, writer_id, question_id, contents, created_date, deleted) VALUES (2, 2, 3, '나는 산지기야!!', '2018-07-10 17:40:00.00', false);