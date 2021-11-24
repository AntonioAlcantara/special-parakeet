ALTER DATABASE lol OWNER TO taric;
GRANT ALL PRIVILEGES ON DATABASE lol TO taric;

CREATE SEQUENCE student_id_seq;
CREATE TABLE public.student (
	id int8 NOT NULL DEFAULT nextval('student_id_seq'),
	"name" varchar(100) NOT NULL,
    lastname varchar(100) NOT NULL,
    passport_number varchar(10) NOT NULL,
	email varchar(100) NOT NULL,
	address varchar(255) NOT NULL,
	date_of_birth DATE NOT NULL,
	creation_date timestamp NULL,
    modification_date timestamp NULL,
	CONSTRAINT student_pkey PRIMARY KEY (id)
);
ALTER SEQUENCE student_id_seq OWNED BY student.id;

CREATE SEQUENCE university_id_seq;
CREATE TABLE public.university (
	id int8 NOT NULL DEFAULT nextval('university_id_seq'),
	"name" varchar(100) NOT NULL,
	description varchar(255) NOT NULL,
	address varchar(255) NOT NULL,
	creation_date timestamp NULL,
	modification_date timestamp NULL,
	CONSTRAINT university_pkey PRIMARY KEY (id)
);
ALTER SEQUENCE university_id_seq OWNED BY university.id;

CREATE SEQUENCE student_university_state_id_seq;
CREATE TABLE public.student_university_state (
	id int8 NOT NULL DEFAULT nextval('student_university_state_id_seq'),
	state varchar(50) NOT NULL,
	end_date date NULL,
	start_date date NOT NULL,
	creation_date timestamp NULL,
	student_id int8 NOT NULL,
	university_id int8 NOT NULL,
	CONSTRAINT student_university_state_pkey PRIMARY KEY (id),
	CONSTRAINT sus_unique_constraint UNIQUE (student_id, university_id, state, start_date, end_date),
	CONSTRAINT sus_university_fkey FOREIGN KEY (university_id) REFERENCES public.university(id),
	CONSTRAINT sus_student_fkey FOREIGN KEY (student_id) REFERENCES public.student(id)
);
ALTER SEQUENCE student_university_state_id_seq OWNED BY student_university_state.id;