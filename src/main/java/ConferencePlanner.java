import core.ConferenceTracksAssigningAlgorithmFactory;
import dto.TalkData;
import dto.TrackData;
import io.IOFacade;

import java.util.List;

public class ConferencePlanner {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide a path to the file with talks data as the first argument");
        }

        IOFacade ioFacade = new IOFacade();

        List<TalkData> talks = ioFacade.readAllFromFile(args[0]);

        List<TrackData> tracks = new ConferenceTracksAssigningAlgorithmFactory().getDefaultAlgorithm()
                                                                                .assignToTracks(talks);

        ioFacade.writeToConsole(tracks);
    }
}
