
echo .....!!!!git checkout note master 
E: 
cd E:\mygitRes\zhao_Note
git pull origin master
git  add .
git commit  -m %date:~0,4%Y%date:~5,2%M%date:~8,2%d_note_synchronize
git push origin master
echo .....enter any key note to exit


echo .....!!!!git checkout note master 
E: 
cd E:\mygitRes\zhao_Mind\
git pull origin master
git  add .
git commit  -m %date:~0,4%Y%date:~5,2%M%date:~8,2%d_mind_synchronize
git push origin master
echo .....enter any key note to exit


echo .....!!!!git checkout project master 
E:
cd E:\mygitRes\zhao_project
git pull origin master
git  add .
git commit  -m %date:~0,4%Y%date:~5,2%M%date:~8,2%d_project_synchronize
git push origin master
echo .....enter any key to exit

pause>nul
