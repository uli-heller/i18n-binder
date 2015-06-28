I18N-BINDER
===========

This is a copy from [google code](https://code.google.com/p/i18n-binder/).
I'm not the author of this code.

* [README.googlecode2github.md](README.googlecode2github.md) ... describes the transfer process
* [LICENSE-2.0.html](LICENSE-2.0.html) ... the license of the code

Unfortunately, the junit tests do fail. I've fixed some issues
related to unix filenames, but currently I'm stuck with self-modifying code.


BUILD
-----

### Build Without Junit Tests

```
./gradlew clean build -x test
```

### Junit Tests

#### Mvn

```
mvn test
```

#### Gradle

```
./gradlew build
```

MVN
---

### First Time Setup

* Added mavenDeployer to build.gradle
* Do the uploads: `./gradlew uploadArchives`
* Create the branch mvn-repo:
    * `git checkout  --orphan mvn-repo`
    * `mv mvn-repo/* .`
    * `git reset --hard`
    * `git add org``
    * `git commit -m "Initial version`
    * `git push --set-upstream origin mvn-repo`
* Checkout master again: `git checkout master`
* Remove mvn-repo
* Checkout mvn-repo:
    * `sh /usr/share/doc/git/contrib/workdir/git-new-workdir . mvn-repo mvn-repo`
* Extend .gitignore:
    * `echo /mvn-repo >>.gitignore`

### Later On

```
git clone .../i18n-binder
cd i18n-binder
sh /usr/share/doc/git/contrib/workdir/git-new-workdir . mvn-repo mvn-repo
```

### Publishing

1. Change version within top-level build.gradle
2. Create the artifacts: `./gradlew uploadArchives`
3. Do some tests (junit tests don't work at the moment)
4. Commit and tag
    * `git commit -m "New version 0.1.17u2"`
    * `git tag v0.1.17u2`
    * `git push`
5. Commit and tag mvn repo
    * `cd mvn-repo`
    * `git add .`
    * `git commit -m "New version 0.1.17u2"`
    * `git push`

### Consuming
