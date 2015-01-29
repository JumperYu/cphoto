-- OAuth2.0
create database auth;
use auth;

drop table if exists access_token;
create table access_token (  
id bigint,
access_token varchar(255) NOT NULL,
token_type varchar(255),
expires varchar(255),
refresh_token varchar(255),
username varchar(255),
client_id varchar(255),
createdtime datetime,
modifiedtime datetime
);
  
drop table if exists account;
create table account (  
uid bigint,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL
);
  
create table client (
id bigint,
client_id varchar(255) NOT NULL,  
client_secret varchar(255)  
);