CREATE TABLE "quiz" (
    "id" serial NOT NULL,
    "next" integer,
    "group" integer,
    "prev" integer,
    "describe" text,
    "additional" text,
    "content" text,
    "delay" int,
    "final" boolean,
    "is_question" boolean,
    "is_answer" boolean,
    "is_option" boolean,
    CONSTRAINT "pk_quiz" PRIMARY KEY (
      "id"
    )
);

CREATE TABLE "administrators" (
  "login" VARCHAR NOT NULL,
  "password" VARCHAR NOT NULL,
  CONSTRAINT "pk_administrators" PRIMARY KEY (
      "login"
    )
);

CREATE TABLE "users" (
     "email" varchar NOT NULL,
     "date_w_tz" timestamp with time zone NOT NULL,
     "phone_number" VARCHAR(11) NOT NULL,
     "business_size" VARCHAR NOT NULL,
     CONSTRAINT "pk_users" PRIMARY KEY (
        "email"
     )
);