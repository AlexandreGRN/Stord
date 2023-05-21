all:
	make install
	make setup

install:
	./install.sh

setup:
	cd API;\
	npm init -y;\
	apt install -y libpq-dev;\
	npm install -y mysql;\
	npm install express
