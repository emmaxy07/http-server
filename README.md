<img src="https://cdn.prod.website-files.com/677c400686e724409a5a7409/6790ad949cf622dc8dcd9fe4_nextwork-logo-leather.svg" alt="NextWork" width="300" />

# Connect a GitHub Repo with AWS

**Project Link:** [View Project](http://learn.nextwork.org/projects/aws-devops-github)

**Author:** Emmanuel Iyare  
**Email:** emmanueliyare7@gmail.com

---

## Connect a GitHub Repo with AWS

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_dd9d254e)

---

## Introducing Today's Project!

In this project, I will demonstrate how to connect a Github repository to AWS. I'm doing this project to learn the fundamentals of CI/CD on AWS, to further enhance my DevOps knowledge.

### Key tools and concepts

Services I used were:
-AWS EC2 Instance
-AWS EC2 Volume
-AWS Key-Pair
-Public IPv4 / Public DNS
-SSH
 Key concepts I learnt include:
-how to securely login remotely into a server from any local computer
-ssh vs scp syntax and how they function
-Java/Maven version alignment
-Git authentication changes
-EC2 networking for SSH

### Project reflection

This project took me approximately four hours. The most challenging part was my ec2 ssh server lagging repeatedly on my local computer. It was most rewarding to finally see the full setup run smoothly.

I did this project because I wanted to continue to learn and implement some Devops fundamentals.

This project is part two of a series of DevOps projects where I'm building a CI/CD pipeline! I'll be working on the next project tomorrow!

---

## Git and GitHub

Git is often called a version control system since it tracks your changes by taking snapshots of what your files look like at specific moments, and each snapshot is considered a 'version'.  I installed Git using the commands:
sudo dnf update -y
sudo dnf install git -y

GitHub is a cloud service, it also lets you access your work from anywehere and collaborate with other developers over the internet. I'm using GitHub in this project to store my code and then run Git commands to track code changes.

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_efaadbf7)

---

## My local repository

A Git repository is a central store for a codebase.

Git init is a command that initializes Git in the code on the local computer. I ran git init in the terminal of my local computer.

A branch in Git is a local version of the code for a contributor to a codebase. After running git init, the response from the terminal was "initialized existing Git repository in /home/ec2-user/simplehttpserver/.git/"

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_7bf21bae)

---

## To push local changes to GitHub, I ran three commands

### git add

The first command I ran was "git add ." A staging area is where I tell Git to put together all my modified files for a final review before I commit them.

### git commit

The second command I ran was git commit -m "..." Using '-m' means writing a commit message.

### git push

The third command I ran was git push -u origin master Using '-u' means setting an 'upstream' for my local branch, which means telling Git to remember to push to master by default.

---

## Authentication

When I commit changes to GitHub, Git asks for my credentials because it needs to double check that I have the right to push any changes to the remote origin my local repo is connected with.

### Local Git identity

Git needs my name and email because it needs author information for commits to track who made what change. If I don't set it manually, Git uses the system's default username, which might not accurately represent my identity in my project's version history.

Running git log showed me that I have made some changes to my code over time.

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_9a27ee3b)

---

## GitHub tokens

GitHub authentication failed when I entered my password because Github phased out password authentication to connect with repositories over HTTPS. There are too many security risks and passwords can get intercepted over the internet.

A GitHub token is a set of characters used to  I'm using one in this project because access my GitHub repo from my local repo.

I could set up a GitHub token by finding the Generate new token (classic) option in my GitHub settings.

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_fa11169d)

---

## Making changes again

I wanted to see Git working in action, so I ran different git commands like push to push my doe to the master branch.

I finally saw the changes in my GitHub repo after running the command "git push origin master"

![Image](http://learn.nextwork.org/refreshed_maroon_agile_abiu/uploads/aws-devops-github_6becb2bc)

---

## Setting up a READMe file

---

---
