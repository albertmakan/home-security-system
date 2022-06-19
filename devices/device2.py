import os
from time import sleep


folder_path = "../my-house/target/classes/devices"
device_path = "/house5/door1"
period = 3  # in seconds

path = folder_path+"/".join(device_path.split('/')[:-1])
if not os.path.exists(path):
    os.makedirs(path)

for i in range(50):
    with open(folder_path+device_path, 'w') as f:
        f.write(f"ddd door message {i}")
    sleep(period)
