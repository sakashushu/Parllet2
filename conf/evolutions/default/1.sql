# --- !Ups
create table HaUser (
  id serial NOT NULL,
  email character varying(255) NOT NULL UNIQUE,
  fullname character varying(255),
  isadmin boolean NOT NULL,
  password character varying(255) NOT NULL,
  fbid bigint,
  CONSTRAINT hauser_pkey PRIMARY KEY (id)
);
# --- !Downs
drop table if exists HaUser;