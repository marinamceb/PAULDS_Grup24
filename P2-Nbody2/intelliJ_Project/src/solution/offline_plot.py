import sys
import numpy as np
import matplotlib.pyplot as plt

fname = sys.argv[1]
with open(fname) as f:
    num_bodies = int(f.readline())   # primera línia: nombre de cossos
    data = np.loadtxt(f)             # la resta: columnes x y

x = data[:, 0].reshape(-1, num_bodies)  # una fila per pas, una columna per cos
y = data[:, 1].reshape(-1, num_bodies)

plt.figure()
for nb in range(num_bodies):
    plt.plot(x[:, nb], y[:, nb], '-')     # trajectòria
    plt.plot(x[-1, nb], y[-1, nb], 'k.')  # posició final
plt.axis('equal')
plt.title(fname)
plt.show()