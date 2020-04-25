import Classes.Solution;

import java.io.IOException;
import java.util.List;

public interface Solver {
    public void solve() throws IOException;
    public void solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations) throws IOException;
}

