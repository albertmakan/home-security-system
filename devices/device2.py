import os
from time import sleep
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding


folder_path = "../my-house/target/classes/devices"
device_path = "/house5/door1"
period = 3  # in seconds
private_key_str = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDhudzGarRNFoCQ348nPgfsUeNI9mPoZTe3u9XuQgA0y2tRv8bpMwWXoIkZkk3yOSOVxEsYkm0AgUsBGX4Uxj5qY0XVT5ljN8RPis9OpEBbd8HBUNr2+1gM3R+zZ0+4foETSDuMGRhF86QBviqWMvPlb7I1z8CbeJ3XdAz2RJBKBQ9sawEHm5tgPMu85elTU9csSDwJNPdII75kfXeFUCKRd+U2l8Dxab0MeUoapJYMfe2JdJL7hHy6q4LmeLjozTrJAf68cIPrFb0u4MhJ3i5ceLpgqeK5pNIz+gbHnQkezO8rxdoBhZf01nG6DQ2EjzvgIvq3X1aEGuMZyWz/wiWnAgMBAAECggEAA0voRSJkOKV12dbxjvJd98gIejB2/PRXJsKq0K4djN26LpypzeXzb4kwLvcNVpNChgt1/lo97jr1KAoPUmyOv6Mbnlh24gEq2+IanDeQf/67r7XaAQA6K6ZYHemDO27nNhc/R82AwYKqs+zdoJA8kJBVFhlBn6HzeTkKSVC/wn42jXsDG88Rn44JusqEeXbUBwggjO9j/qp4vX8DKg/MISMDP0vI4yfe9j7batt9aRTW0nCadm0bqUcfWjmYUH0sHD3JJ3EWvAKqw0JkHQ5OxwOWqtyvOzvJ4MXaDS2yI/wyT9Z4iGxxG3Mbqv/8VFFFPTxOs9tBUNSWbKJSr7AeaQKBgQD9RoxW2MZXSjbwCAAiWcWMwWXJDXPoixh5ohjM7PJ9YlH6HKbbZ8comr3HF/f7Xj5QNRh30Xwc38a7ShK7jplqFkoxSSm/iHg0ZdjbuOVEx6nFKsYxxKx9WMMLUdw3foTHNZNfCKeAeIttCLh6+gL5jN+qqVYzBvzo202qGTYykwKBgQDkJ3NDt9vLg5gwQoH+ug44+H3LO6AmImBF0uNM2N2Aq6sLouvD/iKRy3DScR+LyeaAXigWaYKy7AN0fRqhDTkTJWSq3w858sZLC/wcolgM/6+NNjtQIBkomek1aLqKFb03QTt2Er+yd0tTIco2+/qlojRO+7ttQqL/ly7lkhPJHQKBgH7MVPIdsJ5+zyY4Oyj3XIltQTH4hDDFgklSt6vQbE4NbREUSwzI1dIkNiJ4g3Pa6MNq8Yb633HtLicRnHM9ntyXsCkggcAb6GIDIyBNXqqicwH8uiCsX9aZG3yNTPyTYJIC5nbLdGBC+TprBUC77cVVm5xrDnaZZwCE2krwOEv1AoGBALObetnse1MTHVOgh8WHonqm5CCBp+ldVnyhL97nOjh47AYVE5UPExfDF+YSt8rL/nE/rj82gHVp5q5lbfkq96ti3ITHZK0xuXiKzsWb58UT45c/AiJpomScBULOV0stIe/FopGo4NAVqW6gQxhV9VM/Bf7HnRy+QKME2R5QiNLhAoGBANLFGcKf1SgRZhMLD7xDSe1Ppi6kcxmaBKiNnd97Inb9kwCMOrKN0KDucaVIAWcn8WaNCXmjclFoxVd0G9cGGvSY5NdomIkmxUuQm2wY1fP4YHvkpj5lcdqq4lLFNWoy8rbm/9J9dr04z7PRLHU6XDt+wobI4hpkrBwYXy4pEgxe"
private_key = serialization.load_pem_private_key(
            str.encode(private_key_str),
            password=None,
            backend=default_backend()
        )
encrypted = private_key.encrypt(
        "door message 1",
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
path = folder_path+"/".join(device_path.split('/')[:-1])
if not os.path.exists(path):
    os.makedirs(path)

for i in range(50):
    with open(folder_path+device_path, 'w') as f:
        f.write(encrypted)
    sleep(period)
