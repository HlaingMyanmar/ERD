

-- Account_Type
INSERT INTO account_types (account_type_name) 
VALUES 
    ('Asset'),
    ('Liability'),
    ('Equity'),
    ('Revenue'),
    ('Expense');
	
	INSERT INTO transaction_types (transaction_type_name) 
VALUES ('Purchase'), ('Sale'), ('Return');
	
-- Chart_of_Account
INSERT INTO chart_of_accounts (account_code, account_name, account_type_id) 
VALUES 
    ('1001', 'Cash', 1),            -- Asset
    ('1002', 'Accounts Receivable', 1), -- Asset
    ('2001', 'Accounts Payable', 2), -- Liability
    ('3001', 'Owner’s Equity', 3),   -- Equity
    ('4001', 'Service Revenue', 4),  -- Revenue
    ('5001', 'Office Rent Expense', 5); -- Expense
-- Categories Insert
INSERT INTO categories (category_name) VALUES 
    ('Computers'), 
    ('Accessories'), 
    ('Software');


-- org.suppliersoptions` Insert

INSERT INTO suppliers (supplier_name, contact_person, phone_number, email, address) VALUES 
    ('Tech World Co., Ltd.', 'Aung Aung', '0945123456', 'aung@techworld.com', 'Yangon, Myanmar'),
    ('IT Solutions', 'Kyaw Kyaw', '0976543210', 'kyaw@itsolutions.com', 'Mandalay, Myanmar');


-- Product Insert
INSERT INTO products (product_id, product_name, category_id, supplier_id, cost_price, selling_price, stock_quantity, reorder_level) VALUES 
    ('P001', 'Dell Laptop', 1, 1, 800000, 950000, 0, 5),
    ('P002', 'Wireless Mouse', 2, 1, 20000, 25000, 0, 10),
    ('P003', 'Windows 11 Pro License', 3, 2, 120000, 150000, 0, 5);
	
-- Insert Purchase	
INSERT INTO purchases (supplier_id, purchase_date, total_cost, account_id) VALUES 
(1, '2025-02-12', 5000000, 5),  -- Purchase from Tech World
(2, '2025-02-12', 2500000, 5);  -- Purchase from IT Solutions

-- Insert Purchase_detail
INSERT INTO purchase_details (purchase_id, product_id, quantity, unit_price, total_price, discount, tax) VALUES 
    (1, 'P001', 5, 800000, 4000000, 0, 20000),  -- 5 Dell Laptops
    (1, 'P002', 50, 20000, 1000000, 5000, 10000),  -- 50 Wireless Mice
    (2, 'P003', 10, 120000, 1200000, 0, 5000);  -- 10 Windows Licenses
	


-- Trial Balance
SELECT 
    coa.account_name,
    SUM(CASE 
            WHEN jd.debit > 0 THEN jd.debit 
            ELSE 0 
        END) AS total_debit,
    SUM(CASE 
            WHEN jd.credit > 0 THEN jd.credit 
            ELSE 0 
        END) AS total_credit
FROM 
    journal_details jd
JOIN 
    chart_of_accounts coa ON jd.account_id = coa.account_id
GROUP BY 
    coa.account_name;





-- ကုန်ပစ္စည်းစီမံခန့်ခွဲမှု (Product Management)
-- လက်ရှိစတော့ပမာဏစစ်ဆေးခြင်း


SELECT
    p.product_id AS 'ကုန်ပစ္စည်းနံပါတ်',
        p.product_name AS 'အမည်',
        c.category_name AS 'အမျိုးအစား',
        p.stock_quantity AS 'လက်ကျန်ပမာဏ',
        p.reorder_level AS 'ပြန်ဖြည့်အဆင့်'
FROM products p
         JOIN categories c ON p.category_id = c.category_id
WHERE p.stock_quantity <= p.reorder_level;


--ဘားကုဒ်ဖြင့်ရှာဖွေခြင်း

SELECT
    p.product_id,
    p.product_name,
    b.barcode_value
FROM products p
         JOIN barcodes b ON p.product_id = b.product_id
WHERE b.barcode_value = '123456789'; -- ဘားကုဒ်တန်ဖိုးကို ပြင်ပါ


--ဝယ်ယူမှုစီမံခန့်ခွဲမှု (Purchase Management)
-- ပေးသွင်းသူအလိုက်ဝယ်ယူမှုစာရင်း

SELECT
    s.supplier_name AS 'ပေးသွင်းသူ',
        COUNT(p.purchase_id) AS 'ဝယ်ယူမှုအရေအတွက်',
        SUM(p.total_cost) AS 'စုစုပေါင်းကုန်ကျငွေ'
FROM purchases p
         JOIN suppliers s ON p.supplier_id = s.supplier_id
GROUP BY s.supplier_id;

--မပြီးပြတ်သေးသောဝယ်ယူမှုများ

SELECT
    p.purchase_id,
    s.supplier_name,
    t.transaction_date,
    t.total_amount,
    t.balance
FROM purchases p
         JOIN transactions t ON p.transaction_id = t.transaction_id
         JOIN suppliers s ON p.supplier_id = s.supplier_id
WHERE t.status_id IN (1,3); -- 1=Unpaid, 3=Partial

-- ရောင်းချမှုစီမံခန့်ခွဲမှု (Sales Management)
--နေ့စဉ်ရောင်းချမှုအစီရင်ခံစာ
SELECT
    DATE(s.sale_date) AS 'ရက်စွဲ',
    c.customer_name AS 'ဝယ်ယူသူ',
    SUM(sd.total_price) AS 'စုစုပေါင်းရောင်းငွေ'
FROM sales s
    JOIN sale_details sd ON s.sale_id = sd.sale_id
    JOIN customers c ON s.customer_id = c.customer_id
GROUP BY DATE(s.sale_date), s.customer_id;

--အမြတ်စွန့်မှုခွဲခြမ်းစိတ်ဖြာခြင်း
SELECT
    p.product_id,
    p.product_name,
    SUM(sd.quantity) AS 'ရောင်းချပမာဏ',
        SUM((sd.unit_price - p.cost_price) * sd.quantity) AS 'အမြတ်'
FROM sale_details sd
         JOIN products p ON sd.product_id = p.product_id
GROUP BY p.product_id;

--ငွေပေးချေမှုစနစ် (Payment System)
--ငွေပေးချေမှုမှတ်တမ်းများ
SELECT
    t.transaction_id,
    CASE
        WHEN t.transaction_type = 'Purchase' THEN s.supplier_name
        WHEN t.transaction_type = 'Sale' THEN c.customer_name
        ELSE 'N/A'
        END AS 'အမည်',
        t.transaction_date,
    t.total_amount,
    t.paid_amount,
    t.balance,
    ps.status_name
FROM transactions t
         LEFT JOIN purchases p ON t.linked_id = p.purchase_id AND t.transaction_type = 'Purchase'
         LEFT JOIN sales sa ON t.linked_id = sa.sale_id AND t.transaction_type = 'Sale'
         LEFT JOIN suppliers s ON p.supplier_id = s.supplier_id
         LEFT JOIN customers c ON sa.customer_id = c.customer_id
         JOIN payment_statuses ps ON t.status_id = ps.status_id;

-- KBZ Pay ဖြင့်ပေးချေမှုများ
SELECT
    tp.payment_date,
    tp.amount,
    tp.reference_no
FROM transaction_payments tp
         JOIN payment_methods pm ON tp.method_id = pm.method_id
WHERE pm.method_name = 'KBZ Pay'
  AND tp.is_verified = TRUE;

--အရင်းအနှီးစီမံခန့်ခွဲမှု (Capital Management)
-- အရင်းအနှီးလက်ကျန်စစ်ဆေးခြင်း
SELECT
    (SELECT SUM(amount) FROM capital_injections) AS 'စုစုပေါင်းထည့်ဝင်ငွေ',
        (SELECT SUM(amount) FROM capital_withdrawals) AS 'စုစုပေါင်းထုတ်ယူငွေ',
        (SELECT SUM(amount) FROM capital_injections) - (SELECT SUM(amount) FROM capital_withdrawals) AS 'လက်ကျန်အရင်းအနှီး';


--Audit & Security
-- လုံခြုံရေးချို့ယွင်းမှုများစစ်ဆေးခြင်း
SELECT
    u.username,
    a.action,
    a.table_name,
    a.action_time
FROM audit_logs a
         JOIN users u ON a.user_id = u.user_id
WHERE a.action_time >= CURDATE() - INTERVAL 7 DAY
ORDER BY a.action_time DESC;


-- ဝယ်ယူမှုအသစ်ထည့်ပါက
INSERT INTO purchases (supplier_id, purchase_date, total_cost, account_id)
VALUES (1, CURDATE(), 1500000, 101);

-- Trigger က အလိုအလျောက် transactions ဇယားသို့ထည့်ပေးမည်


-- KBZ Pay ဖြင့်ငွေပေးချေခြင်း
INSERT INTO transaction_payments (transaction_id, payment_date, method_id, amount, reference_no)
VALUES ('PUR-1001', NOW(), 2, 500000, 'KBZ-REF-001');

-- Trigger က paid_amount နှင့် status_id ကို အပ်ဒိတ်လုပ်မည်