apt update -y
apt install -y build-essential
apt install -y curl
apt install -y bash
apt install -y git
apt install -y mysql-server
apt install -y libpq-dev
curl https://raw.githubusercontent.com/creationix/nvm/master/install.sh | bash
source ~/.bashrc
nvm install --lts
apt install python3
apt upgrade -y