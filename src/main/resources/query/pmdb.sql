DROP DATABASE IF EXISTS pmdb;
CREATE DATABASE pmdb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE pmdb;

-- ********************** User & Audit System **********************




CREATE TABLE chart_of_accounts (
                                   account_id INT PRIMARY KEY AUTO_INCREMENT,
                                   account_name VARCHAR(255)  UNIQUE NOT NULL,
                                   account_type varchar(100)  NOT NULL,
                                   is_active TINYINT(1) DEFAULT 1

);

CREATE TABLE roles (
                       role_id INT PRIMARY KEY AUTO_INCREMENT,
                       role_name VARCHAR(100) UNIQUE NOT NULL,
                       description TEXT,
                       is_active TINYINT(1) DEFAULT 0
);

CREATE TABLE permissions (
                             permission_id INT PRIMARY KEY AUTO_INCREMENT,
                             permission_name VARCHAR(100) UNIQUE NOT NULL,
                             description TEXT,
                             is_active TINYINT(1) DEFAULT 1
);

CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       is_active TINYINT(1) DEFAULT 1
);

CREATE TABLE user_roles (
                            user_role_id INT PRIMARY KEY AUTO_INCREMENT,
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,
                            is_active TINYINT(1) DEFAULT 1,
                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE,
                            UNIQUE (user_id, role_id)

);

CREATE TABLE role_permissions (
                                  role_permission_id INT PRIMARY KEY AUTO_INCREMENT,
                                  role_id INT NOT NULL,
                                  permission_id INT NOT NULL,
                                  FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  UNIQUE (role_id, permission_id)
);

CREATE TABLE audit_actions (
                               action_id INT PRIMARY KEY AUTO_INCREMENT,
                               action_name VARCHAR(50) UNIQUE NOT NULL
);



CREATE TABLE audit_tables (
                              table_id INT PRIMARY KEY AUTO_INCREMENT,
                              table_name VARCHAR(50) UNIQUE NOT NULL
);



CREATE TABLE audit_logs (
                            log_id INT PRIMARY KEY AUTO_INCREMENT,
                            user_id INT NOT NULL,
                            action_id INT NOT NULL,
                            table_id INT NOT NULL,
                            reference_no VARCHAR(100),
                            action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (user_id) REFERENCES users(user_id),
                            FOREIGN KEY (action_id) REFERENCES audit_actions(action_id),
                            FOREIGN KEY (table_id) REFERENCES audit_tables(table_id)
);

CREATE TABLE payment_methods (

                                 method_id INT PRIMARY KEY AUTO_INCREMENT not null,
                                 method_code VARCHAR(20) UNIQUE NOT NULL,
                                 method_name VARCHAR(100) NOT NULL,
                                 is_digital TINYINT(1) DEFAULT 0,
                                 is_active TINYINT(1) DEFAULT 0

);


CREATE TABLE transactions (
                              transaction_id VARCHAR(50) PRIMARY KEY,
                              transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              reference_no VARCHAR(100) UNIQUE NOT NULL,
                              total_amount DECIMAL(15,2) NOT NULL,
                              paid_amount DECIMAL(15,2) DEFAULT 0.00,
                              balance DECIMAL(15,2) GENERATED ALWAYS AS (total_amount - paid_amount) STORED,
                              status ENUM('Paid','Unpaid','Half Paid') DEFAULT 'Unpaid',
                              notes TEXT,
                              account_id INT NOT NULL,
                              method_id INT,  -- ထပ်ထည့်တယ်
                              FOREIGN KEY (account_id) REFERENCES chart_of_accounts(account_id),
                              FOREIGN KEY (method_id) REFERENCES payment_methods(method_id)
);

CREATE TABLE transaction_payments (
                                      payment_id INT PRIMARY KEY AUTO_INCREMENT,
                                      transaction_id VARCHAR(50) NOT NULL,
                                      payment_date DATETIME NOT NULL,
                                      method_id INT NOT NULL,
                                      amount DECIMAL(15,2) NOT NULL,
                                      verified_by INT,
                                      FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
                                      FOREIGN KEY (method_id) REFERENCES payment_methods(method_id),
                                      FOREIGN KEY (verified_by) REFERENCES users(user_id)

);

CREATE TABLE journal_entries (
                                 entry_id INT PRIMARY KEY AUTO_INCREMENT,
                                 transaction_date DATE NOT NULL,
                                 description TEXT NOT NULL,
                                 reference_no VARCHAR(100),
                                 FOREIGN KEY (reference_no) REFERENCES transactions(reference_no),
                                 INDEX idx_journal_date (transaction_date)
);



