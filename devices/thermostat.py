import os
from time import sleep
from keygen import sign
from Cryptodome.PublicKey import RSA
from base64 import b64decode

import random
import json


folder_path = "../my-house/target/classes/devices"
device_path = "/house5/thermostat1"
period = 2  # in seconds
private_key_str = "\
MIICXAIBAAKBgQCvoRpDUw84yGIRHRhGV9TkFSBEBZBCeyyGCtTi70Vbmcan7r8l\
GipBmPurE9/CbyjuZPGO5xICJRKHhUeBRptEnlVI9KcACzszwS3WAHLE6j8w+I56\
1Lw2uTrY3ET+pmvkA7NEf+LwAadvhlEnDQfwwXOlu9kuovxA9vRMHS33uwIDAQAB\
AoGAEer85nHNY5d8pc6oQNj0ynY7Tfqb/qRZZQLVjaIn8HyjpKi6DeYkTyqf671s\
foY1EKlHbwhfsSH+Hh8OA1WO696yRhSZr5pTfyN1VlWHGY+NpK07dxwbQs76ujON\
X+qZeLN0YwM378Zemz3QJDT5k08QadK5nWZBBe8Bq4p6SuUCQQC3mFaCgOfZTnyi\
3ZTrFnyIOcOYapDIfO0IsKOjfg2RIIfo4cw3TLY22hAdPGBdbVlJB+RAEiGKAOo6\
eQZgGkwFAkEA9OSLnzQeiaA8glklHoUtZZFaz4VjiG7VAQugkHQoaq5Q/2g0YdIQ\
U8t+CZ7xqon+6A9sFUux+IQ6+5Hfj7ZAvwJAeVQVJ51kmrlBqKfieBGeLrBt0FXs\
ZHIaezmwiqUM9jGXX5GI0arCaDFnoMp7rIJNFPRAJQ9WVssP/6BnVC9MuQJBANB1\
t7BmolQO1VMz5RgMz670w3OBHU/m4GSKbDxq1HizN/mhz20xbnqV7wzrgtXjr9wE\
PMIH9FAS5058RuzDfGsCQAwp+0BqFkUULpUIscpG7P0Mf1qExBkO3bCMfih4h+UB\
9ljlczyS/4HHI6ohmlqVtMgY9Y8cJLrECt0RS5ySHiw="

path = folder_path+"/".join(device_path.split('/')[:-1])
if not os.path.exists(path):
    os.makedirs(path)

key = RSA.importKey(b64decode(private_key_str))

message = {"value": 0, "stateTime": 0, "day": True}


while True:
    day = 0
    new_value = random.randint(-10, 100)
    message["stateTime"] = message["stateTime"] + period if new_value == message["value"] else 0
    message["value"] = new_value
    message["day"] = True if day <= 720 else False

    day = day + 1 if day <= 1440 else 0

    message_str = json.dumps(message)
    with open(folder_path+device_path, 'w') as f:
        f.write(message_str + " " + sign(key, message_str))
        print(message_str + " " + sign(key, message_str))
    sleep(period)