package rsam.utn2017.dam.agenda.model;

/**
 * Created by npadula on 16/1/2018.
 */

public class Utils {
    public void iniciarListas() {

    }

    public Usuario[] getListaPersonas() {
        Usuario[] usrs = new Usuario[5];


        usrs[0]=new Usuario("Juan Perez", "1234");
        usrs[1]=new Usuario("John Doe", "456");
        usrs[2]=new Usuario("Camila Diaz", "777");
        usrs[3]=new Usuario("Nick Warren", "323");
        usrs[4]=new Usuario("Hernan Cattaneo", "4477");

        return usrs;
    }
}
