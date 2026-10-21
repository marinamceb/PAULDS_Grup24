# Doors - Chain of Responsibility

Access controller for doors implemented with the Chain of Responsibility pattern.

## Structure

- `src/` Java sources (default package)
  - `Door` door state (locked, number of trials) and its chain of handlers
  - `CodeHandler` abstract handler, passes the code to the next handler
  - `Log`, `Unlock`, `FireAlarm`, `Open`, `Lock` concrete handlers
  - `Client` builds chain1, chain2, chain3 and tests them
- `doors.puml` PlantUML class diagram, `doors.png` the rendered image

## Compile and run

```
javac -d out src/*.java
java -cp out Client        # tests of the three chains
java -cp out Client 1      # only chain1 (also 2 or 3)
java -cp out Client -i     # interactive: type codes for a door with chain1
```

Codes used: open `1111`, fire alarm `2222`, unlock `3333`.

## Design change with respect to the slides

Failed trials are counted in `Lock` instead of `Open`. A code only reaches
`Lock` when no previous handler served it, so it is a failed attempt and it is
counted exactly once, even when both `FireAlarm` and `Open` are in the chain.
Doors without `Lock` don't count anything. Because of this, `Lock` must be
the last handler of the chain (order of handlers matters).

`Door.setCodeHandler()` also resets the door state, so a new behaviour starts
from an unlocked door with 0 trials.
