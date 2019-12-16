# Git装好后有个cmd目录，把这个路径加到Windows的path环境变量下，然后像平时写bat批处理文件一样，把git命令写到里边去就行了。不用再做bash的login操作
#echo .....Jump to folder E:\komi\komiTest（跳转至该路径，是一个git项目，应该会有一个.git文件夹。如果这个bat文件已经在git项目路径下了，可以不用跳转，直接写git命令，会对该git项目直接进行操作）
#cd /d E:\komi\komiTest

echo .....!!!!git checkout master （执行git命令：切换至master分支）
git pull origin master
git  add *
git commit  -m  'wiki_synchronization'
git push origin master
echo .....enter any key to exit
pause>nul
