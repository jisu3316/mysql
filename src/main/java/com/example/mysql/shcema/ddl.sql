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

select count(id)
from POST;

explain
SELECT createdDate, memberId, count(id) as count
FROM POST
WHERE memberId = 2 and createdDate between '2000-01-01' and '2000-01-31'
GROUP BY memberId, createdDate;

explain SELECT createdDate, memberId, count(id) as count
        FROM POST use index (POST__index_member_id)
        WHERE memberId = 2 and createdDate between '2000-01-01' and '2000-01-31'
        GROUP BY memberId, createdDate;

explain SELECT createdDate, memberId, count(id) as count
        FROM POST use index (POST__index_created_date)
        WHERE memberId = 2 and createdDate between '2000-01-01' and '2023-01-31'
        GROUP BY memberId, createdDate;

explain SELECT createdDate, memberId, count(id) as count
        FROM POST use index (POST__index_member_id_created_date)
        WHERE memberId = 2 and createdDate between '2000-01-01' and '2023-01-31'
        GROUP BY memberId, createdDate;

SELECT *  FROM POST;


select memberId, count(id)
from POST
group by memberId;

select createdDate, count(id)
from POST
group by createdDate
order by 2 desc;

select count(distinct createdDate)
from POST;

# page
select *
from POST
where memberId = 2
order by id desc
limit 2
offset 4;

