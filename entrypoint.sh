#!/bin/sh -l

##
# GitHub Debug Function. Prints debug lines that are only visible if the repository secret "ACTIONS_STEP_DEBUG" is set
# to true within the repository running the action
##
gh_debug() {
  if printf "%s\n" "$#" | grep -q 0; then
    while read -r line; do
      echo "::debug::${line}"
    done
  else
    echo "::debug::${1}"
  fi
}

# Set the correct JAVA_HOME path for the container because this is overwritten by the setup-java action.
# We are using the openjdk:11-jdk-slim image at https://hub.docker.com/layers/openjdk/library/openjdk/11-jdk-slim/images
# /sha256-9fd52bf8b33635acf3aa708813ffbcf340fcfa97c9bf3ae9eff5ae81e490b8b7?context=explore
# It's JAVA_HOME is specified at the below path
JAVA_HOME="/usr/local/openjdk-11"

gh_debug "$(printf '%s' "RUNNING COMMAND: " \
  "$JAVA_HOME/bin/java -jar /app/check-kdoc-present-1.0-SNAPSHOT.jar \"$GITHUB_WORKSPACE/$INPUT_KOTLIN_DIR\"")"

$JAVA_HOME/bin/java -jar /app/check-kdoc-present-1.0-SNAPSHOT.jar "$GITHUB_WORKSPACE/$INPUT_KOTLIN_DIR"
