package data

import dto.TalkData

import static org.apache.commons.collections.ListUtils.union

class SpecificationDatasets {

    public static SPECIFICATION_FIRST_TRACK_TALKS = [
            new TalkData("Writing Fast Tests Against Enterprise Rails", 60),
            new TalkData("Overdoing it in Python", 45),
            new TalkData("Lua for the Masses", 30),
            new TalkData("Ruby Errors from Mismatched Gem Versions", 45),
            new TalkData("Ruby on Rails: Why We Should Move On", 60),
            new TalkData("Common Ruby Errors", 45),
            new TalkData("Pair Programming vs Noise", 45),
            new TalkData("Programming in the Boondocks of Seattle", 30),
            new TalkData("Ruby vs. Clojure for Back-End Development", 30),
            new TalkData("User Interface CSS in Rails Apps", 30)
    ]

    public static SPECIFICATION_SECOND_TRACK_TALKS = [
            new TalkData("Communicating Over Distance", 60),
            new TalkData("Rails Magic", 60),
            new TalkData("Woah", 30),
            new TalkData("Sit Down and Write", 30),
            new TalkData("Accounting-Driven Development", 45),
            new TalkData("Clojure Ate Scala (on my project)", 45),
            new TalkData("A World Without HackerNews", 30),
            new TalkData("Ruby on Rails Legacy App Maintenance", 60),
            new TalkData("Rails for Python Developers", 5)
    ]

    public static SPECIFICATION_TALKS = union(SPECIFICATION_FIRST_TRACK_TALKS,
                                              SPECIFICATION_SECOND_TRACK_TALKS)

}
