latestVersion=2
docker tag zalando_price_tracker:latest adelsadeq24/zalando_pt:${latestVersion}
docker push adelsadeq24/zalando_pt:${latestVersion}
((latestVersion++))