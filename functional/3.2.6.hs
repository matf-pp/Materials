harm :: Int -> [Double]
harm n = [1 / fromIntegral k | k <- [1..n]]
