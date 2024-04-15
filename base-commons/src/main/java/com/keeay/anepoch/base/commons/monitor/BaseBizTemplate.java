


package com.keeay.anepoch.base.commons.monitor;

//import org.slf4j.Logger;
//
//import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author ying.pan
 * @date 2019/7/8 2:05 PM.
 */
public abstract class BaseBizTemplate<T> extends AbstractBizTemplate {
//    private static final Logger LOGGER = getLogger(BaseBizTemplate.class);

    /**
     * 该方法只可以放在Method中new 方法
     */
    public BaseBizTemplate() {
        super();
    }

    public T execute() {
        try {
            checkParam();
        } catch (Exception ex) {
            processParamException(ex);
        }
        T obj = null;
        try {
            obj = process();
            return obj;
        } catch (Exception ex) {
            return onException(ex);
        } finally {
            afterProcess();
        }
    }

    protected T onException(Exception ex) {
//        LOGGER.error("monitor key {} label {} has an error:", monitorName, labelName, ex);
        processExceptionChain(ex);
        return null;
    }

    /**
     * 实际处理的函数
     *
     * @return
     */
    protected abstract T process();
}