CREATE TABLE journal_details (
                                 detail_id INT PRIMARY KEY AUTO_INCREMENT,
                                 entry_id INT NOT NULL,
                                 account_id INT NOT NULL,
                                 debit DECIMAL(10,2) DEFAULT 0.00,
                                 credit DECIMAL(10,2) DEFAULT 0.00,
                                 FOREIGN KEY (entry_id) REFERENCES journal_entries(entry_id),
                                 FOREIGN KEY (account_id) REFERENCES chart_of_accounts(account_id),
                                 INDEX idx_entry (entry_id),
                                 INDEX idx_account (account_id)
);






CREATE TABLE categories (
                            category_id INT PRIMARY KEY AUTO_INCREMENT,
                            category_name VARCHAR(255) UNIQUE NOT NULL,
                            description TEXT

);


CREATE TABLE suppliers (
                           supplier_id INT PRIMARY KEY AUTO_INCREMENT,
                           supplier_name VARCHAR(255) NOT NULL,
                           contact_person VARCHAR(255),
                           phone_number VARCHAR(50) CHECK (phone_number REGEXP '^(09|01)[0-9]{8,9}$'),
	                       email VARCHAR(100) CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
	                        address TEXT,
	                        is_active BOOLEAN DEFAULT TRUE
);


CREATE TABLE customers (
                           customer_id INT PRIMARY KEY AUTO_INCREMENT,
                           customer_name VARCHAR(255) NOT NULL,
                           phone_number VARCHAR(50),
                           email VARCHAR(100),
                           address TEXT,
                           is_active BOOLEAN DEFAULT TRUE,
                           INDEX idx_customer(customer_name,phone_number)
);






-- #########




-- ********************** Products & Inventory **********************

CREATE TABLE products (
                          product_id VARCHAR(100) PRIMARY KEY,
                          product_name VARCHAR(255) NOT NULL,
                          category_id INT NOT NULL,
                          supplier_id INT NOT NULL,
                          selling_price DECIMAL(10,2) DEFAULT NULL,
                          stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                          reorder_level INT DEFAULT 2 CHECK (reorder_level >= 0),
                          is_active TINYINT(1) NOT NULL DEFAULT 1,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE ON UPDATE CASCADE,
                          FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE CASCADE ON UPDATE CASCADE,
                          INDEX idx_category (category_id),
                          INDEX idx_product_name (product_name),
                          INDEX idx_supplier (supplier_id)
);





CREATE TABLE barcodes (
                          barcode_id INT PRIMARY KEY AUTO_INCREMENT,
                          product_id VARCHAR(100) NOT NULL,
                          barcode_value VARCHAR(255) UNIQUE NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          is_active BOOLEAN DEFAULT TRUE,
                          FOREIGN KEY (product_id) REFERENCES products(product_id),
                          INDEX idx_barcode_id(barcode_id)

);

-- စတော့လှုပ်ရှားမှုမှတ်တမ်း
CREATE TABLE inventory_ledger (
                                  ledger_id INT PRIMARY KEY AUTO_INCREMENT,
                                  product_id VARCHAR(100) NOT NULL,
                                  transaction_id varchar(50) NOT NULL,
                                  quantity INT NOT NULL CHECK (quantity <> 0),
                                  balance INT NOT NULL CHECK (balance >= 0),
                                  reason TEXT,
                                  transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (product_id) REFERENCES products(product_id),
                                  FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)

);




-- ********************** Purchases & Sales **********************
-- ဝယ်ယူမှုများ
CREATE TABLE purchases (
                           purchase_id  VARCHAR (100) PRIMARY KEY,
                           supplier_id INT NOT NULL,
                           purchase_date DATE NOT NULL,
                           total_cost DECIMAL(10,2) NOT NULL,
                           transaction_id VARCHAR(50),
                           payment_id int not null,
                           FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                           FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
                           FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)

);

