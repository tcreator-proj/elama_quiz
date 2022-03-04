CREATE TABLE "quiz" (
    "ID" serial NOT NULL,
    "next" integer[] NOT NULL,
    "prev" integer,
    "describe" text NOT NULL,
    "additional" text NOT NULL,
    "question" text NOT NULL,
    "delay" int NOT NULL,
    "final" boolean NOT NULL,
    CONSTRAINT "pk_quiz" PRIMARY KEY (
        "ID"
     )
);

CREATE TABLE "users" (
    "email" varchar NOT NULL,
    "role" VARCHAR(10) NOT NULL,
    "date_w_tz" timestamp with time zone NOT NULL,
    "phone_number" VARCHAR(11) NOT NULL,
    "business_size" VARCHAR NOT NULL,
    CONSTRAINT "pk_users" PRIMARY KEY (
        "email"
     )
);

