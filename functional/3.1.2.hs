proizvodPrvihN :: Integer -> Integer
proizvodPrvihN n
      | n < 1 = 0
      | n == 1 = 1
      | otherwise = n * proizvodPrvihN (n-1)
