/*
 Создание сетки по умолчанию.
 Формируется таблица из 999 каждые 10 записей составляют блок.
 В каждом блоке первый элемент ( равный ID ) вляется ссылкой

 */
CREATE FUNCTION createDataNetwork() RETURNS void as $$
    DECLARE
        counter_limit integer := 1000;
        block_size integer := 10;
        state_digit integer := 0;
        i integer := 1;
    Begin
        while i < counter_limit loop
            if MOD(i, block_size) = 0 then
                state_digit := i;
            end if;
            INSERT INTO "quiz" ("group")
            VALUES (state_digit);
            i := i + 1;
        end loop;
    end;
$$LANGUAGE plpgsql;

SELECT createDataNetwork();