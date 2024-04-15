

package com.keeay.anepoch.base.commons.monitor;


import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


/**
 * @author ying.pan
 * @date 2019/7/8 1:54 PM.
 */
@Slf4j
public abstract class AbstractBizTemplate {
    //    private static final Logger LOGGER = getLogger(AbstractBizTemplate.class);
    protected String monitorName;
    protected String labelName;

    protected void checkParam() {

    }

    protected void afterProcess() {
    }

    protected void processParamException(Throwable ex) {
//        LOGGER.error("Param exception: ", ex);
        throwBizException(ex);
        ErrorCode.of(ErrorCode.PARAMETER_ERROR_CODE, ex.getMessage()).throwBizException();
    }

    private void throwBizException(Throwable ex) {
        if (ex instanceof BizException) {
            throw (BizException) ex;
        }
    }

    protected void processExceptionChain(Throwable ex) {
        if (Objects.isNull(ex)) {
            return;
        }
        throwBizException(ex);
        log.error("biz process exception: ", ex);
        ErrorCode.of(ErrorCode.RUNTIME_ERROR_CODE, ex.getMessage()).throwBizException();
    }

}
