package data

import dto.Talk

import static org.apache.commons.collections.ListUtils.union

class SpecificationDatasets {

    public static SPECIFICATION_FIRST_TRACK_TALKS = [
            new Talk("Writing Fast Tests Against Enterprise Rails", 60),
            new Talk("Overdoing it in Python", 45),
            new Talk("Lua for the Masses", 30),
            new Talk("Ruby Errors from Mismatched Gem Versions", 45),
            new Talk("Ruby on Rails: Why We Should Move On", 60),
            new Talk("Common Ruby Errors", 45),
            new Talk("Pair Programming vs Noise", 45),
            new Talk("Programming in the Boondocks of Seattle", 30),
            new Talk("Ruby vs. Clojure for Back-End Development", 30),
            new Talk("User Interface CSS in Rails Apps", 30)
    ]

    public static SPECIFICATION_SECOND_TRACK_TALKS = [
            new Talk("Communicating Over Distance", 60),
            new Talk("Rails Magic", 60),
            new Talk("Woah", 30),
            new Talk("Sit Down and Write", 30),
            new Talk("Accounting-Driven Development", 45),
            new Talk("Clojure Ate Scala (on my project)", 45),
            new Talk("A World Without HackerNews", 30),
            new Talk("Ruby on Rails Legacy App Maintenance", 60),
            new Talk("Rails for Python Developers", 5)
    ]

    public static SPECIFICATION_TALKS = union(SPECIFICATION_FIRST_TRACK_TALKS,
                                              SPECIFICATION_SECOND_TRACK_TALKS)

}
