package com.tushuoBolg.controller;

import com.tushuoBolg.form.PageRequest;
import com.tushuoBolg.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hyrj on 2019/10/11.
 */
@Controller
@RequestMapping(value = "log")
public class OperationLogController extends BaseController{

    /**
     * 日志信息的展示
     * @return
     */
//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView showLog(PageRequest pageRequest){
//        final Page<LogOperation> page = new Page(pageRequest.getCurrentPage(), pageRequest.getPageLimit());
//        this.sessionUtil.findPageByDetachedCriteria(
//                DetachedCriteria.forClass(LogOperation.class),
//                page
//        );
//        return successJsonView("data",page);
//    }
//
//    /**
//     * 日志的新增
//     * @param logOperation
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public ModelAndView addLog(LogOperation logOperation){
//        this.sessionUtil.save(logOperation);
//        return successJsonView();
//    }

    public void aa(){
        System.out.println("aaa");
    }
}
