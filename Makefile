run1: API_URL = $(shell curl -sq http://127.0.0.1:4314/api/tunnels | grep -m 1 -o "[0-9a-z]*\.ngrok.io" | head -1)
run1:
	cd python_servers; API_URL=${API_URL} python3 server1.py
	#cd ruby_servers/server1; API_URL=${API_URL} bundle exec bin/rails server

run2:
	cd python_servers; python3 apiserver.py
	#cd ruby_servers/apiserver; bundle exec bin/rails server

ngrok1:
	ngrok http 3000 --config ngrok1.yml
ngrok2:
	ngrok http 3100 --config ngrok2.yml
