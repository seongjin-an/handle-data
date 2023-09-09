create table Member
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    createdAt datetime not null,
    constraint member_id_uindex
        primary key (id)
);

create table MemberNicknameHistory
(
    id int auto_increment,
    memberId int not null,
    nickname varchar(20) not null,
    createdAt datetime not null,
    constraint memberNicknameHistory_id_uindex
        primary key (id)
);

create table Follow
(
    id int auto_increment,
    fromMemberId int not null,
    toMemberId int not null,
    createdAt datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_fromMemberId_toMemberId_uindex
    on Follow (fromMemberId, toMemberId);


create table POST
(
    id int auto_increment,
    memberId int not null,
    contents varchar(100) not null,
    createdDate date not null,
    createdAt datetime not null,
    constraint POST_id_uindex
        primary key (id)
);

create index POST__index_member_id
    on POST (memberId);

create index POST__index_created_date
    on POST (createdDate);
create index POST__index_member_id_created_date
    on POST (memberId, createdDate);


# --------------------------------------------------------------------------------------
select count(*)
from POST;


SELECT createdDate, memberId, count(id) as count
FROM POST
WHERE memberId = 3 AND createdDate BETWEEN '2022-01-21' AND '2022-02-01'
GROUP BY createdDate, memberId;

SELECT createdDate, memberId, count(id) as count
FROM POST use index (POST__index_member_id)
WHERE memberId = 0 AND createdDate BETWEEN '2022-01-21' AND '2022-02-01'
GROUP BY createdDate, memberId;

create index POST__index_member_id
    on POST (memberId);

create index POST__index_created_date
    on POST (createdDate);
create index POST__index_member_id_created_date
    on POST (memberId, createdDate);

drop index POST__index_member_id on POST;
drop index POST__index_created_date on POST;
drop index POST__index_member_id_created_date on POST;


select count(distinct createdDate)
from POST;

explain SELECT createdDate, memberId, count(id) as count
        FROM POST use index (POST__index_created_date)
        WHERE memberId = 3 AND createdDate BETWEEN '2022-01-21' AND '2022-02-01'
        GROUP BY createdDate, memberId;


explain SELECT createdDate, memberId, count(id) as count
        FROM POST use index (POST__index_member_id_created_date)
        WHERE memberId = 3 AND createdDate BETWEEN '2022-01-21' AND '2022-02-01'
        GROUP BY createdDate, memberId;