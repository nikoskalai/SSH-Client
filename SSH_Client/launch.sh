JAVAFX_HOME="/usr/lib/jvm/javafx-sdk-15.0.1/lib"
JAR_FILE="SSH_Client-0.1-jar-with-dependencies.jar"
# java -version
if [[ -f "target/$JAR_FILE" ]]; then
	pushd target
	java --module-path ${JAVAFX_HOME} --add-modules=javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar $JAR_FILE
	popd
else
	echo "$JAR_FILE doesn't exist. Exiting..."
	exit 0;
fi