I18N-BINDER
===========

I copied this from [google code](https://code.google.com/p/i18n-binder/) to [github]https://github.com/uli-heller/i18n-binder). Here is the procedure:

``` sh
svn checkout http://i18n-binder.googlecode.com/svn/trunk/ i18n-binder-read-only
cd i18n-binder-read-only/
echo ".svn" >.gitignore
git init
git add .
git commit -m "First github commit"
git remote add origin git@github.com:uli-heller/i18n-binder.git
git push -u origin master
# Create this tile
git add README.googlecode2github.md
git commit -m "Added description of the transfer from google code to github"
git push
```
