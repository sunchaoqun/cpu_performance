# Getting Started



mvn clean package

aws sts get-caller-identity 

# 获取ECR登录令牌
aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin 932423224465.dkr.ecr.ap-southeast-1.amazonaws.com

aws ecr create-repository \
    --repository-name cpu-performance-test \
    --region ap-southeast-1

aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws

# 构建镜像
docker build -t cpu-performance-test .

# 标记镜像
docker tag cpu-performance-test:latest 932423224465.dkr.ecr.ap-southeast-1.amazonaws.com/cpu-performance-test:latest

# 推送到ECR
docker push 932423224465.dkr.ecr.ap-southeast-1.amazonaws.com/cpu-performance-test:latest

sudo apt install apache2-utils

ab -n 1000 -c 100 http://127.0.0.1:8080/cpu-test


aws eks --region us-west-2 update-kubeconfig --name us-west-2


ab -n 100000 -c 100 http://172.31.58.200:30080/cpu-test

ab -n 100000 -c 100 http://172.31.62.205:30081/cpu-test


ab -n 1000 -c 100 "http://172.31.58.200:30080/memory-test?blockSizeMB=100"

ab -n 100000 -c 100 "http://172.31.62.205:30081/memory-test?blockSizeMB=100"

curl http://172.31.58.200:30080/memory-reset

kubectl get svc

# 创建插件目录
mkdir -p ~/.docker/cli-plugins/

# 下载最新版本的 buildx (v0.12.1)
wget https://github.com/docker/buildx/releases/download/v0.12.1/buildx-v0.12.1.linux-amd64 -O ~/.docker/cli-plugins/docker-buildx

# 如果上面的命令失败，可以尝试最新版本 v0.13.0
wget https://github.com/docker/buildx/releases/download/v0.13.0/buildx-v0.13.0.linux-amd64 -O ~/.docker/cli-plugins/docker-buildx

# 添加执行权限
chmod +x ~/.docker/cli-plugins/docker-buildx

# 验证安装
docker buildx version

# 1. 创建 buildx builder（不需要 --name 参数）
docker buildx create --use

# 2. 检查构建器状态
docker buildx ls


docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t 932423224465.dkr.ecr.ap-southeast-1.amazonaws.com/cpu-performance-test:latest \
  --push \
  .


kubectl exec -it cpu-test-m6i-7fc7cffbdf-nlcw5 -- tail -f /tmp/gc.log