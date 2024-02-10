package org.Server.Service.ServerCallBacks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HeartBeatMechanism{
    private final Map<Integer, Long> lastHeartbeatTimes;
    private static final long HEARTBEAT_TIMEOUT = 15000;

    private static HeartBeatMechanism heartBeatMechanism;
    private HeartBeatMechanism(){
        lastHeartbeatTimes = new ConcurrentHashMap<>();
    }
    public static HeartBeatMechanism getInstance(){
        if(heartBeatMechanism==null){
            heartBeatMechanism = new HeartBeatMechanism();
        }
        return heartBeatMechanism;
    }

    public void refreshClientsHeartBeats(Integer clientId){
        System.out.println("HeartBeating ClientID : " + clientId);
        lastHeartbeatTimes.put(clientId, System.currentTimeMillis());
    }


    public Integer checkDisconnectedClients() {
        long currentTime = System.currentTimeMillis();
        for (Integer clientId : lastHeartbeatTimes.keySet()) {
            long lastHeartbeatTime = lastHeartbeatTimes.get(clientId);
            if (currentTime - lastHeartbeatTime > HEARTBEAT_TIMEOUT) {
                System.out.println("Force Stopped Client ID : " + clientId);
                lastHeartbeatTimes.remove(clientId);
                return clientId;
            }

        }
        return -1;
    }
}
