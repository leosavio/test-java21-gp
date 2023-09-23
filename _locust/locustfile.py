from locust import HttpUser, task, between

class WebsiteUser(HttpUser):
    wait_time = between(1, 2.5)
    host = "https://8080-leosavio-testjava21gp-2r2x4edik2e.ws-us104.gitpod.io"
    counter = 0

    headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
    }

    @task(1)
    def post_test(self):
        # Increment counter
        WebsiteUser.counter += 1

        # Use the counter for key1's value
        payload = {
            "id": WebsiteUser.counter,
            "name": "test"
        }

        self.client.post("/customers", headers=self.headers, json=payload)