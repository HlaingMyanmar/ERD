-- ********************** Data **********************

INSERT INTO roles (role_name, description, is_active) VALUES
('Admin', 'Full access to all features', 1),
('Manager', 'Manage products and staff', 1),
('Staff', 'Basic access for daily operations', 1),
('Guest', 'Limited access for viewing only', 0),
('Supervisor', 'Oversee operations and reports', 1);

INSERT INTO permissions (permission_name, description, is_active) VALUES
('view_products', 'View product details', 1),
('edit_products', 'Edit product information', 1),
('delete_products', 'Delete products', 1),
('view_sales', 'View sales records', 1),
('edit_sales', 'Edit sales records', 1),
('view_purchases', 'View purchase records', 1),
('edit_purchases', 'Edit purchase records', 1),
('manage_users', 'Add/Edit/Delete users', 1),
('view_reports', 'View financial and sales reports', 1),
('manage_suppliers', 'Manage supplier information', 1),
('archive_data', 'Archive old records', 0);

INSERT INTO users (username, password, is_active) VALUES
('admin1', 'hashed_password_123', 1),
('mgr_john', 'hashed_password_456', 1),
('staff_aye', 'hashed_password_789', 1),
('supervisor_ko', 'hashed_password_101', 1),
('staff_min', 'hashed_password_112', 1),
('inactive_user', 'hashed_password_999', 0);

INSERT INTO user_roles (user_id, role_id, is_active) VALUES
(1, 1, 1), -- admin1 -> Admin
(2, 2, 1), -- mgr_john -> Manager
(3, 3, 1), -- staff_aye -> Staff
(4, 5, 1), -- supervisor_ko -> Supervisor
(5, 3, 1), -- staff_min -> Staff
(6, 4, 0); -- inactive_user -> Guest (Inactive)

INSERT INTO role_permissions (role_id, permission_id) VALUES
-- Admin (Full access)
(1, 1), -- view_products
(1, 2), -- edit_products
(1, 3), -- delete_products
(1, 4), -- view_sales
(1, 5), -- edit_sales
(1, 6), -- view_purchases
(1, 7), -- edit_purchases
(1, 8), -- manage_users
(1, 9), -- view_reports
(1, 10), -- manage_suppliers

-- Manager (Moderate access)
(2, 1), -- view_products
(2, 2), -- edit_products
(2, 4), -- view_sales
(2, 5), -- edit_sales
(2, 6), -- view_purchases
(2, 7), -- edit_purchases
(2, 9), -- view_reports
(2, 10), -- manage_suppliers

-- Staff (Basic access)
(3, 1), -- view_products
(3, 4), -- view_sales
(3, 6), -- view_purchases

-- Guest (View only, Inactive)
(4, 1), -- view_products
(4, 4), -- view_sales

-- Supervisor (Oversee operations)
(5, 1), -- view_products
(5, 4), -- view_sales
(5, 6), -- view_purchases
(5, 9); -- view_reports



INSERT INTO chart_of_accounts (account_name, account_type, is_active) VALUES
-- Assets (ပိုင်ဆိုင်မှု)
('Cash on Hand', 'Assets', 1),
('Bank Account - Savings', 'Assets', 1),
('Bank Account - Current', 'Assets', 1),
('Accounts Receivable', 'Assets', 1),
('Inventory', 'Assets', 1),
('Office Equipment', 'Assets', 1),
('Vehicles', 'Assets', 1),
('Buildings', 'Assets', 1),
('Land', 'Assets', 1),
('Prepaid Expenses', 'Assets', 1),
('Investment - Stocks', 'Assets', 1),
('Investment - Bonds', 'Assets', 0), -- Inactive ဥပမာ

-- Liabilities (အကြွေးများ)
('Accounts Payable', 'Liabilities', 1),
('Short-term Loan', 'Liabilities', 1),
('Long-term Loan', 'Liabilities', 1),
('Credit Card Payable', 'Liabilities', 1),
('Accrued Expenses', 'Liabilities', 1),
('Taxes Payable', 'Liabilities', 1),
('Salaries Payable', 'Liabilities', 1),
('Bank Overdraft', 'Liabilities', 0), -- Inactive ဥပမာ

-- Equity (အရင်းအနှီး)
('Owner’s Capital', 'Equity', 1),
('Retained Earnings', 'Equity', 1),
('Common Stock', 'Equity', 1),
('Dividends', 'Equity', 1),
('Partner Capital - A', 'Equity', 1),
('Partner Capital - B', 'Equity', 1),
('Capital Reserve', 'Equity', 0), -- Inactive ဥပမာ

-- Revenue (ဝင်ငွေ)
('Sales Revenue', 'Revenue', 1),
('Service Revenue', 'Revenue', 1),
('Interest Income', 'Revenue', 1),
('Rental Income', 'Revenue', 1),
('Commission Income', 'Revenue', 1),
('Other Income', 'Revenue', 1),
('Sales Discounts', 'Revenue', 0), -- Inactive ဥပမာ

