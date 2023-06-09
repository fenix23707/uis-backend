alter table permissions add column application_name varchar(255) not null default 'undefined';
alter table permissions alter column application_name drop default;

create table methods (
    description text,
    permission_id bigint,

    primary key(description),
    foreign key (permission_id) references permissions (id) on delete cascade
);

alter table permissions drop constraint permissions_scope_action_key;
