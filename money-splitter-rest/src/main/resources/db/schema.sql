CREATE TABLE IF NOT EXISTS parties
(
    party_id uuid,
    name character varying(100),
    description text,
    total_amount numeric,
    PRIMARY KEY (party_id)
);

CREATE TABLE IF NOT exists participants
(
    prnt_id uuid,
    name character varying(50),
    party_party_id uuid,
    PRIMARY KEY (prnt_id),
    CONSTRAINT participants_party_party_id_fkey FOREIGN KEY(party_party_id) REFERENCES parties(party_id)
);

CREATE TABLE IF NOT EXISTS split_types
(
    sptp_id integer,
    name character varying(50) NOT NULL,
    PRIMARY KEY (sptp_id)
);

CREATE TABLE IF NOT EXISTS spendings
(
    spnd_id uuid,
    party_party_id uuid,
    prnt_prnt_id uuid,
    name character varying(255) NOT NULL,
    amount numeric,
    sptp_sptp_id integer,    
    PRIMARY KEY (spnd_id),
    CONSTRAINT spendings_party_party_id_fkey FOREIGN KEY(party_party_id) REFERENCES parties(party_id),
    CONSTRAINT spendings_prnt_prnt_id_fkey   FOREIGN KEY(prnt_prnt_id)   REFERENCES participants(prnt_id),
    CONSTRAINT spendings_sptp_sptp_id_fkey   FOREIGN KEY(sptp_sptp_id)   REFERENCES split_types(sptp_id)
);


CREATE TABLE IF NOT EXISTS proportions
(
    prop_id uuid,
    spnd_spnd_id uuid,
    prnt_prnt_id uuid,
    proportion numeric,
    amount numeric,
    PRIMARY KEY (prop_id),
    CONSTRAINT proportions_spnd_spnd_id_fkey  FOREIGN KEY(spnd_spnd_id) REFERENCES spendings(spnd_id),
    CONSTRAINT proportions_prnt_prnt_id_fkey FOREIGN KEY(prnt_prnt_id) REFERENCES participants(prnt_id)
);

CREATE TABLE IF NOT EXISTS transaction_statuses
(
    trst_id integer,
    name character varying(50) NOT NULL,
    PRIMARY KEY (trst_id)
);

CREATE TABLE IF NOT EXISTS transactions
(
    tran_id uuid,
    party_party_id uuid,
    from_prnt_id uuid,
    to_prnt_id uuid,
    amount numeric,
    trst_trst_id integer,
    PRIMARY KEY (tran_id),
    CONSTRAINT transactions_party_party_id_fkey FOREIGN KEY(party_party_id) REFERENCES parties(party_id),
    CONSTRAINT transactions_from_prnt_id_fkey   FOREIGN KEY(from_prnt_id)   REFERENCES participants(prnt_id),
    CONSTRAINT transactions_to_prnt_id_fkey     FOREIGN KEY(to_prnt_id)     REFERENCES participants(prnt_id),
    CONSTRAINT transactions_trst_trst_id_fkey   FOREIGN KEY(trst_trst_id)   REFERENCES transaction_statuses(trst_id)
); 

insert into split_types values
(1, 'EQUAL'),
(2, 'AMOUNT'),
(3, 'PARTITION')
on conflict(sptp_id) do nothing;

insert into transaction_statuses values
(1, 'PENDING'),
(2, 'CLOSED')
on conflict(trst_id) do nothing;
