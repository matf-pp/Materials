magicni :: [Int] -> [(Int, Int)]
-- Za svaki broj cuvamo par: originalni broj i proizvod njegovih cifara.
magicni l = [(n, proizvodCifara n) | n <- l]

proizvodCifara :: Int -> Int
proizvodCifara 0 = 0
proizvodCifara n = proizvodCifara' n 1
  where
    proizvodCifara' 0 acc = acc
    -- Akumulator pamti proizvod do sada obradjenih cifara.
    proizvodCifara' x acc = proizvodCifara' (x `div` 10) (acc * (x `mod` 10))
