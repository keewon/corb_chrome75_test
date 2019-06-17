#!/usr/bin/env python3

import random, os
from http.server import BaseHTTPRequestHandler, HTTPServer

PORT = 3000
API_URL = ""

class MyHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-Type', 'text/html; charset=utf-8')

        self.end_headers()

        content = """
<html><head></head><body>
<div id="test">
Waiting for apiserver...
</div>
  <script type="text/javascript">
    fetch('https://%s/dummy/index?rand=%f', {
      method: 'GET',
      mode: 'cors'
    })
    .then(function(response) {
      return response.text();
    })
    .then(function(j) {
      if (j.length == 0) {
        const text = 'warning: repsonse is zero length';
        console.log(text);
        document.write(text);
      }
      else {
        console.log(j);
        document.write(j);
      }
    });
  </script>
</body></html>""" % (API_URL, random.random())

        self.wfile.write(bytes(content, 'utf-8'))

if __name__ == '__main__':
    API_URL = os.environ['API_URL']

    httpd = HTTPServer(('localhost', PORT), MyHandler)
    print("Listening: %s" % PORT)

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass

    httpd.server_close()
