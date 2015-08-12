# NPM 笔记

## 使用`nvm`工具管理`node`版本
- Installation
    1. Manual Install
    ```shell
    git clone https://github.com/creationix/nvm.git ~/.nvm
    cd ~/.nvm
    git checkout `git describe --abbrev=0 --tags`
    ```
    2. Install script
    ```shell
    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.25.4/install.sh | bash
    // or Wget
    wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.25.4/install.sh | bash
    ```
    3. Add these phrase into `~/.profile`
    ```shell
    export NVM_NODEJS_ORG_MIRROR=https://npm.taobao.org/dist
    source ~/.nvm/nvm.sh
    ```

## 使用npm国内镜像
1. 临时使用
    ```shell
    npm --registry https://registry.npm.taobao.org install xxxx
    ```
2. 持久使用
    ```shell
    npm config set registry https://registry.npm.taobao.org

    // 配置后可通过下面方式来验证是否成功
    npm config get registry
    // 或
    npm info xxxx
    ```
3. 通过cnpm使用
    ```shell
    npm install -g cnpm --registry=https://registry.npm.taobao.org

    // 使用
    cnpm install express
    ```

