#!/bin/bash
if [ $# = 0 ];
then
    echo "please input port number.for example 2012";
    exit
fi
base_url="http://192.168.50.177:8080/";
jsvc_name="jsvc.zip";
jsvc_dir="jsvc";
jsvc_url=$base_url$jsvc_name;
jar_name="DispatchSystemDaemon.jar";
daemon_url=$base_url$jar_name;
current_path=`pwd`;
daemon_jar_path=$current_path"/"$jar_name;
if [ ! -e $current_path"/"$jsvc_name && ! -d $current_path"/"$jsvc_dir ];
then
    wget $jsvc_url;
    unzip $jsvc_name;
    jsvc_dir=$current_path"/"$jsvc_dir;
    cd $jsvc_dir;
    ./configure;
    make;
    cd $current_path;
fi;
if [ ! -e $daemon_jar_path ];
then
    wget $daemon_url;
fi;
$jsvc_dir"/jsvc -cp $daemon_jar_path com.baofeng.dispatchexecutor.boot.DaemonBoot -p $1"

