# --- !Ups
create table HaUser (
  hauser_id serial NOT NULL,
  email character varying(255) NOT NULL UNIQUE,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  CONSTRAINT hauser_pkey PRIMARY KEY (hauser_id)
);
# --- !Downs
drop table if exists HaUser;