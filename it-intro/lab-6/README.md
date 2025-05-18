# aaa

```bash
egor@myLaptop ~/t/i/l/dir (main)> echo lab-6\nlesson 5
lab-6
lesson 5
egor@myLaptop ~/t/i/l/dir (main)> grep "linux" hello
linux
egor@myLaptop ~/t/i/l/dir (main)> grep -r 'Hello' .
/nix/store/0mqngkz34kv5z6hz91bbbgzgrnz56c4y-gnugrep-3.11/bin/grep: ./file1.txt: Permission denied
./linux.txt:Hello
egor@myLaptop ~/t/i/l/dir (main) [2]> grep -i 'lINUX' hello
linux
egor@myLaptop ~/t/i/l/dir (main)> grep -n 'linux' hello
2:linux
egor@myLaptop ~/t/i/l/dir (main)> grep -v 'world' hello
hello
linux
egor@myLaptop ~/t/i/l/dir (main)> wc -L hello
5 hello
egor@myLaptop ~/t/i/l/dir (main)> echo -e "col1 col2 r1\ncol5 col6 r2\ncol3 col4 r3 " > new.txt
egor@myLaptop ~/t/i/l/dir (main)> echo -e "Hello\nlinux\nProgrammers paradise" > linux.txt
egor@myLaptop ~/t/i/l/dir (main)> cut -f1 -d' ' new.txt
col1
col5
col3
egor@myLaptop ~/t/i/l/dir (main)> paste hello new.txt
hello	col1 col2 r1
linux	col5 col6 r2
world	col3 col4 r3 
egor@myLaptop ~/t/i/l/dir (main)> paste -s hello new.txt
hello	linux	world
col1 col2 r1	col5 col6 r2	col3 col4 r3 
egor@myLaptop ~/t/i/l/dir (main)> sort new.txt
col1 col2 r1
col3 col4 r3 
col5 col6 r2
egor@myLaptop ~/t/i/l/dir (main)> diff hello linux.txt
1c1
< hello
---
> Hello
3c3
< world
---
> Programmers paradise
egor@myLaptop ~/t/i/l/dir (main) [1]> diff hello linux.txt
1c1
< hello
---
> Hello
3c3
< world
---
> Programmers paradise
egor@myLaptop ~/t/i/l/dir (main) [1]> diff3 hello new.txt linux.txt
====
1:1,3c
  hello
  linux
  world
2:1,3c
  col1 col2 r1
  col5 col6 r2
  col3 col4 r3 
3:1,3c
  Hello
  linux
  Programmers paradise
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 6
lesson 6
egor@myLaptop ~/t/i/l/dir (main)> dirname dir2/dir3/dir4/hi.txt
dir2/dir3/dir4
egor@myLaptop ~/t/i/l/dir (main)> basename dir2/dir3/dir4/hi.txt
hi.txt
egor@myLaptop ~/t/i/l/dir (main)> chmod -v 666 file1.txt
mode of 'file1.txt' changed from 0000 (---------) to 0666 (rw-rw-rw-)
egor@myLaptop ~/t/i/l/dir (main)> chmod a+rw file1.txt
egor@myLaptop ~/t/i/l/dir (main)> chmod a-rw file1.txt
egor@myLaptop ~/t/i/l/dir (main)> chmod -R 644 ~/chmod_dir
chmod: cannot access '/home/egor/chmod_dir': No such file or directory
egor@myLaptop ~/t/i/l/dir (main) [1]> echo "nixos :("
nixos :(
egor@myLaptop ~/t/i/l/dir (main)> chgrp root file1.txt
chgrp: changing group of 'file1.txt': Operation not permitted
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 7
lesson 7
egor@myLaptop ~/t/i/l/dir (main)> file linux.txt
linux.txt: ASCII text
egor@myLaptop ~/t/i/l/dir (main)> file /dev/null
/dev/null: character special (1/3)
egor@myLaptop ~/t/i/l/dir (main)> file -s /dev/sda2
/dev/sda2: cannot open `/dev/sda2` (No such file or directory)
egor@myLaptop ~/t/i/l/dir (main)> echo ":("
:(
egor@myLaptop ~/t/i/l/dir (main)> whereis ls
ls: /nix/store/440q5scq8paszj2sdgz98hxl1rz12i88-coreutils-9.5/bin/ls /nix/store/fy9yy17sa8fp57s1di1b84fy5njlhcwl-system-path/bin/ls
egor@myLaptop ~/t/i/l/dir (main)> whereis stdio.h
stdio.h:
egor@myLaptop ~/t/i/l/dir (main)> find ~ -name "linux.txt"
/home/egor/tpu-tasks/it-intro/lab-6/dir/linux.txt
egor@myLaptop ~/t/i/l/dir (main)> echo lesson 8
lesson 8
egor@myLaptop ~/t/i/l/dir (main)> uptime
 03:44:53  up   2:33,  0 users,  load average: 0.47, 0.37, 0.41
egor@myLaptop ~/t/i/l/dir (main)> date
Mon May 19 03:45:01 AM +07 2025
egor@myLaptop ~/t/i/l/dir (main)> who
egor@myLaptop ~/t/i/l/dir (main)> echo ":("
:(
egor@myLaptop ~/t/i/l/dir (main)> who -a
           system boot  2025-05-19 01:11
           pts/4        2025-05-19 01:20              6416 id=ts/4  term=0 exit=0
           pts/3        2025-05-19 02:25             42346 id=ts/3  term=0 exit=0
egor@myLaptop ~/t/i/l/dir (main)> w
 03:45:42 up  2:34,  2 users,  load average: 0.53, 0.40, 0.42
USER     TTY        LOGIN@   IDLE   JCPU   PCPU WHAT
egor     tty1      01:11    2:34m 12:35    ?    /nix/store/sx6bldj51cgk70rgpm06bx7kzp
egor               01:11    1:04   0.00s  4.97s /run/current-system/systemd/lib/syste
egor@myLaptop ~/t/i/l/dir (main)> mount -t ext4
/dev/nvme0n1p5 on / type ext4 (rw,relatime,stripe=32)
/dev/nvme0n1p5 on /nix/store type ext4 (ro,relatime,stripe=32)
egor@myLaptop ~/t/i/l/dir (main)> free -m
               total        used        free      shared  buff/cache   available
Mem:           15394        4128        5329         134        6406       11265
Swap:          24315           0       24315
egor@myLaptop ~/t/i/lab-6 (main)> cd ..
egor@myLaptop ~/t/i/lab-6 (main) [1]> sudo chmod -R a+rw dir/
egor@myLaptop ~/t/i/lab-6 (main)> cd dir
```
