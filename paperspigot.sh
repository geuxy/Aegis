#!/usr/bin/bash

DOWNLOAD_URL='https://fill-data.papermc.io/v1/objects/7ff6d2cec671ef0d95b3723b5c92890118fb882d73b7f8fa0a2cd31d97c55f86/paper-1.8.8-445.jar'
SERVER_ROOT='server'
JAR_NAME='paperspigot'
MAX_MEMORY=4096; # Megabytes

if [ ! -d $SERVER_ROOT ]; then
  mkdir $SERVER_ROOT
fi

if [ ! -f $SERVER_ROOT/$JAR_NAME.jar ]; then
  curl $DOWNLOAD_URL --output $SERVER_ROOT/$JAR_NAME.jar
fi

cd $SERVER_ROOT || (printf "Failed to change working directory, does it exist? do i have permissions to access or create the dir?" && exit)
java -jar -XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:+ParallelRefProcEnabled -XX:+PerfDisableSharedMem -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1HeapRegionSize=8M -XX:G1HeapWastePercent=5 -XX:G1MaxNewSizePercent=40 -XX:G1MixedGCCountTarget=4 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1NewSizePercent=30 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=15 -XX:MaxGCPauseMillis=200 -XX:MaxTenuringThreshold=1 -XX:SurvivorRatio=32 -Xmx${MAX_MEMORY}M ${JAR_NAME}.jar