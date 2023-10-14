#!/bin/sh

set -e
readonly PUBSUB_HOST_PORT='http://minor-insights-service-pubsub:8085'
readonly PUBSUB_URL_PREFIX="$PUBSUB_HOST_PORT/v1/projects/is-minor-insights-service-dev"

main() {
  echo 'Waiting on the PubSub service to come online'

  until curl -s $PUBSUB_HOST_PORT > /dev/null; do
    sleep 1
  done

  echo 'PubSub service is now ready.  Setting up topics and subscriptions'

  create_topic    v1.application

  create_subscription     v1.application              minor-insights-service-subscription

  echo 'Topics and subscriptions are now ready'
}

create_topic() {
  topic_name=$1

  echo "Creating topic $topic_name"

  curl -s -S --fail -o /dev/null --location --request \
    PUT "$PUBSUB_URL_PREFIX/topics/$topic_name"
}

create_subscription() {
  topic_name=$1
  subscription_name=$2

  echo "Creating subscriber $subscription_name on $topic_name"

  curl -s -S --fail -o /dev/null --location --request \
    PUT "$PUBSUB_URL_PREFIX/subscriptions/$subscription_name" \
    --header 'Content-Type: application/json' \
    --data-raw "{
        'topic': 'projects/is-minor-insights-service-dev/topics/$topic_name'
    }"
}

main "$@"
