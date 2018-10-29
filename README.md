# GitHubApi

# https://github.com/anggelo17/GitHubApi

Luis A. Ibarra

This project uses the Github api to show all the open pull request from googlesamples/android-architecture repo. If you select any pull request from the list you will see  a diff for the selected pull request. This is presented using two fragments side by side, the way GitHub display diffs. 

To handle the network calls, I have used retrofit + rxjava2. 
To highlight the diffs I have implemented a small method called processString  that keeps track of what has been deleted or added. 
