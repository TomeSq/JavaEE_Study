package beans;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ReaderA {

    @Inject
    transient Logger log;

	// 以下にreadメソッドを作成する
}
