-- Week 4 인덱스 미션 (한 가지 풀이 sample)
--
-- 측정 결과 ProductService.findLatestActiveProducts 와 searchByKeyword 가
-- 큰 시드(10만 row) 환경에서 full scan 으로 잡혔다.
-- (status='ACTIVE') + (created_at DESC) 조합 인덱스를 추가해
-- range scan 으로 전환되는지 확인한다.

CREATE INDEX IF NOT EXISTS idx_products_status_created_at
  ON products (status, created_at DESC);

-- 검색은 LIKE '%keyword%' 형태라 인덱스가 직접 잡히지는 않는다.
-- 학생은 evidence/explain-after.txt에 그 한계를 같이 적는다.
