import numpy as np
import pandas as pd
import pyflux as pf
from datetime import datetime
import matplotlib.pyplot as plt
#%matplotlib inline

data = pd.read_csv('https://vincentarelbundock.github.io/Rdatasets/csv/datasets/sunspot.year.csv')
data.index = data['time'].values

plt.figure(figsize=(15,5))
plt.plot(data.index,data['value'])
plt.ylabel('Sunspots')
plt.title('Yearly Sunspot Data');
plt.show()

model = pf.ARIMA(data=data, ar=4, ma=4, target='value', family=pf.Normal())
x = model.fit("MLE")
x.summary()
model.plot_z(figsize=(15,5))
plt.show()
model.plot_fit(figsize=(15,10))
plt.show()