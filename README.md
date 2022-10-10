**DemoGithub App**

MVVM, Koin, RxJava2, Paging with RemoteMediator, Room, Retrofit, Glide, Pull-to-Refresh, Github API

To use GithubAPI set the env variables `GITHUB_USER_NAME` and `GITHUB_USER_PASSWORD`

* _API documentation:_ [REST API](https://docs.github.com/en/rest)

* Short way:

Add to the bottom of your `~/.bash-profile`

```
export GITHUB_USER_NAME='<your github username>'
export GITHUB_USER_PASSWORD='<your github personal access tocken>'
```

Update bash

```
source ~/.bash-profile
```

Reload IDE if necessary.