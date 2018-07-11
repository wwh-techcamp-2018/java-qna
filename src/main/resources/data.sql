INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'jsPark', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (3, 'ming', 'test', '김민지', 'test@slipp.net');
INSERT INTO QUESTION (id, comment, contents, title, writer_id, deleted) VALUES (1, 0, 'zzzzz', 'kkkkk', 1, false);
INSERT INTO answer (id, contents, question_id, writer_id, deleted) VALUES (1, 'zzzzz', 1, 1, false);
