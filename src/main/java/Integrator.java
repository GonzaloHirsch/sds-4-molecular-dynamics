public class Integrator {

    private double deltaTime;

    // Gear predictor coefficients
    private final double[] gearAlphas = new double[]{3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};

    // Gear predictor delta powers and factorials for reduced computation
    private final double[] gearDeltaFactorials;

    // Gear predictor derivatives
    private double gearDerivatives[];

    public Integrator(Particle p, double deltaTime){
        this.deltaTime = deltaTime;
        this.gearDeltaFactorials = new double[]{1, deltaTime, Math.pow(deltaTime, 2)/2, Math.pow(deltaTime, 3)/6, Math.pow(deltaTime, 4)/24, Math.pow(deltaTime, 5)/120};
        this.gearDerivatives = this.initGearDerivatives(this.k, p);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //                                  ANALYTICAL
    /////////////////////////////////////////////////////////////////////////////////////

    public double analyticalSolution(double time, double gamma, double k, double mass) {
        double exponential = Math.exp(-(gamma / (2*mass)) * time);
        double cosine = Math.cos(Math.pow((k/mass - (gamma*gamma)/(4*(mass*mass))), 0.5) * time);
        return exponential * cosine;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //                                  BEEMAN
    /////////////////////////////////////////////////////////////////////////////////////

    public void beeman(Particle p, Force f, double dt) {
        /* Calculating force components */
        f.evaluate();

        /* Calculating future accelerations TODO -> algo con la fuerza */
        double predictedAx = f.getFx() / p.getMass();
        double predictedAy = f.getFy() / p.getMass();

        /* Predicting positions */
        double predictedX = this.beemanPositionPrediction(p.getX(), p.getVx(), p.getAx(), p.getPrevAx(), dt);
        double predictedY = this.beemanPositionPrediction(p.getY(), p.getVy(), p.getAy(), p.getPrevAy(), dt);

        /* Predicting velocities */
        double predictedVx = this.beemanVelocityPrediction(p.getVx(), p.getAx(), p.getPrevAx(), predictedAx, dt);
        double predictedVy = this.beemanVelocityPrediction(p.getVy(), p.getAy(), p.getPrevAy(), predictedAy, dt);

        /* Set particles future predictions */
        p.setFutureX(predictedX);
        p.setFutureY(predictedY);
        p.setFutureVx(predictedVx);
        p.setFutureVy(predictedVy);
        p.setFutureAx(predictedAx);
        p.setFutureAy(predictedAy);
    }

    private double beemanPositionPrediction(double r, double v, double a, double aPrev, double dt) {
        return r + v*dt + (2.0/3.0)*a*dt*dt + (1.0/6.0)*aPrev*dt*dt;
    }

    private double beemanVelocityPrediction(double v, double a, double aPrev, double aFuture, double dt) {
        return v + ((1.0/3.0)*aFuture + (5.0/6.0)*a + (1.0/6.0)*aPrev) * dt;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //                              EULER PREDICTOR CORRECTOR
    /////////////////////////////////////////////////////////////////////////////////////

    private double[] eulerPrediction(Particle p, Force f, double dt) {
        double predictedX = p.getX() + p.getVx() * dt;
        double predictedY = p.getY() + p.getVy() * dt;

        double predictedVx = p.getVx() + p.getAx() * dt;
        double predictedVy = p.getVy() + p.getAy() * dt;

        f.evaluate(predictedX, predictedY, predictedVx, predictedVy);

        double predictedAx = f.getFx() / p.getMass();
        double predictedAy = f.getFy() / p.getMass();

        double[] calculations = new double[4];
        calculations[0] = p.getVx() + predictedAx * dt;
        calculations[1] = p.getVy() + predictedAy * dt;

        calculations[2] = p.getX() + calculations[0] * dt;
        calculations[3] = p.getY() + calculations[1] * dt;

        return calculations;        
    }


    /////////////////////////////////////////////////////////////////////////////////////
    //                              GEAR PREDICTOR CORRECTOR
    /////////////////////////////////////////////////////////////////////////////////////

    private double[] initGearDerivatives(double k, Particle p){
        double[] derivatives = new double[6];
        derivatives[0] = p.getX();
        derivatives[1] = p.getVx();
        derivatives[2] = (- k / p.getMass()) * p.getX();
        // FIXME: ver si hay que poner 0 o como dice el PPT en pag 29
        derivatives[3] = 0;
        derivatives[4] = 0;
        derivatives[5] = 0;
        return derivatives;
    }

    public void gearPredictorCorrector(Particle p, double dt){

    }
}
