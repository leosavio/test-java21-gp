from locust import HttpUser, task, between
import random

class WebsiteUser(HttpUser):
    wait_time = between(1, 2.5)
    host = "https://8080-leosavio-testjava21gp-2r2x4edik2e.ws-us104.gitpod.io"
    counter = 0
    keys = []

    headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
    }

    first_names = ["Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Isaac", "Jenny", "Leonardo", "Savio"]
    last_names = ["Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Savio", "Cesar", "Anderson", "Clark", "Davis", "Evans", "Franklin", "Harris", "Ingram", "Jackson", "King"]


    @task(1)
    def post_name_to_redis(self):
        # Increment counter
        WebsiteUser.counter += 1

        # Use the counter for key1's value
        payload = {
            "id": WebsiteUser.counter,
            "name": "test"
        }

        response = self.client.get("/demoj21/test", headers=self.headers, json=payload)
        # Print the response content
        print(response.content)
    