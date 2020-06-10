OAuth2.0 
=============

* OAuth Client Table DML and DDL

~~~~sql
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(2000),
  autoapprove VARCHAR(256)
);

INSERT INTO oauth_client_details
(
	client_id,
	client_secret,
	resource_ids,
	scope,
	authorized_grant_types,
	web_server_redirect_uri,
	authorities,
	access_token_validity,
	refresh_token_validity,
	additional_information,
	autoapprove
)
VALUES
(
	'hirit_admin',
	'{bcrypt}$2a$10$Spmbf324qUpmbr1q58PPaejmYQlxveIAjpSYQmMU7nvjthoLqCn9C',
	null,
	'read,write',
	'authorization_code,password,implicit,refresh_token',
	null,
	'ROLE_OAUTH,ROLE_ADMIN',
	36000,
	2592000,
	null,
	null
);

INSERT INTO oauth_client_details
(
	client_id,
	client_secret,
	resource_ids,
	scope,
	authorized_grant_types,
	web_server_redirect_uri,
	authorities,
	access_token_validity,
	refresh_token_validity,
	additional_information,
	autoapprove
)
VALUES
(
	'hirit',
	'{bcrypt}$2a$10$Spmbf324qUpmbr1q58PPaejmYQlxveIAjpSYQmMU7nvjthoLqCn9C',
	null,
	'read',
	'authorization_code,password,implicit,refresh_token',
	null,
	'ROLE_OAUTH,ROLE_ADMIN',
	36000,
	2592000,
	null,
	null
);
~~~~
