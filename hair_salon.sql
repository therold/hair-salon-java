--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: tim
--

CREATE TABLE clients (
    id integer NOT NULL,
    username character varying NOT NULL,
    firstname character varying NOT NULL,
    lastname character varying NOT NULL,
    stylistid integer
);


ALTER TABLE clients OWNER TO tim;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: tim
--

CREATE SEQUENCE clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clients_id_seq OWNER TO tim;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: tim
--

ALTER SEQUENCE clients_id_seq OWNED BY clients.id;


--
-- Name: stylists; Type: TABLE; Schema: public; Owner: tim
--

CREATE TABLE stylists (
    id integer NOT NULL,
    username character varying NOT NULL,
    firstname character varying NOT NULL,
    lastname character varying NOT NULL,
    specialty character varying NOT NULL
);


ALTER TABLE stylists OWNER TO tim;

--
-- Name: stylists_id_seq; Type: SEQUENCE; Schema: public; Owner: tim
--

CREATE SEQUENCE stylists_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stylists_id_seq OWNER TO tim;

--
-- Name: stylists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: tim
--

ALTER SEQUENCE stylists_id_seq OWNED BY stylists.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: tim
--

ALTER TABLE ONLY clients ALTER COLUMN id SET DEFAULT nextval('clients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: tim
--

ALTER TABLE ONLY stylists ALTER COLUMN id SET DEFAULT nextval('stylists_id_seq'::regclass);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: tim
--

COPY clients (id, username, firstname, lastname, stylistid) FROM stdin;
\.


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: tim
--

SELECT pg_catalog.setval('clients_id_seq', 1, false);


--
-- Data for Name: stylists; Type: TABLE DATA; Schema: public; Owner: tim
--

COPY stylists (id, username, firstname, lastname, specialty) FROM stdin;
\.


--
-- Name: stylists_id_seq; Type: SEQUENCE SET; Schema: public; Owner: tim
--

SELECT pg_catalog.setval('stylists_id_seq', 1, false);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: tim
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: clients_username_key; Type: CONSTRAINT; Schema: public; Owner: tim
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_username_key UNIQUE (username);


--
-- Name: stylists_pkey; Type: CONSTRAINT; Schema: public; Owner: tim
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_pkey PRIMARY KEY (id);


--
-- Name: stylists_username_key; Type: CONSTRAINT; Schema: public; Owner: tim
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_username_key UNIQUE (username);


--
-- Name: clients_stylistid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: tim
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_stylistid_fkey FOREIGN KEY (stylistid) REFERENCES stylists(id) ON DELETE SET NULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

