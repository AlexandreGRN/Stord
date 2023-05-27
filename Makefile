all:
	make install
	make setup

install:
	./install.sh

setup:
	cd API;\
	npm init -y;\
	npm install -y mysql;\
	npm install -y express;\
	npm install -y pg;\
	npm install -y cors;\
	npm install -y dotenv;\
	npm install -y fs;\
	npm install -y https

mysql:
	@mysql -h stord.cuderhz8pwyq.eu-north-1.rds.amazonaws.com -u Tulkiidra -p
