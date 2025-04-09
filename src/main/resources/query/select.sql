SELECT
    jd.account_id,
    coa.account_name,
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
GROUP BY jd.account_id, coa.account_name;