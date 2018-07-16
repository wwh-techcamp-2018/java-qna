INSERT INTO USER (user_id, password, name, email) VALUES ('gusdk', '1234', 'hyeona', 'tech_hak@woowahan.com');
INSERT INTO USER (user_id, password, name, email) VALUES ('alstjr', '1234', 'minseok', 'tech_mss@woowahan.com');
INSERT INTO QUESTION (writer_id, title, contents, time, deleted) VALUES (1, '현아 질문1', '현아 질문1의 내용입니다.', '2018-07-15 17:30:00.00', false);
INSERT INTO QUESTION (writer_id, title, contents, time, deleted) VALUES (2, '민석 질문1', '민석 질문1의 내용입니다.', '2018-07-15 17:30:00.00', false);
INSERT INTO ANSWER (writer_id, question_id, contents, time, deleted) VALUES (1, 1, '현아 질문1의 현아 답변1 내용입니다.', '2018-07-16 17:30:00.00', false);
INSERT INTO ANSWER (writer_id, question_id, contents, time, deleted) VALUES (2, 2, '민석 질문1의 민석 답변1 내용입니다.', '2018-07-16 17:30:00.00', false);