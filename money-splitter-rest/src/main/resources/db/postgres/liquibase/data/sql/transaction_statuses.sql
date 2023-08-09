insert into transaction_statuses values
(1, 'PENDING'),
(2, 'CLOSED')
on conflict(trst_id) do nothing;