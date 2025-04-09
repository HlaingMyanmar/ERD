-- transaction payment ->journal_entries -> journal_details trigger


DELIMITER //

CREATE TRIGGER after_payment_insert_journal_entries
    AFTER INSERT ON transaction_payments
    FOR EACH ROW
BEGIN
    DECLARE trans_ref_no VARCHAR(100);

    -- transactions ကနေ reference_no ယူမယ်
    SELECT reference_no INTO trans_ref_no
    FROM transactions
    WHERE transaction_id = NEW.transaction_id
        LIMIT 1;

    INSERT INTO journal_entries (
        transaction_date,
        description,
        reference_no
    ) VALUES (
                 DATE(NEW.payment_date),
                 CONCAT('Payment for Transaction #', NEW.transaction_id),
                 trans_ref_no
             );
END //

DELIMITER ;
DELIMITER //

CREATE TRIGGER after_journal_entries_insert_details
    AFTER INSERT ON journal_entries
    FOR EACH ROW
BEGIN
    DECLARE pay_amount DECIMAL(15,2);
    DECLARE pay_transaction_id VARCHAR(50);
    DECLARE pay_method_id INT;
    DECLARE trans_account_id INT;

    -- transaction_payments ကနေ လိုအပ်တဲ့ ဒေတာယူမယ်
    SELECT tp.amount, tp.transaction_id, tp.method_id, t.account_id
    INTO pay_amount, pay_transaction_id, pay_method_id, trans_account_id
    FROM transaction_payments tp
             JOIN transactions t ON tp.transaction_id = t.transaction_id
    WHERE t.reference_no = NEW.reference_no
        LIMIT 1;

    -- Transaction ရဲ့ သဘောသဘာဝပေါ် မူတည်ပြီး debit/credit ထည့်မယ်
    IF NEW.reference_no LIKE 'CAP-%' THEN
        -- Capital Injection
        INSERT INTO journal_details (entry_id, account_id, debit, credit)
        VALUES (NEW.entry_id, trans_account_id, pay_amount, 0.00);  -- Debit Cash

    INSERT INTO journal_details (entry_id, account_id, debit, credit)
    VALUES (NEW.entry_id, 21, 0.00, pay_amount);  -- Credit Owner’s Capital
    ELSEIF NEW.reference_no LIKE 'SALE-%' THEN
        -- Sales Revenue
        INSERT INTO journal_details (entry_id, account_id, debit, credit)
        VALUES (NEW.entry_id, trans_account_id, pay_amount, 0.00);  -- Debit Cash

    INSERT INTO journal_details (entry_id, account_id, debit, credit)
    VALUES (NEW.entry_id, 30, 0.00, pay_amount);  -- Credit Sales Revenue
    ELSEIF NEW.reference_no LIKE 'EXP-%' THEN
        -- Expense
        INSERT INTO journal_details (entry_id, account_id, debit, credit)
        VALUES (NEW.entry_id, 40, pay_amount, 0.00);  -- Debit Expense (e.g., Rent)

    INSERT INTO journal_details (entry_id, account_id, debit, credit)
    VALUES (NEW.entry_id, trans_account_id, 0.00, pay_amount);  -- Credit Cash
END IF;
END //

DELIMITER ;

