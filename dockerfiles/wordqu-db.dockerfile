#Start MariaDB
FROM mariadb:latest
ADD ../sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD iauwda23d
ENV MYSQL_DATABASE wordquiz
ENV MYSQL_USER wordquiz
ENV MYSQL_PASSWORD SAVXnd7kcjw
EXPOSE 3306
CMD ["mysqld"]
