from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status, permissions
from rest_framework_simplejwt.tokens import RefreshToken

from .serializers import UserSerializer, LoginSerializer

class LoginView(APIView):
    """
    POST /api/auth/login/
    {
        "username": "...",
        "password": "..."
    }
    Returns JWT access & refresh tokens.
    """
    permission_classes = [permissions.AllowAny]

    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data['user']
        refresh = RefreshToken.for_user(user)
        return Response({
            'refresh': str(refresh),
            'access': str(refresh.access_token),
        }, status=status.HTTP_200_OK)

class LogoutView(APIView):
    """
    POST /api/auth/logout/
    Body: empty.
    Client should discard the token; token blacklisting not implemented here.
    """
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        # Optionally you could blacklist the refresh token here if using blacklist app
        return Response(status=status.HTTP_204_NO_CONTENT)

class MeView(APIView):
    """
    GET /api/auth/me/
    Returns current authenticated user’s profile.
    """
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        serializer = UserSerializer(request.user)
        return Response(serializer.data, status=status.HTTP_200_OK)
