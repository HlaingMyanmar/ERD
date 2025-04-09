

လုပ်ငန်းစဉ် ခွဲခြမ်းစိတ်ဖြာ
transactions: ငွေပေးငွေယူ မှတ်တမ်းတစ်ခု စတင် ဖန်တီးမယ်။
transaction_payments: အဲဒီ transaction အတွက် ငွေပေးချေမှု မှတ်တမ်းထည့်မယ်။
capital_injections: အရင်းအနှီး သွင်းမှုကို မှတ်တမ်းတင်မယ်။
journal_entries: စာရင်းအင်း မှတ်တမ်းတစ်ခု ဖန်တီးမယ်။
journal_details: အဲဒီ journal entry ရဲ့ debit/credit အသေးစိတ် ထည့်မယ်။



DDELIMITER //

CREATE TRIGGER after_transaction_insert_payments
    AFTER INSERT ON transactions
    FOR EACH ROW
BEGIN
    IF NEW.reference_no LIKE 'CAP-%' THEN
        INSERT INTO transaction_payments (
            transaction_id,
            payment_date,
            method_id,
            amount,
            verified_by
        ) VALUES (
            NEW.transaction_id,
            NOW(),
            NEW.method_id,
            NEW.total_amount,
            NULL
        );
END IF;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_transaction_insert_capital
    AFTER INSERT ON transactions
    FOR EACH ROW
BEGIN
    IF NEW.reference_no LIKE 'CAP-%' THEN
        INSERT INTO capital_injections (
            injection_date,
            amount,
            description,
            transaction_id,
            payment_id
        ) VALUES (
            DATE(NEW.transaction_date),
            NEW.total_amount,
            CONCAT('Capital Injection via ', NEW.reference_no),
            NEW.transaction_id,
            (SELECT payment_id FROM transaction_payments WHERE transaction_id = NEW.transaction_id LIMIT 1)
        );
END IF;
END //

DELIMITER ;


DELIMITER //

CREATE TRIGGER after_transaction_insert_journal_entries
    AFTER INSERT ON transactions
    FOR EACH ROW
BEGIN
    IF NEW.reference_no LIKE 'CAP-%' THEN
        INSERT INTO journal_entries (
            transaction_date,
            description,
            reference_no
        ) VALUES (
            DATE(NEW.transaction_date),
            CONCAT('Capital Injection via ', NEW.reference_no),
            NEW.reference_no
        );
END IF;
END //

DELIMITER ;

    DELIMITER //

CREATE TRIGGER after_journal_entries_insert_details
    AFTER INSERT ON journal_entries
    FOR EACH ROW
BEGIN
    DECLARE trans_amount DECIMAL(15,2);
    DECLARE cash_account INT;
    DECLARE capital_account INT;

    SET trans_amount = (SELECT total_amount FROM transactions WHERE reference_no = NEW.reference_no LIMIT 1);
    SET cash_account = (SELECT account_id FROM transactions WHERE reference_no = NEW.reference_no LIMIT 1);
    SET capital_account = 21;  -- Owner’s Capital ကို ယာယီထားတယ်

    IF NEW.reference_no LIKE 'CAP-%' THEN
        INSERT INTO journal_details (entry_id, account_id, debit, credit)
        VALUES (NEW.entry_id, cash_account, trans_amount, 0.00);

    INSERT INTO journal_details (entry_id, account_id, debit, credit)
    VALUES (NEW.entry_id, capital_account, 0.00, trans_amount);
END IF;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_purchase_details_insert
    AFTER INSERT ON purchase_details
    FOR EACH ROW
BEGIN

    INSERT INTO product_pricing (product_id, purchase_id, cost_price, quantity)
    VALUES (NEW.product_id, NEW.purchase_id, NEW.unit_price, NEW.quantity);
END //

DELIMITER ;