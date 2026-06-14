jednocifreniDelioci :: Int -> [Int]
-- Proveravamo samo delioce od 1 do 9, jer zadatak trazi jednocifrene delioce.
jednocifreniDelioci n = [x | x <- [1..9], mod n x == 0]
