# Лабораторная работа 5

```bash
egor@myLaptop ~/t/i/l/dir (main)> echo lab-5\nlesson 1
lab-5
lesson 1
egor@myLaptop ~/t/i/l/dir (main)> pwd
/home/egor/tpu-tasks/it-intro/lab-5/dir
egor@myLaptop ~/t/i/l/dir (main)> mkdir -v dir1
mkdir: created directory 'dir1'
egor@myLaptop ~/t/i/l/dir (main)> mkdir -vp dir2/dir3/dir4
mkdir: created directory 'dir2'
mkdir: created directory 'dir2/dir3'
mkdir: created directory 'dir2/dir3/dir4'
egor@myLaptop ~/t/i/l/dir (main)> ls
dir1/  dir2/
egor@myLaptop ~/t/i/l/dir (main)> ls -R
.:
dir1/  dir2/

./dir1:

./dir2:
dir3/

./dir2/dir3:
dir4/

./dir2/dir3/dir4:
egor@myLaptop ~/t/i/l/dir (main)> cd dir2
egor@myLaptop ~/t/i/l/d/dir2 (main)> cd dir3
egor@myLaptop ~/t/i/l/d/d/dir3 (main)> cd ..
egor@myLaptop ~/t/i/l/d/dir2 (main)> cd -
egor@myLaptop ~/t/i/l/d/d/dir3 (main)> cd
egor@myLaptop ~> cd tpu-tasks/it-intro/lab-5/dir/
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 2
lesson 2
egor@myLaptop ~/t/i/l/dir (main)> touch file1.txt
egor@myLaptop ~/t/i/l/dir (main)> touch file2.txt
egor@myLaptop ~/t/i/l/dir (main)> dir
dir1  dir2  file1.txt  file2.txt
egor@myLaptop ~/t/i/l/dir (main)> clear
egor@myLaptop ~/t/i/l/dir (main)> echo "hello"
hello
egor@myLaptop ~/t/i/l/dir (main)> echo "hello" > hello.txt
egor@myLaptop ~/t/i/l/dir (main)> echo "linux" >> hello.txt 
egor@myLaptop ~/t/i/l/dir (main)> echo "world" >> hello.txt
egor@myLaptop ~/t/i/l/dir (main)> cat hello.txt
hello
linux
world
egor@myLaptop ~/t/i/l/dir (main)> head -2 hello.txt
hello
linux
egor@myLaptop ~/t/i/l/dir (main)> head hello.txt
hello
linux
world
egor@myLaptop ~/t/i/l/dir (main)> tail -2 hello.txt
linux
world
egor@myLaptop ~/t/i/l/dir (main)> tail hello.txt
hello
linux
world
egor@myLaptop ~/t/i/l/dir (main)> stat hello.txt
  File: hello.txt
  Size: 18        	Blocks: 8          IO Block: 4096   regular file
Device: 259,5	Inode: 8025709     Links: 1
Access: (0644/-rw-r--r--)  Uid: ( 1000/    egor)   Gid: (  100/   users)
Access: 2025-05-19 02:56:18.162558633 +0700
Modify: 2025-05-19 02:56:10.405470300 +0700
Change: 2025-05-19 02:56:10.405470300 +0700
 Birth: 2025-05-19 02:55:49.935235393 +0700
egor@myLaptop ~/t/i/l/dir (main)> stat dir1
  File: dir1
  Size: 4096      	Blocks: 8          IO Block: 4096   directory
Device: 259,5	Inode: 8014837     Links: 2
Access: (0755/drwxr-xr-x)  Uid: ( 1000/    egor)   Gid: (  100/   users)
Access: 2025-05-19 02:49:05.070318322 +0700
Modify: 2025-05-19 02:48:44.380039794 +0700
Change: 2025-05-19 02:48:44.380039794 +0700
 Birth: 2025-05-19 02:48:44.380039794 +0700
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 3
lesson 3
egor@myLaptop ~/t/i/l/dir (main)> du
4	./dir2/dir3/dir4
8	./dir2/dir3
12	./dir2
4	./dir1
28	.
egor@myLaptop ~/t/i/l/dir (main)> echo du -xh ~
du -xh ~
egor@myLaptop ~/t/i/l/dir (main)> echo du --max-depth 3 ~
du --max-depth 3 ~
egor@myLaptop ~/t/i/l/dir (main)> cp -v hello.txt dir2
'hello.txt' -> 'dir2/hello.txt'
egor@myLaptop ~/t/i/l/dir (main)> cp -v hello.txt dir2/file2.txt
'hello.txt' -> 'dir2/file2.txt'
egor@myLaptop ~/t/i/l/dir (main)> cp  -vr dir2/*.txt dir2/dir3
'dir2/file2.txt' -> 'dir2/dir3/file2.txt'
'dir2/hello.txt' -> 'dir2/dir3/hello.txt'
egor@myLaptop ~/t/i/l/dir (main)> cp -vr dir2/dir3  .
'dir2/dir3' -> './dir3'
'dir2/dir3/dir4' -> './dir3/dir4'
'dir2/dir3/file2.txt' -> './dir3/file2.txt'
'dir2/dir3/hello.txt' -> './dir3/hello.txt'
egor@myLaptop ~/t/i/l/dir (main)> ls
dir1/  dir2/  dir3/  file1.txt  file2.txt  hello.txt
egor@myLaptop ~/t/i/l/dir (main)> md5sum hello.txt
b8d5079c5d6a9dbb3294b31d318d74c0  hello.txt
egor@myLaptop ~/t/i/l/dir (main)> md5sum dir2/hello.txt
b8d5079c5d6a9dbb3294b31d318d74c0  dir2/hello.txt
egor@myLaptop ~/t/i/l/dir (main)> mv hello.txt dir2/dir3/dir4/hi.txt
egor@myLaptop ~/t/i/l/dir (main)> mkdir dir5
egor@myLaptop ~/t/i/l/dir (main)> mv dir2/*.txt dir5
egor@myLaptop ~/t/i/l/dir (main)> mv dir5  dir50
egor@myLaptop ~/t/i/l/dir (main)> ln  dir2/dir3/dir4/hi.txt hello
egor@myLaptop ~/t/i/l/dir (main)> stat hello
  File: hello
  Size: 18        	Blocks: 8          IO Block: 4096   regular file
Device: 259,5	Inode: 8025709     Links: 2
Access: (0644/-rw-r--r--)  Uid: ( 1000/    egor)   Gid: (  100/   users)
Access: 2025-05-19 02:56:18.162558633 +0700
Modify: 2025-05-19 02:56:10.405470300 +0700
Change: 2025-05-19 03:04:47.433480208 +0700
 Birth: 2025-05-19 02:55:49.935235393 +0700
egor@myLaptop ~/t/i/l/dir (main)> stat dir2/dir3/dir4/hi.txt
  File: dir2/dir3/dir4/hi.txt
  Size: 18        	Blocks: 8          IO Block: 4096   regular file
Device: 259,5	Inode: 8025709     Links: 2
Access: (0644/-rw-r--r--)  Uid: ( 1000/    egor)   Gid: (  100/   users)
Access: 2025-05-19 02:56:18.162558633 +0700
Modify: 2025-05-19 02:56:10.405470300 +0700
Change: 2025-05-19 03:04:47.433480208 +0700
 Birth: 2025-05-19 02:55:49.935235393 +0700
egor@myLaptop ~/t/i/l/dir (main)> ln -s  dir2/dir3/dir4/hi.txt  softlink
egor@myLaptop ~/t/i/l/dir (main)> stat softlink
  File: softlink -> dir2/dir3/dir4/hi.txt
  Size: 21        	Blocks: 0          IO Block: 4096   symbolic link
Device: 259,5	Inode: 8022574     Links: 1
Access: (0777/lrwxrwxrwx)  Uid: ( 1000/    egor)   Gid: (  100/   users)
Access: 2025-05-19 03:05:40.419937616 +0700
Modify: 2025-05-19 03:05:40.340936936 +0700
Change: 2025-05-19 03:05:40.340936936 +0700
 Birth: 2025-05-19 03:05:40.340936936 +0700
egor@myLaptop ~/t/i/l/dir (main)> rm -i file2.txt
rm: remove regular empty file 'file2.txt'? y
egor@myLaptop ~/t/i/l/dir (main)> rm -ri dir50/*
rm: remove regular file 'dir50/file2.txt'? y
rm: remove regular file 'dir50/hello.txt'? y
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 4
lesson 4
egor@myLaptop ~/t/i/l/dir (main)> ps
    PID TTY          TIME CMD
  44236 pts/0    00:00:01 fish
  49616 pts/0    00:00:00 ps
egor@myLaptop ~/t/i/l/dir (main)> sleep 60 & ps
    PID TTY          TIME CMD
  44236 pts/0    00:00:01 fish
  49737 pts/0    00:00:00 sleep
  49738 pts/0    00:00:00 ps
egor@myLaptop ~/t/i/l/dir (main)> kill 49737
egor@myLaptop ~/t/i/l/dir (main)> 
fish: Job 1, 'sleep 60 &' terminated by signal SIGTERM (Polite quit request)
egor@myLaptop ~/t/i/l/dir (main)> sleep 30 & sleep 30 & ps
    PID TTY          TIME CMD
  44236 pts/0    00:00:01 fish
  49832 pts/0    00:00:00 sleep
  49833 pts/0    00:00:00 sleep
  49834 pts/0    00:00:00 ps
egor@myLaptop ~/t/i/l/dir (main)>
fish: Job 2, 'sleep 30 &' has ended
egor@myLaptop ~/t/i/l/dir (main)>
fish: Job 1, 'sleep 30 &' has ended
egor@myLaptop ~/t/i/l/dir (main)> killall sleep
The program 'killall' is not in your PATH. It is provided by several packages.
You can make it available in an ephemeral shell by typing one of the following:
  nix-shell -p busybox
  nix-shell -p killall
  nix-shell -p psmisc
  nix-shell -p toybox
egor@myLaptop ~/t/i/l/dir (main)> echo ":("
:(
egor@myLaptop ~/t/i/l/dir (main)> sleep 30 & sleep 30 & ps
    PID TTY          TIME CMD
  44236 pts/0    00:00:02 fish
  49944 pts/0    00:00:00 sleep 
egor@myLaptop ~/t/i/l/dir (main)> echo top
top
egor@myLaptop ~/t/i/l/dir (main)> echo pstree
pstree
egor@myLaptop ~/t/i/l/dir (main)> time ls -l
total 20
drwxr-xr-x 2 egor users 4096 May 19 02:48 dir1/
drwxr-xr-x 3 egor users 4096 May 19 03:04 dir2/
drwxr-xr-x 3 egor users 4096 May 19 03:02 dir3/
drwxr-xr-x 2 egor users 4096 May 19 03:06 dir50/
-rw-r--r-- 1 egor users    0 May 19 02:50 file1.txt
-rw-r--r-- 2 egor users   18 May 19 02:56 hello
lrwxrwxrwx 1 egor users   21 May 19 03:05 softlink -> dir2/dir3/dir4/hi.txt

________________________________________________________
Executed in    4.17 millis    fish           external
   usr time    1.09 millis  319.00 micros    0.77 millis
   sys time    1.86 millis  315.00 micros    1.54 millis

egor@myLaptop ~/t/i/l/dir (main)> 
```
