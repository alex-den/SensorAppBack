--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Ubuntu 15.4-2.pgdg22.04+1)
-- Dumped by pg_dump version 15.4 (Ubuntu 15.4-2.pgdg22.04+1)

-- Started on 2023-11-10 15:14:02 +03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS sensors_dev;
--
-- TOC entry 3410 (class 1262 OID 16473)
-- Name: sensors_dev; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE sensors_dev WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';


ALTER DATABASE sensors_dev OWNER TO postgres;

\connect sensors_dev

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16481)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24711)
-- Name: sensor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sensor_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 10000
    CACHE 1;


ALTER TABLE public.sensor_id_seq OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 24675)
-- Name: sensor_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sensor_types (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.sensor_types OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24704)
-- Name: sensors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sensors (
    id integer DEFAULT nextval('public.sensor_id_seq'::regclass) NOT NULL,
    name character varying NOT NULL,
    model_name character varying NOT NULL,
    range_from integer,
    range_to integer,
    unit_id integer NOT NULL,
    location character varying,
    description character varying
);


ALTER TABLE public.sensors OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24668)
-- Name: units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.units (
    id integer NOT NULL,
    name character varying NOT NULL,
    sensor_type_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.units OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16474)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24665)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 10000
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 217
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public.users.id;


--
-- TOC entry 216 (class 1259 OID 16488)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 3237 (class 2604 OID 24667)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- TOC entry 3398 (class 0 OID 16481)
-- Dependencies: 215
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles VALUES (2, 'ROLE_USER');


--
-- TOC entry 3402 (class 0 OID 24675)
-- Dependencies: 219
-- Data for Name: sensor_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sensor_types VALUES (1, 'Pressure');
INSERT INTO public.sensor_types VALUES (2, 'Voltage');
INSERT INTO public.sensor_types VALUES (3, 'Temperature');
INSERT INTO public.sensor_types VALUES (4, 'Humidity');


--
-- TOC entry 3403 (class 0 OID 24704)
-- Dependencies: 220
-- Data for Name: sensors; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sensors VALUES (3, 'Sensor3', 'TER-21', -75, 50, 3, 'Room3', 'Sensor3 Description');
INSERT INTO public.sensors VALUES (1, 'Sensor1', 'PC--33-56', 0, 16, 1, 'Room1', 'Sensor1 Description');
INSERT INTO public.sensors VALUES (2, 'Sensor2', 'EH-567', -25, 30, 2, 'Room2', 'Sensor2 Description');
INSERT INTO public.sensors VALUES (27, 'Sensor9', 'PC-90', 0, 50, 1, 'Room7', 'Read it');
INSERT INTO public.sensors VALUES (12, 'Sensor5', 'T-1011', -50, 50, 3, 'Room4', 'Description4');


--
-- TOC entry 3401 (class 0 OID 24668)
-- Dependencies: 218
-- Data for Name: units; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.units VALUES (1, 'bar', 1);
INSERT INTO public.units VALUES (2, 'voltage', 2);
INSERT INTO public.units VALUES (3, '°C', 3);
INSERT INTO public.units VALUES (4, '%', 4);
INSERT INTO public.units VALUES (5, '°F', 3);


--
-- TOC entry 3399 (class 0 OID 16488)
-- Dependencies: 216
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_roles VALUES (1, 1);
INSERT INTO public.user_roles VALUES (2, 2);


--
-- TOC entry 3397 (class 0 OID 16474)
-- Dependencies: 214
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, 'admin', '$2a$10$Bxh.Q9TkJ.SrDoDRHdYu8OmjEgJjHYi7bwXZY6gWaK7EtjW0yTWKe');
INSERT INTO public.users VALUES (2, 'viewer', '$2a$10$qB32G/qLtt2AbUa5KhIRT.hWJmp9F0V7Z.bxlEnaPYTZ1vrI9aYtq');


--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 221
-- Name: sensor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sensor_id_seq', 28, true);


--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 217
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 13, true);


--
-- TOC entry 3243 (class 2606 OID 16487)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 24688)
-- Name: sensor_types sensor_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sensor_types
    ADD CONSTRAINT sensor_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3251 (class 2606 OID 24710)
-- Name: sensors sensors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sensors
    ADD CONSTRAINT sensors_pkey PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 24674)
-- Name: units units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.units
    ADD CONSTRAINT units_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 16492)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3241 (class 2606 OID 16480)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 16498)
-- Name: user_roles role_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3254 (class 2606 OID 24716)
-- Name: units sensor_type_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.units
    ADD CONSTRAINT sensor_type_id_fk FOREIGN KEY (sensor_type_id) REFERENCES public.sensor_types(id) NOT VALID;


--
-- TOC entry 3253 (class 2606 OID 16493)
-- Name: user_roles user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-11-10 15:14:04 +03

--
-- PostgreSQL database dump complete
--

