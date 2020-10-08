# Simulación de Sistemas - TP4

## Files
### Static File
The static file contains the mass and radius of the Sun, Earth, Mars and Spaceship. The contents of the file are:
```
sun_mass sun_radius
earth_mass earth_radius
mars_mass mars_radius
spaceship_mass spaceship_radius
```

### Dynamic File

**NOTE:** The information for the static data is given by NASA and the assignment

## Pre Processing
In order to obtain the velocity and position of the ship, we use a script to calculate it:
```
./preprocessing/calculate_spaceship_data.py -V 8 -v 7.12 -o 1500
```

The -V parameter is the orbital velocity of the station, -v parameter is the orbital velocity of the ship and -o is the orbital distance of the station. Units are in km and km/s

## Simulation
### Singular Run
Before running the script, it is needed permissions:
```
chmod u+x ./scripts/run_oscillator.sh
```

To run the analytical and numerical simulations at the same time, the script can be used:
```
./scripts/run_oscillator.sh 0.0001 50
```

Where `0.0001` is the delta of time, and `50` is the amount of delta of time to be skipped each time when generating output. 

### Multiple Run
Another option to generate data is to run the script that generates data using dt from 10^-2 to 10^-8

Before running the script, it is needed permissions:
```
chmod u+x ./scripts/run_oscillator_all.sh
```

To run the analytical and numerical simulations at the same time, the script can be used:
```
./scripts/run_oscillator_all.sh 20
```

Where `20` is the amount of delta of time to be skipped each time when generating output. 

### System Simulation without Ship
This option is to run the simulation without the ship.

Before running the script, it is needed permissions:
```
chmod u+x ./scripts/run_simulation.sh
```

To run the simulation, the script can be used. It runs the simulation and post processes information to generate animation file:
```
./scripts/run_simulation.sh 0.1 216000 31536000 ns
```

The first argument is the delta time, the second argument is the delta multiplicator, the third argument is the total time and the fourth argument indicates whether or not the ship is simulated(ws/ns). Times are in seconds.

The present configuration is delta of 0.1 second, taking measurements every 216000 dts (6 hours), and the simulation runs for 31536000 seconds (365 days). Without the ship. 

### System Simulation with Ship
This option is to run the simulation with the ship.

Before running the script, it is needed permissions:
```
chmod u+x ./scripts/run_simulation.sh
```

To run the simulation, the script can be used. It runs the simulation and post processes information to generate animation file:
```
./scripts/run_simulation.sh 0.1 216000 31536000 ws
```

The first argument is the delta time, the second argument is the delta multiplicator, the third argument is the total time and the fourth argument indicates whether or not the ship is simulated(ws/ns). Times are in seconds.

The present configuration is delta of 0.1 second, taking measurements every 216000 dts (6 hours), and the simulation runs for 31536000 seconds (365 days). With the ship .

## Post Processing
### Oscillator Trajectory Graph
In order to plot the trajectory for the oscillators, first the simulation has to be run. To plot the trajectory for the 4 different methods, we run:
```
python3 ./post/post_oscillator.py -t p
```

### Oscillator Extract Errors
In order to calculate and extract the Mean Cuadratic Error from the simulation, we run:
```
python3 ./post/post_oscillator.py -t ee -dt 0.001
```

Where `0.001` is the delta of time used in the run to be analyzed 

### Oscillator Errors Plotting
In order to plot the oscillator errors, it is recommended to run the **Multiple Run** script in order to automate data recovery and avoid mistakes. We run:
```
python3 ./post/post_oscillator.py -t pe
```

This will generate a plot in logarithmic scales

## Visualization
Ovito is used for this.

In order to generate the animation files, a script can be used to convert simulation output into an animation file:
```
python3 ./visualization/process.py
```

**NOTE:** Scripts mentioned before already run this command, it is not necessary to run it, but if changes are introduced, it should be used.
**NOTE-2:** This script changes the radius of the objects in order to make them visible in the animation because normal radius would be very small and not/barely visible.