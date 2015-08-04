# MongoDB 学习笔记

## 数据迁移

- Useage:

mongodump --host mongodb1.example.net --port 37017 --username user --password pass --out /opt/backup/mongodump-2011-10-24

mongorestore --host mongodb1.example.net --port 37017 --username user --password pass /opt/backup/mongodump-2011-10-24

- Example:

mongodump --host 61.152.132.246 --port 27017 --db augmarketing --username aug --password abc123_ --out /home/user/Documents/backup/mongodump-2015-07-27

mongorestore --host 127.0.0.1 --port 27017 /home/user/Documents/backup/mongodump-2015-07-27
