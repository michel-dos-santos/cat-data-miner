BEGIN;

CREATE TABLE IF NOT EXISTS public.temperament
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    create_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    name character varying NOT NULL,
    fk_breed_id uuid NOT null,
    PRIMARY KEY (id)
);
COMMENT ON TABLE public.temperament
    IS 'Tabela para armazenamento dos temperamentos das raças';


CREATE TABLE IF NOT EXISTS public.image
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    create_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    external_id character varying NOT NULL,
    url character varying NOT NULL,
    fk_breed_id uuid NOT null,
    PRIMARY KEY (id)
);
COMMENT ON TABLE public.image
    IS 'Tabela para armazenamento das imagens dos gatos';


CREATE TABLE IF NOT EXISTS public.breed
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    create_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    external_id character varying NOT NULL,
    name character varying NOT NULL,
    origin character varying NOT NULL,
    description character varying NOT NULL,
    PRIMARY KEY (id)
);
COMMENT ON TABLE public.breed
    IS 'Tabela para armazenamento das raças dos gatos';


ALTER TABLE IF EXISTS public.temperament
    ADD FOREIGN KEY (fk_breed_id)
    REFERENCES public.breed (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.image
    ADD FOREIGN KEY (fk_breed_id)
    REFERENCES public.breed (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


END;