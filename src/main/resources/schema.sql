CREATE TABLE accounts (
  account_id SERIAL PRIMARY KEY,
  account_number VARCHAR(16),
  account_type VARCHAR(50),
  balance DECIMAL(10, 2),
  currency VARCHAR(3),
  owner VARCHAR(100)
);

CREATE TABLE transactions (
  transaction_id SERIAL PRIMARY KEY,
  date DATE,
  description TEXT,
  amount DECIMAL(10, 2),
  currency VARCHAR(3),
  account_id INTEGER REFERENCES accounts(account_id)
);

CREATE TABLE savings_goals (
  id SERIAL PRIMARY KEY,
  target_date DATE,
  description TEXT,
  goal_amount DECIMAL(10, 2),
  current_amount DECIMAL(10, 2),
);


CREATE TABLE rewards (
  id SERIAL PRIMARY KEY,
  name VARCHAR(40)
  description TEXT,
  savings_goal_id INTEGER REFERENCES rewards(id)
);