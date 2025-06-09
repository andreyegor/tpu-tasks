# Небольшое сообщение в будущее для себя, потому что намудрил. Да и эта штука забудется моментально.

* Этот репо используется в [tpu-tasks](https://github.com/andreyegor/tpu-tasks) как subtree.
* Предварительно его нужно подключить `git remote add java-course https://codelab.tpu.ru/andreevegor/java-course.git` -> `git fetch java-course`
* Проверить что всё ок до и после `git branch -r` там будет `java-course/HEAD -> java-course/main \n java-course/main`
* С ним можно работать отдельно как обычно и из tpu-tasks переодически делать `git subtree pull --prefix=java-course java-course main`, он подтянет всю историю, **squash мы тут не любим**. Не main ветки наверное тянуть не стоит.
* Также можно напрямую  `git subtree push --prefix=java-course java-course main` но как будто не очень удобно. 
* Вот и всё