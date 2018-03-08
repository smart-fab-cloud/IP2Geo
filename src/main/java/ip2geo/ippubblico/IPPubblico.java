package ip2geo.ippubblico;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.lang3.StringUtils;

@Path("/IPPubblico")
public class IPPubblico {
    
    public IPPubblico() {}
    
    @GET
    @Path("/{ip}")
    public Response controllaSePubblico(@PathParam("ip") String ip) {
        // Scompone "ip" nelle "parti" separate da "."
        String[] parti = ip.split("\\.");
        
        // Se le "parti" non sono 4, allora l'IP passato non � valido
        if(parti.length != 4)
            return Response.status(Status.BAD_REQUEST)
                    .entity("L'IP passato non � formato da 4 numeri.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Se almeno una delle "parti" non � un numero, allora
        // l'IP passato non � valido
        for (int i=0; i<parti.length; i++)
            if (!StringUtils.isNumeric(parti[i]))
                return Response.status(Status.BAD_REQUEST)
                    .entity("L'IP passato non � formato da soli numeri.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();

        // Isola i numeri dell'IP in variabili intere diverse
        int primoNumero = Integer.parseInt(parti[0]);
        int secondoNumero = Integer.parseInt(parti[1]);
        int terzoNumero = Integer.parseInt(parti[2]);
        int quartoNumero = Integer.parseInt(parti[3]);
        
        // Se almeno uno dei numeri � inferiore a 0 o superiore a 255,
        // allora l'indirizzo IP non � valido
        if (primoNumero < 0 || primoNumero > 255 ||
                secondoNumero < 0 || secondoNumero > 255 ||
                terzoNumero < 0 || terzoNumero > 255 ||
                quartoNumero < 0 || quartoNumero > 255)
            return Response.status(Status.BAD_REQUEST)
                .entity("L'IP passato non � formato da numeri compresi tra 0 e 255.")
                .type(MediaType.TEXT_PLAIN)
                .build();
                
        // Se il primo numero � 10, allora l'indirizzo IP � privato
        if(primoNumero == 10)
            return Response.status(Status.OK)
                    .entity(new IPPubblicoInfo(false))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        // Se il primo numero � 172 e il secondo numero � compreso tra 
        // 16 e 31, allora l'indirizzo IP � privato
        if(primoNumero == 172 && secondoNumero >= 16 && secondoNumero <= 31)
            return Response.status(Status.OK)
                    .entity(new IPPubblicoInfo(false))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        // Se il primo numero � 192 e il secondo numero � 168, allora
        // l'indirizzo IP � privato
        if(primoNumero == 192 && secondoNumero == 168)
            return Response.status(Status.OK)
                    .entity(new IPPubblicoInfo(false))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        // Altrimenti, l'indirizzo IP � privato
        return Response.status(Status.OK)
                .entity(new IPPubblicoInfo(true))
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}
