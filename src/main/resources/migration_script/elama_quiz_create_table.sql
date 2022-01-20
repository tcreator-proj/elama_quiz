CREATE TABLE "quiz" (
    "ID" serial NOT NULL,
    "group" integer NOT NULL, -- номер группы ( каждый 10й ID)
    "next" integer, -- следующий элемент ( ссылка на группу )
    "prev" integer, -- предыдущий элемент ( ссылка на группу )
    "describe" text, -- описание вопроса или варианта ответа
    "additional" text, -- дополнительные материалы для крепления при ответе
    "question" text, -- сам вопрос или вариант ответа ( выводится в чат)
    "delay" int, -- задержка перед выводом в чат
    "final" boolean, -- завершающий элемент в цепочке
    CONSTRAINT "pk_quiz" PRIMARY KEY (
        "ID"
     )
);

CREATE TABLE "administrators" (
    "login" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    "role" VARCHAR(10) NOT NULL,
    CONSTRAINT "pk_administrators" PRIMARY KEY (
        "login"
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

