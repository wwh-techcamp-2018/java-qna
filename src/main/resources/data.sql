INSERT INTO USER (id, user_id, password, name, email) VALUES (null, 'aa', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO USER (id, user_id, password, name, email) VALUES (null, 'bb', 'test', '산지기', 'sanjigi@slipp.net');
insert
    into
        question
        (id, contents, title, write_time, writer_id, deleted, answer_count)
    values
        (null, 'testContents', 'test', '2018-07-11 18:55', 1, false, 2);


insert
    into
        answer
        (id, contents, question_id, write_time, writer_id, deleted)
    values
        (null, 'contents', 1, '2018-07-12 18:55', 1, false);

insert
    into
        answer
        (id, contents, question_id, write_time, writer_id, deleted)
    values
        (null, 'contents2', 1, '2018-07-12 19:55', 1, false);
