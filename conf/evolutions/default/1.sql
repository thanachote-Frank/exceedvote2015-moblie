# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  name                      varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  team_id                   bigint,
  constraint pk_account primary key (id))
;

create table team (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  logo                      varchar(255),
  constraint pk_team primary key (id))
;

create sequence account_seq;

create sequence team_seq;

alter table account add constraint fk_account_team_1 foreign key (team_id) references team (id);
create index ix_account_team_1 on account (team_id);



# --- !Downs

drop table if exists account cascade;

drop table if exists team cascade;

drop sequence if exists account_seq;

drop sequence if exists team_seq;

