FROM node:14-alpine
# FROM node:14

# 设置工作目录
WORKDIR /app

# 将package.json和package-lock.json复制到工作目录
COPY package*.json ./

# 安装依赖
RUN npm install

# 将项目文件复制到工作目录（注意这里使用的是相对路径）
COPY . .

# 设置为生产环境
ENV NODE_ENV=production

# 暴露容器的3000端口
EXPOSE 3000

# 运行React项目
CMD ["npm", "start"]