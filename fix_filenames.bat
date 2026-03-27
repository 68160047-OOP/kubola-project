@echo off
cd /d "C:\Users\admin\eclipse-workspace\World Chef Saga\resources\assets"
echo Fixing double-extension filenames...
for %%f in (*.png.png) do (
    set "old=%%f"
    set "new=%%~nf"
    echo Renaming: %%f  ->  %%~nf
    ren "%%f" "%%~nf"
)
echo Done.
pause
