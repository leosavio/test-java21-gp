from locust import HttpUser, task, between
import random

class WebsiteUser(HttpUser):
    wait_time = between(1, 2.5)
    host = "https://8080-leosavio-testjava21gp-2r2x4edik2e.ws-us104.gitpod.io"
    counter = 0

    headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
    }

    names = ["Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Isaac", "Jenny"]

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
        random_name = random.choice(WebsiteUser.names)
        
        payload = {
            "database:redis:creator": random_name
        }

        self.client.post("/demoj21/api/redis/strings", headers=self.headers, json=payload)
    