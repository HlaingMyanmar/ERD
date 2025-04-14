SELECT
    jd.account_id,
    coa.account_name,
    coa.account_type,
    SUM(jd.debit) AS total_debit,
    SUM(jd.credit) AS total_credit,
    SUM(jd.debit) - SUM(jd.credit) AS balance,
    CASE
        WHEN SUM(jd.debit) > SUM(jd.credit) THEN 'Debit'
        WHEN SUM(jd.credit) > SUM(jd.debit) THEN 'Credit'
        ELSE 'Balanced'
        END AS balance_type
FROM journal_details jd
         JOIN chart_of_accounts coa ON jd.account_id = coa.account_id
GROUP BY jd.account_id, coa.account_name, coa.account_type;


WITH AccountBalances AS (
    SELECT
        jd.account_id,
        coa.account_name,
        coa.account_type,
        SUM(jd.debit) AS total_debit,
        SUM(jd.credit) AS total_credit,
        SUM(jd.credit) - SUM(jd.debit) AS balance,
        CASE
            WHEN SUM(jd.credit) > SUM(jd.debit) THEN 'Credit'
            WHEN SUM(jd.debit) > SUM(jd.credit) THEN 'Debit'
            ELSE 'Balanced'
            END AS balance_type
    FROM journal_details jd
             JOIN chart_of_accounts coa ON jd.account_id = coa.account_id
    GROUP BY jd.account_id, coa.account_name, coa.account_type
)
SELECT
    account_id,
    account_name,
    account_type,
    total_debit,
    total_credit,
    balance,
    balance_type
FROM AccountBalances
UNION ALL
SELECT
    NULL AS account_id,
    'Total Net Balance' AS account_name,
    NULL AS account_type,
    SUM(total_debit) AS total_debit,
    SUM(total_credit) AS total_credit,
    SUM(balance) AS balance,
    CASE
        WHEN SUM(balance) > 0 THEN 'Credit'
        WHEN SUM(balance) < 0 THEN 'Debit'
        ELSE 'Balanced'
        END AS balance_type
FROM AccountBalances;