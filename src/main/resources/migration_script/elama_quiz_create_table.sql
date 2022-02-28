CREATE TABLE "quiz" (
    "id" serial NOT NULL,
    "next" integer,
    "group" integer,
    "prev" integer,
    "describe" text,
    "additional" text,
    "content" text,
    "checkpoint" boolean,
    "delay" int,
    "final" boolean,
    "is_question" boolean,
    "is_answer" boolean,
    "is_option" boolean,
    CONSTRAINT "pk_quiz" PRIMARY KEY (
      "id"
    )
);

create table travel_state (
      chat_id bigint not null primary key,
      user_name varchar not null,
      user_nick_name varchar,
      user_route int[],
      the_end bool,
      current_frame int
);