-- Expenses (အသုံးစရိတ်)
('Rent Expense', 'Expenses', 1),
('Utilities Expense', 'Expenses', 1),
('Salaries Expense', 'Expenses', 1),
('Office Supplies Expense', 'Expenses', 1),
('Travel Expense', 'Expenses', 1),
('Advertising Expense', 'Expenses', 1),
('Depreciation Expense', 'Expenses', 1),
('Insurance Expense', 'Expenses', 1),
('Repairs and Maintenance', 'Expenses', 1),
('Bank Charges', 'Expenses', 1),
('Legal Fees', 'Expenses', 1),
('Bad Debt Expense', 'Expenses', 0), -- Inactive ဥပမာ
('Entertainment Expense', 'Expenses', 1),
('Fuel Expense', 'Expenses', 1),
('Training Expense', 'Expenses', 1);

INSERT INTO suppliers (supplier_name, contact_person, phone_number, email, address, is_active) VALUES
-- Active Suppliers
('Myanmar Trading Co.', 'U Aung Myo', '09123456789', 'aungmyo@myanmartrading.com', 'No. 123, Bogyoke Street, Yangon', TRUE),
('Golden Supplies Ltd.', 'Daw Hla Hla', '09987654321', 'hlahla@goldensupplies.com', '45 Sule Pagoda Road, Yangon', TRUE),
('Shwe Taung Group', 'Ko Min Thu', '09456789012', 'minthu@shwetaung.com', '78 Insein Road, Yangon', TRUE),
('Yangon Wholesale', 'Mg Kyaw Zin', '09234567890', 'kyawzin@ygnwholesale.com', '12 Strand Road, Yangon', TRUE),
('Mandalay Imports', 'Daw Aye Aye', '09789123456', 'ayeaye@mdyimports.com', '35th Street, Mandalay', TRUE),
('Sagaing Traders', 'U Soe Win', '09412345678', 'soewin@sagaingtraders.com', 'Sagaing Main Road, Sagaing', TRUE),
('Taunggyi Distributors', 'Ko Zaw Min', '09912345678', 'zawmin@taunggyi.com', 'Bogyoke Road, Taunggyi', TRUE),
('Eastern Supplies', 'Daw Myint Myint', '09256789012', 'myintmyint@easternsupplies.com', 'No. 56, Eastern Road, Yangon', TRUE),
('Western Imports', 'U Htay Aung', '09765432109', 'htayaung@westernimports.com', 'Hledan Street, Yangon', TRUE),
('Northern Trading', 'Mg Tun Tun', '09498765432', 'tuntun@northerntrading.com', 'Pyay Road, Yangon', TRUE),

-- Inactive Suppliers
('Old Yangon Supplies', 'U Win Naing', '09111222333', 'winnaing@oldygnsupplies.com', 'Lanmadaw Township, Yangon', FALSE),
('Retired Traders', 'Daw Tin Tin', '09444455566', 'tintin@retiredtraders.com', 'Mandalay Road, Mandalay', FALSE),
('Closed Imports', 'Ko Aung Ko', '09777788899', 'aungko@closedimports.com', 'Shan State, Taunggyi', FALSE),
('Legacy Suppliers', 'Mg Hla Myo', '09222233344', 'hlamyo@legacysuppliers.com', 'Bahan Township, Yangon', FALSE),
('Past Trading Co.', 'U Myo Zin', '09555566677', 'myozin@pasttrading.com', 'Kyauktada Township, Yangon', FALSE);


INSERT INTO payment_methods (method_code, method_name, is_digital, is_active) VALUES
-- Active Payment Methods
('CASH', 'Cash', 0, 1),
('BANKCARD', 'Bank Card (Debit)', 1, 1),
('CREDITCARD', 'Credit Card', 1, 1),
('MPU', 'MPU Card', 1, 1),
('KBZPAY', 'KBZ Pay', 1, 1),
('WAVEPAY', 'Wave Pay', 1, 1),
('BANKTRANS', 'Bank Transfer', 1, 1),

-- Inactive Payment Methods
('CHECK', 'Cheque', 0, 0),
('PROMISE', 'Promise to Pay', 0, 0),
('MOBILEVOUCH', 'Mobile Voucher', 1, 0);

INSERT INTO categories (category_name, description) VALUES
('Electronics', 'Devices and gadgets like phones, laptops, and TVs'),
('Clothing', 'Men’s, women’s, and children’s apparel'),
('Footwear', 'Shoes, sandals, and boots'),
('Furniture', 'Home and office furniture like chairs, tables, and beds'),
('Groceries', 'Daily essentials like rice, oil, and snacks'),
('Cosmetics', 'Beauty products like makeup, skincare, and perfumes'),
('Books', 'Fiction, non-fiction, and educational books'),
('Toys', 'Children’s toys and games'),
('Appliances', 'Household appliances like refrigerators and microwaves'),
('Jewelry', 'Rings, necklaces, and earrings'),
('Sports Equipment', 'Gear for sports like footballs, rackets, and weights'),
('Stationery', 'Office and school supplies like pens, notebooks, and paper'),
('Automotive', 'Car parts and accessories'),
('Home Decor', 'Items for decorating homes like curtains and vases'),
('Health & Fitness', 'Supplements, fitness trackers, and gym equipment');

