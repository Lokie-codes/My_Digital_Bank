from rest_framework import serializers
from django.contrib.auth import authenticate
from .models import User

class UserSerializer(serializers.ModelSerializer):
    """
    Serializer for exposing User model fields.
    """
    class Meta:
        model = User
        fields = [
            'id',
            'username',
            'email',
            'first_name',
            'last_name',
            'phone_number',
            'address',
            'date_of_birth',
        ]
        read_only_fields = ['id']

class LoginSerializer(serializers.Serializer):
    """
    Serializer for user login. Returns a User if credentials are valid.
    """
    username = serializers.CharField(write_only=True)
    password = serializers.CharField(write_only=True, style={'input_type': 'password'})

    def validate(self, attrs):
        user = authenticate(
            username=attrs.get('username'),
            password=attrs.get('password')
        )
        if not user:
            raise serializers.ValidationError('Invalid username or password.', code='authorization')
        attrs['user'] = user
        return attrs