-- ဝယ်ယူမှုအသေးစိတ်
CREATE TABLE purchase_details (
                                  purchase_detail_id INT PRIMARY KEY AUTO_INCREMENT,
                                  purchase_id VARCHAR (100) NOT NULL,
                                  product_id VARCHAR(100) NOT NULL,
                                  quantity INT NOT NULL,
                                  unit_price DECIMAL(10,2) NOT NULL,
                                  total_price DECIMAL(10,2) NOT NULL,
                                  discount DECIMAL(10,2) DEFAULT 0.00,
                                  tax DECIMAL(10,2) DEFAULT 0.00,
                                  FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id),
                                  FOREIGN KEY (product_id) REFERENCES products(product_id)

);
CREATE TABLE product_pricing (
                                 pricing_id INT PRIMARY KEY AUTO_INCREMENT,
                                 product_id VARCHAR(100) NOT NULL,
                                 purchase_id VARCHAR(100) NOT NULL,
                                 cost_price DECIMAL(10,2) NOT NULL,
                                 selling_price DECIMAL(10,2) DEFAULT NULL,
                                 quantity INT NOT NULL CHECK (quantity >= 0),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (product_id) REFERENCES products(product_id),
                                 FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id),
                                 UNIQUE (product_id, purchase_id)
);

-- ရောင်းချမှုများ
CREATE TABLE sales (
                       sale_id VARCHAR(100) PRIMARY KEY,
                       sale_date DATE NOT NULL,
                       customer_id INT NOT NULL,
                       total_sale DECIMAL(10,2) NOT NULL,
                       account_id INT NOT NULL,
                       transaction_id VARCHAR(50),
                       payment_id int not null,
                       FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                       FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
                       FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)

);

-- ရောင်းချမှုအသေးစိတ်
CREATE TABLE sale_details (
                              sale_detail_id INT PRIMARY KEY AUTO_INCREMENT,
                              sale_id VARCHAR(100) NOT NULL,
                              product_id VARCHAR(100) NOT NULL,
                              quantity INT NOT NULL,
                              unit_price DECIMAL(10,2) NOT NULL,
                              total_price DECIMAL(10,2) NOT NULL,
                              discount DECIMAL(10,2) DEFAULT 0.00,
                              tax DECIMAL(10,2) DEFAULT 0.00,
                              FOREIGN KEY (sale_id) REFERENCES sales(sale_id),
                              FOREIGN KEY (product_id) REFERENCES products(product_id)

);

-- ********************** Returns Management **********************
-- ရောင်းချမှုပြန်လည်ပေးအပ်မှု

CREATE TABLE sale_returns (
                              return_id INT PRIMARY KEY AUTO_INCREMENT,
                              sale_id varchar(100) NOT NULL,
                              return_date DATE NOT NULL,
                              total_refund DECIMAL(10,2) NOT NULL,
                              payment_id int not null,
                              FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                              FOREIGN KEY (sale_id) REFERENCES sales(sale_id)
);

-- ရောင်းချမှုပြန်လည်ပေးအပ်မှုအသေးစိတ်
CREATE TABLE sale_return_details (
                                     return_detail_id INT PRIMARY KEY AUTO_INCREMENT,
                                     return_id INT NOT NULL,
                                     product_id VARCHAR(100) NOT NULL,
                                     quantity INT NOT NULL,
                                     unit_price DECIMAL(10,2) NOT NULL,
                                     total_price DECIMAL(10,2) NOT NULL,
                                     FOREIGN KEY (return_id) REFERENCES sale_returns(return_id),
                                     FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ဝယ်ယူမှုပြန်လည်ပေးအပ်မှု
CREATE TABLE purchase_returns (
                                  return_id INT PRIMARY KEY AUTO_INCREMENT,
                                  purchase_id VARCHAR (100) NOT NULL,
                                  return_date DATE NOT NULL,
                                  total_refund DECIMAL(10,2) NOT NULL,
                                  payment_id int not null,
                                  FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                                  FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id)
);

-- ဝယ်ယူမှုပြန်လည်ပေးအပ်မှုအသေးစိတ်
CREATE TABLE purchase_return_details (
                                         return_detail_id INT PRIMARY KEY AUTO_INCREMENT,
                                         return_id INT NOT NULL,
                                         product_id VARCHAR(100) NOT NULL,
                                         quantity INT NOT NULL,
                                         unit_price DECIMAL(10,2) NOT NULL,
                                         total_price DECIMAL(10,2) NOT NULL,
                                         FOREIGN KEY (return_id) REFERENCES purchase_returns(return_id),
                                         FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ********************** Income & Expenses **********************
-- အသုံးစရိတ်အမျိုးအစားများ
CREATE TABLE expense_types (
                               expense_type_id INT PRIMARY KEY AUTO_INCREMENT,
                               expense_type_name VARCHAR(255) NOT NULL,
                               description TEXT
);

-- ဝင်ငွေအမျိုးအစားများ
CREATE TABLE income_types (
                              income_type_id INT PRIMARY KEY AUTO_INCREMENT,
                              income_type_name VARCHAR(255) NOT NULL,
                              description TEXT
);


-- ဝင်ငွေမှတ်တမ်း
CREATE TABLE income (
                        income_id INT PRIMARY KEY AUTO_INCREMENT,
                        income_date DATE NOT NULL,
                        income_source VARCHAR(255) NOT NULL,
                        amount DECIMAL(10,2) NOT NULL,
                        description TEXT,
                        transaction_id VARCHAR(50),
                        payment_id INT,
                        FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
                        FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id)
);




