fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n 
    -- Rekurzija koristi definiciju F(n) = F(n-1) + F(n-2).
    | n > 0 = fib (n-1) + fib (n-2)
    | otherwise = 0
