INSERT INTO USER (id, user_id, password, name, email) VALUES (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO QUESTION(id, title, contents, writer_id, deleted) VALUES (1, '첫번째 질문', '첫번째 내용', 1, FALSE);
INSERT INTO QUESTION(id, title, contents, writer_id, deleted) VALUES (2, '두번째 질문', '두번째 내용', 2, false);

INSERT INTO ANSWER(id, question_id, contents ,writer_id, deleted) VALUES (1, 1, '자바지기의 첫번째 답변', 1, false);
