create table curriculum_discipline (

  curriculum_id bigint not null,
  discipline_id bigint not null,

  semester integer not null,
  total_hours integer not null,
  lecture_hours integer not null,
  practice_hours integer not null,
  lab_hours integer not null,
  self_study_hours integer not null,
  test_count integer not null,
  has_credit boolean not null,
  has_exam boolean not null,
  credit_units double precision not null,

  primary key (curriculum_id, discipline_id),
  constraint fk_curriculum_discipline_curriculum foreign key (curriculum_id) references curriculums (id),
  constraint fk_curriculum_discipline_discipline foreign key (discipline_id) references disciplines (id)
);
