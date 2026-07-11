import Data.List (nub, sort)

izdvojNivo :: Int -> [(Int, String)] -> [(Int, String)]
izdvojNivo n = filter (\(nivo, _) -> nivo == n)

brojGresaka :: [(Int, String)] -> Int
brojGresaka = length . filter (\(nivo, _) -> nivo == 3)

grupiši :: [(Int, String)] -> [(Int, [String])]
grupiši poruke =
    [ (nivo, [poruka | (nivo', poruka) <- poruke, nivo' == nivo])
    | nivo <- sort (nub [nivo | (nivo, _) <- poruke])
    ]

najcesciNivo :: [(Int, String)] -> Maybe Int
najcesciNivo [] = Nothing
najcesciNivo poruke = Just (minimum najcesci)
  where
    grupe = grupiši poruke
    brojPoNivou = [(nivo, length porukeNivoa) | (nivo, porukeNivoa) <- grupe]
    najvise = maximum [broj | (_, broj) <- brojPoNivou]
    najcesci = [nivo | (nivo, broj) <- brojPoNivou, broj == najvise]

kompresuj :: [(Int, String)] -> [(Int, String, Int)]
kompresuj [] = []
kompresuj ((nivo, poruka) : ostatak) =
    (nivo, poruka, 1 + length iste) : kompresuj dalje
  where
    (iste, dalje) = span (== (nivo, poruka)) ostatak
