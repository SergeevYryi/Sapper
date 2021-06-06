package miner;

 class Bomb
{
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start ()
    {
        bombMap = new Matrix(Box.ZERO);
        for (int j = 0; j < totalBombs; j++)
        placeBomb();
    }

    Box get (Coordinate coordinate)
    {
        return bombMap.get(coordinate);
    }
    private void fixBombsCount()
    {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }
    private void placeBomb ()
    {
        while (true)
        {
            Coordinate coordinate = Ranges.getRandomCoordinate();
            if (Box.BOMB == bombMap.get(coordinate))
                continue;
            bombMap.set (coordinate, Box.BOMB);
            incNumbersAroundBomb(coordinate);
            break;
        }

    }
    private void incNumbersAroundBomb (Coordinate coordinate)
    {
        for (Coordinate around : Ranges.getCoordinateAround(coordinate))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set (around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs()
    {
        return totalBombs;
    }
}
