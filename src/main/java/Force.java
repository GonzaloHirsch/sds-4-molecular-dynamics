public interface Force {
    double getFx();
    double getFy();
    void evaluate(double x, double y, double vx, double vy);
}
