package org.acme.rules.drools.service;

import org.acme.rules.drools.internal.repository.WoErrorsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


@Service
public class WoErrorsServiceImpl implements WoErrorsService {
    @Autowired
    private WoErrorsDAO woErrorsDAO;

    private static final Logger log = LoggerFactory.getLogger(WoErrorsServiceImpl.class);

    @Override
    public void add(String woNumber, String grouping, RuntimeException error) {
       /*
        List<ErrorOts> errorOts = !nroOt.equals("-") ? errorOtsDao.findErrorOtsActiveAndNroOt(nroOt, Constants.NOK).orElse(List.of(new ErrorOts())) :
                errorOtsDao.findErrorOtsActiveAndDomiDireccion(domiDireccionCompleta, Constants.NOK).orElse(List.of(new ErrorOts()));
        ErrorOts errorOt = errorOts.get(0);
        errorOt.setMensajeError(controlMensajeError(messageException(error)));
        errorOt.setCantReintentos(errorOt.getCantReintentos() == null || errorOt.getCantReintentos() == 0 ? 1 : errorOt.getCantReintentos() + 1);
        errorOt.setStatus(Constants.NOK);
        errorOt.setId(idReglaTipo);
        errorOt.setNroOt(nroOt);
        errorOtsDao.save(errorOt);
        */
    }

    @Override
    public void add(String woNumber, RuntimeException error) {
        // implement
    }

    protected String errorMessageControl(String mensajeError) {
        if (mensajeError != null && !mensajeError.isEmpty())
            return mensajeError.substring(0, mensajeError.length() <= 900 ? mensajeError.length() : 1000 - 100);
        return "";
    }

    protected Date computeSearchDate(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public String messageException(RuntimeException e){
        return e.getMessage()!=null ? e.getMessage() : e.getStackTrace()!=null && e.getStackTrace().length>0 ? e.getStackTrace()[0].toString() : Arrays.toString(e.getStackTrace());
    }
}
