# generate ssh key
    ssh-keygen

# clone project
    git clone git@git.example.com.cn:eriksson.luo/exam.git

# set user name
    git config --global user.name 'Kevin Sun'

# set user email
    git config --global user.email 'kevinsun@example.com.cn'

# set file mode
    git config --global core.filemode false

# set content editor
    git config --global core.editor vim

# set diff tool
    git config --global merge.tool vimdiff

# create or delete branch
    git branch kevin.sun

# check repository status
    git status

# add modified/untracked file[s]
    git add -u/-A

# commit changes
    git commit -m "comment..."

# push new commits to remote
    git push origin kevin.sun

# check out code from remote
    git pull -r

# create branch
    git branch branchName

# switch branch
    git checkout branchName

# create and switch a new branch
    git checkout -b branchName

# merge and rebase

本地分支与远程分支建立关联
`git branch --set-upstream-to=origin/<branch> your-branch`

创建一个新的分支并切换到该分支，并使该分支与指定的远程分支(remote-branch)建立关联
`git checkout -b your-branch -t origin remote-branch`

执行上面的语句后，可以直接执行`git pull` 或 `git pull --rebase`了

## merge

`git pull origin remote-branch` 内部执行 `git fetch && git merge` 命令，此时会创建一个新的commit。

在merge后，你可以编写新的code或执行`git stash pop`继续写你没写完的code。
注意提交时，**一定不要使用`git commit --amend` 命令**，
因为，如果使用此命令后，将会在merge后生成的新的commit基础上进行提交。你所编写的code就并入到了这个commit，
此次commit的内容就会不光有你的新代码，还有你之前merge的远程分支上最新的一次修改内容。
这就是有时候会遇到在执行`git show commit-hash-code`查看本次commit的详细内容时，会出现很多不是自己修改的代码。

**正确的操作应该是`git commit -m "your-comment"`**，执行一次新的提交，而此次的commit内容就会是你所修改的代码。

## rebase

rebase 操作分为两种 `git rebase local-branch` 和 `git pull origin remote-branch --rebase`。

其中`git pull origin remote-branch --rebase`是对远程分支的操作。期内部执行的是`git fetch && git rebase`

执行rebase命令不会产生新的commit，它实际上是在你的分支上，将你要rebase的这个分支的变化过程进行重演。

rebase之后，你再编写你的新code或者`git stash pop`继续之前的code编写，这些动作都是在你之前commit的基础上进行的。

*你可以用`git log`命令查看commit的信息来比较merge和rebase的区别。*

- 如果你是编写的新Feature的代码。你可以执行`git commit -m "your-comment"`,
- 如果是继续编写之前的code，你可以执行`git commit --amend`,重新编辑你上次的commit并提交。
- 如果提交时添加了`--amend`参数，那么此次的commit内容就是执行rebase前的那次提交的内容加上你rebase后新修改的内容。

## push.default

- matching - push all matching branches. All branches having the same name in both ends are considered to be matching. This is the default in Git 1.x.
- upstream - push the current branch to its upstream branch...
- simple - like upstream, but refuses to push if the upstream branch’s name is different from the local one...
- current - push the current branch to a branch of the same name.
