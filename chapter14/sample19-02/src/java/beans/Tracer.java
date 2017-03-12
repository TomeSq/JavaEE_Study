package beans;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class Tracer implements Serializable {

    @Inject
    transient Logger logger;
    @Inject
    LoggingUtil u;

    @AroundConstruct
    public void ConstructorLog(InvocationContext ic) throws Exception {
        logger.fine("◆ENTER◆" + u.ConstructorName(ic) + "【PARAM】" + u.paramList(ic));
        try {
            ic.proceed();
        } finally {
            logger.fine("◆EXIT◆" + u.ConstructorName(ic));
        }
    }

    @AroundInvoke
    public Object MethodLog(InvocationContext ic) throws Exception {
        logger.fine("■ENTER■" + u.className(ic) + "#" + u.methodName(ic) + "【PARAM】" + u.paramList(ic));
        Object result = null;
        try {
            result = ic.proceed();
            return result;
        } finally {
            logger.fine("■EXIT■" + u.className(ic) + "#" + u.methodName(ic) + "【RESULT】" + result);
        }
    }

}
