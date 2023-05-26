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
	npm install -y express;\
	npm install -y pg;\
	npm install -y cors;\
	npm install -y dotenv

mysql:
	@mysql -h stord.cuderhz8pwyq.eu-north-1.rds.amazonaws.com -u Tulkiidra -p
