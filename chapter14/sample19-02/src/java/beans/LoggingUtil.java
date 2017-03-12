package beans;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.enterprise.context.Dependent;
import javax.interceptor.InvocationContext;

@Dependent
public class LoggingUtil implements Serializable {

    public String className(InvocationContext ic) {
        Method method = ic.getMethod();
        Class decClass = method.getDeclaringClass();
        return decClass.getName();
    }

    public String methodName(InvocationContext ic) {
        Method method = ic.getMethod();
        return method.getName();
    }

    public String paramList(InvocationContext ic) {
        Object[] params = ic.getParameters();
        return Arrays.toString(params);
    }

    public String ConstructorName(InvocationContext ic) {
        Constructor con = ic.getConstructor();
        return con.getName();
    }
}
