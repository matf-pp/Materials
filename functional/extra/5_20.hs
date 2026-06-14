uDekadnu :: Int -> Int -> Int
-- Pretvaranje u dekadni zapis obradjuje cifre zdesna nalevo.
uDekadnu x osn = if x==0 then 0 else uDekadnu (x `div` 10) osn * osn + (mod x 10)

izDekadne :: Int -> Int -> Int
izDekadne x osn = if x==0 then 0 else izDekadne (x `div` osn) osn * 10 + (mod x osn)

osnova :: Int -> Int -> Int -> Int
-- Broj prvo prevodimo u dekadni zapis iz osnove o1, pa zatim iz dekadnog u osnovu o2.
osnova x o1 o2 = izDekadne (uDekadnu x o1) o2
