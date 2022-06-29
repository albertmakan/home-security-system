from Cryptodome import Random
from Cryptodome.Hash import SHA256
from Cryptodome.Signature import PKCS1_v1_5
from Cryptodome.PublicKey import RSA
import base64


def rsa_keys():
    length = 1024
    private_key = RSA.generate(length, Random.new().read)
    public_key = private_key.publickey()
    return private_key, public_key


def sign(private_key, message):
    digest = SHA256.new()
    digest.update(message.encode('utf-8'))

    signer = PKCS1_v1_5.new(private_key)
    signature = signer.sign(digest)
    return signature.hex()


def verify(public_key, message, signature_str):
    digest = SHA256.new()
    digest.update(message.encode('utf-8'))

    signature = bytes.fromhex(signature_str)
    verifier = PKCS1_v1_5.new(public_key)
    verified = verifier.verify(digest, signature)
    return verified


if __name__ == '__main__':
    private_key, public_key = rsa_keys()  # generating keys
    print(private_key.exportKey().decode("utf-8"))
    print(public_key.exportKey().decode("utf-8"))

    message = 'Hello world'
    signature = sign(private_key, message)
    print(signature)

    print(verify(public_key, message, signature))