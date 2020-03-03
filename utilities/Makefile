# This file is used by Linux (and sometimes Apple) systems
# to automatically compile certain source files from the command line.

.PHONY: all
all: WallGeneratorApp.class

WallGeneratorApp.jar: WallGeneratorApp.class
	jar cf $@ *.class

WallGeneratorApp.class: WallGeneratorApp.java WallGeneratorView.class

WallGenerator.class: WallGenerator.java

WallGeneratorView.class: WallGeneratorView.java \
                         WallGenerator.class \
                         WallGeneratorPreviewGrid.class \
                         WallGeneratorSettings.class \
                         WallGeneratorSettingsObserver.class

WallGeneratorPreviewGrid.class: WallGeneratorPreviewGrid.java

WallGeneratorSettings.class: WallGeneratorSettings.java \
                             WallGeneratorSettingsObserver.class

%.class: %.java
	javac $<

.PHONY: clean
clean:
	$(RM) *.class *.jar