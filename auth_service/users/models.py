from django.db import models
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    """
    Custom User model extending Django's AbstractUser.
    Add any additional fields (e.g. KYC info) as needed.
    """
    phone_number = models.CharField("Phone Number", max_length=20, blank=True, null=True)
    address = models.TextField("Address", blank=True, null=True)
    date_of_birth = models.DateField("Date of Birth", blank=True, null=True)

    def __str__(self):
        return self.username
