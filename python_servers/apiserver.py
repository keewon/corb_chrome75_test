#!/usr/bin/env python3

from http.server import BaseHTTPRequestHandler, HTTPServer

import json

PORT = 3100

class MyHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-Type', 'application/json; charset=utf-8')
        if True and self.headers['Origin']:
            self.send_header('Access-Control-Allow-Origin', self.headers['Origin'])
        else:
            self.send_header('Access-Control-Allow-Origin', '*')

        self.send_header('Access-Control-Allow-Methods', 'GET')
        self.send_header('Access-Control-Allow-Headers', '*')
        self.send_header('Access-Control-Max-Age', "1728000")
        self.send_header('Access-Control-Allow-Credentials', 'true')

        self.end_headers()

        x = open('test.json', 'rb').read()
        self.wfile.write(x)

if __name__ == '__main__':
    httpd = HTTPServer(('localhost', PORT), MyHandler)
    print("Listening: %s" % PORT)

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()
