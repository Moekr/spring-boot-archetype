#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}!/usr/bin/env bash

${symbol_pound} Application root directory.
path=/opt/${artifactId}
${symbol_pound} Application name.
name=${artifactId}
${symbol_pound} Application version.
version=${version}

${symbol_pound} Application config file. Default config.yml
config_file=${symbol_dollar}{path}/config.yml
${symbol_pound} File which store application process PID. Default proc.pid
pid_file=${symbol_dollar}{path}/proc.pid
${symbol_pound} Directory which store application log. Default ${symbol_dollar}{path}/log
log_dir=${symbol_dollar}{path}/log
${symbol_pound} Name template of log files (Parsed with shell command "date"). Default %Y-%m-%d_%H-%M-%S.log
log_name=%Y-%m-%d_%H-%M-%S.log
${symbol_pound} Time in second how long "stop" and "force-stop" wait for application totally stop. Default 60
wait_count=60

function parseConfig {
   local s='[[:space:]]*' w='[a-zA-Z0-9_]*' fs=${symbol_dollar}(echo @|tr @ '${symbol_escape}034')
   sed -ne "s|^${symbol_escape}(${symbol_dollar}s${symbol_escape}):|${symbol_escape}1|" ${symbol_escape}
        -e "s|^${symbol_escape}(${symbol_dollar}s${symbol_escape})${symbol_escape}(${symbol_dollar}w${symbol_escape})${symbol_dollar}s:${symbol_dollar}s[${symbol_escape}"']${symbol_escape}(.*${symbol_escape})[${symbol_escape}"']${symbol_dollar}s${symbol_escape}${symbol_dollar}|${symbol_escape}1${symbol_dollar}fs${symbol_escape}2${symbol_dollar}fs${symbol_escape}3|p" ${symbol_escape}
        -e "s|^${symbol_escape}(${symbol_dollar}s${symbol_escape})${symbol_escape}(${symbol_dollar}w${symbol_escape})${symbol_dollar}s:${symbol_dollar}s${symbol_escape}(.*${symbol_escape})${symbol_dollar}s${symbol_escape}${symbol_dollar}|${symbol_escape}1${symbol_dollar}fs${symbol_escape}2${symbol_dollar}fs${symbol_escape}3|p"  ${symbol_dollar}1 |
   awk -F${symbol_dollar}{fs} '{
      indent = length(${symbol_dollar}1)/2;
      name[indent] = ${symbol_dollar}2;
      for (i in name) {if (i > indent) {delete name[i]}}
      if (length(${symbol_dollar}3) > 0) {
         vn=""; for (i=0; i<indent; i++) {vn=(vn)(name[i])("_")}
         printf("local %s%s=${symbol_escape}"%s${symbol_escape}"${symbol_escape}n", vn, ${symbol_dollar}2, ${symbol_dollar}3);
      }
   }'
}

function start {
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        local pid=${symbol_dollar}(cat ${symbol_dollar}{pid_file})
        if ps -p ${symbol_dollar}{pid} > /dev/null
        then
            echo "Application ${symbol_dollar}{name} is already running! (Version:${symbol_dollar}{version} PID:${symbol_dollar}{pid})"
        else
            rm -rf ${symbol_dollar}{pid_file}
        fi
    fi
    if [ ! -f ${symbol_dollar}{pid_file} ]
    then
        if [ ! -d ${symbol_dollar}{log_dir} ]
        then
            mkdirs ${symbol_dollar}{log_dir}
        fi
        java -server -jar ${symbol_dollar}{path}/${symbol_dollar}{name}-${symbol_dollar}{version}.jar --spring.config.location=${symbol_dollar}{config_file} 2>&1 >${symbol_dollar}{log_dir}/${symbol_dollar}(date +${symbol_dollar}{log_name}) &
        local pid=${symbol_dollar}(echo -e "${symbol_dollar}!${symbol_escape}c")
        echo -e "${symbol_dollar}{pid}${symbol_escape}c" > ${symbol_dollar}{pid_file}
        echo "Start application ${symbol_dollar}{name} successfully! (Version:${symbol_dollar}{version} PID:${symbol_dollar}{pid})"
    fi
}

