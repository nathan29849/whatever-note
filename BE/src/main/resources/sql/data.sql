SET AUTOCOMMIT = 0;

-- MEMBER
INSERT INTO member(email, nickname, profile_image, unique_id)
VALUES ('hgd1234@naver.com', '홍길동', 'https://dummy-image.co.kr', '12-324-523-122-11');


-- NOTE
INSERT INTO note(created_at, deleted, updated_at, note_order, title, member_id)
VALUES ('2022-06-04 00:00:01', false, '2022-06-04 00:00:01', 1000, '첫번째 노트', 1);

INSERT INTO note(created_at, deleted, updated_at, note_order, title, member_id)
VALUES ('2022-06-05 00:00:01', false, '2022-06-05 00:00:01', 2000, '두번째 노트', 1);

INSERT INTO note(created_at, deleted, updated_at, note_order, title, member_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 3000, '세번째 노트', 1);

-- CARD
INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-05 00:00:01', false, '2022-06-05 00:00:01', 1000, 'Card 1-1', 1);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-05 00:00:01', false, '2022-06-05 00:00:01', 2000, 'Card 1-2', 1);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-05 00:00:01', false, '2022-06-05 00:00:01', 3000, 'Card 1-3', 1);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 1000, 'Card 2-1', 2);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 2000, 'Card 2-2', 2);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 3000, 'Card 2-3', 2);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-07 00:00:01', false, '2022-06-07 00:00:01', 1000, 'Card 3-1', 3);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-07 00:00:01', false, '2022-06-07 00:00:01', 2000, 'Card 3-2', 3);

INSERT INTO card(created_at, deleted, updated_at, card_order, title, note_id)
VALUES ('2022-06-07 00:00:01', false, '2022-06-07 00:00:01', 3000, 'Card 3-3', 3);

-- CONTENT
INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 1000, 'Content 1-1', false, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 2000, 'Content 1-2', false, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 3000, 'Content 1-3', false, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 4000, 'Content 1-4', false, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 5000, 'Content 1-5', false, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 6000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 7000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 8000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 9000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-06 00:00:01', false, '2022-06-06 00:00:01', 10000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 1);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 1000, 'Content 2-1', false, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 2000, 'Content 2-2', false, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 3000, 'Content 2-3', false, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 4000, 'Content 2-4', false, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 5000, 'Content 2-5', false, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 6000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 7000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 8000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 9000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 10000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 2);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 1000, 'Content 3-1', false, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 2000, 'Content 3-2', false, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 3000, 'Content 3-3', false, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 4000, 'Content 3-4', false, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 5000, 'Content 3-5', false, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 6000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 7000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 8000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 9000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 3);

INSERT INTO content(created_at, deleted, updated_at, content_order, info, is_image, card_id)
VALUES ('2022-06-10 00:00:01', false, '2022-06-10 00:00:01', 10000, 'https://velog.velcdn.com/images/nathan29849/post/a0e581fb-1928-4e5d-9820-7343e9725a71/image.png', true, 3);

SET AUTOCOMMIT = 1;
