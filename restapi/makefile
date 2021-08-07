db-up:
	echo "123" | sudo -S docker run -e POSTGRES_DB=spring-api -e POSTGRES_PASSWORD=123456 -p 5434:5432 postgres:12.5

pgadmin-up:
	echo "123" | sudo -S docker run --network host -e "PGADMIN_LISTEN_PORT=5050" -e "PGADMIN_DEFAULT_EMAIL=ngotanphuc@amitgroup.vn" -e "PGADMIN_DEFAULT_PASSWORD=123456" dpage/pgadmin4
	