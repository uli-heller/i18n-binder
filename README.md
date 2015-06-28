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

### Mvn

```
mvn test
```

### Gradle

```
./gradlew build
```

