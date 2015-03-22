# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  name                      varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  type_id                   bigint,
  team_id                   bigint,
  constraint pk_account primary key (id))
;

create table criteria (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_criteria primary key (id))
;

create table rating (
  id                        bigint not null,
  account_id                bigint,
  criteria_id               bigint,
  rating                    integer,
  constraint pk_rating primary key (id))
;

create table screenshot (
  id                        bigint not null,
  team_id                   bigint,
  url                       varchar(255),
  constraint pk_screenshot primary key (id))
;

create table team (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  logo                      varchar(255),
  constraint pk_team primary key (id))
;

create table user_type (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_user_type primary key (id))
;

create sequence account_seq;

create sequence criteria_seq;

create sequence rating_seq;

create sequence screenshot_seq;

create sequence team_seq;

create sequence user_type_seq;

alter table account add constraint fk_account_type_1 foreign key (type_id) references user_type (id);
create index ix_account_type_1 on account (type_id);
alter table account add constraint fk_account_team_2 foreign key (team_id) references team (id);
create index ix_account_team_2 on account (team_id);
alter table rating add constraint fk_rating_account_3 foreign key (account_id) references account (id);
create index ix_rating_account_3 on rating (account_id);
alter table rating add constraint fk_rating_criteria_4 foreign key (criteria_id) references criteria (id);
create index ix_rating_criteria_4 on rating (criteria_id);
alter table screenshot add constraint fk_screenshot_team_5 foreign key (team_id) references team (id);
create index ix_screenshot_team_5 on screenshot (team_id);



# --- !Downs

drop table if exists account cascade;

drop table if exists criteria cascade;

drop table if exists rating cascade;

drop table if exists screenshot cascade;

drop table if exists team cascade;

drop table if exists user_type cascade;

drop sequence if exists account_seq;

drop sequence if exists criteria_seq;

drop sequence if exists rating_seq;

drop sequence if exists screenshot_seq;

drop sequence if exists team_seq;

drop sequence if exists user_type_seq;

