from flask import Flask, render_template, request, jsonify
import requests, json, hashlib

app = Flask(__name__, static_url_path='')

@app.route("/")
def main():    
    d = {'info': 'API Root', 'version': 1.0}
    return jsonify(d)
    
@app.route("/hash", methods=['POST'])
def hash():
    
    try:
        myText = json.loads(request.data)
        sha_signature = hashlib.sha256( myText['text'].encode() ).hexdigest()
        d = {'hash': sha_signature}
        statusCode = 200
    except:
        d = {'error': 'Wrong data format!'}
        statusCode = 400
    
    return jsonify(d), statusCode


if __name__ == "__main__":
    app.run(debug=True, port=5000)
