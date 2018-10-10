insert into t_memo(id, title, description, done, updated) values
(1, 'memo belanja', '001 deskripsi', false, '2018-10-10 14:04:30')
(1, 'memo pekerjaan', '002 software engineer java', false, '2018-10-10 15:08:40')
(1, 'memo bepergian', '003 travel', false, '2018-10-10 16:07:60')
(1, 'memo rumahsakit', '004 sakit', false, '2018-10-10 17:06:50')
(1, 'memo pekerjaan', '005 pekerjaan', false, '2018-10-10 18:05:07')

INSERT INTO `user` (id, `name`, password, email, admin_flag) VALUES
  (1, 'valentinus', '{bcrypt}$2a$10$yiIGwxNPWwJ3CZ0SGAq3i.atLYrQNhzTyep1ALi6dbax1b1R2Y.cG', 'kkamimura@example.com', TRUE),
  (2, 'silalahi', '{bcrypt}$2a$10$9jo/FSVljst5xJjuw9eyoumx2iVCUA.uBkUKeBo748bUIaPjypbte', 'rsakuma@example.com', FALSE),
  (3, 'sinabutar', '{bcrypt}$2a$10$1OXUbgiuuIi3SOO3t.jyZOEY66ELL03dRcGpAKWql8HBXOag4YZ8q', 'tyukinaga@example.com', FALSE)
;

INSERT INTO `user` (id, `name`, password, email, admin_flag) VALUES
  (4, 'ronaldo de jeneiro', '{pbkdf2}0963ebe5b7508e9f0de55e7480ee7b87c623754ea18a94f25a20cc213a6341695d6ad38d18ff8f25', 'ronaldo@example.com', TRUE),
  (5, 'beethoven', '{pbkdf2}998f1e8af4662f9c7e44bad5af69e916f0ab6cf6af1a1a38b0e667f5f7b9f5bb0d3700e2eacfcf72', 'beethoven@example.com', FALSE)
;