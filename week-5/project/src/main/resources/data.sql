-- 매우 작은 기본 시드. 실제 측정용 시드(10만 row 등)는 각 미션 evidence/seed-data.sql에 둡니다.
INSERT INTO products (title, sku, status, stock, price, created_at) VALUES
  ('Sample Product 1', 'SKU-0001', 'ACTIVE', 100, 19900, CURRENT_TIMESTAMP),
  ('Sample Product 2', 'SKU-0002', 'ACTIVE', 50,  29900, CURRENT_TIMESTAMP),
  ('Sample Product 3', 'SKU-0003', 'INACTIVE', 0, 9900,  CURRENT_TIMESTAMP);