function stop {
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        if ! ps -p ${symbol_dollar}(cat ${symbol_dollar}{pid_file}) > /dev/null
        then
            rm -rf ${symbol_dollar}{pid_file}
        fi
    fi
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        eval ${symbol_dollar}(parseConfig ${symbol_dollar}{config_file})
        local authorization=${symbol_dollar}(echo -e "${symbol_dollar}{security_user_name}:${symbol_dollar}{security_user_password}${symbol_escape}c" | base64)
        curl -o /dev/null -l -s -H "Content-type: application/json" -H "Authorization: Basic ${symbol_dollar}{authorization}" -X POST http://localhost:${symbol_dollar}{server_port}/api/shutdown
        local pid=${symbol_dollar}(cat ${symbol_dollar}{pid_file})
        local count=${symbol_dollar}{wait_count}
        while((${symbol_dollar}{count} > 0))
        do
            sleep 1
            if ! ps -p ${symbol_dollar}{pid} > /dev/null
            then
                rm -rf ${symbol_dollar}{pid_file}
                echo "Stop application ${symbol_dollar}{name} successfully! (Version:${symbol_dollar}{version})"
                break
            fi
            count=`expr ${symbol_dollar}{count} - 1`
        done
        if [ ${symbol_dollar}{count} -eq 0 ]
        then
            echo "Failed to stop application ${symbol_dollar}{name}! (Version:${symbol_dollar}{version} PID:${symbol_dollar}{pid})"
            exit 1
        fi
    else
        echo "Application ${symbol_dollar}{name} is not running! (Version:${symbol_dollar}{version})"
    fi
}

function restart {
    stop
    start
}

function forceStop {
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        if ! ps -p ${symbol_dollar}(cat ${symbol_dollar}{pid_file}) > /dev/null
        then
            rm -rf ${symbol_dollar}{pid_file}
        fi
    fi
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        local pid=${symbol_dollar}(cat ${symbol_dollar}{pid_file})
        kill ${symbol_dollar}{pid}
        local count=${symbol_dollar}{wait_count}
        while((${symbol_dollar}{count} > 0))
        do
            sleep 1
            if ! ps -p ${symbol_dollar}{pid} > /dev/null
            then
                rm -rf ${symbol_dollar}{pid_file}
                echo "Stop application ${symbol_dollar}{name} successfully! (Version:${symbol_dollar}{version})"
                break
            fi
            count=`expr ${symbol_dollar}{count} - 1`
        done
        if [ ${symbol_dollar}{count} -eq 0 ]
        then
            echo "Failed to stop application ${symbol_dollar}{name}! (Version:${symbol_dollar}{version} PID:${symbol_dollar}{pid})"
            exit 1
        fi
    else
        echo "Application ${symbol_dollar}{name} is not running! (Version:${symbol_dollar}{version})"
    fi
}

function forceRestart {
    forceStop
    start
}

function status {
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        if ! ps -p ${symbol_dollar}(cat ${symbol_dollar}{pid_file}) > /dev/null
        then
            rm -rf ${symbol_dollar}{pid_file}
        fi
    fi
    if [ -f ${symbol_dollar}{pid_file} ]
    then
        local pid=${symbol_dollar}(cat ${symbol_dollar}{pid_file})
        echo "Application ${symbol_dollar}{name} is running! (Version:${symbol_dollar}{version} PID:${symbol_dollar}{pid})"
    else
        echo "Application ${symbol_dollar}{name} is not running! (Version:${symbol_dollar}{version})"
    fi
}

function usage {
    echo "Usage: ${symbol_dollar}0 {start|stop|restart|force-stop|force-restart|status}"
}

pushd ${symbol_dollar}{path} > /dev/null

case ${symbol_dollar}1 in
start)
    start
;;
stop)
    stop
;;
restart)
    restart
;;
force-stop)
    forceStop
;;
force-restart)
    forceRestart
;;
status)
    status
;;
*)
    usage
;;
esac

popd > /dev/null

exit 0