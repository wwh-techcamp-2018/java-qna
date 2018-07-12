INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'p', 'a', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'a', 'a', '산지기', 'sanjigi@slipp.net');
INSERT INTO QUESTION (id, writer_id, title, contents, deleted) VALUES (3, 1, '첫번째 제목', '첫번째 내용', false);
INSERT INTO QUESTION (id, writer_id, title, contents, deleted) VALUES (4, 2, '두번째 제목', '두번째 내용', false);
INSERT INTO ANSWER (writer_id, question_id, contents, deleted) VALUES (1, 3, '첫번째 글의 첫번째 댓글', false);
INSERT INTO ANSWER (writer_id, question_id, contents, deleted) VALUES (1, 4, '두번째 글의 첫번째 댓글', false);
INSERT INTO ANSWER (writer_id, question_id, contents, deleted) VALUES (2, 4, '두번째 글의 두번째 댓글', false);
