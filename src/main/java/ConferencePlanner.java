import core.AlgorithmFailureException;
import core.ConferenceTracksAssigningAlgorithmFactory;
import dto.Talk;
import dto.Track;
import io.IOFacade;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;

import java.util.List;

@CommonsLog
public class ConferencePlanner {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide a path to the file with talks data as the first argument");
        }

        IOFacade ioFacade = new IOFacade();

        List<Talk> talks = ioFacade.readAllFromFile(args[0]);

        List<Track> tracks;

        val algorithmFactory = new ConferenceTracksAssigningAlgorithmFactory();

        try {
            tracks = algorithmFactory.getOptimalAlgorithm()
                                     .assignToTracks(talks);
        } catch (AlgorithmFailureException e) {
            log.info("The optimal algorithm failed, retrying with the safe algorithm");
            tracks = algorithmFactory.getSafeAlgorithm()
                                     .assignToTracks(talks);
        }

        ioFacade.writeToConsole(tracks);
    }
}
