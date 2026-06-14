fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n 
    -- Svaki clan niza dobija se sabiranjem prethodna dva clana.
    | n > 0 = fib (n-1) + fib (n-2)
    | otherwise = 0
