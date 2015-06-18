# ScaleProgressBar
a customer progressbar for loading effect.
  
![image](https://github.com/arjinmc/ScaleProgressBar/blob/master/effect.gif)  
 

You can use ScaleProgressBar(will not stuck in ui thread) like this  
1.in xml 
``` java
    <com.arjinmc.widgets.ScaleProgressBar 
        android:id="@+id/spb"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
``` 
2. in java
``` java 
	ScaleProgressBar spBar = (ScaleProgressBar) findViewById(R.id.spb);
	spBar.setProgress(20);
``` 

Or you can use ScaleProgressDilog(will stuck in ui thread) like this
``` java
  ScaleProgressDialog spDialog = new ScaleProgressDialog(this);
  spDialog.show();
  spDialog.setProgress(20);
``` 
