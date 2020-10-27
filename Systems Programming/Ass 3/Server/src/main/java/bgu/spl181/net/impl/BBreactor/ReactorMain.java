package bgu.spl181.net.impl.BBreactor;

import bgu.spl181.net.impl.MovieRentalService.MovieRentalProtocol;
import bgu.spl181.net.impl.USTP.CommandMessageEncoderDecoder;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        SharedData shared = new SharedData();
        Server.reactor(
                7,
                Integer.parseInt(args[0]), //port
                ()->new MovieRentalProtocol<>(shared), //protocol factory
                CommandMessageEncoderDecoder::new //message encoder decoder factory
        ).serve();


    }
}
