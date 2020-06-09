## The Definitive Guide to migrating development to Compute Engine

Prerequisites: comfort with linux and a text based editor such as nano, vim, or emacs. You also need to know what your cloud project is called. 

### Creating and Connecting to the VM
1. Login to Compute engine and create an instance
	- The settings don’t really matter. Just choose a region/zone that is closest to you. Make sure that you are creating this instance under your cloud project. 
2. Prepare your linux terminal
    1. If you haven’t already, install the linux terminal on your chromebook
  	1. Run the following command: `sudo apt-get gcloud`
	1. Now run this command: `gcloud init`
3. If it isn't already running, start your instance.
4. Click the arrow next to SSH. There should be an option called view gcloud command. Copy this command and run it in your terminal. You should have to do a little bit of setup but after that you should be logged in.
	  * If you were unable to connect due to a connection time out run this command: `gcloud compute firewall-rules create allow-ssh --allow tcp:22`
    
### Setting up your VM
run `apt-get install -yq openjdk-11-jdk git maven`

This *should* be everything you need to get started. If you want to be able to preview your maven project in your browser (which you probably do) you will need to append `--ssh-flag"-L 8080:localhost:8080"` to the `gcloud compute ssh ... ` command you used to connect to your instance before. 

Finally, I recommend using tmux. Basically, it allows you to create sessions that you can connect and disconect to to save your work. It also allows you to split your terminal window into various panes as well as create multiple terminal windows. If this sounds intresting to you run `sudo apt-get tmux`

Below are some cheatsheets you might find useful:

https://tmuxcheatsheet.com/

https://vim.rtorr.com/
