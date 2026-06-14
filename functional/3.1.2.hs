proizvodPrvihN :: Integer -> Integer
proizvodPrvihN n
      | n < 1 = 0
      | n == 1 = 1
      -- Rekurzivni korak svodi proizvod 1..n na n * proizvod 1..(n-1).
      | otherwise = n * proizvodPrvihN (n-1)
