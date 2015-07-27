# Setup Environment

## Install sublime text plugin for editorconfig

[Setup Guide](https://github.com/sindresorhus/editorconfig-sublime#readme)

## Setup PHP

1. Install PHP
The Yii2.0 require the verison of PHP must be >= 5.4.0, so before install PHP and its extensions, you must do like below first
```sh
sudo add-apt-repository ppa:ondrej/php5
sudo apt-get update
```
Then install PHP and its extensions
```sh
sudo apt-get install php5-cgi
sudo apt-get install php5-fpm
sudo apt-get install php5-curl
sudo apt-get install php5-mcrypt
sudo apt-get install php5-gd
sudo apt-get install php5-dev
```
or
```sh
sudo apt-get install php5-cgi php5-fpm php5-curl php5-mcrypt php5-gd php5-dev
```

2. Config PHP
```sh
vi /etc/nginx/sites-available/default
```
Modify 'default' file like below
```sh
index index.html index.htm index.php;
location ~ \.php$ {
fastcgi_split_path_info ^(.+\.php)(/.+)$;
　　# # NOTE: You should have "cgi.fix_pathinfo = 0;" in php.ini
　　#
　　# # With php5-cgi alone:
　　fastcgi_pass 127.0.0.1:9000;
　　# # With php5-fpm:
　　# fastcgi_pass unix:/var/run/php5-fpm.sock;
　　fastcgi_index index.php;
　　include fastcgi_params;
　　fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
}
```

3. PHP-FPM Status Management
```sh
/etc/init.d/php5-fpm {start|stop|quit|restart|reload|logrotate}
```
or
```sh
service php5-fpm {start|stop|quit|restart|reload|logrotate}
```

## Setup Redis
1. Install Redis
```sh
sudo apt-get install redis-server
```

2. Install PHP Redis extension
```sh
sudo apt-get install php5-redis
```

## Setup nginx
1. Install nginx
```sh
sudo apt-get install nginx
```

2. Config nginx
```sh
vi /etc/nginx/conf.d/wm.conf
```

Add below configuration to the file, **Change the folder name to your own project (/usr/share/nginx/www/aug-marketing)**

```sh
server {
    listen       80;
    server_name wm.com *.wm.com;
    root  /usr/share/nginx/www/aug-marketing/src/backend/web;
    index index.html index.htm index.php;

    access_log /var/log/nginx/wm.com-access.log;
    error_log  /var/log/nginx/wm.com-error.log;

    location / {
        proxy_pass http://localhost:81/;
    }

    location /api {
        try_files $uri $uri/ /index.php?$args;
    }

    location ~ .*\.(php|php5)?$ {
        fastcgi_pass   127.0.0.1:9000;
        # or fastcgi_pass unix:/var/run/php5-fpm.sock;
        include        fastcgi_params;
    }

    location ~ /\.(ht|svn|git) {
            deny all;
    }
}

server {
    listen       81;
    server_name wm.com *.wm.com;
    root  /usr/share/nginx/www/aug-marketing/src/frontend/web;
    index index.html index.htm index.php;

    access_log /var/log/nginx/wm.com-access.log;
    error_log  /var/log/nginx/wm.com-error.log;

    location / {
        try_files $uri $uri/ /index.php?$args;
    }

    location /vendor/ {
        alias /usr/share/nginx/www/aug-marketing/src/vendor/;
    }

    location /dist/ {
        alias /usr/share/nginx/www/aug-marketing/src/web/dist/;
    }

    location ~ .*\.(php|php5)?$ {
        fastcgi_pass   127.0.0.1:9000;
        # or fastcgi_pass unix:/var/run/php5-fpm.sock;
        include        fastcgi_params;
    }

    location ~ /\.(ht|svn|git) {
            deny all;
    }
}
```

**Restart nginx to make the configuration work**

```sh
sudo /etc/init.d/nginx reload
```

## Config your host file

```sh
vi /etc/hosts
```
Add below line to your host file
```sh
127.0.0.1 wm.com
```

## Setup Yii2

1. Install composer
```sh
curl -sS https://getcomposer.org/installer | php
mv composer.phar /usr/local/bin/composer
```

2. Config composer mirror
Follow [the guide](http://pkg.phpcomposer.com/)

3. Install composer asset plugin
```sh
composer global require "fxp/composer-asset-plugin:1.0.0-beta4"
```

4. Install dependent packages
```sh
cd /<DIR>/src
composer install
```

## Setup SCSS

1. Install ruby (version > 1.9.1)
```sh
sudo apt-get install ruby
```

2. Use mirror for ruby
```sh
gem sources --remove http://rubygems.org/
gem sources -a https://ruby.taobao.org/
gem sources -l
*** CURRENT SOURCES ***
https://ruby.taobao.org
```
Ensure only ruby.taobao.org exists

3. Installl sass
```sh
gem install sass
```

## Setup grunt and bower

1. Install latest nodejs
```sh
curl -o ~/node.tar.gz http://nodejs.org/dist/v0.10.24/node-v0.10.24.tar.gz
cd
tar -zxvf node.tar.gz
cd node-v0.6.18
./configure && make && sudo make install
```

2. Install grunt
```sh
npm install -g grunt-cli
```

3. Setup bower
```sh
npm install -g bower
```

## Run grunt task

```sh
cd src/frontend/web
npm install
bower install
grunt compile
grunt dev
```

**Every time you pull code from our repo, execute 'grunt compile'**

Access http://wm.com from brower

## Initial project

```sh
cd src
./initProd
```

## Setup Mongo

1. Install Mongo
[Setup Guide](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

2. Install PHP Mongo Extension
```sh
sudo pecl install mongo
```
After install successfully, create 'mongo.ini' file in `/etc/php5/mods-available`, write 'extension=mongo.so' to it. Then create symbol link like below
```sh
sudo ln -s /etc/php5/mods-available/mongo.ini /etc/php5/fpm/conf.d/30-mongo.ini
sudo ln -s /etc/php5/mods-available/mongo.ini /etc/php5/cgi/conf.d/30-mongo.ini
sudo ln -s /etc/php5/mods-available/mongo.ini /etc/php5/cli/conf.d/30-mongo.ini
```

3. Restart PHP-fpm
```sh
sudo service php5-fpm restart
```

## Reference

* [Grunt Get Started](http://gruntjs.cn/getting-started/)
* [Bower](http://bower.io)
* [SASS用法指南](http://www.ruanyifeng.com/blog/2012/06/sass.html)
* [CoffeeScript中文教程](http://coffee-script.org/)