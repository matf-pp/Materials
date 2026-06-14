tupJ :: (Show a, Show b) => (a, b) -> String
tupJ (a, b) = "(" ++ show a ++ "," ++ show b ++ ")"

lstJ :: Show a => [a] -> String
lstJ lst = show lst

flstJ :: Show a => ([a] -> Bool) -> [[a]] -> [String]
-- Prvo biramo podliste koje zadovoljavaju predikat, zatim svaku pretvaramo u String.
flstJ p lst = map lstJ (filter p lst)

zipJ :: Show a => [a] -> [(a, String)]
-- Svaki element uparujemo sa njegovim tekstualnim prikazom.
zipJ lst = map (\x -> (x, show x)) lst
