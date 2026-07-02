-- Week 4 측정용 시드 (한 가지 풀이 sample, 약 10만 row)
-- 학생은 본인 환경에서 분량과 분포를 조정해도 된다.
-- 핵심은 "인덱스 효과를 측정 가능한 분량"이라는 점.

INSERT INTO products (title, sku, status, stock, price, created_at)
SELECT
  CONCAT('Sample Product ', X) AS title,
  CONCAT('SKU-', LPAD(X, 6, '0')) AS sku,
  CASE WHEN MOD(X, 7) = 0 THEN 'INACTIVE' ELSE 'ACTIVE' END AS status,
  MOD(X, 100) AS stock,
  10000 + (MOD(X, 50) * 100) AS price,
  DATEADD('SECOND', -X, CURRENT_TIMESTAMP) AS created_at
FROM SYSTEM_RANGE(1, 100000) AS T(X);
