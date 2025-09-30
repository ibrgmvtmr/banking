SELECT u.*
FROM users u
WHERE NOT EXISTS (
    SELECT 1
    FROM accounts a
    WHERE a.user_id = u.id
      AND a.balance <= 10000
);

SELECT *
FROM users
WHERE email = :email;

SELECT SUM(balance) AS total_balance
FROM accounts;
