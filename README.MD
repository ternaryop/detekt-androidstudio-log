# Android Studio Log Report

Create a [detekt](https://github.com/arturbosch/detekt) report where file paths are clickable when the output is shown inside the Android Studio terminal window

From Android Studio open the terminal `View -> Tool Windows -> Terminal` then run

	cat android_studio_warnings.txt
	
Where `android_studio_warnings.txt` is the file created with detekt

Configure detekt
================

The configuration file must contain

	config:
	  validation: true
	  excludes: "ternaryop"

	ternaryop:
	  AndroidStudioLog:
	    # The extra informations shown
	    # The declaration order corresponds to the output order
	    show: "id,message"

Run detekt
==========

	java -jar detekt-cli-all.jar \
		-i <<the file/path to inspect>> \
		-r AndroidStudioLog:android_studio_warnings.txt \
		--plugins DetektAndroidStudioLog.jar \
		-c config.yml