-- အသုံးစရိတ်မှတ်တမ်း
CREATE TABLE expenses (
                          expense_id INT PRIMARY KEY AUTO_INCREMENT,
                          expense_date DATE NOT NULL,
                          amount DECIMAL(10,2) NOT NULL,
                          description TEXT,
                          transaction_id VARCHAR(50),
                          payment_id INT,
                          FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
                          FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id)
);

-- ********************** Capital Management **********************
-- အရင်းအနှီးထည့်ဝင်မှု
CREATE TABLE capital_injections (
                                    injection_id INT PRIMARY KEY AUTO_INCREMENT,
                                    injection_date DATE NOT NULL,
                                    amount DECIMAL(10,2) NOT NULL,
                                    description TEXT,
                                    transaction_id VARCHAR(50),
                                    payment_id INT,
                                    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
                                    FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id)

);

-- အရင်းအနှီးထုတ်ယူမှု
CREATE TABLE capital_withdrawals (
                                     withdrawal_id INT PRIMARY KEY AUTO_INCREMENT,
                                     withdrawal_date DATE NOT NULL,
                                     amount DECIMAL(10,2) NOT NULL,
                                     description TEXT,
                                     transaction_id VARCHAR(50),
                                     payment_id INT,
                                     FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
                                     FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id)

);
-- ********************** Services Management **********************

CREATE TABLE service_categories (
                                    category_id INT PRIMARY KEY AUTO_INCREMENT,
                                    category_name VARCHAR(255) UNIQUE NOT NULL,
                                    description TEXT
);


CREATE TABLE services (

                          service_id INT PRIMARY KEY AUTO_INCREMENT,
                          service_name VARCHAR(255) NOT NULL,
                          category_id int not null,
                          service_selling_price DECIMAL(10,2) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_id) REFERENCES service_categories(category_id)
);


CREATE TABLE service_payment (

                                 service_payment_id INT PRIMARY KEY AUTO_INCREMENT,
                                 reference_no INT NOT NULL,
                                 amount DECIMAL(10,2) NOT NULL,
                                 transaction_id VARCHAR(50) NOT NULL,
                                 payment_id INT NOT NULL,
                                 description TEXT,
                                 FOREIGN KEY (reference_no) REFERENCES services(service_id),
                                 FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                                 FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);


CREATE TABLE installment_plans (
                                   plan_id INT AUTO_INCREMENT PRIMARY KEY,
                                   plan_name VARCHAR(50) NOT NULL,
                                   total_installments INT NOT NULL
);


-- ********************** Installment System **********************


CREATE TABLE installment (
                             installment_id INT PRIMARY KEY AUTO_INCREMENT,
                             reference_no VARCHAR(100) NOT NULL,
                             sale_id VARCHAR(100) NOT NULL,
                             plan_id INT NOT NULL,
                             total_amount DECIMAL(15,2) NOT NULL,
                             FOREIGN KEY (plan_id) REFERENCES installment_plans(plan_id),
                             FOREIGN KEY (sale_id) REFERENCES sales(sale_id),
                             UNIQUE (reference_no, sale_id)
);

CREATE TABLE installment_payments (

                                      installment_payment_id INT PRIMARY KEY AUTO_INCREMENT,
                                      installment_id INT NOT NULL,
                                      payment_date DATETIME NOT NULL,
                                      installment_number INT NOT NULL,
                                      amount DECIMAL(15,2) NOT NULL,
                                      transaction_id VARCHAR(50) NOT NULL,
                                      payment_id INT NOT NULL,
                                      is_verified BOOLEAN DEFAULT FALSE,
                                      verified_by INT,
                                      verified_at DATETIME,
                                      FOREIGN KEY (installment_id) REFERENCES installment(installment_id),
                                      FOREIGN KEY (verified_by) REFERENCES users(user_id),
                                      FOREIGN KEY (payment_id) REFERENCES transaction_payments(payment_id),
                                      FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)

);

































