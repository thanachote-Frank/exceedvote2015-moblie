# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  type_id                   bigint,
  team_id                   bigint,
  constraint pk_account primary key (id))
;

create table criteria (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_criteria primary key (id))
;

create table rating (
  id                        bigint auto_increment not null,
  account_id                bigint,
  criteria_id               bigint,
  team_id                   bigint,
  rating                    integer,
  constraint pk_rating primary key (id))
;

create table screenshot (
  id                        bigint auto_increment not null,
  team_id                   bigint,
  url                       varchar(255),
  constraint pk_screenshot primary key (id))
;

create table setting (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  is_activated              tinyint(1) default 0,
  constraint pk_setting primary key (id))
;

create table team (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  logo                      varchar(255),
  constraint pk_team primary key (id))
;

create table time_out (
  id                        bigint auto_increment not null,
  date_time                 datetime,
  constraint pk_time_out primary key (id))
;

create table user_type (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  weight                    integer,
  constraint pk_user_type primary key (id))
;

alter table account add constraint fk_account_type_1 foreign key (type_id) references user_type (id) on delete restrict on update restrict;
create index ix_account_type_1 on account (type_id);
alter table account add constraint fk_account_team_2 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_account_team_2 on account (team_id);
alter table rating add constraint fk_rating_account_3 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_rating_account_3 on rating (account_id);
alter table rating add constraint fk_rating_criteria_4 foreign key (criteria_id) references criteria (id) on delete restrict on update restrict;
create index ix_rating_criteria_4 on rating (criteria_id);
alter table rating add constraint fk_rating_team_5 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_rating_team_5 on rating (team_id);
alter table screenshot add constraint fk_screenshot_team_6 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_screenshot_team_6 on screenshot (team_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table criteria;

drop table rating;

drop table screenshot;

drop table setting;

drop table team;

drop table time_out;

drop table user_type;

SET FOREIGN_KEY_CHECKS=1;

