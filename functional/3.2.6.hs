harm :: Int -> [Double]
-- fromIntegral pretvara ceo broj u realan da bi se dobilo realno deljenje.
harm n = [1 / fromIntegral k | k <- [1..n]]
