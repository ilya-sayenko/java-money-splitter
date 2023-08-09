insert into split_types values
(1, 'EQUAL'),
(2, 'AMOUNT'),
(3, 'PARTITION')
on conflict(sptp_id) do nothing;