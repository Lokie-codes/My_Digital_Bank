FROM python:3.11-slim
WORKDIR /app
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
ENV DJANGO_SETTINGS_MODULE=auth_service.settings
EXPOSE 8000
CMD ["gunicorn", "auth_service.wsgi:application", "--bind", "0.0.0.0:8000"]
