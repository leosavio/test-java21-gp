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
    def post_test(self):
        # Increment counter
        WebsiteUser.counter += 1

        # Use the counter for key1's value
        payload = {
            "id": WebsiteUser.counter,
            "name": "test"
        }

        self.client.post("/demoj21/customers", headers=self.headers, json=payload)


    @task(2)
    def post_name_to_redis(self):
         # Pick a random name
        first_name = random.choice(WebsiteUser.first_names)
        last_name = random.choice(WebsiteUser.last_names)
        full_name = f"{first_name} {last_name}"

        # Use the counter for key's value
        key_name = f"database:redis:{WebsiteUser.counter}"

        payload = {
            key_name: full_name
        }

        response = self.client.post("/demoj21/api/redis/strings", headers=self.headers, json=payload)
        WebsiteUser.keys.append(key_name)
        # Print the response content
        print(response.content)

    @task(3)
    def get_data_from_redis(self):
        if WebsiteUser.keys:
            key_to_fetch = random.choice(WebsiteUser.keys)
            response = self.client.get(f"/demoj21/api/redis/strings/{key_to_fetch}")
            
            # Print the response content
            print(response.content)