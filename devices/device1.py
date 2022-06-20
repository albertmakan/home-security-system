import os
from time import sleep


folder_path = "../my-house/target/classes/devices"
device_path = "/house5/camera1"
period = 2  # in seconds

path = folder_path+"/".join(device_path.split('/')[:-1])
if not os.path.exists(path):
    os.makedirs(path)

for i in range(50):
    with open(folder_path+device_path, 'w') as f:
        f.write(f"aaa example message {i}")
    sleep(period)
