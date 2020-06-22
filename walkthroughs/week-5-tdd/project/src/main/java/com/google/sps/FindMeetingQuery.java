// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        Set<String> desiredAttendees = new HashSet<>(request.getAttendees());
        Set<String> optionalAttendees = new HashSet<>(request.getOptionalAttendees());
        int desiredDuration = (int)request.getDuration();
        HashSet<TimeRange> timesTaken = new HashSet<>();
        HashSet<TimeRange> optionalTimesTaken = new HashSet<>();
        for(Event event: events){
            Set<String> eventAttendees = event.getAttendees();
            if(!Collections.disjoint(desiredAttendees, eventAttendees)){
                timesTaken.add(event.getWhen());
            }
            if(!Collections.disjoint(optionalAttendees, eventAttendees)){
                optionalTimesTaken.add(event.getWhen());
            }
        }
        ArrayList<TimeRange> possibleTimes = findAvaliableTimes(timesTaken,desiredDuration);
        ArrayList<TimeRange> possibleTimesOptional = findAvaliableTimes(optionalTimesTaken,desiredDuration);
        if(desiredAttendees.size() == 0){
            return possibleTimesOptional;
        }
        else if(optionalAttendees.size() == 0){
            return possibleTimes;
        }
        //We account for optional attendees by adding the smaller of any overlaps
        ArrayList<TimeRange> overlapingTimes = new ArrayList<>();
        for(TimeRange optionalTime: possibleTimesOptional){
            for(TimeRange mandatoryTime: possibleTimes){
                if(optionalTime.contains(mandatoryTime)){
                    overlapingTimes.add(mandatoryTime);
                    //if the optional time is broader than the mandatory we add the mandatory
                }
                else if(mandatoryTime.contains(optionalTime)){
                    overlapingTimes.add(optionalTime);
                    //if the mandatory time is broader than the optional time we add the optional time
                }
            }
        }
        if(overlapingTimes.size() > 0){
            return overlapingTimes;
        }
        return possibleTimes;
    }

    //This basic avaliable times algorithm is nLogn. The basic idea is that I run through a sorted
    //list of the events and build the avaliable times around those. 
    private ArrayList<TimeRange> findAvaliableTimes(Set<TimeRange> timesTaken, int desiredDuration){
        ArrayList<TimeRange> possibleTimes = new ArrayList<>();
        ArrayList<TimeRange> byStart = new ArrayList<>(timesTaken);
        Collections.sort(byStart,TimeRange.ORDER_BY_START);
        int start = TimeRange.START_OF_DAY;
        while(byStart.size() > 0){
            TimeRange nextMeeting = byStart.get(0);
            if(start < nextMeeting.start() && nextMeeting.start() - start >= desiredDuration){
                TimeRange time = TimeRange.fromStartEnd(start,nextMeeting.start(),false);
                possibleTimes.add(time);
            }
            start = nextMeeting.end();
            byStart.remove(0);
            //Here we are accounting for overlapping meetings
            while(byStart.size() > 0 && byStart.get(0).start() < start ){
                if(byStart.get(0).end() > start){
                    start = byStart.get(0).end();
                }
                byStart.remove(0);
            }
        }

        //This accounts for the time period between the last event and the end of the day
        TimeRange potentialTime = TimeRange.fromStartEnd(start,TimeRange.END_OF_DAY,true);
        if(!overlap(potentialTime,timesTaken) && potentialTime.duration() > desiredDuration){
            possibleTimes.add(potentialTime);
        }
        return possibleTimes;
    }

    private boolean overlap(TimeRange time, Set<TimeRange> timeSet){
        for(TimeRange t : timeSet){
            if(t.overlaps(time)){
                return true;
            }
        }
        return false;
    }
}
