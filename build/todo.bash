#! /bin/bash

### BEGIN INIT INFO
# Provides:       b2b-todo
# Required-Start:
# Required-Stop:
# Default-Start:  2 3 4 5
# Default-Stop:   0 1 6
# Short-Description: b2b-todo service
### END INIT INFO


NAME="todo"
SERVICE_NAME="$NAME"
ARTIFACT="$NAME.war"
SERVER_ARTIFACT="server.jar"
PORT=8005

USER="b2b"
WORKDIR="/home/$USER/$NAME"

CONFIG_DIR=/usr/local/etc/$USER/$NAME/conf
CMD="java -cp $CONFIG_DIR:$WORKDIR/$SERVER_ARTIFACT io.framework.todo.server.JettyServer $PORT $WORKDIR/$ARTIFACT"
PIDFILE="/usr/local/var/run/$USER/$NAME.pid"
LOGFILE="/usr/local/var/log/$USER/$NAME.log"

recursiveKill() { # Recursively kill a process and all sub processes
  CPIDS=$(pgrep -P $1);
  for PID in $CPIDS
  do
    recursiveKill $PID
  done
  sleep 3 && kill -9 $1 2>/dev/null & # hard kill after 3 seconds
  kill $1 2>/dev/null # try soft kill first
}

case "$1" in
  start)
    echo "Starting $SERVICE_NAME ..."
    if [ -f "$PIDFILE" ]; then
      echo "Already running according to $PIDFILE"
      exit 1
    fi
    /bin/su -m -l $USER -c "$CMD" > $LOGFILE 2>&1 &
    PID=$!
    echo $PID > $PIDFILE
    echo "Started with pid $PID - Logging to $LOGFILE" && exit 0
    ;;
  stop)
    echo "Stopping $SERVICE_NAME ..."
    if [ ! -f $PIDFILE ]; then
      echo "Already stopped!"
      exit 1
    fi
    PID=`cat $PIDFILE`
    recursiveKill $PID
    rm -f $PIDFILE
    echo "Stopped $SERVICE_NAME" && exit 0
    ;;
  restart)
    $0 stop
    sleep 1
    $0 start
    ;;
  status)
    if [ -f "$PIDFILE" ]; then
      PID=`cat $PIDFILE`
      if [ "$(/bin/ps --no-headers -p $PID)" ]; then
        echo "$SERVICE_NAME is running (pid : $PID)" && exit 0
      else
        echo "Pid $PID found in $PIDFILE, but not running." && exit 1
      fi
    else
      echo "$SERVICE_NAME is NOT running" && exit 1
    fi
    ;;
  *)
    echo "Usage: /etc/init.d/$SERVICE_NAME {start|stop|restart|status}" && exit 1
    ;;
esac