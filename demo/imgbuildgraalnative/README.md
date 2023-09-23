




docker build -t demoj21:latest .

Create secrets for login at dockerhub

username: ${{ secrets.DOCKERHUB_USERNAME }}
password: ${{ secrets.DOCKERHUB_TOKEN }}