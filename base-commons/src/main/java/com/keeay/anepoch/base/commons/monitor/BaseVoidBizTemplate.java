


package com.keeay.anepoch.base.commons.monitor;

//import org.slf4j.Logger;
//
//import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author ying.pan
 * @date 2019/7/8 2:38 PM.
 */
public abstract class BaseVoidBizTemplate extends AbstractBizTemplate {
//    private static final Logger LOGGER = getLogger(BaseVoidBizTemplate.class);

    /**
     * 该方法只可以放在Method中new 方法
     */
    public BaseVoidBizTemplate() {
        super();
    }


    public void execute() {
        try {
            checkParam();
        } catch (Exception ex) {
            processParamException(ex);
        }
        try {
            process();
        } catch (Exception ex) {
            onException(ex);
        } finally {
            afterProcess();
        }
    }

    protected void onException(Exception ex) {
//        LOGGER.error("monitor key {} label {} has an error:", monitorName, labelName, ex);
        processExceptionChain(ex);
    }

    /**
     * 实际处理的函数
     *
     * @return
     */
    protected abstract void process();
}
