docker-compose up -d postgres-auth redis
docker-compose run auth_service python manage.py makemigrations users
docker-compose run auth_service python manage.py migrate
